#include <iostream>
#include <stdlib.h>
#include <math.h>
#include <IL/il.h>
#include <GL/glew.h>
#include <GL/glut.h>

float camX = 201, camY = 199, camZ = 201;
int startX, startY, tracking = 0;

int alpha = 0, beta = 45, r = 50;

// Initializations to load the Image
unsigned int t, tw, th;
unsigned char *imageData;

//Variables of VBO
int arraySize;
GLuint buffers[1];


void changeSize(int w, int h) {

    // Prevent a divide by zero, when window is too short
    // (you cant make a window with zero width).
    if (h == 0)
        h = 1;

    // compute window's aspect ratio
    float ratio = w * 1.0 / h;

    // Reset the coordinate system before modifying
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();

    // Set the viewport to be the entire window
    glViewport(0, 0, w, h);

    // Set the correct perspective
    gluPerspective(45, ratio, 1, 1000);

    // return to the model view matrix mode
    glMatrixMode(GL_MODELVIEW);
}


void drawTerrain() {
    // colocar aqui o código de desenho do terreno usando VBOs com TRIANGLE_STRIPS
    glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);

    glVertexPointer(3, GL_FLOAT, 0, 0);

    glPushMatrix();

    glTranslatef(-(tw / 2.0), 0, -(th / 2.0));

    for (int i = 0; i < th; i++) {
        glDrawArrays(GL_TRIANGLE_STRIP, (tw) * 2 * i, (tw) * 2);
    }
    glPopMatrix();
}

float h(int c, int l) {
    return imageData[l * tw + c] / 255.0 * 100;
}


void initVBO(float height, float width) {
//Step 1 – Allocate and fill arrays with vertices
    // array for vertices
    int i = 0;
    float *vertexB = NULL;
    float auxheight = height;
    float auxwith;

    height /= th;
    width /= tw;

    // fill arrays with vertex values
    arraySize = (tw * 2 * (th - 1)) * 3;
    vertexB = (float *) malloc(arraySize * sizeof(float));

//Step 2 - Enable Buffers
    glEnableClientState(GL_VERTEX_ARRAY);

    for (int line = 0; line < th - 1; ++line) {
        auxwith = 0;
        for (int column = 0; column < tw; ++column) {
            vertexB[i++] = auxheight;
            vertexB[i++] = h(column, line);
            vertexB[i++] = auxwith;

            vertexB[i++] = auxheight - height;
            vertexB[i++] = h(column, line + 1);
            vertexB[i++] = auxwith;

            auxwith = width + auxwith;
        }
        auxheight -= height;
    }

//Step 3 - Generate Vertex Buffer Objects
    glGenBuffers(1, buffers);
    glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
    glBufferData(GL_ARRAY_BUFFER, arraySize * sizeof(float), vertexB, GL_STATIC_DRAW);
}


void renderScene(void) {

    float pos[4] = {-1.0, 1.0, 1.0, 0.0};

    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glLoadIdentity();
    gluLookAt(camX, camY, camZ,
              0.0, 0.0, 0.0,
              0.0f, 1.0f, 0.0f);

    glColor3f(1, 1, 1);
    drawTerrain();

    // just so that it renders something before the terrain is built
    // to erase when the terrain is ready
    //glColor3f(0, 0, 0.75);
    //glutWireTeapot(5.0);

    // End of frame
    glutSwapBuffers();
}


void processKeys(unsigned char key, int xx, int yy) {

// put code to process regular keys in here
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


void init() {

// 	Load the height map "terreno.jpg"
    ilGenImages(1, &t);
    ilBindImage(t);
    // terreno.jpg is the image containing our height map
    ilLoadImage((ILstring) "terreno.jpg");

// 	Build the vertex arrays
    // convert the image to single channel per pixel
    // with values ranging between 0 and 255
    ilConvertImage(IL_LUMINANCE, IL_UNSIGNED_BYTE);
    // important: check tw and th values
    // both should be equal to 256
    // if not there was an error loading the image
    // most likely the image could not be found
    tw = ilGetInteger(IL_IMAGE_WIDTH);
    th = ilGetInteger(IL_IMAGE_HEIGHT);
    // imageData is a LINEAR array with the pixel values
    imageData = ilGetData();

// 	OpenGL settings
    //glEnable(GL_DEPTH_TEST);
    //glEnable(GL_CULL_FACE);
}


int main(int argc, char **argv) {

// init GLUT and the window
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
    glutMouseFunc(processMouseButtons);
    glutMotionFunc(processMouseMotion);

    glewInit();
    ilInit();

    init();
    initVBO(th, tw);

    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

// enter GLUT's main cycle
    glutMainLoop();

    return 0;
}

