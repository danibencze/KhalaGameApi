package com.khala.game;

public class Runner {
    public static void main(String[] args){
        GameMaster gameStore = new GameMaster();
        System.out.println(gameStore.newGame(6));
        System.out.println(gameStore.newGame(6));
        System.out.println(gameStore.newGame(6));
        System.out.println(gameStore.getAllGames());
        Game test = new Game(6);
        System.out.println(test.getBoard());
        //test.processTurn(1);
        System.out.println(test.getBoard());
    }
}
