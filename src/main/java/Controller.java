import java.io.*;
import java.util.ArrayList;

public class Controller {

    private static final String GAME_NAMES = "gamenames.txt";

    private CharacterGUI gui;
    private CharacterDB db;

    public static void main(String[] args) throws IOException {
        new Controller().startApp();
    }

    private void startApp() throws IOException{
        db = new CharacterDB();

        ArrayList<Character> allData = db.fetchAllCharacters();

        gui = new CharacterGUI(this);
        gui.setTableData(allData);
    }

    ArrayList<Character> getAllData() {
        return db.fetchAllCharacters();
    }

    void addToDatabase(Character character) {
        db.addCharacter(character);
    }

    void deleteCharacter(int ide){
        db.deleteCharacter(ide);
    }

    void updateCharacter(int ide, Character character) {
        db.updateCharacter(ide, character);
    }

    void deleteAllCharacter() {
        db.deleteAll();
    }

    Character fillText(int ide) { return db.fillTextField(ide); }

    void writing(String name) throws IOException {

        FileWriter writer = new FileWriter(GAME_NAMES, true);
        BufferedWriter bufWriter = new BufferedWriter(writer);

        bufWriter.write(name + "\n");
        bufWriter.close();
    }

    void reading() throws IOException{

        FileReader reader = new FileReader(GAME_NAMES);
        BufferedReader bufReader = new BufferedReader(reader);

        String line = bufReader.readLine();

        while (line != null) {
            gui.GAME_ARRAY.add(line);
        }

        bufReader.close();
    }
}