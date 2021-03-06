import java.util.Scanner;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class Maze{
   
    static int rows=10;
    static int cols=10;
    static Coord[][] Maze = new Coord[rows][cols];
    static char spread = '+';
    static boolean DIE = false;
    static int steps = 0;
    
    static Stack<Coord> path = new Stack<Coord>();

    public static void main(String[]args){
        generateMaze();
        System.out.println("Random Maze Generated");
        printMaze();
        bfsSearch(0,0);
        System.out.println();
        if(Maze[rows-1][cols-1].val == spread) System.out.println("Path was found, it made " + steps + " moves. ");
        else{
            System.out.println("No valid path to the End");
        }
        printMaze();

    }

    public static void generateMaze(){
        Random rand = new Random();
        int k = rand.nextInt(10)+1;
        for(int i=0; i < rows; i++){

            for(int j=0; j< cols; j++){
                k=rand.nextInt(100)+1;
                if(i == rows-1 && j == cols-1)  Maze[i][j] = new Coord(i,j,false,'2'); 
                else{
                    if( k > 65){
                        Maze[i][j] = new Coord(i,j,false,'1');
                    }else{
                        Maze[i][j] = new Coord(i,j,false,'0');
                    }
                }
                if(i ==0 && j ==0) Maze[i][j] = new Coord(i,j,false,'0');
                  
                
            }
        }

    }
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
    public static void bfsSearch(int i,int j){
        steps++;
        if(DIE)return;
        
        clearScreen();
        printMaze();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Maze[i][j].val == 1) return;
        Coord p = Maze[i][j];
        path.push(p);
        Maze[i][j].Visisted = true;
        
        if(Maze[rows-1][cols-1].val == spread){
            System.out.print("Found!");
            DIE = true;
            return;
        }
        if(i+1 < rows && Maze[i+1][j].val != '1' && Maze[i+1][j].Visisted == false){
            Maze[i+1][j].val = spread;
            bfsSearch(i+1,j);
        }
        if(i - 1 >= 0 && Maze[i-1][j].val != '1' && Maze[i-1][j].Visisted == false){
            Maze[i-1][j].val = spread;
            bfsSearch(i-1,j);
        }
        if(j + 1 < cols && Maze[i][j+1].val != '1' && Maze[i][j+1].Visisted == false){
            Maze[i][j+1].val = spread;
            bfsSearch(i,j+1);
        }
        if(j - 1 >= 0 && Maze[i][j-1].val != '1' && Maze[i][j-1].Visisted == false){
            Maze[i][j-1].val = spread;
            bfsSearch(i,j-1);
        }
       return;
        
    }

    public static void printMaze(){
        for(int i=0; i < rows; i ++){
            for(int j=0; j< cols; j++){
                Coord p = Maze[i][j];
                System.out.print(p.val);
            }
            System.out.println();
        }
    }
}