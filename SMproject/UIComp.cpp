#include "UIComp.h"

void UIComp::SetParams()
{
    int Y = MainBackground.Top;
    int X = MainBackground.Left;
    if (X == 0 and Y == 0) return;
    MainBackground.Left -= X;
    MainBackground.Top -= Y;
    for (int i = 0; i < buttons.size(); ++i) {
        buttons[i].Left -= X;
        buttons[i].Top -= Y;
    }
    //Moves it back to 0,0 before moving it again
    //Right and Bottom don't need to be changed
}

UIComp::UIComp(std::string Name, int width, int buttonHeight, vector<Button> StarterButtons)
{
    UIName = Name;
    if (width < 20) width = 20;
    MainBackground.Left = 0; MainBackground.Right = width;
    MainBackground.Top = 0; MainBackground.Bottom = 5;
    DefaultX = width - 10; DefaultY = buttonHeight;

    if (StarterButtons.size() > 0) AddButton(StarterButtons);
}

bool UIComp::Check(int x, int y)
{
    if (x > MainBackground.Left and x < (MainBackground.Right + MainBackground.Left) and y > MainBackground.Top and y < (MainBackground.Bottom+ MainBackground.Top)) return true;
    return false;
    //Checks the bounds of the mainBackground
}

void UIComp::AddButton(Button button)
{
    MainBackground.Bottom += (DefaultY + 5);
    button.Left = MainBackground.Left + 5; 
    button.Right = DefaultX;
    button.Top = MainBackground.Top + 5 + (buttons.size() * (DefaultY + 5));
    button.Bottom = DefaultY;
    buttons.push_back(button);
}
void UIComp::AddButton(vector<Button> button)
{
    for (int i = 0; i < button.size(); i++) AddButton(button[i]);
}
void UIComp::EraseButton(int Index)
{
    if (buttons.size() <= Index) return;
    MainBackground.Bottom -= (DefaultY + 5);
    for (int i = Index+1; i < buttons.size(); ++i) {
        buttons[i].Top -= (DefaultY + 5);
    }
    buttons.erase(buttons.begin() + Index);
}

void UIComp::Activate() { 
    MoveComponent();
    IsActive = !IsActive;
    for (int i = 0; i < buttons.size(); ++i) {
        buttons[i].Active = IsActive;
        buttons[i].ActiveCheck();
    }
    if (!IsActive) { SetParams(); }
}
void UIComp::Location(int x, int y)
{
    X = x;
    Y = y;
}
void UIComp::MoveComponent(int x, int y)
{
    SetParams();
    MainBackground.Left += X + x;
    MainBackground.Top += Y + y;
    for (int i = 0; i < buttons.size(); ++i) {
        buttons[i].Left += X + x;
        buttons[i].Top += Y + y;
    }
    //Moves the buttons based on the location of the UIComp
}
int UIComp::GetButton(int x, int y)
{
    for (int i = 0; i < buttons.size(); ++i) {
        int ID = buttons[i].GetButton(x, y);
        if (ID != 0) return ID;
    }
    return 0;
    //Checks each button for an ID
    //ID usage is in buttons.cpp
}
