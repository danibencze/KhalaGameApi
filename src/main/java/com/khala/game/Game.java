package com.khala.game;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    private  String gameID = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
    private  List<Integer> gameBoard = new ArrayList<Integer>();
    private  Integer currentPlayer = 7; // Identifies the player based on the house

    public Game(int noOfStones){
        for (int i = 0; i < (noOfStones*2)+2; i++) {
            if((i+1) % (noOfStones+1) == 0){
                gameBoard.add(0);
            }else{
                gameBoard.add(noOfStones);
            }
        }
    }

    /**
     * @return  4-digit gameID in a string format
     */
    public  String getGameID(){
        return gameID;
    }


    /**
     * @return List Board in its current state
     */
    public  List<Integer>  getBoard(){
        return gameBoard;
    }

    //TODO:Add checkiflandedonempty
    //TODO: Add a check at the end of the function to see if it landed on the player's own khala if so they get another turn
    public List<Integer> processTurn(Integer startingPitIndex) {
        //Check if the game is still accepting moves
        if (checkIfGameOver()){
            return gameBoard;
        }
        //Check if the pitId is valid
        if (verifyIfValidPit(startingPitIndex).equals(Boolean.FALSE)){
            return gameBoard;
        }
        startingPitIndex = processSingleMove(startingPitIndex);
        //Check is the last stone is landed on the current player's khala, if so don't change the player
        //Else we give way to another player
        if (startingPitIndex.equals(currentPlayer)){
            return gameBoard;
        }else{
            checkIfLandedOnEmptyPit(startingPitIndex);
            //Changing player's
            if (currentPlayer == gameBoard.size()){
                currentPlayer = (gameBoard.size())/2;
            }else{
                currentPlayer = gameBoard.size();
            }
        }
        return gameBoard;
    }

    /**
     * Picks up the stones from a single pit and distributes them according to the game rules
     *
     * @param  startingPitIndex  The pitIndex to start the turn on
     * @return List              Board after all moves in the turn has been processed
     */
    public List<Integer> processTurnOld(Integer startingPitIndex){
        int tester = 0;
        if (verifyIfValidPit(startingPitIndex)==Boolean.FALSE){
            return gameBoard;
        }
        while (true){
            checkIfLandedOnEmptyPit(startingPitIndex);
            if (checkIfGameOver()){
                return gameBoard;
            }
            if(verifyIfValidPit(startingPitIndex)){
                System.out.println(startingPitIndex);
                startingPitIndex = processSingleMove(startingPitIndex);
                tester +=1;
            }else{
                if (currentPlayer == gameBoard.size()){
                    currentPlayer = (gameBoard.size())/2;
                }else{
                    currentPlayer = gameBoard.size();
                }
                System.out.println(currentPlayer);
                return gameBoard;
            }
        }
    }

    /**
     * Picks up the stones from a single pit and distributes them according to the game rules
     *
     * @param  pitIndex  The pitIndex to start the move on
     * @return Integer   The pitIndex that the last stone has landed on
     */
    public Integer processSingleMove(Integer pitIndex){
            Integer noOfStonesToDist = gameBoard.get(pitIndex);
            Integer currentPitIndex = pitIndex;
            gameBoard.set(pitIndex,0);
            while(noOfStonesToDist > 0){
                noOfStonesToDist -=1;
                currentPitIndex=getNextPit(currentPitIndex);
                gameBoard.set(currentPitIndex,gameBoard.get(currentPitIndex)+1);
                System.out.println(gameBoard);
            }
            return currentPitIndex;
    }

    /**
     * Gets the next valid pit. Deals with end of board, and to skip the other player's Khala
     *
     * @param  currentPit  the pit that's the previous move ended on
     * @return Integer     The pitindex of the next valid move
     */
    public Integer getNextPit(Integer currentPit){
        if (((currentPit+2)%((gameBoard.size()+1)/2)==0)&&currentPit+2 != currentPlayer){
            if (currentPit+2>=gameBoard.size()){
                return 0;
            }
            return currentPit+2;
        }else if(currentPit+2>=gameBoard.size()+1){
            return 0;
        }
        return currentPit+1;
    }

    /**
     *
     * @param  pitIndex  the index for the next move
     * @return Boolean   if the pit is a valid move for the current player
     */
    private Boolean verifyIfValidPit(Integer pitIndex){
        //Checks if a pit a house or not
        if(pitIndex+1 < gameBoard.size() && pitIndex+1 != gameBoard.size()/2){
            //Checks if the pit contains any stones
            if (gameBoard.get(pitIndex)==0){
                return Boolean.FALSE;
            }
            //Checks if the pit is in the player's collection (Player 1 (7-house) case)
            if ((gameBoard.size()==currentPlayer)&&((currentPlayer/2)<pitIndex)){
                return Boolean.TRUE;
            }
            //Checks if the pit is in the player's collection (Player 2 (14-house) case)
            else if ((currentPlayer == (gameBoard.size()/2)) && (pitIndex<currentPlayer)){
             return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
        }else{
            return Boolean.FALSE;
        }
    }


    /**
     * Checks if one 'pick up' is landed on an empty pit
     * If so, it will capture the opposite pit
     */
    private void checkIfLandedOnEmptyPit(Integer pitIndex) {
        if ((gameBoard.get(pitIndex) == 1)&&verifyIfValidPit(pitIndex)) {
            //capture stone in opposite
            Integer oppositePit = gameBoard.size()-pitIndex-2;
            System.out.println(pitIndex);
            System.out.println(oppositePit);
            gameBoard.set(currentPlayer-1,gameBoard.get(pitIndex)+gameBoard.get(oppositePit));
            gameBoard.set(oppositePit,0);
        }
    }


    /**
     * Checks if the game is over (Either one of the player pits summed up are 0)
     *
     * @return Boolean   If game still running
     */
    private Boolean checkIfGameOver(){
        if (gameBoard.subList(0,(gameBoard.size()/2)-2).stream().mapToInt(Integer::intValue).sum() == 0){
            return Boolean.TRUE;
        }else if (gameBoard.subList((gameBoard.size()/2),gameBoard.size()-2).stream().mapToInt(Integer::intValue).sum() == 0){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }
}
