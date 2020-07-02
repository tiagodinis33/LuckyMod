#version 400 core
layout(location = 0) in vec4 pos;
layout(location = 1) in int hasTexi;
layout(location = 2) in vec2 texCoords;
layout(location = 3) in int texId;
out float hasTex;
out vec2 texCoorde;
flat out int texID;
uniform mat4 projection;

void main() {
    texID = texId;
    texCoorde = texCoords;
    hasTex = hasTexi;
    gl_Position = projection * pos;
}
