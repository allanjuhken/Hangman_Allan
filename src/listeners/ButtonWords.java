package listeners;

import models.DataWords;
import models.Model;
import views.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klass nupu Sõnad jaoks
 */
public class ButtonWords implements ActionListener {
    /**
     * Model
     */
    private Model model;
    /**
     * View
     */
    private View view;
    /**
     * JTabel
     */
    private JTable table;
    /**
     * Tabeli päise veergude nimed String[] listina
     */
    private String[] header = new String[] {"Sõna", "Kategooria"};
    /**
     * Siia JDialog aknale pannakse Sõnade tabeli sisu
     */
    private JDialog dialogWords; // Siin näidatakse sõnu ja kategooriaid eraldi aknas

    /**
     * Konstruktor
     * @param model Model
     * @param view View
     */
    public ButtonWords(Model model, View view) {
        this.model = model;
        this.view = view;
        createDialog(); // Teeme dialoogi akna
    }

    /**
     * Kui klikitakse nuppu Sõnad
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Kui klikitakse nuppu btnWords
        model.wordsSelect(); // Loeme kogu sisu uuesti listi
        createWordsBoard(); // Teeb Dialogi akna et seda korduvalt kasutada (iga klikk nupul Sõnad)

        view.getTxtChar().requestFocus(); // Peale selle nupu klikkimist anna fookus teksti kastile
    }
    /**
     * Teeb dialogi akna ette määratud suurusega
     */
    private void createDialog() {
        dialogWords = new JDialog();
        dialogWords.setPreferredSize(new Dimension(500, 200));
    }
    /**
     * Teeb tabeli sõnade jaoks ja lisab dialogWords dialoogi aknale
     */
    private void createWordsBoard() {
        DefaultTableModel dtm = new DefaultTableModel();
        table = new JTable(dtm);

        // Tabeli päis
        for (String column : header) {
            dtm.addColumn(column);
        }
        // Tabeli sisu
        String gametime = null;
        for (DataWords dw : model.getDataWords()) {
            String word = dw.getWord();
            String category = dw.getCategory();
            dtm.addRow(new Object[]{word, category});
        }

        dialogWords.add(new JScrollPane(table));
        dialogWords.setTitle("Teadaolevad sõnad");
        dialogWords.pack();
        dialogWords.setLocationRelativeTo(view);
        dialogWords.setModal(true);
        dialogWords.setVisible(true);
    }
}
