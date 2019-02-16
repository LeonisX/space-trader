# Space Trader for Java 8
by Stavila Leonid aka Leonis

[The history of commits](https://github.com/LeonisX/space-trader/commits/master)

16.02.2019 v.3.51.225

All Very Rare Encounters in Quest System:

* Captain Ahab (with possibility to disable)
* Captain Conrad
* Captain Huie
* Dated Tonic
* Good Tonic
* Marie Celeste

Changelog:

* GigaGaia cheat
* Rareware cheat
* Get rid of several static methods in Game class
* Reorganize docs
* Refactor ENCOUNTER_SHOW_ACTION_BUTTONS

Bug fixes:

* Fix bug with mercenaries list
* Fix "free" ships in Shipyard
* Fix Events cheat
* Repair encounter
* Repair cheats

20.01.2019 v.3.43.178

New quests in Quest System:

* Space Monster
* Scarab Stolen
* Skill Increase
* Moon For Sale
* Japori Disease
* Alien Invasion
* Dangerous Experiment
* Erase Record
* Dragonfly
* Alien Artifact (with improvement)
* Cargo For Sale

Changelog:

* Remove unneeded SpecialEventType enum
* Allow to create few independent quests with same type
* Delete unneeded files

Bug fixes:

* Fix warp to Ray system
* Fix error when try to see high scores w/o started game

31.12.2018 v.3.32.138

The main innovation of the third version is the Quest Engine. 
Now the logic of each quest is collected in a separate class. 
This greatly simplifies the process of creating new quests, as well as testing.
At the moment, the following quests have been processed:

* Lottery Winner
* Ambassador Jarek
* Kidnapped Princess
* Jonathan Wild
* Stolen Sculpture
* Morgan's Reactor
* Troubles with Tribbles

Changelog:

* Debug controls in FormViewCommander
* FormMonster: show quest/shipyard features; order by feature
* Cheats subMenu (FormMonster)
* Shipyard form in Cheats menu
* Dialogs and Alerts tests in Cheats menu
* Docs (Russian and English): spaceships and travelling through Space, skills, mercenaries, encounters in Space, trade goods, equipment
* Logging (via Java Util Logging)
* Encounters in separate class (not in Game)

Bug Fixes:

* Fix all/max buttons on Cargo Panel
* Fix shield initial charge bug
* Fix special ships graphic bugs
* Fix stealable cargo
* Show all quests in MonsterCom
* Avoid negative values for ship slots
* After deal in Shipyard - alert in center of Shipyard form
* Fix bug in hasCrew method

13.11.2018. v.2.17.21

* Full translation for File Dialogs
* FormMonsters and FormViewQuests now fully workable.
* Docs: game overview and specifications

* Remove unused classes: AnchorStyles, ComboBoxStyle, FlatStyle, Link*.

12.11.2018. v.2.13.15

* Save/load game and options using serialization
* Set locale with language (need for correct number formatting)
* Move all IO functions to IOUtils

11.11.2018. v.2.11.13

* Keep main window position and language in registry
* Distance + "parsec" in TargetSystemPanel
* Update Russian script

Bug fixes:

* Fix game loading from scratch
* Correct determination of the number of lines in Label
* Fix version comparisons

Refactor:

* Simplify classes: MyComboBoxModel, MyListModel, ResourceManager, OpenFileDialog, SaveFileDialog
* Remove unneeded methods: beginInit(), endInit(), suspendLayout() 
* Remove unneeded methods: setSize(Dimension size) 
* Remove unneeded methods: setLocation(Point location)
* Remove unneeded classes: ContentAlignment, Size class, SolidBrush
* Remove unneeded interface IContainer 
* Remove unneeded value WinformJPanel.autoScaleBaseSize




10.11.2018. v.2.8.9

* Case and Plural Support
* Support single line comments in properties files
* Migrate all comments to the names and titles in the language files
* First unit tests for few functions

Bug fixes:

* Corrections in Russian translation
* Small corrections in the English text
* Refinement Bar Status
* Fixed splash screen size

06.11.2018. v2.3.1-RC1

* Upgrade Gradle to 4.10.2 version
* Show game version from Gradle in FormAbout
* Automatic prepare BAT file and copy JAR file to root
* Solve compilation warnings

05.11.2018. v2.00-RC1

New features:

* Multilingual support for UI dialogs and in-game strings
* Russian translation
* Gradle as main build tool
* Full refactor, code clean-up
* Custom strings reader
* Autosize labels, buttons, panels
* Measure multiline labels

Bug fixes:

* High Score is working now
* Many other minor bug fixes

Started at 13.10.2018


# Space Trader for Java
by Aviv Eyal (09.10.2008-03.12.2010)

13.03.2010 1.12

A few tiny changes, including one or two bug fixes.
This version is mostly about syncing the binary version with the code - many changes that did not on their own were important enough for a binary release, combined with the long time passed, it's time for a new one.

Most notable bug fix is that exiting the game should now always work. Also, ship designers will now limit the points you can use for your ship.

* Many code changes, that don't (shouldn't) affect game.
* Fixed another "Can't exit" bug (Whenever a window was closed by hitting the X button)
* Fixed the bug where there was no upper limit on the amounts of Design Points you could
  put in your ship.

