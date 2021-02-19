#ifdef __APPLE__
#include <GLUT/glut.h>
#else

#include <GL/glut.h>

#endif

#define _USE_MATH_DEFINES

#include <math.h>

float lx = 5.0f, ly = 5.0f, lz = 5.0f; //coordenadas para o lookAt
float xx = 0.0f, yy = 0.0f, zz = 0.0f; // coordenadas para a camara
float angleY = 0.0f, angleX = 0.0f, angleZ = 0.0f; //deslocar pelos eixos


void drawCylinder(float radius, float height, int slices) {
    // put code to draw cylinder in here
    float angle_por_slice = (float) ((2 * M_PI) / slices);
    float aux1, aux2 = 0;

    glBegin(GL_TRIANGLES);
    for (int i = 0; i < slices; i++) {
        aux1 = aux2;
        aux2 += angle_por_slice;

        //Base Superior
        glColor3f(0, 1, 0);
        glVertex3f(0, height, 0);
        glVertex3f(sin(aux1), height, cos(aux1));
        glVertex3f(sin(aux2), height, cos(aux2));

        //Base Inferior
        glVertex3f(0, 0, 0);
        glVertex3f(sin(aux2), 0, cos(aux2));
        glVertex3f(sin(aux1), 0, cos(aux1));

        //Dois triângulos para os lados
        glColor3f(1, 0, 0);
        glVertex3f(sin(aux1), height, cos(aux1));
        glVertex3f(sin(aux1), 0, cos(aux1));
        glVertex3f(sin(aux2), 0, cos(aux2));

        glColor3f(0, 0, 1);
        glVertex3f(sin(aux1), height, cos(aux1));
        glVertex3f(sin(aux2), 0, cos(aux2));
        glVertex3f(sin(aux2), height, cos(aux2));

    }
    glEnd();
}


void drawAxis() {
    glBegin(GL_LINES);
    // Eixo dos X em Vermelho
    glColor3f(1.0f, 0.0f, 0.0f);
    glVertex3f(-100.0f, 0.0f, 0.0f);
    glVertex3f(100.0f, 0.0f, 0.0f);
    // Eixo dos Y em Verde
    glColor3f(0.0f, 1.0f, 0.0f);
    glVertex3f(0.0f, -100.0f, 0.0f);
    glVertex3f(0.0f, 100.0f, 0.0f);
    // Eixo dos Z em Azul
    glColor3f(0.0f, 0.0f, 1.0f);
    glVertex3f(0.0f, 0.0f, -100.0f);
    glVertex3f(0.0f, 0.0f, 100.0f);
    glEnd();
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


void renderScene(void) {
    // clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    // set the camera
    glLoadIdentity();
    gluLookAt(lx, ly, lz,
              0.0, 0.0, 0.0,
              0.0f, 1.0f, 0.0f);

    drawAxis();

    glTranslatef(xx, yy, zz);
    glRotatef(angleX, 1.0, 0.0, 0.0);
    glRotatef(angleY, 0.0, 1.0, 0.0);
    glRotatef(angleZ, 0.0, 0.0, 1.0);


    drawCylinder(1, 2, 30);

    // End of frame
    glutSwapBuffers();
}

void processSpecialKeys(int key, int x, int y) {
    switch (key) {
        case GLUT_KEY_UP:
            yy += 0.5;
            break;
        case GLUT_KEY_DOWN:
            yy -= 0.5;
            break;
        case GLUT_KEY_LEFT:
            xx -= 0.5;
            break;
        case GLUT_KEY_RIGHT:
            xx += 0.5;
            break;
    }
    glutPostRedisplay();
}



void processKeys(unsigned char c, int xx, int yy) {
    // put code to process regular keys in here
    float fraction = 0.1f;
    switch (c) {
        case 'a': //rodar no sentido dos ponteiros do relogio, pelo eixo do Y
            angleX -= 5;
            break;
        case 'd': //rodar no sentido contrario dos ponteiros do relogio, pelo eixo do Y
            angleX += 5;
            break;
        case 'w': //rodar no sentido dos ponteiros do relogio, pelo eixo do X
            angleY += 5;
            break;
        case 's': //rodar no sentido contrario dos ponteiros do relogio, pelo eixo do X
            angleY -= 5;
            break;
        case 'z':
            angleZ -= 5;
            break;
        case 'x':
            angleZ += 5;
            break;
        case '-': //afastar
            gluLookAt(lx += 2, ly += 2, lz += 2, 0.0, 0.0, 0.0, 0.0f, 1.0f, 0.0f);
            break;
        case '+': //aproximar
            gluLookAt(lx -= 2, ly -= 2, lz -= 2, 0.0, 0.0, 0.0, 0.0f, 1.0f, 0.0f);
            break;
        case 'r': //reset a camara
            gluLookAt(lx = 5.0, ly = 5.0, lz = 5.0,
                      0.0, 0.0, 0.0,
                      0.0f, 1.0f, 0.0f);
            glTranslatef(xx = 0, yy = 0, zz = 0);
            glRotatef(angleY = 1, 0, 1, 0);
            glRotatef(angleX = 1, 1, 0, 0);
            glRotatef(angleZ = 1, 0, 0, 1);
            break;
    }
    glutPostRedisplay();
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

// Callback registration for keyboard processing
    glutSpecialFunc(processSpecialKeys);
    glutKeyboardFunc(processKeys);

//  OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

// enter GLUT's main cycle
    glutMainLoop();

    return 1;
}
