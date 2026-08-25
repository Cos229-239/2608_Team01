#include "Buttons.h"
#include "wx/graphics.h"
#include "wx/dcbuffer.h"
#include "MainWindow.h"
#include < cstdlib >

PortfolioX::PortfolioX(Screen* Screen) {
	screen = Screen;  screen->Port = this;
	Portfolio = new UIComp("Portfolio", 350, 150, { Button(1,0,0,0,0, "DaPic") }, wxColour(99, 99, 99), MyGray);
	Portfolio->Location(350, 100);
	screen->SideP->Panels.push_back(Portfolio);
	screen->SideP->SidePanel->AddButton(Button(screen->SideP->SidePanel->buttons.size() + 1, 0, 0, 0, 0, Portfolio->UIName));
}