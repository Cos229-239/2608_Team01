#pragma once
#include "buttonFactory.h"
#include "UIComp.h"
#include <vector>
#define ScreenWidth 1725
#define ScreenHeight 1075
#define ScreenColor wxColour(245,245,245)

#define MyBlue wxColour(22, 22, 99)
#define MyCyan wxColour(00, 88, 99)
#define MyGray wxColour(33,33,33)

//Never define methods inside this file
//Each struct/class should have their own file

class Screen;

struct UICtrlX {
	UICtrlX(Screen* Screen); Screen* screen = nullptr;
	~UICtrlX();
	UIComp* UICtrl = nullptr;
	int OrigX = 0, OrigY = 0;
	void ButtonCtrl(int x, int y);
	void Show(int x, int y, UIComp* Panel);
	void Del();
};
struct SidePanelX {
	SidePanelX(Screen* Screen); Screen* screen = nullptr;
	~SidePanelX();
	vector<UIComp*> Panels = {};
	UIComp* SidePanel = nullptr;
	void SidePanelFunctions(int numb);
	void ReActivate(UIComp* Panel);
	void CheckOverlap(UIComp* Panel, int& modX, int& modY);
	bool CheckOverlap(UIComp* Panel);
};
struct InventoryX {
	InventoryX(Screen* Screen); Screen* screen = nullptr;
	~InventoryX();
	vector<Button> InventoryButtons = { ButtonFactory::MakeButton(1, "Item1", "Price1")};
	UIComp* Inventory = nullptr;
	void InventoryFunctions(int numb);
	void AddNewButton(); 
	void DstryButton(int x, int y);
	void ChangeButtonLabl(int Index);
};
struct PortfolioX {
	PortfolioX(Screen* Screen); Screen* screen = nullptr;
	~PortfolioX();
	UIComp* Portfolio = nullptr;
};
struct MarketTrendsX {
	MarketTrendsX(Screen* Screen); Screen* screen = nullptr;
	~MarketTrendsX();
	UIComp* MarketTrends = nullptr;
};
//This is how we'll make the Panels and their functions
class MainWindow;
class Screen : public wxPanel
{
	UICtrlX* UIC = nullptr;
public: 
	wxGraphicsContext* context = nullptr;
	SidePanelX* SideP = nullptr;
	InventoryX* Inve = nullptr;
	PortfolioX* Port = nullptr;
	MarketTrendsX* Mark = nullptr;
	Screen(wxFrame* parent);
	~Screen();
	void SetPanelSize(wxSize size);
	void SetWindow(MainWindow* window);
	void OnPaint(wxPaintEvent& event);
	bool OverlappingX(UIComp* panel, UIComp* panel2), OverlappingY(UIComp* panel, UIComp* panel2);
	bool Overlap(UIComp* panel, UIComp* panel2);
	bool OffScreenX(UIComp* panel), OffScreenY(UIComp* panel);
private:
	void OnMouseClick(wxMouseEvent& event);
	void OnMouseRClick(wxMouseEvent& event);
	void ShowButton(Button& button, wxColor color);
	MainWindow* mainWindow = nullptr;
public:
	void ShowComponent(UIComp* panel);
	void UISet(UICtrlX* uic);
	wxDECLARE_EVENT_TABLE();
};