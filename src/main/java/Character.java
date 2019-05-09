public class Character {

    private int id;
    private String playerName;
    private String characterName;
    private String gameName;
    private int classIndex;
    private int raceIndex;
    private int alignmentIndex;
    private int levelIndex;
    private boolean npcCheck;
    private String background;
    private String equipment;
    private String spells;

    Character(String p, String ch, String g, int c, int r, int a,
              int l, boolean npc, String bg, String eq, String sp) {
        playerName = p;
        characterName = ch;
        gameName = g;
        classIndex = c;
        raceIndex = r;
        alignmentIndex = a;
        levelIndex = l;
        npcCheck = npc;
        background = bg;
        equipment = eq;
        spells = sp;
    }

    Character(int i, String p, String ch, String g) {
        id = i;
        playerName = p;
        characterName = ch;
        gameName = g;
    }

    Character() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(int classIndex) {
        this.classIndex = classIndex;
    }

    public int getRaceIndex() {
        return raceIndex;
    }

    public void setRaceIndex(int raceIndex) {
        this.raceIndex = raceIndex;
    }

    public int getAlignmentIndex() {
        return alignmentIndex;
    }

    public void setAlignmentIndex(int alignmentIndex) {
        this.alignmentIndex = alignmentIndex;
    }

    public int getLevelIndex() {
        return levelIndex;
    }

    public void setLevelIndex(int levelIndex) {
        this.levelIndex = levelIndex;
    }

    public boolean isNpcCheck() {
        return npcCheck;
    }

    public void setNpcCheck(boolean npcCheck) {
        this.npcCheck = npcCheck;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getSpells() {
        return spells;
    }

    public void setSpells(String spells) {
        this.spells = spells;
    }

//    @Override
//    public String toString() {
//        return "PLAYER: " + playerName + "\t\tCHARACTER: " + characterName + "\t\tGAME: " + gameName;
//    }
}
