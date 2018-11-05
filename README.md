# Space Trader for Java 8

Fully reworked version of [Space Trader for Java](https://sourceforge.net/projects/spacetraderjava/files/Space%20Trader%20Java/Version%201.12/) from Aviv Eyal (09.10.2008-03.12.2010)

The project is a Java port of the [Space Trader for Windows](https://sourceforge.net/projects/spacetraderwin) from [Jay French](http://web.archive.org/web/20040212092717/http://www.frenchfryz.com:80/jay/spacetrader/home.php) (08.12.2003-14.08.2008), which is a C# port of the [original](http://ticc.uvt.nl/~pspronck/spacetrader/STFrames.html) Palm version from Peter Spronk (2000-2002, 2005). [New site](https://www.spronck.net/spacetrader/).

Useful Source Repositories:

* https://github.com/videogamepreservation/spacetrader
* https://github.com/SpaceTraderGame/SpaceTrader-Windows
* https://bitbucket.org/brucelet/space-trader/src

[Other implementations on GitHub](https://github.com/search?q=space+trader)

[Changelog](chandelog.md)

## How to start

[abbreviated version of this documentation in Russian](README_RU.md)

The game is written in Java 8. While it is still in active development, and I do not have time to make a friendly installer for it.

First you need to install JRE (Java Runtime Environment) or JDK. It sounds scary, but, in fact, this is just another program on your computer, a virtual machine in which the game will run.

### Windows

If you do not plan to write programs in Java, it will be sufficient to install the JRE. The game was tested for Java with version 8, so if there is no acute need in the 11th version, then set yourself the 8th.

https://www.oracle.com/technetwork/java/javase/downloads/index.html

### Linux

If you have Linux installed, it means that you are a more or less experienced user and are able to install Java yourself.

To start the game, you need to run the command:

`java -jar space-trader-2.00-RC1.jar`

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
* FormAbout - show version (from Gradle) in titleLabel
* FormCargoBuy/Sell: separate phrases for each case.
* FormEncounter: need to carefully refactor all phrases 
* Copy all comments to language files
* Full Russian description

### Minor tasks

* Need to update font sizes. Dump all too

### Feature requests

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
