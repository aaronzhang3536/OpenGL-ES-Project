package com.bn.ZDR.Scene;


import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState2D;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.Util.ScreenScaleUtil;
import com.bn.ZDR.Util.TextureRectangle2D;
import com.bn.ZDR.View.MyGLSurfaceView;

import static com.bn.ZDR.Util.Constant.ButtonDropBombTextureId;
import static com.bn.ZDR.Util.Constant.RunEatGoldChengORJinbi;
import static com.bn.ZDR.Util.Constant.RunEatGoldChengTextureId;
import static com.bn.ZDR.Util.Constant.RunEatGoldJinBiTextureId;
import static com.bn.ZDR.Util.Constant.all2DTextureRectangle;
import static com.bn.ZDR.Util.Constant.runGoldSum;

//数字绘制类
public class NumberForDraw {
	//创建10个数字的纹理矩形
	TextureRectangle2D[] number;
	String scoreStr;//数字字符串
	float width_height[]=new float[2];//0 w 1 h
	MyGLSurfaceView mv;
	public NumberForDraw(int numberSize, float width, float height, MyGLSurfaceView mv)
	{
		this.mv=mv;
		number=new TextureRectangle2D[numberSize];
		width_height= ScreenScaleUtil.from2DSizeTo3DSize(width,height);
		//生成十个数字的纹理矩形
		for(int i=0;i<numberSize;i++)
		{
			number[i]=new TextureRectangle2D
					(
							width_height[0],
							width_height[1],
							new float[]
									{
											1f/numberSize*i,0,
											1f/numberSize*i,1,
											1f/numberSize*(i+1),0,
											1f/numberSize*(i+1),1,
									},
							mv
					);
		}
	}

	public void drawSelfGold(String score, int texId)//传入数字和纹理坐标
	{
		scoreStr=score;
		float xy[];
		MatrixState2D.pushMatrix();
		if (score.length()==1)
		{
			xy=ScreenScaleUtil.fromPixPositionToScreenPosition(Constant.Qianbi_number_X1,Constant.Qianbi_number_Y);
			MatrixState2D.translate(xy[0],xy[1],0.5f);
		}
		else if(score.length()==2)
		{
			xy=ScreenScaleUtil.fromPixPositionToScreenPosition(Constant.Qianbi_number_x2,Constant.Qianbi_number_Y);
			MatrixState2D.translate(xy[0],xy[1],0.5f);
		}
		else if (score.length()==3)
		{
			xy=ScreenScaleUtil.fromPixPositionToScreenPosition(Constant.Qianbi_number_X3,Constant.Qianbi_number_Y);
			MatrixState2D.translate(xy[0],xy[1],0.5f);
		}
		else  if (score.length()==4)
		{
			xy=ScreenScaleUtil.fromPixPositionToScreenPosition(Constant.Qianbi_number_x4,Constant.Qianbi_number_Y);
			MatrixState2D.translate(xy[0],xy[1],0.5f);
		}
		for(int i=0;i<scoreStr.length();i++)//将得分中的每个数字字符绘制
		{
			char c=scoreStr.charAt(i);

			MatrixState2D.translate(width_height[0], 0, 0);
			number[c-'0'].numberdrawSelf(texId);
		}
		MatrixState2D.popMatrix();
	}

	public void drawSelfTntNum(String score, int texId)//传入数字和纹理坐标 左对齐
	{
		scoreStr=score;
		float xy[] =ScreenScaleUtil.fromPixPositionToScreenPosition(Constant.tnt_number_X-8*(score.length()-1),Constant.tnt_number_Y);
		MatrixState2D.pushMatrix();
		MatrixState2D.translate(xy[0],xy[1],0.5f);
		for(int i=0;i<scoreStr.length();i++)//将得分中的每个数字字符绘制
		{
			char c=scoreStr.charAt(i);
			MatrixState2D.translate(width_height[0], 0, 0);
			number[c-'0'].numberdrawSelf(texId);
		}
		MatrixState2D.popMatrix();
	}

	//绘制金币连吃数字
	public void drawSelfRunEatGold(String score,int texId)
	{
		scoreStr=score;
		float xy[] =ScreenScaleUtil.fromPixPositionToScreenPosition(Constant.SCREEN_WIDTH/4,Constant.SCREEN_HEIGHT/4);
		MatrixState2D.pushMatrix();
		MatrixState2D.translate(xy[0],xy[1],0f);
		MatrixState2D.scale(0.05f*runGoldSum+1,0.05f*runGoldSum+1,1f);
		for(int i=0;i<scoreStr.length();i++)//将得分中的每个数字字符绘制
		{
			char c=scoreStr.charAt(i);
			MatrixState2D.translate(width_height[0], 0, 0);
			number[c-'0'].numberdrawSelf(texId);
		}
		MatrixState2D.translate(width_height[0], 0, 0);
		RunEatGoldChengORJinbi.numberdrawSelf(RunEatGoldChengTextureId);
		MatrixState2D.translate(width_height[0], 0, 0);
		MatrixState2D.scale(4,4,1);
		RunEatGoldChengORJinbi.numberdrawSelf(RunEatGoldJinBiTextureId);
		MatrixState2D.popMatrix();
	}

	public void drawself(String score,int texId,float x,float y)//画任何数字
	{
		scoreStr=score;
		float xy[] =ScreenScaleUtil.fromPixPositionToScreenPosition(x,y);
		MatrixState2D.pushMatrix();
		MatrixState2D.translate(xy[0],xy[1],0.5f);
		for(int i=0;i<scoreStr.length();i++)//将得分中的每个数字字符绘制
		{
			char c=scoreStr.charAt(i);
			MatrixState2D.translate(width_height[0], 0, 0);
			number[c-'0'].numberdrawSelf(texId);
		}
		MatrixState2D.popMatrix();
	}

}