package com.khala.api;

/**
 *
 * @author Daniel Bencze
 * @date 06/12/2019
 *
 */

import com.khala.game.Game;
import com.khala.game.GameMaster;

import static spark.Spark.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class RestApi {
    private static   GameMaster gameStore = new GameMaster();
    private static   String exposedIP;

    static {
        try {
            exposedIP = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            exposedIP = "0.0.0.0";
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        spark.Spark.ipAddress(exposedIP);
        get("/healthcheck", (request, response) -> {
            return "OK";
        });
        get("/games/:id", (request, response) -> {
            return gameStore.getGame(request.params(":id")).getBoard();
        });
        get("/games", (request, response) -> {
            response.status(201);
            response.type("application/json");
            String gameID = createNewGame();
            return "{\"gameid\":\""+gameID+"\", \"uri\":\""+endpointBuilder("/games/"+gameID)+"\"}";
        });
        get("/games/:id/pitsall/:pitid", (request, response) -> {
            return Integer.parseInt(request.params(":pitid"));
        });
    }

    private static String createNewGame(){
        /**
         * createNewGame
         * @return Innteger gameID
         */
         return gameStore.newGame(6);
    }

    private static Map<String, Game> getAllGame(){
        return gameStore.getAllGames();
    }

    private static String endpointBuilder(String route){
        return new StringBuilder().append("http://").append(exposedIP).append(":").append(port()).append(route).toString();
    }
}