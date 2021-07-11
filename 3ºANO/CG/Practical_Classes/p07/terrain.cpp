#include "terrain.h"


void drawTerrain() {
    glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);

    glVertexPointer(3, GL_FLOAT, 0, 0);

    glPushMatrix();

    for (int i = 0; i < th - 1; i++) {
        glDrawArrays(GL_TRIANGLE_STRIP, (tw) * 2 * i, (tw) * 2);
    }

    glPopMatrix();
}


float h(int c, int l) {
    return imageData[l * tw + c] / 255.0 * 100;
}


void initVBO(float tw, float th) {
    //Step 1 – Allocate and fill arrays with vertices
    float *vertexB = NULL;

    int num_strips = th - 1;
    int vert_strip = tw * 2;

    int z = -th / 2;
    int i = 0;

    vertexB = (float *) malloc(vert_strip * num_strips * sizeof(float) * 3);

    //Step 2 - Enable Buffers
    glEnableClientState(GL_VERTEX_ARRAY);

    for (int s = 0; s < num_strips; s++) {
        int x = -tw / 2;
        for (int c = 0; c < tw; c++) {
            vertexB[i++] = x;
            vertexB[i++] = h(c, s);
            vertexB[i++] = z;

            vertexB[i++] = x;
            vertexB[i++] = h(c, s + 1);
            vertexB[i++] = z + 1;

            x++;
        }
        z++;
    }

    //Step 3 - Generate Vertex Buffer Objects
    glGenBuffers(1, buffers);
    glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
    glBufferData(GL_ARRAY_BUFFER, vert_strip * num_strips * sizeof(float) * 3, vertexB, GL_STATIC_DRAW);
}


void init() {
    ilGenImages(1, &t);
    ilBindImage(t);

    ilLoadImage((ILstring) "terreno.jpg");

    ilConvertImage(IL_LUMINANCE, IL_UNSIGNED_BYTE);

    tw = ilGetInteger(IL_IMAGE_WIDTH);

    th = ilGetInteger(IL_IMAGE_HEIGHT);

    imageData = ilGetData();
}