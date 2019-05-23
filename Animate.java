package FinalSelfMade;
import java.awt.*;
import java.util.*;


import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Animate {
	private int x,y,size;
	private Color color;
	Rectangle rect;
	public Animate(int size, Color c){
		this.size = size;
		color = c;
		
	}
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

	public void setX(int x){
		this.x = x;
	}
	public void setX1(int x1){
		this.x = x1;
	}
	
	
	public void setColor(Color c)
	{
		color = c;
	}
	public Color getColor()
	{
		return color;
	}
	public void setY(int y){
		this.y = y;
	}
	
	public void setPostition(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public void move(int dx,int dy){
		x += dx;
		y += dy;
	}
	public void setBound(int size)
	{
		rect.setBounds(x+15, y+15, size + 15, size+15);
	}
	public Rectangle getRect(){
		rect =new Rectangle(x, y, size, size);
		return rect;
	}

	public boolean Collision(Animate o){
		if (o == this) 
			return false;
		return getRect().intersects(o.getRect());
	}
	
	public void render(Graphics2D g){
		g.setColor(this.getColor());
		g.fillRect(x , y , size , size );
		
	}
	
	
}


