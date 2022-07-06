package models;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Klass mudel (Üldine)
 */
public class Model {
    /**
     * Siin on kõik unikaalsed kategooriad mis andmebaasi failist leiti
     */
    private String[] categories;
    /**
     * Andmeaasi nimi kettal
     */
    private String dbName = "words.db";
    /**
     *  Andmebaasi ühenduse jaoks
     */
    private String dbUrl = "jdbc:sqlite:" + dbName;
    /**
     * Andmebaasi tabeli scores sisu (Edetabel)
     */
    private List<DataScores> dataScores;
    /**
     * Andmebaasi tabeli words sisu Sõnad
     */
    private List<DataWords> dataWords;
    /**
     * Andmebaasi ühendust algselt pole
     */
    Connection connection = null;
    /**
     * Juhuslik sõna mis valitakse uus mängu klikkides
     */
    private String randomWord;
    /**
     * juhuslik sõna suurtähtedega
     */
    private String randomWordUpperCase;
    /**
     * juhuslik sõna peidetuna
     */
    private StringBuffer hiddenWord;
    /**
     * Valesti sisestatud tähtede list
     */
    private List<String> missedCharsList = new ArrayList<>();
    /**
     * VAlesti sisestatud tähtede loendur
     */
    private int missedCharsCounter;
    /**
     * DefaultTableModel tabeli jaoks
     */
    private DefaultTableModel dtm = new DefaultTableModel();

