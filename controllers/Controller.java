package controllers;

import listeners.*;
import models.Model;
import views.View;

/**
 * Klass kontroller (Üldine)
 */
public class Controller {
    /**
     * Model
     */
    private Model model;
    /**
     * View
     */
    private View view;

    /**
     * Konstruktor
     * @param model Model
     * @param view View
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        view.registerButtonNew(new ButtonNew(model, view)); // Paneme Uue mängu nupu toimima
        view.registerButtonSend(new ButtonSend(model, view)); // Paneme Saada nupu toimima
        view.registerButtonScore(new ButtonScore(model, view)); // Paneme Edetabeli nupu toimima
        view.registerButtonWords(new ButtonWords(model, view)); // Paneme Sõnad nupu toimima
    }
}
