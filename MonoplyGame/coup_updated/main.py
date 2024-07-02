from cards import captain, coup, duke, whackP1
from cardsP2 import whackP2, dukeP2, captainP2, coupP2
import random
from challenge import challengeP1, challengeP2
from variables import p1cards1, p1cards2, p2cards1, p2cards2, p2cards, p1cards,p1coins, p2coins, action

print("Welcome to Coup. Win by being the last one standing.")

player1= input("Enter player 1: ")
player2= input("Enter player 2: ")

print(f"{player1} cards:", p1cards1, p1cards2)
print(f"{player2} cards:", p2cards1, p2cards2)
#every turn, each player gets 1 coin + foreign aid

while p1cards1!=0 and p1cards2!=0 or p2cards1!=0 and p2cards2!=0:
  # p1aid = 2  
  # p2aid = 2
  x=1
  if x==1:
    print(f"It is {player1}'s turn.")
  print()

  # p2duke= input(f"Will {player2} use a Duke to block foreign aid? Y/N: ").lower()
  # if p2duke=="y":
  #   chall= input(f"Will {player1} challenge the Duke? Y/N: ").upper()
  #   if chall == "Y" and (p2cards1 != 'duke' or p2cards2 != 'duke' or p2cards1 != 'Duke' or p2cards2 != 'Duke'):
  #     eliminate= random.choice(p2cards)
  #     print(eliminate)
  #     eliminate= 0
  #     print(f"{player2} loses the challenge.")
  #   elif chall == "Y" and (p2cards1 == 'duke' or p2cards2 == 'duke' or p2cards1 == 'Duke' or p2cards2 == 'Duke'):
  #     eliminate= random.choice(p1cards)
  #     print(eliminate)
  #     eliminate= 0
  #     print(f"{player1} loses the challenge.")
  #   p1aid=0
  p1coins= p1coins+1#+p1aid#1 passive coin+ foreign aid if allowed
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
  x= x+1
  
  # current= next(turns)#player 2 turn
  # print(f"{current}'s turn")

  

  # p2duke= input(f"{player2} Use Duke? ")
  # if p1duke == "Yes":
  #   p2aid=0
  #   print(":)")
  # if p1duke == "Yes":
  #   p2aid=0
  #   print(":)")
  # if p2duke=="Yes":
  #   p1aid=0
  #   print(":)")
  # p1duke= input(f"{player1} Use Duke? ")
  # p2duke= input(f"{player2} Use Duke? ")
  # p1coins= p1coins+1+p1aid
  # p2coins= p2coins+1+p2aid
  # print(player1, "coins:", p1coins) #display p1 coins
  # print(player2, "coins:", p2coins) #display p2 coins
  
  break##########             REMOVE LINE AFTER FINSIHED            ###############

  
print()
print()
  
