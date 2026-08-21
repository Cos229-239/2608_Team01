#pragma once
#include "buttonFactory.h"
#include "UIComp.h"
#include <vector>

class Screen;

struct SidePanelX {
	SidePanelX(Screen* Screen); Screen* screen = nullptr;
	vector<Button> SidePanelButtons = ButtonFactory::MakeMultiButtons({ 1, 2, 3, 4, 5 }, { "Inventory", "Portfolio", "Test3", "Test4", "Test5" });
	UIComp SidePanel = UIComp(300, 75, SidePanelButtons);
	int X = 20, Y = 50;
	void SidePanelFunctions(int numb);
};
struct InventoryX {
	InventoryX(Screen* Screen); Screen* screen = nullptr;
	vector<Button> InventoryButtons = ButtonFactory::MakeMultiButtons({1,2,3,4,5}, {"Item1","Item2","Item3","Item4","Item5",});
	UIComp Inventory = UIComp(350, 100, InventoryButtons);
	int X = 350, Y = 50;
	void InventoryFunctions(int numb);
};
struct PortfolioX {
	PortfolioX(Screen* Screen); Screen* screen = nullptr;
	UIComp Portfolio = UIComp(350, 100, { Button(1,0,0,0,0, "DaPic") });
	int X = 780, Y = 50;
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
	Screen(wxFrame* parent);
	void SetPanelSize(wxSize size);
	void SetWindow(MainWindow* window);
	void OnPaint(wxPaintEvent& event);

	void ShowComponent(int x, int y, UIComp& panel, wxColour Main, wxColour Buttons);
private:
	void OnMouseClick(wxMouseEvent& event);
	void OnMouseRClick(wxMouseEvent& event);
	void ShowButton(Button buttonVecc, wxColor color);
	void MakeText(int x, int y, std::string TEXT, wxColor color);
	MainWindow* mainWindow = nullptr;

	wxDECLARE_EVENT_TABLE();
};