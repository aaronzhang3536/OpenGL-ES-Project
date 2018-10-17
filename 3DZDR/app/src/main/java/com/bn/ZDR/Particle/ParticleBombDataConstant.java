package com.bn.ZDR.Particle;//声明包

import android.opengl.GLES30;//相关类的引用

import com.bn.ZDR.Util.Constant;


public class ParticleBombDataConstant
{
	//资源访问锁
	public static Object lock=new Object();

	public static final float[] START_COLOR={0.7569f,0.2471f,0.1176f,1.0f};//粒子起始颜色
//			{
//					{0.7569f,0.2471f,0.1176f,1.0f},	//0-普通火焰
//					{0.7569f,0.2471f,0.1176f,1.0f},	//1-白亮火焰
//					{0.6f,0.6f,0.6f,1.0f},			//2-普通烟
//					{0.6f,0.6f,0.6f,1.0f},			//3-纯黑烟
//			};

	public static final float[] END_COLOR={0.0f,0.0f,0.0f,0.1f};//粒子终止颜色
//			{
//					{0.0f,0.0f,0.0f,0.0f},	//0-普通火焰
//					{0.0f,0.0f,0.0f,0.0f},	//1-白亮火焰
//					{0.0f,0.0f,0.0f,0.0f},	//2-普通烟
//					{0.0f,0.0f,0.0f,0.0f},	//3-纯黑烟
//			};

	public static final int SRC_BLEND=GLES30.GL_SRC_ALPHA;//源混合因子
//			{
//					GLES30.GL_SRC_ALPHA,   				//0-普通火焰
//					GLES30.GL_ONE,   					//1-白亮火焰
//					GLES30.GL_SRC_ALPHA,				//2-普通烟
//					GLES30.GL_ONE,						//3-纯黑烟
//			};

	public static final int DST_BLEND=GLES30.GL_ONE;//目标混合因子
//			{
//					GLES30.GL_ONE,      				//0-普通火焰
//					GLES30.GL_ONE,      				//1-白亮火焰
//					GLES30.GL_ONE_MINUS_SRC_ALPHA,		//2-普通烟
//					GLES30.GL_ONE,						//3-纯黑烟
//			};

	public static final int BLEND_FUNC=GLES30.GL_FUNC_ADD;//混合方式
//			{
//					GLES30.GL_FUNC_ADD,    				//0-普通火焰
//					GLES30.GL_FUNC_ADD,    				//1-白亮火焰
//					GLES30.GL_FUNC_ADD,    				//2-普通烟
//					GLES30.GL_FUNC_REVERSE_SUBTRACT,	//3-纯黑烟
//			};

	public static final int COUNT=400;//总粒子数
//			{
//					340,   					//0-普通火焰
//					340,   					//1-白亮火焰
//					99,						//2-普通烟
//					99,						//3-纯黑烟
//			};

	public static final float RADIS=25f;//单个粒子半径
//			{
//					0.5f,		//0-普通火焰
//					0.5f,		//1-白亮火焰
//					0.8f,		//2-普通烟
//					0.8f,		//3-纯黑烟
//			};

	public static final float MAX_LIFE_SPAN=5f;//粒子最大生命期
//			{
//					5.0f,		//0-普通火焰
//					5.0f,		//1-白亮火焰
//					6.0f,		//2-普通烟
//					6.0f,		//3-纯黑烟
//			};

	public static final float LIFE_SPAN_STEP=0.5f;//粒子生命期步进
//			{
//					0.07f,		//0-普通火焰
//					0.07f,		//1-白亮火焰
//					0.07f,		//2-普通烟
//					0.07f,		//3-纯黑烟
//			};

	public static final int GROUP_COUNT=20;//每批激活的粒子数量
//			{
//					4,			//0-普通火焰
//					4,			//1-白亮火焰
//					1,			//2-普通烟
//					1,			//3-纯黑烟
//			};

	public static final int THREAD_SLEEP=50;//粒子更新物理线程休眠时间（ms）

	public static float R= Constant.SideLengh;//爆炸球半径
//			{
//					60,		//0-普通火焰
//					60,		//1-白亮火焰
//					30,		//2-普通烟
//					30,		//3-纯黑烟
//			};

}
