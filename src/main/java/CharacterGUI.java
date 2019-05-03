import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterGUI extends JFrame {

    private JPanel mainPanel;
    private JTextField playerText;
    private JTextField characterText;
    private JTextField gameText;
    private JComboBox classCombo;
    private JComboBox raceCombo;
    private JComboBox alignmentCombo;
    private JComboBox levelCombo;
    private JCheckBox npc;
    private JTextArea backgroundArea;
    private JTextArea equipmentArea;
    private JTextArea spellsArea;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable characterTable;
    private JButton saveButton;
    private JButton clearButton;

    // fill the combo boxes
    private static final String[] CLASS_ARRAY = {"N/A", "Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk",
            "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard"};

    private static final String[] RACE_ARRAY = {"N/A", "Dragonborn", "Dwarf", "Elf", "Gnome", "Half Elf", "Half Orc",
            "Halfling", "Human", "Tiefling"};

    private static final String [] ALIGNMENT_ARRAY = {"N/A", "Lawful Good", "Neutral Good", "Chaotic Good",
            "Lawful Neutral", "Neutral", "Chaotic Neutral", "Lawful Evil", "Neutral Evil", "Chaotic Evil"};

    private static final String[] LEVEL_ARRAY = {"N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};


    CharacterGUI() {

        setContentPane(mainPanel);
        setTitle("Game Master Character List");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Player");
        tableModel.addColumn("Character");
        tableModel.addColumn("Game");

        fetchCombo();

        characterTable.setModel(tableModel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String playerName = playerText.getText();
                String characterName = characterText.getText();
                String gameName = gameText.getText();

                int classIndex = classCombo.getSelectedIndex();
                int raceIndex = raceCombo.getSelectedIndex();
                int alignmentIndex = alignmentCombo.getSelectedIndex();
                int levelIndex = levelCombo.getSelectedIndex();

                boolean npcCheck = npc.isSelected();    //redo

                String background = backgroundArea.getText();
                String equipment = equipmentArea.getText();
                String spells = spellsArea.getText();

                CharacterDB.addCharacter(playerName, characterName, gameName, classIndex, raceIndex,
                        alignmentIndex, levelIndex, npcCheck, background, equipment, spells);

                tableModel.addRow(new String[]{playerName, characterName, gameName});
                clearAll();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                clearAll();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        pack();
    }

    private void clearAll() {

        playerText.setText("");
        characterText.setText("");
        gameText.setText("");

        classCombo.setSelectedIndex(0);
        raceCombo.setSelectedIndex(0);
        alignmentCombo.setSelectedIndex(0);
        levelCombo.setSelectedIndex(0);

        npc.setSelected(false);

        backgroundArea.setText("");
        equipmentArea.setText("");
        spellsArea.setText("");

        characterTable.setRowSelectionInterval(0, 0);
    }

    private void fetchCombo() {

        for (String c : CLASS_ARRAY) {
            classCombo.addItem(c);
        }

        for (String r : RACE_ARRAY) {
            raceCombo.addItem(r);
        }

        for (String a : ALIGNMENT_ARRAY) {
            alignmentCombo.addItem(a);
        }

        for (String l : LEVEL_ARRAY) {
            levelCombo.addItem(l);
        }
    }
}