    /**
     * Konstruktor
     */
    public Model() {
        dataScores = new ArrayList<>(); // Teeme tühja edetabeli listi
        dataWords = new ArrayList<>(); // Teeme tühja sõnade listi
        //categories = new String[]{"Kõik kategooriad", "Kategooria 1", "Kategooria 2"}; // TESTIKS!
        scoreSelect(); // Loeme edetabeli dataScores listi, kui on!
        wordsSelect(); // Loeme sõnade tabeli dataWords listi.
    }
    // ANDMEBAASI ASJAD
    /**
     * Andmebaaasi ühenduseks
     * @return tagastab ühenduse või rakendus lõpetab töö
     */
    private Connection dbConnection() throws SQLException {
        if (connection != null) { // Kui ühendus on püsti
            connection.close(); // Sulge ühendus
        }
        connection = DriverManager.getConnection(dbUrl); // Tee ühendus
        return connection; // Tagasta ühendus
    }
    /**
     * SELECT lause edetabeli sisu lugemiseks ja info dataScores listi lisamiseks
     */
    public void scoreSelect() {
        String sql = "SELECT * FROM scores ORDER BY playertime DESC";
        try {
            Connection conn = this.dbConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            dataScores.clear(); // Tühjenda dataScores list vanadest andmetest
            while (rs.next()) {
                //int id = rs.getInt("id");
                String datetime = rs.getString("playertime");
                LocalDateTime playerTime = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String playerName = rs.getString("playername");
                String guessWord = rs.getString("guessword");
                String wrongCharacters = rs.getString("wrongcharacters");
                // Lisame tabeli kirje dataScores listi
                dataScores.add(new DataScores(playerTime, playerName, guessWord, wrongCharacters));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lisab objekti dataScore andmebaasi
     */
    public void scoreInsert(DataScores playerScore){
        /*if (!tableExists("scores")){
            createTable();
        }*/
        String sql = "INSERT INTO scores(playertime,playername,guessword,wrongcharacters) VALUES (?,?,?,?)";
        try {
            Connection conn = this.dbConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            DateTimeFormatter formatSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String playerTime = playerScore.getGameTime().format(formatSQL);
            ps.setString(1,playerTime);
            ps.setString(2,playerScore.getPlayerName());
            ps.setString(3,playerScore.getGuessWord());
            ps.setString(4,playerScore.getMissingLetters());
            ps.executeUpdate();
            scoreSelect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * kontrollib kas tabel on olemas
     * @param tableName
     * @return
     */
    /*private boolean tableExists(String tableName) { // EI KASUTA ENNEM KUI 'createTable' ON OLEMAS, võib aktiivseks jääda
        try {
            Connection conn = this.dbConnection();
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null, null, tableName, null);
            rs.next(); // Lugemis järg järgmisele kirjele (1)
            return  rs.getRow() > 0; // see on ka kui true > 0
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.out.println("Viga tabeli kontrollil");
            return false;
        }
    }*/

    /**
     * teeb tabeli kui tabelit ei ole
     *
     */
    /*private void createTable(){ // JÄÄB PRAEGU VÄLJA, VAJA TEHA SQL TABELI PÕHJAL,
        try {
            Connection conn = this.dbConnection();
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE \"scores\" (\n" +
                    "\"playertime\" INTEGER NOT NULL UNIQUE,\n" +
                    "\"playername\" TEXT NOT NULL,\n" +
                    "\"quessword\" TEXT NOT NULL,\n" +
                    "\"wrongcharacters\" TEXT,\n" +
                    "PRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ");";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    /**
     * SELECT lause tabeli words sisu lugemiseks ja info dataWords listi lisamiseks
     */
    public void wordsSelect() {
        String sql = "SELECT * FROM words ORDER BY category, word";
        List<String> categories = new ArrayList<>(); // NB! See on meetodi sisene muutuja categories!
        try {
            Connection conn = this.dbConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            dataWords.clear(); // Tühjenda dataScores list vanadest andmetest
            while (rs.next()) {
                //int id = rs.getInt("id");
                int id = rs.getInt("id");
                String word = rs.getString("word");
                String category = rs.getString("category");
                dataWords.add(new DataWords(id, word, category)); // Lisame tabeli kirje dataWords listi
                categories.add(category);
            }
            // https://howtodoinjava.com/java8/stream-find-remove-duplicates/
            List<String> unique = categories.stream().distinct().collect(Collectors.toList());
            setCorrectCategoryNames(unique); // Unikaalsed nimed Listist String[] listi categories
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // SETTERS
    /**
     * Paneb unikaalsed kategooriad ComboBox-i jaoks muutujasse
     * @param unique unikaalsed kategooriad
     */
    private void setCorrectCategoryNames(List<String> unique) {
        categories = new String[unique.size()+1]; // Vali kategooria. See on klassi sisene muutuja!
        categories[0] = "Kõik kategooriad";
        for(int x = 0; x < unique.size(); x++) {
            categories[x+1] = unique.get(x);
        }
    }
    public void setDtm(DefaultTableModel dtm) {
        this.dtm = dtm;
    }

    /**
     * määrab valesti tagastatud tähtede arvu
     */
    public void setMissedCharsCounter(int missedCharsCounter) {
        this.missedCharsCounter = missedCharsCounter;
    }


    // GETTERS
    /**
     * Tagasta kategooriad
     * @return tagastab String[] listi kategooria nimedega
     */
    public String[] getCategories() {
        return categories;
    }
    /**
     * Tagastab edetabeli listi
     * @return tagastab List&lt;DataScores&gt; listi edetabeli tabelis sisuga
     */
    public List<DataScores> getDataScores() {
        return dataScores;
    }
    /**
     * Tagastab sõnade listi
     * @return List
     */
    public List<DataWords> getDataWords() {
        return dataWords;
    }
    /**
     * Tagastab valitud suvalise sõna
     */
    public String getRandomWord() {
        return randomWord;
    }

    /**
     *tagastab suvalise sõna suurtähtedena
     * @return
     */
    public String getRandomWordUpperCase() {
        return randomWordUpperCase;
    }

    /**
     *tagastab suvalise sõna peidetuna
     * @return
     */
    public StringBuffer getHiddenWord() {
        return hiddenWord;
    }

    /**
     * tagastab valestiarvatud tähtede listi
     * @return
     */
    public List<String> getMissedCharsList() {
        return missedCharsList;
    }

    /**
     * Tagastab valesti arvatud tähemärkide arvu
     * @return
     */
    public int getMissedCharsCounter() {
        return missedCharsCounter;
    }

    /**
     * Tagastab tabeli
     * @return
     */
    public DefaultTableModel getDtm() {
        return dtm;
    }

    /**
     * Määrab hiddenwordi, asendfab tähed alakriipsuga
     */
    public void hideWord(){
        StringBuffer newWord = new StringBuffer(this.randomWordUpperCase);
        for (int i = 0; i<this.randomWordUpperCase.length(); i++){
            newWord.setCharAt(i,'_');
        }
        this.hiddenWord = newWord;
    }

    /**
     * Paneb valitud kategoorisa sõnad listi ja võtab suvalise sõna
     * @param category
     */
    public void setRandomWordByCategory(String category){
        List<String> wordsList = new ArrayList<>(); // uus list sõnade jaoks
        Random random = new Random(); // uuele listile random valik
        String randomWord; // sõna mis valitakse
        if (category.equalsIgnoreCase("Kõik kategooriad")){ // kui on valitud kõik kategooriad siis valib kõikide sõnade seast
            randomWord =dataWords.get(random.nextInt(dataWords.size())).getWord();
        }else {
            for(DataWords word : dataWords){
                if (category.equalsIgnoreCase(word.getCategory())){ // Kui on valitud kategooria siis lisab need sõnad wordsListi
                    wordsList.add(word.getWord());
                }
            }
            randomWord = wordsList.get(random.nextInt(wordsList.size())); // valib suvalise sõna wordsListsit
        }
        this.randomWord = randomWord;
        this.randomWordUpperCase = randomWord.toUpperCase(); // teeb suurtähtedeks
        hideWord(); // teeb peidetud sõna
    }

    /**
     * lisab sõnale iga tähe järel tühiku
     * @param word
     * @return
     */
    public String wordSpacer(String word){
        String[] wordCharArray = word.split(""); // teeb sõna arrayks
        StringJoiner joiner = new StringJoiner(" ");
        String spacedWord;
        for (String w : wordCharArray){
            joiner.add(w);
            System.out.println(w);
        }
        return joiner.toString();
    }


}
