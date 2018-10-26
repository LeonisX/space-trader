# Space Trader

Deeply refactored and upgraded version of [Space Trader for Java](https://sourceforge.net/projects/spacetraderjava/files/Space%20Trader%20Java/Version%201.12/) from Aviv Eyal (09.10.2008-03.12.2010)

The project is a Java port of the [Space Trader for Windows](https://sourceforge.net/projects/spacetraderwin) from [Jay French](http://web.archive.org/web/20040212092717/http://www.frenchfryz.com:80/jay/spacetrader/home.php) (08.12.2003-14.08.2008), which is a C# port of the [original](http://ticc.uvt.nl/~pspronck/spacetrader/STFrames.html) Palm version from Peter Spronk (2000-2002). [New site](https://www.spronck.net/spacetrader/).

Useful Source Repositories:

* https://github.com/videogamepreservation/spacetrader
* https://github.com/SpaceTraderGame/SpaceTrader-Windows
* https://bitbucket.org/brucelet/space-trader/src

[Other implementations on GitHub](https://github.com/search?q=space+trader)

## Whats new:

* Multilingual support
* Gradle
* Full refactor, code clean-up
* --Bug fixes
* Custom strings reader

## TODO

* i18n for Swing
* Language selector
* Different image sizes, font sizes (config)
* Special object for save states
* SaveFileDialog - full localization
* Full refactor
* Optional refactor GUI with WindowBuilder Pro (autosize, grouping)
* ReflectionUtils.loadControlsDimensions(this.asSwingObject(), this.getName(), GlobalAssets.getDimensions());
* ReflectionUtils.loadControlsStrings(this.asSwingObject(), this.getName(), GlobalAssets.getStrings());
* setSize, setClientSize, setLocation
* Fix 2 latest bugs from https://sourceforge.net/p/spacetraderwin/bugs/

### Minor tasks

* Find all languages in resources
* Need to update font sizes. Dump all too
* Show in Cargo planet names (target)

## Known bugs:

* Load game from scratch
* High Score
* LinkLabel - not implemented sort
* FormMonster - not correct columns order; bug in mercenariesPanel (systems); Links don't work
* Many dialogs don't allow to close them from [x]
* FormShipyard: Trying to set unknown background: color at 0x0: 0 0 0 0 (Color.BLACK)

## Regressions:

* Bold font for labels on main screen, formEncounter. Source: new `tahomabd.ttf`

## Far perspectives

* JavaFX UI
* Full-windowed
