package com.khala.game;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Integer noOfStones = 6;
    Game testGame = new Game(noOfStones);

    @Test
    void getGameID() {
        assertTrue((Integer.parseInt(testGame.getGameID()) >= 1000)&& Integer.parseInt(testGame.getGameID()) <= 9999);
    }

    @Test
    void getBoard() {
        assertEquals(testGame.getBoard().size(), (noOfStones * 2) + 2);
    }

    @Test
    void processTurn() {
    }

    @Test
    void processSingleMove() {
        assertEquals(7,testGame.processSingleMove(1));
    }

    @Test
    void getNextPit() {
        assertEquals(0,testGame.getNextPit(12));
    }
}