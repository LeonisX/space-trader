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

## Captain Quest

# Alerts:
AlertEncounterAttackCaptainTitle=Really Attack?
AlertEncounterAttackCaptainMessage=Famous Captains get famous by, among other things, destroying everyone who attacks them. Do you really want to attack?
AlertEncounterAttackCaptainAccept=Really Attack
AlertEncounterAttackCaptainCancel=OK, I Won't
AlertMeetCaptainAhabTitle=Meet Captain Ahab
AlertMeetCaptainAhabMessage=Captain Ahab is in need of a spare shield for an upcoming mission. He offers to trade you some piloting lessons for your reflective shield. Do you wish to trade?
AlertMeetCaptainAhabAccept=Yes, Trade Shield
AlertMeetCaptainConradTitle=Meet Captain Conrad
AlertMeetCaptainConradMessage=Captain Conrad is in need of a military laser. She offers to trade you some engineering training for your military laser. Do you wish to trade?
AlertMeetCaptainConradAccept=Yes, Trade Laser
AlertMeetCaptainHuieTitle=Meet Captain Huie
AlertMeetCaptainHuieMessage=Captain Huie is in need of a military laser. She offers to exchange some bargaining training for your military laser. Do you wish to trade?
AlertMeetCaptainHuieAccept=Yes, Trade Laser
AlertSpecialTrainingCompletedTitle=Training Completed
AlertSpecialTrainingCompletedMessage=After a few hours of training with a top expert, you feel your abilities have improved significantly.

# News:
NewsCaptAhabAttacked=Thug Assaults Captain Ahab!
NewsCaptAhabDestroyed=Destruction of Captain Ahab's Ship Causes Anger!
NewsCaptConradAttacked=Captain Conrad Comes Under Attack By Criminal!
NewsCaptConradDestroyed=Captain Conrad's Ship Destroyed by Villain!
NewsCaptHuieAttacked=Famed Captain Huie Attacked by Brigand!
NewsCaptHuieDestroyed=Citizens Mourn Destruction of Captain Huie's Ship!

# Encounters:
EncounterPretextCaptainAhab=the famous Captain Ahab in a ^1
EncounterPretextCaptainConrad=the famous Captain Conrad in a ^1
EncounterPretextCaptainHuie=the famous Captain Huie in a ^1
EncounterShipCaptain=Captain
EncounterTextFamousCaptain=The Captain requests a brief meeting with you.

# VeryRareEncounters:
VeryRareEncounterCaptainAhab=Captain Ahab
VeryRareEncounterCaptainConrad=Captain Conrad
VeryRareEncounterCaptainHuie=Captain Huie

# Crew Member Names:
CrewNameCaptain=Captain


## Marie Celeste Quest

# Alerts:
AlertEncounterMarieCelesteTitle=Engage Marie Celeste
AlertEncounterMarieCelesteMessage=The ship is empty: there is nothing in the ship's log, but the crew has vanished, leaving food on the tables and cargo in the holds. Do you wish to offload the cargo to your own holds?
AlertEncounterMarieCelesteAccept=Yes, Take Cargo
AlertEncounterMarieCelesteNoBribeTitle=No Bribe
AlertEncounterMarieCelesteNoBribeMessage=We'd love to take your money, but Space Command already knows you've got illegal goods onboard.
AlertEncounterPostMarieTitle=Contraband Removed
AlertEncounterPostMarieMessage=The Customs Police confiscated all of your illegal cargo, but since you were cooperative, you avoided stronger fines or penalties.
AlertEncounterPostMarieFleeTitle=Criminal Act!
AlertEncounterPostMarieFleeMessage=Are you sure you want to do that? The Customs Police know you have engaged in criminal activity, and will report it!
AlertEncounterPostMarieFleeAccept=Yes, I still want to
AlertEncounterPostMarieFleeCancel=OK, I won't

