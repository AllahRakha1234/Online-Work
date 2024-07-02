from challenge import challengeP1
from challenge import challengeP2
from variables import p1cards1, p1cards2, p2cards1, p2cards2, p1cards, p2cards, p1coins, p2coins, action
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
  
    
  

