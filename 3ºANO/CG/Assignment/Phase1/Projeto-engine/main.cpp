#ifdef __APPLE__
#else

#include <GL/glut.h>

#endif

#include "OurXml.h"
#include "Our3d.h"


bool rodaAutomatico = false;
int ficheiros;
float time1;
float angulo = 0;

float lx = 50.0f, ly = 50.0f, lz = 50.0f; //coordenadas para o lookAt
float xx = 0.0f, yy = 0.0f, zz = 0.0f; // coordenadas para a camara
float angleY = 0.0f, angleX = 0.0f, angleZ = 0.0f; //deslocar pelos eixos
GLenum type = GL_FILL;

figura **figuras;


void desenha(int linhas, int colunas, ponto **matriz) {
    for (int i = 0; i < linhas - 1; i++) {
        for (int j = 0; j < colunas - 1; j++) {
            glBegin(GL_TRIANGLES);
            glColor3f(0, 0, 250);
            glVertex3f(matriz[i + 1][j + 1].x, matriz[i + 1][j + 1].y, matriz[i + 1][j + 1].z);
            glVertex3f(matriz[i][j].x, matriz[i][j].y, matriz[i][j].z);
            glVertex3f(matriz[i + 1][j].x, matriz[i + 1][j].y, matriz[i + 1][j].z);

            glColor3f(250, 0, 0);
            glVertex3f(matriz[i + 1][j + 1].x, matriz[i + 1][j + 1].y, matriz[i + 1][j + 1].z);
            glVertex3f(matriz[i][j + 1].x, matriz[i][j + 1].y, matriz[i][j + 1].z);
            glVertex3f(matriz[i][j].x, matriz[i][j].y, matriz[i][j].z);
            glEnd();
        }
    }
}


void desenhaEixos() {
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
    if (h == 0) {
        h = 1;
    }
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

    desenhaEixos();

    glPolygonMode(GL_FRONT_AND_BACK, type);
    glTranslatef(xx, yy, zz);
    glRotatef(angleX, 1.0, 0.0, 0.0);
    glRotatef(angleY, 0.0, 1.0, 0.0);
    glRotatef(angleZ, 0.0, 0.0, 1.0);

    if (rodaAutomatico) {
        float time2 = glutGet(GLUT_ELAPSED_TIME);
        if (time2 - time1 >= 1) {
            angulo = angulo + 0.25;
            glRotatef(angulo, 1, 1, 1);
            time1 = time2;
        }
    }

    for (int i = 0; i < ficheiros; i++) {
        for (int j = 0; j < (figuras[0] + i)->numero_matrizes; j++) {
            desenha((figuras[0] + i)->di->linhas, (figuras[0] + i)->di->colunas, (figuras[0] + i)->matrizes[j]);
        }
    }

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
        case 'z': //rodar no sentido contrario dos ponteiros do relogio, pelo eixo do Z
            angleZ -= 5;
            break;
        case 'x': //rodar no sentido dos ponteiros do relogio, pelo eixo do Z
            angleZ += 5;
            break;
        case '-': //afastar
            gluLookAt(lx += 2, ly += 2, lz += 2, 0.0, 0.0, 0.0, 0.0f, 1.0f, 0.0f);
            break;
        case '+': //aproximar
            gluLookAt(lx -= 2, ly -= 2, lz -= 2, 0.0, 0.0, 0.0, 0.0f, 1.0f, 0.0f);
            break;
        case 'p':
            type = GL_POINT;
            break;
        case 'l':
            type = GL_LINE;
            break;
        case 'f':
            type = GL_FILL;
            break;
        case 'r': //reset a camara
            gluLookAt(lx = 50.0, ly = 50.0, lz = 50.0,
                      0.0, 0.0, 0.0,
                      0.0f, 1.0f, 0.0f);
            glTranslatef(xx = 0, yy = 0, zz = 0);
            glRotatef(angleY = 1, 0, 1, 0);
            glRotatef(angleX = 1, 1, 0, 0);
            glRotatef(angleZ = 1, 0, 0, 1);
            type = GL_FILL;
            glutPostRedisplay();
            break;
    }
    glutPostRedisplay();
}


int main(int argc, char **argv) {
    if (argc > 1) {
        char **nomeFicheiros;

        ficheiros = abrir_xml_file(argv[1], &nomeFicheiros);

        figuras = (figura **) malloc(sizeof(figura *));
        figuras[0] = (figura *) malloc(sizeof(figura) * ficheiros);

        for (int i = 0; i < ficheiros; i++) {
            lee3d(nomeFicheiros[i], figuras[0], i);
        }

        // put GLUT init here
        glutInit(&argc, argv);
        time1 = glutGet(GLUT_ELAPSED_TIME);
        glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
        glutInitWindowPosition(100, 100);
        glutInitWindowSize(500, 500);
        glutCreateWindow("Engine@CG");

        // put callback registration here
        glutDisplayFunc(renderScene);
        glutReshapeFunc(changeSize);

        if (rodaAutomatico) {
            glutIdleFunc(renderScene);
        }

        // put here the registration of the keyboard callbacks
        glutSpecialFunc(processSpecialKeys);
        glutKeyboardFunc(processKeys);

        // OpenGL settings
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // enter GLUT's main loop
        glutMainLoop();

        return 1;
    }
}