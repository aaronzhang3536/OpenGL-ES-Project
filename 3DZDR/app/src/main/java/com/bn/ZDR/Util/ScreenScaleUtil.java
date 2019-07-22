package com.bn.ZDR.Util;


import static com.bn.ZDR.Util.Constant.SCREEN_HEIGHT;
import static com.bn.ZDR.Util.Constant.SCREEN_WIDTH;
import static com.bn.ZDR.Util.Constant.SideLengh;

//计算缩放情况的工具类
public class ScreenScaleUtil
{
	static final float sHpWidth=1920;//原始横屏的宽度
	static final float sHpHeight=1080;//原始横屏的高度
	static final float whHpRatio=sHpWidth/sHpHeight;//原始横屏的宽高比

	static final float sSpWidth=Constant.SCREEN_WIDTH;//原始竖屏的宽度
	static final float sSpHeight=Constant.SCREEN_HEIGHT;//原始竖屏的高度

	static final float whSpRatio=sSpWidth/sSpHeight;//原始竖屏的宽高比
	public static ScreenScaleResult calScale
			(
					float targetWidth,	//目标宽度
					float targetHeight	//目标高度
			)
	{
		ScreenScaleResult result=null;
		ScreenOrien so=null;
		//首先判断目标是横屏还是竖屏
		if(targetWidth>targetHeight)
		{
			so=ScreenOrien.HP;
		}
		else
		{
			so=ScreenOrien.SP;
		}
		System.out.println(so);
		//进行横屏结果的计算
		if(so==ScreenOrien.HP)
		{
			//计算目标的宽高比
			float targetRatio=targetWidth/targetHeight;
			if(targetRatio>=whHpRatio)
			{
				//若目标宽高比大于原始宽高比则以目标的高度计算结果
				float ratio=targetHeight/sHpHeight;
				float realTargetWidth=sHpWidth*ratio;
				float lcuX=(targetWidth-realTargetWidth)/2.0f;
				float lcuY=0;
				result=new ScreenScaleResult((int)lcuX,(int)lcuY,ratio,so);
			}
			else
			{
				//若目标宽高比小于原始宽高比则以目标的宽度计算结果
				float ratio=targetWidth/sHpWidth;
				float realTargetHeight=sHpHeight*ratio;
				float lcuX=0;
				float lcuY=(targetHeight-realTargetHeight)/2.0f;
				result=new ScreenScaleResult((int)lcuX,(int)lcuY,ratio,so);
			}
		}
		//进行竖屏结果的计算
		if(so==ScreenOrien.SP)
		{
			//计算目标的宽高比
			float targetRatio=targetWidth/targetHeight;

			if(targetRatio>whSpRatio)
			{
				//若目标宽高比大于原始宽高比则以目标的高度计算结果
				float ratio=targetHeight/sSpHeight;
				float realTargetWidth=sSpWidth*ratio;
				float lcuX=(targetWidth-realTargetWidth)/2.0f;
				float lcuY=0;
				result=new ScreenScaleResult((int)lcuX,(int)lcuY,ratio,so);
			}
			else
			{
				//若目标宽高比小于原始宽高比则以目标的宽度计算结果
				float ratio=targetWidth/sSpWidth;
				float realTargetHeight=sSpHeight*ratio;
				float lcuX=0;
				float lcuY=(targetHeight-realTargetHeight)/2.0f;
				result=new ScreenScaleResult((int)lcuX,(int)lcuY,ratio,so);
			}
		}
		return result;
	}

	//目标屏幕的触控点转为原始屏幕触控点的方法
	public static int[] touchFromTargetToOrigin(float x,float y,ScreenScaleResult ssr)
	{
		int[] result=new int[2];

		result[0]=(int) ((x-ssr.lucX)/ssr.ratio);
		result[1]=(int) ((y-ssr.lucY)/ssr.ratio);

		return result;
	}

	//返回屏幕3d位置
	public static float[] fromPixPositionToScreenPosition(float cx,float cy)
	{
		float xAndY_Position[]=new float[2];//0 x 1 y
		if (SCREEN_HEIGHT>SCREEN_WIDTH)
		{
			xAndY_Position[0]=(cx-Constant.SCREEN_WIDTH/2)/(Constant.SCREEN_WIDTH/2);
			xAndY_Position[1]=(Constant.SCREEN_HEIGHT/2-cy)/(Constant.SCREEN_HEIGHT/2)*(Constant.SCREEN_HEIGHT/Constant.SCREEN_WIDTH);
		}
		else if (SCREEN_HEIGHT<SCREEN_WIDTH) {
			xAndY_Position[1] = ((Constant.SCREEN_HEIGHT / 2) - cy) / (Constant.SCREEN_HEIGHT / 2);
			xAndY_Position[0] = (cx - (SCREEN_WIDTH / 2)) / (SCREEN_WIDTH / 2) * (SCREEN_WIDTH / SCREEN_HEIGHT);
		}
		return  xAndY_Position;
	}
	//返回3d尺寸
	public static float[] from2DSizeTo3DSize(float Width,float High)
	{
		float wAndH[]=new float[2];//1 h 0 w
		if(SCREEN_HEIGHT>SCREEN_WIDTH)//根据像素大小的坐标绘制一个长方形
		{
			wAndH[1] = (High/Constant.SCREEN_HEIGHT)*(Constant.SCREEN_HEIGHT/Constant.SCREEN_WIDTH);
			wAndH[0]= Width/Constant.SCREEN_WIDTH;
		}
		else if (SCREEN_HEIGHT<SCREEN_WIDTH)
		{
			wAndH[1] = (High/Constant.SCREEN_HEIGHT);
			wAndH[0] =( Width/Constant.SCREEN_WIDTH)*(Constant.SCREEN_WIDTH/Constant.SCREEN_HEIGHT);;
		}
		return  wAndH;
	}

	//根据行列返回坐标
	public static float[] fromRowColToXYZ(float i,float j,float y)
	{
		float xz[]=new float[3];
		xz[0]=0.5f+SideLengh*j;//x
		xz[1]=y;
		xz[2]=-0.5f-SideLengh*i;//z
		return xz;
	}


}