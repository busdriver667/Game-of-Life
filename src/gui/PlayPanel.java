package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.GameThread;
import physics.inputs;

public class PlayPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public static final int PLAY_HEIGHT = 500;
	private inputs in = new inputs();
	private boolean[][] bools;
	int scale;
	public PlayPanel() {
		this.setSize(Window.WIDTH, PLAY_HEIGHT);
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(null);
		this.setDoubleBuffered(true);
		this.addMouseListener(in);
		scale = 20;
		initBools();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//if game is running we want to continue applying logic
		if (GameThread.running)
			applyLogic();
		else
			initBools();
			applyClicks();
		//draw our array of cells and fill based on whether they are alive or dead
		for(int i = 0; i < bools[0].length * scale; i +=scale) {
			for(int j = 0; j < bools[1].length * scale; j +=scale) {
				Rectangle r = new Rectangle(i, j, scale, scale);
				if (bools[i/scale][j/scale])
					g2.fill(r);
				else
					g2.draw(r);
			}
		}
		
	}
	
	public void reset() {
		initBools();
		scale = GamePanel.tileSize;
	}
	//create array the size of the n x n where n is the number of cells, all cells are dead intially
	private void initBools() {
		bools = new boolean[PLAY_HEIGHT/scale + 1][PLAY_HEIGHT/scale + 1];
		for (int i = 0; i < bools[0].length; i++) {
			for(int j = 0; j < bools[1].length; j++) {
				bools[i][j] = false;
			}
		}
	}
	
	public void applyClicks() {
		if (GameThread.running)
			return;
		ArrayList<Integer> X = in.getXList();
		ArrayList<Integer> Y = in.getYList();
		//use X and Y pos to determine which cell was clicked
		for (int i = 0; i < X.size(); i++) {
			bools[(int)(X.get(i) / scale)][(int)(Y.get(i) / scale)] = !bools[(int)(X.get(i) / scale)][(int)(Y.get(i) / scale)];
		}
	}
	
	public void applyLogic() {
		boolean newVal;
		boolean[][] newB = new boolean[bools[0].length][bools[1].length];
		ArrayList<Boolean> neighbours = new ArrayList<Boolean>();
		//loop through our array of booleans(tiles) to determine whether each tile should live or die
		for (int i = 0; i < bools[0].length - 1; i++) {
			for (int j = 0; j < bools[1].length - 1; j++) {
				//if top left corner
				if (i == 0 && j == 0) {
					
					for (int m = i; m <= i + 1; m++) 
						for (int n = j; n <= j + 1; n++) 
							if (!(m == i && j == i))
								if (bools[m][n])
									neighbours.add(true);
						
				//if top/left edge	
				} else if (i == 0 || j == 0) {
					
					for (int m = (i == 0 ? i : i - 1); m <= i + 1; m++) 
						for (int n = (j == 0 ? j : j - 1); n <= j + 1; n++) 
							if (!(m == i && j == i))
								if (bools[m][n])
									neighbours.add(true);
						
					
				//if bottom right corner	
				} else if (i == bools[0].length && j == bools[1].length) {
					
					for (int m = i - 1; m <= i; m++) 
						for (int n = i - 1; n <= j; n++) 
							if (!(m == i && j == i))
								if (bools[m][n])
									neighbours.add(true);
					
				//if bottom/right edge	
				} else if (i == bools[0].length - 1 || j == bools[1].length - 1) {
					
					for (int m = i - 1; m <= (i == bools[0].length - 1 ? i : i + 1); m++) 
						for (int n = j - 1; n <= (j == bools[0].length - 1 ? j : j + 1); n++) 
							if (!(m == i && j == i))
								if (bools[m][n])
									neighbours.add(true);
				//rest of grid
				} else {
					for (int m = i - 1; m <= i + 1; m++) {
						for (int n = j - 1; n <= j + 1; n++) {
							if (!(m == i && j == i))
								if (bools[m][n])
									neighbours.add(true);
						}
					}
				}
			//update values in our new array based on the number of neighbours found
			if (!newB[i][j] && neighbours.size() == 3)
				newB[i][j] = true;
			else if (newB[i][j] && neighbours.size() == 2 || neighbours.size() == 3)
				newB[i][j] = true;
			else if (newB[i][j] && neighbours.size() < 2 || neighbours.size() > 3)
				newB[i][j] = false;
			neighbours = new ArrayList<Boolean>();	
			}
		}
		
		for (int i = 0; i < bools[0].length - 1; i++) 
			for (int j = 0; j < bools[1].length - 1; j++) 
				bools[i][j] = newB[i][j];
			
		
	}
}
