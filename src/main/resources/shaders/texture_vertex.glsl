#version 330 core
layout(location = 0) in vec2 aPos;
layout(location = 1) in vec2 aTexCoord;
out vec2 TexCoord;

uniform mat4 uProjection;
uniform vec2 uPosition;
uniform vec2 uScale;
uniform float uRotation;

void main() {
	mat2 m = mat2(
	    cos(uRotation), -sin(uRotation),   // first column
	    sin(uRotation), cos(uRotation)    // second column
	);
	
	vec2 bPos = aPos * m;
    vec2 scaled = bPos * uScale + uPosition;
    gl_Position = uProjection * vec4(scaled, 0.0, 1.0);
    TexCoord = aTexCoord;
}
