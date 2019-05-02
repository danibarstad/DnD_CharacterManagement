public class CharacterDB {

    private static final String DB_URL = "jdbc:sqlite:character.db";

    private static final String CREATE_CHARACTER_TABLE = "CREATE TABLE characters (id INTEGER PRIMARY KEY, " +
            "player TEXT, character TEXT, game TEXT, classBox INTEGER, raceBox INTEGER, alignmentBox INTEGER, " +
            "levelBox INTEGER, npcBox BOOLEAN, background TEXT, equipment TEXT, spells TEXT";
}
