package com.bn.ZDR.Util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.os.Build;

import com.bn.ZDR.View.MyGLSurfaceView;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

//初始化所有图片资源
@SuppressLint("InlinedApi") @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class TextureManager {
	static HashMap<String,Integer> texList= new HashMap<String,Integer>();
	static String[] texturesName=
			{
					"first_level_game_land.png","first_level_game_wall.png","loading.png","iBuff_gold.jpg","first_level_game_box.png"//5
					,"yaogan1.png","yaogan2.png","zhadan.png","tnt.png","tnt_food.png"//10
					,"zhadanren.png","jinbi_background.png","huo_background.png","shui_background.png","number.png"//15
					,"b_huo.png","b_shui.png","w_huo.png","w_shui.png","third_level_game_land.png"//20
					,"tnt2.png","shui_food.png","huo_food.png","shui_fly_food.png","tnt_fly_food.png"//25
					,"huo_fly_food.png","sky.png","number_big.png","runEatGoldCheng.png","runEatGoldJinbi.png"//30
					,"cup.png","best.png","ji_guang_guai.png","final_score_background.png","relive.png"//35
					,"restart.png","second_level_game_wall.png","second_level_game_box.png","fire.png","ciqiu.png"//40
					,"second_level_game_land.png","relive1.png","relive2.png","set.png","chart.png"//45
					,"beijing.png","shouzhi.png","shou.png","bowen.png","lansezhengfangxing.png"//50
					,"fensezhengfangxing.png","lusezhengfanxing.png","zhadanTexture.png","set_page_title.png","set_page_mianban.png"//55
					,"set_page_high.png","set_page_low.png","set_page_yes.png","shui_background2.png","huo_background2.png"//60
					,"set_page_back.png","pause.png","resume.png","jieshu.png","mohu.png"//65
					,"chart_title.png","textTexture.png","chart_page_panel.png","third_level_game_box.png","third_level_game_wall.png"//70
					,"sky2.png","sky3.png","mandown_rect.png"//73


			};//纹理图的名称
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2) @SuppressLint({ "InlinedApi", "NewApi" })
	public static int initTexture(MyGLSurfaceView mv, String texName, boolean isRepeat)//生成纹理id
	{
		int[] textures=new int[1];
		GLES30.glGenTextures
				(
						1,//产生的纹理id的数量
						textures,//纹理id的数组
						0//偏移量
				);
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textures[0]);//绑定纹理id
		//设置MAG时为线性采样
		GLES30.glTexParameterf
				(
						GLES30.GL_TEXTURE_2D,
						GLES30.GL_TEXTURE_MAG_FILTER,
						GLES30.GL_LINEAR
				);
		//设置MIN时为最近点采样
		GLES30.glTexParameterf
				(
						GLES30.GL_TEXTURE_2D,
						GLES30.GL_TEXTURE_MIN_FILTER,
						GLES30.GL_NEAREST
				);
		if(isRepeat)
		{
			//设置S轴的拉伸方式为重复拉伸
			GLES30.glTexParameterf
					(
							GLES30.GL_TEXTURE_2D,
							GLES30.GL_TEXTURE_WRAP_S,
							GLES30.GL_REPEAT
					);
			//设置T轴的拉伸方式为重复拉伸
			GLES30.glTexParameterf
					(
							GLES30.GL_TEXTURE_2D,
							GLES30.GL_TEXTURE_WRAP_T,
							GLES30.GL_REPEAT
					);
		}else
		{
			//设置S轴的拉伸方式为截取
			GLES30.glTexParameterf
					(
							GLES30.GL_TEXTURE_2D,
							GLES30.GL_TEXTURE_WRAP_S,
							GLES30.GL_CLAMP_TO_EDGE
					);
			//设置T轴的拉伸方式为截取
			GLES30.glTexParameterf
					(
							GLES30.GL_TEXTURE_2D,
							GLES30.GL_TEXTURE_WRAP_T,
							GLES30.GL_CLAMP_TO_EDGE
					);
		}
		InputStream in = null;
		try {
			in = mv.getResources().getAssets().open(Constant.TexturePath+texName);
		}catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap bitmap= BitmapFactory.decodeStream(in);//从流中加载图片内容
		GLUtils.texImage2D
				(
						GLES30.GL_TEXTURE_2D,//纹理类型，在OpenGL ES中必须为GL30.GL_TEXTURE_2D
						0,//纹理的层次，0表示基本图像层，可以理解为直接贴图
						bitmap,//纹理图像
						0//纹理边框尺寸
				);
		bitmap.recycle();//纹理加载成功后释放内存中的纹理图
		return textures[0];
	}

	public static void loadingTexture(MyGLSurfaceView mv,int start,int picNum)//加载所有纹理图
	{
		for(int i=start;i<start+picNum;i++)
		{
			int texture=0;
			texture=initTexture(mv,texturesName[i],true);
			texList.put(texturesName[i],texture);//将数据加入到列表中
		}
	}

	public static int getTextures(String texName)//获得纹理图
	{
		int result=0;
		if(texList.get(texName)!=null)//如果列表中有此纹理图
		{
			result=texList.get(texName);//获取纹理图
		}
		else
		{
			result=-1;
		}
		System.out.println("result="+result+"texname="+texName);
		return result;
	}

}
