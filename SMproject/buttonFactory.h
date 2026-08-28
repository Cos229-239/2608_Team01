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
	std::string Label2;
	wxStaticText* Labl2 = nullptr;
	bool Active = true;
	Button(int id = 0, int left = 0, int right = 0, int top = 0, int bottom = 0, std::string Labl = "", std::string Labl2 = "") {
		ID = id;
		Left = left;
		Right = right;
		Bottom = bottom;
		Top = top;
		Label = Labl;
		Label2 = Labl2;
	}
	void SecondText(std::string labl2) { Label2 = labl2; }
	void MakeText(wxWindow* This, wxColour color, int Position = 5, int Modifier = -1, int SecPosition = -1, int SecModifier = -1) {
		ActiveCheck();
		if (Modifier <= 0 or Modifier > Bottom + Top) { Modifier = Bottom / 4; SecModifier = Modifier; }
		if (SecModifier <= 0 or SecModifier > Bottom + Top) { SecModifier = Modifier; }

		if (Labl != nullptr or Label == "") return;
		Labl = new wxStaticText(This, wxID_ANY, Label, wxPoint(Left + Position, Top + Modifier), wxSize(7 * strlen(Label.c_str()), 15));
		Labl->SetBackgroundColour(color);
		
		if (Labl2 != nullptr or Label2 == "") return;
		if (SecPosition <= 0) { SecPosition = Right - (7 * strlen(Label2.c_str())); }
		Labl2 = new wxStaticText(This, wxID_ANY, Label2, wxPoint(Left + SecPosition, Top + SecModifier), wxSize(7 * strlen(Label2.c_str()), 15));
		Labl2->SetBackgroundColour(color);
	}
	
	void ActiveCheck() {
		if (Active) return;
		if (Labl != nullptr) { delete Labl; Labl = nullptr; }
		if (Labl2 != nullptr) { delete Labl2; Labl2 = nullptr; }
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
	static Button MakeButton(int Height, int Width, int SizeH, int SizeW, int ID, std::string Labl, std::string Labl2 = "") {
		return Button(ID, Width, SizeW, Height, SizeH, Labl, Labl2);
	}
	static Button MakeButton(int ID, std::string Labl, std::string Labl2 = "") {
		return Button(ID, 0, 0, 0, 0, Labl, Labl2);
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