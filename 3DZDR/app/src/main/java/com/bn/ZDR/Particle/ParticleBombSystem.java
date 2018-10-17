package com.bn.ZDR.Particle;//声明包

import static com.bn.ZDR.Particle.ParticleBombDataConstant.*;

import android.opengl.GLES30;

import com.bn.ZDR.Util.MatrixState3D;

public class ParticleBombSystem implements Comparable<ParticleBombSystem>
{

	private float[] startColor;//粒子起始颜色数组
	private float[] endColor;//粒子终止颜色数组
	private int srcBlend;//源混合因子
	private int dstBlend;//目标混合因子
	private int blendFunc;//混合方式
	private float maxLifeSpan;//粒子最大生命期
	private float lifeSpanStep;//粒子生命期步进
	private int sleepSpan;//粒子更新线程休眠时间间隔
	private int groupCount;//每批喷发的粒子数量
	private float xAngle=0;//此粒子系统沿X轴的旋转角度
	private float yAngle=0;//此粒子系统沿Y轴的旋转角度
	private ParticleBombForDraw fpfd;//粒子群的绘制者
	private boolean flag=true;//线程工作的标志位

	private float[] points;//粒子对应的所有顶点数据数组

	public ParticleBombSystem(ParticleBombForDraw fpfd, int count)
	{//构造器
		this.startColor=START_COLOR;//初始化粒子起始颜色
		this.endColor=END_COLOR;//初始化粒子终止颜色
		this.srcBlend=SRC_BLEND;//初始化源混合因子
		this.dstBlend=DST_BLEND;//初始化目标混合因子
		this.blendFunc=BLEND_FUNC;//初始化混合方式
		this.maxLifeSpan=MAX_LIFE_SPAN;//初始化每个粒子的最大生命期
		this.lifeSpanStep=LIFE_SPAN_STEP;//初始化每个粒子的生命步进
		this.groupCount=GROUP_COUNT;//初始化每批喷发的粒子数
		this.sleepSpan=THREAD_SLEEP;//初始化线程的休眠时间
		this.fpfd=fpfd;//初始化粒子群的绘制者
		this.points=initPoints(count);//初始化粒子所对应的所有顶点数据数组
		fpfd.initVertexData(points);//调用初始化顶点坐标与纹理坐标数据的方法
		new Thread()
		{//创建粒子的更新线程
			public void run()//重写run方法
			{
				while(flag)
				{
					update();//调用update方法更新粒子状态
					try
					{
						Thread.sleep(sleepSpan);//休眠一定的时间
					} catch (InterruptedException e)
					{
						e.printStackTrace();//打印异常信息
					}
				}
			}
		}.start();//启动线程
	}

	public float[] initPoints(int zcount)//初始化粒子所对应的所有顶点数据的方法
	{
		float[] points=new float[zcount*4];//临时存放顶点数据的数组-每个粒子对应6个顶点，每个顶点包含4个值
		for(int i=0;i<zcount;i++)
		{//循环遍历所有粒子

			double elevation=Math.random()*Math.PI-Math.PI/2;//仰角
			double direction=Math.random()*Math.PI*2;//方位角

			float px = (float)(Math.cos(elevation)*Math.cos(direction));
			float py = (float)(Math.cos(elevation)*Math.sin(direction));
			float pz = (float)Math.sin(elevation);

			points[i*4]=  R*px;//粒子对应的第一个点的x坐标
			points[i*4+1]=R*py;//粒子对应的第一个点的y坐标
			points[i*4+2]=R*pz;//粒子对应的第一个点的z坐标
			points[i*4+3]=10.0f;//粒子对应的第一个点的当前生命期--10代表粒子处于未激活状态
		}
		for(int j=0;j<groupCount;j++)
		{//循环遍历第一批的粒子
			points[j*4+3]=lifeSpanStep;//设置粒子生命期，不为10时，表示粒子处于活跃状态
		}
		return points;//返回所有粒子顶点属性数据数组
	}

