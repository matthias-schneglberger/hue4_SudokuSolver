package net.htlgrieskirchen.pos3.sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Please enter here an answer to task four between the tags:
 * <answerTask4>
 *    Hier sollte die Antwort auf die Aufgabe 4 stehen.
 * </answerTask4>
 */
public class SudokuSolver implements ISodukoSolver {
    public static int[][] sudoku = new int[9][9];
    public SudokuSolver() {
        //initialize if necessary
    }

    @Override
    public final int[][] readSudoku(File file) {
        int[][] sudoku = new int[9][9];

        // implement this method
        try (
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader)) {

            String line = br.readLine();
            int counter = 0;
            while (line != null) {
                String[] parts = line.split(";");
                int[] partsInt = new int[9];

                for (int i = 0; i < parts.length; i++) {
                    partsInt[i] = Integer.valueOf(parts[i]);
                }

                sudoku[counter] = (int[]) (partsInt);

                line = br.readLine();

                counter++;
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sudoku; // delete this line!
    }

    @Override
    public boolean checkSudoku(int[][] rawSudoku) {
        // row checker
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 8; col++) {
                for (int col2 = col + 1; col2 < 9; col2++) {
                    if (rawSudoku[row][col] != 0) {
                        if (rawSudoku[row][col] == rawSudoku[row][col2]) {
                            return false;
                        }
                    }
                }
            }
        }

        // column checker
        for (int col = 0; col < 9; col++) {
            for (int row = 0; row < 8; row++) {
                for (int row2 = row + 1; row2 < 9; row2++) {
                    if (rawSudoku[row][col] != 0) {
                        if (rawSudoku[row][col] == rawSudoku[row2][col]) {
                            return false;
                        }
                    }
                }
            }
        }

