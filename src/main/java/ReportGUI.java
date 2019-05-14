import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ReportGUI extends JFrame{
    private JPanel mainPanel;
    private JTextField newGame;
    private JButton addButton;

    private CharacterGUI characterGUI;
    private Controller controller;

    ReportGUI(CharacterGUI characterGUI) {
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
            public void actionPerformed(ActionEvent e) throws IOException {

                String gameName = newGame.getText();
                characterGUI.GAME_ARRAY.add(gameName);
                characterGUI.fetchCombo();

                controller.writing(gameName);

                characterGUI.setEnabled(true);
                ReportGUI.this.dispose();
            }
        });
    }
}
