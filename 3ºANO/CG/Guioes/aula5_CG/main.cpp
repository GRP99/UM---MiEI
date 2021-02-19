#include <stdio.h>
#include <stdlib.h>

#define _USE_MATH_DEFINES

#include <math.h>
#include <GL/glut.h>

float alfa = 0.0f, beta = 0.5f, radius = 100.0f;
float camX, camY, camZ;
float circulo1, circulo2 = 0;
int xOrigem = 0, yOrigem = 0;


void spherical2Cartesian() {
    camX = radius * cos(beta) * sin(alfa);
    camY = radius * sin(beta);
    camZ = radius * cos(beta) * cos(alfa);
}

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

void arvore(float tamanho) {
    glPushMatrix();
    glRotatef(-90, 1, 0, 0);
    glColor3f(0.55, 0.45, 0);
    glutSolidCone(0.05 * tamanho, tamanho, 25, 15);
    glTranslatef(0, 0, 0.25 * tamanho);
    int n = rand() % 2;
    switch (n) {
        case 0:
            glColor3f(0, 1, 0);
            break;
        case 1:
            glColor3f(0.0, 0.75, 0.25);
            break;
        default:
            break;
    }
    glutSolidCone(0.25 * tamanho, 0.75 * tamanho, 25, 15);
    glPopMatrix();
}

void plantaArvores(int numArvores) {
    float posX, posZ;
    srand(5); // start the random number sequence
    while (numArvores > 0) {
        posX = rand() % 99;
        posZ = rand() % 99;
        if (sqrt(posX * posX + posZ * posZ) < 45) {
            if (rand() % 2 == 0)
                posX = rand() % 49 + 50;
            else
                posZ = rand() % 49 + 50;
        }
        if (rand() % 2 == 0)
            posX = -posX;
        if (rand() % 2 == 0)
            posZ = -posZ;
        glPushMatrix();
        glTranslatef(posX, 0, posZ);
        arvore(10);
        glPopMatrix();
        numArvores--;
    }
}

void criaFiguras(int numTeapots) {
    float ang = 360 / numTeapots;
    float angAzul = 360 / (numTeapots / 2);

    // TORUS
    glColor3f(0.75, 0, 0.25);
    glutSolidTorus(1, 2, 15, 30);

    // TEAPOT AZUL
    glPushMatrix();
    glRotatef(circulo1, 0, 1, 0);
    glColor3f(0, 0, 1);
    for (int i = 0; i < numTeapots / 2; i++) {
        glPushMatrix();
        glTranslatef(10, 1, 0);
        glutSolidTeapot(1);
        glPopMatrix();
        glRotatef(angAzul, 0, 1, 0);
    }
    glPopMatrix();

    // TEAPOT VERMELHO
    glPushMatrix();
    glRotatef(circulo2, 0, 1, 0);
    glColor3f(1, 0, 0);
    for (int i = 0; i < numTeapots; i++) {
        glPushMatrix();
        glTranslatef(20, 1, 0);
        glutSolidTeapot(1);
        glPopMatrix();
        glRotatef(ang, 0, 1, 0);
    }
    glPopMatrix();
}

void renderScene(void) {

    // clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // set the camera
    glLoadIdentity();
    gluLookAt(camX, camY, camZ,
              0.0f, 0.0f, 0.0f,
              0.0f, 1.0f, 0.0f);

    //CAMPO
    glColor3f(0.2f, 0.8f, 0.2f);
    glBegin(GL_TRIANGLES);
    glVertex3f(100.0f, 0, -100.0f);
    glVertex3f(-100.0f, 0, -100.0f);
    glVertex3f(-100.0f, 0, 100.0f);
    glVertex3f(100.0f, 0, -100.0f);
    glVertex3f(-100.0f, 0, 100.0f);
    glVertex3f(100.0f, 0, 100.0f);
    glEnd();

    plantaArvores(250);
    criaFiguras(16);

    circulo1 += 0.2;
    circulo2 += 0.2;

    // End of frame
    glutSwapBuffers();
}


void processSpecialKeys(int key, int xx, int yy) {
    switch (key) {
        case GLUT_KEY_RIGHT:
            alfa -= 0.1;
            break;
        case GLUT_KEY_LEFT:
            alfa += 0.1;
            break;
        case GLUT_KEY_UP:
            beta += 0.1f;
            if (beta > 1.5f)
                beta = 1.5f;
            break;
        case GLUT_KEY_DOWN:
            beta -= 0.1f;
            if (beta < -1.5f)
                beta = -1.5f;
            break;
        case GLUT_KEY_PAGE_DOWN:
            radius -= 1.0f;
            if (radius < 1.0f)
                radius = 1.0f;
            break;
        case GLUT_KEY_PAGE_UP:
            radius += 1.0f;
            break;
    }
    spherical2Cartesian();
}

void ratoMovimento(int x, int y) {
    if (xOrigem >= x && xOrigem - x > 10) {
        alfa -= 0.05f;
        xOrigem = x;
    } else if (xOrigem < x && x - xOrigem > 10) {
        alfa += 0.05f;
        xOrigem = x;
    }
    if (yOrigem >= y && yOrigem - y > 10) {
        beta += 0.05f;
        if (beta > 1.5f)
            beta = 1.5f;
        yOrigem = y;
    } else if (yOrigem < y && y - yOrigem > 10) {
        beta -= 0.05f;
        if (beta < -1.5f)
            beta = -1.5f;
        yOrigem = y;
    }
    spherical2Cartesian();
}

void printInfo() {
    printf("Vendor: %s\n", glGetString(GL_VENDOR));
    printf("Renderer: %s\n", glGetString(GL_RENDERER));
    printf("Version: %s\n", glGetString(GL_VERSION));
    printf("\nUse Arrows to move the camera up/down and left/right\n");
    printf("Home and End control the distance from the camera to the origin");
}


int main(int argc, char **argv) {
    // init GLUT and the window
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
    glutInitWindowPosition(100, 100);
    glutInitWindowSize(800, 800);
    glutCreateWindow("CG@DI-UM");

    // Required callback registry
    glutDisplayFunc(renderScene);
    glutReshapeFunc(changeSize);
    glutIdleFunc(renderScene);

    // Callback registration for keyboard processing
    glutIgnoreKeyRepeat(0);
    glutSpecialFunc(processSpecialKeys);

    glutMotionFunc(ratoMovimento);

    // OpenGL settings
    glEnable(GL_DEPTH_TEST);
    // glEnable(GL_CULL_FACE);

    spherical2Cartesian();

    printInfo();

    // enter GLUT's main cycle
    glutMainLoop();

    return 1;
}