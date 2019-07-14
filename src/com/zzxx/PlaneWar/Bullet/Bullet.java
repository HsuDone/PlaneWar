package com.zzxx.PlaneWar.Bullet;

import com.zzxx.PlaneWar.FlyingObject.FlyingObject;
import com.zzxx.PlaneWar.Shooter.ShootGame;

/**
 * @author 章旭东
 *
 */
public  class Bullet extends FlyingObject {
		private int speed = 3;
		
		public Bullet(int x , int y) {
			this.image = ShootGame.bullet;
			this.width = image.getWidth();
			this.height = image.getHeight();
			//带入的参数是hero飞机的位置
			this.x = x;
			this.y = y;
		}

		@Override
		public void movestep() {
			this.y -= speed;
		}

		@Override
		public boolean outOfBounds() {
			//出上界
			return this.y  +  this.height <= 0;
		}


}
