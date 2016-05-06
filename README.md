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

We have used [Trello](https://trello.com/) for project management, so we could easily plan iterations and allot features between us. Especially important when working separately. Figure 2 shows a snapshot from the Trello-board which would obviously change frequently.

<p>
    <img src="https://raw.githubusercontent.com/hardboilr/AIAntKiller/master/img/trello.PNG?token=AHPOUA7Qcf7gvEoACM8OFjjs7Ofdcxfyks5XNZKswA%3D%3D" alt>
	<br>
    <em>Figure 2: Project Management with Trello</em>
</p>

## Overview    

### a3.ai.JT_Destroyer ###
JT_Destroyer implements `IAntAI`. Is responsible for delegating work to all sub-ai's; `a3.logic.CarrierLogic`, `a3.logic.QueenLogic`, `a3.logic.ScoutLogic` and [`a3.logic.WarriorLogic`](). Also creates `a3.memory.CollectiveMemory`, which is passed on to the AI's. 



### A-star implementation

### Collective Memory

### QueenLogic

### CarrierLogic

### ScoutLogic

### WarriorLogic   

### Helper methods


