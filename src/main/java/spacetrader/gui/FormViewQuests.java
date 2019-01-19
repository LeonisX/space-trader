package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Game;
import spacetrader.game.Strings;
import spacetrader.game.quest.enums.EventName;
import spacetrader.stub.ArrayList;
import spacetrader.util.ReflectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FormViewQuests extends SpaceTraderForm {

    Game game = Game.getCurrentGame();

    private Button closeButton = new Button();
    private SimpleVPanel questsPanel = new SimpleVPanel();

    public FormViewQuests() {
        initializeComponent();
        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formViewQuests");
        setText("Quests");
        setClientSize(450, 160);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        questsPanel.setLocation(8, 8);
        questsPanel.setSize(440, 160);

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(questsPanel, closeButton);

        ReflectionUtils.loadControlsData(this);
    }

    private void updateAll() {
        questsPanel.asJPanel().removeAll();
        List<String> quests = getQuestStrings();
        List<String> systemNames = Arrays.asList(Strings.SystemNames);

        if (quests.isEmpty()) {
            Label label = new Label(Strings.QuestNone);
            label.setAutoSize(true);
            questsPanel.asJPanel().add(label.asSwingObject());
        } else {
            quests.forEach(quest -> {
                Label label = new Label("  ");
                label.setSize(100, 7);
                questsPanel.asJPanel().add(label.asSwingObject());


                Optional<String> systemName = systemNames.stream().filter(quest::contains).findFirst();
                if (systemName.isPresent()) {
                    LinkLabel linkLabel = new LinkLabel(quest);
                    linkLabel.setAutoSize(true);
                    linkLabel.setLinkClicked(new SimpleEventHandler<Object>() {
                        public void handle(Object sender) {
                            questsLabelValueClicked(systemName.get());
                        }
                    });
                    questsPanel.asJPanel().add(linkLabel.asSwingObject());
                } else {
                    label = new Label(quest);
                    label.setAutoSize(true);
                    questsPanel.asJPanel().add(label.asSwingObject());
                    System.out.println("Can't find system for quest: " + quest);
                }
            });
        }
    }

    private List<String> getQuestStrings() {
        List<String> quests = new ArrayList<>();
        game.getQuestSystem().fireEvent(EventName.ON_GET_QUESTS_STRINGS, quests);
        return quests;
    }

    private void questsLabelValueClicked(String systemName) {
        game.setSelectedSystemByName(systemName);
        game.getParentWindow().updateAll();
        close();
    }
}
