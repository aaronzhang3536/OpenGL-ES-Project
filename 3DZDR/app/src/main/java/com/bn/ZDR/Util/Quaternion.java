package com.bn.ZDR.Util;
/*
 * 代表四元数的类
 */
public class Quaternion {
	//四元数的四个分量值
	float w, x, y, z;
	//单位四元数
	public static Quaternion getIdentityQuaternion()
	{
		return  new Quaternion(1f, 0f, 0f, 0f);
	}

	public Quaternion() {	}
	public Quaternion(float w, float x, float y, float z)
	{
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	//构造绕指定轴旋转的四元数的方法
	public void setToRotateAboutAxis(Vector3f axis, float theta){
		//旋转轴必须规格化
		axis.normalize();
		//计算半角和sin值
		float thetaOver2 = theta/2f;
		float sinThetaOver2 = (float) Math.sin(thetaOver2);
		//赋值
		w = (float) Math.cos(thetaOver2);
		x = axis.x * sinThetaOver2;
		y = axis.y * sinThetaOver2;
		z = axis.z * sinThetaOver2;
	}
	//提取旋转角的方法
	public float getRotationAngle(){
		//计算半角，w = cos(theta/2)
		float thetaOver2 = (float) Math.acos(w);
		//返回旋转角
		return thetaOver2 * 2f;
	}
	//提取旋转轴的方法
	public Vector3f getRotationAxis(){
		//计算sin^2(theta/2), 记住w = cos(theta/2),  sin^2(x) + cos^2(x) = 1
		float sinThetaOver2Sq = 1.0f - w*w;
		//注意保证数值精度
		if(sinThetaOver2Sq <= 0.0f){
			//单位四元数或不精确的数值，只要返回有效的向量即可
			return new Vector3f(1.0f, 0f, 0f);
		}
		//计算1/sin(theta/2)
		float oneOversinThetaOver2 = (float) (1.0f/ Math.sqrt(sinThetaOver2Sq));
		//返回旋转轴
		return new Vector3f(
				x * oneOversinThetaOver2,
				y * oneOversinThetaOver2,
				z * oneOversinThetaOver2
		);
	}
	/*
	 * 四元数叉乘运算，用以汇总多次旋转，
	 * 乘的顺序是从左向右，
	 * 这和四元数叉乘的“标准”定义相反
	 */
	public Quaternion cross(Quaternion a){
		Quaternion result = new Quaternion();

		result.w = w*a.w - x*a.x - y*a.y - z*a.z;
		result.x = w*a.x + x*a.w + z*a.y - y*a.z;
		result.y = w*a.y + y*a.w + x*a.z - z*a.x;
		result.z = w*a.z + z*a.w + y*a.x - x*a.y;

		return result;
	}
}
