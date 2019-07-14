package com.zzxx.PlaneWar.Enemy;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.zzxx.PlaneWar.FlyingObject.FlyingObject;
import com.zzxx.PlaneWar.Shooter.ShootGame;

/**
 * @author ����
 *
 */
public class EnemyPlane extends FlyingObject	implements Enemy{
		private int speed = 2;
		
		public EnemyPlane() {
			this.image = ShootGame.EnemyPlane;
			this.embers = ShootGame.EnemyPlane_ember;
			this.width = image.getWidth();
			this.height = image.getHeight();
			this.x = new Random().nextInt(ShootGame.width - this.width);
			this.y = 0-this.height;
			this.Life = 1;
		}

		@Override
		public int GetScore() {
			return 5;
		}
		@Override
			public void movestep() {
			//����ǰ��,������һ���ٶȵ�λ
			this.y += this.speed;
			
		}

		@Override
		public boolean outOfBounds() {
			return this.y >= ShootGame.length;
		}

		

		
}
