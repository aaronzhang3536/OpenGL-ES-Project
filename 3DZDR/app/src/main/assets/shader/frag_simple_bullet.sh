#version 300 es
precision mediump float;
in vec2 vTextureCoord; //接收从顶点着色器过来的参数
uniform sampler2D sTexture;//纹理内容数据
out vec4 fragColor;

uniform float baozhashijian;//纹理矩形半径

in vec3 vPosition;//接收从顶点着色器过来的顶点位置
in vec4 vAmbient;//接收从顶点着色器过来的环境光最终强度
in vec4 vDiffuse;//接收从顶点着色器过来的散射光最终强度
in vec4 vSpecular;//接收从顶点着色器过来的镜面反射光最终强度
void main()
{
   //给此片元从纹理中采样出颜色值
   fragColor = texture(sTexture, vTextureCoord);
   fragColor = fragColor*vAmbient + fragColor*vDiffuse + fragColor*vSpecular;
   fragColor = fragColor*(baozhashijian/(4200.0));
}