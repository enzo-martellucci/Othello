package com.insa.othello.ai;

import com.insa.othello.ai.algorithm.AlphaBetaAI;
import com.insa.othello.ai.algorithm.MinMaxAI;
import com.insa.othello.ai.strategy.AbsoluteStrategy;
import com.insa.othello.ai.strategy.EvaluationStrategy;
import com.insa.othello.ai.strategy.MixteStrategy;
import com.insa.othello.ai.strategy.MobileStrategy;
import com.insa.othello.ai.strategy.PositionalStrategy;
import com.insa.othello.constant.Cell;
import com.insa.othello.constant.PlayerType;
import com.insa.othello.constant.StrategyType;
import com.insa.othello.model.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AITest {

    @Test
    public void testMinMaxAICreation() {
        AI minMaxAI = AbstractAI.createAI(PlayerType.MIN_MAX, StrategyType.POSITIONAL, 3, Cell.WHITE);
        assertNotNull(minMaxAI, "MinMaxAI should not be null");
        assertTrue(minMaxAI instanceof MinMaxAI, "Should create MinMaxAI instance");
    }

    @Test
    public void testAlphaBetaAICreation() {
        AI alphaBetaAI = AbstractAI.createAI(PlayerType.MIN_MAX_ALPHA_BETA, StrategyType.ABSOLUTE, 3, Cell.BLACK);
        assertNotNull(alphaBetaAI, "AlphaBetaAI should not be null");
        assertTrue(alphaBetaAI instanceof AlphaBetaAI, "Should create AlphaBetaAI instance");
    }

    @Test
    public void testRandomAICreation() {
        // RandomAI is created for testing evaluation strategies
        RandomAI randomAI = new RandomAI();
        assertNotNull(randomAI, "RandomAI should not be null");
    }

    @Test
    public void testStrategyFactory() {
        EvaluationStrategy positional = EvaluationStrategy.create(StrategyType.POSITIONAL);
        assertNotNull(positional, "PositionalStrategy should not be null");
        assertTrue(positional instanceof PositionalStrategy);

        EvaluationStrategy absolute = EvaluationStrategy.create(StrategyType.ABSOLUTE);
        assertNotNull(absolute, "AbsoluteStrategy should not be null");
        assertTrue(absolute instanceof AbsoluteStrategy);

        EvaluationStrategy mobile = EvaluationStrategy.create(StrategyType.MOBILE);
        assertNotNull(mobile, "MobileStrategy should not be null");
        assertTrue(mobile instanceof MobileStrategy);

        EvaluationStrategy mixte = EvaluationStrategy.create(StrategyType.MIXTE);
        assertNotNull(mixte, "MixteStrategy should not be null");
        assertTrue(mixte instanceof MixteStrategy);
    }

    @Test
    public void testPositionalStrategyEvaluation() {
        PositionalStrategy positional = new PositionalStrategy();

        // Test a corner move (should be high value) - corner = 100
        Position cornerPos = new Position(0, 0);
        List<Position> flipped = new ArrayList<>();

        int cornerScore = positional.evaluate(cornerPos, flipped);
        assertEquals(100, cornerScore, "Corner move should score 100");

        // Test a center move (should be lower)
        Position centerPos = new Position(3, 3);
        int centerScore = positional.evaluate(centerPos, flipped);
        assertEquals(1, centerScore, "Center should score 1");
        assertTrue(cornerScore > centerScore, "Corner should score more than center");
    }

    @Test
    public void testAbsoluteStrategyEvaluation() {
        AbsoluteStrategy absolute = new AbsoluteStrategy();

        Position pos = new Position(2, 2);
        List<Position> flipped = new ArrayList<>();

        // Test with 0 flipped pieces
        int score0 = absolute.evaluate(pos, flipped);
        assertEquals(1, score0, "Score should be 1 for placing without flipping");

        // Test with 3 flipped pieces
        flipped.add(new Position(1, 1));
        flipped.add(new Position(1, 2));
        flipped.add(new Position(1, 3));
        int score3 = absolute.evaluate(pos, flipped);
        assertEquals(4, score3, "Score should be 4 for 3 flipped pieces + 1 placed");
    }

    @Test
    public void testMobileStrategyEvaluation() {
        MobileStrategy mobile = new MobileStrategy();

        Position cornerPos = new Position(0, 0);
        List<Position> flipped = new ArrayList<>();
        int cornerScore = mobile.evaluate(cornerPos, flipped);

        Position centerPos = new Position(3, 3);
        int centerScore = mobile.evaluate(centerPos, flipped);

        // Corner should score higher
        assertTrue(cornerScore > centerScore, "Corner should score higher in mobile strategy");
    }

    @Test
    public void testHumanPlayerReturnsNull() {
        AI humanAI = AbstractAI.createAI(PlayerType.HUMAN, StrategyType.ABSOLUTE, 3, Cell.BLACK);
        assertNull(humanAI, "Human player should return null AI");
    }

    @Test
    public void testMixteStrategyCreation() {
        MixteStrategy mixte = new MixteStrategy();
        assertNotNull(mixte, "MixteStrategy should not be null");

        Position pos = new Position(0, 0); // Corner position
        List<Position> flipped = new ArrayList<>();
        // Corner scores 100 in positional, 1 in absolute, 150 in mobile
        // So mixte = 100 * 0.5 + 1 * 0.3 + 150 * 0.2 = 50 + 0.3 + 30 = 80.3 -> 80

        int score = mixte.evaluate(pos, flipped);
        assertTrue(score > 50, "Mixte strategy should return positive score for corner");
    }
}
