#include "Buttons.h"
#include "wx/graphics.h"
#include "wx/dcbuffer.h"
#include "MainWindow.h"
#include < cstdlib >

UICtrlX::UICtrlX(Screen* Screen) { screen = Screen; screen->UISet(this); }
UICtrlX::~UICtrlX(){ 
	if (UICtrl != nullptr) {
		UICtrl->DeActivate();
		delete UICtrl;
	}
}

void UICtrlX::ButtonCtrl(int x, int y)
{
		int choi = UICtrl->GetButtonID(x, y);
		switch (choi) {
			//SidePanel 215
		case 2151: {
			for (int i = 0; i < screen->SideP->Panels.size(); ++i) {
				screen->SideP->ReActivate(screen->SideP->Panels[i]);
			}
		}break;
		case 2152: {
			for (int i = 0; i < screen->SideP->Panels.size(); ++i) {
				screen->SideP->Panels[i]->DeActivate();
			}
		}break;
			//Inventory 514
		case 5141: screen->Inve->AddNewButton(); break;
		case 5142: screen->Inve->DstryButton(OrigX, OrigY); break;
		case 514: screen->Inve->Inventory->DeActivate(); break;
			//Portfolio 1615
		case 1615: screen->Port->Portfolio->DeActivate(); break;
			//MarketTrends 141
		case 141: screen->Mark->MarketTrends->DeActivate(); break;
		default: break;
		}
		Del();
}
void UICtrlX::Show(int x, int y, UIComp* Panel)
{
	if (Panel == screen->Inve->Inventory) {
		UICtrl = new UIComp("UICtrl", 150, 40, ButtonFactory::MakeMultiButtons({ 514,5141, 5142,-1 }, { "Hide", "New Button", "Erase Button", "Back"}), MyGray, wxColour(88, 00, 166));
		//Other Buttons Planned ("Show Suggested" --- "Show Lowest" --- "Show Median"
		OrigX = x; OrigY = y;
	}
	else if (Panel == screen->SideP->SidePanel) {
		UICtrl = new UIComp("UICtrl", 150, 40, ButtonFactory::MakeMultiButtons({ 2151, 2152, -1 }, {"Show All", "Hide All", "Back"}), MyGray, wxColour(88, 00, 166));
		OrigX = x; OrigY = y;
	}
	else if (Panel == screen->Port->Portfolio) {
		UICtrl = new UIComp("UICtrl", 150, 40, ButtonFactory::MakeMultiButtons({ 1615, -1 }, {"Hide", "Back"}), MyGray, wxColour(88, 00, 166));
		OrigX = x; OrigY = y;
	}
	else if (Panel == screen->Mark->MarketTrends) {
		UICtrl = new UIComp("UICtrl", 150, 40, ButtonFactory::MakeMultiButtons({ 141, -1 }, {"Hide", "Back"}), MyGray, wxColour(88, 00, 166));
		OrigX = x; OrigY = y;
	}
	UICtrl->Location(x, y);
	UICtrl->Activate();
	//Method for assigning IDs is first and second letter: (a = 1, z = 26) then Actual ID: (1 for first function)
	//Inventory = 514(ID)
	//Reason is because we'd need to use shared pointers otherwise. We can get a better method for this later.
}

void UICtrlX::Del()
{
	if (UICtrl == nullptr) return;
	OrigX = 0; OrigY = 0;
	UICtrl->DeActivate();
	delete UICtrl;
	UICtrl = nullptr;
}
