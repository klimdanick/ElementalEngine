#version 330 core
layout(location = 0) in vec2 aPos;
layout(location = 1) in vec2 aTexCoord;
out vec2 TexCoord;

uniform mat4 uProjection;
uniform vec2 uPosition;
uniform vec2 uScale;
uniform float uRotation;

uniform mat3 uCamProjection;
uniform vec2 uCamTranslation;
uniform vec2 uCamScale;
uniform float uCamRotation;

void main() {
	mat2 rotationM = mat2(
	    cos(uRotation), -sin(uRotation),   // first column
	    sin(uRotation), cos(uRotation)    // second column
	);
	
	mat2 rotationMC = mat2(
	    cos(uCamRotation), -sin(uCamRotation),   // first column
	    sin(uCamRotation), cos(uCamRotation)    // second column
	);
	
	vec2 rotPos = aPos * rotationM;
    vec2 scaled = rotPos * uScale + uPosition;
    
    vec2 rotPos2 = scaled * rotationMC;
    vec2 scaled2 = rotPos2 * uCamScale - uCamTranslation;
    
    gl_Position = uProjection * vec4(scaled2, 0.0, 1.0);
    TexCoord = aTexCoord;
}
