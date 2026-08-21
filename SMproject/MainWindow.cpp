#include "MainWindow.h"
#include "Buttons.h"
MainWindow::MainWindow() : wxFrame(nullptr, wxID_ANY, "SMproject", wxPoint(0, 0), wxSize(1725, 1075)) 
{
	//Norm Demis: 1725  1075

	buttonHandler = new Screen(this);
	buttonHandler->SetWindow(this);

	wxBoxSizer* sizer = new wxBoxSizer(wxVERTICAL);
	sizer->Add(buttonHandler, 1, wxEXPAND | wxALL, 5);
	SetSizer(sizer);
	
}
