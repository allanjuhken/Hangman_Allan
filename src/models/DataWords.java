package models;

/**
 * Klass mis sisaldab tabeli words sisu
 */
public class DataWords {
    /**
     * Unikaalne Id
     */
    private int id;
    /**
     * Sõna
     */
    private String word;
    /**
     * Kategooria
     */
    private String category;

    /**
     * Konstruktor
     * @param id id
     * @param word sõna
     * @param category kategooria
     */
    public DataWords(int id, String word, String category) {
        this.id = id;
        this.word = word;
        this.category = category;
    }
    // GETTERS

    /**
     * Tagastab unikaalse kirje id tabelist
     * @return int
     */
    public int getId() {
        return id;
    }
    /**
     * Tagastab sõna
     * @return String
     */
    public String getWord() {
        return word;
    }
    /**
     * Tagastab kategooria
     * @return String
     */
    public String getCategory() {
        return category;
    }
}