	public void drawSelf(int texId)
	{
		GLES30.glDisable(GLES30.GL_DEPTH_TEST);//关闭深度检测
		GLES30.glEnable(GLES30.GL_BLEND); //开启混合
		GLES30.glBlendEquation(blendFunc);//设置混合方式
		GLES30.glBlendFunc(srcBlend,dstBlend); //设置混合因子

		MatrixState3D.rotate(yAngle, 0, 1, 0);//执行旋转变换
		MatrixState3D.rotate(-xAngle, 1, 0, 0);//执行旋转变换

		MatrixState3D.pushMatrix();//保护现场
		fpfd.drawSelf(texId,startColor,endColor,maxLifeSpan);//绘制粒子群
		MatrixState3D.popMatrix();//恢复现场

		GLES30.glEnable(GLES30.GL_DEPTH_TEST);//开启深度检测
		GLES30.glDisable(GLES30.GL_BLEND);//关闭混合
	}
	int count=1;//激活粒子的位置计算器
	public void update()//更新粒子状态的方法
	{
		if(count>=(points.length/groupCount/4))//计算器超过激活粒子位置时
		{
			count=0;//重新计数
		}

		//查看生命期以及计算下一位置的相应数据
		for(int i=0;i<points.length/4;i++)
		{//循环遍历所有粒子
			if(points[i*4+3]!=10.0f)//当前为活跃粒子时
			{
				points[i*4+3]+=lifeSpanStep;//计算当前生命期
				if(points[i*4+3]>this.maxLifeSpan)//当前生命期大于最大生命期时---重新设置该粒子参数
				{
				}
				else//生命期小于最大生命期时----计算粒子的下一位置坐标
				{
					double elevation=Math.random()*Math.PI-Math.PI/2;//仰角
					double direction=Math.random()*Math.PI*2;//方位角

					float px = (float)(Math.cos(elevation)*Math.cos(direction));
					float py = (float)(Math.cos(elevation)*Math.sin(direction));
					float pz = (float)Math.sin(elevation);
					points[i*4]=  R*(1-points[i*4+3]/MAX_LIFE_SPAN)*px;//粒子对应的第一个点的x坐标
					points[i*4+1]=R*(1-points[i*4+3]/MAX_LIFE_SPAN)*py;//粒子对应的第一个点的y坐标
					points[i*4+2]=R*(1-points[i*4+3]/MAX_LIFE_SPAN)*pz;//粒子对应的第一个点的z坐标
				}
			}
		}

		for(int i=0;i<groupCount;i++)
		{//循环发射一批激活计数器所指定位置的粒子
			if(points[groupCount*count*4+4*i+3]==10.0f)//如果粒子处于未激活状态时
			{
				points[groupCount*count*4+4*i+3]=lifeSpanStep;//激活粒子--设置粒子当前的生命期
			}
		}
		synchronized(lock)
		{//加锁--防止在更新顶点坐标数据时，将顶点坐标数据送入渲染管线
			fpfd.updatVertexData(points);//更新顶点坐标数据缓冲的方法
		}
		count++;//下次激活粒子的位置
	}

	public void calculateBillboardDirection(float x,float y,float z)
	{
		//根据摄像机位置及火焰位置计算粒子系统朝向

		//计算Y轴转动角度
		float xSpan=MatrixState3D.cameraPosition[0]-x;
		float zSpan=MatrixState3D.cameraPosition[2]-z;
		if(xSpan<=0)
		{
			yAngle=180+(float) Math.toDegrees(Math.atan(xSpan/zSpan));
		}
		else
		{
			yAngle=(float) Math.toDegrees(Math.atan(xSpan/zSpan));
		}

		//计算X轴转动角度
		float ySpan=MatrixState3D.cameraPosition[1]-y;
		if(zSpan<=0)
		{
			xAngle=180+(float) Math.toDegrees(Math.atan(ySpan/zSpan));
		}
		else
		{
			xAngle=(float) Math.toDegrees(Math.atan(ySpan/zSpan));
		}
	}

	public int compareTo(ParticleBombSystem another)
	{
		//重写的比较两个火焰离摄像机距离的方法
		float xs=-MatrixState3D.cameraPosition[0];
		float zs=-MatrixState3D.cameraPosition[2];

		float xo=-MatrixState3D.cameraPosition[0];
		float zo=-MatrixState3D.cameraPosition[2];

		float disA=(float)(xs*xs+zs*zs);
		float disB=(float)(xo*xo+zo*zo);
		return ((disA-disB)==0)?0:((disA-disB)>0)?-1:1;
	}

}
