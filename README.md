# What is it?

Minesweeper is a common game that the goal is find all the bombs inside a board without explode them. The game has some basics commands: 

1. Choose a field, typing the number of the row and the column; 
2. Open a chosen field; 
3. Marker ou unmarker a chosen field; 
4. Any moment you can type the word **"leave"** to leave the game;

You need to try to marker all fields with bombs and open the free fields, if you open a field that contains a bomb, you lost the game. 

# How to play?

First you can choose the dimensions of the game and the number of the bombs,  you can do that in the class **Application**.  In the example below, we have a board with 6 rows, 6 columns and 3 bombs.

```  
import minesweeper.model.Board;  
import minesweeper.view.Terminal;  
  
public class Application {  
  
    public static void main(String[] args) {  
  
        Board board = new Board(6, 6, 3);  
 new Terminal(board);  
  }  
}
```

You can make the board bigger or smaller and put as many bombs as you want, but remember: as more bombs, harder the game will be! 

After that, you can run the application and the game will start.
In the beginning of the game you will see the board with respective rows and columns indicated by numbers. Here all fields in the game are closed, indicates by the symbol **"?"**.
```
   0  1  2  3  4  5 
0  ?  ?  ?  ?  ?  ? 
1  ?  ?  ?  ?  ?  ? 
2  ?  ?  ?  ?  ?  ? 
3  ?  ?  ?  ?  ?  ? 
4  ?  ?  ?  ?  ?  ? 
5  ?  ?  ?  ?  ?  ? 
```

To play you need to answer the displayed message: **"Type (x, y):"**. As **"x"** corresponds to a row and **"y"** to a column, you can choose an exact field typing the related number of the row and of the column separated (always) by a comma and type **"enter"**.
```
   0  1  2  3  4  5 
0  ?  ?  ?  ?  ?  ? 
1  ?  ?  ?  ?  ?  ? 
2  ?  ?  ?  ?  ?  ? 
3  ?  ?  ?  ?  ?  ? 
4  ?  ?  ?  ?  ?  ? 
5  ?  ?  ?  ?  ?  ? 

Type (x, y): 0, 0
```

Then you need to choose what you want to do with the field. You can choose between two options: to open the field type **"1"** + **"enter"** and to alternate between marked/unmarked type **"2"** + **"enter"**. 
```
   0  1  2  3  4  5 
0  ?  ?  ?  ?  ?  ? 
1  ?  ?  ?  ?  ?  ? 
2  ?  ?  ?  ?  ?  ? 
3  ?  ?  ?  ?  ?  ? 
4  ?  ?  ?  ?  ?  ? 
5  ?  ?  ?  ?  ?  ? 

Type (x, y): 0, 0
1 - To open or 2 - To (Un)Marker: 1
```
You should marker a field when you know that there has a bomb. Also, a marked field (displaying the symbol **"x"** cannot be opened, to do that you need first to alternate the field to unmarked.

When a field shows you a number, this means that the field is opened and the number corresponds to the number of bombs around it. Use this information to help you to find the bombs **;)**
If the field is blank, this means that the field is opened and there is no bomb around.

```
   0  1  2  3  4  5 
0        1  ?  ?  ? 
1        1  ?  ?  ? 
2  1  1  2  ?  ?  ? 
3  2  x  ?  ?  ?  ? 
4  ?  ?  ?  ?  ?  ? 
5  ?  ?  ?  ?  ?  ? 

Type (x, y): 1, 3
```

Here is where the fun begins! Try to marker all bombs and open all other fields without a explosion. If you open a field with a bomb, you lose and the game will show you where were all the hidden bombs by a symbol **" * "**. In the end of the game you can restart it again answering a simple **Y**es/**N**o question:
```
   0  1  2  3  4  5 
0        1  1  1    
1        1  *  1    
2  1  1  2  1  1    
3  2  x  2          
4  2  *  2          
5  1  1  1          

Game over!
Do you want to start a new game? (Y/N) 
N

Process finished with exit code 0
```