ShopTool
========
Version 0.x.x Alpha

Copyright (C) 2012 Felix Wiemuth, Anton Pirogov

License
-------

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

##Description
ShopTool is a tool for [SimpleServer](https://github.com/SimpleServer/SimpleServer)
to create Minecraft in-game shops based on SimpleServer *Events*.
In fact the user in the first step creates *pricelists* based on existing,
e.g. the *BasePriceList* pricelists, to be able to create shops from these
lists in the second step.

##Usage
<!--- TODO
*If you are a (Windows) user who doesn´t know how to deal with the
command line or how to edit text files with extensions other than
".txt" you may want to consult this tutorial before using ShopTool:*
-->

To create a shop for your Minecraft SimpleServer server you have to follow these steps

1. Create a pricelist
2. Create a shop file
3. Create event code from a shop file
4. Put event code into SimpleServer´s `config.xml` file

###Pricelists
Pricelists are files containing entries with items to be bought and sold.
Each line in a `.pricelist` file represents one item. A line (with all keys used) may look like this:

    ID=35:6 NAME=pinkwool PRICE_BUY=2000 PRICE_SELL=1800 NORMAL=12 MAX=64 TIME=6000 #This is a comment

* `ID` the block id of the minecraft item (see <http://www.minecraftwiki.net/wiki/Item_id> for information on IDs)
* `NAME` [opt.] an alias for the item - this is only for convenience in the text files and currently not used by ShopTool or SimpleServer
* `PRICE_BUY` the price you have to pay for buying an item of this type
* `PRICE_SELL` the amout you get for selling an item of this type
* `PRICE` can be used instead of `PRICE_BUY` and `PRICE_SELL` indicating the price for both
* `NORMAL` the amount of items the shop tends to have (the stock is changing towards this amount while time passes)
* `MAX` the maximum of items the shop can hold - this means you cannot sell infinitely
* `TIME` time interval in seconds it takes to change the current stock by one item towards 'NORMAL'

*Note:* Leaving out a price means that this item is not available for selling or buying.
Use this feature to make some items only available for purchase and orhers only for selling.

*Note:* Not all of these keys must be given - if something is left out, default values are used
(either those specified in commands or internal defaults).

To start, you can use the different basic pricelists available for download.
For example, the "items.pricelist" file contains all IDs and the corresponding SimpleServer aliases.
Copy a pricelist, delete item lines you don´t want to use and add lines that are missing.


You can generate a new pricelist based on existing pricelists.
In this process, all items given in the different lists are thrown together to build the new list.
If items occur twice, only the first one ist taken.
This is especially useful if you want to apply some values to all items of a pricelist, e.g. interests or default stock or refill time values.
It is also useful to create a raw assortment of a new shop - for this purpose there are basic pricelists that contain only items of a special group,
e.g. ore or tools. After creating a pricelist like this you can go into the file an manually adapt some items.

Note that pricelists are revised before being processed: If an item line is incorrect ShopTool tries to make it valid but if that fails, the item is discarded.

**Syntax of the pricelist command:**

    pl [factor] [interest buy] [interest sell] [[-n][-N]] normalStock [[-max][-MAX]] maxStock [[-t][-T]] stockUpdateTime [dest] [file 1] [file 2] ...

* `factor` factor all prices with this constant
* `interest buy` add an interest to all buy prices
* `interest sell` subtract an interest from all sell prices
* `dest` path to the file the new pricelist should be saved to
* `file n` a correct pricelist file

The switches `-n`, `-max` and `-t` set the corresponding values for each item where they are not already set.
Using a switch in capital letters forces the value for all items (existing values are overwritten).
Note that all switches are optional but must be given in the shown order.

###Shop(file)s
You don´t actually define shops with ShopTool. Shops are simply built by a file containing the following information:

    NAME=MyShop
    STARTCOORD=-30,70,20
    ENDCOORD=-20,70,25
    BOTCOORD=-25,70,24
    VENDORNAME=Vendor
    PRICELIST=myPricelist

* `NAME` how the shop should be called in the game
* `STARTCOORD`, `ENDCOORD` specify two points in the world between which an own area for the shop will be created
* `BOTCOORD` the coordinates where the vendor bot will stand
* `VENDORNAME` the name of the shop vendor
* `PRICELIST` the pricelist the shop should use - a path (starting from the location of the shop file) to a correct `.pricelist` file must be given, leaving out the extension

*Note:* All "coords" are minecraft coordinates and must be given in the form `x,y,z`.

*Note:* The keys (words in capital letters until the equal sign) must occur exactly in the shown order and one key per line.

###Shop events
For using a shop with SimpleServer the script code for *SimpleServer events* must be generated.
This is simply done with the event command.

**Syntax of the event command:**

    ev [-s] [dest] [file 1] [file 2] ...

* `dest` path to the file the generated code should be saved to
* `file n` a correct shop file

<!--- TODO
The switch `-s` is optional. If given... .
-->

<!--- TODO
###Use generated event code with SimpleServer
-->
