import java.util.Scanner;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class Maze_V2{
   
    
    static char spread = 'X';
    static boolean DIE = false;
    static int steps = 0;

    static int startX=-1;
    static int startY=-1;
    static int endX=-1;
    static int endY=-1;

    static int speed =0;
    static int diff=0;
    static boolean check = true;
    
    static Stack<Coord> path = new Stack<Coord>();





    public static void main(String[]args){
        Scanner input = new Scanner(System.in);

        System.out.print("Width size of maze? : ");
        int rows = input.nextInt();

        System.out.print("Height size of maze? : ");
        int cols = input.nextInt();
        previewMaze(rows, cols);
       
        System.out.print("Difficult of Maze? 1-10 : ");
        diff = input.nextInt();
       
        

        System.out.print("Start X and Y Coord? Input as x,y : ");
        String placeHolder = input.next();
        checkXY(placeHolder,rows,cols);
        previewMaze(rows, cols);

        System.out.print("End X and Y Coord? Input as x,y : ");
        placeHolder = input.next();
        checkXY(placeHolder,rows,cols);
        previewMaze(rows, cols);
       
        System.out.print("Speed in Milliseconds for spread: ");
        speed = input.nextInt();

        Coord[][] Maze = new Coord[rows][cols];
        generateMaze(Maze,rows,cols);
        input.close();

        
        bfsSearch(startX,startY,Maze,rows,cols);
        System.out.println();
        if(Maze[endX][endY].val == spread) System.out.println("Path was found, it made " + steps + " moves. ");
        else{
            System.out.println("No valid path to the End");
        }
        printMaze(Maze,rows,cols);

    }





    public static void generateMaze(Coord [][] Maze ,int rows,int cols){
        Random rand = new Random();
        int k = rand.nextInt(10)+1;
        for(int i=0; i < rows; i++){

            for(int j=0; j< cols; j++){
                k=rand.nextInt(100)+1;
                if(i == endX && j == endY)  Maze[i][j] = new Coord(i,j,false,'0'); 
                else{
                    if( k < diff * 4){
                        Maze[i][j] = new Coord(i,j,false,'1');
                    }else{
                        Maze[i][j] = new Coord(i,j,false,'*');
                    }
                }
                if(i ==startX && j ==startY) Maze[i][j] = new Coord(i,j,false,'0');
                  
                
            }
        }

    }





    public static void checkXY(String s,int rows,int cols){
        Scanner input = new Scanner(System.in);
        if(!s.contains(",")){
            System.out.print("Invalid Input, try again: x,y ");
            s=input.nextLine();
            checkXY(s, rows, cols);
        }
        String [] temp = s.split(",");
        int tempY=Integer.valueOf(temp[0])-1;
        int tempX=Integer.valueOf(temp[1])-1;
        if(tempX > rows || tempY > cols){
            System.out.print("Invalid Input, try again: x,y ");
            s=input.nextLine();
            checkXY(s, rows, cols);
            
        }else{
            if(check){
                startY = tempY;
                startX = tempX;
                check = false;
            }else{
                endY = tempY;
                endX = tempX;
            }
        }
    }





    public static void previewMaze(int rows, int cols){
       printHorz(cols);
        System.out.println("");
        for(int i=0; i<rows; i++){
            System.out.print("                               ");
            System.out.print("|");
            
            for(int j=0; j<cols; j++){
                if((i == startX && startY == j) || (i == endX && j == endY)) System.out.print(ConsoleColors.RED + "0" + ConsoleColors.RESET);
                else{
                    System.out.print("*");
                }
            }
            System.out.print("|");
            System.out.println("");
        }
        printHorz(cols);
        System.out.println();

    }



    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  






    public static void bfsSearch(int i,int j, Coord[][] Maze, int rows, int cols){
        steps++;
        if(DIE)return;
        if(steps % 3 == 0){
        printMaze(Maze, rows, cols);
        
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       
            
            clearScreen();
        }
        if(Maze[i][j].val == 1) return;
        Coord p = Maze[i][j];
        path.push(p);
        Maze[i][j].Visisted = true;
        
        if(Maze[endX][endY].val == spread){
            System.out.print("Found!");
            DIE = true;
            return;
        }

        if(i+1 < rows && Maze[i+1][j].val != '1' && Maze[i+1][j].Visisted == false){
            Maze[i+1][j].val = spread;
            bfsSearch(i+1,j,Maze,rows,cols);
        }
        if(i - 1 >= 0 && Maze[i-1][j].val != '1' && Maze[i-1][j].Visisted == false){
            Maze[i-1][j].val = spread;
            bfsSearch(i-1,j,Maze,rows,cols);
        }
        if(j + 1 < cols && Maze[i][j+1].val != '1' && Maze[i][j+1].Visisted == false){
            Maze[i][j+1].val = spread;
            bfsSearch(i,j+1,Maze,rows,cols);
        }
        if(j - 1 >= 0 && Maze[i][j-1].val != '1' && Maze[i][j-1].Visisted == false){
            Maze[i][j-1].val = spread;
            bfsSearch(i,j-1,Maze,rows,cols);
        }
       return;
        
    }




    public static void printHorz(int cols){
        System.out.print("                               ");
        for(int i=0; i < cols+2; i++){
            System.out.print("_");
        }
    }




    public static void printMaze(Coord[][] Maze, int rows, int cols){
        printHorz(cols);
        System.out.println("");
        for(int i=0; i < rows; i ++){
            System.out.print("                               ");
            System.out.print("|");
            for(int j=0; j< cols; j++){
                
                Coord p = Maze[i][j];
                if((i == startX && startY == j) || (i == endX && j == endY)) System.out.print(ConsoleColors.RED + p.val + ConsoleColors.RESET);
                else{

                
                if(Maze[i][j].val == spread){
                    System.out.print(ConsoleColors.GREEN + p.val + ConsoleColors.RESET);
                }else if(Maze[i][j].val == '1'){
                    System.out.print(ConsoleColors.WHITE+p.val + ConsoleColors.RESET);
                }else{
                System.out.print(ConsoleColors.BLACK+p.val + ConsoleColors.RESET);
                }
            }
            }
            System.out.print("|");
            System.out.println();
        }
        printHorz(cols);
    }
}