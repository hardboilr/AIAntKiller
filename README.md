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

### QueenLogic

### CarrierLogic

### ScoutLogic

### WarriorLogic   

### Helper methods


