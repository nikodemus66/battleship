/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.view;
    import javax.swing.*;
import java.awt.*;
import java.util.Random;
/**
 *
 * @author brian
 */
public class Map extends JPanel{

    public static final Color SHIP = new Color(214,217,223);
    public static final Color OCEAN = new Color(0,0,153);

    public static final Color[] TERRAIN = {
        SHIP,
        OCEAN,
    };

    public static final int NUM_ROWS = 10;
    public static final int NUM_COLS = 10;
    
    public static final int PREFERRED_GRID_SIZE_PIXELS = 20;

    private final Color[][] terrainGrid;

    public Map(){
        this.terrainGrid = new Color[NUM_ROWS][NUM_COLS];
        Random r = new Random();
        // Randomize the terrain
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                int randomTerrainIndex = r.nextInt(TERRAIN.length);
                Color randomColor = TERRAIN[randomTerrainIndex];
                this.terrainGrid[i][j] = randomColor;
            }
        }
        int preferredWidth = NUM_COLS * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = NUM_ROWS * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }
        @Override
    public void paintComponent(Graphics g) {
        // Important to call super class method
        super.paintComponent(g);
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid
        int rectWidth = getWidth() / NUM_COLS;
        int rectHeight = getHeight() / NUM_ROWS;

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                // Upper left corner of this terrain rect
                int x = i * rectWidth;
                int y = j * rectHeight;
                Color terrainColor = terrainGrid[i][j];
                g.setColor(terrainColor);
                g.fillRect(x, y, rectWidth, rectHeight);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Game");
                Map map = new Map();
                frame.add(map);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
