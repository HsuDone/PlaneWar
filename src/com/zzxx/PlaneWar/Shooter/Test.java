package com.zzxx.PlaneWar.Shooter;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioPermission;
import sun.audio.AudioPlayer; 
/**
 * @author ÕÂÐñ¶«
 *
 */
public class Test {

	public static void main(String[] args)  {
		int time = 0;
		while(time++ < 50000) {
			System.out.println((time/10000)%5);
		}
	}
		

}
