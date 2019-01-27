package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Graphics;
import spacetrader.controls.Label;
import spacetrader.controls.enums.BorderStyle;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Encounter;
import spacetrader.game.Game;
import spacetrader.game.Ship;
import spacetrader.game.enums.Buttons;
import spacetrader.game.enums.EncounterResult;
import spacetrader.game.enums.EncounterType;
import spacetrader.game.quest.quests.TribblesQuest;
import spacetrader.game.quest.enums.QuestName;
import spacetrader.guifacade.Facaded;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;

@Facaded
public class FormEncounter extends SpaceTraderForm {

    private final Game game = Game.getCurrentGame();
    private final Encounter encounter = game.getEncounter();
    private final Ship commanderShip = game.getShip();
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

    private PictureBox continuousPicture = new PictureBox();
    private ImageList continuousImageList = new ImageList();
    private PictureBox encounterTypePicture = new PictureBox();
    private ImageList encounterTypeImageList = new ImageList();
    private ImageList tribblesImageList = new ImageList();

    private Timer timer = new Timer();
    
    private int continueImage = 1;

    private EncounterResult result = EncounterResult.CONTINUE;

    public FormEncounter() {
        initializeComponent();

        // Set up the Game encounter variables.
        encounter.encounterBegin();

        // Enable the control box (the X button) if cheats are enabled.
        if (encounter.isEasyEncounters()) {
            setControlBox(true);
        }

        updateShipInfo();
        updateButtons();

        if (encounter.getEncounterImageIndex() >= 0) {
            encounterTypePicture.setImage(encounterTypeImageList.getImages()[encounter.getEncounterImageIndex()]);
        } else {
            encounterTypePicture.setVisible(false);
        }

        encounterLabelValue.setText(encounter.getEncounterTextInitial());
        actionLabelValue.setText(encounter.getEncounterActionInitial());
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
        //opponentsShipLabelValue.setText("Space Mon-ster");

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

        List<PictureBox> tribbles = ((TribblesQuest) game.getQuestSystem().getQuest(QuestName.Tribbles)).getCoordinates().stream()
                .map(c -> {
                    PictureBox pictureBox = new PictureBox();
                    pictureBox.setLocation(c.x, c.y); //TODO scale
                    pictureBox.setBackground(SystemColors.CONTROL);
                    pictureBox.setSize(12, 12); //TODO scale
                    pictureBox.setTabStop(false);
                    pictureBox.setImage(tribblesImageList.getImages()[Functions.getRandom(tribblesImageList.getImages().length)]);
                    pictureBox.setClick(new EventHandler<Object, EventArgs>() {
                        @Override
                        public void handle(Object sender, EventArgs e) {
                            tribblePictureClick();
                        }
                    });
                    return pictureBox;
                }).collect(Collectors.toList());

        controls.addAll(tribbles);
        controls.addAll(encounterTypePicture, youLabel, opponentLabel, yourShipPicture, opponentsShipPicture,
                yourShipLabelValue, opponentsShipLabelValue, yourHullLabelValue, opponentsHullLabelValue,
                yourShieldsLabelValue, opponentsShieldsLabelValue, encounterLabelValue, actionLabelValue,
                attackButton, fleeButton, submitButton, bribeButton, surrenderButton, ignoreButton, tradeButton,
                plunderButton, boardButton, meetButton, drinkButton, interruptButton, yieldButton, continuousPicture);

        ReflectionUtils.loadControlsData(this);
    }

    private void disableAuto() {
        timer.stop();

        encounter.setEncounterContinueFleeing(false);
        encounter.setEncounterContinueAttacking(false);
        interruptButton.setVisible(false);
        continuousPicture.setVisible(false);
    }

