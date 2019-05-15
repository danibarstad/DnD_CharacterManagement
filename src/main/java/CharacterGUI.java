import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class CharacterGUI extends JFrame {

    private JPanel mainPanel;
    private JTextField playerText;
    private JTextField characterText;
//    private JTextField gameText;
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
    private JButton clearButton;
    private JLabel playerNameLabel;
    private JLabel characterNameLabel;
    private JLabel gameNameLabel;
    private JButton deleteAllButton;
    private JButton addNewGameButton;
    private JComboBox gameCombo;

    // arrays used for combo boxes
    protected static final String[] CLASS_ARRAY = {"N/A", "Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk",
            "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard"};

    protected static final String[] RACE_ARRAY = {"N/A", "Dragonborn", "Dwarf", "Elf", "Gnome", "Half Elf", "Half Orc",
            "Halfling", "Human", "Tiefling"};

    protected static final String [] ALIGNMENT_ARRAY = {"N/A", "Lawful Good", "Neutral Good", "Chaotic Good",
            "Lawful Neutral", "Neutral", "Chaotic Neutral", "Lawful Evil", "Neutral Evil", "Chaotic Evil"};

    protected static final String[] LEVEL_ARRAY = {"N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};

    private Controller controller;


    CharacterGUI(Controller controller) {
        this.controller = controller;

        setContentPane(mainPanel);
        setTitle("Game Master Character List");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tabOrder(spellsArea);
        tabOrder(equipmentArea);
        tabOrder(backgroundArea);

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("ID");
        tableModel.addColumn("Player");
        tableModel.addColumn("Character");
        tableModel.addColumn("Game");

        fetchCombo();

        characterTable.setModel(tableModel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // add character data to database and displays it in the jtable
                Character characterRecord = getCharacter();

                if (characterRecord != null) {
                    controller.addToDatabase(characterRecord);

                    ArrayList<Character> allData = controller.getAllData();
                    setTableData(allData);

                    clearAll();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // updates selected character
                if (characterTable.getRowCount() > 0) {
                    int cell = getCell();

                    Character characterRecord = getCharacter();

                    controller.updateCharacter(cell, characterRecord);

                    ArrayList<Character> allData = controller.getAllData();
                    setTableData(allData);
                } else {
                    showMessageDialog("No characters to update.",
                            "UPDATE ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // deletes character from database and reconfigures jtable
                if (characterTable.getRowCount() > 0) {
                    int cell = getCell();

                    controller.deleteCharacter(cell);

                    ArrayList<Character> allData = controller.getAllData();
                    setTableData(allData);

                    clearAll();
                } else {
                    showMessageDialog("There is nothing to delete.",
                            "DELETION ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // clears all text fields and comboboxes
                clearAll();
            }
        });

        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // removes ALL characters and games from database
                // and clears all text fields, comboboxes, and jtable
                if (characterTable.getRowCount() < 1) {
                    showMessageDialog("The table is already empty",
                            "DELETE ALL ERROR", JOptionPane.ERROR_MESSAGE);
                } else {

                    // asks user to confirm before deleting all data
                    int input = showYesNoDialog("Are you sure? This will delete all existing data.",
                            "CAUTION", JOptionPane.YES_NO_OPTION);

                    if (input == 0) {
                        controller.deleteAllCharacter();
                        controller.deleteGames();

                        ArrayList<String> allGames = controller.getAllGames();
                        setGameCombo(allGames);

                        ArrayList<Character> allData = controller.getAllData();
                        setTableData(allData);

                        clearAll();
                    }
                }
            }
        });

        addNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // calls second form to add game to combobox
                GameGUI addNewGame = new GameGUI(CharacterGUI.this, controller);
            }
        });

        // fills text fields and comboboxes with data from selected character in jtable
        characterTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int cell = getCell();

                Character selection = controller.fillText(cell);

                playerText.setText(selection.getPlayerName());
                characterText.setText(selection.getCharacterName());
                gameCombo.setSelectedIndex(selection.getGameIndex());
                classCombo.setSelectedIndex(selection.getClassIndex());
                raceCombo.setSelectedIndex(selection.getRaceIndex());
                alignmentCombo.setSelectedIndex(selection.getAlignmentIndex());
                levelCombo.setSelectedIndex(selection.getLevelIndex());
                npc.setSelected(selection.isNpcCheck());
                backgroundArea.setText(selection.getBackground());
                equipmentArea.setText(selection.getEquipment());
                spellsArea.setText(selection.getSpells());
            }
        });

        pack();
    }

    private boolean isChecked(String p, String c, String g, int gi) {

        if (isPresent(playerNameLabel.getText(), p)         &&
                isPresent(characterNameLabel.getText(), c)  &&
                isPresent(gameNameLabel.getText(), g)       &&
                isSelected(gameNameLabel.getText(), gi)) {

            return true;
        }
        return false;
    }

    private boolean isSelected(String name, int sel) {

        // checks that a selection for game name was made
        if (sel != -1) {
            return true;
        } else {
            showMessageDialog("You must make a selection for " + name,
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean isPresent(String name, String text) {

        // checks that player and character name were not left blank
        if (! text.equals("")) {
            return true;
        } else {
            showMessageDialog(name + " cannot be left blank.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private int getCell() {

        // gets index for selected row
        int selectedRow = characterTable.getSelectedRow();
        int column = 0;

        return Integer.parseInt(characterTable.getValueAt(selectedRow, column).toString());
    }

    private Character getCharacter() {

        // creates Character object
        String playerName = playerText.getText();
        String characterName = characterText.getText();
        String gameNameString = (String) gameCombo.getSelectedItem();

        int gameName = gameCombo.getSelectedIndex();
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

        if (isChecked(playerName, characterName, gameNameString, gameName)){
            return new Character(playerName, characterName, gameNameString, gameName, classIndex,
                    raceIndex, alignmentIndex, levelIndex, npcCheck, background, equipment, spells);
        }
        return null;
    }

    private void clearAll() {

        // clears all text fields and comboboxes
        playerText.setText("");
        characterText.setText("");

        classCombo.setSelectedIndex(0);
        raceCombo.setSelectedIndex(0);
        alignmentCombo.setSelectedIndex(0);
        levelCombo.setSelectedIndex(0);

        npc.setSelected(false);

        backgroundArea.setText("");
        equipmentArea.setText("");
        spellsArea.setText("");

        if (characterTable.getRowCount() > 0) {
            characterTable.setRowSelectionInterval(0, 0);
        }
    }

    private void fetchCombo() {

        // fills combo boxes using global arrays
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

    private int showYesNoDialog(String message, String title, int type) {

        int i = JOptionPane.showConfirmDialog(this, message, title, type);
        return i;
    }

    protected void showMessageDialog(String message, String title, int type) {

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

    void setTableData(ArrayList<Character> data) {

        // builds jtable with data from database
        DefaultTableModel tableModel = (DefaultTableModel) characterTable.getModel();
        tableModel.setRowCount(0);

        if (data != null) {
            for (Character character : data) {
                tableModel.addRow(new String[]{Integer.toString(character.getId()), character.getPlayerName(),
                        character.getCharacterName(), character.getGameName()});
            }
        }
    }

    void setGameCombo(ArrayList<String> data) {

        // fills game name combobox with data from database
        gameCombo.removeAllItems();

        for (String g : data) {
            gameCombo.addItem(g);
        }
    }
}
