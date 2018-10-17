package com.bn.ZDR.Particle;//声明包

import android.opengl.GLES30;

public class ParticleJiGuangDataConstant
{
	//资源访问锁
	public static Object lock=new Object();

	public static final float[] START_COLOR={1,0,0,1.0f};//粒子起始颜色

	public static final float[] END_COLOR={0.0f,0.0f,0.0f,0.1f};//粒子终止颜色

	public static final int SRC_BLEND=GLES30.GL_SRC_ALPHA;//源混合因子

	public static final int DST_BLEND=GLES30.GL_ONE;//目标混合因子

	public static final int BLEND_FUNC=GLES30.GL_FUNC_ADD;//混合方式

	public static final int COUNT=400;//总粒子数

	public static final float RADIS=20f;//单个粒子半径

	public static final float MAX_LIFE_SPAN=5f;//粒子最大生命期

	public static final float LIFE_SPAN_STEP=0.01f;//粒子生命期步进

	public static final int GROUP_COUNT=5;//每批激活的粒子数量

	public static final int THREAD_SLEEP=50;//粒子更新物理线程休眠时间（ms）

}
