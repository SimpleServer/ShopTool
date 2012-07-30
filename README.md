ShopTool
========
Version 0.x.x Alpha

Copyright (C) 2012 Felix Wiemuth, Anton Pirogov

##License
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
The result is a vendor bot showing up in the world with whom can be interacted.
In fact the user in a first step creates *pricelists* to be able to create shops from these
lists in a second step. To get started you can refer to the *Quickstart guide* below.

##Setup: Preperations to use the shop system with SimpleServer
Since the shop system is implemented via events,
first make sure that the `enableEvents` flag in `simpleserver.properties` is set to `true`.
Now the server needs additional code which is located in the `static.xml` file that comes with ShopTool.
In general, the content can simply by copied into the `<config>` section
of SimpleServers `config.xml`.
If you already have a `onServerStart` event, just add the line `launch storeBotsRestartService`
to initialize the shops and delete the copied `onServerStart` event.

##Usage
<!--- TODO
*If you are a (Windows) user who doesn´t know how to deal with the
command line or how to edit text files with extensions other than
".txt" you may want to consult this tutorial before using ShopTool:*
-->

###General
ShopTool is a command-line program that works by passing commands with options and arguments.
You can run the program by executing

    $ java -jar ShopTool.jar <ShopTool command> <command options, arguments>

inside a console at the directory where your `ShopTool.jar` file is. If you use Linux you can run

    $ ./shoptool.sh <ShopTool command> <command options, arguments>

instead. If you are a (Windows) user who doesn´t know how to deal with the
command line or how to edit text files with extensions other than
".txt" you should inform yourself about these things before using ShopTool.



###Basic steps
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

**Syntax of the pricelist command**

    pl [factor] [interest buy] [interest sell] [[-n][-N]] normalStock [[-max][-MAX]] maxStock [[-t][-T]] stockUpdateTime [dest] [file 1] [file 2] ...

* `factor` factor all prices with this constant
* `interest buy` add an interest to all buy prices
* `interest sell` subtract an interest from all sell prices
* `dest` path to the file the new pricelist should be saved to
* `file n` a correct pricelist file

The switches `-n`, `-max` and `-t` set the corresponding values for each item where they are not already set.
Using a switch in capital letters forces the value for all items (existing values are overwritten).
Note that all switches are optional but must be given in the shown order.

**Example of using the pricelist command**

We start with two pricelist files:

Pricelist 1: `general.pricelist`

    #General
    ID=3 NAME=dirt PRICE=10
    ID=4 NAME=cobblestone PRICE=20
    ID=5 NAME=wood PRICE=25
    ID=12 NAME=sand PRICE=40
    ID=13 NAME=gravel PRICE=30
    ID=24 NAME=sandstone PRICE=70

Pricelist 2: `metal.pricelist`

    #Metal ingot / ore
    ID=263 NAME=coal PRICE=250
    ID=331 NAME=redstone PRICE=1000
    ID=265 NAME=iron PRICE=10000
    ID=348 NAME=glowstone PRICE=13000
    ID=22 NAME=lapiz PRICE=25000
    ID=266 NAME=gold PRICE=100000
    ID=264 NAME=diamond PRICE=2000000

As you see, these lists only include *id*, *name* and a single *price* per item.
This is perfectly ok, making it easy to build special pricelists from these general forms.

Let´s say we want all the items of both lists, subtract an interest of 0.1 from the sell price and have all prices be 25% higher.
The normal stock should be 20, the maximum 64 and the time interval of stock changing 2 minutes.
Our new pricelist should be saved to the file `mix.pricelist`.
No problem:

    pl 1.25 0 0.1 -n 20 -max 64 -t 120 mix.pricelist general.pricelist metal.pricelist

