#version 330 core
layout(location = 0) in vec3 pos;
layout(location = 1) in vec2 inputCoord;
uniform mat4 transform;
uniform mat4 proj;
uniform mat4 view;
out vec2 texCoord;
void main() {
    texCoord = inputCoord;
    gl_Position = (proj * view * transform) * vec4(pos,1.0f);
}
