package graphic;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;

import javax.swing.*;

import algorithm.*;
public class Display extends JPanel implements Runnable,ActionListener,MouseListener{
	Thread thread;
	Algorithm_ForUIDataInput algorithm;
	
	private JButton bt_sourcetObj;
	private JButton bt_destinationObj;
	private JButton bt_obstacle;
	private JButton bt_Start;
	private JButton bt_Reset;
	private JButton bt_Next;
	private JButton bt_Auto;
	
	private final int WIDTH = 600;
	private final int HEIGHT = 600;
	private int ROW;
	private int COLLUM;
	public static int color_matrix[][];
	
	private final int value_empty = 0;
	private final int value_Source = 1;
	private final int value_destination = 2;
	private final int value_obstacle = 3;
	private final int value_near_node = 4;
	private final int value_chosed_node = 5;
	private final int value_special_node = 6;

	private int valueNow=value_empty;
	
	private Point source = null;
	private Point destination = null;
	
	private Color value_color_arr[] = {Color.WHITE, Color.BLUE, Color.YELLOW, Color.BLACK, Color.PINK, Color.RED,new Color(149, 50, 211)};
	private Color colorNow = value_color_arr[value_empty];
	int square = WIDTH/MainWindow.ROW;
	int mouseSquare = 24;
	int mousePadding = mouseSquare/2;
	private boolean isHasSource = false;
	private boolean isHasDestination = false;
	public static boolean allowToRun = true;
	public static boolean singleModel = true;
	public Display(int ROW, int COLLUM) {
		this.ROW = ROW;
		this.COLLUM = COLLUM;
		this.color_matrix = new int[ROW][COLLUM];
		square = WIDTH/ROW;
		
		thread = new Thread(this);
		setLayout(null);
		addButton();
		addMouseListener(this);
		
		thread.start();

	}
	