The result: `mix.pricelist`

    ID=263 NAME=coal PRICE_BUY=313 PRICE_SELL=281 NORMAL=20 MAX=64 TIME=120
    ID=22 NAME=lapiz PRICE_BUY=31250 PRICE_SELL=28125 NORMAL=20 MAX=64 TIME=120
    ID=24 NAME=sandstone PRICE_BUY=88 PRICE_SELL=78 NORMAL=20 MAX=64 TIME=120
    ID=331 NAME=redstone PRICE_BUY=1250 PRICE_SELL=1125 NORMAL=20 MAX=64 TIME=120
    ID=13 NAME=gravel PRICE_BUY=38 PRICE_SELL=33 NORMAL=20 MAX=64 TIME=120
    ID=12 NAME=sand PRICE_BUY=50 PRICE_SELL=45 NORMAL=20 MAX=64 TIME=120
    ID=348 NAME=glowstone PRICE_BUY=16250 PRICE_SELL=14625 NORMAL=20 MAX=64 TIME=120
    ID=3 NAME=dirt PRICE_BUY=13 PRICE_SELL=11 NORMAL=20 MAX=64 TIME=120
    ID=5 NAME=wood PRICE_BUY=32 PRICE_SELL=28 NORMAL=20 MAX=64 TIME=120
    ID=4 NAME=cobblestone PRICE_BUY=25 PRICE_SELL=22 NORMAL=20 MAX=64 TIME=120
    ID=266 NAME=gold PRICE_BUY=125000 PRICE_SELL=112500 NORMAL=20 MAX=64 TIME=120
    ID=265 NAME=iron PRICE_BUY=12500 PRICE_SELL=11250 NORMAL=20 MAX=64 TIME=120
    ID=264 NAME=diamond PRICE_BUY=2500000 PRICE_SELL=2250000 NORMAL=20 MAX=64 TIME=120

ShopTool calculated the factorization of all prices, inserted buy and sell prices seperately and added the *normal*, *max* and *time* values.

Now we think our prices are too high and it takes too long to refill - let´s change our pricelist: 10% lower prices and only 30 seconds stock update time.
We don´t have to start all over again, we can simply run the *pricelist command* ontop of the current list:

    pl 0.9 0 0 -T 30 mix2.pricelist mix.pricelist

Note that we had to use the `-T` option instead of `-t` because we wanted to overwrite the old values.

Result: `mix2.pricelist`

    ID=263 NAME=coal PRICE_BUY=282 PRICE_SELL=252 NORMAL=20 MAX=64 TIME=30
    ID=22 NAME=lapiz PRICE_BUY=28125 PRICE_SELL=25312 NORMAL=20 MAX=64 TIME=30
    ID=24 NAME=sandstone PRICE_BUY=80 PRICE_SELL=70 NORMAL=20 MAX=64 TIME=30
    ID=331 NAME=redstone PRICE_BUY=1125 PRICE_SELL=1012 NORMAL=20 MAX=64 TIME=30
    ID=13 NAME=gravel PRICE_BUY=35 PRICE_SELL=29 NORMAL=20 MAX=64 TIME=30
    ID=12 NAME=sand PRICE_BUY=45 PRICE_SELL=40 NORMAL=20 MAX=64 TIME=30
    ID=348 NAME=glowstone PRICE_BUY=14625 PRICE_SELL=13162 NORMAL=20 MAX=64 TIME=30
    ID=3 NAME=dirt PRICE_BUY=12 PRICE_SELL=9 NORMAL=20 MAX=64 TIME=30
    ID=5 NAME=wood PRICE_BUY=29 PRICE_SELL=25 NORMAL=20 MAX=64 TIME=30
    ID=4 NAME=cobblestone PRICE_BUY=23 PRICE_SELL=19 NORMAL=20 MAX=64 TIME=30
    ID=266 NAME=gold PRICE_BUY=112500 PRICE_SELL=101250 NORMAL=20 MAX=64 TIME=30
    ID=265 NAME=iron PRICE_BUY=11250 PRICE_SELL=10125 NORMAL=20 MAX=64 TIME=30
    ID=264 NAME=diamond PRICE_BUY=2250000 PRICE_SELL=2025000 NORMAL=20 MAX=64 TIME=30

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
To use a shop with SimpleServer the script code for SimpleServer *Events* must be generated.
This is simply done with the event command.