    private void executeAction() {
        if ((result = encounter.getEncounterExecuteAction()) == EncounterResult.CONTINUE) {
            updateButtons();
            updateShipStats();

            encounterLabelValue.setText(encounter.getEncounterText());
            actionLabelValue.setText(encounter.getEncounterAction());

            if (encounter.getEncounterContinueFleeing() || encounter.getEncounterContinueAttacking()) {
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
        List<Boolean> visible = Arrays.stream(buttons).map(b -> false).collect(Collectors.toList());

        if (encounter.getEncounterType() < 1000) {
            switch (EncounterType.fromInt(encounter.getEncounterType())) {
                case TRADER_ATTACK:
                    visible.set(Buttons.ATTACK.ordinal(), true);
                    visible.set(Buttons.FLEE.ordinal(), true);
                    break;
                case POLICE_DISABLED:
                case POLICE_FLEE:
                case POLICE_IGNORE:
                case PIRATE_FLEE:
                case PIRATE_IGNORE:
                case TRADER_FLEE:
                case TRADER_IGNORE:
                    visible.set(Buttons.ATTACK.ordinal(), true);
                    visible.set(Buttons.IGNORE.ordinal(), true);
                    break;
                case PIRATE_ATTACK:
                case POLICE_ATTACK:
                case POLICE_SURRENDER:
                    visible.set(Buttons.ATTACK.ordinal(), true);
                    visible.set(Buttons.FLEE.ordinal(), true);
                    visible.set(Buttons.SURRENDER.ordinal(), true);
                    break;
                case PIRATE_DISABLED:
                case PIRATE_SURRENDER:
                case TRADER_DISABLED:
                case TRADER_SURRENDER:
                    visible.set(Buttons.ATTACK.ordinal(), true);
                    visible.set(Buttons.PLUNDER.ordinal(), true);
                    break;
                case POLICE_INSPECT:
                    visible.set(Buttons.ATTACK.ordinal(), true);
                    visible.set(Buttons.FLEE.ordinal(), true);
                    visible.set(Buttons.SUBMIT.ordinal(), true);
                    visible.set(Buttons.BRIBE.ordinal(), true);
                    break;
                case TRADER_BUY:
                case TRADER_SELL:
                    visible.set(Buttons.ATTACK.ordinal(), true);
                    visible.set(Buttons.IGNORE.ordinal(), true);
                    visible.set(Buttons.TRADE.ordinal(), true);
                    break;
            }
        } else {
            game.getQuestSystem().fireEvent(ENCOUNTER_SHOW_ACTION_BUTTONS, visible);
        }

        if (encounter.getEncounterContinueAttacking() || encounter.getEncounterContinueFleeing()) {
            visible.set(Buttons.INT.ordinal(), true);
        }

        int left = attackButton.getLeft();

        for (int i = 0; i < visible.size(); i++) {
            if (visible.get(i)) {
                buttons[i].setLeft(left);
                //TODO scale
                left += buttons[i].getWidth() + 4;
            }
            buttons[i].setVisible(visible.get(i));
        }

        continuousPicture.setVisible(visible.get(Buttons.INT.ordinal()));

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

    private void attackButtonClick() {
        disableAuto();

        if (encounter.isEncounterVerifyAttack()) {
            executeAction();
        }
    }

    private void boardButtonClick() {
        if (encounter.isEncounterVerifyBoard()) {
            exit(EncounterResult.NORMAL);
        }
    }

    private void bribeButtonClick() {
        if (encounter.isEncounterVerifyBribe()) {
            exit(EncounterResult.NORMAL);
        }
    }

    private void drinkButtonClick() {
        encounter.encounterDrink();

        exit(EncounterResult.NORMAL);
    }

    private void fleeButtonClick() {
        disableAuto();

        if (encounter.isEncounterVerifyFlee()) {
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
        encounter.encounterMeet();

        exit(EncounterResult.NORMAL);
    }

    private void plunderButtonClick() {
        disableAuto();

        encounter.encounterPlunder();

        exit(EncounterResult.NORMAL);
    }

    private void submitButtonClick() {
        if (encounter.isEncounterVerifySubmit()) {
            exit(commanderShip.isIllegalSpecialCargo() ? EncounterResult.ARRESTED : EncounterResult.NORMAL);
        }
    }

    private void surrenderButtonClick() {
        disableAuto();

        if ((result = encounter.getEncounterVerifySurrender()) != EncounterResult.CONTINUE) {
            close();
        }
    }

    private void tradeButtonClick() {
        encounter.encounterTrade();

        exit(EncounterResult.NORMAL);
    }

    private void yieldButtonClick() {
        if ((result = encounter.getEncounterVerifyYield()) != EncounterResult.CONTINUE) {
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
        game.getQuestSystem().fireEvent(ENCOUNTER_ON_TRIBBLE_PICTURE_CLICK);
    }

    private void timerTick() {
        disableAuto();

        executeAction();
    }

    public EncounterResult getResult() {
        return result;
    }
}
