#pragma once
#include "buttonFactory.h"
#include "UIComp.h"
#include <vector>
#define ScreenWidth 1725
#define ScreenHeight 1075

#define MyBlue wxColour(22, 22, 99)
#define MyCyan wxColour(00, 88, 99)
#define MyGray wxColour(33,33,33)

//Never define methods inside this file
//Each struct/class should have their own file

class Screen;

struct SidePanelX {
	SidePanelX(Screen* Screen); Screen* screen = nullptr;
	vector<UIComp*> Panels = {};
	UIComp* SidePanel = nullptr;
	void SidePanelFunctions(int numb);
	void ReActivate(UIComp* Panel);
	void CheckOverlap(UIComp* Panel, int& modX, int& modY);
	bool CheckOverlap(UIComp* Panel);
};
struct InventoryX {
	InventoryX(Screen* Screen); Screen* screen = nullptr;
	vector<Button> InventoryButtons = { ButtonFactory::MakeButton(1, "Item1") };
	UIComp* Inventory = nullptr;
	void InventoryFunctions(int numb);
	void AddNewButton(); 
	void DstryButton(int x, int y);
	void ChangeButtonLabl(int Index);
};
struct PortfolioX {
	PortfolioX(Screen* Screen); Screen* screen = nullptr;
	UIComp* Portfolio = nullptr;
};
struct MarketTrendsX {
	MarketTrendsX(Screen* Screen); Screen* screen = nullptr;
	UIComp* MarketTrends = nullptr;
};
//This is how we'll make the Panels and their functions
class MainWindow;
class Screen : public wxPanel
{
	
public: 
	wxGraphicsContext* context = nullptr;
	SidePanelX* SideP = nullptr;
	InventoryX* Inve = nullptr;
	PortfolioX* Port = nullptr;
	MarketTrendsX* Mark = nullptr;
	Screen(wxFrame* parent);
	void SetPanelSize(wxSize size);
	void SetWindow(MainWindow* window);
	void OnPaint(wxPaintEvent& event);
	bool OverlappingX(UIComp* panel, UIComp* panel2), OverlappingY(UIComp* panel, UIComp* panel2);
	bool Overlap(UIComp* panel, UIComp* panel2);
	bool OffScreenX(UIComp* panel), OffScreenY(UIComp* panel);
private:
	void ShowComponent(UIComp* panel);
private:
	void OnMouseClick(wxMouseEvent& event);
	void OnMouseRClick(wxMouseEvent& event);
	void ShowButton(Button& button, wxColor color);
	MainWindow* mainWindow = nullptr;

	wxDECLARE_EVENT_TABLE();
};