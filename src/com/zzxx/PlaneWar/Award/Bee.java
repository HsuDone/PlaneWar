package com.zzxx.PlaneWar.Award;

import java.util.Random;

import com.zzxx.PlaneWar.FlyingObject.FlyingObject;
import com.zzxx.PlaneWar.Shooter.ShootGame;

/**
 * @author 章旭东
 *
 */
public class Bee extends FlyingObject  implements Award {
	
	private int Xspeed = 1;
	private  int Yspeed = 2;
	private int AwardType ; //分0/1两种类型
	
	public Bee() {
		this.image = ShootGame.bee;
		this.embers = ShootGame.bee_ember;
		this.width = image.getWidth();
		this.height = image.getHeight();
		Random r = new Random();
		//坐标、奖励类型初始化
		this.x = r.nextInt(ShootGame.width - this.width);
		this.y = 0 - this.height;
		AwardType = r.nextInt(2);
		super.Life = 1;
	}

	@Override
	public int getType() {
		return AwardType;
	}
	
	@Override
	public void movestep() {
		//奖励蜜蜂初始会沿右下方行动
		//在碰到窗口右边时刻应该能反弹
		this.x += this.Xspeed;
		this.y += this.Yspeed;
		if(x + this.width >= ShootGame.width) {
			this.Xspeed = -Xspeed;
		}
		if( x <= 0) {
			this.Xspeed = -Xspeed;//碰左窗再次反弹
		}
	}

	@Override
	public boolean outOfBounds() {
		//出下界
		return this.y >= ShootGame.length;
	}

	
	


}
