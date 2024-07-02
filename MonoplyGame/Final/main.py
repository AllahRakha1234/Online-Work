from monopoly import Monopoly
from coup import CoupGame

cont = "yes"

if __name__ == '__main__':
    while cont == "yes":
        g = int(input("Which game do you want to play? 1. Monopoly, 2. Coup "))
        if g == 1:
            print("Welcome to Monopoly! Win by bankrupting the other players!\n")
            game = Monopoly()
            game.play_game()
        elif g == 2:
            # print("\nWelcome to Coup. Win by being the last one standing.")
            game = CoupGame()
            game.playGame()
        else:
            pass
        cont = input("Do you want to play again?")
