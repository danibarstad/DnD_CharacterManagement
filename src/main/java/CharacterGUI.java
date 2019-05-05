import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
    private JLabel playerNameLabel;
    private JLabel characterNameLabel;
    private JLabel gameNameLabel;

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
        tabOrder(spellsArea);
        tabOrder(equipmentArea);
        tabOrder(backgroundArea);

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

                boolean npcCheck;
                if (npc.isSelected()){
                    npcCheck = true;
                } else {
                    npcCheck = false;
                }

                String background = backgroundArea.getText();
                String equipment = equipmentArea.getText();
                String spells = spellsArea.getText();

                if (isPresent(playerNameLabel.getText(), playerName) &&
                        isPresent(characterNameLabel.getText(), characterName) &&
                        isPresent(gameNameLabel.getText(), gameName)){

                    CharacterDB.addCharacter(playerName, characterName, gameName, classIndex, raceIndex,
                            alignmentIndex, levelIndex, npcCheck, background, equipment, spells);

                    tableModel.addRow(new String[]{playerName, characterName, gameName});
                    clearAll();
                }
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

     private boolean isPresent(String name, String text) {

        if (! text.equals("")) {
            return true;
        } else {
            showMessageDialog(name + " cannot be left blank.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
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

    private void showMessageDialog(String message, String title, int type) {

        JOptionPane.showMessageDialog(this, message, title, type);
    }

    private void tabOrder(JTextArea area) {

        // code found on StackOverflow
        // sets "Tab" button for JTextArea to move to focus to next area,
        // instead of adding "\t" to the text
        area.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    if (e.getModifiers() > 0) {
                        area.transferFocusBackward();
                    } else {
                        area.transferFocus();
                    }
                    e.consume();
                }
            }
        });
    }
}
// hey
