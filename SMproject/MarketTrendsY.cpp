#include "Buttons.h"
#include "wx/graphics.h"
#include "wx/dcbuffer.h"
#include "MainWindow.h"
#include < cstdlib >

MarketTrendsX::MarketTrendsX(Screen* Screen) {
	screen = Screen;  screen->Mark = this;
	MarketTrends = new UIComp("MarketTrends", 350, 150, { Button(1, 0, 0, 0, 0, "DaMarket") }, wxColour(99, 99, 99), MyGray);
	MarketTrends->Location(350, 100);
	screen->SideP->Panels.push_back(MarketTrends);
	screen->SideP->SidePanel->AddButton(Button(screen->SideP->SidePanel->buttons.size() + 1, 0, 0, 0, 0, MarketTrends->UIName));
}

MarketTrendsX::~MarketTrendsX() {
	if (MarketTrends != nullptr)
	{
		MarketTrends->DeActivate();
		delete MarketTrends;
	}
}
