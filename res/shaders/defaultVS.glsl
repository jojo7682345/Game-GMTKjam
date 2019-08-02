#version 330

layout (location = 0) in vec3 position0;
layout (location = 1) in vec3 normal0;
layout (location = 2) in vec2 textureCoord0;

out vec3 normal_FS;
out vec2 texture_FS;
out vec3 toCameraDirection;

uniform mat4 modelViewProjectionMatrix;

void main()
{
	gl_Position = modelViewProjectionMatrix * vec4(position0,1);
	normal_FS = normal0;
	texture_FS = textureCoord0;
	toCameraDirection = (inverse(modelViewProjectionMatrix) * vec4(0,0,0,1)).xyz;
}