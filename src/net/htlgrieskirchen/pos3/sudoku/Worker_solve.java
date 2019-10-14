/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.htlgrieskirchen.pos3.sudoku;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author matth
 */
public class Worker_solve implements Runnable{
    
    int[] nums = new int[9];
    boolean isCorrect = false;
    
    public Worker_solve(int[] nums){
        this.nums = nums;
    }
    
    @Override
    public void run() {
        Set<Integer> checkList = new TreeSet<>();
        Arrays.stream(nums).forEach(n -> checkList.add(n));
        isCorrect = checkList.size() == 9;
    }
    
    public boolean getIsCorrect(){
        return isCorrect;
    }
    
}
