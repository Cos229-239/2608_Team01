#pragma once
#include "wx/wx.h"

class Buttons;
class MainWindow : public wxFrame
{

public: 
	MainWindow();

	Buttons* buttonHandler = nullptr;
};