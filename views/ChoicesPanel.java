package views;

import listeners.TextFieldLimit;
import models.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Jpanel kus on kõik valiku võimalused mängu jaoks
 */
public class ChoicesPanel extends JPanel {
    /**
     * Mudel
     */
    private Model model;
    /**
     * View
     */
    private View view;
    /**
     * JPanel mis sisaldab komponente (JButton, JLabel, JTxtField, JComboBox)
     */
    private JPanel pnlComponents = new JPanel(new GridBagLayout());
    /**
     * Seaded "Excel"-i variandis asjade paigutamiseks
     */
    private GridBagConstraints gbc = new GridBagConstraints();
    /**
     * Vajalikud JLabel-id
     */
    private JLabel lblChar, lblWrongInfo;
    /**
     * Vajalik sisestus kast (täht)
     */
    private JTextField txtChar;
    /**
     * Kategooria ComboBox
     */
    private JComboBox<String> cmbCategory;
    /**
     * Kõik kasutatavad Nupud
     */
    private JButton btnNew, btnSend, btnScore, btnWords, btnCancel;

    /**
     * Konstruktor
     * @param model Model
     * @param view View
     */
    public ChoicesPanel(Model model, View view) {
        // JPanel seaded
        //this.setBackground(new Color(200, 255, 255)); // Tasutavärv
        this.setLayout(new FlowLayout()); // FlowLayout ilma joondamata
        //pnlComponents.setBackground(new Color(220, 255, 185)); // pnlComponents tausta värv

        this.model = model; // Model mis tuleb view-ist kaasa
        this.view = view;   // View mis tleb viev-ist

        // JPaneli Constraints seaded "Excel"i seaded
        gbc.anchor = GridBagConstraints.WEST; // Lahtri sisu vasakule joondamine
        gbc.insets = new Insets(2,2,2,2); // Ümberringi 2 pikselit
        gbc.fill = GridBagConstraints.BOTH; // Venitab lahtri laiuse täis. Nagu Excel!

        createComponents(); // Teeme KÕIK komponendid ja paneme paneelile pnlComponents

        add(pnlComponents); // Paneme komponentide paneeli pnlComponents olemasolevale choicesPanel paneelile
    }

    /**
     * Meetod kus tehakse ja pannakse kõik komponendid paneelile pnlComponents (rida ja veerg)
     */
    public void createComponents() {
        // ESIMENE RIDA
        JLabel lblCategory = new JLabel("Kategooria");
        gbc.gridx = 0;  // Veerg
        gbc.gridy = 0;  // Rida
        pnlComponents.add(lblCategory, gbc);

        cmbCategory = new JComboBox<>(model.getCategories()); // Modelist võtame juba teadaolevad kategooriad
        gbc.gridx = 1;  // Veerg
        gbc.gridy = 0;  // Rida
        pnlComponents.add(cmbCategory, gbc);

        btnNew = new JButton("Uus mäng");
        gbc.gridx = 2;  // Veerg
        gbc.gridy = 0;  // Rida
        pnlComponents.add(btnNew, gbc);

        // TEINE RIDA
        lblChar = new JLabel("Sisesta täht");
        gbc.gridx = 0;  // Veerg
        gbc.gridy = 1;  // Rida
        pnlComponents.add(lblChar, gbc);

        // Järgnev on SISESTUSKAST ja selleks et sisestus kast oleks alati aktiivne, kui pole saab hiljem requestFocus() kasutada
        // Lisaks teeb ka txtChar!
        txtChar = new JTextField("", 14) {
            // https://stackoverflow.com/questions/4640138/setting-the-focus-to-a-text-field
            @Override
            public void addNotify() {
                super.addNotify();
                requestFocus(); // Tähe sisestus aktiivseks
            }
        };
        txtChar.setEnabled(false); // Vaikimisi teksti ei saa sisestada
        txtChar.setHorizontalAlignment(JTextField.CENTER); // Kirjuta lahtri keskele tähti
        txtChar.setDocument(new TextFieldLimit(1)); // Seadistame maksimum stringi pikkuse tekstiväljale (listener.TextFieldLimit)
        gbc.gridx = 1;  // Veerg
        gbc.gridy = 1;  // Rida
        pnlComponents.add(txtChar, gbc);

        btnSend = new JButton("Saada täht");
        btnSend.setEnabled(false); // Vaikimisi ei saa seda nuppu klikkida
        gbc.gridx = 2;  // Veerg
        gbc.gridy = 1;  // Rida
        pnlComponents.add(btnSend, gbc);

        // KOLMAS rida
        lblWrongInfo = new JLabel("Valesti 0 täht(e). ", JLabel.CENTER); // Label joondatakse lahtrite keskele
        gbc.gridx = 0;  // Veerg
        gbc.gridy = 2;  // Rida
        gbc.gridwidth = 3; // Üle kolme veeru
        pnlComponents.add(lblWrongInfo, gbc);

        // NELJAS rida
        btnCancel = new JButton("Katkesta mäng");
        btnCancel.setVisible(false); // Nupp ei ole algselt nähtav
        /**
         * Selle nupu Katkesta mäng funktsionaalsus on siin!!!
         */
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setEndGame();
            }
        });
        gbc.gridx = 0;  // Veerg
        gbc.gridy = 3;  // Rida
        gbc.gridwidth = 1; // Jälle üks veerg korraga, muidu on üle kolme ikka
        pnlComponents.add(btnCancel, gbc);

        btnScore = new JButton("Edetabel");
        gbc.gridx = 1;  // Veerg
        gbc.gridy = 3;  // Rida
        pnlComponents.add(btnScore, gbc);

        btnWords = new JButton("Sõnad");
        gbc.gridx = 2;  // Veerg
        gbc.gridy = 3;  // Rida
        pnlComponents.add(btnWords, gbc);
    }

    // GETTERID
    /**
     * Kategooria
     * @return JComboBox
     */
    public JComboBox<String> getCmbCategory() {
        return cmbCategory;
    }
    /**
     * Uus mängu nupp
     * @return JButton
     */
    public JButton getBtnNew() {
        return btnNew;
    }
    /**
     * Sisestatud tähe kast
     * @return JTextField
     */
    public JTextField getTxtChar() {
        return txtChar;
    }
    /**
     * Nupp saada
     * @return JButton
     */
    public JButton getBtnSend() {
        return btnSend;
    }
    /**
     * Edetabeli nupp
     * @return JButton
     */
    public JButton getBtnScore() {
        return btnScore;
    }
    /**
     * Sõnade nupp
     * @return JButton
     */
    public JButton getBtnWords() {
        return btnWords;
    }
    /**
     * Katkesta mängu nupp
     * @return JButton
     */
    public JButton getBtnCancel() {
        return btnCancel;
    }
    /**
     * Tagastab JLabeli mis sisaldab vigade ingot
     * @return JLabel
     */
    public JLabel getLblWrongInfo() {
        return lblWrongInfo;
    }
}
