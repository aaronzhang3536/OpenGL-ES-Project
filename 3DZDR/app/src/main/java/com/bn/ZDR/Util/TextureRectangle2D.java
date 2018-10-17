package com.bn.ZDR.Util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.opengl.GLES30;
import android.os.Build;

import com.bn.ZDR.View.MyGLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Comparator;

import static com.bn.ZDR.Util.Constant.SCREEN_HEIGHT;
import static com.bn.ZDR.Util.Constant.SCREEN_WIDTH;
import static com.bn.ZDR.Util.Constant.beijingId;
import static com.bn.ZDR.Util.Constant.ssr;
import static com.bn.ZDR.Util.Constant.tiny_roate_angle;
import static com.bn.ZDR.Util.ScreenScaleUtil.sHpHeight;
import static com.bn.ZDR.Util.ScreenScaleUtil.sHpWidth;

@SuppressLint("NewApi")
public class TextureRectangle2D extends TouchableObject
{
	int vCount = 6;
	FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
	FloatBuffer mTextureBuffer;//顶点纹理坐标数据缓冲
	int mProgram;// 自定义渲染管线程序id
	int muMVPMatrixHandle;// 总变换矩阵引用id
	int maPositionHandle; // 顶点位置属性引用id
	int maTexCoorHandle; // 顶点纹理坐标属性引用id
	int maNormalHandle; //顶点法向量属性引用
	int muMMatrixHandle;//位置、旋转变换矩阵
	String mVertexShader;// 顶点着色器
	String mFragmentShader;// 片元着色器
	float hlafHigh;			//绘制物高的一半<Open GL坐标>
	float hlafWidth;		//绘制物宽的一半
	float[] vertices;//定义顶点坐标
	public TextureRectangle2D(MyGLSurfaceView mv)//按需求大小绘制一个2D长方形到指定位置
	{
		// 初始化顶点坐标与着色数据
		initVertexData();
		// 初始化shader
		initShader(mv);
	}

