package listeners;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Piirab tähtede arvu JTextField väljal. Meie variandis on see üks täht, kuid lahendus on suvalise pkkusega!
 * https://www.tutorialspoint.com/how-can-we-limit-the-number-of-characters-inside-a-jtextfield-in-java
 */
public class TextFieldLimit extends PlainDocument {
    /**
     * Stringi max pikkus
     */
    private int limit;

    /**
     * Konstruktor 1
     * @param limit int
     */
    public TextFieldLimit(int limit) {
        super(); // See on PlainDocument KÕIK enda omadused
        this.limit = limit;
    }
    /**
     * Konstruktor 2
     * @param limit int
     * @param upper boolean
     */
    public TextFieldLimit(int limit, boolean upper) {
        super(); // See on PlainDocument KÕIK enda omadused
        this.limit = limit;
    }

    /**
     * See on meetodi originaal kirjeldus, kuid meetodi sisu on "enda oma"
     * @param offset the starting offset &gt;= 0
     * @param str    the string to insert; does nothing with null/empty strings
     * @param attr   the attributes for the inserted content
     * @throws BadLocationException BadLocationException
     */
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if(str == null) return;
        if((getLength() + str.length() <= limit)) {
            super.insertString(offset, str, attr);
        }
    }
}
