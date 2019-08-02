#version 330

layout(location = 0) out vec4 outputColor;

in vec2 texture_FS;

uniform sampler2D tex;

void main(){
	vec4 color = texture(tex,texture_FS);
	if(color.a==0){
		discard;
	}
	outputColor = color;
	
}