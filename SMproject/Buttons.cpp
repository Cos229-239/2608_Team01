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
	SideP->SidePanel.Activate();
}
//Side Panel
SidePanelX::SidePanelX(Screen* Screen) { screen = Screen;  Screen->SideP = this; }
void SidePanelX::SidePanelFunctions(int numb)
{
	if (numb == 0) return;
	switch (numb) {
	case 1: screen->Inve->Inventory.Activate(); break;
	case 2: screen->Port->Portfolio.Activate(); break;
	case 3: break;
	case 4: break;
	case 5: break;
	default: break;
	}
}
//Inventory
InventoryX::InventoryX(Screen* Screen) { screen = Screen;  Screen->Inve = this; }
void InventoryX::InventoryFunctions(int numb)
{

}
//Portfolio
PortfolioX::PortfolioX(Screen* Screen) { screen = Screen;  Screen->Port = this; }


void Screen::SetPanelSize(wxSize size)
{
	SetSize(size);

	Refresh();
}
void Screen::OnMouseClick(wxMouseEvent& event)
{
	int x = event.GetX();
	int y = event.GetY();

	if (SideP->SidePanel.Check(x,y)) {
		SideP->SidePanelFunctions(SideP->SidePanel.GetButton(x, y));
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

	if (Inve->Inventory.Check(x,y)){
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
	ShowComponent(SideP->X, SideP->Y, SideP->SidePanel, wxColor(77, 77, 77), wxColour(22, 22, 99));
	ShowComponent(Inve->X, Inve->Y, Inve->Inventory, wxColour(99,99,99), wxColour(00,88,99));
	ShowComponent(Port->X, Port->Y, Port->Portfolio, wxColour(99,99,99), wxColour(33,33,33));

	wxSize dasize = GetSize();
	double Winwidth = dasize.GetWidth();
	double Winheight = dasize.GetHeight();
	context->SetPen(*wxBLACK_PEN);
	context->SetBrush(*wxCYAN_BRUSH);
}

void Screen::ShowComponent(int x, int y, UIComp& panel, wxColour Main, wxColour Buttons)
{
	if (!panel.IsActive) return;

	panel.MoveComponent(x, y);
	context->SetPen(*wxBLACK_PEN);
	ShowButton(panel.MainBackground, Main);
	for (int i = 0; i < panel.buttons.size(); ++i) {
		ShowButton(panel.buttons[i], Buttons);
	}
}
void Screen::ShowButton(Button button, wxColor color)
{
	//50 330
	context->SetBrush(color);
	context->DrawRectangle(button.Left, button.Top, button.Right, button.Bottom);
	if (button.Label == "") return;
	int modX = button.Right/2 + button.Left;
	int modY = button.Bottom/2 + button.Top;
	MakeText(modX, modY, button.Label, color);
}
void Screen::MakeText(int x, int y, std::string TEXT, wxColor color)
{
	wxStaticText label = wxStaticText(this, wxID_ANY, TEXT, wxPoint(x, y), wxSize(70, 15));
	label.SetBackgroundColour(color);
}

void Screen::SetWindow(MainWindow* window)
{
	this->mainWindow = window;
}