# Encounters:
EncounterPretextMarieCeleste=a drifting ^1
EncounterPretextMariePolice=the Customs Police in a ^1
EncounterTextMarieCeleste=The Marie Celeste appears to be completely abandoned.
EncounterTextPolicePostMarie="We know you removed illegal goods from the Marie Celeste. You must give them up at once!"

# VeryRareEncounters:
VeryRareEncounterMarieCeleste=Marie Celeste


## Bottle Quest

# Alerts:
AlertEncounterDrinkContentsTitle=Drink Contents?
AlertEncounterDrinkContentsMessage=You have come across an extremely rare bottle of Captain Marmoset's Amazing Skill Tonic! The "use-by" date is illegible, but might still be good.  Would you like to drink it?
AlertEncounterDrinkContentsAccept=Yes, Drink It
AlertEncounterTonicConsumedGoodTitle=Tonic Consumed
AlertEncounterTonicConsumedGoodMessage=Mmmmm. Captain Marmoset's Amazing Skill Tonic not only fills you with energy, but tastes like a fine single-malt.
AlertEncounterTonicConsumedStrangeTitle=Tonic Consumed
AlertEncounterTonicConsumedStrangeMessage=While you don't know what it was supposed to taste like, you get the feeling that this dose of tonic was a bit off.

# Encounters:
EncounterPretextBottle=a floating ^1
EncounterTextBottle=It appears to be a rare bottle of Captain Marmoset's Skill Tonic!

# VeryRareEncounters:
VeryRareEncounterDatedTonic=Dated Tonic
VeryRareEncounterGoodTonic=Good Tonic


============================================================

## Captain Quest

# Alerts:
AlertEncounterAttackCaptainTitle=Точно решили напасть?
AlertEncounterAttackCaptainMessage=Прославленные капитаны известны, кроме всего прочего тем, что уничтожают всех, кто им угрожает. Вы действительно хотите атаковать?
AlertEncounterAttackCaptainAccept=Точно, атаковать
AlertEncounterAttackCaptainCancel=Хорошо, я не буду
AlertMeetCaptainAhabTitle=Встреча с капитаном Ахабом
AlertMeetCaptainAhabMessage=Капитану Ахабу, в его предстоящей миссии, понадобится запасной отражающий щит. Он готов предложить уроки пилотирования в обмен на ваш. Согласиться на такой обмен?
AlertMeetCaptainAhabAccept=Да, обменять щит
AlertMeetCaptainConradTitle=Встреча с капитаном Конрадом
AlertMeetCaptainConradMessage=Капитану Конраду необходим военный лазер. За него она может обучить вас разным инженерным штучкам. Согласиться на такой обмен?
AlertMeetCaptainConradAccept=Да, обменять лазер
AlertMeetCaptainHuieTitle=Встреча с капитаном Хьюи
AlertMeetCaptainHuieMessage=Капитану Хьюи нужен военный лазер. За него она может научить вас заключать выгодные торговые сделки. Согласиться на такой обмен?
AlertMeetCaptainHuieAccept=Да, обменять лазер
AlertSpecialTrainingCompletedTitle=Обучение завершено
AlertSpecialTrainingCompletedMessage=После нескольких часов обучения с лучшим специалистом вы чувствуете, что способности значительно улучшились.

# News:
NewsCaptAhabAttacked=Головорезы совершили покушение на капитана Ахаба!
NewsCaptAhabDestroyed=Уничтожение судна капитана Ахаба вызвало волну гнева!
NewsCaptConradAttacked=Капитан Конрад была атакована преступником!
NewsCaptConradDestroyed=Злодей уничтожил судно капитана Конрада!
NewsCaptHuieAttacked=Прославленный капитан Хьюи была атакована бандитом!
NewsCaptHuieDestroyed=Граждане скорбят после известия об уничтожении судна капитана Хьюи!