        // grid checker
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) // row, col is start of the 3 by 3 grid
            {
                for (int pos = 0; pos < 8; pos++) {
                    for (int pos2 = pos + 1; pos2 < 9; pos2++) {
                        if (rawSudoku[row + pos % 3][col + pos / 3] != 0) {
                            if (rawSudoku[row + pos % 3][col + pos / 3] == rawSudoku[row + pos2 % 3][col + pos2 / 3]) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
//        boolean isCorrect = true;
//        // implement this method
//        
//        List<Worker_solve> workers = new ArrayList<>();
//        
//        ExecutorService executer = Executors.newFixedThreadPool(9);
//        for(int i = 0; i < 9; i++){
//            Worker_solve worker = new Worker_solve(rawSudoku[i]);
//            workers.add(worker);
//            executer.execute(worker);
//                    
//        }
//        executer.shutdown();
//        
//        try {
//            executer.awaitTermination(10, TimeUnit.SECONDS);
//        }
//        catch (InterruptedException ex) {
//            Logger.getLogger(SudokuSolver.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        for(Worker_solve w : workers){
//            if(!w.isCorrect){
//                isCorrect = false;
//            }
//        }
//        if(isCorrect){
//            
//            workers = new ArrayList<>();
//            executer = Executors.newFixedThreadPool(9);
//            
//            for(int i = 0; i < 9; i++){
//                int[] raw = new int[9];
//                for(int a = 0; a < 9; a++){
//                    raw[a] = rawSudoku[i][a];
//                }
//                
//                Worker_solve worker = new Worker_solve(raw);
//                workers.add(worker);
//                executer.execute(worker);
//                
//                
//            }
//            executer.shutdown();
//
//            try {
//                executer.awaitTermination(10, TimeUnit.SECONDS);
//            }
//            catch (InterruptedException ex) {
//                Logger.getLogger(SudokuSolver.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            for(Worker_solve w : workers){
//                if(!w.isCorrect){
//                    isCorrect = false;
//                }
//            }
//            
//            if(isCorrect){
//                System.out.println("asfasdfasdfasdfasfäöü");
//             for(int row = 0; row < 9; row += 3)
//                for(int col = 0; col < 9; col += 3)
//                   // row, col is start of the 3 by 3 grid
//                   for(int pos = 0; pos < 8; pos++)
//                      for(int pos2 = pos + 1; pos2 < 9; pos2++)
//                         if(rawSudoku[row + pos%3][col + pos/3]==rawSudoku[row + pos2%3][col + pos2/3])
//                            return false;
//                
//                
//                //return true;
//            }
//            
//            
//            
//        }
//        
//        
//        
//        return false; // delete this line!
    }

    @Override
    public int[][] solveSudoku(int[][] rawSudoku) {
        boolean[][] fixedNums = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            for (int a = 0; a < 9; a++) {
                if (rawSudoku[i][a] != 0) {
                    fixedNums[i][a] = true;
                }
            }
        }

        return solve(rawSudoku, fixedNums, 0, 0);
    }

    private int[][] solve(int[][] sudoku, boolean[][] fixed, int posX, int posY) {
        //temp++;
        //System.out.println("Anzahl der Objekte: " + temp);
        Main.printSudoku(sudoku);
        //sudoku[posX][posY] = sudoku[posX][posY]+1;

        if (checkSudoku(sudoku)) {
            if (fixed[posX][posY] == false) {
                if (sudoku[posX][posY] == 0) {
                    sudoku[posX][posY] = 1;
                    return solve(sudoku, fixed, posX, posY);
                }
            }
            if (posY + 1 > 8) {
                if(posX+1 < 9){
                    return solve(sudoku, fixed, posX + 1, 0);
                }
                
            }
            else {
                return solve(sudoku, fixed, posX, posY + 1);
            }
        }
        else {
            //System.out.println("dobini");
            if (fixed[posX][posY] == false) {
                if (sudoku[posX][posY] == 0) {
                    sudoku[posX][posY] = 1;
                    return solve(sudoku, fixed, posX, posY);
                }
                else {
                    int tempNum = sudoku[posX][posY] + 1;
//                    if(tempNum > 9){
//                        sudoku[posX][posY] = 0;
//                        System.out.println("overdrive");
//                        
//                        while(true){
//                            
//                            if(posY -1  >= 0){
//                                if(fixed[posX][posY] == false){
//                                    sudoku[posX][posY-1] = sudoku[posX][posY-1]+1;
//                                    
//                                    
//                                    if(sudoku[posX][posY-1] > 9){
//                                        sudoku[posX][posY-1] = 0;
//                                        return solve(sudoku, fixed, posX, posY);
//                                        
//                                    }
//                                    
//                                }
//                                
//                                
//                            }  
//                            else{
//                                posY = posY-1;
//                            }
//                            
//                            
//                            
//                            if(checkSudoku(sudoku)){
//                                break;
//                            }
//                            
//                        }
//                        
//                        return solve(sudoku, fixed, posX, posY-1);
//                        
//                    }
//                    else{
                        sudoku[posX][posY] = tempNum;
                        return solve(sudoku, fixed, posX, posY);
//                    }
                    
                    
                }
            }
//            if(posY+1 > 8){
//            return solve(sudoku, fixed, posX+1, 0);
//        }
//        else{
//            return solve(sudoku, fixed, posX, posY+1);
//        }

        }

        return sudoku;
    }
    
    public boolean solve2(){
        //System.out.println("asdf");
        for(int i = 0; i < 9; i++){
            //System.out.println("###");
            for(int n = 0; n < 9; n++){
                if(sudoku[i][n] == 0){
                    for(int input = 1; input <= 9; input++){
                        sudoku[i][n] = input;
                        if(checkSudoku(sudoku)){
                            if( solve2()){
                                return true;
                            }
                            else{
                                sudoku[i][n] = 0;
                                
                            }
                        }
                        else{
                            sudoku[i][n] = 0;
                        }
                        
                    }
                    return false;
                }
            }
        }
        //Main.printSudoku(sudoku);
        return true;
    }

    @Override
    public int[][] solveSudokuParallel(int[][] rawSudoku) {

        // implement this method
        return new int[0][0]; // delete this line!
    }
}
