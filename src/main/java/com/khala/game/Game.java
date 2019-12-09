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

    public  String getGameID(){
        return gameID;
    }

    public  List<Integer>  getBoard(){
        return gameBoard;
    }

    public List<Integer> processTurn(Integer startingPitIndex){
        Integer tester = 0;
        while (Boolean.TRUE){
           if(verifyIfValidPit(startingPitIndex)){
                System.out.println(startingPitIndex);
                startingPitIndex = processSingleMove(startingPitIndex);
                tester +=1;
            }else{
               return gameBoard;
           }
        }
        return gameBoard;
    }

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

    public  Integer getNextPit(Integer currentPit){
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


    private Boolean verifyIfValidPit(Integer pitIndex){
        if(pitIndex+1 < gameBoard.size() && pitIndex+1 != gameBoard.size()/2){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }
}
