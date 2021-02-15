# Space Trader for Java 8

Fully reworked version of [Space Trader for Java](https://sourceforge.net/projects/spacetraderjava/files/Space%20Trader%20Java/Version%201.12/) from Aviv Eyal.

The project is a Java port of the [Space Trader for Windows](https://sourceforge.net/projects/spacetraderwin) from [Jay French](http://web.archive.org/web/20040212092717/http://www.frenchfryz.com:80/jay/spacetrader/home.php), which is a C# port of the [original](http://ticc.uvt.nl/~pspronck/spacetrader/STFrames.html) Palm version from [Peter Spronk](https://www.spronck.net/spacetrader/).

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

## Useful links

Discussion of the game and its development in [Gitter](https://gitter.im/LeonisX/space-trader).

Game manuals: [English](docs/manual_en.md) | [Russian](docs/manual_ru.md)

[Changelog](docs/changelog.md)

Source Repositories:

* [Space Trader from Peter Spronk on GitHub](https://github.com/videogamepreservation/spacetrader)
* [Space Trader from Jay French on GitHub](https://github.com/SpaceTraderGame/SpaceTrader-Windows)
* [Android version of Space Trader from Russel Wolf aka brucelet](https://bitbucket.org/brucelet/space-trader/src), [Official site](http://spacetrader.brucelet.com/)
* [Android version of Space Trader from Benjamin Schieder aka blind-coder](https://github.com/blind-coder/SpaceTrader), [Official site](https://www.benjamin-schieder.de/)
* [Dark Nova - iOS, Android sources](https://github.com/deadjim/dark-nova-android)

[Other implementations on GitHub](https://github.com/search?q=space+trader)

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

* Those who wish to improve the Russian translation are invited. [Download script](docs/english-russian.xls)
* Everyone who wants to translate the game into their native language is also welcome. Write to me, I'll tell you what you need to do.
* Also, I will be glad to anyone who offers a fascinating quest, any interesting name for the planet, or the name of a mercenary.
* Any suggestions and recommendations are welcome.
* Have a nice game

## TODO

* Checked: when finish game - see > 100% percents. It's for cheats only.

* TODO: complete documentation from: https://www.spronck.net/spacetrader/SpaceTrader.html
* TODO: document the effect of difficulty on gameplay
* TODO: document all quest events
* TODO: investigate: https://gamefaqs.gamespot.com/palmos/917550-space-trader/faqs/23321

### News

* Clean from old events
* Try to simplify. May be keep only map: ID -> TTL?
* Find start of the flight and clear news at the start of flight
* News: Time to live?
* Check all news - when can - implement in phases

### Quests

* More flexible news. Many quests, for example Experiment - news appear prior action
* Increase difficulty, depends on game difficulty (days to delivery)
* DragonflyQuest: when disabled - don't show news about explosion. Need to check on windows version - if can disable

### Monster.com

* MonsterCom: Show only current phase of quest. CheckBox for full view (only for cheat/debug)
* Quest panel with scrolling
* Panel - Quest Persons: all persons from quests; Show planet. If onboard - onboard
* All quests - to land passengers on the planets? Zeethibal

### Translate

* Rewrite CargoSellStatement. Can't translate to Russian
* FormTests, FormAlertTests - refactor, translate
* Update english-russian.xls

## Feature requests

* QuestSystem: hidden phases (hidden from Monster.com, isHidden, default = false)
* EasterEgg quest: Og: The story of an experienced trader about how he started. Tip (10 containers). Hidden phase
* EasterEgg quest: Manually attach to Og
* Show in Cargo planet names (target)
* On sell, plunder, jettison - show goods with bold
* Sell - add goods to planet
* Buy/Sell equipment - CheckBox, which offers all equipment in debug/cheat modes

## Known bugs:

* After disabling can repeat attacks w/o killing
* Don't generate traders at all???
* Many dialogs don't allow to close them from [x]
* FormShipyard: Trying to set unknown background: color at 0x0: 0 0 0 0 (Color.BLACK)
* FormJettison - incorrect flow when try to dump all - first message about littering, second must be: EncounterDumpAll, AlertsEncounterDumpAllMessage,
* 2 latest bugs from https://sourceforge.net/p/spacetraderwin/bugs/
* Commander status, Find system - truncated window sometimes
* What to do with ending title?? It's too long :(
* What to do with cheats status title?? It's too long :(
* After disabling can fully destroy without encounter ending
* Strange - when start new game - see few visited systems
* Very strange - after arrival see, that I visited other systems. Or arrive in other system
* Bug? Don't hide wormhole route after arrive
* Autosize doesn't works in FormViewQuests (ArtifactQuest))

### Other

* Different image sizes, font sizes (config)
* Installers: http://www.jrsoftware.org/isdl.php
* Installers: https://github.com/nebula-plugins/gradle-ospackage-plugin
* Full Russian description, FAQ
* Dig about Star Trader for C16

### Minor tasks

* Need to update font sizes. Dump all too.

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

* ~~Quests engine~~
* Standard game, Extended game
* JavaFX UI
* Full-windowed
* Site
* ~~FAQ, description~~
* New interesting quests

## For me

git log --pretty=format:%s
