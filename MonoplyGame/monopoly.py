import random  # Import the random module for generating random numbers

# Class representing a Property on the board
class Property:
    def __init__(self, name, property_type, color, rent, price=None, house_costs=None, hotel_cost=None):
        self.name = name  # Property name (e.g., "Mediterranean Avenue")
        self.property_type = property_type  # Type of property (e.g., "Property", "Special")
        self.color = color  # Color group of the property (e.g., "Brown")
        self.rent = rent  # Rent array for different situations (e.g., [4, 10, 30, 90, 160, 250])
        self.price = price  # Price to purchase the property
        self.house_costs = house_costs  # Cost to build houses (if applicable)
        self.hotel_cost = hotel_cost  # Cost to build a hotel (if applicable)
        self.owner = None  # The player who owns the property (initially None)
        self.num_houses = 0  # Number of houses built on the property
        self.has_hotel = False  # Flag indicating if the property has a hotel

# Class representing a player in the game
class Player:
    def __init__(self, name, balance=1500):
        self.name = name  # Player's name
        self.balance = balance  # Player's starting balance ($1500)
        self.position = 0  # Player's current position on the board
        self.properties = []  # List of properties owned by the player
        self.in_jail = False  # Flag indicating if the player is in jail

# Class representing the Monopoly game
class Monopoly:
    def __init__(self):
        self.players = []  # List of players in the game
        self.board = self.init_board()  # Initialize the board with properties and special spaces
        self.owned_properties = [None] * len(self.board)  # Initialize list tracking ownership of properties
        self.max_rounds = 100  # Maximum number of rounds in the game
        
        # Initialize Community Chest cards and shuffle them
        self.community_chest_cards = [
            ("Advance to Go (Collect $200)", 200),  # Card to advance to GO and collect $200
            ("Bank error in your favor. Collect $200", 200),  # Card to receive $200 due to a bank error
            ("Doctor's fees. Pay $50", -50),  # Card to pay $50 in doctor's fees
            ("From sale of stock you get $50", 50),  # Card to receive $50 from stock sale
            ("Get Out of Jail Free", "Get Out of Jail Free")  # Card to get out of jail for free
        ]
        random.shuffle(self.community_chest_cards)  # Shuffle the Community Chest cards

    # Initialize the board with properties and special spaces
    def init_board(self):
        return [
            Property("GO", "Special", None, None),  # Non-purchasable GO space
            Property("Mediterranean Avenue", "Property", "Brown", [4, 10, 30, 90, 160, 250], 60, 50, 250),
            Property("Community Chest", "Special", None, None),  # Non-purchasable Community Chest space
            Property("Baltic Avenue", "Property", "Brown", [8, 20, 60, 180, 320, 450], 60, 50, 250),
            Property("Income Tax", "Special", None, None),  # Non-purchasable Income Tax space
            Property("Reading Railroad", "Property", "Railroad", [25, 50, 100, 200], 200),
            Property("Oriental Avenue", "Property", "Light Blue", [6, 30, 90, 270, 400, 550], 100, 50, 250),
            Property("Chance", "Special", None, None),  # Non-purchasable Chance space
            Property("Vermont Avenue", "Property", "Light Blue", [6, 30, 90, 270, 400, 550], 100, 50, 250),
            Property("Connecticut Avenue", "Property", "Light Blue", [8, 40, 100, 300, 450, 600], 120, 50, 250),
            Property("Just Visiting/Jail", "Special", None, None),  # Non-purchasable Just Visiting/Jail space
            Property("St. Charles Place", "Property", "Pink", [10, 50, 150, 450, 625, 750], 140, 100, 500),
            Property("Electric Company", "Property", "Utility", [4, 10], 150),
            Property("States Avenue", "Property", "Pink", [10, 50, 150, 450, 625, 750], 140, 100, 500),
            Property("Virginia Avenue", "Property", "Pink", [12, 60, 180, 500, 700, 900], 160, 100, 500),
            Property("Pennsylvania Railroad", "Property", "Railroad", [25, 50, 100, 200], 200),
            Property("St. James Place", "Property", "Orange", [14, 70, 200, 550, 750, 950], 180, 100, 500),
            Property("Community Chest", "Special", None, None),  # Non-purchasable Community Chest space
            Property("Tennessee Avenue", "Property", "Orange", [14, 70, 200, 550, 750, 950], 180, 100, 500),
            Property("New York Avenue", "Property", "Orange", [16, 80, 220, 600, 800, 1000], 200, 100, 500),
            Property("Free Parking", "Special", None, None),  # Non-purchasable Free Parking space
            Property("Kentucky Avenue", "Property", "Red", [18, 90, 250, 700, 875, 1050], 220, 150, 750),
            Property("Chance", "Special", None, None),  # Non-purchasable Chance space
            Property("Indiana Avenue", "Property", "Red", [18, 90, 250, 700, 875, 1050], 220, 150, 750),
            Property("Illinois Avenue", "Property", "Red", [20, 100, 300, 750, 925, 1100], 240, 150, 750),
            Property("B.O. Railroad", "Property", "Railroad", [25, 50, 100, 200], 200),
            Property("Atlantic Avenue", "Property", "Yellow", [22, 110, 330, 800, 975, 1150], 260, 150, 750),
            Property("Ventnor Avenue", "Property", "Yellow", [22, 110, 330, 800, 975, 1150], 260, 150, 750),
            Property("Water Works", "Property", "Utility", [4, 10], 150),
            Property("Marvin Gardens", "Property", "Yellow", [24, 120, 360, 850, 1025, 1200], 280, 150, 750),
            Property("Go To Jail", "Special", None, None),  # Non-purchasable Go To Jail space
            Property("Pacific Avenue", "Property", "Green", [26, 130, 390, 900, 1100, 1275], 300, 200, 1000),
            Property("North Carolina Avenue", "Property", "Green", [26, 130, 390, 900, 1100, 1275], 300, 200, 1000),
            Property("Community Chest", "Special", None, None),  # Non-purchasable Community Chest space
            Property("Pennsylvania Avenue", "Property", "Green", [28, 150, 450, 1000, 1200, 1400], 320, 200, 1000),
            Property("Short Line", "Property", "Railroad", [25, 50, 100, 200], 200),
            Property("Chance", "Special", None, None),  # Non-purchasable Chance space
            Property("Park Place", "Property", "Dark Blue", [35, 175, 500, 1100, 1300, 1500], 350, 200, 1000),
            Property("Boardwalk", "Property", "Dark Blue", [50, 200, 600, 1400, 1700, 2000], 400, 200, 1000)
        ]

    # Set up players for the game
    def setup_players(self):
        # Ask the user for the number of players
        while True:
            try:
                num_players = int(input("How many players will be playing Monopoly (2-8)?: "))
                if 2 <= num_players <= 8:
                    break
                else:
                    print("The number of players must be between 2 and 8. Please try again.")
            except ValueError:
                print("Please enter a valid number.")

        # Get player names and add them to the list of players
        for i in range(num_players):
            name = input(f"Player {i + 1}, please enter your name: ")
            self.players.append(Player(name))

    # Print the starting balances of all players
    def print_starting_balances(self):
        for player in self.players:
            print(f"{player.name}: ${player.balance}")

    # Roll two six-sided dice and return the total
    def roll_dice(self):
        return random.randint(1, 6) + random.randint(1, 6)

    # Move the player forward by the given number of steps
    def move_player(self, player, steps):
        player.position = (player.position + steps) % len(self.board)

    # Draw a Community Chest card and apply its effect to the player
    def draw_community_chest_card(self, player):
        card, effect = self.community_chest_cards.pop(0)
        print(f"{player.name} drew a Community Chest card: {card}")
        # Apply effect to player's balance if effect is an integer
        if isinstance(effect, int):
            player.balance += effect
            if effect < 0:
                print(f"{player.name} pays ${abs(effect)} due to the card.")
            else:
                print(f"{player.name} receives ${effect} due to the card.")
        # Add the drawn card back to the deck
        self.community_chest_cards.append((card, effect))

    # Handle buying a property
    def buy_property(self, player, property_index):
        current_property = self.board[property_index]
        property_name = current_property.name
        property_price = current_property.price
        
        # Check if the player can afford the property
        if player.balance >= property_price:
            player.balance -= property_price
            player.properties.append(current_property)
            current_property.owner = player
            self.owned_properties[property_index] = player
            print(f"{player.name} bought {property_name} for ${property_price}.")
            print(f"{player.name}'s new balance: ${player.balance}.")
        else:
            print(f"{player.name} cannot afford {property_name}.")

    # Play the Monopoly game
    def play_game(self):
        self.setup_players()
        self.print_starting_balances()

        # Game loop
        current_round = 1
        while current_round <= self.max_rounds:
            for player in self.players:
                # Skip the player's turn if they have a zero or negative balance
                if player.balance <= 0:
                    continue

                print(f"\n{player.name}'s turn:")
                input("Press Enter to roll the dice...")
                dice_roll = self.roll_dice()
                print(f"{player.name} rolled {dice_roll}.")
                self.move_player(player, dice_roll)
                current_property = self.board[player.position]

                print(f"{player.name} landed on {current_property.name}.")

                # Check if the space is purchasable
                if current_property.property_type == "Special":
                    # Handle special spaces (e.g., Community Chest, Chance, Income Tax, Go To Jail)
                    if current_property.name == "Community Chest":
                        self.draw_community_chest_card(player)
                    elif current_property.name == "Chance":
                        self.draw_community_chest_card(player)  # Reusing Community Chest logic
                    elif current_property.name == "Income Tax":
                        tax_amount = min(200, int(player.balance * 0.1))
                        print(f"{player.name} pays ${tax_amount} in income tax.")
                        player.balance -= tax_amount
                        print(f"{player.name}'s new balance: ${player.balance}.")
                    elif current_property.name == "Go To Jail":
                        print(f"{player.name} is sent to jail!")
                        player.position = 10  # Assuming 10 is the jail position
                        player.in_jail = True
                elif current_property.property_type == "Property":
                    # Handle purchasable properties
                    property_index = player.position
                    if self.owned_properties[property_index] is None:
                        print(f"{current_property.name} is available for purchase.")
                        # Let player decide whether to buy the property
                        while True:
                            response = input("Do you want to buy it? (yes/no): ").lower()
                            if response == "yes":
                                self.buy_property(player, property_index)
                                break
                            elif response == "no":
                                print(f"{player.name} decided not to buy {current_property.name}.")
                                break
                            else:
                                print("Invalid input. Please enter 'yes' or 'no'.")
                    elif self.owned_properties[property_index] != player:
                        owner = self.owned_properties[property_index]
                        rent = current_property.rent[0]  # Basic rent (adjust as needed)
                        print(f"{current_property.name} is owned by {owner.name}. {player.name} pays ${rent} in rent.")
                        player.balance -= rent
                        owner.balance += rent
                        print(f"{player.name}'s new balance: ${player.balance}.")
                        print(f"{owner.name}'s new balance: ${owner.balance}.")
                        # Check if the player has gone bankrupt
                        if player.balance <= 0:
                            print(f"{player.name} has gone bankrupt!")

            current_round += 1  # Increment the round number

        # End of game summary
        remaining_players = [p for p in self.players if p.balance > 0]
        if len(remaining_players) == 1:
            print(f"\nGame over! {remaining_players[0].name} wins the game!")
        else:
            print(f"\nGame over after {self.max_rounds} rounds. It's a draw among the remaining players.")

if __name__ == "__main__":
    print("Welcome to Monopoly! Win by bankrupting the other players!\n")
    game = Monopoly()
    game.play_game()
