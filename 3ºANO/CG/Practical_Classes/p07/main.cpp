#include <iostream>
#include <stdlib.h>
#include <math.h>
#include <IL/il.h>
#include <GL/glew.h>
#include <GL/glut.h>

#include "build_3Dscenario.h"
#include "terrain.h"

float camX = 201, camY = 199, camZ = 201;
int startX, startY, tracking = 0;
int alpha = 0, beta = 45, r = 50;

float circulo1, circulo2 = 0;

// Initializations to load the Image
unsigned int t, tw, th;
unsigned char *imageData;

//Variables of VBO
int arraySize;
GLuint buffers[1];

int altura_do_gajo = 5;
float px;
float py;
float pz;
float upx = 0;
float upy = 1.0f;
float upz = 0;
float angulo_alfa;
float lx;
float ly;
float lz;

float coefeciente_k = 0.3;

float coef_rotate = 0.01;


void changeSize(int w, int h) {
    // Prevent a divide by zero, when window is too short
    // (you cant make a window with zero width).
    if (h == 0)
        h = 1;

    // compute window's aspect ratio
    float ratio = w * 1.0 / h;

    // Set the projection matrix as current
    glMatrixMode(GL_PROJECTION);
    // Load Identity Matrix
    glLoadIdentity();

    // Set the viewport to be the entire window
    glViewport(0, 0, w, h);

    // Set perspective
    gluPerspective(45.0f, ratio, 1.0f, 1000.0f);

    // return to the model view matrix mode
    glMatrixMode(GL_MODELVIEW);
}


void renderScene(void) {
    // clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // set the camera
    glLoadIdentity();
    gluLookAt(px, py, pz,
              lx, ly, lz,
              upx, upy, upz);

    glColor3f(200, 0, 200);

    drawTerrain();

    plantaArvores(250);

    criaFiguras(16);

    circulo2++;
    circulo1--;

    // End of frame
    glutSwapBuffers();
}


void processMouseButtons(int button, int state, int xx, int yy) {
    if (state == GLUT_DOWN) {
        startX = xx;
        startY = yy;
        if (button == GLUT_LEFT_BUTTON)
            tracking = 1;
        else if (button == GLUT_RIGHT_BUTTON)
            tracking = 2;
        else
            tracking = 0;
    } else if (state == GLUT_UP) {
        if (tracking == 1) {
            alpha += (xx - startX);
            beta += (yy - startY);
        } else if (tracking == 2) {
            r -= yy - startY;
            if (r < 3)
                r = 3.0;
        }
        tracking = 0;
    }
}


void processMouseMotion(int xx, int yy) {
    int deltaX, deltaY;
    int alphaAux, betaAux;
    int rAux;

    if (!tracking)
        return;

    deltaX = xx - startX;
    deltaY = yy - startY;

    if (tracking == 1) {
        alphaAux = alpha + deltaX;
        betaAux = beta + deltaY;
        if (betaAux > 85.0)
            betaAux = 85.0;
        else if (betaAux < -85.0)
            betaAux = -85.0;
        rAux = r;
    } else if (tracking == 2) {
        alphaAux = alpha;
        betaAux = beta;
        rAux = r - deltaY;
        if (rAux < 3)
            rAux = 3;
    }

    camX = rAux * sin(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
    camZ = rAux * cos(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
    camY = rAux * sin(betaAux * 3.14 / 180.0);
}


void processKeys(unsigned char c, int xx, int yy) {
    float dx = lx - px;
    float dy = 0;
    float dz = lz - pz;

    float rx = dy * upz - dz * upy;
    float ry = dz * upx - dx * upz;
    float rz = dx * upy - dy * upx;

    switch (c) {
        case 'a':
            px = px - coefeciente_k * rx;
            lx = lx - coefeciente_k * rx;
            pz = pz - coefeciente_k * rz;
            lz = lz - coefeciente_k * rz;
            py = altura_do_gajo + hf(px + (tw / 2.0), pz + (th / 2.0));
            ly = py;
            break;
        case 'd':
            px = px + coefeciente_k * rx;
            lx = lx + coefeciente_k * rx;
            pz = pz + coefeciente_k * rz;
            lz = lz + coefeciente_k * rz;
            py = altura_do_gajo + hf(px + (tw / 2.0), pz + (th / 2.0));
            ly = py;
            break;
        case 's':
            px = px - coefeciente_k * dx;
            lx = lx - coefeciente_k * dx;
            pz = pz - coefeciente_k * dz;
            lz = lz - coefeciente_k * dz;
            py = altura_do_gajo + hf(px + (tw / 2.0), pz + (th / 2.0));
            ly = py;
            break;
        case 'w':
            px = px + coefeciente_k * dx;
            lx = lx + coefeciente_k * dx;
            pz = pz + coefeciente_k * dz;
            lz = lz + coefeciente_k * dz;
            py = altura_do_gajo + hf(px + (tw / 2.0), pz + (th / 2.0));
            ly = py;
            break;
    }
}


void processSpecialKeys(int key, int x, int y) {
    switch (key) {
        case GLUT_KEY_LEFT:
            angulo_alfa += coef_rotate;
            lx = px + sin(angulo_alfa);
            ly = py;
            lz = pz + cos(angulo_alfa);
            break;
        case GLUT_KEY_RIGHT:
            angulo_alfa -= coef_rotate;
            lx = px + sin(angulo_alfa);
            ly = py;
            lz = pz + cos(angulo_alfa);
            break;
    }
    glutPostRedisplay();
}


void startfpscamera() {
    px = 0;
    pz = 0;
    py = altura_do_gajo + hf(px + (tw / 2.0), pz + (th / 2.0));

    angulo_alfa = 0;

    lx = px + sin(angulo_alfa);
    ly = py;
    lz = pz + cos(angulo_alfa);
}


int main(int argc, char **argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
    glutInitWindowPosition(100, 100);
    glutInitWindowSize(1920, 1080);
    glutCreateWindow("CG@DI-UM");

    // Required callback registry
    glutDisplayFunc(renderScene);
    glutIdleFunc(renderScene);
    glutReshapeFunc(changeSize);

    // Callback registration for keyboard processing
    glutKeyboardFunc(processKeys);
    glutSpecialFunc(processSpecialKeys);
    glutMouseFunc(processMouseButtons);
    glutMotionFunc(processMouseMotion);

    glewInit();
    ilInit();

    //Carregar Imagem e construir a VBO
    init();
    startfpscamera();
    initVBO(tw, th);

    // OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

    // enter GLUT's main cycle
    glutMainLoop();

    return 0;
}