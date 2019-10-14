package net.htlgrieskirchen.pos3.sudoku;


import java.io.File;

public class Main {
    public static void main(String[] args) {
        SudokuSolver ss = new SudokuSolver();
        int[][] input = ss.readSudoku(new File("3_sudoku_level2.csv"));
        
        System.out.println(">--- ORIGINAL ---");
        printSudoku(input);
        
        
        int[][] output = ss.solveSudoku(input);
        System.out.println(">--- SOLUTION ---");
        printSudoku(output);
        
        
        System.out.println(">----------------");
        //System.out.println("SOLVED    = " + ss.checkSudoku(output));
        System.out.println(">----------------");
    }
    
    public static void printSudoku(int[][] sudoku){
        //│┌┐└┘├┤┬┴┼─
        //║╔╕╖╗╘╙╚╛╜╝╞╟╠╡╢╣╤╥╦╧╨╩╪╬╫▬
        System.out.println("┌──┬──┬──┬──┬──┬──┬──┬──┬──┐");
        for(int i = 0; i < 9; i++){
            
            for(int n = 0; n < 9; n++){
                System.out.print("│");
                String num = String.valueOf(sudoku[i][n]);
                if(sudoku[i][n] == (0)){
                    num = " ";
                }
                
                System.out.print("  " + num + "   ");
                
            }
            System.out.println("│");
            
            if(i != 8){
                System.out.println("├──┼──┼──┼──┼──┼──┼──┼──┼──┤");
            }
            
        }
        System.out.println("└──┴──┴──┴──┴──┴──┴──┴──┴──┘");
    }
}
