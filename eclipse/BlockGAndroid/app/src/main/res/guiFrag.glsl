#version 400 core
out vec4 color;
in vec2 texCoorde;
in float hasTex;
flat in int texID;
uniform sampler2D texts[32];
void main() {
    if(hasTex == 1.0f){
        color = texture(texts[texID], texCoorde);
    }else
        color = vec4(1.0f,0.0f,0.0f,1.0f);
}
