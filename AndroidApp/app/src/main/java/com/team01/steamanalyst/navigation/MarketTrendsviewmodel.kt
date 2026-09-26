package com.team01.steamanalyst.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import com.team01.steamanalyst.data.MarketSummary
import com.team01.steamanalyst.data.MarketplaceRanking
import com.team01.steamanalyst.data.SkinPortItem
import com.team01.steamanalyst.screens.MarketTrendsScreen
import com.team01.steamanalyst.service.CS2CapService
import com.team01.steamanalyst.service.SkinportCatalogCache
import com.team01.steamanalyst.service.SkinportService
import com.team01.steamanalyst.valuation.MarketSummaryBuilder
import com.team01.steamanalyst.valuation.MarketplaceAnalyzer
import com.team01.steamanalyst.valuation.MarketplaceRankingAnalyzer
import com.team01.steamanalyst.valuation.WatchlistManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class MarketTrendsUiState(
    val query: String = "",
    val searchResults: List<SkinPortItem> = emptyList(),
    val selectedSkin: SkinPortItem? = null,
    val summary: MarketSummary? = null,
    val rankings: List<MarketplaceRanking> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val randomList: List<SkinPortItem> = emptyList()

)

class MarketTrendsViewModel(
    private val cS2CapService: CS2CapService = CS2CapService(),
    private val skinportService: SkinportService = SkinportService(),
    private val marketplaceAnalyzer: MarketplaceAnalyzer = MarketplaceAnalyzer(),
    private val summaryBuilder: MarketSummaryBuilder = MarketSummaryBuilder(),
    private val rankingAnalyzer: MarketplaceRankingAnalyzer = MarketplaceRankingAnalyzer()
) : ViewModel(){

    private val _uiState = MutableStateFlow(MarketTrendsUiState())
    val uiState: StateFlow<MarketTrendsUiState> = _uiState.asStateFlow()

    private var catalog: List<SkinPortItem> = emptyList()

    init{
        loadCatalog()
    }

    private fun loadCatalog(){
        viewModelScope.launch{
            try{
                catalog = SkinportCatalogCache.get(skinportService)
                _uiState.update { it.copy(randomList = catalog.shuffled().take(20)) }
            }catch(e: Exception){
                _uiState.update{
                    it.copy(errorMessage = "Couldn't load item list: ${e.message}")
                }
            }
        }
    }
    fun shuffleRandomList(){
        _uiState.update { it.copy(randomList = catalog.shuffled().take(20)) }
    }
    fun addToActiveWatchlist(item: SkinPortItem){
        val active = WatchlistManager.getActiveWatchlist()
        WatchlistManager.addItem(active.id, item.marketHashName)
    }

    fun onQueryChange(query: String){
        _uiState.update{it.copy(query = query)}

        if (query.isBlank()){
            _uiState.update{it.copy(searchResults = emptyList())}
            return
        }
        val matches = catalog
            .asSequence()
            .filter { it.marketHashName.contains(query, ignoreCase = true) }
            .distinctBy { it.marketHashName }
            .take(20)
            .map{ SkinPortItem(marketHashName = it.marketHashName)}
            .toList()

        _uiState.update { it.copy(searchResults = matches) }
    }

    fun onSkinSelected(skin: SkinPortItem){
        _uiState.update{
            it.copy(
                selectedSkin = skin,
                isLoading = true,
                errorMessage = null,
                summary = null,
                rankings = emptyList()
            )
        }

        viewModelScope.launch{
            try{
                val prices = withContext(Dispatchers.IO){
                    cS2CapService.fetchPrices(skin.marketHashName)
                }
                val analysis = marketplaceAnalyzer.analyze(prices)
                val summary = summaryBuilder.build(analysis)
                val rankings = rankingAnalyzer.rank(analysis)
                _uiState.update {
                    it.copy(
                        summary = summary,
                        rankings = rankings,
                        isLoading = false
                    )
                }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Failed to load marketplace prices"
                    )
                }
            }
        }
    }
    fun clearSelection(){
        _uiState.update {
            it.copy(
                selectedSkin = null,
                summary = null,
                rankings = emptyList(),
                errorMessage = null
            )
        }
    }
}

@Composable
fun MarketTrendsRoute(viewModel: MarketTrendsViewModel){
    val uiState by viewModel.uiState.collectAsState()
    uiState.errorMessage?.let{message ->
        Text(text = message)
    }

    MarketTrendsScreen(
        searchResult = uiState.searchResults,
        randomList = uiState.randomList,
        selectedSkin = uiState.selectedSkin,
        summary = uiState.summary,
        rankings = uiState.rankings,
        isLoading = uiState.isLoading,
        onQueryChange = viewModel::onQueryChange,
        onSkinSelected = viewModel::onSkinSelected,
        onBack = viewModel::clearSelection,
        onShuffle = viewModel::shuffleRandomList,
        onAddToWatchlist = viewModel::addToActiveWatchlist
    )
}