import javax.swing.*;

public class ReportGUI extends JFrame{
    private JPanel mainPanel;
    private JCheckBox npcCheck;
    private JTextArea backgroundArea;
    private JTextArea equipemtTextArea;
    private JTextArea spellsArea;
    private JLabel playerLabel;
    private JLabel characterLabel;
    private JLabel gameLabel;
    private JLabel classLabel;
    private JLabel raceLabel;
    private JLabel alignmentLabel;
    private JLabel levelLabel;

    ReportGUI(Character character) {

        setContentPane(mainPanel);
        setTitle("Character Report");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();

        playerLabel.setText("Player: " + character.getPlayerName());
        characterLabel.setText("Character: " + character.getCharacterName());
        gameLabel.setText("Game: " + character.getGameName());
        classLabel.setText("Class: " + CharacterGUI.CLASS_ARRAY[character.getClassIndex()]);
        raceLabel.setText("Race: " + CharacterGUI.RACE_ARRAY[character.getRaceIndex()]);
        alignmentLabel.setText("Alignment: " + CharacterGUI.ALIGNMENT_ARRAY[character.getAlignmentIndex()]);
        levelLabel.setText("Level: " + CharacterGUI.LEVEL_ARRAY[character.getLevelIndex()]);
        npcCheck.setSelected(character.isNpcCheck());
        backgroundArea.setText(character.getBackground());
        equipemtTextArea.setText(character.getEquipment());
        spellsArea.setText(character.getSpells());
    }
}
