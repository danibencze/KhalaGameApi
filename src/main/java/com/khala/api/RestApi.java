package com.khala.api;

/**
 *
 * @author Daniel Bencze
 * @date 06/12/2019
 *
 */

import com.khala.game.Game;
import com.khala.game.GameMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Spark;

import static spark.Spark.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


public class RestApi {
    private static   GameMaster gameStore = new GameMaster();
    private static   String exposedIP;
    //slf4j logger init
    private static Logger logger = LoggerFactory.getLogger(RestApi.class);

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

        //Catching all errors what could appear in the API
        spark.Spark.exception(Exception.class, (exception, request, response) -> logger.error(String.valueOf(exception)));
        //Log every request
        before((request, response) -> {
            logger.info(requestInfoToString(request));
        });

        get("/healthcheck", (request, response) -> {
            return "OK";
        });
        //TODO:Add current player
        //TODO:Add input validation
        get("/games/:id", (request, response) -> {
            response.type("application/json");
            if (request.params(":id").length()!=4){
                return "{\"error\":\"Please enter a valid value\"}";
            }
            return gameStore.createJsonGameBoard(request.params(":id"));
        });
        //OneOfThefinalendpoints
        get("/games", (request, response) -> {
            response.status(201);
            response.type("application/json");
            String gameID = createNewGame();
            return "{\"gameid\":\""+gameID+"\", \"uri\":\""+endpointBuilder("/games/"+gameID)+"\"}";
        });
        get("/games/:gameId/pitid/:pitId", (request, response) -> {
            response.type("application/json");
            // Making sure that the user can't input anything that they shouldn't
            try {
                //As the game deals with indexes from 0 the input pitId needs to be deducted by one
                gameStore.getGame(request.params(":gameId")).processTurn(Integer.parseInt(request.params(":pitId"))-1);
                return gameStore.createJsonGameBoard(request.params(":gameId"));
            }
            catch(Exception e) {
                response.status(400);
                return "{\"error\":\"Please enter a valid value\"}";
            }

        });
    }

    /**
     * createNewGame
     * @return Integer gameID
     */
    private static String createNewGame(){
         return gameStore.newGame(6);
    }

    /**
     * getAllGames
     * @return Map<GameID, GameObj>
     */
    private static Map<String, Game> getAllGames(){
        return gameStore.getAllGames();
    }

    /**
     * createNewGame
     * @param route The route to generate endpoint to ("/test")
     * @return String full url to the specific endpoint
     */
    private static String endpointBuilder(String route){
        return new StringBuilder().append("http://").append(exposedIP).append(":").append(port()).append(route).toString();
    }

    /**
     * createNewGame
     * @param request spark.Spark request object
     * @return A loggable trsing for the request
     */
    private static String requestInfoToString(Request request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.requestMethod());
        sb.append(" " + request.url());
        sb.append(" " + request.body());
        return sb.toString();
    }
}