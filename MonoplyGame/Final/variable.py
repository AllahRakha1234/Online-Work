import random

#assign random cards, need to find out how to remove cards when chosen
cards= ['assassin', 'duke', 'contessa', 'captain', 'assassin',
        'duke', 'contessa', 'captain']

p1cards1= random.choice(cards)
cards.remove(p1cards1)
p1cards2= random.choice(cards)
cards.remove(p1cards2)
p1cards= [p1cards1, p1cards2]
print()

p2cards1= random.choice(cards)
cards.remove(p2cards1)
p2cards2= random.choice(cards)
cards.remove(p2cards2)
p2cards= [p2cards1, p2cards2]
print()

p1coins=10
p2coins=10
