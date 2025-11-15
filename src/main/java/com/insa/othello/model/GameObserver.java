package com.insa.othello.model;

/**
 * Interface Observer pour le pattern Observer
 * Les observateurs sont notifiés des changements du jeu
 */
public interface GameObserver {
    /**
     * Appelé quand l'état du jeu change
     */
    void onGameStateChanged();
}
