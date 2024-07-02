from challenge import challengeP1
from challenge import challengeP2
from variables import p1cards1, p1cards2, p2cards1, p2cards2, p1cards, p2cards, p1coins, p2coins, action

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