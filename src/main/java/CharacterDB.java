import java.sql.*;

public class CharacterDB {

    CharacterDB(){
        createTable();
    }

    private static void createTable() {
        try (Connection connection = DriverManager.getConnection(DBConfig.DB_URL);
             Statement statement = connection.createStatement()) {

            String createCharacterTable = "CREATE TABLE IF NOT EXISTS characters (id INTEGER PRIMARY KEY, " +
                    "player TEXT, character TEXT, game TEXT, classBox INTEGER, raceBox INTEGER, alignmentBox INTEGER, " +
                    "levelBox INTEGER, npcBox BOOLEAN, background TEXT, equipment TEXT, spells TEXT";

            statement.executeUpdate(createCharacterTable);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    protected static void addCharacter(String player, String character, String game, int classX, int race, int alignment,
                                    int level, boolean npc, String background, String equipment, String spells) {

        final String addSql = "INSERT INTO characters VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )";

        try (Connection connection = DriverManager.getConnection(DBConfig.DB_URL);
             PreparedStatement ps = connection.prepareStatement(addSql)) {

            ps.setString(1, player);
            ps.setString(2, character);
            ps.setString(3, game);

            ps.setInt(4, classX);
            ps.setInt(5, race);
            ps.setInt(6, alignment);
            ps.setInt(7, level);

            ps.setBoolean(8, npc);

            ps.setString(9, background);
            ps.setString(10, equipment);
            ps.setString(11, spells);

            ps.execute();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
}