08.10.2009 1.11

I've spent the last couple of weeks making the game actually playable in non-windows stations - thanks to Chris (For pointing out it doesn't work, and for repeatedly testing versions on his Linux station) and to Sonia (For testing on the Mac).

It should now work on any computer with a 5.0 JRE installed.

Also: I know of are two bugs in the game; If you happen to run across any more, please let me know, so I can fix them :)

* Game now runs under Linux, Mac, and hopefully everything else. Thanks Chris and Sonia!
* Everything is compiled as Java version 1.5 (Macs have problems with version 1.6)
* Fixed a bug that you could't exit the game without saving or retiring.

04.07.2009 1.1

* Separate main window to several classes.
* Enable play in non-cheat mode (Just enter a name in the New Game dialog)
* Fixed bugs in high-score: sort during save, read from file

21.11.2008 0.9

The first release is out - Version 0.9. Play today!


# Space Trader for Windows
by Jay French aka JAF (08.12.2003-14.08.2008)

14.08.2008
2.01 Changes:
* Fixed 6 bugs logged at sf.net.
* 1844296	100 Bays truncate to 10 during plunder 
* 1844287	Hull Hardening 
* 1844164	V 2.00 police choice negated 
* 1684012	v2.00 money error 
* 1184044	shipyard design error 
* 1173304	cargo transfer by pirate crashes the game 

* Removed installer and game converter projects
* Added folders to zip file for custom ship data, saved games, and settings data
* Added 4 new sets of custom ship images - thanks to William W. Connors!

23.03.2005
2.0 Enhancements:
* Bounty offered for capture/killing of player based on their Police Record
* Custom ship designs in Shipyards, and newspaper will have notice when nearby system has a shipyard
* Redesigned Equipment screen
* Redesigned Personnel screen
* Added color to Special button
* Added some tooltips
* Added 15 new mercenaries and 8 new systems; allow up to three mercenaries per system at when new game is started
* Added option to show newspaper automatically on arrival
* Redesigned the saved game file format, so that new versions of Space Trader will always be able to open saved games from previous versions (back to 2.0)
* Created file converter (saved games, high scores, options) to convert 1.3 files to 2.0
* Added folders to the base folder for the saved games, images, etc. to reside in
* Added 2 new quests
* Added 1 new gadget
* Numerous bug fixes
* Added lots more images for equipment, etc.
* Require confirmation when loading a game, starting a new game, or quitting game (but only when at least one day has passed since the last save)
* Fixed code that generates opponents - the opponents were usually weaker than they should have been
* Added 2 new weapons that can disable an opponent without distroying them

2004-02-09 - Space Trader 1.2 has been released. It includes 2 bug fixes. See the History page for the list of fixes. Visit the Download page to download the latest install.

