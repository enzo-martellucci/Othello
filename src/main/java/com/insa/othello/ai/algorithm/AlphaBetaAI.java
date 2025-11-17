package com.insa.othello.ai.algorithm;

import com.insa.othello.ai.strategy.EvaluationStrategy;
import com.insa.othello.constant.Cell;
import com.insa.othello.model.Board;
import com.insa.othello.model.Position;

public class AlphaBetaAI extends AbstractAI
{
    private final EvaluationStrategy strategy;
    private final int limitMS;
    private final int maxDepth;
    private final Cell playerColor;
    private long startTime;

    public AlphaBetaAI(EvaluationStrategy strategy, int limitMS, int maxDepth, Cell playerColor)
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

        // Déterminer si on maximise ou minimise
        boolean isMaximizing = (color == MAXIMIZER);

        Position bestMove = null;
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Initialiser alpha et beta
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        // Évaluer chaque coup possible
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

            // Appeler AlphaBeta récursivement
            int score = alphabeta(newBoard, nextColor, 1, alpha, beta, !isMaximizing);

            // Mettre à jour le meilleur coup
            if (isMaximizing) {
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                alpha = Math.max(alpha, bestScore);
            } else {
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                beta = Math.min(beta, bestScore);
            }
        }

        return bestMove;
    }

    /**
     * Algorithme Alpha-Beta avec élagage
     * @param board Le plateau actuel
     * @param color La couleur du joueur actuel
     * @param depth La profondeur actuelle
     * @param alpha La meilleure valeur pour le joueur maximisant
     * @param beta La meilleure valeur pour le joueur minimisant
     * @param isMaximizing True si on maximise, false si on minimise
     * @return Le score du meilleur coup
     */
    private int alphabeta(Board board, Cell color, int depth, int alpha, int beta, boolean isMaximizing)
    {
        // Vérifier la limite de temps
        if (isTimeUp()) {
            return strategy.evaluate(board);
        }

        // Vérifier la profondeur maximale
        if (depth >= maxDepth) {
            return strategy.evaluate(board);
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
                return strategy.evaluate(board);
            }

            // Sinon, passer le tour à l'adversaire
            return alphabeta(nextBoard, nextColor, depth, alpha, beta, !isMaximizing);
        }

        // Évaluer chaque coup possible avec élagage
        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;

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

                // Appel récursif
                int score = alphabeta(newBoard, nextColor, depth + 1, alpha, beta, false);

                maxScore = Math.max(maxScore, score);
                alpha = Math.max(alpha, score);

                // Élagage Beta (Beta cut-off)
                if (beta <= alpha) {
                    break; // Coupe Beta
                }
            }

            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;

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

                // Appel récursif
                int score = alphabeta(newBoard, nextColor, depth + 1, alpha, beta, true);

                minScore = Math.min(minScore, score);
                beta = Math.min(beta, score);

                // Élagage Alpha (Alpha cut-off)
                if (beta <= alpha) {
                    break; // Coupe Alpha
                }
            }

            return minScore;
        }
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
