package com.bn.ZDR.Thread;

import com.bn.ZDR.Scene.Bullet;
import com.bn.ZDR.Scene.TNTBullet;

import java.util.ArrayList;

import static com.bn.ZDR.Util.Constant.BulletGoThreadFlag;

//定时绘制炸弹的线程
public class BulletGoThread extends Thread
{
	ArrayList<Bullet> bulletAl;//子弹列表
	ArrayList<TNTBullet> tnt_bulletAl;

	public BulletGoThread(ArrayList<Bullet> bulletAl,ArrayList<TNTBullet> tnt_bulletAl)
	{
		this.bulletAl=bulletAl;
		this.tnt_bulletAl=tnt_bulletAl;
	}

	public void run()
	{
		while(true)
		{//循环定时移动炮弹
			doSometing();
			try
			{
				Thread.sleep(100);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void doSometing()
	{
		if (BulletGoThreadFlag)
		{
			try
			{
				for(Bullet b:bulletAl)
				{
					b.go();
				}

				for(TNTBullet tnt_bullet:tnt_bulletAl)
				{
					tnt_bullet.go();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
