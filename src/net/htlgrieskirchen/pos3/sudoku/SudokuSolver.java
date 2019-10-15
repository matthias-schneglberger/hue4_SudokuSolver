package net.htlgrieskirchen.pos3.sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
        
        
//        ExecutorService executor = Executors.newFixedThreadPool(3);
//        Callable<Boolean> rowChecker = new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                // row checker
//                for (int row = 0; row < 9; row++) {
//                    for (int col = 0; col < 8; col++) {
//                        for (int col2 = col + 1; col2 < 9; col2++) {
//                            if (rawSudoku[row][col] != 0) {
//                                if (rawSudoku[row][col] == rawSudoku[row][col2]) {
//                                    return false;
//                                }
//                            }
//                        }
//                    }
//                }
//                return true;
//            }
//            
//        };
//        //System.out.println("asdf");
//        Future<Boolean> rowChecker_ = executor.submit(rowChecker);
//        executor.shutdown();

        
        
        

        
        
        
        
        
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
            for (int col = 0; col < 9; col += 3){ // row, col is start of the 3 by 3 grid
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

    }

    @Override
    public int[][] solveSudoku(int[][] rawSudoku) {
        sudoku = rawSudoku;
        solve2();
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
