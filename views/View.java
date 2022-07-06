package views;

import models.DataScores;
import models.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

/**
 * Klass View (Üldine)
 */
public class View extends JFrame {
    /**
     * Mudel
     */
    private Model model;
    /**
     * Paneel mis sisaldab komponentide paneeli
     */
    private ChoicesPanel choicesPanel; // ÜLemine paneel JButton, JTextField, JLabel komponentidele
    /**
     * Paneel mis näitab äraarvatava sõna näitamist
     */
    private WordPanel wordPanel; // Paneel kus on äraarvatav sõna (algeselt H A K K A M E  M Ä N G I M A)

    /**
     * Konstruktor
     * @param model Model
     */
    public View(Model model) {
        // JFrame osa
        super("Hangman for Java"); // Frame tiitelrea tekst
        this.setPreferredSize(new Dimension(420,220)); // Frame suurus
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Et aken sulguks
        this.setLayout(new BorderLayout()); // Layout


        this.model = model; // View tahab ka modelit kasutada

        choicesPanel = new ChoicesPanel(model, this); // Teeme ülemise paneeli
        wordPanel = new WordPanel(); // Teeme alumise paneeli, kus on arvatav sõna

        // https://stackoverflow.com/questions/13731710/allowing-the-enter-key-to-press-the-submit-button-as-opposed-to-only-using-mo
        // Miks see siin peab olema? Ja miks see siin töötab aga kolm rida kõrgemal mitte? :)
        this.getRootPane().setDefaultButton(getBtnSend()); // Enter klahv töötab Saada täht puhul

        this.add(choicesPanel, BorderLayout.NORTH); // Paneme selle choicesPanel paneeli Framele
        this.add(wordPanel, BorderLayout.CENTER); // Paneme selle wordPanel paneeli Framele
    }

    /**
     * Nupu "Saada täht" funktsionaalsuseks
     * @param al ActionListener
     */
    public void registerButtonSend(ActionListener al) {
        choicesPanel.getBtnSend().addActionListener(al);
    }
    /**
     * Nupu "Uus mäng" funktsionaalsuseks
     * @param al ActionListener
     */
    public void registerButtonNew(ActionListener al) {
        choicesPanel.getBtnNew().addActionListener(al);
    }
    /**
     * Nupu "Edetabel" funktsionaalsuseks
     * @param al ActionListener
     */
    public void registerButtonScore(ActionListener al) {choicesPanel.getBtnScore().addActionListener(al);}
    /**
     * Nupu "Sõnad" funktsionaalsus
     * @param al ActionListener
     */
    public void registerButtonWords(ActionListener al) {choicesPanel.getBtnWords().addActionListener(al);}


    /**
     * Tähe sisestus kast
     * @return JTextField
     */
    public JTextField getTxtChar() {
        return choicesPanel.getTxtChar();
    }

    /**
     * Tagastab comboboxi
     * @return JCombobox
     */
    public JComboBox<String> getCmbCategory() {
        return choicesPanel.getCmbCategory();
    }
    /**
     * Tagastab uue mängu nupu
     * @return JButton
     */
    public JButton getBtnNew() {
        return choicesPanel.getBtnNew();
    }
    /**
     * Tagastab nupu Saada tähte nupu
     * @return JButton
     */
    public JButton getBtnSend() {
        return choicesPanel.getBtnSend();
    }

    /**
     * Tagasta nupp Sõnad
     * @return JButton
     */
    public JButton getBtnWords() {
        return choicesPanel.getBtnWords();
    }
    /**
     * Tagastab Katkesta mäng nupu
     * @return JButton
     */
    public JButton getBtnCancel() {
        return choicesPanel.getBtnCancel();
    }
    /**
     * Tagastab JLabeli mis näitab mängijale hetke seisu. Tähti ja allkriipse
     * @return JLabel
     */
    public JLabel getLblGuessWord() {
        return wordPanel.getLblGuessWord();
    }
    /**
     * Tagsatab lbalei mis sisaldab vigast infot
     * @return JLabel
     */
    public JLabel getLblWrongInfo() {
        return choicesPanel.getLblWrongInfo();
    }

    // SETTERS
    /**
     * Seadistab mängu ALGSEISU nuppude ja tekstiväljadega seoses. See kutsuda siis, kui kogu mängu info on olemas.
     */
    public void setStartGame() {
        getCmbCategory().setEnabled(false); // Comboboxi ei saa valida
        getBtnNew().setEnabled(false); // Mängimise ajal ei saa uut mängu alustada
        getBtnWords().setEnabled(false);   // Sõnu ja kategooriaid ei saa mängu ajal vaadata!
        getTxtChar().setEnabled(true); // Tähte saab sisestada
        getBtnSend().setEnabled(true); // Saada täht nuppu saab kasutada
        getBtnCancel().setVisible(true);   // Mängu saab katkestada
        model.getMissedCharsList().clear(); //valesti arvatud tähtede lsit tühjaks
        //getLblWrongInfo().setText("Valesti 0 täht(e). "); // Muuda vigade teavitus vaikimisi tekstiks
        //getLblWrongInfo().setForeground(Color.RED); // Muuda teksti värv vaikimsii mustaks
    }
    /**
     * Seadistab mängu LÕPPSEISU nuppude ja tekstiväljadega seoses. See kustuda siis kui mängu lõpp tulemus on teada
     * ja mäng on KINDLASTI lõppenud
     */
    public void setEndGame() {
        getCmbCategory().setEnabled(true); // Comboboxi saab  valida
        getBtnNew().setEnabled(true); // Saab uut mängu alustada
        getBtnWords().setEnabled(true); // Nupp Sõnad saab jälle klikkida
        getTxtChar().setEnabled(false); // Tähte ei saa sisestada
        getBtnSend().setEnabled(false); // Saada täht nuppu ei saa kasutada
        getBtnCancel().setVisible(false);  // Mängu ei saa enam katkestada
        getTxtChar().setText("");   // Sisestatud tähe tühjendamine
        getLblWrongInfo().setText("Valesti 0 täht(e). "); // Muuda vigade teavitus vaikimisi tekstiks
        getLblWrongInfo().setForeground(Color.BLACK); // Muuda teksti värv vaikimsii mustaks
    }
    /**
     * Uuendab JDialog aknas JTabel infot
     */
    public void updateTable() {
        model.getDtm().setRowCount(0);
        String gametime = null;
        for (DataScores ds : model.getDataScores()) {
            gametime = ds.getGameTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            String name = ds.getPlayerName();
            String word = ds.getGuessWord();
            String chars = ds.getMissingLetters();
            model.getDtm().addRow(new Object[]{gametime, name, word, chars});
        }
    }
}
