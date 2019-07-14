package com.zzxx.PlaneWar.Enemy;

import java.util.Random;

import com.zzxx.PlaneWar.FlyingObject.FlyingObject;
import com.zzxx.PlaneWar.Shooter.ShootGame;

/**
 * @author ÕÂÐñ¶«
 *
 */
public class boss extends FlyingObject implements Enemy{
	private int speed = 1;
	public boss() {
		this.image = ShootGame.boss;
		this.embers = ShootGame.boss_ember;
		width	=	image.getWidth();
		height	=	image.getHeight();
		Random rand	=	new Random();
		x	=	rand.nextInt(ShootGame.width-this.width);
		y	=	0- height;
		Life = 5;
	}

	@Override
	public int GetScore() {
		return 40;
	}
	
	@Override
	public void movestep() {
		this.y += speed;
	}

	@Override
	public boolean outOfBounds() {
		return this.y >= ShootGame.length;
	}


	
}
