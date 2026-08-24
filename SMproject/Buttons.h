#pragma once
#include "buttonFactory.h"
#include "UIComp.h"
#include <vector>

class Screen;

struct SidePanelX {
	SidePanelX(Screen* Screen); Screen* screen = nullptr;
	vector<UIComp*> Panels = {};
	UIComp* SidePanel = nullptr;
	void SidePanelFunctions(int numb);
	void CheckOverlap(UIComp* Panel);
};
struct InventoryX {
	InventoryX(Screen* Screen); Screen* screen = nullptr;
	vector<Button> InventoryButtons = ButtonFactory::MakeMultiButtons({1,2,3,4,5}, {"Item1","Item2","Item3","Item4","Item5",});
	UIComp* Inventory = nullptr;
	void InventoryFunctions(int numb);
};
struct PortfolioX {
	PortfolioX(Screen* Screen); Screen* screen = nullptr;
	UIComp* Portfolio = nullptr;
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
	bool OverlappingX(UIComp* panel, UIComp* panel2);
	bool OverlappingY(UIComp* panel, UIComp* panel2);
private:
	void ShowComponent(UIComp* panel, wxColour Main, wxColour Buttons);
private:
	void OnMouseClick(wxMouseEvent& event);
	void OnMouseRClick(wxMouseEvent& event);
	void ShowButton(Button& button, wxColor color);
	MainWindow* mainWindow = nullptr;

	wxDECLARE_EVENT_TABLE();
};