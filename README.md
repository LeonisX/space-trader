# Space Trader

Deeply refactored and upgraded version of [Space Trader for Java](https://sourceforge.net/projects/spacetraderjava/files/Space%20Trader%20Java/Version%201.12/) from Aviv Eyal (09.10.2008-03.12.2010)

The project is a Java port of the [Space Trader for Windows](https://sourceforge.net/projects/spacetraderwin) from [Jay French](http://web.archive.org/web/20040212092717/http://www.frenchfryz.com:80/jay/spacetrader/home.php) (08.12.2003-14.08.2008), which is a C# port of the [original](http://ticc.uvt.nl/~pspronck/spacetrader/STFrames.html) Palm version from Peter Spronk (2000-2002). [New site](https://www.spronck.net/spacetrader/).


## Whats new:

* Multilingual support
* Gradle
* Full refactor, code clean-up
* --Bug fixes
* Custom strings reader

## TODO

* Move cheats-only enums to cheat package
* i18n for Swing
* Russian for mnuGameSave/mnuGameLoad
* Strings to file
* Language selector
* Different image sizes, font sizes (config)
* Special object for save states
* SaveFileDialog - full localization
* Full refactor
* Optional refactor GUI with WindowBuilder Pro (autosize, grouping)
* ReflectionUtils.loadControlsDimensions(this.asSwingObject(), this.getName(), SpaceTrader.getDimensions());
* ReflectionUtils.loadControlsStrings(this.asSwingObject(), this.getName(), SpaceTrader.getStrings());

### Minor tasks

* All main window strings and sizes to file
* Automatic update main window
* Find all languages in resources
* Need to update font sizes. Dump all too
* Show in Cargo planet names (target)

## Known bugs:

* Load game from scratch
* High Score
* When start game: 8->9->0->1!! & start
* LinkLabel - not implemented sort
* FormMonster - not correct columns order; bug in mercenariesPanel (systems); Links don't work
* Many dialogs don't allow to close them from [x]

## Regressions:

* Bold font for labels on main screen, formEncounter. Source: new `tahomabd.ttf`

## Far perspectives

* JavaFX UI
* Full-windowed

