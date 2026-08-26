#pragma once
#include "wx/wx.h"
#include <string>
#include <vector>


struct Button {
	int ID;
	int Left, Right;
	int Top, Bottom;
	std::string Label;
	wxStaticText* Labl = nullptr;
	bool Active = true;
	Button(int id = 0, int left = 0, int right = 0, int top = 0, int bottom = 0, std::string Labl = "") {
		ID = id;
		Left = left;
		Right = right;
		Bottom = bottom;
		Top = top;
		Label = Labl;
	}
	void MakeText(wxWindow* This, wxColour color, int Position = 1) {
		ActiveCheck();
		if (Labl != nullptr) return;
		Labl = new wxStaticText(This, wxID_ANY, Label, wxPoint(Right / 3 + Left, Bottom / 2 + Top), wxSize(7*strlen(Label.c_str()), 15));
		Labl->SetBackgroundColour(color);
	}
	void ActiveCheck() {
		if (Active) return;
		if (Labl != nullptr) { delete Labl; Labl = nullptr; }
		return;
	}
	int GetButton(int x, int y) const {
		if (x > Left && x < (Left+Right) && y > Top && y < (Top + Bottom)) return ID;
		return 0;
	}
};

class ButtonFactory {
	std::vector<Button> AllButtons = {};
public:
	static Button MakeButton(int Height, int Width, int SizeH, int SizeW, int ID, std::string Labl) {
		return Button(ID, Width, SizeW, Height, SizeH, Labl);
	}
	static std::vector<Button> MakeMultiButtons(int Height, int Width, int SizeH, int SizeW, int amountright, int amountdown, std::vector<int> ID, std::vector<std::string> Labl) {
		std::vector<Button> buttons; int index = 0;

		amountright *= SizeW;
		amountdown *= SizeH;
		for (int i = Height; i < amountdown + Height; i += SizeH) {
			for (int j = Width; j < amountright + Width; j += SizeW) {
				if (index == ID.size() or index == Labl.size()) break;
				buttons.push_back(ButtonFactory::MakeButton(i, j, SizeH, SizeW, ID[index], Labl[index]));
				index++;
			}
		}
		return buttons;
	}
	static std::vector<Button> MakeMultiButtons(std::vector<int> ID, std::vector<std::string> Labl) {
		std::vector<Button> buttons; int index = 0;
		while (true) {
			if (index == ID.size() or index == Labl.size()) break;
			buttons.push_back(ButtonFactory::MakeButton(0, 0, 0, 0, ID[index], Labl[index]));
			index++;
		}
		return buttons;
	}
	static void AddButtonsToVecc(std::vector<Button> ToAdd, std::vector<Button>& ToThis) {
		for (int i = 0; i < ToAdd.size(); ++i) {
			ToThis.push_back(ToAdd[i]);
		}
	}
};