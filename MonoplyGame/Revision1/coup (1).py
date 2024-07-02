import random
from variable import p1cards1, p1cards2, p2cards1, p2cards2, p2cards, p1cards,p1coins, p2coins

####challenge functions####

def challengeP1():
  #asking if player 2 wants to challenge player 1
  print(action)
  global p1cards, p2cards, p1coins, p2coins, p1cards1, p1cards2, p2cards2, p2cards1
  chall= input(f"Will {player2} challenge player 1 from claiming that card? Y/N: ").upper()
  if chall == "Y" and action != p1cards:#player 1 wins
    eliminate= random.choice(p2cards)
    print(f"{player2}'s {eliminate} is gone.")
    eliminate= 0
    print(f"{player1} wins the challenge")

  elif chall == "Y" and action == p1cards1 or action == p1cards2:
    eliminate= random.choice(p1cards)#player 2 wins
    print(f"{player1}'s {eliminate} is gone.")
    eliminate= 0
    print(f"{player2} wins the challenge")

def challengeP2():
  #asking if player 1 wants to challenge player 2
  global p1cards, p2cards, p1coins, p2coins, p1cards1, p1cards2, p2cards2, p2cards1
  chall= input(f"Will {player1} challenge the action? Y/N: ").upper()
  if chall == "Y" and action != p2cards:#player 2 wins
    eliminate= random.choice(p1cards)
    print(f"{player1}'s {eliminate} is gone.")
    eliminate= 0
    print(f"{player2} wins the challenge")
  elif chall == "Y" and action == p2cards1 or action == p2cards2:
    #if p2cards1 or p2cards2 is not the same as action, P1 wins
    eliminate= random.choice(p2cards)#player 1 wins
    print(f"{player2}'s {eliminate} is gone.")
    eliminate= 0
    print(f"{player1} wins the challenge")

def play_game(self):
  # Main game loop
  print("Let the game begin!\n")
  quit_game = False

####card functions for player 1####

#Assassin takes out any card for 3 coins
def whackP1():
  global p1cards, p2cards, p1coins, p2coins, p1cards1, p1cards2, p2cards2, p2cards1, turns
  protec= input("Will the targeted player use the Contessa? Y/N: ").upper()
  if protec == "N": #player 1 uses assassin
      challengeP1()
      print()
      choice= input("Which card do you choose to eliminate? 1 or 2: ")
      if p1coins<3:#need 3 coins to use assassin
        print("Not enough coins (3)")
      elif choice == "1":
        print(f"{p2cards1} is gone.")
        p2cards1=0#remove chosen card
        p1coins= p1coins-3
        print(p1coins)
      else:
        print(f"{p2cards2} is gone.")
        p2cards2=0
        p1coins= p1coins-3
        print(p1coins)
  if protec== "Y":
    challengeP2()


def captain():
  global p1cards, p2cards, p1coins, p2coins, p1cards1, p1cards2, p2cards2, p2cards1
  challengeP1()
  print()
  cap= input("Will the opposing player defend with a captain? Y/N: ").upper()
  if cap=="Y":
    challengeP2()
  else:
    p1coins = p1coins+2
    print("player 1 has", p1coins, "coins.")
    p2coins = p2coins-2
    print("player 2 has", p2coins, "coins.")


#contessa: if getting assassinated, ask if you want to use contessa, then ask for challenge

def duke():
  global p1cards, p2cards, p1coins, p2coins, p1cards1, p1cards2, p2cards2, p2cards1
  challengeP1()
  p1coins= p1coins + 3
  print(p1coins)


def coup():#same as assassin, but costs 7 coins and cant defend
  global p1cards, p2cards, p1coins, p2coins, p1cards1, p1cards2, p2cards2, p2cards1
  choice= input("Take out card 1 or 2") 
  if p1coins<7:
    print("Not enough coins (7)")
  elif choice == "1":
    print(f"{p2cards1} is gone.")
    p2cards1=0
    p1coins= p1coins-7
    print(p1coins)
  elif choice == "2":
    print(f"{p2cards2} is gone.")
    p2cards2=0
    p1coins= p1coins-7
    print(p1coins)

####card functions for player 2####

