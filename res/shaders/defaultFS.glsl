#version 330

layout(location = 0) out vec4 outputColor;

in vec3 normal_FS;
in vec2 texture_FS;
in vec3 toCameraDirection;

const vec3 lightDirection = vec3(2,10,5);

//uniform sampler2D tex;
uniform float alpha;
uniform vec3 color;
uniform float specularFactor;

float sigmoid(float x){
 	return 0.8/(1.0 + pow(10, -(x-0.5)))+0.2;
}

void main(){
	float nDotl = sigmoid(dot(normal_FS,lightDirection));
	float diffuse = max(nDotl,0.2);
	
	vec3 reflectedLightDirection = reflect(-lightDirection,normal_FS);
	
	float s = dot(reflectedLightDirection , toCameraDirection);
	s = max(s*specularFactor,0.0);
	
	vec3 fragColor = vec3(1,1,1);//texture(tex,texture_FS);
	outputColor = vec4(color*diffuse+s,alpha);
	
}