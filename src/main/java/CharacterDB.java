import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URI;
import java.sql.*;
import java.util.ArrayList;

public class CharacterDB {

    private static final String DB_URL = "jdbc:sqlite:character.db";

    private static final String ID_COL = "id";
    private static final String PLAYER_COL = "playerName";
    private static final String CHARACTER_COL = "characterName";
    private static final String GAME_COL = "gameName";

    CharacterDB(){
        createTable();
    }

    private static void createTable() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
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

    void addCharacter(Character character) {

        final String addSql = "INSERT INTO characters (playerName, characterName, gameName, classBox, raceBox, " +
                "alignmentBox, levelBox, npcBox, backgroundArea, equipmentArea, spellsArea) " +
                "VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = connection.prepareStatement(addSql)) {

            ps.setString(1, character.getPlayerName());
            ps.setString(2, character.getCharacterName());
            ps.setString(3, character.getGameName());

            ps.setInt(4, character.getClassIndex());
            ps.setInt(5, character.getRaceIndex());
            ps.setInt(6, character.getAlignmentIndex());
            ps.setInt(7, character.getLevelIndex());

            ps.setBoolean(8, character.isNpcCheck());

            ps.setString(9, character.getBackground());
            ps.setString(10, character.getEquipment());
            ps.setString(11, character.getSpells());

            ps.execute();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    void updateCharacter(int ide, Character character) {

        final String updateSql = "UPDATE characters SET playerName = ?, characterName = ?, gameName = ?, " +
                "classBox = ?, raceBox = ?, alignmentBox = ?, levelBox = ?, npcBox = ?, backgroundArea = ?, " +
                "equipmentArea = ?, spellsArea = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement updatePs = connection.prepareStatement(updateSql)) {

            updatePs.setString(1, character.getPlayerName());
            updatePs.setString(2, character.getCharacterName());
            updatePs.setString(3, character.getGameName());

            updatePs.setInt(4, character.getClassIndex());
            updatePs.setInt(5, character.getRaceIndex());
            updatePs.setInt(6, character.getAlignmentIndex());
            updatePs.setInt(7, character.getLevelIndex());

            updatePs.setBoolean(8, character.isNpcCheck());

            updatePs.setString(9, character.getBackground());
            updatePs.setString(10, character.getEquipment());
            updatePs.setString(11, character.getSpells());
            updatePs.setInt(12, ide);

            updatePs.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    void deleteCharacter(int ide) {

        final String deleteSql = "DELETE FROM characters WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement deletePs = connection.prepareStatement(deleteSql)) {

            deletePs.setInt(1, ide);
            deletePs.execute();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    void deleteAll() {

        final String deleteAllSql = "DELETE FROM characters";

        try (Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement dropPs = connection.prepareStatement(deleteAllSql)) {

            dropPs.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    ArrayList<Character> fetchAllCharacters() {

        ArrayList<Character> allCharacters = new ArrayList<Character>();

        try (Connection connection = DriverManager.getConnection(DB_URL);
            Statement statement = connection.createStatement()) {
            String selectAllSQL = "SELECT * FROM characters";
            ResultSet rsAll = statement.executeQuery(selectAllSQL);

            while (rsAll.next()) {
                int ide = rsAll.getInt(ID_COL);
                String player = rsAll.getString(PLAYER_COL);
                String character = rsAll.getString(CHARACTER_COL);
                String game = rsAll.getString(GAME_COL);
                Character characterRecord = new Character(ide, player, character, game);
                allCharacters.add(characterRecord);
            }

            rsAll.close();

            return allCharacters;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Character fillTextField(int i) {

        Character index = new Character();
        String selectCharacterSQL = "SELECT * FROM characters WHERE id LIKE ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement statement = connection.prepareStatement(selectCharacterSQL)) {

            statement.setInt(1, i);
            ResultSet rsAll = statement.executeQuery();

            while (rsAll.next()) {
                String player = rsAll.getString(PLAYER_COL);
                String character = rsAll.getString(CHARACTER_COL);
                String game = rsAll.getString(GAME_COL);
                int classIndex = rsAll.getInt("classBox");
                int raceIndex = rsAll.getInt("raceBox");
                int alignmentIndex = rsAll.getInt("alignmentBox");
                int levelIndex = rsAll.getInt("levelBox");
                boolean npcCheck = rsAll.getBoolean("npcBox");
                String background = rsAll.getString("backgroundArea");
                String equipment = rsAll.getString("equipmentArea");
                String spells = rsAll.getString("spellsArea");

                index = new Character(player, character, game, classIndex, raceIndex, alignmentIndex, levelIndex,
                        npcCheck, background, equipment, spells);
            }

            rsAll.close();

            return index;

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
}
