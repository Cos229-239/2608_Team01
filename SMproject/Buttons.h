#pragma once
#include "wx/wx.h"
#include "buttonFactory.h"
#include "UIComp.h"
#include <vector>

class MainWindow;
class Buttons : public wxPanel
{
	
public: 
	Buttons(wxFrame* parent);
	void SetPanelSize(wxSize size);
	void SetWindow(MainWindow* window);
	void OnPaint(wxPaintEvent& event);
private:
	void OnMouseClick(wxMouseEvent& event);
	
	MainWindow* mainWindow = nullptr;

	wxDECLARE_EVENT_TABLE();
};