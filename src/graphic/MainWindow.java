package graphic;

import algorithm.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
public class MainWindow extends JFrame{
	static int ROW;
	static int COLLUM;
	static final int WIDTH = 800;
	static final int HEIGHT = 600;
	
	private Display display;

	
	public MainWindow(int ROW, int COLLUM) {
		super();
		this.ROW = ROW;
		this.COLLUM = COLLUM;
		setSize(WIDTH,HEIGHT+20);
		setTitle("A* Algorithm");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		//add control
		
		display = new Display(ROW,COLLUM);
		add(display);

		setVisible(true);
	}
}
