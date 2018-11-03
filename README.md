# Space Trader for Java 8

Fully reworked version of [Space Trader for Java](https://sourceforge.net/projects/spacetraderjava/files/Space%20Trader%20Java/Version%201.12/) from Aviv Eyal (09.10.2008-03.12.2010)

The project is a Java port of the [Space Trader for Windows](https://sourceforge.net/projects/spacetraderwin) from [Jay French](http://web.archive.org/web/20040212092717/http://www.frenchfryz.com:80/jay/spacetrader/home.php) (08.12.2003-14.08.2008), which is a C# port of the [original](http://ticc.uvt.nl/~pspronck/spacetrader/STFrames.html) Palm version from Peter Spronk (2000-2002). [New site](https://www.spronck.net/spacetrader/).

Useful Source Repositories:

* https://github.com/videogamepreservation/spacetrader
* https://github.com/SpaceTraderGame/SpaceTrader-Windows
* https://bitbucket.org/brucelet/space-trader/src

[Other implementations on GitHub](https://github.com/search?q=space+trader)

## Whats new:

* Multilingual support for UI dialogs and in-game strings
* Gradle as main build tool
* Full refactor, code clean-up
* Many bug fixes
* Custom strings reader
* Autosize labels, buttons, panels
* Measure multiline labels

## TODO

* TargetSystemPanel: расстояние + парсек
* Save last selected language as default
* Declension engine
* Different image sizes, font sizes (config)
* Special object for save states
* SaveFileDialog - full localization
* Optional refactor GUI with WindowBuilder Pro (autosize, grouping)
* Fix 2 latest bugs from https://sourceforge.net/p/spacetraderwin/bugs/
* FormAbout - show version (from Gradle) in titleLabel
* FormCargoBuy/Sell: separate phrases for each case.
* FormEncounter: need to carefully refactor all phrases 

### Minor tasks

* Need to update font sizes. Dump all too

### Feature requests

* Show in Cargo planet names (target)

## Known bugs:

* Load game from scratch
* LinkLabel - not implemented sort
* FormMonster - not correct columns order; bug in mercenariesPanel (systems); Links don't work
* Many dialogs don't allow to close them from [x]
* FormShipyard: Trying to set unknown background: color at 0x0: 0 0 0 0 (Color.BLACK)
* FormJettison - incorrect flow when try to dump all - first message about littering, second must be: EncounterDumpAll, AlertsEncounterDumpAllMessage, 

## Regressions:

* Bold font for labels on main screen, formEncounter. Source: new `tahomabd.ttf`
* Probably absent special event (lottery) at starting planet

## Fixed bugs and regressions:

* High Score

## Far perspectives

* JavaFX UI
* Full-windowed
