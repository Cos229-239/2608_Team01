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

void InventoryX::AddNewButton()
{
	wxString textInput = wxGetTextFromUser("Enter your Item Name:", "User Input Required");
	if (!textInput.IsEmpty()) {
		std::string Labl = textInput.ToStdString();
		Inventory->AddButton(Button(99, 0, 0, 0, 0, Labl));
		screen->SideP->ReActivate(Inventory);
		//For Inventory, I don't think function ID matters since we'll need the data to do anything
		//We just need it to have an ID to get the button
		//This method will be adjusted to get and modify actual data (For now just adds a button with a label)
	}
}
void InventoryX::DstryButton(int x, int y)
{
	int Index = 0;
	Inventory->SeeButton(x, y, Index);
	Inventory->EraseButton(Index);
	screen->SideP->ReActivate(Inventory);
	//Just gets rid of the clicked button for now
	//Can change parameters to specify button deletion or get confirmation later
}
void InventoryX::ChangeButtonLabl(int Index)
{
	wxString textInput = wxGetTextFromUser("Enter your Item Name:", "User Input Required");
	if (!textInput.IsEmpty()) {
		std::string Labl = textInput.ToStdString();
		Inventory->buttons[Index].Label = Labl;
		screen->SideP->ReActivate(Inventory);
	}
	//Changes button Label at a specific Index
}
