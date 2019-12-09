package com.khala.game;

public class Runner {
    public static void main(String[] args){
        GameMaster gameStore = new GameMaster();
        System.out.println(gameStore.newGame(6));
        String id = gameStore.newGame(6);
        System.out.println(gameStore.newGame(6));
        System.out.println(gameStore.getAllGames());
        System.out.println(gameStore.getGame(id));
        gameStore.getGame(id).processTurn(1);
        gameStore.getGame(id).processTurn(8);
        gameStore.getGame(id).processTurn(0);
        gameStore.getGame(id).processTurn(9);
        gameStore.getGame(id).processTurn(1);
        gameStore.getGame(id).processTurn(9);
        System.out.println(gameStore.getGame(id).getBoard());
    }
}
