package listeners;

import models.DataScores;
import models.Model;
import views.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

/**
 * Klass nupu Saada täht jaoks
 */
public class ButtonSend implements ActionListener {
    /**
     * Mudel
     */
    private Model model;
    /**
     * View
     */
    private View view;

    /**
     * Konstuktor
     *
     * @param model Model
     * @param view  View
     */
    public ButtonSend(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Kui kliikida nupul Saada täht
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //JOptionPane.showMessageDialog(null, "Kes vajutas nuppu/Enter: " + view.getTxtChar().getText().toUpperCase());

        view.getTxtChar().requestFocus(); // Peale selle nupu klikkimist anna fookus tekstikastile
        String guessStringChar = view.getTxtChar().getText().toUpperCase();
        char guessChar = guessStringChar.charAt(0);
        String guessWord = model.getRandomWordUpperCase();
        String[] wordCharArray = guessWord.split(""); //tee arvatav sõna arrayks
        boolean miss = true;

        for (int i = 0; i < wordCharArray.length; i++) {
            if (wordCharArray[i].equalsIgnoreCase(guessStringChar)) {
                //System.out.println("great success!");
                model.getHiddenWord().setCharAt(i, guessChar); // avaldab ära arvatud tähed ja määrab uue stringi
                miss = false;
            }
        }

        if (miss){
            model.getMissedCharsList().add(guessStringChar);
            view.getLblWrongInfo().setForeground(Color.RED);
        }

        String wordUpdate = model.wordSpacer(model.getHiddenWord().toString()); //võtab peidetud tähed uuesti ja paneb tühikud vahele
        view.getLblGuessWord().setText(wordUpdate);                 // paneb arvatud tähtedega sõna uuesti labelile
        String missedWordsStream = model.getMissedCharsList().toString().replace("[", "").replace("]", ""); // võtab [] ära, sest nii on ilusam
        model.setMissedCharsCounter(model.getMissedCharsList().size());
        view.getLblWrongInfo().setText("Valesti " + model.getMissedCharsCounter() + " täht(e). " + missedWordsStream); // uuendab, mitu sõna mööda pandud ja näitab sõnu
        //System.out.println(model.getMissedCharsList().size());
        view.getTxtChar().setText("");

        if (!model.getHiddenWord().toString().contains("_")) { //kontrollib, ega võitnud juba ei ole
            String nameWinner;
            do {
                nameWinner = (String) JOptionPane.showInputDialog(view, "Sisesta oma nimi: ");
                if (nameWinner == null) {
                    view.setEndGame();
                    break;
                } else if (nameWinner.trim().length() < 2) {
                    JOptionPane.showMessageDialog(view, "Nimi peab olema vähemalt kaks tähemärki");
                } else if (nameWinner.trim().length() >= 2) {
                    DataScores playerScore = new DataScores(LocalDateTime.now(), nameWinner, model.getRandomWord(), missedWordsStream); //loob uue objekti
                    model.scoreInsert(playerScore); //sisestab sqli. modelisse on tehtud uus meetod sisestamiseks
                    model.getDataScores().add(playerScore); //lisab viimase score ka datascores listi
                    view.setEndGame();
                }
            } while (nameWinner.trim().length() < 2);
        }

        if (model.getMissedCharsCounter() >= 7) {
            JOptionPane.showMessageDialog(view, "Kaotasid mängu.");

            view.setEndGame();
        }
    }
}

