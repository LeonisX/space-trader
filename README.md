# Space Trader for Java 8

[abbreviated version of this documentation in Russian](README_RU.md)

Fully reworked version of [Space Trader for Java](https://sourceforge.net/projects/spacetraderjava/files/Space%20Trader%20Java/Version%201.12/) from Aviv Eyal.

The project is a Java port of the [Space Trader for Windows](https://sourceforge.net/projects/spacetraderwin) from [Jay French](http://web.archive.org/web/20040212092717/http://www.frenchfryz.com:80/jay/spacetrader/home.php), which is a C# port of the [original](http://ticc.uvt.nl/~pspronck/spacetrader/STFrames.html) Palm version from Peter Spronk. [New site](https://www.spronck.net/spacetrader/).

Useful Source Repositories:

* [Space Trader from Peter Spronk on GitHub](https://github.com/videogamepreservation/spacetrader)
* [Space Trader from Jay French on GitHub](https://github.com/SpaceTraderGame/SpaceTrader-Windows)
* [Android version of Space Trader from Russel Wolf aka brucelet](https://bitbucket.org/brucelet/space-trader/src)
* [Dark Nova - iOS, Android sources](https://github.com/deadjim/dark-nova-android)

[Other implementations on GitHub](https://github.com/search?q=space+trader)

[Changelog](changelog.md)

## Overview
   
Space Trader is a complex game, in which the player's aim is to amass enough money to be able to buy a moon to retire to. The player starts out with a small space ship, armed with one simple laser, and 1000 credits in cash. The safest and easiest way to earn money is to trade goods between neighbouring solar systems. If the player chooses the goods to trade wisely, it isn't too difficult to sell them with a profit. There are other ways to get rich, though. You might become a bounty hunter and hunt down pirates. It is also possible to become a pirate yourself and rob honest traders of their cargo. Beware, though: pirating is a way to get rich quickly, but the police force will go after you.

## Features
   
Some of the features of Space Trader are:
   
* Ten different trade goods are available, two of which are considered to be illegal but can bring great profits.
* Ten different ship types are available, some of which are especially suitable for trading, some for pirating, and others for both. Ships differ in size, the distance they can travel, their hull strength, the number of weapons, shields and gadgets they can carry, the number of cargo bays and the number of crew quarters that are available.
* Ships can be equipped with different selections of equipment, among which are several types of lasers, several types of shields, an escape pod and certain special gadgets like a cloaking device.
* You can distribute skill points over your character at the start of the game, to allow you to function well in your chosen role. For the skills your character lacks, mercenaries can be hired which may have different skills.
* There are more than a hundred solar systems in the galaxy, with different sizes, tech levels, governments, resources and special situations. These are not just for background color, but play a vital role in the game.
* While travelling to another solar system, you might encounter police ships, pirates and other traders. There are several ways to handle such encounters. You can even force a trader to allow you to plunder his ship.
* There are about a dozen unique missions and offers available, some of which may bring great special rewards.
* The ships are displayed graphically during an encounter. There are also large pictures during key moments of the game.


## Introduction

You grew up as a member of a small colony on a planet in a solar system that is part of the Great Galactic Federation (GGF). You worked on your family's farm, dreaming about a life as an intergalactic trader. You imagined yourself buying goods on one system, selling them on another making huge profits, battling pirates, finding opportunities and perhaps, one day, buy your own moon to which you could retire to live a wealthy and peaceful life for the rest of your days.
After your parents died, as their only child you inherited the farm. Since it would be too difficult to run it all on your own, you saw your chance clear and sold it to a neighbour. With the earnings, you bought a second-hand space ship of the Gnat type, equipped it with one pulse laser, and went to the local GGF space port to buy trade goods with your last 1000 credits. This is where your life as a space trader begins.

## Your Job as a Space Trader

Your ultimate goal as a space trader is to amass enough money so you can buy your own moon, then claim that moon to retire to it. You need a lot of money for that, and will encounter many dangers, so along the way you might want to buy a better ship and better equipment.
At first, the main point is to stay alive, and earn some money by trading. The GGF has a space port in every solar system, where goods locally produced are sold, and goods the locals need are bought. A good trader will judge, based on the systems tech level, government type, resources and current situation, which goods are cheap and which are expensive in a system, and will adapt his trading strategy accordingly.
Later on, when you are better equipped, you might try to become a bounty hunter alongside your trading job. If you feel so inclined, you might also become a pirate and rob other traders of their goods. Being a pirate can be very profitable, but remember that the police will try to hunt you down, and that, as a pirate, you cannot sell goods out in the open (even those that you bought legally). You need an intermediary to sell your goods, and this sleazy person will take 10% of everything your cargo sells for. This makes the return to an honest life all the more difficult. 

## Spaceships

Every type of ship has certain characteristics, making some ships better for trading and others better for pirating or bounty hunting. Your first ship, of the Gnat type, is mainly used for trading since it is rather weak and has few defensive capabilities. You might be able to win a few battles with it, though, especially if you are a good fighter. Later on, you can buy a ship better suited to your purpose.

Ships differ in their hull strengths, number of cargo bays, number of weapon slots, number of shield slots, number of gadget slots, number of crew quarters and maximum travelling range. At the Ship Yard, they will be able to inform you about the capabilities of each ship. 

When you decide to buy a new ship, you trade in your old one, including its equipment. The worth of your ship, including its cargo and equipment, is subtracted from the price of the new ship. You should be warned that if you have cargo on board which the people in the current system don't want, you will lose that cargo without any compensation. You therefore better sell a ship with empty cargo bays. If you have an escape pod installed on your old ship, that will be transferred to your new ship. Any insurance you have will also be transferred, including no-claim. 

Equipment you can buy for your ship is divided in three groups. The first group consists of weapons, and you can select one of three types of lasers for each weapon slot. The more expensive a laser, the more power it has. The second group consists of shields, and you can choose one of two shields for each shield slot. The more expensive the shield is, the better the protection it gives. The last group consists of gadgets, which may enhance certain functions of a ship. 

The most important part of a spaceship is its hull. If the hull strength is reduced to zero percent during a fight, the ship will explode, killing everyone on it and destroying everything it was carrying. You might fly your ship with a low hull strength, but it is best not to do that. Repairs can be made at the Ship Yard and are fairly cheap, especially for the smaller ship types. The only way to survive the destruction of your ship is to have an escape pod installed, which will eject you automatically when your ship can't be saved anymore. 

## Travelling through Space

Space travel consists of a four-step procedure: 
* *Leaving the space port*. After you have refuelled your ship, sold your cargo, bought new goods, and perhaps made some repairs, you simply ask the control tower for permission to depart, and fly your ship into space. 
* *Warping to another system*. As soon as you have left the space port area, you activate your warp engine. The warp engine creates a hyperspace bubble around your ship, and you get transported to your target system instantly. This is what actually consumes the fuel. You materialise at a safe distance from the target system's space port, usually about 20 clicks. 
* *Approaching the target space port*. The last few clicks en route to the target space port are the most dangerous of your journey. You may and very probably will encounter several other ships, among which there may be fellow traders, police ships, and pirates. Fellow traders won't attack unless provoked. The police might ask you to submit to an inspection, of which an honest trader, who doesn't deal in illegal goods (firearms and narcotics), has nothing to fear. Pirates will often attack you on sight. 
* *Docking at the target space port*. When you have reached the target space port, you are safe from interventions by other ships, and you can dock at your leisure after you have asked permission of the control tower.
A few solar systems are lucky to have a wormhole nearby. A wormhole is a rift in the space-time continuum, which leads directly from one place in the galaxy to another. You can use the wormhole to travel to another system in an instant, even if that other system is at the other end of the galaxy. You can only enter a wormhole from the space port of the system that owns it. That system will tax you for the use of the wormhole. The tax depends on your ship type: poor traders with a small ship pay considerably less than rich traders with a huge ship. On the plus side: you won't spend any fuel travelling through a wormhole, which compensates partly for the tax. 

## How to start

The game is written in Java 8. While it is still in active development, and I don't have time to make a friendly installer for it.

First you need to install JRE (Java Runtime Environment) or JDK. It sounds scary, but, in fact, this is just another program on your computer, a virtual machine in which the game will run.

### Windows

If you do not plan to write programs in Java, it will be sufficient to install the JRE. The game was tested for Java with version 8, so if there is no acute need in the 11th version, then set yourself the 8th.

[Download Java from Oracle site](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

Next you need to download these two files:

* `space-trader.bat`
* `space-trader-%currentVersion%.jar`

and run the first one.

### Linux

If you have Linux installed, it means that you are a more or less experienced user and are able to install Java yourself.

To start the game, you need to run the command:

`java -jar space-trader-%currentVersion%.jar`

If you have `wine` installed, then you can safely run `space-trader.bat`.

## Other

* Those who wish to improve the Russian translation are invited. [Download script](english-russian.xls)
* Everyone who wants to translate the game into their native language is also welcome. Write to me, I'll tell you what you need to do.
* Any suggestions and recommendations are welcome.
* Have a nice game

## TODO

* Different image sizes, font sizes (config)
* Installers: http://www.jrsoftware.org/isdl.php
* Installers: https://github.com/nebula-plugins/gradle-ospackage-plugin
* Full Russian description
* Dig about Star Trader for C16

### Minor tasks

* Need to update font sizes. Dump all too.

## Feature requests

* Show in Cargo planet names (target)
* Rewrite CargoBuyStatement, CargoSellStatement. Can't translate to Russian

## Known bugs:

* Many dialogs don't allow to close them from [x]
* FormShipyard: Trying to set unknown background: color at 0x0: 0 0 0 0 (Color.BLACK)
* FormJettison - incorrect flow when try to dump all - first message about littering, second must be: EncounterDumpAll, AlertsEncounterDumpAllMessage,
* When fire mercenary - he disappears forever
* 2 latest bugs from https://sourceforge.net/p/spacetraderwin/bugs/

## Regressions:

* Bold font for labels on main screen, formEncounter. Source: new `tahomabd.ttf`

## TODO list of Jay French

* make shipyard news advertisements
* rework escape pod sequence
* create images for trade goods
* passenger modules
* courier quests
* more very rare encounters

## Far perspectives

* Quests engine
* JavaFX UI
* Full-windowed
* Site
* FAQ, description
* New interesting quests
