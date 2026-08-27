#pragma once
#include "wx/wx.h"

class Screen;
class MainWindow : public wxFrame
{

public: 
	MainWindow();
	~MainWindow();
	Screen* buttonHandler = nullptr;
};