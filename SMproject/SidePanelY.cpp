#include "Buttons.h"
#include "wx/graphics.h"
#include "wx/dcbuffer.h"
#include "MainWindow.h"
#include < cstdlib >

SidePanelX::SidePanelX(Screen* Screen) {
	screen = Screen;  screen->SideP = this;
	SidePanel = new UIComp("SidePanel", 300, 100, {}, wxColour(77, 77, 77), MyBlue);
	SidePanel->Location(5, 100);
}
SidePanelX::~SidePanelX() { 
	if (SidePanel != nullptr) 
	{
		SidePanel->DeActivate();
		delete SidePanel;
	}
}
void SidePanelX::SidePanelFunctions(int numb)
{
	if (numb == 0) return;
	if (numb > Panels.size()) return;
	if (Panels[numb - 1]->IsActive) { Panels[numb - 1]->DeActivate(); return; }
	else Panels[numb - 1]->Activate();
	int modX = 0, modY = 0;
	CheckOverlap(Panels[numb - 1], modX, modY);
	return;
}
void SidePanelX::ReActivate(UIComp* Panel)
{
	Panel->DeActivate();
	Panel->Activate();
	int modX = 0, modY = 0;
	CheckOverlap(Panel, modX, modY);
}
void SidePanelX::CheckOverlap(UIComp* Panel, int& modX, int& modY)
{
	for (int i = 0; i < Panels.size(); ++i) {
		while (true) {
			if (CheckOverlap(Panel)) {
				modY += 5; Panel->MoveComponent(modX, modY);
			}
			else break;
		}
		if (screen->OffScreenY(Panel)) {
			modY = 0;
			Panel->MoveComponent(modX, modY);
			while (true) {
				if (screen->OverlappingX(Panel, Panels[i])) {
					modX += 5; Panel->MoveComponent(modX, modY);
				}
				else break;
			}
		}
	}
}
bool SidePanelX::CheckOverlap(UIComp* Panel)
{
	for (int i = 0; i < Panels.size(); ++i) { if (screen->Overlap(Panel, Panels[i])) return true; }
	return false;
}