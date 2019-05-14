import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameGUI extends JFrame{
    private JPanel mainPanel;
    private JTextField newGame;
    private JButton addButton;
    private JButton cancelButton;

    private CharacterGUI characterGUI;
    private Controller controller;

    GameGUI(CharacterGUI characterGUI, Controller controller) {
        this.characterGUI = characterGUI;
        this.controller = controller;

        setContentPane(mainPanel);
        setTitle("New Game");
        setVisible(true);
        characterGUI.setEnabled(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (! newGame.getText().equals("")) {

                    String gameName = newGame.getText();

                    controller.addGame(gameName);

                    ArrayList<String> allGames = controller.getAllGames();
                    characterGUI.setGameCombo(allGames);

                    characterGUI.setEnabled(true);
                    GameGUI.this.dispose();

                } else {

                    characterGUI.showMessageDialog("You must enter a game name.",
                            "GAME NAME ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                characterGUI.setEnabled(true);
                GameGUI.this.dispose();
            }
        });
    }
}
