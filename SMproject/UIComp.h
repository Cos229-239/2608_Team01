#pragma once
#include "buttonFactory.h"
using std::vector;
class UIComp {
	/*
	- Set the Width of the MainBackground
	- MainBackground height is automatically set with a modifier of 5 (can be changed later)
	- MainBackground width are automatically set with a modifier of 5 (can be changed later)
	- Modifier is space between background edge and button edge 
	- Set button Height (All button heights will be the same on that panel)
	*/
public:
	Button MainBackground;
	bool IsActive = false; bool Moving = true;
	int DefaultX, DefaultY;
	vector<Button> buttons;
public:
	UIComp(int width = 20, int buttonHeight = 20, vector<Button> StarterButtons = {});
	void SetParams();
	bool Check(int x, int y);
	void AddButton(Button button), AddButton(vector<Button> button);
	void EraseButton(int Index);
	void Activate();

	void MoveComponent(int x, int y);
	int GetButton(int x, int y);

};