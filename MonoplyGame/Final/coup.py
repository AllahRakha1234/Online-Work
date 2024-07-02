import random

class CoupGame:
    def __init__(self):
        self.player1 = None
        self.player2 = None
        self.cards = ['assassin', 'duke', 'contessa', 'captain', 'assassin',
                      'duke', 'contessa', 'captain']
        self.p1cards1 = None
        self.p1cards2 = None
        self.p2cards1 = None
        self.p2cards2 = None
        self.p1coins = 10
        self.p2coins = 10
        
        self.p1cards = [self.p1cards1, self.p1cards2]
        self.p2cards = [self.p2cards1, self.p2cards2]        

    def assign_cards(self):
        self.p1cards1 = random.choice(self.cards)
        self.cards.remove(self.p1cards1)
        self.p1cards2 = random.choice(self.cards)
        self.cards.remove(self.p1cards2)
        self.p2cards1 = random.choice(self.cards)
        self.cards.remove(self.p2cards1)
        self.p2cards2 = random.choice(self.cards)
        self.cards.remove(self.p2cards2)

    def challengeP1(self, action):
        # Asking if player 2 wants to challenge player 1
        chall = input(f"Will {self.player2} challenge player 1 from claiming that card? Y/N: ").upper()
        if chall == "Y" and action != self.p1cards:  # Player 1 wins
            eliminate = random.choice(self.p2cards)
            print(f"{self.player2}'s {eliminate} is gone.")
            eliminate = 0
            print(f"{self.player1} wins the challenge")
        elif chall == "Y" and (action == self.p1cards1 or action == self.p1cards2):
            eliminate = random.choice(self.p1cards)  # Player 2 wins
            print(f"{self.player1}'s {eliminate} is gone.")
            eliminate = 0
            print(f"{self.player2} wins the challenge")

    def challengeP2(self, action):
        # Asking if player 1 wants to challenge player 2
        chall = input(f"Will {self.player1} challenge the action? Y/N: ").upper()
        if chall == "Y" and action != self.p2cards:  # Player 2 wins
            eliminate = random.choice(self.p1cards)
            print(f"{self.player1}'s {eliminate} is gone.")
            eliminate = 0
            print(f"{self.player2} wins the challenge")
        elif chall == "Y" and (action == self.p2cards1 or action == self.p2cards2):
            # If p2cards1 or p2cards2 is not the same as action, P1 wins
            eliminate = random.choice(self.p2cards)  # Player 1 wins
            print(f"{self.player2}'s {eliminate} is gone.")
            eliminate = 0
            print(f"{self.player1} wins the challenge")

    def playGame(self):
        print("Welcome to Coup. Win by being the last one standing.")
        self.player1 = input("Enter player 1: ")
        self.player2 = input("Enter player 2: ")
        print()

        self.assign_cards()

        print(f"{self.player1} cards:", self.p1cards1, self.p1cards2)
        print(f"{self.player2} cards:", self.p2cards1, self.p2cards2)
        print()

        # every turn, each player gets 1 coin + foreign aid

        while self.p1cards1 != 0 and self.p1cards2 != 0 or self.p2cards1 != 0 and self.p2cards2 != 0:
            p1aid = 2
            p2aid = 2
            x = 1
            if x == 1:
                print(f"It is {self.player1}'s turn.")
            print()

            p2duke = input(f"Will {self.player2} use a Duke to block foreign aid? Y/N: ").lower()
            if p2duke == "y":
                chall = input(f"Will {self.player1} challenge the Duke? Y/N: ").upper()
                if chall == "Y" and (self.p2cards1 == "duke" or self.p2cards2 == "duke"):
                    eliminate = random.choice(self.p2cards)
                    print(f"{self.player2}'s {eliminate} is gone")
                    eliminate = 0
                    print(f"{self.player1} wins the challenge.")
                elif chall == "Y" and self.p2cards != "Duke":
                    eliminate = random.choice(self.p1cards)
                    print(f"{self.player1}'s {eliminate} is gone.")
                    eliminate = 0
                    print(f"{self.player2} wins the challenge.")
                p1aid = 0
            self.p1coins = self.p1coins + 1 + p1aid  # 1 passive coin+ foreign aid if allowed
            print(self.player1, "coins:", self.p1coins)  # display p1 coins

            action = input("What card would you like to use: ")
            print()
            if action == "duke":
                self.duke()
            if action == "assassin":
                self.whackP1()
            if action == "captain":
                self.captain()
            if action == "coup":
                self.coup()
            print()
            x = x + 1

            if x == 2:
                print(f"It is {self.player2}'s turn.")
            print()

            p1duke = input(f"Will {self.player1} use a Duke to block foreign aid? Y/N: ").lower()
            if p1duke == "y":
                chall = input(f"Will {self.player2} challenge the Duke? Y/N: ").upper()
                if chall == "Y" and (self.p1cards1 == "duke" or self.p1cards2 == "duke"):
                    eliminate = random.choice(self.p1cards)
                    print(f"{self.player1}'s {eliminate} is gone")
                    eliminate = 0
                    print(f"{self.player2} wins the challenge.")
                elif chall == "Y" and self.p1cards != "Duke":
                    eliminate = random.choice(self.p2cards)
                    print(f"{self.player2}'s {eliminate} is gone.")
                    eliminate = 0
                    print(f"{self.player1} wins the challenge.")
                p2aid = 0
            self.p2coins = self.p2coins + 1 + p2aid  # 1 passive coin+ foreign aid if allowed
            print(self.player2, "coins:", self.p2coins)  # display p1 coins

            action = input("What card would you like to use: ")
            print()
            if action == "duke":
                self.duke()
            if action == "assassin":
                self.whackP1()
            if action == "captain":
                self.captain()
            if action == "coup":
                self.coup()
            print()

    def whackP1(self):
        protec = input("Will the targeted player use the Contessa? Y/N: ").upper()
        if protec == "N":  # player 1 uses assassin
            self.challengeP1("assassin")
            print()
            choice = input("Which card do you choose to eliminate? 1 or 2: ")
            if self.p1coins < 3:  # need 3 coins to use assassin
                print("Not enough coins (3)")
            elif choice == "1":
                print(f"{self.p2cards1} is gone.")
                self.p2cards1 = 0  # remove chosen card
                self.p1coins = self.p1coins - 3
                print(self.p1coins)
            else:
                print(f"{self.p2cards2} is gone.")
                self.p2cards2 = 0
                self.p1coins = self.p1coins - 3
                print(self.p1coins)
        if protec == "Y":
            self.challengeP2("assassin")

    def captain(self):
        self.challengeP1("captain")
        print()
        cap = input("Will the opposing player defend with a captain? Y/N: ").upper()
        if cap == "Y":
            self.challengeP2("captain")
        else:
            self.p1coins = self.p1coins + 2
            print("player 1 has", self.p1coins, "coins.")
            self.p2coins = self.p2coins - 2
            print("player 2 has", self.p2coins, "coins.")

    def duke(self):
        self.challengeP1("duke")
        self.p1coins = self.p1coins + 3
        print(self.p1coins)

    def coup(self):  # same as assassin, but costs 7 coins and can't defend
        choice = input("Take out card 1 or 2")
        if self.p1coins < 7:
            print("Not enough coins (7)")
        elif choice == "1":
            print(f"{self.p2cards1} is gone.")
            self.p2cards1 = 0
            self.p1coins = self.p1coins - 7
            print(self.p1coins)
        elif choice == "2":
            print(f"{self.p2cards2} is gone.")
            self.p2cards2 = 0
            self.p1coins = self.p1coins - 7
            print(self.p1coins)