**Syntax of the event command:**

    ev [-s] [dest] [file 1] [file 2] ...

* `dest` path to the file the generated code should be saved to
* `file n` a correct shop file

The switch `-s` is optional. If given, ShopTool will output the static code which is shared
by all shops and does not need to change when you generate subsequent shops.
You will need use that flag just the first time you use ShopTool to get that static event code.
It contains all the generic shop logic and two new player commands - ``/balance`` and ``/buy``.

###Use generated event code with SimpleServer
The generated code must be copied into the `<config>` section of your SimpleServer `config.xml`.
The generated areas have to be manually merged with your own areas.
To do so, move the content of the generated `<dimension>` section into your own.

If everything has been set up correctly, you will see the NPCs spawning at the specified locations
after starting the server and you can test your shops.
Just follow the in-game instructions when entering a shop area
or see the [SimpleServer wiki](https://github.com/SimpleServer/SimpleServer/wiki/Npcshops).

##Quickstart guide
This will give a quick overview of how creating shops works.
We won´t cover the useful `pricelist` command here, see above for that!

***First read the "Setup" and "Usage" sections at the beginning of this file,
it is necessary that you did the setup and know how to use the ShopTool program
for this tutorial!***

Let´s create a shop! We start by searching or building a good place in our Minecraft world (in-game). Then we write down the following coordinates:
* *start* and *end* coordinates of a new 3D area that will be created for the shop like with the SimpleServer `myarea` command (imagine the two coords spanning a box)
* the coordinates where the vendor bot should be placed

For this tutorial we assume all files created and needed to be in the same directory as the `ShopTool.jar` file.

###The pricelist
Now we need to say what we want to sell and buy in our shop and how much it should cost. That´s where pricelists come into play.
We have two options: create our pricelist manually or generate it from existing ones.
We will do it manually here, but note that the *pricelist command* is quite useful to make creating pricelists convenient.
See the *pricelists* section above for an example.

First we create a new file called `list1.pricelist`.

If the shop should sell iron ingots for 500, buy them for 400 and buy birch wood for 200 but should not sell it we put these lines into the file:

    ID=265 NAME=iron-ingot PRICE_BUY=500 PRICE_SELL=400 NORMAL=12 MAX=20 TIME=400
    ID=17:2 NAME=birch-wood PRICE_BUY= PRICE_SELL=200 NORMAL=0 MAX=64 TIME=600

We also stated that the shop should normally have 12 iron ingots available, can store a maximum of 20 and updates its stock towards 12 every 400 seconds. The same applies for the second entry respectively.

###The shop file
It´s time to create our shop file which contains the general information about our shop by
writing the following lines into a new file `myshop.shop`.

    NAME=MyShop
    STARTCOORD=-30,70,20
    ENDCOORD=-20,70,25
    BOTCOORD=-25,70,24
    VENDORNAME=Harry
    PRICELIST=list1

We replace the coordinates with the ones we wrote down before.

###Final steps
Now lets create the code for our shop by executing

    ev code.xml myshop.shop

Finally, we open the `code.xml` file that was created by ShopTool and copy its content.
Then we open the `config.xml` file of our SimpleServer server and paste the code inside the `<config>` section.
If you were already using areas you will have to merge them (see above for help).

Now the shop can be tested by starting the server and entering the shop area.
Just follow the in-game instructions!

If you have any problems you could read the more detailed descriptions above and refer to the *troubleshooting* section below.

##Troubleshooting

###Checklist
* Is `enableEvents=true` in `simpleserver.properties`?
* Did you try to read the output of ShopTool to get information about what´s wrong?
* Did you merge the generated code correctly into `config.xml`? Check the SimpleServer console for errors!

###Bugs
If you find any bugs in ShopTool, please report them using the Github issue system.
