## Notes
This README is also available on this project's github repository [here](https://github.com/hardboilr/AIAntKiller "Antwars Github Project"). 

## Project overview 

Initially our aim was to create very **low level ant-behaviour** which would mostly be independent of ant-type. Types of behavior would be kill, hunt, flee, scavenge, breed, explore etc. This layer would essentially be solely responsible for returning actions such as *Turn Left* or *Attack*. **Combining these low-level behaviors** would then create different types **mid-level** behavior akin **moods/state of mind**, based on a configuration of weighted probabilities. For instance creating an aggressive mood would be achieved by having a higher probability  against hunt, kill or scavenge behaviors. Finally on top of that a **high-level** type of behavior where it would be possible to create specific **goals**, such as set two warrior-ants to attack the enemy queen or assign an warrior-ant to protect the queen, again by **using mid-level behavior**.
We found that in practice the lines between this four-layer structure would often be blurred and it was difficult to completely comply to this design rule when developing, as illustrated below.   

In practice each ant-type has it own main logic serving as the highest layer. The logic take action based on when a specific set of conditions have been made. The conditions are in order of priority. Intentionally we do not save an ant's state between turns, but utilize a `CollectiveMemory.class`, which can be used to make more informed decisions. We feel this mimics how ants work in nature - that an ant does not have a memory per se but rely on its perception about the environment to make a decision in the here and now.

The main logic can then directly choose to return an action such as *Attack* (lowest level action) or rely on a specific implementation of some behavior such as *ScavengeFood* to get an action it can return. Example below:
Looking at the *CarrierLogic*, the first order of priority is:
> a. When ant have food and current position is a deposit, then drop food

The action taken is: *DropFood* and that's it (1 line of code). The last order of priority is:

> d. When ant max food load has been reached, then return to deposit location with lowest food count 

This action is on the contrary much more complex and relies on the `ScavengeFood.class` (around 100 lines of code). 

In conclusion, we would say that we have managed to come close to our initial idea. The compromise is that we specialize logic against ant types, sacrificing interchangeability between ant-types. Secondly the highest level logic layer can both make decisions on lowest level actions or rely on specialized implementations of behavior. Figure 1 roughly illustrates this. *CollectiveMemory* is created by *JT_Destroyer* and passed onto other classes. ShortestPath is used by several the two middle layers and the various static utility methods are used on essentially all layers. 

<p>
	<img src="https://raw.githubusercontent.com/hardboilr/AIAntKiller/master/img/hierachy.png?token=AHPOUO4MT-R9QSq6TPmdWpeJbYtlYpxvks5XNYkywA%3D%3D" alt>
	<br>
	<em>Figure 1: Project overview</em>
</p>
          
## Project management 