# Encounters:
EncounterPretextCaptainAhab=знаменитый Капитан Ахаб в корабле класса ^1 | знаменитого Капитана Ахаба в корабле класса ^1
EncounterPretextCaptainConrad=знаменитый Капитан Конрад в корабле класса ^1 | знаменитого Капитана Конрада в корабле класса ^1
EncounterPretextCaptainHuie=знаменитый Капитан Хьюи в корабле класса ^1 | знаменитого Капитана Хьюи в корабле класса ^1
EncounterShipCaptain=Капитан | Капитана
EncounterTextFamousCaptain=Капитан настаивает на непродолжительной стыковке с вами.

# VeryRareEncounters:
VeryRareEncounterCaptainAhab=Капитан Ахаб
VeryRareEncounterCaptainConrad=Капитан Конрад
VeryRareEncounterCaptainHuie=Капитан Хьюи

# Crew Member Names:
CrewNameCaptain=Капитан | Капитана


## Marie Celeste Quest

# Alerts:
AlertEncounterMarieCelesteTitle=Взойти на борт Марии Селесты
AlertEncounterMarieCelesteMessage=Корабль пуст. В судовом журнале нет никаких отметок, а экипаж исчез, оставив нетронутой еду на столах и груз в трюмах. Хотите перегрузить товары к себе на борт?
AlertEncounterMarieCelesteAccept=Да, забрать груз
AlertEncounterMarieCelesteNoBribeTitle=Взятке нет
AlertEncounterMarieCelesteNoBribeMessage=Мы бы взяли деньги, но космическое командование уже знает о том, что у вас на борту находятся запрещённые товары.
AlertEncounterPostMarieTitle=Контрабанда конфискована
AlertEncounterPostMarieMessage=Таможенная полиция конфисковала все запрещённые товары, но поскольку вы сотрудничали, то избежали большого штрафа.
AlertEncounterPostMarieFleeTitle=Это уголовный поступок!
AlertEncounterPostMarieFleeMessage=Вы уверены, что хотите так поступить? Таможенная полиция знает, что вы вовлечены в преступную деятельность, и сообщит об этом!
AlertEncounterPostMarieFleeAccept=Да, я всё ещё хочу
AlertEncounterPostMarieFleeCancel=Хорошо, я не буду

# Encounters:
EncounterPretextMarieCeleste=дрейфующее судно класса ^1
EncounterPretextMariePolice=таможенная полиция в корабле класса ^1 | таможенную полицию в корабле класса ^1
EncounterTextMarieCeleste=Мария Селеста, похоже, полностью заброшена.
EncounterTextPolicePostMarie="Нам известно, что вы забрали с Марии Селесты запрещённые товары. Вы должны сейчас же их отдать!"

# VeryRareEncounters:
VeryRareEncounterMarieCeleste=Мария Селеста


## Bottle Quest

# Alerts:
AlertEncounterDrinkContentsTitle=Выпить содержимое?
AlertEncounterDrinkContentsMessage=Вы столкнулись с чрезвычайно редкой бутылкой Удивительного Тоника Умений капитана Мармоше! Срок годности прочитать не удаётся, но он всё равно может быть хорошим. Хотите выпить его?
AlertEncounterDrinkContentsAccept=Да, выпить это
AlertEncounterTonicConsumedGoodTitle=Тоник был выпит
AlertEncounterTonicConsumedGoodMessage=Ммммм. Удивительный Тоник Умений капитана Мармоше не только полон энергии, но и на вкус напоминает односолодовый виски.
AlertEncounterTonicConsumedStrangeTitle=Тоник был выпит
AlertEncounterTonicConsumedStrangeMessage=Поскольку вы не знаете, каким этот тоник должен быть на вкус, у вас возникает ощущение, что он немного просрочен.

# Encounters:
EncounterPretextBottle=летящая бутылка | летящую бутылку
EncounterTextBottle=Похоже, что это редчайшая бутылка Тоника Умений из коллекции Капитана Мармоше!

