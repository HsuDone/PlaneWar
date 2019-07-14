package com.zzxx.PlaneWar.Bullet;

import com.zzxx.PlaneWar.FlyingObject.FlyingObject;
import com.zzxx.PlaneWar.Shooter.ShootGame;

/**
 * @author ����
 *
 */
public  class Bullet extends FlyingObject {
		private int speed = 3;
		
		public Bullet(int x , int y) {
			this.image = ShootGame.bullet;
			this.width = image.getWidth();
			this.height = image.getHeight();
			//����Ĳ�����hero�ɻ���λ��
			this.x = x;
			this.y = y;
		}

		@Override
		public void movestep() {
			this.y -= speed;
		}

		@Override
		public boolean outOfBounds() {
			//���Ͻ�
			return this.y  +  this.height <= 0;
		}


}
