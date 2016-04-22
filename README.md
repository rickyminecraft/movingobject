# movingobject

A sponge plugin allowing named zone to move in any direction using any block / item

#usage
```
use /mo to get a list of commands

use /mo define to start create a movingobject with a stick

use /mo save <object name> to save defined movingobject

use /mo reload to reload config file

use /mo list to get a list of defined movingobject

use /mo delete <object name> to delete named movingobject

use /mo time <object name> <duration> to change time in seconds between to move
default: 1

use /mo length <object name> <length> to change displacement length
default: the length of the object (height)

use /mo direction <object name> <direction> to change displacement direction
direction is: up down north south east west
default: up.

use /mo hide <object name> <true/false> to hide or not the object when it move
default: false

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
```
