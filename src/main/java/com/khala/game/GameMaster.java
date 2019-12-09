package com.khala.game;

import java.util.HashMap;
import java.util.Map;

public class GameMaster {
        private HashMap<String,Game> allGames;

        public GameMaster() {
            allGames = new HashMap<>();
        }

        public String newGame(int noOfStones) {
            Game createGame = new Game(noOfStones);
            allGames.put(createGame.getGameID(),createGame);
            return createGame.getGameID();
        }

        public Map<String,Game> getAllGames(){
            return allGames;
        }

        public Game getGame(String gameId){
            return allGames.get(gameId);
        }

}
