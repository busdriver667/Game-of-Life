package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Main.GameThread;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton start;
	private JButton pause;
	private PlayPanel area;
	private JTextField textField;
	private JLabel info;
	int n; 
	public static int tileSize;
	public GamePanel() {
		this.setRequestFocusEnabled(true);
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		this.setBackground(Color.GRAY);
		tileSize = 20;
		
		initButtons();
		this.add(start);
		this.add(pause);
		
		initTextField();
		this.add(textField);
		
		initLabel();
		this.add(info);
		
		area = new PlayPanel();
		area.setLocation(0, 0);
		this.add(area);		
	}
	//start and stop buttons will control the game
	public void initButtons() {
		start = new JButton("START");
		start.setBounds(210, 500, 80, 40);
		start.setBorderPainted(false);
		start.setFocusPainted(false);
		start.setBackground(Color.WHITE);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!GameThread.running) {
					start.setText("STOP");
					pause.setText("PAUSE");
					GameThread.running = true;
					textField.setEnabled(false);
					area.applyClicks();
				} else {
					start.setText("START");
					pause.setText("PAUSE");
					GameThread.running = false;
					textField.setEnabled(true);
					area.reset();
				}
			}
		});
		//pause button to pause on a frame (not fully working, currently restarts the game)
		pause = new JButton("PAUSE");
		pause.setBounds(335, 500, 80, 40);
		pause.setBorderPainted(false);
		pause.setFocusPainted(false);
		pause.setBackground(Color.WHITE);
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (start.getText().equals("STOP")) { 
					if (GameThread.running) { 					
						pause.setText("UNPAUSE");
						GameThread.running = false;
					} else { 
						pause.setText("PAUSE");
						GameThread.running = true;
					}
				}
			}
		});
	}
	
	public void initTextField() {
		textField = new JTextField();
		textField.setBounds(155, 505, 50, 30);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					n = Integer.parseInt(textField.getText());
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Field size must be an integer");
				}
				textField.setText("");
				
				tileSize = n > 250 ? 250 : n;
				tileSize = n < 10 ? 10 : n;
				area.reset();
				//tileSize = n;
			}
		});
	}
	
	public void initLabel() {
		info = new JLabel("<html> Enter tile size between 10 and 250 </html>");
		info.setBounds(5, 475, 150, 90);
	}
	
	public void update() {
		area.repaint();
	}
}
