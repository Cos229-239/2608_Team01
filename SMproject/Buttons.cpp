#include "Buttons.h"
#include "wx/graphics.h"
#include "wx/dcbuffer.h"
#include "MainWindow.h"
#include < cstdlib >

wxBEGIN_EVENT_TABLE(Buttons, wxPanel)
EVT_PAINT(Buttons::OnPaint)
wxEND_EVENT_TABLE()

Buttons::Buttons(wxFrame* parent) 
	: wxPanel(parent, wxID_ANY, wxDefaultPosition, wxDefaultSize)
{
	this->SetBackgroundStyle(wxBG_STYLE_PAINT);
	this->Bind(wxEVT_LEFT_UP, &Buttons::OnMouseClick, this);
}

void Buttons::SetPanelSize(wxSize size)
{
	SetSize(size);

	Refresh();
}

void Buttons::OnMouseClick(wxMouseEvent& event)
{
	int x = event.GetX();
	int y = event.GetY();

	for (int i = 0; i < 1; ++i) {
		if (true) {
			Refresh();
			return;
		}
	}
}

void Buttons::OnPaint(wxPaintEvent& event)
{
	wxAutoBufferedPaintDC dc(this);
	dc.Clear();

	wxGraphicsContext* context = wxGraphicsContext::Create(dc);
	if (!context) return;

	wxSize dasize = GetSize();
	double Winwidth = dasize.GetWidth();
	double Winheight = dasize.GetHeight();

	context->SetPen(*wxBLACK_PEN);
	context->SetBrush(*wxCYAN_BRUSH);
	
}

void Buttons::SetWindow(MainWindow* window)
{
	this->mainWindow = window;
}
