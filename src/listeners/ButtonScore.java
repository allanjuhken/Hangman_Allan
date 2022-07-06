package listeners;

import models.DataScores;
import models.Model;
import views.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

/**
 * Klass nupu Edetabel jaoks
 */
public class ButtonScore implements ActionListener {
    /**
     * Model
     */
    private final Model model;
    /**
     * View
     */
    private final View view;
    /**
     * Päise String[] list
     */
    private final String[] header = new String[] {"Kuupäev", "Nimi", "Sõna", "Tähed"};
    /**
     * Seda kasutatakse üks kord seda (ButtonScore) objekti tehes.
     * Tekitab tabeli päise ilma kirjeteta
     */
    private final DefaultTableModel dtm = new DefaultTableModel(header, 0);
    /**
     * JTable, millel ka tabeli sisu osa olemas. Hetkel tühi!
     */
    private final JTable table = new JTable(dtm);

    /**
     * Siia Dialog-i aknasse pannakse edetabeli tabel
     */
    private JDialog dialogScore; // Siin näidatakse edetabelit eraldi aknas

    /**
     * Konstruktor
     * @param model mudel
     * @param view vaade
     */
    public ButtonScore(Model model, View view) {
        this.model = model;
        this.view = view;
        model.setDtm(dtm); // Seadistame olemasoleva model jaoks
        createDialog(); // Loob dialoogi akna ja paneb sellel ka tabeli päise aga ilma andmeteta
    }

    /**
     * Kui klikitakse nupul Edetabel
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //JOptionPane.showMessageDialog(null, "Keegi klikkis edetabeli nuppu!");
        model.scoreSelect(); // Loeme kogu sisu uuesti listi
        //createScoreboard(); // Teeb dialoogi akna
        view.updateTable(); // Uuendame andmeid dialoogi akna tabelis
        dialogScore.setVisible(true); // Teeme dialoogiakna nähtavaks
        view.getTxtChar().requestFocus(); // Peale selle nupu klikkimist anna fookus tekstikastile
    }

    /**
     * Teeb dialog-i akna ja paneb tabeli sinna. Kuna seda on vaja korduvalt kasutada,
     * peab ta olemasolema.
     */
    private void createDialog() {
        dialogScore = new JDialog();
        dialogScore.setPreferredSize(new Dimension(500, 200));

        dialogScore.add(new JScrollPane(table));
        dialogScore.setTitle("Edetabel");
        dialogScore.pack();
        dialogScore.setLocationRelativeTo(view);
        dialogScore.setModal(true);
    }

}
