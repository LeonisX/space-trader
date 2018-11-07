# Space Trader for Java 8

[abbreviated version of this documentation in Russian](README_RU.md)

Fully reworked version of [Space Trader for Java](https://sourceforge.net/projects/spacetraderjava/files/Space%20Trader%20Java/Version%201.12/) from Aviv Eyal

The project is a Java port of the [Space Trader for Windows](https://sourceforge.net/projects/spacetraderwin) from [Jay French](http://web.archive.org/web/20040212092717/http://www.frenchfryz.com:80/jay/spacetrader/home.php), which is a C# port of the [original](http://ticc.uvt.nl/~pspronck/spacetrader/STFrames.html) Palm version from Peter Spronk. [New site](https://www.spronck.net/spacetrader/).

Useful Source Repositories:

* [Space Trader from Peter Spronk on GitHub](https://github.com/videogamepreservation/spacetrader)
* [Space Trader from Jay French on GitHub](https://github.com/SpaceTraderGame/SpaceTrader-Windows)
* [Android version of Space Trader from Russel Wolf aka brucelet](https://bitbucket.org/brucelet/space-trader/src)

[Other implementations on GitHub](https://github.com/search?q=space+trader)

[Changelog](changelog.md)

## Introduction

You grew up as a member of a small colony on a planet in a solar system that is part of the Great Galactic Federation (GGF). You worked on your family's farm, dreaming about a life as an intergalactic trader. You imagined yourself buying goods on one system, selling them on another making huge profits, battling pirates, finding opportunities and perhaps, one day, buy your own moon to which you could retire to live a wealthy and peaceful life for the rest of your days.
After your parents died, as their only child you inherited the farm. Since it would be too difficult to run it all on your own, you saw your chance clear and sold it to a neighbour. With the earnings, you bought a second-hand space ship of the Gnat type, equipped it with one pulse laser, and went to the local GGF space port to buy trade goods with your last 1000 credits. This is where your life as a space trader begins.

## Your Job as a Space Trader

Your ultimate goal as a space trader is to amass enough money so you can buy your own moon, then claim that moon to retire to it. You need a lot of money for that, and will encounter many dangers, so along the way you might want to buy a better ship and better equipment.
At first, the main point is to stay alive, and earn some money by trading. The GGF has a space port in every solar system, where goods locally produced are sold, and goods the locals need are bought. A good trader will judge, based on the systems tech level, government type, resources and current situation, which goods are cheap and which are expensive in a system, and will adapt his trading strategy accordingly.
Later on, when you are better equipped, you might try to become a bounty hunter alongside your trading job. If you feel so inclined, you might also become a pirate and rob other traders of their goods. Being a pirate can be very profitable, but remember that the police will try to hunt you down, and that, as a pirate, you cannot sell goods out in the open (even those that you bought legally). You need an intermediary to sell your goods, and this sleazy person will take 10% of everything your cargo sells for. This makes the return to an honest life all the more difficult. 

## How to start

The game is written in Java 8. While it is still in active development, and I don't have time to make a friendly installer for it.

First you need to install JRE (Java Runtime Environment) or JDK. It sounds scary, but, in fact, this is just another program on your computer, a virtual machine in which the game will run.

### Windows

If you do not plan to write programs in Java, it will be sufficient to install the JRE. The game was tested for Java with version 8, so if there is no acute need in the 11th version, then set yourself the 8th.

[Download Java from Oracle site](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

Next you need to download these two files:

* `space-trader.bat`
* `space-trader-%текущаяВерсия%.jar`

and run the first one.

### Linux

If you have Linux installed, it means that you are a more or less experienced user and are able to install Java yourself.

To start the game, you need to run the command:

`java -jar space-trader-%currentVersion%.jar`

If you have `wine` installed, then you can safely run `space-trader.bat`.

## Other

* Those who wish to improve the Russian translation are invited. [Download script](english-russian.xls)
* Any suggestions and recommendations are welcome.
* Have a nice game

## TODO

* TargetSystemPanel: расстояние + парсек
* Declension engine
* Different image sizes, font sizes (config)
* Special object for save states
* SaveFileDialog - full localization
* Optional refactor GUI with WindowBuilder Pro (autosize, grouping)
* FormCargoBuy/Sell: separate phrases for each case.
* FormEncounter: need to carefully refactor all phrases
* Full Russian description
* Installers: http://www.jrsoftware.org/isdl.php
* Installers: https://github.com/nebula-plugins/gradle-ospackage-plugin

### Minor tasks

* Need to update font sizes. Dump all too

### TODO list of Jay French

* make shipyard news advertisements
* rework escape pod sequence
* create images for trade goods
* passenger modules
* courier quests
* more very rare encounters

## Feature requests

* Show in Cargo planet names (target)
* Save last selected language as default

## Known bugs:

* Load game from scratch
* LinkLabel - not implemented sort
* FormMonster - not correct columns order; bug in mercenariesPanel (systems); Links don't work
* Many dialogs don't allow to close them from [x]
* FormShipyard: Trying to set unknown background: color at 0x0: 0 0 0 0 (Color.BLACK)
* FormJettison - incorrect flow when try to dump all - first message about littering, second must be: EncounterDumpAll, AlertsEncounterDumpAllMessage,
* When fire mercenary - he disappears forever
* 2 latest bugs from https://sourceforge.net/p/spacetraderwin/bugs/

## Regressions:

* Bold font for labels on main screen, formEncounter. Source: new `tahomabd.ttf`
* Probably absent special event (lottery) at starting planet

## Far perspectives

* JavaFX UI
* Full-windowed
* Site
* FAQ, description
* Quests engine
* New interesting quests
