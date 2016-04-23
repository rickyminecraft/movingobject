# movingobject

A sponge plugin allowing named zone to move in a defined direction using any block / item

When you define a MO you select a block/item who act as a trigger, you also can use redstone. Then you hit the trigger with right click or active redstone circuit to active the MO.

#usage
```
/mo get a list of commands

/mo define start create a movingobject with a stick

/mo save <object name> save defined movingobject

/mo reload reload config file

/mo list get a list of defined movingobject

/mo delete <object name> delete named movingobject

/mo time <object name> <duration> change time in seconds between to move
default: 1

/mo length <object name> <length> change displacement length
default: the length of the object (height)

/mo direction <object name> <direction> change displacement direction
direction is: up down north south east west
default: up.

/mo hide <object name> <true/false> hide or not the object when it move
default: false

/mo changetool change tool used to define a MO

right click the block/item to make it move and again to make it return to it's initial position
```
# permissions
```
movingobject.bypass
movingobject.hide
movingobject.reload
movingobject.save
movingobject.delete
movingobject.direction
movingobject.time
movingobject.length
movingobject.list
movingobject.define
movingobject.changetool
```
