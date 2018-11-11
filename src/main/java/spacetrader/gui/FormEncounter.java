package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.Container;
import spacetrader.controls.*;
import spacetrader.controls.Graphics;
import spacetrader.controls.Label;
import spacetrader.controls.enums.*;
import spacetrader.game.Consts;
import spacetrader.util.Functions;
import spacetrader.game.Game;
import spacetrader.game.Ship;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.EncounterResult;
import spacetrader.guifacade.Facaded;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.ReflectionUtils;

import java.awt.*;
import java.util.Arrays;

import static java.lang.Math.*;

@Facaded
public class FormEncounter extends SpaceTraderForm {

    private static final int ATTACK = 0;
    private static final int BOARD = 1;
    private static final int DRINK = 2;
    private static final int FLEE = 3;
    private static final int IGNORE = 4;
    private static final int PLUNDER = 5;
    private static final int MEET = 6;
    private static final int SUBMIT = 7;
    private static final int SURRENDER = 8;
    private static final int TRADE = 9;
    private static final int YIELD = 10;
    private static final int BRIBE = 11;
    private static final int INT = 12;

    private final Game game = Game.getCurrentGame();
    private final Ship commanderShip = game.getCommander().getShip();
    private final Ship opponent = game.getOpponent();

    private Button attackButton = new Button();
    private Button fleeButton = new Button();
    private Button submitButton = new Button();
    private Button bribeButton = new Button();
    private Button surrenderButton = new Button();
    private Button ignoreButton = new Button();
    private Button tradeButton = new Button();
    private Button plunderButton = new Button();
    private Button boardButton = new Button();
    private Button meetButton = new Button();
    private Button drinkButton = new Button();
    private Button interruptButton = new Button();
    private Button yieldButton = new Button();

    private final Button[] buttons = new Button[]{attackButton, boardButton, drinkButton,
            fleeButton, ignoreButton, plunderButton, meetButton, submitButton, surrenderButton,
            tradeButton, yieldButton, bribeButton, interruptButton};

    private Label encounterLabelValue = new Label();
    private PictureBox yourShipPicture = new PictureBox();
    private PictureBox opponentsShipPicture = new PictureBox();
    private Label opponentLabel = new Label();
    private Label youLabel = new Label();
    private Label opponentsShipLabelValue = new Label();
    private Label yourShipLabelValue = new Label();
    private Label yourHullLabelValue = new Label();
    private Label yourShieldsLabelValue = new Label();
    private Label opponentsShieldsLabelValue = new Label();
    private Label opponentsHullLabelValue = new Label();
    private Label actionLabelValue = new Label();

    //TODO need?
    private IContainer components = new Container();
    
    private PictureBox continuousPicture = new PictureBox();
    private ImageList continuousImageList = new ImageList(components);
    private PictureBox encounterTypePicture = new PictureBox();
    private ImageList encounterTypeImageList = new ImageList(components);
    private ImageList tribblesImageList = new ImageList(components);

    //TODO refactor, optimize, generate on fly.
    //TODO clean-up configs
    private PictureBox tribblePicture00 = new PictureBox();
    private PictureBox tribblePicture50 = new PictureBox();
    private PictureBox tribblePicture10 = new PictureBox();
    private PictureBox tribblePicture40 = new PictureBox();
    private PictureBox tribblePicture20 = new PictureBox();
    private PictureBox tribblePicture30 = new PictureBox();
    private PictureBox tribblePicture04 = new PictureBox();
    private PictureBox tribblePicture03 = new PictureBox();
    private PictureBox tribblePicture02 = new PictureBox();
    private PictureBox tribblePicture01 = new PictureBox();
    private PictureBox tribblePicture05 = new PictureBox();
    private PictureBox tribblePicture11 = new PictureBox();
    private PictureBox tribblePicture12 = new PictureBox();
    private PictureBox tribblePicture13 = new PictureBox();
    private PictureBox tribblePicture14 = new PictureBox();
    private PictureBox tribblePicture15 = new PictureBox();
    private PictureBox tribblePicture21 = new PictureBox();
    private PictureBox tribblePicture22 = new PictureBox();
    private PictureBox tribblePicture23 = new PictureBox();
    private PictureBox tribblePicture24 = new PictureBox();
    private PictureBox tribblePicture25 = new PictureBox();
    private PictureBox tribblePicture31 = new PictureBox();
    private PictureBox tribblePicture32 = new PictureBox();
    private PictureBox tribblePicture33 = new PictureBox();
    private PictureBox tribblePicture34 = new PictureBox();
    private PictureBox tribblePicture35 = new PictureBox();
    private PictureBox tribblePicture41 = new PictureBox();
    private PictureBox tribblePicture51 = new PictureBox();
    private PictureBox tribblePicture42 = new PictureBox();
    private PictureBox tribblePicture52 = new PictureBox();
    private PictureBox tribblePicture43 = new PictureBox();
    private PictureBox tribblePicture53 = new PictureBox();
    private PictureBox tribblePicture44 = new PictureBox();
    private PictureBox tribblePicture45 = new PictureBox();
    private PictureBox tribblePicture54 = new PictureBox();
    private PictureBox tribblePicture55 = new PictureBox();

