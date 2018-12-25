package spacetrader.util

import spacetrader.game.*
import spacetrader.game.cheat.GameCheats
import spacetrader.game.enums.*
import spock.lang.Specification

import java.nio.file.FileSystems

class FunctionsTest extends Specification {

    @SuppressWarnings("all")
    def "distance"() {
        expect:
        Functions.distance(new StarSystem(x: 0, y: 0), new StarSystem(x: 4, y: 4)) == 5
        Functions.distance(new StarSystem(x: 0, y: 0), 2, 2) == 2
    }

    def "Format number for US"() {
        when:
        GlobalAssets.setLanguage(Language.ENGLISH)

        then:
        Functions.formatNumber(1) == '1'
        Functions.formatNumber(10) == '10'
        Functions.formatNumber(100) == '100'
        Functions.formatNumber(1000) == '1,000'
    }

    def "Format number for Russia"() {
        when:
        GlobalAssets.setLanguage(Language.RUSSIAN)

        then:
        Functions.formatNumber(1) == '1'
        Functions.formatNumber(10) == '10'
        Functions.formatNumber(100) == '100'
        Functions.formatNumber(1000) == '1 000'
    }

    def "FormatList"() {
    }

    def "FormatMoney"() {
    }

    def "FormatPercent"() {
    }

    def "stringVars with plural"() {
        when:
        Strings.pluralMap = ['a2': 'aa']
        def toParse = '^1 ^^1 ^2 ^^2 ^^^3'
        def vars = ['a', 'b ']

        then:
        Functions.stringVars(toParse, vars) == 'a aa b  b  ^^^3'
    }

    def "english plurals"() {
        when:
        GlobalAssets.setLanguage(Language.ENGLISH)
        Strings.pluralMap.put('book2', 'books')
        String unit = 'book'

        then:
        Functions.plural(0, unit) == '0 books'
        Functions.plural(1, unit) == '1 book'
        Functions.plural(2, unit) == '2 books'
        Functions.plural(1001, unit) == '1,001 books'
    }

    def "russian plurals"() {
        when:
        GlobalAssets.setLanguage(Language.RUSSIAN)
        Strings.pluralMap = ['книга2': 'книги', 'книга3': 'книг']

        then:
        Functions.plural(number, 'книга') == result

        where:
        number || result
        0      || '0 книг'
        1      || '1 книга'
        2      || '2 книги'
        3      || '3 книги'
        4      || '4 книги'
        5      || '5 книг'
        11     || '11 книг'
        500000 || '500 000 книг'
        1001   || '1 001 книга'
        103    || '103 книги'
    }

    def "PluralWoNumber"() {
    }

    def "StringVars"() {
    }

    def "StringVars1"() {
    }

    def "StringVars2"() {
    }

    def "StringVars3"() {
    }

    def "split text"() {
        expect:
        Functions.splitString('') == []
        Functions.splitString('asd | ddd\\|ddd | c') == ['asd', 'ddd|ddd', 'c']
    }

    def "capitalize"() {
        expect:
        Functions.capitalize('') == ''
        Functions.capitalize('foo') == 'Foo'
        Functions.capitalize('FOO') == 'FOO'
    }

    def "GetRandom"() {
    }

    def "GetRandom1"() {
    }

    def "GetRandom2"() {
    }

    def "RandomSkill"() {
    }

    def "RandSeed"() {
    }

    def "IsInt"() {
    }

    def "version to Long"() {
        expect:
        Functions.versionToLong('') == 0
        Functions.versionToLong('1') == 1
        Functions.versionToLong('2.11') == 10011
        Functions.versionToLong('2.4.6.8-PR') == 2L * 5000 * 5000 * 5000 + 4 * 5000 * 5000 + 6 * 5000 + 8
    }

    def "version to Long with compare"() {
        expect:
        Functions.versionToLong('1.9.9') < Functions.versionToLong('1.9.10')
        Functions.versionToLong('1.9.9') == Functions.versionToLong('1.9 .09')
        Functions.versionToLong('1.9.9') < Functions.versionToLong('1.10.8')
    }

    def "GetHighScores"() {
    }

