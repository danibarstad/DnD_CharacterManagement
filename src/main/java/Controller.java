import java.util.ArrayList;

public class Controller {

    private CharacterGUI gui;
    private CharacterDB db;

    public static void main(String[] args) {
        new Controller().startApp();
    }

    private void startApp() {
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
}