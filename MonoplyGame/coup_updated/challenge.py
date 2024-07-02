
from variables import  p1cards, p2cards, p1cards1, p1cards2, p2cards1, p2cards2, action
def challengeP1():
  #asking if player 2 wants to challenge player 1
  print(action)
  print("a")
  import random
  global p1cards, p2cards, p1coins, p2coins, p1cards1, p1cards2, p2cards2, p2cards1
  chall= input("Will player 2 challenge the action? Y/N: ")
  if chall == "Y" and action == p1cards:
    eliminate= random.choice(p2cards)
    print(f"{eliminate} is gone.")
    eliminate= 0
    print("Player 1 wins the challenge")
    
  else: #if chall == "Y" and action != p1cards1 or action != p1cards2:
    eliminate= random.choice(p1cards)
    print(f"{eliminate} is gone.")
    eliminate= 0
    print("Player 2 wins the challenge")

def challengeP2():
  #asking if player 1 wants to challenge player 2
  import random
  global p1cards, p2cards, p1coins, p2coins, p1cards1, p1cards2, p2cards2, p2cards1
  chall= input("Will player 1 challenge the action? Y/N: ")
  if chall == "Y" and action == p2cards:
    eliminate= random.choice(p1cards)
    print(f"{eliminate} is gone.")
    eliminate= 0
    print("Player 2 wins the challenge")
  elif chall == "Y" and action != p2cards[0] or action != p2cards[1]:
    eliminate= random.choice(p2cards)
    print(f"{eliminate} is gone.")
    eliminate= 0
    print("Player 1 wins the challenge")