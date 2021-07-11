#ifndef CLASS7_TERRAIN_H
#define CLASS7_TERRAIN_H

#include <iostream>
#include <stdlib.h>
#include <math.h>
#include <IL/il.h>
#include <GL/glew.h>
#include <GL/glut.h>

extern unsigned int t, tw, th;
extern unsigned char *imageData;
extern int arraySize;
extern GLuint buffers[1];


void drawTerrain();

float h(int c, int l);

void initVBO(float height, float width);

void init();

#endif