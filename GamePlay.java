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
	public void init() //INITIALIZES THE FRAME, SNAKES, FOOD, ETC.
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
		
		addKeyListener(this);
		
		
		a1 = new Animate(15, Color.green); //starts at default locatioon
		ap = new Animate(15, Color.red);
		ap1 = new Animate(15, Color.red);
		snake1.add(a1); //add to arraylist as head
		a2 = new Animate(15, Color.orange);
		a2.setPostition(WIDTH-15,0); //location the second snake starts at
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
		int rand1 = (int)(Math.random() * (WIDTH - 15)); //-size
		int rand2 = (int)(Math.random() * (HEIGHT - 35)); //-35 is for the extra 20 on the height and 15 for size
		rand1 = rand1 - (rand1 % 15); //mod lines it up on grid
		rand2 = rand2 - (rand2 % 15);
		
		apple.setPostition(rand1, rand2);
		
	}
	public void run() {
		init();
		System.out.println("CPU or Two Player (C/T): "); //determines if you want CPU or Two Player
		Scanner sc = new Scanner(System.in);
		String s = sc.next();
		if(s.equals("C"))
			while(running && !gameover)
			{	
				update();
				CPU();
				render(g);
			
				try {
					thread.sleep(70);
				}catch (Exception e) {}
			
			}
		else
		{
			while(running && !gameover)
			{	
				update();
				update1();
				render(g);
			
				try {
					thread.sleep(70);
				}catch (Exception e) {}
			
			}
		}
		

	}
	public void CPU() //CONTROLS AUTOMATED SNAKE
	{
		int diffX = ap.getX()-a2.getX(); //distance from apple to animate X
		int diffY = ap.getY()-a2.getY(); //distance from apple to animate Y
		int diffX1 = ap1.getX()-a2.getX(); //distance from apple1 to animate X
		int diffY1 = ap1.getY()-a2.getY(); //distance from apple1 to animate Y
		/* CAUSING GAME TO QUIT
		 for(Animate a : snake2)
			{
				if(a.Collision(a2)) //checks if it hits the body of itself
				{
					System.out.println("animate");
					gameover = true;
				}
			}
		 */
		if((diffX+diffY) <= (diffX1 + diffY1)) { //whichever distance is shorter
			if(diffX!=0) { //move X coordinates first
				if(diffX<0)
				{

					y1=0;
					x1=-15;
					
					diffX += 15;//UPDATE DISTANCE
				}
				else if(diffX>=0)
				{
					
					x1 =15;
					y1=0;
					diffX-=15;//UPDATE DISTANCE
				}
			}//end inner if
			else if(diffY!=0) { //move Y coordinates second
				if(diffY<0  )
				{
					
					y1 =-15;
					x1=0;
					diffY+=15;//UPDATE DISTANCE
				}
				else if(diffY>=0  )
				{
					
					y1=15;
					x1=0;
					diffY-=15;//UPDATE DISTANCE
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
					diffX1 += 15;//UPDATE DISTANCE
				}
				else
				{
					x1 =15;
					y1=0;
					diffX1-=15;//UPDATE DISTANCE
				}
			}
			else if(diffY1!=0) {
				if(diffY1<0)
				{
					y1 =-15;
					x1=0;
					diffY1+=15;//UPDATE DISTANCE
				}
				else
				{
					y1=15;
					x1=0;
					diffY1-=15;//UPDATE DISTANCE
				}
			}	
		}//end else if
		
		
			 if(x1 != 0 || y1 !=0){ //if there's an x or y coordinate to move...
				 for(int i = snake2.size() - 1 ;i > 0;i--){ //iterates to start of snake
					 
					 int dx = snake2.get(i - 1).getX(); //gets previous position in snake, x coord
					 int dy = snake2.get(i - 1).getY(); //gets previous position in snake, y coord
					snake2.get(i).setPostition(dx, dy); // replaces previous snake position 
					
				 }
				
					 a2.move(x1, y1);
			 	}
		
			 //CHECK BOTH APPLES
			 if(ap.Collision(a2)){
				
				 setAp(ap);
				 
				 Animate temp = new Animate(15,Color.orange);
				 temp.setPostition(-300,-300);
				 snake2.add(temp);	
				 }
			 
			 if(ap1.Collision(a2)){
					
				 setAp(ap1);
				 
				 Animate temp = new Animate(15,Color.orange);
				 temp.setPostition(-300,-300);
				 snake2.add(temp);	
				 }
			 
			 
	}
	public void update() { //CONTROLS FIRST SNAKE
	score = snake1.size(); //SIZE OF FIRST SNAKE
	//	CHECKS FOR COLLISION BETWEEN SNAKES
	for(Animate a: snake1)
	{
		for(Animate b: snake2)
		{
		
			if(a1.Collision(a2) && snake1.size() ==1 && snake2.size()==1) //IF ONLY THE HEADS AND THEY COLLIDE, THEN TIE
			{
				System.out.println("TIE");
				gameover = true;
			}
			else if(a1.Collision(b)) //IF THE GREEN SNAKE RUNS INTO THE BODY OF ORANGE, GREEN LOSES/
			{
				System.out.println("ORANGE WINS");
				gameover = true; 
			}
			else if(a2.Collision(a)) //IF THE ORANGE SNAKE RUNS INTO THE BODY OF GREEN, ORANGE LOSES.
			{
				System.out.println("GREEN WINS");
				gameover= true;
			}	
		}
	}
	//CHECKS FOR COLLISION BETWEEN ITSELF
	for(Animate a : snake1)
	{
		if(a.Collision(a1))
		{
			gameover =true;
			 
		}
	}
	
	//DETERMINES WHICH DIRECTION TO MOVE SNAKE
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
	 
	 
	 
	 if(x != 0 || y !=0)
	 {
		 for(int i = snake1.size() - 1 ;i > 0;i--){
			 
			 int dx = snake1.get(i - 1).getX(); //gets previous position in snake, x coord
			 int dy = snake1.get(i - 1).getY(); //gets previous position in snake, y coord
			snake1.get(i).setPostition(dx, dy); // replaces previous snake position 
			
		 }
		 a1.move(x, y); //update head position.
	 	}
	
	 
	 if(ap.Collision(a1)){ //IF SNAKE COLLIDES WITH FOOD
		
		 setAp(ap); //CREATE NEW FOOD
		 
		 Animate temp = new Animate(15,Color.green);
		 temp.setPostition(-300,-300);
		 snake1.add(temp);
	
		 }
	 else if(ap1.Collision(a1)) //IF SNAKE COLLIDES WITH OTHER FOOD
	 {
		setAp(ap1);
		
		Animate temp = new Animate(15,Color.green);
		temp.setPostition(-300,-300);
		snake1.add(temp); 
	
		 
	 }
	 //WALL COLLISION 
		 if(a1.getX() < 0 || a1.getY() <0  || a1.getX() >= WIDTH || a1.getY() >= HEIGHT-35) {
			 
			 gameover = true;
		 }
	 }


	
	
	public void render(Graphics2D g2d) //RENDERS BOTH SNAKES AND FOOD
	{
		
		
		g2d= (Graphics2D)buffer.getDrawGraphics();
		g2d.fillRect(0, 0, WIDTH,  HEIGHT);
		for(Animate a : snake1) //render snake 1
		{
			a.render(g2d);
		}
		for(Animate a: snake2) //render snake2
		{
			a.render(g2d); //calls the animate render() method
		}
		ap.render(g2d);  //render food
		ap1.render(g2d);
		g.dispose();
		buffer.show();
	}
	public void update1() //CONTROLS SECOND SNAKE
	{
		score1 = snake2.size(); //how big the snake is
		//CHECKS FOR COLLSION BETWEEN ITSELF
		for(Animate a : snake2)
		{
			if(a.Collision(a2))
			{
				gameover = true;
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
			 a2.move(x1, y1); //update head position.
		 	}
		
		 
		 if(ap.Collision(a2)){
			
			 setAp(ap);
			 
			 Animate temp = new Animate(15,Color.green);
			 temp.setPostition(-300,-300);
			 snake2.add(temp); 

				
				
			 }
		 else if(ap1.Collision(a2))
		 {
			 setAp(ap1);
			 
			 Animate temp = new Animate(15,Color.orange);
			 temp.setPostition(-300,-300);
			 snake2.add(temp); 
		 }
			 if(a2.getX() < 0 || a2.getY() < 0 || a2.getX() >= WIDTH || a2.getY() >= HEIGHT-35) 
				 gameover = true;
			 
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		//FIRST PLAYER USE ARROWS
		
		if(k == KeyEvent.VK_UP) 
			{
			if(snake1.size()>1 && down) //MAKES SURE THE SNAKE ISN'T BIGGER THAN ONE AND ALREADY TRAVELING THE OPPOSITE DIRECTION
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
		
		//SECOND PLAYER W A S D
		
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
		game.start(); // BEGIN THREAD
		
		
		
	}
}
