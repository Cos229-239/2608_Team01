#include "Buttons.h"
#include "wx/graphics.h"
#include "wx/dcbuffer.h"
#include "MainWindow.h"
#include < cstdlib >

wxBEGIN_EVENT_TABLE(Screen, wxPanel)
EVT_PAINT(Screen::OnPaint)
wxEND_EVENT_TABLE()

Screen::Screen(wxFrame* parent)
	: wxPanel(parent, wxID_ANY, wxDefaultPosition, wxDefaultSize)
{
	this->SetBackgroundStyle(wxBG_STYLE_PAINT);
	this->Bind(wxEVT_LEFT_UP, &Screen::OnMouseClick, this);
	this->Bind(wxEVT_RIGHT_UP, &Screen::OnMouseRClick, this);
	SideP = new SidePanelX(this);
	Inve = new InventoryX(this);
	Port = new PortfolioX(this);
	//nullptr in .h then new them here
	SideP->SidePanel->Activate();
}
//Side Panel
SidePanelX::SidePanelX(Screen* Screen) { 
	screen = Screen;  screen->SideP = this;
	SidePanel = new UIComp("SidePanel", 300, 100, {});
	SidePanel->Location(5, 100);
}
void SidePanelX::SidePanelFunctions(int numb)
{
	if (numb == 0) return;
	if (numb > Panels.size()) return;
	Panels[numb-1]->Activate();
	CheckOverlap(Panels[numb - 1]);
	return;
}
void SidePanelX::CheckOverlap(UIComp* Panel)
{
	for (int i = 0; i < Panels.size(); ++i) {
		int mod = 0;
		while (true) {
			if (screen->OverlappingX(Panel, Panels[i])) { mod += 5;
				Panel->MoveComponent(mod, 0); }
			else break; }
	}
}
//Inventory
InventoryX::InventoryX(Screen* Screen) { 
	screen = Screen;  screen->Inve = this; 
	Inventory = new UIComp("Inventory", 350, 100, InventoryButtons);
	Inventory->Location(350, 100);
	screen->SideP->Panels.push_back(Inventory);
	screen->SideP->SidePanel->AddButton(Button(screen->SideP->SidePanel->buttons.size()+1, 0, 0, 0, 0, Inventory->UIName));
}
void InventoryX::InventoryFunctions(int numb)
{

}
//Portfolio
PortfolioX::PortfolioX(Screen* Screen) { 
	screen = Screen;  screen->Port = this;
	Portfolio = new UIComp("Portfolio", 350, 100, { Button(1,0,0,0,0, "DaPic") });
	Portfolio->Location(350, 100);
	screen->SideP->Panels.push_back(Portfolio);
	screen->SideP->SidePanel->AddButton(Button(screen->SideP->SidePanel->buttons.size()+1, 0, 0, 0, 0, Portfolio->UIName));
}


void Screen::SetPanelSize(wxSize size)
{
	SetSize(size);

	Refresh();
}
void Screen::OnMouseClick(wxMouseEvent& event)
{
	int x = event.GetX();
	int y = event.GetY();

	if (SideP->SidePanel->Check(x,y)) {
		SideP->SidePanelFunctions(SideP->SidePanel->GetButton(x, y));
		Refresh();
		return;
	}
	/*
	if (ThePanelYouWant.Check(x,y)){
	//Call that Panels function
	Refresh();
	return;
	}
	//Copy all this and ctrl + d then ctrl K + ctrl U
	*/
}
void Screen::OnMouseRClick(wxMouseEvent& event)
{
	int x = event.GetX();
	int y = event.GetY();

	if (Inve->Inventory->Check(x,y)){
	Refresh();
	return;
	}
	
	/*
	if (ThePanelYouWant.Check(x,y)){
	//Call that Panels function
	Refresh();
	return;
	}
	//Copy all this and ctrl + d then ctrl K + ctrl U
	*/
}

void Screen::OnPaint(wxPaintEvent& event)
{
	wxAutoBufferedPaintDC dc(this);
	dc.Clear();
	context = wxGraphicsContext::Create(dc);
	if (!context) return;
	//Show            where it shows    which panel      background color        button color
	ShowComponent(SideP->SidePanel, wxColor(77, 77, 77), wxColour(22, 22, 99));
	ShowComponent(Inve->Inventory, wxColour(99,99,99), wxColour(00,88,99));
	ShowComponent(Port->Portfolio, wxColour(99,99,99), wxColour(33,33,33));
	wxSize dasize = GetSize();
	double Winwidth = dasize.GetWidth();
	double Winheight = dasize.GetHeight();
	context->SetPen(*wxBLACK_PEN);
	context->SetBrush(*wxCYAN_BRUSH);
}

bool Screen::OverlappingX(UIComp* panel, UIComp* panel2)
{
	if (panel == panel2 or !panel2->IsActive) return false;
	if (panel->X + panel->MainBackground.Right < panel2->MainBackground.Left) { return false; }
	if (panel->MainBackground.Left <= panel2->X + panel2->MainBackground.Right) { return true; }
	return false;
}
bool Screen::OverlappingY(UIComp* panel, UIComp* panel2)
{
	if (panel == panel2 or !panel2->IsActive) return false;
	if (panel->Y + panel->MainBackground.Bottom <= panel2->MainBackground.Top) { return false; }
	if (panel->MainBackground.Top <= panel2->Y + panel2->MainBackground.Bottom) { return true; }
	return false;
}

void Screen::ShowComponent(UIComp* panel, wxColour Main, wxColour Buttons)
{
	if (!panel->IsActive) return;

	context->SetPen(*wxBLACK_PEN);
	ShowButton(panel->MainBackground, Main);
	for (int i = 0; i < panel->buttons.size(); ++i) {
		ShowButton(panel->buttons[i], Buttons);
	}
}
void Screen::ShowButton(Button& button, wxColor color)
{
	context->SetBrush(color);
	context->DrawRectangle(button.Left, button.Top, button.Right, button.Bottom);
	if (button.Label == "") return;
	button.MakeText(this, color);
}

void Screen::SetWindow(MainWindow* window)
{
	this->mainWindow = window;
}


