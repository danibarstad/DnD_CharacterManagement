import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

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
    private JButton clearButton;
    private JLabel playerNameLabel;
    private JLabel characterNameLabel;
    private JLabel gameNameLabel;
    private JButton deleteAllButton;
    private JButton addNewGameButton;
    private JComboBox gameName;

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

                clearAll();
            }
        });

        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (characterTable.getRowCount() < 1) {
                    showMessageDialog("The table is already empty",
                            "DELETE ALL ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    controller.deleteAllCharacter();

                    ArrayList<Character> allData = controller.getAllData();
                    setTableData(allData);

                    clearAll();
                }
            }
        });

        addNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GameGUI addNewGame = new GameGUI(CharacterGUI.this, controller);

                ArrayList<String> allGames = controller.getAllGames();
                setGameCombo(allGames);
            }
        });

        characterTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int cell = getCell();

                Character selection = controller.fillText(cell);

                playerText.setText(selection.getPlayerName());
                characterText.setText(selection.getCharacterName());
                gameText.setText(selection.getGameName());
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

    private boolean isChecked(String p, String c, String g) {

        if (isPresent(playerNameLabel.getText(), p)         &&
                isPresent(characterNameLabel.getText(), c)  &&
                isPresent(gameNameLabel.getText(), g)) {
            return true;
        }
        return false;
    }

    private boolean isPresent(String name, String text) {

        if (! text.equals("")) {
            return true;
        } else {
            showMessageDialog(name + " cannot be left blank.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private int getCell() {

        int selectedRow = characterTable.getSelectedRow();
        int column = 0;

        return Integer.parseInt(characterTable.getValueAt(selectedRow, column).toString());
    }

    private Character getCharacter() {

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

        if (isChecked(playerName, characterName, gameName)){
            return new Character(playerName, characterName, gameName, classIndex,
                    raceIndex, alignmentIndex, levelIndex, npcCheck, background, equipment, spells);
        }
        return null;
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

        if (characterTable.getRowCount() > 0) {
            characterTable.setRowSelectionInterval(0, 0);
        }
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

        for (String g : data) {
            gameName.addItem(g);
        }
    }
}
