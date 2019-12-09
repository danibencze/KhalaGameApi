package com.khala.game;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class Player {
    private List<Integer> playerPits  = new ArrayList<>();
    int[] range = IntStream.iterate(1, n -> {playerPits.add(n);return n + 1;}).limit(7).toArray();

    public Integer getKhalaCount(){
        return playerPits.get(6);
    }

    public Boolean isPlayerBoardEmpty(){
        if (playerPits.subList(0,5).stream().mapToInt(Integer::intValue).sum() == 0){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }
}


