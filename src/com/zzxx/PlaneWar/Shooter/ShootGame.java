package com.zzxx.PlaneWar.Shooter;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.zzxx.PlaneWar.Award.*;
import com.zzxx.PlaneWar.Bullet.Bullet;
import com.zzxx.PlaneWar.Enemy.*;
import com.zzxx.PlaneWar.FlyingObject.FlyingObject;
import com.zzxx.PlaneWar.FlyingObject.MyHero;

/**
 * @author 章旭东
 *
 */
public class ShootGame	extends JPanel {
		//窗口参数
		public static final	int width = 400;
		public static final int length = 700;
		
		//程序运行状态
		private static final int START = 0;
		private static final int RUNNING = 1;
		private static final int PAUSE = 2;
		private static final int GAME_OVER = 3;
		private  int Current_state = START; 
		
		//图片素材对象
		public static	 BufferedImage EnemyPlane ;
		public static	 BufferedImage[] EnemyPlane_ember = new BufferedImage[4];
		public static  BufferedImage BackGround ;
		public static  BufferedImage[] heros;
		public static  BufferedImage bee;
		public static  BufferedImage[] bee_ember = new BufferedImage[4];
		public static  BufferedImage boss;
		public static  BufferedImage[] boss_ember = new BufferedImage[4];
		public static  BufferedImage bullet;
		public static  BufferedImage Start;
		public static  BufferedImage Pause;
		public static  BufferedImage GameOver;
		public static  BufferedImage[] hero_ember = new BufferedImage[4];;
		public static  AudioClip	UI_BGM;
		public static 	 AudioClip	 battle_BGM;
		private boolean Ishit = false;
		private MyHero hero = new MyHero();
		private FlyingObject[] flys  =  {};
		private Bullet[] bullets = {};
		private ArrayList<FlyingObject> ember_flys = new ArrayList<>();
		static {
			try {
				  EnemyPlane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
				  BackGround = ImageIO.read(ShootGame.class.getResource("background.png"));
				  Start = ImageIO.read(ShootGame.class.getResource("start.png"));
				  Pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
				  heros = new BufferedImage[2];
				  for (int i = 0; i < heros.length; i++) 
				  {
					heros[i] = ImageIO.read(ShootGame.class.getResource("hero"+i+".png"));
				  }
				  bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
				  bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
				  GameOver = ImageIO.read(ShootGame.class.getResource("gameover.png"));
				  boss = ImageIO.read(ShootGame.class.getResource("bigplane.png"));
				  UI_BGM = Applet.newAudioClip(new File("主界面BGM.wav").toURI().toURL());
				  
				  for(int i = 0 ; i < hero_ember.length ; i ++)
					  hero_ember[i] = ImageIO.read(ShootGame.class.getResource("hero_ember"+i+".png"));

				  for(int i = 0 ; i < EnemyPlane_ember.length ; i ++)
					  EnemyPlane_ember[i] = ImageIO.read(ShootGame.class.getResource("airplane_ember"+i+".png"));
				  
				  for(int i = 0 ; i < boss_ember.length ; i ++)
					  boss_ember[i] = ImageIO.read(ShootGame.class.getResource("bigplane_ember"+i+".png"));
				  
				  for(int i = 0 ; i < bee_ember.length ; i ++) 
					  bee_ember[i] = ImageIO.read(ShootGame.class.getResource("bee_ember"+i+".png"));
				  
				  
			}catch(IOException e) {
				System.out.println(e);
			}
		}
		
