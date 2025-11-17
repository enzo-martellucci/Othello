package com.insa.othello.ai.algorithm;

import com.insa.othello.ai.strategy.EvaluationStrategy;
import com.insa.othello.constant.Cell;
import com.insa.othello.model.Board;
import com.insa.othello.model.Position;

public class NegaMaxAI extends AbstractAI
{
    private final EvaluationStrategy strategy;
    private final int limitMS;
    private final int maxDepth;
    private final Cell playerColor;
    private long startTime;

    public NegaMaxAI(EvaluationStrategy strategy, int limitMS, int maxDepth, Cell playerColor)
    {
        this.strategy = strategy;
        this.limitMS = limitMS;
        this.maxDepth = maxDepth;
        this.playerColor = playerColor;
    }

    @Override
    protected Position selectMove(Board board, Cell color)
    {
        // Initialiser le chronomètre
        this.startTime = System.currentTimeMillis();

        // Récupérer les coups valides
        var validMoves = board.getMapMove().keySet();

        // Si aucun coup valide, retourner null (passe)
        if (validMoves.isEmpty()) {
            return null;
        }

        // Déterminer le coefficient de couleur (+1 pour MAXIMIZER, -1 pour MINIMIZER)
        int colorCoef = (color == MAXIMIZER) ? 1 : -1;

        Position bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        // Évaluer chaque coup possible avec élagage Alpha-Beta
        for (Position move : validMoves) {
            // Vérifier la limite de temps
            if (isTimeUp()) {
                break;
            }

            // Créer une copie du plateau et jouer le coup
            Board newBoard = board.copy();
            newBoard.play(move.r(), move.c(), color);

            // Préparer le tour suivant
            Cell nextColor = color.opponent();
            newBoard.updatePossiblePlay(nextColor);

            // Appeler NegaMax avec Alpha-Beta (négation et inversion de alpha/beta)
            int score = -negamax(newBoard, nextColor, 1, -colorCoef, -beta, -alpha);

            // Mettre à jour le meilleur coup
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }

            // Mettre à jour alpha
            alpha = Math.max(alpha, score);

            // Élagage Beta
            if (alpha >= beta) {
                break;
            }
        }

        return bestMove;
    }

    /**
     * Algorithme NegaMax récursif avec élagage Alpha-Beta
     * @param board Le plateau actuel
     * @param color La couleur du joueur actuel
     * @param depth La profondeur actuelle
     * @param colorCoef Le coefficient de couleur (+1 pour MAXIMIZER, -1 pour MINIMIZER)
     * @param alpha La meilleure valeur garantie pour le joueur courant
     * @param beta La meilleure valeur garantie pour l'adversaire
     * @return Le score du meilleur coup
     */
    private int negamax(Board board, Cell color, int depth, int colorCoef, int alpha, int beta)
    {
        // Vérifier la limite de temps
        if (isTimeUp()) {
            return colorCoef * strategy.evaluate(board);
        }

        // Vérifier la profondeur maximale
        if (depth >= maxDepth) {
            return colorCoef * strategy.evaluate(board);
        }

        // Récupérer les coups valides
        var validMoves = board.getMapMove().keySet();

        // Cas terminal : aucun coup valide
        if (validMoves.isEmpty()) {
            // Vérifier si l'adversaire peut jouer
            Cell nextColor = color.opponent();
            Board nextBoard = board.copy();
            nextBoard.updatePossiblePlay(nextColor);

            // Si l'adversaire ne peut pas jouer non plus, la partie est finie
            if (nextBoard.getMapMove().isEmpty()) {
                return colorCoef * strategy.evaluate(board);
            }

            // Sinon, passer le tour à l'adversaire
            return -negamax(nextBoard, nextColor, depth, -colorCoef, -beta, -alpha);
        }

        // Initialiser le meilleur score
        int bestScore = Integer.MIN_VALUE;

        // Évaluer chaque coup possible avec élagage
        for (Position move : validMoves) {
            // Vérifier la limite de temps
            if (isTimeUp()) {
                break;
            }

            // Créer une copie du plateau et jouer le coup
            Board newBoard = board.copy();
            newBoard.play(move.r(), move.c(), color);

            // Préparer le tour suivant
            Cell nextColor = color.opponent();
            newBoard.updatePossiblePlay(nextColor);

            // Appel récursif NegaMax avec Alpha-Beta (négation et inversion de alpha/beta)
            int score = -negamax(newBoard, nextColor, depth + 1, -colorCoef, -beta, -alpha);

            // Mettre à jour le meilleur score
            bestScore = Math.max(bestScore, score);

            // Mettre à jour alpha
            alpha = Math.max(alpha, score);

            // Élagage Beta (coupure)
            if (alpha >= beta) {
                break;  // Coupe Beta
            }
        }

        return bestScore;
    }

    /**
     * Vérifie si la limite de temps est dépassée
     * @return True si le temps est écoulé
     */
    private boolean isTimeUp()
    {
        return (System.currentTimeMillis() - startTime) >= limitMS;
    }
}
