#include "Buttons.h"
#include "wx/graphics.h"
#include "wx/dcbuffer.h"
#include "MainWindow.h"
#include < cstdlib >

InventoryX::InventoryX(Screen* Screen) {
	screen = Screen;  screen->Inve = this;
	Inventory = new UIComp("Inventory", 350, 100, InventoryButtons, wxColour(99, 99, 99), MyCyan);
	Inventory->Location(350, 100);
	screen->SideP->Panels.push_back(Inventory);
	screen->SideP->SidePanel->AddButton(Button(screen->SideP->SidePanel->buttons.size() + 1, 0, 0, 0, 0, Inventory->UIName));
}
void InventoryX::InventoryFunctions(int numb)
{

}