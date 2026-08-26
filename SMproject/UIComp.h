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
	std::string UIName;
	Button MainBackground; wxColour MBColor;
	bool IsActive = false;
	int DefaultX, DefaultY; //Button
	int X = 0, Y = 0;		//Location on screen
	vector<Button> buttons; wxColour SBColor;
public:
	UIComp(std::string Name, int width = 20, int buttonHeight = 20, vector<Button> StarterButtons = {}, wxColour Main = wxColour(77, 77, 77), wxColour Side = wxColour(22, 22, 99));
	void SetParams();
	bool Check(int x, int y);
	void AddButton(Button button), AddButton(vector<Button> button);
	void EraseButton(int Index);
	Button SeeButton(int x, int y);
	Button SeeButton(int x, int y, int& Index);
	void Activate();
	void Location(int x, int y);
	void Colors(wxColour Main, wxColour Side);
	void MoveComponent(int x = 0, int y = 0);
	int GetButtonID(int x, int y);

};