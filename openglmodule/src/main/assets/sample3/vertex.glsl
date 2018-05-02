attribute vec4 aColor;
attribute vec4 aPosition;
uniform mat4 uMVPMatrix;
varying vec4 vColor;

void main() {
    gl_Position = uMVPMatrix * aPosition;
//    gl_Position = vec4(aPosition,1);
    vColor = aColor;
}