def whackP2():
  global p1cards1, p1cards2, p2coins
  protec= input("Will the targeted player use the Contessa? Y/N: ")
  if protec == "N": #player 1 says no to assassin
      challengeP2()
      print()
      choice= input("Which card do you choose to eliminate? 1 or 2: ")
      if p2coins<3:#need 3 coins to use assassin
        print("Not enough coins (3)")
      if choice == "1":
        print(f"{p1cards1} is gone.")
        p1cards1=0#remove chosen card
        p2coins= p2coins-3
        print(p2coins)
      else:
        print(f"{p1cards2} is gone.")
        p1cards2=0
        p2coins= p2coins-3
        print(p2coins)
  if protec== "Y":
    challengeP1()

def captainP2():
  global p1cards, p2cards, p1coins, p2coins, p1cards1, p1cards2, p2cards2, p2cards1
  challengeP2()
  cap= input("Will the targeted player defend with a captain? Y/N: ")
  if cap=="Y":
    challengeP1()
  else:
    p1coins = p1coins-2
    print(p1coins)
    p2coins = p2coins+2
    print(p2coins)

def dukeP2():
  global p1cards, p2cards, p1coins, p2coins, p1cards1, p1cards2, p2cards2, p2cards1
  challengeP2()
  p2coins= p2coins +3
  print(p2coins)

def coupP2():#same as assassin, but costs 7 coins and cant defend
  global p1cards, p2cards, p1coins, p2coins, p1cards1, p1cards2, p2cards2, p2cards1
  choice= input("Take out card 1 or 2")  
  if p2coins<7:
    print("Not enough coins (7)")
  elif choice == "1":
    print(f"{p1cards1} is gone.")
    p1cards1= 0
    p2coins= p2coins-7
    print(p2coins)
  elif choice == "2":
    print(f"{p1cards2} is gone.")
    p1cards2= 0
    p2coins= p2coins-7
    print(p2coins)
  else:
    print("1 or 2")  

####main function####

print("Welcome to Coup. Win by being the last one standing.")
player1= input("Enter player 1: ")
player2= input("Enter player 2: ")
print()

print(f"{player1} cards:", p1cards1, p1cards2)
print(f"{player2} cards:", p2cards1, p2cards2)
print()
print()
#every turn, each player gets 1 coin + foreign aid

while p1cards1!=0 and p1cards2!=0 or p2cards1!=0 and p2cards2!=0:
  p1aid = 2  
  p2aid = 2
  x=1
  if x==1:
    print(f"It is {player1}'s turn.")
  print()

  p2duke= input(f"Will {player2} use a Duke to block foreign aid? Y/N: ").lower()
  if p2duke=="y":
    chall= input(f"Will {player1} challenge the Duke? Y/N: ").upper()
    if chall == "Y" and p2cards1== "duke" or p2cards2=="duke":
      eliminate= random.choice(p2cards)
      print(f"{player2}'s {eliminate} is gone")
      eliminate= 0
      print(f"{player1} wins the challenge.")
    elif chall == "Y" and p2cards!= "Duke":
      eliminate= random.choice(p1cards)
      print(f"{player1}'s {eliminate} is gone.")
      eliminate= 0
      print(f"{player2} wins the challenge.")
    p1aid=0
  p1coins= p1coins+1+p1aid #1 passive coin+ foreign aid if allowed
  print(player1, "coins:", p1coins) #display p1 coins

  action= input("What card would you like to use: ")
  print()
  if action== "duke":
    duke()
  if action=="assassin":
    whackP1()
  if action=="captain":
    captain()  
  if action=="coup":
    coup()
  print()
  x= x+1

  if x==2:
    print(f"It is {player2}'s turn.")
  print()

  p1duke= input(f"Will {player1} use a Duke to block foreign aid? Y/N: ").lower()
  if p1duke=="y":
    chall= input(f"Will {player2} challenge the Duke? Y/N: ").upper()
    if chall == "Y" and p1cards1== "duke" or p1cards2=="duke":
      eliminate= random.choice(p1cards)
      print(f"{player1}'s {eliminate} is gone")
      eliminate= 0
      print(f"{player2} wins the challenge.")
    elif chall == "Y" and p1cards!= "Duke":
      eliminate= random.choice(p2cards)
      print(f"{player2}'s {eliminate} is gone.")
      eliminate= 0
      print(f"{player1} wins the challenge.")
    p2aid=0
  p2coins= p2coins+1+p2aid #1 passive coin+ foreign aid if allowed
  print(player2, "coins:", p2coins) #display p1 coins

  action= input("What card would you like to use: ")
  print()
  if action== "duke":
    duke()
  if action=="assassin":
    whackP1()
  if action=="captain":
    captain()  
  if action=="coup":
    coup()
  print()
  



  ##########             REMOVE LINE AFTER FINSIHED            ###############




print()
print()


