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
	Mark = new MarketTrendsX(this);
	UIC = new UICtrlX(this);
	//nullptr in .h then new them here
	SideP->SidePanel->Activate();
}
Screen::~Screen()
{
	if (SideP != nullptr) delete SideP;
	if (Inve != nullptr) delete Inve;
	if (Port != nullptr) delete Port;
	if (Mark != nullptr) delete Mark;
	if (UIC != nullptr) delete UIC;
	if (context != nullptr) delete context;
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

	if (UIC->UICtrl != nullptr) {
		if (UIC->UICtrl->Check(x, y)) {
			UIC->ButtonCtrl(x, y);
			Refresh();
			return;
		}
		else UIC->Del();
	}

	if (SideP->SidePanel->Check(x, y)) {
		SideP->SidePanelFunctions(SideP->SidePanel->GetButtonID(x, y));
		Refresh();
		return;
	}
	if (Inve->Inventory->Check(x, y)) {
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
	Refresh();
}
void Screen::OnMouseRClick(wxMouseEvent& event)
{
	int x = event.GetX();
	int y = event.GetY();
	UIC->Del();
	if (SideP->SidePanel->Check(x, y)) {
		UIC->Show(x, y, SideP->SidePanel);
			Refresh();
		return;
	}
	for (int i = 0; i < SideP->Panels.size(); ++i) {
		if (SideP->Panels[i]->Check(x, y)) {
			UIC->Show(x, y, SideP->Panels[i]);
			Refresh();
			return;
		}
	}
	/*
	if (ThePanelYouWant.Check(x,y)){
	//Call that Panels function
	Refresh();
	return;
	}
	//Copy all this and ctrl + d then ctrl K + ctrl U
	*/
	Refresh();
}

void Screen::OnPaint(wxPaintEvent& event)
{
	wxAutoBufferedPaintDC dc(this);
	dc.Clear();
	context = wxGraphicsContext::Create(dc);
	if (!context) return;
	Button BackGround = Button(0, 0, ScreenWidth, 0, ScreenHeight);
	ShowButton(BackGround, ScreenColor);
	ShowComponent(SideP->SidePanel);
	for (int i = 0; i < SideP->Panels.size(); ++i) { ShowComponent(SideP->Panels[i]); }
	ShowComponent(UIC->UICtrl);

	wxSize dasize = GetSize();
	double Winwidth = dasize.GetWidth();
	double Winheight = dasize.GetHeight();
	context->SetPen(*wxBLACK_PEN);
	context->SetBrush(*wxCYAN_BRUSH);
}

bool Screen::OverlappingX(UIComp* panel, UIComp* panel2)
{
	if (panel == panel2 or !panel2->IsActive) return false;
	if (panel->MainBackground.Left + panel->MainBackground.Right < panel2->MainBackground.Left) { return false; }
	if (panel->MainBackground.Left <= panel2->MainBackground.Left + panel2->MainBackground.Right) { return true; }
	return false;
}
bool Screen::OverlappingY(UIComp* panel, UIComp* panel2)
{
	if (panel == panel2 or !panel2->IsActive) return false;
	if (panel->MainBackground.Top + panel->MainBackground.Bottom <= panel2->MainBackground.Top) { return false; }
	if (panel->MainBackground.Top <= panel2->MainBackground.Bottom + panel2->MainBackground.Top) { return true; }
	return false;
}
bool Screen::Overlap(UIComp* panel, UIComp* panel2)
{
	if (OverlappingX(panel, panel2) and OverlappingY(panel, panel2)) return true;
	return false;
}
bool Screen::OffScreenX(UIComp* panel)
{
	int tot = panel->MainBackground.Right + panel->MainBackground.Left;
	if (tot > ScreenWidth-25 or panel->MainBackground.Top < 0) return true;
	return false;
}
bool Screen::OffScreenY(UIComp* panel)
{
	int tot = panel->MainBackground.Bottom + panel->MainBackground.Top;
	if (tot > ScreenHeight-50 or panel->MainBackground.Left < 0) return true;
	return false;
}

void Screen::ShowComponent(UIComp* panel)
{
	if (panel == nullptr) return;
	if (!panel->IsActive) return;
	context->SetPen(*wxBLACK_PEN);
	ShowButton(panel->MainBackground, panel->MBColor);
	for (int i = 0; i < panel->buttons.size(); ++i) {
		ShowButton(panel->buttons[i], panel->SBColor);
	}
}
void Screen::ShowButton(Button& button, wxColor color)
{
	context->SetBrush(color);
	context->DrawRectangle(button.Left, button.Top, button.Right, button.Bottom);
	if (button.Label == "") return;
	button.MakeText(this, color);
}

void Screen::UISet(UICtrlX* uic)
{
	UIC = uic;
}
void Screen::SetWindow(MainWindow* window)
{
	this->mainWindow = window;
}
