package com.zzxx.PlaneWar.FlyingObject;

import java.awt.image.BufferedImage;

import com.zzxx.PlaneWar.Bullet.Bullet;

/**
 * @author ÕÂÐñ¶«
 *
 */
public abstract class FlyingObject {
	protected BufferedImage	image;
	protected	int width;
	protected int height;
	//ËØ²Ä×ø±ê;
	protected int x;
	protected int y ;
	protected int Life =1;
	public BufferedImage[] embers ;
	public int embersindex = 0;
	
	public int getLife() {
		return Life;
	}

	public void setLife(int life) {
		Life = life;
	}
	public void comsumeLife() {
		--Life;
	}

	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public abstract void movestep() ;
	
		
	
		
	public abstract boolean outOfBounds();
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public boolean IsAttackedby(Bullet b) {
		int x1 = this.x;
		int x2 = this.x + this.width;
		int y1 = this.y;
		int y2 = this.y + this.height;
		int bx = b.x;
		int by = b.y;
		
		return (bx > x1) && (bx < x2) && (by > y1) &&(by < y2);
	}
	public void ember() {
		this.image = embers[(embersindex++)/10%embers.length];
	}

	
}
