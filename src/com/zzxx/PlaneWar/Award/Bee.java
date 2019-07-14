package com.zzxx.PlaneWar.Award;

import java.util.Random;

import com.zzxx.PlaneWar.FlyingObject.FlyingObject;
import com.zzxx.PlaneWar.Shooter.ShootGame;

/**
 * @author ����
 *
 */
public class Bee extends FlyingObject  implements Award {
	
	private int Xspeed = 1;
	private  int Yspeed = 2;
	private int AwardType ; //��0/1��������
	
	public Bee() {
		this.image = ShootGame.bee;
		this.embers = ShootGame.bee_ember;
		this.width = image.getWidth();
		this.height = image.getHeight();
		Random r = new Random();
		//���ꡢ�������ͳ�ʼ��
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
		//�����۷��ʼ�������·��ж�
		//�����������ұ�ʱ��Ӧ���ܷ���
		this.x += this.Xspeed;
		this.y += this.Yspeed;
		if(x + this.width >= ShootGame.width) {
			this.Xspeed = -Xspeed;
		}
		if( x <= 0) {
			this.Xspeed = -Xspeed;//�����ٴη���
		}
	}

	@Override
	public boolean outOfBounds() {
		//���½�
		return this.y >= ShootGame.length;
	}

	
	


}
