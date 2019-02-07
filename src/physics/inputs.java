package physics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class inputs implements MouseListener {
	
	//array needs to be expandable so i am using 2 ArrayLists instead of a 2D array
	private ArrayList<Integer> x = new ArrayList<Integer>();
	private ArrayList<Integer> y = new ArrayList<Integer>();
	
	public void mousePressed(MouseEvent e) {
		x.add(e.getX());
		y.add(e.getY());
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	public ArrayList<Integer> getXList() {
		return x;
	}
	
	public ArrayList<Integer> getYList() {
		return y;
	}
	
}
