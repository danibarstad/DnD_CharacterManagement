import java.sql.*;

public class CharacterDB {

    CharacterDB(){
        createTable();
    }

    private static void createTable() {
        try (Connection connection = DriverManager.getConnection(DBConfig.DB_URL);
             Statement statement = connection.createStatement()) {

            String createCharacterTable = "CREATE TABLE IF NOT EXISTS characters (id INTEGER PRIMARY KEY, " +
                    "playerName TEXT, characterName TEXT, gameName TEXT, classBox INTEGER, raceBox INTEGER, " +
                    "alignmentBox INTEGER, levelBox INTEGER, npcBox BOOLEAN, backgroundArea TEXT, " +
                    "equipmentArea TEXT, spellsArea TEXT)";

            statement.executeUpdate(createCharacterTable);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    protected static void addCharacter(String player, String characterX, String game, int classX, int race, int alignment,
                                    int level, boolean npc, String background, String equipment, String spells) {

        final String addSql = "INSERT INTO characters (playerName, characterName, gameName, classBox, raceBox, " +
                "alignmentBox, levelBox, npcBox, backgroundArea, equipmentArea, spellsArea) " +
                "VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )";

        try (Connection connection = DriverManager.getConnection(DBConfig.DB_URL);
             PreparedStatement ps = connection.prepareStatement(addSql)) {

            ps.setString(1, player);
            ps.setString(2, characterX);
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

    protected static void deleteCharacter(String name) {

        String deleteSql = "DELETE FROM characters WHERE playerName = ?";

        try (Connection connection = DriverManager.getConnection(DBConfig.DB_URL);
        PreparedStatement deletePs = connection.prepareStatement(deleteSql)) {

            deletePs.setString(1, name);
            deletePs.execute();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
}