	// 初始化顶点坐标与着色数据的方法
	private void initVertexData()
	{
		vertices = new float[] {
				-1,1,0,//0
				1,-1,0,//2
				1,1,0,//3
				-1,1,0,//0
				-1,-1,0,//1
				1,-1,0//2
		};
		ByteBuffer vbb_advance = ByteBuffer
				.allocateDirect(vertices.length * 4);
		vbb_advance.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb_advance.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);
		float[] textures = new float[] {
				0, 0,
				1, 1,
				1, 0,
				0, 0,
				0, 1,
				1, 1};
		ByteBuffer cbb_advance = ByteBuffer
				.allocateDirect(textures.length * 4);
		cbb_advance.order(ByteOrder.nativeOrder());
		mTextureBuffer = cbb_advance.asFloatBuffer();
		mTextureBuffer.put(textures);
		mTextureBuffer.position(0);

	}

	// 初始化着色器
	@SuppressLint("NewApi")
	public void initShader(MyGLSurfaceView mv)
	{
		// 加载顶点着色器的脚本内容
		mVertexShader = ShaderUtil.loadFromAssetsFile("vertex_sky.sh",
				mv.getResources());
		// 加载片元着色器的脚本内容
		mFragmentShader = ShaderUtil.loadFromAssetsFile("frag_sky.sh",
				mv.getResources());
		// 基于顶点着色器与片元着色器创建程序
		mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
		//获取位置、旋转变换矩阵引用
		muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix");
		//获取程序中顶点颜色属性引用
		maNormalHandle= GLES30.glGetAttribLocation(mProgram, "aNormal");
		// 获取程序中顶点位置属性引用id
		maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
		// 获取程序中顶点纹理坐标属性引用id
		maTexCoorHandle = GLES30.glGetAttribLocation(mProgram, "aTexCoor");
		// 获取程序中总变换矩阵引用id
		muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2) @SuppressLint("NewApi")
	public void drawSelf(int texId,float cx,float cy, float Width,float High,int type )
	{
		// 制定使用某套shader程序
		GLES30.glUseProgram(mProgram);
		float xDraw=cx;
		float yDraw=cy;
		if(type!=2)//位置不是Open GL ES坐标
		{
			if (SCREEN_HEIGHT>SCREEN_WIDTH)
			{
				xDraw=(cx-Constant.SCREEN_WIDTH/2)/(Constant.SCREEN_WIDTH/2);
				yDraw=(Constant.SCREEN_HEIGHT/2-cy)/(Constant.SCREEN_HEIGHT/2)*(Constant.SCREEN_HEIGHT/Constant.SCREEN_WIDTH);
			}
			else if (SCREEN_HEIGHT<SCREEN_WIDTH)
			{
				yDraw= 	((Constant.SCREEN_HEIGHT/2)-cy)/(Constant.SCREEN_HEIGHT/2);
				xDraw= (cx-(SCREEN_WIDTH/2))/(SCREEN_WIDTH/2)*(SCREEN_WIDTH/SCREEN_HEIGHT);
//			xDraw=(cx-Constant.SCREEN_WIDTH/2)/(Constant.SCREEN_WIDTH/2);
//			yDraw=(Constant.SCREEN_HEIGHT/2-cy)/(Constant.SCREEN_HEIGHT/2)*(Constant.SCREEN_WIDTH/Constant.SCREEN_HEIGHT);
//			xDraw=(cx-Constant.SCREEN_WIDTH/2)/(Constant.SCREEN_WIDTH/2);
//			yDraw=(Constant.SCREEN_HEIGHT/2-cy)/(Constant.SCREEN_HEIGHT/2)*(Constant.SCREEN_HEIGHT/Constant.SCREEN_WIDTH);
			}
		}

		if(type==0)//根据Open GL ES的坐标大小绘制一个长方形
		{
			this.hlafHigh = High;
			this.hlafWidth = Width;
		}
		else if(SCREEN_HEIGHT>SCREEN_WIDTH)//根据像素大小的坐标绘制一个长方形
		{
			this.hlafHigh = (High/Constant.SCREEN_HEIGHT)*(Constant.SCREEN_HEIGHT/Constant.SCREEN_WIDTH);
			this.hlafWidth = Width/Constant.SCREEN_WIDTH;
		}
		else if (SCREEN_HEIGHT<SCREEN_WIDTH)
		{
			this.hlafHigh = (High/Constant.SCREEN_HEIGHT);
			this.hlafWidth =( Width/Constant.SCREEN_WIDTH)*(Constant.SCREEN_WIDTH/Constant.SCREEN_HEIGHT);
		}

		//保护现场
		MatrixState2D.translate(xDraw,yDraw,0);
		if (texId==beijingId)
		{
			MatrixState2D.rotate(tiny_roate_angle,0,0,1);
		}
		MatrixState2D.scale(hlafWidth,hlafHigh,1);

		// 将最终变换矩阵传入shader程序
		GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				MatrixState2D.getFinalMatrix(), 0);
		// 为画笔指定顶点位置数据
		GLES30.glVertexAttribPointer(maPositionHandle, 3, GLES30.GL_FLOAT,
				false, 3 * 4, mVertexBuffer);
		// 为画笔指定顶点纹理坐标数据
		GLES30.glVertexAttribPointer(maTexCoorHandle, 2, GLES30.GL_FLOAT,
				false, 2 * 4, mTextureBuffer);

		// 允许顶点位置数据数组
		GLES30.glEnableVertexAttribArray(maPositionHandle);
		GLES30.glEnableVertexAttribArray(maTexCoorHandle);

		// 绑定纹理
		GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);
		// 绘制纹理矩形
		GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
	}

	public void drawSelf(int texId)
	{
		// 制定使用某套shader程序
		GLES30.glUseProgram(mProgram);
		// 将最终变换矩阵传入shader程序
		GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				MatrixState2D.getFinalMatrix(), 0);
		// 为画笔指定顶点位置数据
		GLES30.glVertexAttribPointer(maPositionHandle, 3, GLES30.GL_FLOAT,
				false, 3 * 4, mVertexBuffer);
		// 为画笔指定顶点纹理坐标数据
		GLES30.glVertexAttribPointer(maTexCoorHandle, 2, GLES30.GL_FLOAT,
				false, 2 * 4, mTextureBuffer);

		// 允许顶点位置数据数组
		GLES30.glEnableVertexAttribArray(maPositionHandle);
		GLES30.glEnableVertexAttribArray(maTexCoorHandle);

		// 绑定纹理
		GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);
		// 绘制纹理矩形
		GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
	}

	/**
	 * 绘制数字
	 */
	public float[] textures;//定义纹理坐标
	//绘制数字的构造器
	public TextureRectangle2D(float width,float height,float[] textures,MyGLSurfaceView mv){
		this.hlafWidth=width;
		this.hlafHigh=height;
		this.textures=textures;
		initVertexData(width,height,true,1);
		initShader(mv);
	}

	public void initVertexData(float width,float height,boolean hasTexture,int n)
	{
		vCount=4;
		float vertices[]=new float[]
				{
						-width/2,height/2,0,
						-width/2,-height/2,0,
						width/2,height/2,0,
						width/2,-height/2,0
				};
		//创建顶点坐标数据缓冲
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序为本地操作系统顺序
		mVertexBuffer = vbb.asFloatBuffer();//转换为Float型缓冲
		mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);//设置缓冲区起始位置
		if(!hasTexture)//如果没有传入纹理
		{
			textures=new float[]//顶点颜色值数组，每个顶点4个色彩值RGBA
					{
							0,0, 0,n,
							n,0, n,n
					};
		}
		//创建顶点纹理坐标数据缓冲
		ByteBuffer cbb = ByteBuffer.allocateDirect(textures.length*4);
		cbb.order(ByteOrder.nativeOrder());//设置字节顺序为本地操作系统顺序
		mTextureBuffer = cbb.asFloatBuffer();//转换为Float型缓冲
		mTextureBuffer.put(textures);//向缓冲区中放入顶点着色数据
		mTextureBuffer.position(0);//设置缓冲区起始位置
	}

	public void numberdrawSelf(int texId)
	{
		//制定使用某套shader程序
		GLES30.glUseProgram(mProgram);
		//将最终变换矩阵传入shader程序
		GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState2D.getFinalMatrix(), 0);
		//将位置、旋转变换矩阵传入着色器程序
		GLES30.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState2D.getMMatrix(), 0);
		//传入顶点位置数据
		GLES30.glVertexAttribPointer
				(
						maPositionHandle,
						3,
						GLES30.GL_FLOAT,
						false,
						3*4,
						mVertexBuffer
				);
		//传入顶点纹理坐标数据
		GLES30.glVertexAttribPointer
				(
						maTexCoorHandle,
						2,
						GLES30.GL_FLOAT,
						false,
						2*4,
						mTextureBuffer
				);
		//允许顶点位置数据数组
		GLES30.glEnableVertexAttribArray(maPositionHandle);
		//GLES30.glEnableVertexAttribArray(maNormalHandle);
		GLES30.glEnableVertexAttribArray(maTexCoorHandle);
		//绑定纹理
		GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);
		//绘制纹理矩形
		GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, vCount);
	}


}