package Main;

import gui.GamePanel;
import gui.Window;

public class Main {
	public static void main (String [] args) {
		 GamePanel game = new GamePanel();
		 GameThread gameThread = new GameThread(game);
		 gameThread.start();
		 Window window = new Window(game);
	}
}
