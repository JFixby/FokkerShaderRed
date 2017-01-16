#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 vColor;
varying vec2 vTexCoord;
varying vec2 vScreenCoord;

uniform sampler2D u_texture_0_current;
uniform sampler2D u_texture_1_original;

//resolution of screen or buffer dimensions
uniform float screen_width;
uniform float screen_height;
uniform float time;
uniform float progress;

uniform float alpha_blend;

float PI = 3.14159265358979323846;

bool isInsideRing(float x, float y, float n, float w) {

	float dist2 = sqrt(dot(vec2(x, y), vec2(x, y)));
//	return dist2 <= 1;
	return sin(dist2 * n * 2.0 * PI) > 0.0;

//	return x > 0.0;
}
float distance(vec2 x, vec2 y) {
	vec2 diff = y - x;
	return sqrt(dot(diff, diff));
}
float F(float x, float y, float t) {
	vec2 resolution = vec2(screen_width, screen_height);
	vec2 center = vec2(screen_width / 2.0, screen_height / 2.0);
	vec2 pos = vec2(x, y) - center;
	float radius = min(center.x, center.y) * 0.9
			* (0.25 + (1.0 - 0.25) * ((sin(t / 4.0) + 1.0) / 2.0));


	float dist = distance(pos, vec2(0.0, 0.0));
	if (dist < radius && dist > radius * 0.8) {
		return 1.0;
	}
	return 0.0;
}

vec4 finalColor(vec2 vTexCoord, float t, float p) {

	vec2 resolution = vec2(screen_width, screen_height);
	float aspectRatio = screen_width / screen_height;
	vec4 color = vec4(1.0, 1.0, 1.0, 1.0);

	color.a = 1.0;
	color.r = sin(time) * sin(time) * 0.0 + 1.0;
	color.g = vTexCoord.x;
	color.b = vTexCoord.y;

	int w = 0;
	int h = 0;
	float counter = 0.0;
	float alpha = 0.0;
	for (int i = -w; i <= w; i++) {
		for (int j = -h; j <= h; j++) {
			counter++;
			float x = screen_width * vTexCoord.x + float(i);
			float y = screen_height * vTexCoord.y + float(j);
			alpha = alpha + F(x, y, t);
		}
	}
	color.a = alpha / counter;
	return color;
}

void main() {

	gl_FragColor = finalColor(vTexCoord, time, progress);
}

