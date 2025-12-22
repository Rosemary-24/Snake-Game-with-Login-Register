package login_register;
// denotes that app.java is in login_register package 
import javax.swing.*;
// tells jaba to use swing GUI classes in the filr


public class App {
    public static void main(String[] args) throws Exception {
        // defining variables -> setting boardwiidth and height to a set size
        int boardWidth = 600;
        int boardHeight = boardWidth;
// creating a window
        JFrame frame = new JFrame("Snake");
        // making the window visible in the scree, without this line the window 
        //exists in memory but doesnt appear on screen
        frame.setVisible(true);
        // sets the frame side using variable defined earlier
	frame.setSize(boardWidth, boardHeight);
        // positions the window in the center of the screen -> null means center relative to the screen 
        frame.setLocationRelativeTo(null);
        // stops from resizing the window -> prevent breaking snake movement, collision logic and grid alignment
        frame.setResizable(false);
        // when the user clicks "x" the program terminates 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
// creating a custom game panel -> draws snake, handles keyboard input
        // runs the game loop , detect collision
        SnakeGamePanel snakeGame = new SnakeGamePanel(boardWidth, boardHeight);
        
        // places snakeGamePanel inside the Jframe 
        frame.add(snakeGame);
        // automatically resizes the frame, fits to the preffered size of its content 
        frame.pack();
        // makes sure the panel receives keyboars input
        snakeGame.requestFocus();
    }
}
