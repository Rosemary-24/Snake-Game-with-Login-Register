package login_register;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class SnakeGamePanel extends JPanel implements ActionListener {

    private final int TILE_SIZE = 25;
    private final int WIDTH = 700;
    private final int HEIGHT = 550;
    private final int ALL_TILES = (WIDTH * HEIGHT) / (TILE_SIZE * TILE_SIZE);
    
    private final int x[] = new int[ALL_TILES];
    private final int y[] = new int[ALL_TILES];
    
    private int snakeLength = 3;
    private int foodX;
    private int foodY;
    
    private boolean left = false;
    private boolean right = true; // start moving right
    private boolean up = false;
    private boolean down = false;
    private boolean running = false;
    
    private Timer timer;
    private Random random;

    public SnakeGamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        random = new Random();
        startGame();
    }

    private void startGame() {
        spawnFood();
        running = true;
        timer = new Timer(150, this);
        timer.start();
    }

    private void spawnFood() {
        foodX = random.nextInt(WIDTH / TILE_SIZE) * TILE_SIZE;
        foodY = random.nextInt(HEIGHT / TILE_SIZE) * TILE_SIZE;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            // Draw food
            g.setColor(Color.RED);
            g.fillRect(foodX, foodY, TILE_SIZE, TILE_SIZE);

            // Draw snake
            for (int i = 0; i < snakeLength; i++) {
                if (i == 0) g.setColor(Color.GREEN);
                else g.setColor(Color.YELLOW);
                g.fillRect(x[i], y[i], TILE_SIZE, TILE_SIZE);
            }
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(msg, (WIDTH - metrics.stringWidth(msg)) / 2, HEIGHT / 2);
        timer.stop();
    }

    private void move() {
        // Move the body
        for (int i = snakeLength; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        // Move the head
        if (left) x[0] -= TILE_SIZE;
        if (right) x[0] += TILE_SIZE;
        if (up) y[0] -= TILE_SIZE;
        if (down) y[0] += TILE_SIZE;
    }

    private void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            snakeLength++;
            spawnFood();
        }
    }

    private void checkCollisions() {
        // Check collision with body
        for (int i = snakeLength; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        // Check collision with walls
        if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (!right) { left = true; up = false; down = false; right = false; }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (!left) { right = true; up = false; down = false; left = false; }
                    break;
                case KeyEvent.VK_UP:
                    if (!down) { up = true; left = false; right = false; down = false; }
                    break;
                case KeyEvent.VK_DOWN:
                    if (!up) { down = true; left = false; right = false; up = false; }
                    break;
            }
        }
    }
}
