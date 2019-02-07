package Main;

import gui.GamePanel;

public class GameThread extends Thread {

	public static boolean running;
	public static final int WAIT = 1000;
	private GamePanel gamePanel;
	
	public GameThread(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		running = false;		
	}
	
	public void run() {
		while (true) {
			try {
				gamePanel.update();
				Thread.sleep(WAIT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
