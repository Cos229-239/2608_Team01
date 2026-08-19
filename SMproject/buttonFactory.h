#pragma once
#include <string>
#include <vector>


struct Button {
	int ID;
	int Left, Right;
	int Top, Bottom;
	std::string Label;
	Button(int id = 0, int left = 0, int right = 0, int top = 0, int bottom = 0, std::string Labl = "") {
		ID = id;
		Left = left;
		Right = right;
		Bottom = bottom;
		Top = top;
		Label = Labl;
	}
	int GetButton(int x, int y) const {
		if (x > Left && x < Right && y > Top && y < Bottom) return ID;
		return 0;
	}
};

class ButtonFactory {
	std::vector<Button> AllButtons = {};
public:
	static Button MakeButton(int Height, int Width, int SizeH, int SizeW, int ID, std::string Labl) {
		Button button;
		int id = ID;
		int Left = Width, Right = SizeW;
		int Top = Height, Bottom = SizeH;

		button.ID = id;
		button.Left = Left;
		button.Right = Left + Right;
		button.Bottom = Top + Bottom;
		button.Top = Top;
		button.Label = Labl;

		return button;
	}
	static std::vector<Button> MakeMultiButtons(int Height, int Width, int SizeH, int SizeW, int amountright, int amountdown, std::vector<int> ID, std::vector<std::string> Labl) {
		std::vector<Button> buttons; int index = 0;

		amountright *= SizeW;
		amountdown *= SizeH;
		for (int i = Height; i < amountdown + Height; i += SizeH) {
			for (int j = Width; j < amountright + Width; j += SizeW) {
				if (index == ID.size()) break;
				buttons.push_back(ButtonFactory::MakeButton(i, j, SizeH, SizeW, ID[index], Labl[index]));
				index++;
			}
		}
		return buttons;
	}
	static void AddButtonsToVecc(std::vector<Button> ToAdd, std::vector<Button>& ToThis) {
		for (int i = 0; i < ToAdd.size(); ++i) {
			ToThis.push_back(ToAdd[i]);
		}
	}
};