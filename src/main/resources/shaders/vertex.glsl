#version 330 core
layout(location = 0) in vec2 aPos;
layout(location = 1) in vec2 aTexCoord;
out vec2 TexCoord;

uniform mat4 uProjection;
uniform vec2 uPosition;
uniform vec2 uScale;

void main() {
    vec2 scaled = aPos * uScale + uPosition;
    gl_Position = uProjection * vec4(scaled, 0.0, 1.0);
    TexCoord = aTexCoord;
}