	public void paintComponent(Graphics g) {
		
		paintMatrix(g);
		paintMouse(g);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == bt_sourcetObj) {
			readyForPaintSource();
		}else if(arg0.getSource() == bt_destinationObj) {
			readyForPaintDestination();
		}else if(arg0.getSource() == bt_obstacle) {
			readyForPaintObstacle();
		}else if(arg0.getSource() == bt_Start) {
			StartPathFinding_UsingA_star();
		}else if(arg0.getSource() == bt_Reset) {
			Reset();
		}else if(arg0.getSource() == bt_Next) {
			 Next();
		}else if(arg0.getSource() == bt_Auto) {
			System.out.println("AUto");

			AutoFindPath();
		}
	}
	
	private void Reset() {
		for(int i=0; i<ROW; i++) Arrays.fill(color_matrix[i], 0);
		isHasSource = false;
		isHasDestination = false;
		valueNow=value_empty;
		colorNow = value_color_arr[value_empty];

	}

	@Override
	public void mouseClicked(MouseEvent ev) {
		if(isMouseWithinPanel()) {
			Point p = getMousePosition_inPanel();
			int j = (int)(p.getX() / square);
			int i = (int)(p.getY() / square);
			if(color_matrix[i][j]==value_empty) {
				if(valueNow == value_Source) {
					if(!isHasSource) {
						color_matrix[i][j] = value_Source;
						source = new Point(i, j);
						isHasSource = true;
					}

				}else if(valueNow == value_destination) {
							if(!isHasDestination) {
								color_matrix[i][j] = valueNow;
								destination = new Point(i, j);
								isHasDestination = true;
							}

				}else {
					color_matrix[i][j] = valueNow;
				}
			}else {
				if(color_matrix[i][j] == valueNow) {
					color_matrix[i][j] = value_empty;
					if(valueNow==value_Source) {
						isHasSource=false;
						source = null;
					}else if(valueNow==value_destination){
						isHasDestination = false;
						destination = null;
					}
				}
			}
			
		}

	}
	private void paintMatrix(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.WHITE);
		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COLLUM; j++) {
				int x = j*square;
				int y = i*square;
				g.setColor(value_color_arr[color_matrix[i][j]]);
				g.fillRect(x,y, square-1, square-1);
				
				g.setColor(Color.BLACK);
				g.setFont(new Font("Bold",Font.BOLD,10));
				if(
				color_matrix[i][j] == value_special_node
					 ||color_matrix[i][j] == value_near_node
					|| color_matrix[i][j] == value_chosed_node
					) {
					g.drawString(Algorithm_ForUIDataInput.matrix[i][j].g+""
							, x+10, y+10);
					g.drawString(Algorithm_ForUIDataInput.matrix[i][j].h+""
							, x+35, y+10);
					g.drawString(Algorithm_ForUIDataInput.matrix[i][j].f+""
							, x+25, y+30);
				}
			}
		}
	}
	private void paintMouse(Graphics g) {
		g.setColor(colorNow);
		Point mousePosition = getMousePosition_inPanel();
		if(isMouseWithinPanel()) g.fillRect((int)mousePosition.getX()-mousePadding, (int)mousePosition.getY()-mousePadding, mouseSquare, mouseSquare);
	}
	
	private void paintClickedSquare(int i, int j, Graphics g) {
		g.setColor(colorNow);
		g.fillRect(i*square, j*square, square-1, square-1);
	}
	
	@Override
	public void run() {
		while(true) {
			repaint();
			try {
				thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public int[][] get_color_matrix() {
		return this.color_matrix;
	}
	public Point getSource() {
		return source;
	}
	public Point getDestination() {
		return destination;
	}
	private Point getMousePosition_inPanel() {
		Point mousePosition = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mousePosition, this);
		return mousePosition;
	}
	protected void readyForPaintSource() {
		colorNow = value_color_arr[value_Source];
		valueNow = value_Source;
		
	}
	protected void readyForPaintDestination() {
		colorNow = value_color_arr[value_destination];
		valueNow = value_destination;
	}
	protected void readyForPaintObstacle() {
		colorNow = value_color_arr[value_obstacle];
		valueNow = value_obstacle;
	}
	protected void StartPathFinding_UsingA_star() {
		algorithm = new Algorithm_ForUIDataInput(ROW, COLLUM,
													getSource(), 
													getDestination());
		algorithm.FindPath();
	}
	protected void Next() {
		algorithm.resumFindPath();
	}
	private void AutoFindPath() {
		algorithm = new Algorithm_ForUIDataInput(ROW, COLLUM,
				getSource(), 
				getDestination());
		algorithm.FindPath_Auto();
	}
	public boolean isMouseWithinPanel(){
		Point p = getMousePosition_inPanel();
	    if(p.getX() < WIDTH-mousePadding && p.getY() < HEIGHT-mousePadding) return true;
	    return false;
	}
	private void addButton() {
		bt_sourcetObj = new JButton("Source Object");
			bt_sourcetObj.setSize(new Dimension(150, 40));
			bt_sourcetObj.setLocation(WIDTH+20, 50);
			bt_sourcetObj.setBackground(Color.BLUE);
			bt_sourcetObj.setForeground(Color.WHITE);
			bt_sourcetObj.addActionListener(this);
		bt_destinationObj = new JButton("Destination Object");
			bt_destinationObj.setSize(new Dimension(150, 40));
			bt_destinationObj.setLocation(WIDTH+20, 105);
			bt_destinationObj.setBackground(Color.YELLOW);
			bt_destinationObj.addActionListener(this);
		bt_obstacle = new JButton("Obstacle Object");
			bt_obstacle.setSize(new Dimension(150, 40));
			bt_obstacle.setLocation(WIDTH+20, 160);
			bt_obstacle.setBackground(Color.BLACK);
			bt_obstacle.setForeground(Color.WHITE);
			bt_obstacle.addActionListener(this);
		bt_Start = new JButton("Start");
			bt_Start.setSize(new Dimension(150, 40));
			bt_Start.setLocation(WIDTH+20,250);
			bt_Start.setBackground(Color.GREEN);
			bt_Start.setForeground(Color.BLACK);
			bt_Start.addActionListener(this);
		bt_Reset = new JButton("Reset");
			bt_Reset.setSize(new Dimension(150, 40));
			bt_Reset.setLocation(WIDTH+20,500);
			bt_Reset.setBackground(Color.CYAN);
			bt_Reset.setForeground(Color.BLACK);
			bt_Reset.addActionListener(this);
		bt_Next = new JButton("Next");
			bt_Next.setSize(new Dimension(150, 40));
			bt_Next.setLocation(WIDTH+20, 350);
			bt_Next.setBackground(Color.MAGENTA);
			bt_Next.setForeground(Color.WHITE);
			bt_Next.addActionListener(this);
		bt_Auto = new JButton("Auto");
			bt_Auto.setSize(new Dimension(150, 40));
			bt_Auto.setLocation(WIDTH+20, 420);
			bt_Auto.setBackground(Color.orange);
			bt_Auto.setForeground(Color.WHITE);
			bt_Auto.addActionListener(this);
		this.add(bt_sourcetObj);
		this.add(bt_destinationObj);
		this.add(bt_obstacle);
		this.add(bt_Start);
		this.add(bt_Reset);
		this.add(bt_Next);
		this.add(bt_Auto);
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}