		public static void main(String[] args) {
			
				JFrame frame = new JFrame("飞机大战");
				ShootGame GamePanel = new ShootGame();//游戏面板初始化
				frame.add(GamePanel);//加入至窗口
				frame.setSize(width, length);
				frame.setAlwaysOnTop(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//把窗口关闭作为程序终止
				frame.setLocationRelativeTo(null);	//自动居中
				frame.setUndecorated(true);
				frame.setVisible(true);
				GamePanel.action();
				
		}
		
		

		private void action() {
			MouseAdapter m = new MouseAdapter() {
					@Override
					public void mouseMoved(MouseEvent e) {
						int mousex   = e.getX();
						int mousey   = e.getY();
						if(Current_state == RUNNING)
						hero.MovebyMouse(mousex, mousey);
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						if(Current_state == START) {
							Current_state = RUNNING;
						}else if(Current_state == GAME_OVER) {
							Current_state = START;
							hero = new MyHero();
							bullets = new Bullet[0];
							flys = new FlyingObject[0];
						}else if(Current_state == PAUSE) {
							Current_state = RUNNING;
						}else Current_state = PAUSE;
					}
					@Override
					public void mouseExited(MouseEvent e) {
						if(Current_state == RUNNING)
						Current_state = PAUSE;
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						if(Current_state == PAUSE)
						Current_state = RUNNING;
					}
			};		
			this.addMouseListener(m);
			this.addMouseMotionListener(m);
			Timer	timer = new Timer();
			int delay = 15;
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					if(Current_state == RUNNING) 
					{
						flys2Array();
						Bullets2Array();
						ObjectsMove();
						hitAction();
						CheckBullets();
						CheckBound();
						CheckGameOver();
						emberAction();
					}
					repaint();
				}

				
			}, delay,delay);
		}


		//显示背景
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(BackGround, 0, 0, null);
			paintStates(g);
			PaintFlyings(g);
			paintEmber(g);
			paintBullets(g);
			PaintHero(g);
			paintInfo(g);
		}
		
		//显示出主飞机
		public void PaintHero(Graphics g) {
			g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
			
		}
		public void paintInfo(Graphics g) {
			Color InitColor = 	g.getColor();
			g.setColor(Color.RED);
			Font InitFont = g.getFont();
			
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			if(Current_state == RUNNING) {
				g.drawString("分数："+hero.getScore(), 10, 20);
				g.drawString("生命值："+hero.getLife(), 10, 40);
				g.drawString("火力值："+hero.getFire(), 10, 60);
			}else if(Current_state == START)
			{	
				g.setColor(Color.GRAY);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
				g.drawString("单击鼠标左键开始游戏！", 50, 380);
			}
			g.setColor(InitColor);
			g.setFont(InitFont);
			
		}
		
		//显示出其他飞行物
		public void PaintFlyings(Graphics g) {
			for(int i = 0 ; i < flys.length ; i++) {
					FlyingObject f = flys[i];
					g.drawImage(f.getImage(), f.getX(), f.getY(), null);
			}
		}
		
		//子弹显示
		public void paintBullets(Graphics g){
			for(int i=0;i<bullets.length;i++){ 
				Bullet b = bullets[i]; 
				g.drawImage(b.getImage(),b.getX(),b.getY(),null); 
			}
		}
		
		//状态素材显示
		public void paintStates(Graphics g) {
			switch (Current_state) {
			case START:
				g.drawImage(Start, 0, 0, null);
				
				break;
			case PAUSE :
				g.drawImage(Pause, 0, 0, null);
				break;
			
			case GAME_OVER :
				g.drawImage(GameOver, 0, 0, null);
				break;
				
			}
		}
		public void paintEmber(Graphics g) {
			Iterator<FlyingObject> it =	ember_flys.iterator();
			while(it.hasNext()) {
				FlyingObject fly = it.next();
				g.drawImage(fly.getImage(), fly.getX(), fly.getY(), null);
			}
		}
		
		//随机生成飞行物
		public FlyingObject Getflys_randomly() {
			Random	 r = new Random();
			int result = r.nextInt(50);//百分10生成奖励，百分15生成大型BOSS
			if(result < 3) {
				return new Bee();
			}else if(result > 35) {
				return new boss();
			}else {
				return new EnemyPlane();
			}
		}
		
		int FlysTimer = 0;
		public void flys2Array() {
			//创建新飞行物对象置入数组以显示
			++FlysTimer;
			if(FlysTimer %20 ==0) {//画一个需要40ms*10ms
				FlyingObject fly =	Getflys_randomly();
				flys = Arrays.copyOf(flys,flys.length+1);
				flys[flys.length-1] = fly;
			}
		}
		
		int BullTimer = 0;
		public void Bullets2Array() {
			//产生子弹
			++BullTimer;
			//每15ms飞机发射一次子弹
			if(BullTimer % 15 == 0) {
				Bullet[] newbs = hero.fire();
				bullets = Arrays.copyOf(bullets, bullets.length+newbs.length);//扩容
				System.arraycopy(newbs, 0, bullets, bullets.length - newbs.length, newbs.length);
			}
		}
		
		public void ObjectsMove() 
		{
			if(! Ishit)
			hero.movestep();
			for(int i = 0 ; i < flys.length ; i++) 
			{
				flys[i].movestep();
			}
			for(int i = 0 ; i < bullets.length ; i++) {
				bullets[i].movestep();
			}
		}
		public void emberAction() 
		{
			Iterator<FlyingObject> it = 	ember_flys.iterator();
			while (it.hasNext()) {
				FlyingObject fly = 	it.next();
				if(fly.embersindex <= 30) {
					fly.ember();
				}
				else
					it.remove();
			}
			
		}
		private static void Index2Last(FlyingObject[] arr,int index)
		{
			FlyingObject temp = arr[arr.length-1];
			arr[arr.length-1] = arr[index];
			arr[index] = temp;
			
		}
		public void CheckBullets() {
			for (int i = 0; i < bullets.length; i++) {
				bang(bullets[i], i);
			}
		}
		public void bang (Bullet b, int pos)
		{
			int index = -1;
			for(int i = 0 ;i < flys.length ; i ++)
			{
				if (flys[i].IsAttackedby(b)) 
				{
					index = i ;
					break;
				}
			}
			if (index >= 0) 
			{
				FlyingObject bingofly = 	flys[index];
				if (bingofly instanceof Enemy) {
					bingofly.comsumeLife();
					if(bingofly.getLife() <= 0) 
					{
						ember_flys.add(bingofly);
						hero.addScore(  ((Enemy)bingofly).GetScore());
						Index2Last(flys, index);//将该飞行物置末
						flys = Arrays.copyOf(flys, flys.length-1);
					}
				}
				else if(bingofly instanceof Award) 
				{
					ember_flys.add(bingofly);
					Award award =  (Bee) bingofly;
					switch (award.getType()){
					case Award.LifeType:
						hero.addLife();
						break;
					case Award.FireType :
						hero.addFire();
						break;
					}
					Index2Last(flys, index);//将该奖励置末并删除
					flys = Arrays.copyOf(flys, flys.length-1);
				}
				Index2Last(bullets, pos);
				bullets = Arrays.copyOf(bullets, bullets.length-1);
			}
		}
		public boolean IsDead() {
			for(int i = 0 ; i < flys.length ; i ++)
			{
				if(hero.hit(flys[i]))
				{
					Ishit = true;
					hero.comsumeLife();
					hero.setFire(MyHero.Init_DefaultFire);
					//被撞了的敌机需要消失
					FlyingObject temp = flys[flys.length-1];
					flys[flys.length-1] = flys[i];
					flys[i] = temp;
					//剃去最后一个
					flys = Arrays.copyOf(flys, flys.length-1);
				}
			}
			return hero.getLife()<=0;
		}
		
		public void hitAction() {
			if(Ishit && hero.embersindex <= 30) 
			{
				hero.ember();
			}else if(hero.embersindex > 30)
			{
				Ishit = false;
				hero.embersindex = 0;
				hero.setImage(ShootGame.heros[0]);
			}
		}
		
		public void CheckGameOver() {
			if(IsDead() && ! Ishit) 
			{
				this.Current_state = GAME_OVER;
			}
		}
		
		public void CheckBound() 
		{
			//检查边界外飞行物
			FlyingObject[] flys_InBound =	new FlyingObject[flys.length];
			Bullet[]	bullet_InBound = new Bullet[bullets.length];
			int pos = 0;
			for(int i = 0 ; i < flys.length ; i++) {
				if( ! flys[i].outOfBounds()) {
					flys_InBound[pos++] = flys[i];
				}
			}
			flys = Arrays.copyOf(flys_InBound, pos);//只保留界内的飞行物对象
			pos = 0;
			for(int i = 0 ; i < bullets.length ; i++) {
				if( ! bullets[i].outOfBounds()) {
					bullet_InBound[pos++] = bullets[i];
				}
			}
			bullets = Arrays.copyOf(bullet_InBound, pos);//只保留界内的子弹对象数据
		}
}
