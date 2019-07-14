package com.zzxx.PlaneWar.FlyingObject;

import java.awt.image.BufferedImage;

import com.zzxx.PlaneWar.Bullet.Bullet;
import com.zzxx.PlaneWar.Shooter.ShootGame;


/**
 * @author 章旭东
 *	主角飞机
 */
public class MyHero extends FlyingObject {
		public static final int Init_DefaultFire = 0;
		public static final int Init_DefaultLife = 3;
		private int score;
		private int Fire;
		private BufferedImage[] Imgs;
		
		private int ImgsIndex;
		
		public MyHero() {
			Imgs = ShootGame.heros;
			embers = ShootGame.hero_ember;
			embersindex = 0;
			this.image = Imgs[0];
			this.height = image.getHeight();
			this.width = image.getWidth();
			x = 150;
			y = 400;
			setFire(Init_DefaultFire);
			setLife(Init_DefaultLife);
			ImgsIndex = 0;
			
		}
		
		
		@Override
		public void movestep() {
			//10ms切换一下主角飞机	动画效果
			this.image = Imgs[(ImgsIndex++)/10%Imgs.length];
		}
		public void MovebyMouse(int X , int Y) {
			this.x = X - this.width/2;
			this.y = Y - this.height/2;
		}
		
		public int getFire() {
			return Fire;
		}
		public void addFire() {
			Fire += 50; 
		}
		
		public void addLife() {
			++ this.Life;
		}
		public void setFire(int fire) {
			Fire = fire;
		}
		
		
		public Bullet[] fire() {
			int heroX =	this.x;
			int heroY = 	this.y;
			Bullet[] bs;
			if(Fire >= 100) {
				//三连发模式
				bs = new Bullet[3];
				bs[0] =	new Bullet(heroX+this.width/2, heroY);
				bs[1]  = new Bullet(heroX, heroY+this.height/2);
				bs[2] = new Bullet(heroX+this.width, heroY+this.height/2);
				Fire -= 1;
				return bs;
			}else if(Fire >0 && Fire<100){
				//双连发
				 bs = new Bullet[2];
				 bs[0]  = new Bullet(heroX+this.width/4, heroY+this.height/2);
				 bs[1] = new Bullet(heroX+this.width*3/4, heroY+this.height/2);
				 Fire -= 1;
				 return bs;
			}
			//默认单发
			bs = new Bullet[1];
			bs[0] =	new Bullet(heroX+this.width/2, heroY);
			return bs;
		}
		
		
		@Override
		public boolean outOfBounds() 
		{
			return false;
		}
		public int getScore() 
		{
			return score;
		}
		public void setScore(int score) {
			this.score = score;
		}
		public void addScore(int add) {
			this.score += add;
		}
		
		public boolean hit(FlyingObject obj) {
			int x1=obj.x-this.width/2;
			int x2=obj.x+this.width/2+obj.width;
			int y1=obj.y-this.height/2;
			int y2=obj.y+this.height/2+obj.height;
			int x=this.x+this.width/2;
			int y=this.y+this.height;
			return x>x1&&x<x2&&y>y1&&y<y2;
		}
		
		
		
}
