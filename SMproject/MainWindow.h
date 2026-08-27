#pragma once
#include "wx/wx.h"

class Screen;
class MainWindow : public wxFrame
{

public: 
	MainWindow();

	Screen* buttonHandler = nullptr;
};