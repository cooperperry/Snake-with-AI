package FinalSelfMade;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
public class GamePlay extends Canvas implements Runnable, KeyListener {
	public static final int WIDTH =900, HEIGHT = 922;
	private static Thread thread;
	private static Thread thread1;
	private Canvas canvas;
	private boolean running, gameover;
	private Dimension size;
	private Graphics2D g;
	private Graphics2D gd;
	private Animate a1;
	private Animate a2;
	private Animate ap, ap1;
	private int score, score1;
	private ArrayList<Animate> snake1 = new ArrayList<Animate>(); 
	private ArrayList<Animate> snake2 = new ArrayList<Animate>();
	private BufferStrategy buffer, buffer1;
	
	private boolean left, right, up, down, left1, right1, up1, down1;
	private int x,y,x1,y1;
	public GamePlay()
	{
		size = new Dimension(900,920);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		
	}
	public void init()
	{
		JFrame frame = new JFrame("Two Player Snake!");
		GamePlay panel = new GamePlay();
		frame.add(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(new Dimension(GamePlay.WIDTH, GamePlay.HEIGHT));
		createBufferStrategy(3);
		buffer = getBufferStrategy();
		g= (Graphics2D)buffer.getDrawGraphics();
		buffer1 = getBufferStrategy();
		addKeyListener(this);
		
		gd = (Graphics2D)buffer1.getDrawGraphics();
		a1 = new Animate(15, Color.green);
		ap = new Animate(15, Color.red);
		ap1 = new Animate(15, Color.yellow);
		snake1.add(a1);
		a2 = new Animate(15, Color.green);
		a2.setPostition(WIDTH-15,0);
		snake2.add(a2);
		setAp(ap);
		setAp(ap1);
		
		
	}
	public void start()
	{
		
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public void stop()
	{
		if(!running)
			return;
		running = false;
		thread.interrupt();
		
	}
	public void setAp(Animate apple)
	{
		int rand1 = (int)(Math.random() * (WIDTH - 15));
		int rand2 = (int)(Math.random() * (HEIGHT - 35));
		rand1 = rand1 - (rand1 % 15);
		rand2 = rand2 - (rand2 % 15);
		
		apple.setPostition(rand1, rand2);
		
	}
	public void run() {
		init();
		int i =0;
		while(running && !gameover)
		{	
			update(a1);
			//update1(a2);
			CPU(a2);
			render(g);
		
			try {
				thread.sleep(30);
			}catch (Exception e) {}
		
		}
		

	}
	public void CPU(Animate animate)
	{
		int diffX = ap.getX()-animate.getX();
		int diffY = ap.getY()-animate.getY();
		int diffX1 = ap1.getX()-animate.getX();
		int diffY1 = ap1.getY()-animate.getY();
		System.out.print(Math.abs(diffX+diffY) +" : ");
		System.out.println(" "+ Math.abs(diffX1+diffY1));
		 for(Animate a : snake2)
			{
				if(a.Collision(animate))
				{
					running = false; 
				}
			}
		if((diffX+diffY) <= (diffX1 + diffY1)) {
			if(diffX!=0) {
				if(diffX<0)
				{
					x1=-15;
					y1=0;
					diffX += 15;
				}
				else
				{
					x1 =15;
					y1=0;
					diffX-=15;
				}
			}//end if
			else if(diffY!=0) {
				if(diffY<0)
				{
					y1 =-15;
					x1=0;
					diffY+=15;
				}
				else
				{
					y1=15;
					x1=0;
					diffY-=15;
				}
			} //end else if
		} //end if
		else 
		{
			if(diffX1!=0) {
				if(diffX1<0)
				{
					x1=-15;
					y1=0;
					diffX1 += 15;
				}
				else
				{
					x1 =15;
					y1=0;
					diffX1-=15;
				}
			}
			else if(diffY1!=0) {
				if(diffY1<0)
				{
					y1 =-15;
					x1=0;
					diffY1+=15;
				}
				else
				{
					y1=15;
					x1=0;
					diffY1-=15;
				}
			}	
		}//end else if
		
		
			 if(x1 != 0 || y1 !=0){
				 for(int i = snake2.size() - 1 ;i > 0;i--){
					 
					 int dx = snake2.get(i - 1).getX(); //gets previous position in snake, x coord
					 int dy = snake2.get(i - 1).getY(); //gets previous position in snake, y coord
					snake2.get(i).setPostition(dx, dy); // replaces previous snake position 
					
				 }
				 animate.move(x1, y1); //update head position.
			 	}
		
			 //CHECK BOTH APPLES
			 if(ap.Collision(animate)){
				
				 setAp(ap);
				 
				 Animate temp = new Animate(15,Color.green);
				 temp.setPostition(-300,-300);
				 snake2.add(temp);	
				 }
			 
			 if(ap1.Collision(animate)){
					
				 setAp(ap1);
				 
				 Animate temp = new Animate(15,Color.green);
				 temp.setPostition(-300,-300);
				 snake2.add(temp);	
				 }
			 
			 
	}
	public void update(Animate animate) {
	score = snake1.size();
	
	for(Animate a: snake1)
	{
		for(Animate b: snake2)
		{
			if(a.Collision(b))
			{
				System.out.println("A wins");
				gameover = true;
			}
		}
	}
	for(Animate a : snake1)
	{
		if(a.Collision(animate))
		{
			running = false; 
		}
	}
	
	if(up && y == 0){
		 y = -15;
		 x = 0;
	 }
	 if(down && y==0){
		 y = 15;
		 x = 0;
	 }
	 if(left && x == 0){
		 y = 0;
		 x = -15;
	 }
	 if(right && x == 0){
		 y = 0;
		 x = 15;
	 }
	 
	 
	 
	 if(x != 0 || y !=0){
		 for(int i = snake1.size() - 1 ;i > 0;i--){
			 
			 int dx = snake1.get(i - 1).getX(); //gets previous position in snake, x coord
			 int dy = snake1.get(i - 1).getY(); //gets previous position in snake, y coord
			snake1.get(i).setPostition(dx, dy); // replaces previous snake position 
			
		 }
		 animate.move(x, y); //update head position.
	 	}
	
	 
	 if(ap.Collision(animate)){
		
		 setAp(ap);
		 
		 Animate temp = new Animate(15,Color.green);
		 temp.setPostition(-300,-300);
		 if(animate.equals(a1))
			 snake1.add(temp); 
		 else
			 snake2.add(temp);
	
		 }
	 else if(ap1.Collision(animate))
	 {
		 setAp(ap1);
		 Animate temp = new Animate(15,Color.green);
		 temp.setPostition(-300,-300);
		 if(animate.equals(a1))
			 snake1.add(temp); 
		 else
			 snake2.add(temp);
	 }
		 if(animate.getX() < 0 || animate.getY() <0  || animate.getX() >= WIDTH || animate.getY() >= HEIGHT-35) 
			 gameover = true;
		 
	 }


	
	
	public void render(Graphics2D g2d)
	{
		
		
		g2d= (Graphics2D)buffer.getDrawGraphics();
		g2d.fillRect(0, 0, WIDTH,  HEIGHT);
		for(Animate a : snake1)
		{
			a.render(g2d);
		}
		for(Animate a: snake2)
		{
			a.render(g2d);
		}
		ap.render(g2d);
		ap1.render(g2d);
		g.dispose();
		buffer.show();
	}
	public void update1(Animate animate)
	{
		score1 = snake2.size();
		for(Animate a : snake2)
		{
			if(a.Collision(animate))
			{
				running = false; 
			}
		}
		
		if(up1 && y1 == 0){
			 y1 = -15;
			 x1 = 0;
		 }
		 if(down1 && y1 == 0){
			 y1 = 15;
			 x1 = 0;
		 }
		 if(left1 && x1 == 0){
			 y1 = 0;
			 x1 = -15;
		 }
		 if(right1 && x1 == 0 && y1 != 0){
			 y1 = 0;
			 x1 = 15;
		 }
		 
		 
		 
		 if(x1 != 0 || y1 !=0){
			 for(int i = snake2.size() - 1 ;i > 0;i--){
				 
				 int dx = snake2.get(i - 1).getX(); //gets previous position in snake, x coord
				 int dy = snake2.get(i - 1).getY(); //gets previous position in snake, y coord
				snake2.get(i).setPostition(dx, dy); // replaces previous snake position 
				
			 }
			 animate.move(x1, y1); //update head position.
		 	}
		
		 
		 if(ap.Collision(animate)){
			
			 setAp(ap);
			 
			 Animate temp = new Animate(15,Color.green);
			 temp.setPostition(-300,-300);
			 snake2.add(temp); 

				
				
			 }
		 else if(ap1.Collision(animate))
		 {
			 setAp(ap1);
			 
			 Animate temp = new Animate(15,Color.green);
			 temp.setPostition(-300,-300);
			 snake2.add(temp); 
		 }
			 if(animate.getX() < 0 || animate.getY() < 0 || animate.getX() >= WIDTH || animate.getY() >= HEIGHT-35) 
				 gameover = true;
			 
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();

		
		if(k == KeyEvent.VK_UP) 
			{
			if(snake1.size()>1 && down)
				up = false;
			else {
				up = true;
				down = false;
				left = false;
				right = false;
			}
			}
		if(k == KeyEvent.VK_DOWN) {
			if(snake1.size()>1 && up)
				down = false;
			
			else {
				up = false;
				down = true;
				left = false;
				right = false;
			}
		}
		if(k == KeyEvent.VK_LEFT) {
			if(snake1.size()>1 && right)
				left = false;
			
			else {
				up = false;
				down = false;
				left = true;
				right = false;
			}
		}
		if(k == KeyEvent.VK_RIGHT) {
			if(snake1.size()>1 && left)
				right = false;
			
			else {
				up = false;
				down = false;
				left = false;
				right = true;
			}
		}
		
		//SECOND ONE
		
		int j = e.getKeyCode();

		
		if(j == KeyEvent.VK_W) 
			{
			if(snake1.size()>1 && down1)
				up1 = false;
			else {
				up1 = true;
				down1 = false;
				left1 = false;
				right1 = false;
			}
			}
		if(j == KeyEvent.VK_S) {
			if(snake1.size()>1 && up1)
				down1 = false;
			
			else {
				up1 = false;
				down1 = true;
				left1 = false;
				right1 = false;
			}
		}
		if(j == KeyEvent.VK_A) {
			if(snake1.size()>1 && right1)
				left1 = false;
			
			else {
				up1 = false;
				down1 = false;
				left1 = true;
				right1 = false;
			}
		}
		if(j == KeyEvent.VK_D) {
			if(snake1.size()>1 && left1)
				right = false;
			
			else {
				up1 = false;
				down1 = false;
				left1 = false;
				right1 = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
	}
	public static void main(String[] args)
	{
		GamePlay game = new GamePlay();
		game.start();
		
		
		
	}
}
