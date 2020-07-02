#version 330 core
out vec4 outColor;
uniform sampler2D slot;
in vec2 texCoord;

void main() {
    outColor = texture(slot, texCoord);
}