* You can't manually enter larger values on the new game screen to give total skills greater than 20.
* Fixed a bug that prevented the Alien Artifact quest from ever being finished.
* Didn't break saved-game compatability!
    
2003-12-23 - Space Trader 1.1 has been released. It includes several bug fixes. See the History page for the list of fixes.

* Buying a new ship when you had already upgraded from the Gnat no longer causes an error.
* Jarek and Wild will now leave the ship when you arrive at the destination you agreed to transport them to (you must click the Special button at the destination for them to leave your ship, though).
* There was a bug where if you had special cargo (cargo that wasn't a trade item but takes up cargo bays) an error message would pop up when you defeated an opponent.
* Firing a mercenary caused an error if your ship had 2 crew quarters (if you had 3 there was no error, which is what made this one elusive for me).

2003-12-08 - Space Trader for Windows 1.0 is released! Thanks to all my beta testers, including (but not limited to):

* David Besselievre
* David Dousay
* Ames Grawert
* Andrew Kuehler
* Jack Lindsay
* Tim Schaab
* Jean-Paul Turcotte
* Eric Zippe

And of course a huge "Thanks!" to Pieter Spronk for creating such a great game in the first place. 

    
# Space Trader (Original version for Palm OS)
by Pieter Spronck (25.09.2000-08.12.2005)

Version 1.2.2 (December 8, 2005)
* Jim Anderson sent me a fix for the absent
  Scarab problem.
* The newspapers will now announce the
  coming of all heroes, not only of those
  that are exactly at the minimum hero score.
* If, by any chance, after transporting Wild 
  you are not a criminal, your police record
  will no longer be reset to clean.

Version 1.2.1 (April 16, 2005)
* DrWowe sent me the fix for the "Special" bug,
  which makes newer Palm versions crash when you
  tap the "Special" button. It should be fixed now.
  Not that this was a "pointer" error, which in
  programmer terms means that it could have any
  weird effect. Now, there were some rare errors
  reported that I have not been able to solve,
  nor been able to replicate. It may very well
  be that this pointer error was responsible for
  those problems too.
* Solved a very irritating problem that could
  happen if you installed a version of SpaceTrader
  which was incompatible with your Palm; it could
  happen that your Palm would lock up even if
  you didn't try to start the game.
* Solved a rare glitch where you could get negative 
  cargo bays.
* Solved a problem where donating a shield or
  weapon to a famous captain would bodge your
  remaining shields or weapons, if it wasn't the
  last shield/weapon in row that was removed.

Version 1.2.0 (October 13, 2002)
Three-quarters of the changes in version 1.2.0 have 
been made by Samuel Goldstein (palm@fogbound.net).
* There are four new quests. Two of these are meant 
  for players with a criminal career. I won't divulge 
  more of these quests (they are for the player to
  discover), except that some interesting quest 
  rewards have been added.
* Six special, very rare encounters have been added.
  I leave these also for the player to discover.
* From the System Information screen, you can now 
  access "News", which provides general information
  on the current system, interesting information
  on nearby systems, and a few special messages.
  The price of the News depends on the difficulty
  level, but you have to pay it only once when you
  have docked at a system. Every next time you view
  the news, you can do so for free. If you want to
  pay automatically, you can indicate this on the
  Options page.
* You can now dump cargo while docked. You do this 
  from the Sell Cargo screen by tapping the 
  appropriate "Dump" button. You have to pay for 
  garbage processing, though. You can also dump cargo 
  in space, when you are robbing another ship or when 
  you try to pick up floating canisters. However, if 
  you do that you run a serious risk the police will 
  update your record in a negative way, for "space 
  littering". This can have serious repercussions
  if you are discovered.
* Traders you meet in space may contact you to sell
  or buy goods. If you are a criminal, they may wish 
  to trade illegal goods. While the deals they offer 
  may often be good, some of your trading partners 
  may try to rip you off. Be especially careful when 
  doing shady deals! If you don't like this feature, 
  you may turn these meetings off on the Options page, 
  with the "Ignore dealing traders" option.
* On the Galactic chart, when you tap a wormhole,
  you get to see its destination, both in text and
  as a line on the chart.
* On the galactic chart, you can indicate that you
  want to "track" a system. You can do this through
  the "Find" function, or by tapping a target system
  twice. A further tap allows you to remove the 
  track again. A system that is tracked is indicated
  with an arrow on the Short Range Chart, and at the
  bottom of the Short Range Chart you see the 
  distance to the tracked system. A track is 
  automatically removed when you dock at the tracked
  system.
* On the Options page you can indicate that you want
  the hardware buttons to emulate the four shortcut
  buttons which are located in the top right of the
  screen. This may especially be useful for left-
  handed people.
* You can use the "Shortcuts" choice in the "Game" 
  menu to change the functions of the four shortcut
  buttons in the top right corner of the "docked"
  screens. You can assign any of the major functions.
* The Commander Status screen has been updated, and
  now allows you to see Special Cargo on a separate
  screen. You can switch between Status, Quests, Ship
  and Special Cargo with buttons at the bottom of
  these screens. 
* The Commander Status screen now also shows the 
  player's net worth.
* The bounty system has been redesigned. Bounties 
  are higher for the stronger ships and lower for
  the weaker ships. This depends on a target ship's 
  type, equipment and crew. The career of a bounty 
  hunter has become more intersting this way. 
  Bounties now vary between 25 credits for a measly 
  Flea to 2500 credits for a fully equipped Wasp. 
  You don't get bounties paid if you are a criminal.
* The bank will issue statements on your debt at 
  certain intervals. If you don't want this, you can 
  turn it off in the Options menu.
* A customs officer may give you a warning if a scan 
  detects tribbles on your ship.
* The Termite can now travel 13 parsecs. Furthermore, 
  you will always find at least one other system 
  within 13 parsecs of any system. The combination of 
  these two changes has solved the problem of the 
  player being able to buy a ship that can not get to 
  any other system in the neighbourhood. Whatever 
  ship you are flying now, you can always travel to 
  any other system in the galaxy. Since this makes 
  the Termite more useful, I had to raise its price 
  by 12.5%, though. 
* Excluding the moon quest, you can now never have 
  more than four open quests at the same time. You
  simply won't be offered new quests as long as
  you have four quests open on the Quests screen.
* Added support for grayscales. The color version of
  the game now no longer supports black & white
  graphics. You have to install the right version
  for your Palm. See the ReadMe.txt file for more
  information.
* There are now two Options screens. You can switch
  between them using a button on the screens.
* From the "Game" menu you can now switch to a 
  parallel game, and back again. This allows you to
  play to different, parallel games on one Palm.
  Handy for people who share a Palm. Parallel games
  can either share their preferences or have their 
  own separate preferences. This is configured on the 
  second page of the Options menu choice. You can 
  also indicate here whether or not you want your 
  commander to be identified when you start Space 
  Trader.
* From the "Game" menu you can now make a snapshot of
  your game. This is meant purely for debugging
  purposes. If you encounter a bug and send me a
  message about it, I might ask you to make a 
  snapshot just before the error occurs and send it
  to me. I can use this to recreate the error or
  study your current game to see if there is anything
  weird going on.
* If you use Palm OS 5.0 or higher, the game will
  default to using buttons with rounded corners 
  everywhere. This is because at the release of
  Space Trader 1.2.0 it is still unknown whether or 
  not these OS versions support buttons with 
  rectangular corners, but it is highly likely they
  do not.
* To account for the extra space needed for 
  possible rounded buttons, several buttons have 
  been made slightly wider, notably the shortcut
  buttons and the "All" buttons on the Sell Cargo
  screen. This forced a name change of the System
  Information screen to System Info screen. Also,
  the trade good "Machinery" had to be changed to
  "Machines".
* The black & white graphics have been redone by
  hand.
* Contrast between the colors of visited and
  unvisited systems on color Palms has been
  increased.
* On Hard and Impossible level your police record
  does not convert back to Dubious so fast as 
  before. On Hard level it takes three times as long,
  and on Impossible level four times as long.
* If you flee from a police inspection, other police
  ships you meet on the remainder of your journey 
  will attack you, at least on Normal difficulty 
  level or higher.
* Added Help On Menu.
* Backup bits for PRC file and databases are now set.
* Fixed a few spelling mistakes, displays and 
  incorrect help texts.
* Extra events have been captured to ensure correct
  working of the game on Palm OS 5.0.

Version 1.1.2 (January 14, 2002)
* In the Options menu you can now choose "Continuous
  attack and flight". If you check this option, after
  you choose "Attack" or "Flee" on the Encounter screen,
  that action is repeated every second until you choose
  another action, or the situation changes (a ship is
  destroyed, a ship escapes, the opponent turns around
  and starts to flee, the opponent surrenders, etc.).
  This may alleviate the "tap-fest" for more experienced
  players. While the action is repeating, you can
  interrupt by tapping the button marked "Int." (for
  "Interrupt"), or you can simply switch to another 
  action. Anything you do (except tapping "Attack" or 
  "Flee") will interrupt the repetition. To show you
  that the repeated action is still continuing, a
  spot is blinking right next to the "Int." button.
  If you want, you can, on the Options screen, also 
  indicate that you want to automatically continue 
  attacking a fleeing ship. If you don't check this 
  option, as soon as a ship stops attacking you and 
  starts fleeing, the repeated attack will be 
  interrupted because the opponent is changing his action. 
  Since most players want to be ruthless killers, they 
  probably simply want their attack to continue, and 
  that's why this option has been added.
* You now have the possibility to keep your unique
  equipment when buying a new ship, if the ship has slots
  available for this equipment. The prices asked may 
  seem very high at first glance, but they are actually
  a good deal, because they were already subtracted from 
  the new ship's price, and you don't have to buy new 
  equipment for those slots. You will automatically lose 
  (or rather, sell) your unique equipment if your new ship 
  doesn't have the slots to install it.
* Space Trader is now a multi-segmented application,
  meaning that it should have far less problems on Palm 
  OS 2.0. Beta tests have shown that this also solves the 
  crashes encountered sporadically on Palm OS 4.0 and 
  higher. This conversion has been done by Sam Anderson, 
  to whom I extend my heartfelt thanks.
* Sam Anderson also solved the problem of a debts getting
  so large that they would turn into profits.
* Sam allowed you to see your cash position on the Ship 
  Yard screen.
* I've made a few, small textual and visual changes.
  Sam corrected some grammatical errors.
* I corrected some rare errors which could occur when 
  activating cheat functions. Of course, cheaters 
  shouldn't prosper...
* In earlier versions it was, due to an oversight, in
  some circumstances possible to jump to the current 
  system. This is no longer possible.
* The alien invasion quest now gets one extra day. That
  is, the final day of the quest (day zero) they haven't
  arrived yet, while in the previous version of Space
  Trader they were already there. 
* I finally solved the problem of the information "i"
  appearing in the upper right corner of some screens
  when tapping between two buttons.
* Some players who installed an enhanced Palm environment
  would get white boxes around the ship displays. That
  should have been solved now, though I couldn't test it.
* You get to see your score even if you don't enter the
  high-score list.
* I changed the reputation "Helper" into "Liked". 
  Originally I intended this to be "Deputy", and allowing
  the player to take over the role of police officer, but
  that proved to be too much work for my limited time for
  this release.
* When the picture was shown for the Destroyed ship, the
  Utopia moon or the Retirement, you could still switch 
  back into the game by using one of the shortcuts. Of
  course, this has been fixed.
* You must now confirm the firing of a mercenary.
* On Palm OS 2.0, the icon of the application was shown
  for only three quarters. This has been solved.

Version 1.1.1 (December 24, 2000)
* The Alien Invasion quest could overwrite one of the
  other quest end systems, meaning that you wouldn't be
  able to finish such a quest. This could even mean that
  you couldn't claim your moon. This has been fixed. The
  claiming of the moon is also fixed in on-going games,
  but if you are still playing a game when you install the
  new version, it might be that one of the other quests 
  cannot be ended. Chances for that to happen are low, 
  though. New games won't have any problems.
* On some Palms, it could happen that little bits of the
  shields were still shown at the top of the ship. This 
  should be fixed in this version, though I couldn't test
  this, because it doesn't happen on my Palms.
* This version uses slightly less memory than the previous
  version. This might solve a problem on the oldest Palms,
  which would sometimes report that the game can't be
  loaded. Not certain about that, though. A future version
  should reduce memory usage drastically, solving this
  problem completely.

Version 1.1 (November 1, 2000)
* Thanks to Alexander Lawrence, encounters in space now 
  have graphical ship displays. If you like, you can turn 
  this feature off in the Options menu. The graphical 
  displays visualize the status of your shields and hull. 
  A small icon indicates what type of opponent you are 
  dealing with. The ship designs are also visible on the 
  ship info screen.
* Alexander Lawrence also supplied new pictures for the
  intro screen and all the endgame screens. Previously,
  only the "moon" endgame screen had a picture. Now all
  endgame screens have a picture.
* Since this release, there are two versions of Space
  Trader, a color version and a black & white version. The 
  only difference is in the screen-filling pictures, of 
  which there are four in the game. In the black & white 
  version, these are only available as black & white. 
  This makes the black & white version substantially 
  smaller than the color version. If the black & white 
  version is played on a color Palm, you still have colored 
  solar systems and colored ships, though.
* In version 1.0.2, you could warp to the system where you 
  are currently located from the Average Price List. This 
  has been solved.
* The check whether or not the extra cargo bays you have 
  are full when you want to sell them was faulty. The check
  wasn't executed for the extra cargo bays, but for any
  gadget which was first in your gadget list at the
  moment you tried to sell it. This has been solved.
* The fact that your ship is worth less when you have
  tribbles on board should only be taken into account when
  you try to sell it, not for insurance calculations. This
  wasn't the case in earlier versions, but it has been
  changed now.
* Trying to find a system with a name later in the alphabet
  than the last system name ("Zuul") would result in a
  soft reset. Now it simply focuses on the system where
  you are currently docked.
* You can now buy a trade item by tapping its name or price
  on the Average Price List.
* The option (in the Options menu) "Always ignore when it
  is safe" has been replaced by separate options for police,
  pirates and traders. The previous option has been 
  converted to the "traders" one. The police one is on
  by default, since normally almost any player won't attack
  a police ship unless it attacks first.
* When a pirate couldn't see you because you had a cloaking
  device, the game wouldn't say that he didn't notice you.
  This has been solved.
* Average prices can now only be bold if the item is indeed
  available in the current system.
* You must now confirm your surrender to aliens.
* You would never pay more than 1 credit in interest per 
  day. This has been solved.
* When you had an Elite reputation, the Commander Status 
  screen could crash the game, forcing a soft reset. This 
  has been solved.
* Previously, a player on Beginner level would get a bonus
  of 2 points on all of his skills. This has been reduced
  to 1 point.
* Previously, there were three systems where you could get
  the space monster quest, and three systems where you
  could get the Dragonfly quest. It is now one system for
  each, like all the other quests. This makes the occurrence 
  of these quests rarer. Which is a good thing, because they
  are very difficult and should only be attempted by 
  very strong and quick ships.
* Added two new quests "Ambassador Jarek" and "Alien
  Invasion". Both these quests have interesting rewards.
  Note that you can only get them when you start a new game
  with this version of Space Trader.
* Pirates can now surrender to you. They don't do it often,
  and if they do, they normally have less on board than
  your average trader.
* A bug in the game made opponent's reactions different 
  than I wanted them to be in some cases. This has been
  changed.
* Tribbles multiply faster when you feed them.
* For the score calculation, every credit you have over
  one million, won't be counted for as much the ones below.
  This may change the score percentages in your high score
  table a bit.
* The Flea has got a stronger hull.
* When an opponent has shields, he now has an 80% chance
  that his hull is intact. If not, his hull damages will
  be equivalent to the hull damages of a ship without
  shields. In the previous version, the hull of a shielded
  ship always had a good chance to be damaged, though it
  would usually be less damaged than an unshielded ship.
* I accidentely reset the random number generator at some
  point during the fight with a pirate or a trader. If that 
  happened, you could get into a loop where each next fight 
  would go through exactly the same motions. This has been 
  solved.

Version 1.0.2 (October 5, 2000)
* You can no longer get to the average price list for an
  out-of-range system. In 1.0.1 you could. You could even
  warp to an out-of-range system. This could get you an
  impossible amount of fuel in your tanks, which could
  crash the system later on in the short range chart screen. 
  Thanks to all of you who helped me finding out the cause 
  of this annoying and elusive problem.
* Your police record score converges to "clean" faster than
  before, if you don't do anything special, especially 
  when you're a criminal. Your score won't rise above
  "dubious" in this way, however. So, even if you're keeping
  quiet, the police won't trust you when you were a criminal,
  until you undertake some positive action.
* When you get arrested, you are convicted according to your
  police record. A criminal therefore gets off a lot easier
  than a villain. If you can pay the fine with your credits,
  you don't lose your ship. Mercenaries, however, will leave
  and any insurance will also be lost. During your time
  in prison, if you have debts, you will pay the interest and
  if needed, your debt will increase during that time.
* If tribbles eat narcotics, they eat at most three bays of
  them, and their furs are left behind.
* You can now use the physical scroll buttons to scroll 
  through the systems within range in the average price list
  and the target system screens.
* Added "Crook" to police records. A crook will be attacked
  by the police, but will be dealt with fairly easily when 
  arrested. The other levels have been moved a bit.
* You can now also see information on ships which aren't for
  sale at a space station.

Version 1.0.1 (October 1, 2000)
* The black and white version of the end-game picture 
  was corrupted on earlier versions of the Palm OS.
  This proved to be due to the picture compression.
  I turned compression for the black and white pictures
  off, which solved the problem.
* If you would specify a loan amount, you were limited to
  four digits. This has been fixed. Paying back was even
  worse: you could enter five digits, but the game could
  crash if you did.
* Shortcut buttons have been added to most screens to go
  immediately to Buy Cargo, Sell Cargo, the Ship Yard or the
  Short Range Chart.
* On the average price list screen, it is now possible to
  toggle between absolute prices and price differences.
* On both the average price list screen and the target system
  screen, you can now scroll through the systems within range.
* On the short range chart, the last system you have 
  consulted is marked with a cross.
* Tapping a system on the short range chart will load either 
  the average price list screen or the target system screen,
  depending on which of these two screens was loaded last.
* Tribbles can now bring even greater profits. Also, selling
  a tribble infested ship will get rid of the tribbles, but
  your ship won't be worth much. If your ship is destroyed,
  this will also destroy the tribbles.
* Water now brings slightly higher profits.
* Criminals and villains can surrender to the police. This 
  will cost you your ship and cargo, and you have to spend 
  some time in prison. You will live, however. After that 
  time, your police record will have reverted to dubious.  
  Psychopaths do not have the option to surrender.
* Attacking a police ship while you are still dubious, or
  attacking a trader while you are still clean, will ask
  you for confirmation.
* The Options menu now has a shortcut.
* In the Options menu, you can now specify that you want to
  leave some cargo bays empty when buying trade goods. This
  makes it easier to leave some room for picking up cargo
  during flight, especially if you are an avid user of the 
  Max button.

Version 1.0 (September 25, 2000)
* First release.