# VeryRareEncounters:
VeryRareEncounterDatedTonic=Просроченный Тоник
VeryRareEncounterGoodTonic=Хороший Тоник



* Test every very rare encounter separately

* Test captain destruction
* Test all
* Revert back isDetermineRandomEncounter()
* Revert back Game(String name, Difficulty difficulty, int pilot, int fighter, int trader, int engineer, MainWindow parentWin)

* EncounterPretextMariePolice - unused string. Need to implement
* Rareware cheat - first all rare quests, next as usual.


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

### Shipyard

* Ship modification is free :(
* Investigate - when new shipyard - can receive 600%
* Correlian - hull strength - 200 minimum, no max (JAF). Cargo bays - no limit. Need to test
* Update money status (StatusBar) after Shipyard
* Instead of the custom ship name sometimes see "Bottle"

### Monster.com

* Monster.com - rename cheat, form name (not MonsterCom)
* MonsterCom: Show only current phase of quest. CheckBox for full view (only for cheat/debug)
* MonsterCom - second click - order reverse
* Quest panel with scrolling
* Panel - Quest Persons: all persons from quests; Show planet. If onboard - onboard
* All quests - to land passengers on the planets?

### Translate

* ~~Translate all quests~~
* FormInput, FormViewCommander translate
* FormMonsterMenuItem, CheatsMenu -> Strings, languages
* FileDialogs - hide translation logs
* FormTests, FormAlertTests - refactor, translate
* На растоянии 1 клик от системы...
* В течении 2 дня остановить эксперимент доктора...
* Update english-russian.xls

### Other

* Different image sizes, font sizes (config)
* Installers: http://www.jrsoftware.org/isdl.php
* Installers: https://github.com/nebula-plugins/gradle-ospackage-plugin
* Full Russian description, FAQ
* Dig about Star Trader for C16

### Minor tasks

* Need to update font sizes. Dump all too.

## Feature requests

* Show in Cargo planet names (target)
* Rewrite CargoBuyStatement, CargoSellStatement. Can't translate to Russian
* On sell, plunder, jettison - show goods with bold
* Sell - add goods to planet
* Save last search phase in Find form
* Buy/Sell equipment - CheckBox, which offers all equipment in debug/cheat modes

## Known bugs:

* When finish game - see > 100% percents, but in HighScores all OK.
* Many dialogs don't allow to close them from [x]
* FormShipyard: Trying to set unknown background: color at 0x0: 0 0 0 0 (Color.BLACK)
* FormJettison - incorrect flow when try to dump all - first message about littering, second must be: EncounterDumpAll, AlertsEncounterDumpAllMessage,
* When fire mercenary - he disappears forever
* 2 latest bugs from https://sourceforge.net/p/spacetraderwin/bugs/
* Commander status, Find system - truncated window sometimes
* What to do with ending title?? It's too long :(
* What to do with cheats status title?? It's too long :(
* Commander status - Всеобщий любимец (70) under button
* Bug - when 5 gadget slots - cant install 5 extra hidden bays. When decrease to 4 - can.
* After disabling can fully destroy without encounter ending
* Morgan's Laser - sell price 0 cr.
* Ability to ignore encounter by press "x"
* Strange - when start new game - see few visited systems
* Very strange - after arrival see, that I visited other systems. Or arrive in other system
* Bug? Don't hide wormhole route after arrive
* Ship name (FormViewShip) - Bottle :(
* Autosize doesn't works in FormViewQuests (ArtifactQuest))

## Regressions:

* Bold font for labels on main screen, formEncounter. Source: new `tahomabd.ttf`

## TODO list of Jay French

* make shipyard news advertisements
* rework escape pod sequence
* create images for trade goods
* passenger modules
* courier quests
* more very rare encounters
* Bug - after prison - Ziyal onboard, can finish quest

## Far perspectives

* ~~Quests engine~~
* Standard game, Extended game
* JavaFX UI
* Full-windowed
* Site
* ~~FAQ, description~~
* New interesting quests