    private PictureBox[] tribblesArray;

    private Timer timer = new Timer(components);
    
    private int continueImage = 1;

    private EncounterResult result = EncounterResult.CONTINUE;

    public FormEncounter() {
        initializeComponent();

        // Set up the Game encounter variables.
        game.encounterBegin();

        // Enable the control box (the X button) if cheats are enabled.
        if (game.isEasyEncounters()) {
            setControlBox(true);
        }

        updateShipInfo();
        updateTribbles();
        updateButtons();

        if (game.getEncounterImageIndex() >= 0) {
            encounterTypePicture.setImage(encounterTypeImageList.getImages()[game.getEncounterImageIndex()]);
        } else {
            encounterTypePicture.setVisible(false);
        }

        encounterLabelValue.setText(game.getEncounterTextInitial());
        actionLabelValue.setText(game.getEncounterActionInitial());
    }

    private void initializeComponent() {
        ResourceManager resources = new ResourceManager(FormEncounter.class);
        ReflectionUtils.setAllComponentNames(this);

        setName("formEncounter");
        setText("Encounter");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setControlBox(false);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(260, 290);

        encounterTypePicture.setLocation(220, 2);
        encounterTypePicture.setSize(12, 12);
        encounterTypePicture.setTabStop(false);

        youLabel.setAutoSize(true);
        youLabel.setFont(FontCollection.bold825);
        youLabel.setLocation(26, 8);
        //youLabel.setSize(28, 16);
        youLabel.setText("You:");

        opponentLabel.setAutoSize(true);
        opponentLabel.setFont(FontCollection.bold825);
        opponentLabel.setLocation(148, 8);
        //opponentLabel.setSize(59, 16);
        opponentLabel.setText("Opponent:");

        yourShipPicture.setBackground(Color.WHITE);
        yourShipPicture.setBorderStyle(BorderStyle.FIXED_SINGLE);
        yourShipPicture.setLocation(26, 24);
        yourShipPicture.setSize(70, 58);
        yourShipPicture.setTabStop(false);
        yourShipPicture.setPaint(new EventHandler<Object, PaintEventArgs>() {
            @Override
            public void handle(Object sender, PaintEventArgs e) {
                yourShipPicturePaint(e);
            }
        });

        opponentsShipPicture.setBackground(Color.WHITE);
        opponentsShipPicture.setBorderStyle(BorderStyle.FIXED_SINGLE);
        opponentsShipPicture.setLocation(148, 24);
        opponentsShipPicture.setSize(70, 58);
        opponentsShipPicture.setTabStop(false);
        opponentsShipPicture.setPaint(new EventHandler<Object, PaintEventArgs>() {
            @Override
            public void handle(Object sender, PaintEventArgs e) {
                opponentsShipPicturePaint(e);
            }
        });

        yourShipLabelValue.setAutoSize(true);
        yourShipLabelValue.setControlBinding(ControlBinding.LEFT);
        yourShipLabelValue.setLocation(26, 88);
        //yourShipLabelValue.setSize(100, 13);
        //yourShipLabelValue.setText("Grasshopper");

        opponentsShipLabelValue.setAutoSize(true);
        opponentsShipLabelValue.setControlBinding(ControlBinding.LEFT);
        opponentsShipLabelValue.setLocation(148, 88);
        //opponentsShipLabelValue.setSize(80, 13);
        //opponentsShipLabelValue.setText("Space Monster");

        yourHullLabelValue.setAutoSize(true);
        yourHullLabelValue.setControlBinding(ControlBinding.LEFT);
        yourHullLabelValue.setLocation(26, 104);
        //yourHullLabelValue.setSize(68, 13);
        //yourHullLabelValue.setText("Hull at 100%");

        opponentsHullLabelValue.setAutoSize(true);
        opponentsHullLabelValue.setControlBinding(ControlBinding.LEFT);
        opponentsHullLabelValue.setLocation(148, 104);
        //opponentsHullLabelValue.setSize(68, 13);
        //opponentsHullLabelValue.setText("Hull at 100%");

        yourShieldsLabelValue.setAutoSize(true);
        yourShieldsLabelValue.setControlBinding(ControlBinding.LEFT);
        yourShieldsLabelValue.setLocation(26, 120);
        //yourShieldsLabelValue.setSize(86, 13);
        //yourShieldsLabelValue.setText("Shields at 100%");

        opponentsShieldsLabelValue.setAutoSize(true);
        opponentsShieldsLabelValue.setControlBinding(ControlBinding.LEFT);
        opponentsShieldsLabelValue.setLocation(148, 120);
        //opponentsShieldsLabelValue.setSize(86, 13);
        //opponentsShieldsLabelValue.setText("Shields at 100%");

        encounterLabelValue.setLocation(8, 152);
        encounterLabelValue.setSize(245, 39);
        //encounterLabelValue.setText("At 20 clicks from Tarchannen, you encounter the famous Captain Ahab.");

        actionLabelValue.setLocation(8, 197);
        actionLabelValue.setSize(245, 39);
        //actionLabelValue.setText("\"We know you removed illegal goods from the Marie Celeste. You must give them up at once!\"");

        attackButton.setAutoWidth(true);
        attackButton.setLocation(8, 245);
        attackButton.setSize(46, 22);
        attackButton.setTabIndex(24);
        attackButton.setText("Attack");
        //attackButton.setVisible(false);
        attackButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                attackButtonClick();
            }
        });

        fleeButton.setAutoWidth(true);
        fleeButton.setLocation(62, 245);
        fleeButton.setSize(36, 22);
        fleeButton.setTabIndex(25);
        fleeButton.setText("Flee");
        //fleeButton.setVisible(false);
        fleeButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                fleeButtonClick();
            }
        });

        submitButton.setAutoWidth(true);
        submitButton.setLocation(106, 245);
        submitButton.setSize(49, 22);
        submitButton.setTabIndex(26);
        submitButton.setText("Submit");
        //submitButton.setVisible(false);
        submitButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                submitButtonClick();
            }
        });

        bribeButton.setAutoWidth(true);
        bribeButton.setLocation(163, 245);
        bribeButton.setSize(41, 22);
        bribeButton.setTabIndex(27);
        bribeButton.setText("Bribe");
        //bribeButton.setVisible(false);
        bribeButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                bribeButtonClick();
            }
        });

        surrenderButton.setAutoWidth(true);
        surrenderButton.setLocation(106, 245);
        surrenderButton.setSize(65, 22);
        surrenderButton.setTabIndex(28);
        surrenderButton.setText("Surrender");
        //surrenderButton.setVisible(false);
        surrenderButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                surrenderButtonClick();
            }
        });

        ignoreButton.setAutoWidth(true);
        ignoreButton.setLocation(62, 245);
        ignoreButton.setSize(46, 22);
        ignoreButton.setTabIndex(29);
        ignoreButton.setText("Ignore");
        //ignoreButton.setVisible(false);
        ignoreButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                ignoreButtonClick();
            }
        });

        tradeButton.setAutoWidth(true);
        tradeButton.setLocation(116, 245);
        tradeButton.setSize(44, 22);
        tradeButton.setTabIndex(30);
        tradeButton.setText("Trade");
        //tradeButton.setVisible(false);
        tradeButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                tradeButtonClick();
            }
        });

        plunderButton.setAutoWidth(true);
        plunderButton.setLocation(62, 245);
        plunderButton.setSize(53, 22);
        plunderButton.setTabIndex(31);
        plunderButton.setText("Plunder");
        //plunderButton.setVisible(false);
        plunderButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                plunderButtonClick();
            }
        });

        boardButton.setAutoWidth(true);
        boardButton.setLocation(8, 245);
        boardButton.setSize(44, 22);
        boardButton.setTabIndex(32);
        boardButton.setText("Board");
        //boardButton.setVisible(false);
        boardButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                boardButtonClick();
            }
        });

        meetButton.setAutoWidth(true);
        meetButton.setLocation(116, 245);
        meetButton.setSize(39, 22);
        meetButton.setTabIndex(34);
        meetButton.setText("Meet");
        //meetButton.setVisible(false);
        meetButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                meetButtonClick();
            }
        });

        drinkButton.setAutoWidth(true);
        drinkButton.setLocation(8, 245);
        drinkButton.setSize(41, 22);
        drinkButton.setTabIndex(35);
        drinkButton.setText("Drink");
        //drinkButton.setVisible(false);
        drinkButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                drinkButtonClick();
            }
        });

        interruptButton.setAutoWidth(true);
        interruptButton.setLocation(179, 245);
        interruptButton.setSize(30, 22);
        interruptButton.setTabIndex(36);
        interruptButton.setText("Int.");
        //interruptButton.setVisible(false);
        interruptButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                interruptButtonClick();
            }
        });

        yieldButton.setAutoWidth(true);
        yieldButton.setLocation(106, 245);
        yieldButton.setSize(39, 22);
        yieldButton.setTabIndex(37);
        yieldButton.setText("Yield");
        //yieldButton.setVisible(false);
        yieldButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                yieldButtonClick();
            }
        });

        continuousPicture.setLocation(230, 252);
        continuousPicture.setSize(9, 9);
        continuousPicture.setTabIndex(38);
        continuousPicture.setTabStop(false);
        continuousPicture.setVisible(false);

        tribblePicture00.setLocation(16, 16);
        tribblePicture01.setLocation(56, 8);
        tribblePicture02.setLocation(96, 16);
        tribblePicture03.setLocation(128, 8);
        tribblePicture04.setLocation(176, 8);
        tribblePicture05.setLocation(208, 16);
        tribblePicture10.setLocation(8, 56);
        tribblePicture11.setLocation(32, 80);
        tribblePicture12.setLocation(88, 56);
        tribblePicture13.setLocation(128, 40);
        tribblePicture14.setLocation(192, 72);
        tribblePicture15.setLocation(216, 48);
        tribblePicture20.setLocation(8, 96);
        tribblePicture21.setLocation(56, 96);
        tribblePicture22.setLocation(96, 80);
        tribblePicture23.setLocation(136, 88);
        tribblePicture24.setLocation(176, 104);
        tribblePicture25.setLocation(216, 96);
        tribblePicture30.setLocation(16, 136);
        tribblePicture31.setLocation(56, 128);
        tribblePicture32.setLocation(96, 120);
        tribblePicture33.setLocation(128, 128);
        tribblePicture34.setLocation(168, 144);
        tribblePicture35.setLocation(208, 128);
        tribblePicture40.setLocation(8, 184);
        tribblePicture41.setLocation(48, 176);
        tribblePicture42.setLocation(88, 168);
        tribblePicture43.setLocation(136, 176);
        tribblePicture44.setLocation(184, 184);
        tribblePicture45.setLocation(216, 176);
        tribblePicture50.setLocation(16, 224);
        tribblePicture51.setLocation(64, 216);
        tribblePicture52.setLocation(96, 224);
        tribblePicture53.setLocation(144, 216);
        tribblePicture54.setLocation(176, 224);
        tribblePicture55.setLocation(208, 216);

        tribblesArray = new PictureBox[]{
                tribblePicture00, tribblePicture01, tribblePicture02, tribblePicture03, tribblePicture04, tribblePicture05, 
                tribblePicture10, tribblePicture11, tribblePicture12, tribblePicture13, tribblePicture14, tribblePicture15,
                tribblePicture20, tribblePicture21, tribblePicture22, tribblePicture23, tribblePicture24, tribblePicture25, 
                tribblePicture30, tribblePicture31, tribblePicture32, tribblePicture33, tribblePicture34, tribblePicture35, 
                tribblePicture40, tribblePicture41, tribblePicture42, tribblePicture43, tribblePicture44, tribblePicture45, 
                tribblePicture50, tribblePicture51, tribblePicture52, tribblePicture53, tribblePicture54, tribblePicture55
        };

        Arrays.stream(tribblesArray).forEach(picture -> {
            picture.setBackground(SystemColors.CONTROL);
            picture.setSize(12, 12);
            picture.setTabStop(false);
            picture.setVisible(false);
            picture.setClick(new EventHandler<Object, EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs e) {
                    tribblePictureClick();
                }
            });
        });

        timer.setInterval(1000);
        timer.setTick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                timerTick();
            }
        });

        encounterTypeImageList.setImageSize(12, 12);
        encounterTypeImageList.setImageStream(((ImageListStreamer) (resources.getObject("ilEncounterType.ImageStream"))));
        encounterTypeImageList.setTransparentColor(Color.WHITE);

        tribblesImageList.setImageSize(12, 12);
        tribblesImageList.setImageStream(((ImageListStreamer) (resources.getObject("ilTribbles.ImageStream"))));
        tribblesImageList.setTransparentColor(Color.WHITE);

        continuousImageList.setImageSize(9, 9);
        continuousImageList.setImageStream(((ImageListStreamer) (resources.getObject("ilContinuous.ImageStream"))));
        continuousImageList.setTransparentColor(Color.WHITE);

        controls.addAll(encounterTypePicture, youLabel, opponentLabel, yourShipPicture, opponentsShipPicture,
                yourShipLabelValue, opponentsShipLabelValue, yourHullLabelValue, opponentsHullLabelValue,
                yourShieldsLabelValue, opponentsShieldsLabelValue, encounterLabelValue, actionLabelValue,
                attackButton, fleeButton, submitButton, bribeButton, surrenderButton, ignoreButton, tradeButton,
                plunderButton, boardButton, meetButton, drinkButton, interruptButton, yieldButton, continuousPicture);
        controls.addAll(tribblesArray);

        ReflectionUtils.loadControlsData(this);
    }

    private void disableAuto() {
        timer.stop();

        game.setEncounterContinueFleeing(false);
        game.setEncounterContinueAttacking(false);
        interruptButton.setVisible(false);
        continuousPicture.setVisible(false);
    }

    private void executeAction() {
        if ((result = game.getEncounterExecuteAction()) == EncounterResult.CONTINUE) {
            updateButtons();
            updateShipStats();

            encounterLabelValue.setText(game.getEncounterText());
            actionLabelValue.setText(game.getEncounterAction());

            if (game.getEncounterContinueFleeing() || game.getEncounterContinueAttacking()) {
                timer.start();
            }
        } else {
            close();
        }
    }

    private void exit(EncounterResult result) {
        this.result = result;
        close();
    }

    private void updateButtons() {
        boolean[] visible = new boolean[buttons.length];

        switch (game.getEncounterType()) {
            case BOTTLE_GOOD:
            case BOTTLE_OLD:
                visible[DRINK] = true;
                visible[IGNORE] = true;
                break;
            case CAPTAIN_AHAB:
            case CAPTAIN_CONRAD:
            case CAPTAIN_HUIE:
                visible[ATTACK] = true;
                visible[IGNORE] = true;
                visible[MEET] = true;
                break;
            case DRAGONFLY_ATTACK:
            case FAMOUS_CAPTAIN_ATTACK:
            case SCORPION_ATTACK:
            case SPACE_MONSTER_ATTACK:
            case TRADER_ATTACK:
                visible[ATTACK] = true;
                visible[FLEE] = true;
                break;
            case DRAGONFLY_IGNORE:
            case FAMOUS_CAPT_DISABLED:
            case POLICE_DISABLED:
            case POLICE_FLEE:
            case POLICE_IGNORE:
            case PIRATE_FLEE:
            case PIRATE_IGNORE:
            case SCARAB_IGNORE:
            case SCORPION_IGNORE:
            case SPACE_MONSTER_IGNORE:
            case TRADER_FLEE:
            case TRADER_IGNORE:
                visible[ATTACK] = true;
                visible[IGNORE] = true;
                break;
            case MARIE_CELESTE:
                visible[BOARD] = true;
                visible[IGNORE] = true;
                break;
            case MARIE_CELESTE_POLICE:
                visible[ATTACK] = true;
                visible[FLEE] = true;
                visible[YIELD] = true;
                visible[BRIBE] = true;
                break;
            case PIRATE_ATTACK:
            case POLICE_ATTACK:
            case POLICE_SURRENDER:
            case SCARAB_ATTACK:
                visible[ATTACK] = true;
                visible[FLEE] = true;
                visible[SURRENDER] = true;
                break;
            case PIRATE_DISABLED:
            case PIRATE_SURRENDER:
            case TRADER_DISABLED:
            case TRADER_SURRENDER:
                visible[ATTACK] = true;
                visible[PLUNDER] = true;
                break;
            case POLICE_INSPECT:
                visible[ATTACK] = true;
                visible[FLEE] = true;
                visible[SUBMIT] = true;
                visible[BRIBE] = true;
                break;
            case TRADER_BUY:
            case TRADER_SELL:
                visible[ATTACK] = true;
                visible[IGNORE] = true;
                visible[TRADE] = true;
                break;
        }

        if (game.getEncounterContinueAttacking() || game.getEncounterContinueFleeing()) {
            visible[INT] = true;
        }

        int left = attackButton.getLeft();

        for (int i = 0; i < visible.length; i++) {
            if (visible[i]) {
                buttons[i].setLeft(left);
                //TODO scale
                left += buttons[i].getWidth() + 4;
            }
            buttons[i].setVisible(visible[i]);
        }

        continuousPicture.setVisible(visible[INT]);

        if (continuousPicture.isVisible()) {
            continuousPicture.setLeft(left);
            continuousPicture.setImage(continuousImageList.getImages()[continueImage = (continueImage + 1) % 2]);
        }
    }

    private void updateShipInfo() {
        yourShipLabelValue.setText(commanderShip.getName());
        opponentsShipLabelValue.setText(opponent.getName());

        updateShipStats();
    }

    private void updateShipStats() {
        yourHullLabelValue.setText(commanderShip.getHullText());
        yourShieldsLabelValue.setText(commanderShip.getShieldText());
        opponentsHullLabelValue.setText(opponent.getHullText());
        opponentsShieldsLabelValue.setText(opponent.getShieldText());

        yourShipPicture.refresh();
        opponentsShipPicture.refresh();
    }

    private void updateTribbles() {
        int toShow = min(tribblesArray.length,
                (int) sqrt(commanderShip.getTribbles() / ceil(Consts.MaxTribbles / pow(tribblesArray.length + 1, 2))));

        for (int i = 0; i < toShow; i++) {
            int index = Functions.getRandom(tribblesArray.length);
            while (tribblesArray[index].isVisible()) {
                index = (index + 1) % tribblesArray.length;
            }
            tribblesArray[index].setImage(tribblesImageList.getImages()[Functions.getRandom(tribblesImageList.getImages().length)]);
            tribblesArray[index].setVisible(true);
        }
    }

    private void attackButtonClick() {
        disableAuto();

        if (game.isEncounterVerifyAttack()) {
            executeAction();
        }
    }

    private void boardButtonClick() {
        if (game.isEncounterVerifyBoard()) {
            exit(EncounterResult.NORMAL);
        }
    }

    private void bribeButtonClick() {
        if (game.isEncounterVerifyBribe()) {
            exit(EncounterResult.NORMAL);
        }
    }

    private void drinkButtonClick() {
        game.encounterDrink();

        exit(EncounterResult.NORMAL);
    }

    private void fleeButtonClick() {
        disableAuto();

        if (game.isEncounterVerifyFlee()) {
            executeAction();
        }
    }

    private void ignoreButtonClick() {
        disableAuto();

        exit(EncounterResult.NORMAL);
    }

    private void interruptButtonClick() {
        disableAuto();
    }

    private void meetButtonClick() {
        game.encounterMeet();

        exit(EncounterResult.NORMAL);
    }

    private void plunderButtonClick() {
        disableAuto();

        game.encounterPlunder();

        exit(EncounterResult.NORMAL);
    }

    private void submitButtonClick() {
        if (game.isEncounterVerifySubmit()) {
            exit(commanderShip.isIllegalSpecialCargo() ? EncounterResult.ARRESTED : EncounterResult.NORMAL);
        }
    }

    private void surrenderButtonClick() {
        disableAuto();

        if ((result = game.getEncounterVerifySurrender()) != EncounterResult.CONTINUE) {
            close();
        }
    }

    private void tradeButtonClick() {
        game.encounterTrade();

        exit(EncounterResult.NORMAL);
    }

    private void yieldButtonClick() {
        if ((result = game.getEncounterVerifyYield()) != EncounterResult.CONTINUE) {
            close();
        }
    }

    private void opponentsShipPicturePaint(PaintEventArgs e) {
        Graphics.paintShipImage(opponent, e.getGraphics(), opponentsShipPicture.getBackground());
    }

    private void yourShipPicturePaint(PaintEventArgs e) {
        Graphics.paintShipImage(commanderShip, e.getGraphics(), yourShipPicture.getBackground());
    }

    private void tribblePictureClick() {
        GuiFacade.alert(AlertType.TribblesSqueek);
    }

    private void timerTick() {
        disableAuto();

        executeAction();
    }

    public EncounterResult getResult() {
        return result;
    }
}
