package views;

import javax.swing.*;
import java.awt.*;

/**
 * Klass äraarvatava sõna paneeli (alumine)
 */
public class WordPanel extends JPanel {
    /**
     * JLabel mis näitab mängijale arvatavat sõna
     */
    private JLabel lblGuessWord;
    /**
     * Kirjastiil antud JLabelile
     */
    private Font fontBold = new Font("Verdana", Font.BOLD, 22); // Rasvane

    /**
     * Konstruktor
     */
    public WordPanel() {
        //this.setBackground(Color.PINK);
        this.setLayout(new FlowLayout());

        lblGuessWord = new JLabel("H A _ _ A _ E  _ _ N _ I _ A"); // HAKKAME MÄNGIMA
        lblGuessWord.setFont(fontBold);
        add(lblGuessWord);
    }
    // GETTERS

    /**
     * Tagastab labeli mis sisaldab teksti mida kasutajale näidata
     * @return JLabel
     */
    public JLabel getLblGuessWord() {
        return lblGuessWord;
    }
}
