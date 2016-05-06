## Introduction 

Initially our aim was to create very **low level ant-behaviour** which would mostly be independent of ant-type. Types of behaviour would be kill, hunt, flee, scavenge, breed, explore etc. Then **combining a subset of these low-level bahaviours** to create different types **mid-level** behaviour akin **moods/state of mind**. For instance creating an aggressive mood would be achieved by combining hunt, kill, scavenge and then create a set of weighted probabilities for one behaviour to be triggered. Finally on top of that a **high-level** type of behaviour where it would be possible to create specific **goals**, such as set two warrior-ants to attack enemy queen or assign warrior-ant to protect queen.



What we found reality
          

get another layer      

## Overview    

### a3.ai.JT_Destroyer ###
JT_Destroyer implements `IAntAI`. The class is responsible for delegating work to all sub-ai's; CarrierLogic, QueenLogic, ScoutLogic and WarriorLogic. 

![hierachy](https://raw.githubusercontent.com/hardboilr/AIAntKiller/master/img/hierachy.png?token=AHPOUO4MT-R9QSq6TPmdWpeJbYtlYpxvks5XNYkywA%3D%3D)

### A-star implementation

### Collective Memory
A simple class that contains information about the current state of the game.  
Right now it contains:
* Map with all the tiles in the game, is constantly updated by all ants in the game (used by all behaviour classes)
* List of all current alive team ants (used for determing the next ant type onLayEgg)
* The spawnpoint of queen ant (used for positioning of breeding and deposit locations)
* WorldSize is stored (used in pathfinding)
* Current turn is stored (uses in some behaviour classes)
* Team id is stored (used in some behaviour classes) 

The map is updated each time the getAction method is called on any of the ants in the game, the current and visible locations is saved in collective memory by overriding the data already stored for these locations.
### QueenLogic
Responsible for the hatching and breeding of new ants.  
##### Breeding and deposit locations
All through the game the queen creates new "breeding" and "deposit" locations which is used for the breeding of new ants. Breeding locations is locations close to the spawnpoint of the queen  with a low foodCount to avoid blocking the food. When the queen has enough food for a laying an egg, it looks for a breeding location to lay the egg. Deposit locations is placed a little further from the queen spawn and is a locations with no ants. When the queen is low on food, it looks for a deposit location for to get food for another egg.  
Picks up food if the foodCount is low, and it's a possible action. To speed up the breeding of ants, in the first 30 rounds the queen lay eggs at all free locations, after this only breeding locations can hold eggs.  
##### Finding breeding location
When the queen has enough food to lay an egg it uses the collective memory, and looks for the closest empty breeding location. For this it uses the pathfinding algorithm implemented. 
For the queen to lay an egg it needs 5 action points, this does not necessarily match the amount of points the queen have when standing in front of a breeding location. Therefore the queen looks at location right in front and if it is a breeding location and the amount of action points is less than 5, the queen passes until it can lay an egg.

##### Finding deposit location
When the queen is low on food, it also uses the collective memory, looking for the closest location with the highest food count. Here the queen also uses the pathfinding algorithm.

##### Determining ant type
When the method onLayEgg is called the ai uses another class called Breed, this class uses the current ants, and the current turn to determine which type of ant is to be hatched next. This class also makes sure that the first ant is always a Carrier ant, so our ants can get out of an inclosure if that is the case. 

### CarrierLogic

### ScoutLogic

### WarriorLogic   

### Helper methods