We have used [Trello](https://trello.com/ "Trello front page") for project management, so we could easily plan iterations and allot features between us. Especially important when working separately. Figure 2 shows a snapshot from the Trello-board which would obviously change frequently.

<p>
    <img src="https://raw.githubusercontent.com/hardboilr/AIAntKiller/master/img/trello.PNG?token=AHPOUA7Qcf7gvEoACM8OFjjs7Ofdcxfyks5XNZKswA%3D%3D" alt>
	<br>
    <em>Figure 2: Project Management with Trello</em>
</p>

## Overview    

### [`a3.ai.JT_Destroyer`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/ai/JT_Destroyer.java) ###

JT_Destroyer implements `IAntAI`. Is responsible for delegating work to all sub-ai's: 

[`a3.logic.CarrierLogic`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/logic/CarrierLogic.java)
[`a3.logic.QueenLogic`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/logic/QueenLogic.java)
[`a3.logic.ScoutLogic`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/logic/ScoutLogic.java) 
[`a3.logic.WarriorLogic`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/logic/WarriorLogic.java). 

Also creates [`a3.memory.CollectiveMemory`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/memory/CollectiveMemory.java), which is passed on to the AI's. 

### [`a3.algorithm.ShortestPath`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/algorithm/ShortestPath.java)

Calculates shortest path to from `ILocationInfo start` to `ILocationInfo goal` using the A-Star algorithm. The ShortestPath constructor creates a two-dimensional array of Nodes, [`a3.algorithm.model.Node`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/algorithm/model/Node.java), which it get from `board.getBoardNodes();`, [`a3.algorithm.model.Board`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/algorithm/model/Board.java). The constructor in Board creates this array based on the x and y size of the world. It uses the Collective Memory to get information about world size and possible blocked or filled positions, which it consequently ignores. 
A Node contains a list of adjacent nodes which is set in the Board constructor as well. Furthermore a Node is initialized with an `int direction` which is used to calculate movement costs, a `double gVal` and `double hVal` set to `Double.POSITIVE_INFINITY`, and finally a `Node parent` used for backtracking.

`getShortestPath()` is responsible for calculating the shortest path, which it does using the principles of the [A-Star search algorithm](https://en.wikipedia.org/wiki/A*_search_algorithm). It was particular challenging to calculate a Node's H- and G-cost due to this particular game's mechanics concerning movement: Movement cost varies based on the ant's direction relatively to the movement direction.

#### H-cost
H-cost is movement cost from current node to goal node. First, straight movement cost is calculated. This is the ant's action cost for moving forward multiplied with the number of traversed nodes. Afterwards the optional costs turn costs in the X and Y direction is added to the cost. 

#### G-cost

G-cost is movement cost from current node to start node. Calculates movement cost from current node to parent. It then adds movement cost from parent node to parent's node and so on until start node is reached. 

### [`a3.memory.CollectiveMemory`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/memory/CollectiveMemory.java)

A simple class that contains information about the current state of the game.  
Right now it contains:
* Map with all the tiles in the game, is constantly updated by all ants in the game (used by all behaviour classes)
* List of all current alive team ants (used for determing the next ant type onLayEgg)
* The spawnpoint of queen ant (used for positioning of breeding and deposit locations)
* WorldSize is stored (used in pathfinding)
* Current turn is stored (uses in some behaviour classes)
* Team id is stored (used in some behaviour classes) 

The map is updated each time the getAction method is called on any of the ants in the game, the current and visible locations is saved in collective memory by overriding the data already stored for these locations.

### [`a3.logic.QueenLogic`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/logic/QueenLogic.java)
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

### [`a3.logic.CarrierLogic`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/logic/CarrierLogic.java)

Constructor calculates ant's maximum food load based on number of carriers already on team. If less than 4 ants *or* less than 3 carrier-ants, then max food load is set to 50% of ant's max food load.

Has 6 prioritized conditional courses of action:

**1.** When ant have food and current position is a deposit, then **drop food**
**2.** When ant have food and current position is a deposit, but ant **cannot drop food**, then **pass turn**
**3.** When ant is below max food load threshold, then **pickup food**
**4.** When ant can pickup filled, pickup filled
To usually frees up space in the immediate area.
**5.** When ant max food load has been reached, then **return to deposit location** with lowest food count
First the deposit location with lowest food count is found. If a location can be found, ShortestPath is used to determine the shortest path to that location. Finally the Calc-methods are used to find the movement action required to initialize the journey towards that location. 
**6.** When current position has 0 food, then **scavenge food**
ScavengeFood is used to find the optimal position for getting food.
**7.** If no action is returned from above, then pick a random action  

#### [`a3.behaviour.ScavengeFood`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/behaviour/ScavengeFood.java)

Looks at the north, south, west and east locations from ant's current position using CollectiveMemory. If a location is free, it is added to a TreeSet, which sorts the locations using a custom FoodCost**Comparator** contained in the `Tile.class`. The Set is sorted so that tiles with lowest foodCost and highest foodCount comes first. If the Set is empty, return `EAction.Pass`, otherwise uses Calc-methods to return the first action necessary to getting to that location. 

### [`a3.logic.ScoutLogic`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/logic/ScoutLogic.java)

Has 3 prioritized conditional courses of action:

1. When location has food and ant's food load is below 5, then pickup food
2. Else *explore*
3. If no action was returned, then get random action

#### [`a3.behaviour.Explore`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/behaviour/Explore.java)

Go's through all tiles in collective memory. Calculates distance to current location and calculates *explorationPropensity*. Both values are saved to each tile. ExplorationPropensity is a factor of frequency, distance to queen spawn, distance to scout and the tile's potentially unexplored neighbors: 
```java
double explorationPropensity = 
tile.getFrequency() * distanceFromAtoB(thisLocation, cm.getQueenSpawn()) * tile.getDistanceToScout();
// if tile has unexplored neighbour, multiply by 0.1
```
                
The tile with the **lowest propensity-value** is the most attractive tile for the scout to go to. This tile is found using a custom ExplorationPropensity**Comparator** found in the `Tile.class`. Finally the Calc-methods are used to find the first action necessary to getting to that location.

### [`a3.logic.WarriorLogic`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/logic/WarriorLogic.java)   
The warrior logic class is responsible for attacking enemies, warriors is always on the lookout for food, if the foodCount of the ant is lower than or equal to 3 warriors will pickup the food, to stay healthy.
##### Attacking
If there is an ant right in front of the warrior, that is not on the same team, and it is a possible action then it attacks.

##### Finding enemies
The warrior always checks its surroundings for enemies, it looks north, south, east and west to see if an enemy ant is in that direction, and turns in that direction. 
#### [`a3.behaviour.Fight`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/behaviour/Fight.java)
When the whole colony has 2 or more warriors they will start to use the information collected by the scout ants, to hunt for enemy ants. 

### Helper methods

#### [`a3.utility.Action`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/utility/Action.java)

```public static EAction getRandomAction(List<EAction> possibleActions)```: Returns a random action based on normalized weights. This static method is initialized once with the following weights:
```java
//default weights
turnLeftWeight = 75;
turnRightWeight = 75;
moveForwardWeight = 100;
moveBackwardWeight = 20;
attackWeight = 0;
pickUpFoodWeight = 10;
dropFoodWeight = 0;
eatFoodWeight = 5;
digOutWeight = 0;
dropSoilWeight = 0;
layEggWeight = 0;
passWeight = 5;
```
It is then just a matter of calling a setter, ex. `public static void setTurnLeftWeight(double turnLeftWeight)` to influence the probability of that particular attribute.

#### [`a3.utility.Calc`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/utility/Calc.java)

```public static EAction getMovementAction(int currentDirection, int direction, boolean canMoveBackward)```
Calculates movement action based on current direction (int) and destination direction (int). Also if *canMoveBackward* is false, it won't return the *MoveBackward*-action even though that would be the "best" action to take. Instead TurnLeft or TurnRight is returned.  

```public static int getMovementCost(EAction action, EAntType antType, boolean canMoveBackward)```
Calculates movement cost based on chosen Action, antType and if ant can move backward. 
**movementCost** is an expression for the action cost associated with moving an ant from one Tile to another. Example: If the ant is moving forward, then the action cost is simply the cost associated with: `EAction.MoveFoward`. On the other hand, if the ant's action is to turn left, it will eventually have to move forward also, which means that the actual movement cost is the action cost associated with: `EAction.TurnLeft + EAction.MoveForward`.

```public static int getMovementDirection(Object a, Object b)```
Accept Object types of Node or ILocationInfo (and Tile can be easily added too). 
The method simply calculates the absolute movement direction from A to B, based on the game rules. The rules state that north is 0, East is 1, South is 2 and West is 3. Example: If B tile is positioned above tile A, then the direction is 0 (north). If both tiles are on the same y-axis (in line horizontally), but B is to the left of A, then the direction is 3 (west) and so on.  

```public static int distanceFromAtoB(Object currentLocation, Object goalLocation)```
Accept Object-types of ILocationInfo, Location or Tile. 
Calculates distance from A to B when 1 movement from position A to neighbor position B, equals 1 distance.

#### [`a3.utility.Debug`](https://github.com/hardboilr/AIAntKiller/blob/master/src/a3/utility/Debug.java)

Used to print text to System.out for the purpose of debugging. Global variable *isDebug* can be set to turnOff/On printing globally. Can print colored messages to easily identify segments of texts in the console related to ant-type, ex. write `queen:` in the message and that text will be colored red. 
Also mute all messages that contains `queen:`, by setting static variable `muteQueen = true`;.  

```public static void println(Object message)```: Prints **a line** of text to the console

```public static void print(Object message)```: Prints text to the console. 
