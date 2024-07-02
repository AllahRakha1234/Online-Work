from monopoly import Monopoly

cont = "yes"

while cont == "yes":
  g = int(input("Which game do you want to play? 1. Monopoly, 2. Coup "))
  if g == 1:
    print("Welcome to Monopoly! Win by bankrupting the other players!\n")
    game = Monopoly()
    game.play_game()
  elif game == 2:
    print("Welcome to Coup. Win by being the last one standing.")
   
    pass
  else:
    pass
  cont = input("Do you want to play again?")