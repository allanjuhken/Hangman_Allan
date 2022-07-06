package models;

import java.time.LocalDateTime;

/**
 * Klass mis sisaldab edetabeli sisu words.db olevat tabelit scores
 */
public class DataScores {
    /**
     * Mängu aeg YYYY-MM-DD HH:MM:SS
     */
    private LocalDateTime gameTime;
    /**
     * Mängija nimi
     */
    private String playerName;
    /**
     * Äraarvatav sõna
     */
    private String guessWord;
    /**
     * Valed tähed. Võib ka tühi olla!
     */
    private String missingLetters;

    /**
     * Konstruktor
     * @param gameTime LocalDateTime
     * @param playerName String
     * @param guessWord String
     * @param missingLetters String
     */
    public DataScores(LocalDateTime gameTime, String playerName, String guessWord, String missingLetters) {
        this.gameTime = gameTime;
        this.playerName = playerName;
        this.guessWord = guessWord;
        this.missingLetters = missingLetters;
    }

    /**
     * Tagastab kuupäeva ja aja
     * @return LocalDateTime
     */
    public LocalDateTime getGameTime() {
        return gameTime;
    }
    /**
     * Tagastab mängija nime
     * @return String
     */
    public String getPlayerName() {
        return playerName;
    }
    /**
     * Tagastab äraarvatud sõna
     * @return String
     */
    public String getGuessWord() {
        return guessWord;
    }
    /**
     * Tasgastab valesti sisestatud tähed
     * @return String
     */
    public String getMissingLetters() {
        return missingLetters;
    }
}
