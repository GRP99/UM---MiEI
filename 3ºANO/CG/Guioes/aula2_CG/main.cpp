#ifdef __APPLE__
#include <GLUT/glut.h>
#else

#include <GL/glut.h>

#endif

#include <math.h>

float alturaCamara = 5, altura = 2, translateX = 0, rotate = 0;

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

void piramide(float lado, float alt) {
    glBegin(GL_TRIANGLES);
    //LADOS
    glColor3f(0, 0, 1);
    glVertex3f(0, alt, 0);
    glVertex3f(-lado, 0, lado);
    glVertex3f(lado, 0, lado);

    glColor3f(0, 1, 0);
    glVertex3f(0, alt, 0);
    glVertex3f(lado, 0, lado);
    glVertex3f(lado, 0, -lado);

    glColor3f(0, 0, 1);
    glVertex3f(0, alt, 0);
    glVertex3f(lado, 0, -lado);
    glVertex3f(-lado, 0, -lado);

    glColor3f(1, 0, 0);
    glVertex3f(0, alt, 0);
    glVertex3f(-lado, 0, -lado);
    glVertex3f(-lado, 0, lado);
    //BASE
    glColor3f(1, 1, 1);
    glVertex3f(lado, 0, -lado);
    glVertex3f(-lado, 0, lado);
    glVertex3f(-lado, 0, -lado);

    glVertex3f(lado, 0, -lado);
    glVertex3f(lado, 0, lado);
    glVertex3f(-lado, 0, lado);
    glEnd();
}

void eixos() {
    glBegin(GL_LINES);
// X axis in red
    glColor3f(1.0f, 0.0f, 0.0f);
    glVertex3f(-100.0f, 0.0f, 0.0f);
    glVertex3f(100.0f, 0.0f, 0.0f);
// Y Axis in Green
    glColor3f(0.0f, 1.0f, 0.0f);
    glVertex3f(0.0f, -100.0f, 0.0f);
    glVertex3f(0.0f, 100.0f, 0.0f);
// Z Axis in Blue
    glColor3f(0.0f, 0.0f, 1.0f);
    glVertex3f(0.0f, 0.0f, -100.0f);
    glVertex3f(0.0f, 0.0f, 100.0f);
    glEnd();
}


void renderScene(void) {
    // clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // set the camera
    glLoadIdentity();
    gluLookAt(0.0, alturaCamara, 10.0,
              0.0, 0.0, 0.0,
              0.0f, 1.0f, 0.0f);

    // put the geometric transformations here
    glTranslatef(translateX, 0, 0);
    glRotatef(rotate, 0, 1, 0);

    // put drawing instructions here
    eixos();
    piramide(2, altura);


    // End of frame
    glutSwapBuffers();
}


// write function to process keyboard events
void tecladoNormal(unsigned char key, int x, int y) {
    switch (key) {
        case 'a' :
            translateX -= 0.1;
            break;
        case 's' :
            altura -= 0.1;
            break;
        case 'd' :
            translateX += 0.1;
            break;
        case 'w' :
            altura += 0.1;
            break;
    }
    glutPostRedisplay();
}

void tecladoEspecial(int tecla, int x, int y) {
    switch (tecla) {
        case GLUT_KEY_UP:
            alturaCamara++;
            break;
        case GLUT_KEY_DOWN:
            alturaCamara--;
            break;
        case GLUT_KEY_LEFT:
            rotate -= 5;
            break;
        case GLUT_KEY_RIGHT:
            rotate += 5;
            break;
        default:
            break;
    }
    glutPostRedisplay();
}

int main(int argc, char **argv) {

    // init GLUT and the window
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
    glutInitWindowPosition(10, 100);
    glutInitWindowSize(800, 800);
    glutCreateWindow("CG@DI-UM");

    // Required callback registry
    glutDisplayFunc(renderScene);
    glutReshapeFunc(changeSize);

    // put here the registration of the keyboard callbacks
    glutKeyboardFunc(tecladoNormal);
    glutSpecialFunc(tecladoEspecial);

    //  OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    // enter GLUT's main cycle
    glutMainLoop();

    return 1;
}