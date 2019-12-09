package com.khala.game;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameMaster {
        //A collection of Games
        private HashMap<String,Game> allGames;

        public GameMaster() {
            allGames = new HashMap<>();
        }

        /**
         * Initialises a new game with a specific no of stones and stores it in it's allGames Map
         *
         * @param  noOfStones  No of stones that the game should be initialised with
         * @return Integer     Unique 4-digit Game ID
         */
        //TODO: Have to check if the ID already exists
        public String newGame(int noOfStones) {
            Game createGame = new Game(noOfStones);
            allGames.put(createGame.getGameID(),createGame);
            return createGame.getGameID();
        }


        /**
         * Returns with all games stored in it's map
         *
         * @return Map<String,Game>   A map with all games, Keys being gameID, values being Game object references
         */
        public Map<String,Game> getAllGames(){
            return allGames;
        }


        /**
         * Retrieves a specific game from memory based on it's ID
         * @param gameId    The unique ID of the game to be retrieved
         * @return Game     Game Object reference
         */
        public Game getGame(String gameId){
            return allGames.get(gameId);
        }

        public JSONObject createJsonGameBoard(String gameId) throws JSONException {
            JSONObject returner = new JSONObject();

            List<Integer> localBoard =allGames.get(gameId).getBoard();
            for (int i = 0; i <= localBoard.size()-1; i +=1) {
                returner.put(Integer.toString(i+1), Integer.toString(localBoard.get(i)));
            }
            return returner;
        }

}
