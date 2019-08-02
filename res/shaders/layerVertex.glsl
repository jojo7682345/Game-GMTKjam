#version 330

layout (location = 0) in vec3 position0;
layout (location = 1) in vec3 normal0;
layout (location = 2) in vec2 textureCoord0;

out vec2 texture_FS;

uniform vec3 scale;
uniform mat4 viewMatrix;

void main()
{
	gl_Position = viewMatrix * vec4(position0.xy*scale.xy,scale.z,1);
	texture_FS = textureCoord0;
}