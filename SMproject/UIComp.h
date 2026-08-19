#pragma once
#include "buttonFactory.h"
using std::vector;
class UIComp {
	Button MainBackground;
	bool IsActive = false;
	vector<Button> buttons;
public:
	UIComp();
	bool Check(int x, int y);
	void AddButton(Button button), AddButton(vector<Button> button);
	void EraseButton(Button button), EraseButton(vector<Button> button);
	void Activate(bool Active), ShowComponent(int x, int y);
	int GetButton(int x, int y);

};