    @SuppressWarnings("all")
    def "write game to file, load and verify"() {
        setup:
        def fileName = System.getProperty('java.io.tmpdir') + FileSystems.getDefault().getSeparator() + UUID.randomUUID()

        Game game = new Game(
                commander:
                        new Commander(id: CrewMemberId.COMMANDER, skills: [3, 4, 5, 6], currentSystemId: StarSystemId.Bretel,
                                cash: 999, debt: 34, killsPirate: 12, killsPolice: 13, killsTrader: 14, policeRecordScore: 15,
                                reputationScore: 16, days: 17, insurance: true, noClaim: 18, ship: new Ship(ShipType.BUMBLEBEE),
                                priceCargo: [21, 22, 23, 24, 25, 26, 27, 28, 29, 30]),


                cheats: new GameCheats(cheatMode: true),

                universe: [
                        new StarSystem(id: StarSystemId.Aldea, x: 1, y: 2, size: Size.LARGE,
                                techLevel: TechLevel.MEDIEVAL, politicalSystemType: PoliticalSystemType.CAPITALIST,
                                systemPressure: SystemPressure.CROP_FAILURE, specialResource: SpecialResource.DESERT,
                                specialEventType: SpecialEventType.Dragonfly, tradeItems: [1, 2, 3, 4, 5, 6, 7, 8, 9, 0], countDown: 11,
                                visited: true, shipyardId: ShipyardId.KUAT),
                        new StarSystem(id: StarSystemId.Balosnee, x: 11, y: 12, size: Size.MEDIUM,
                                techLevel: TechLevel.HI_TECH, politicalSystemType: PoliticalSystemType.CYBERNETIC,
                                systemPressure: SystemPressure.NONE, specialResource: SpecialResource.NOTHING,
                                specialEventType: SpecialEventType.NA, tradeItems: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9], countDown: 111,
                                visited: false, shipyardId: ShipyardId.NA)
                ],

                wormholes: [5, 4, 3, 2, 1, 0],

                mercenaries: [
                        new CrewMember(id: CrewMemberId.ARAGORN, skills: [3, 4, 5, 6], currentSystemId: StarSystemId.Bretel),
                        new CrewMember(id: CrewMemberId.ALYSSA, skills: [7, 6, 5, 4], currentSystemId: StarSystemId.NA)
                ],

                dragonfly: new Ship(ShipType.DRAGONFLY),
                scarab: new Ship(ShipType.SCARAB),
                spaceMonster: new Ship(ShipType.SPACE_MONSTER),
                opponent: new Ship(type: ShipType.BEETLE, size: Size.LARGE, cargoBays: 24, weaponSlots: 3,
                        shieldSlots: 4, gadgetSlots: 5, crewQuarters: 6, fuelTanks: 34, fuelCost: 12, hullStrength: 120,
                        repairCost: 11, price: 240001, occurrence: 7, police: Activity.ABSENT, pirates: Activity.FEW,
                        traders: Activity.SWARMS, minTech: TechLevel.HI_TECH, hullUpgraded: true, imageIndex: 56, fuel: 777,
                        hull: 778, tribbles: 65535, cargo: [90, 91, 92, 93, 94, 95, 96, 97, 98, 99],
                        weapons: [
                                new Weapon(equipType: EquipmentType.WEAPON, price: 1, minTech: TechLevel.AGRICULTURAL,
                                        chance: 12, type: WeaponType.MILITARY_LASER, power: 11, disabling: true),
                                new Weapon(equipType: EquipmentType.WEAPON, price: 2, minTech: TechLevel.MEDIEVAL,
                                        chance: 120, type: WeaponType.PULSE_LASER, power: 120, disabling: false)
                        ],
                        shields: [
                                new Shield(equipType: EquipmentType.SHIELD, price: 1, minTech: TechLevel.AGRICULTURAL,
                                        chance: 12, type: ShieldType.ENERGY, power: 11),
                                new Shield(equipType: EquipmentType.SHIELD, price: 2, minTech: TechLevel.MEDIEVAL,
                                        chance: 120, type: ShieldType.REFLECTIVE, power: 120)
                        ],
                        gadgets: [
                                new Gadget(equipType: EquipmentType.GADGET, price: 1, minTech: TechLevel.AGRICULTURAL,
                                        chance: 12, type: GadgetType.CLOAKING_DEVICE, skillBonus: SkillType.PILOT),
                                new Gadget(equipType: EquipmentType.GADGET, price: 2, minTech: TechLevel.MEDIEVAL,
                                        chance: 120, type: GadgetType.NAVIGATING_SYSTEM, skillBonus: SkillType.FIGHTER)
                        ],
                        crewMembers: [new CrewMember(id: CrewMemberId.C2U2, skills: [0, 0, 0, 0], currentSystemId: StarSystemId.Guinifer)],
                        pod: true,
                        // The following does not need to be saved. It's more of a temp variable.
                        tradeableItems: [true, false, true, false, false],
                        escapePod: true
                ),

                opponentDisabled: true,
                chanceOfTradeInOrbit: 70,
                clicks: 7,
                raided: true,
                inspected: true,
                tribbleMessage: true,
                arrivedViaWormhole: true,
                paidForNewspaper: true,
                litterWarning: true,

                newsEvents: [NewsEvent.ArtifactDelivery, NewsEvent.CaptHuieAttacked],

                difficulty: Difficulty.HARD,

                autoSave: true,
                easyEncounters: true,
                endStatus: GameEndType.BOUGHT_MOON_GIRL,
                encounterType: EncounterType.MARIE_CELESTE_POLICE,
                selectedSystemId: StarSystemId.Antedi,
                warpSystemId: StarSystemId.NA,
                trackedSystemId: StarSystemId.Inthara,
                targetWormhole: true,
                priceCargoBuy: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
                priceCargoSell: [1, 2, 3, 4, 5, 6, 7, 8, 9, 0],
                questStatusArtifact: 1,
                questStatusDragonfly: 2,
                questStatusExperiment: 3,
                questStatusGemulon: 4,
                questStatusJapori: 5,
                questStatusJarek: 6,
                questStatusMoon: 7,
                questStatusPrincess: 8,
                questStatusReactor: 9,
                questStatusScarab: 10,
                questStatusSculpture: 11,
                questStatusSpaceMonster: 12,
                questStatusWild: 13,
                fabricRipProbability: 13,
                justLootedMarie: true,
                canSuperWarp: true,
                chanceOfVeryRareEncounter: 7,

                veryRareEncounters: [VeryRareEncounter.CAPTAIN_HUIE, VeryRareEncounter.BOTTLE_GOOD,
                                     VeryRareEncounter.BOTTLE_OLD, VeryRareEncounter.CAPTAIN_AHAB,
                                     VeryRareEncounter.CAPTAIN_CONRAD, VeryRareEncounter.MARIE_CELESTE],

                options: new GameOptions(alwaysIgnorePirates: true, alwaysIgnorePolice: true,
                        alwaysIgnoreTradeInOrbit: true, alwaysIgnoreTraders: true, autoFuel: true, autoRepair: true,
                        continuousAttack: true, continuousAttackFleeing: true, disableOpponents: true, newsAutoPay: true,
                        newsAutoShow: true, remindLoans: true, reserveMoney: true, showTrackedRange: true, trackAutoOff: true,
                        leaveEmpty: 12),

                // The rest of the member variables are not saved between games.
                parentWin: null,
                encounterContinueFleeing: true,
                encounterContinueAttacking: true,
                encounterCmdrFleeing: true,
                encounterCmdrHit: true,
                encounterOppFleeingPrev: true,
                encounterOppFleeing: true,
                encounterOppHit: true
        )

        Game.game = game;

        IOUtils.writeObjectToFile(fileName, game)

        when:
        Game savedGame = IOUtils.readObjectFromFile(fileName, false).get()

        then:
        game == savedGame

        cleanup:
        new File(fileName).delete()
    }

    def "write object to file"() {
    }

    def "ReadObjectFromFile"() {
    }

    def "GetRegistrySetting"() {
    }

    def "SetRegistrySetting"() {
    }

    def "WormholeExists"() {
    }

    def "WormholeExists1"() {
    }

    def "WormholeTarget"() {
    }
}
