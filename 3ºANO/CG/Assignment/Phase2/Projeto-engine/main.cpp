#ifdef __APPLE__
#else

#include <GL/glut.h>
#include <cstdio>

#endif

#include "OurXml.h"
#include "Our3d.h"

int ficheiros = 0;

grupo *grupos;
int numero_grupos = 0;

int red = 0;
int green = 0;
int blue = 250;

bool rodaAutomatico = false;

float time1;
float angulo = 0;

float lx = 600.0f, ly = 600.0f, lz = 600.0f; //coordenadas para o lookAt
float xx = 0.0f, yy = 0.0f, zz = 0.0f; // coordenadas para a camara
float angleY = 0.0f, angleX = 0.0f, angleZ = 0.0f; //deslocar pelos eixos
GLenum type = GL_FILL;

figura **figuras;


void desenha(int linhas, int colunas, ponto **matriz) {
    for (int i = 0; i < linhas - 1; i++) {
        for (int j = 0; j < colunas - 1; j++) {
            glBegin(GL_TRIANGLES);
            glColor3f(red, green, blue);
            glVertex3f(matriz[i + 1][j + 1].x, matriz[i + 1][j + 1].y, matriz[i + 1][j + 1].z);
            glVertex3f(matriz[i][j].x, matriz[i][j].y, matriz[i][j].z);
            glVertex3f(matriz[i + 1][j].x, matriz[i + 1][j].y, matriz[i + 1][j].z);
            glVertex3f(matriz[i + 1][j + 1].x, matriz[i + 1][j + 1].y, matriz[i + 1][j + 1].z);
            glVertex3f(matriz[i][j + 1].x, matriz[i][j + 1].y, matriz[i][j + 1].z);
            glVertex3f(matriz[i][j].x, matriz[i][j].y, matriz[i][j].z);
            glEnd();
        }
    }
}


void desenhaListaGrupo(grupo g) {
    glPushMatrix();

    for (int o = 0; o < g.numero_op; o++) {
        if (g.ops[o].id_operacao == Translacao) { glTranslatef(g.ops[o].x, g.ops[o].y, g.ops[o].z); }
        else if (g.ops[o].id_operacao == Rotacao) { glRotatef(g.ops[o].a, g.ops[o].x, g.ops[o].y, g.ops[o].z); }
        else if (g.ops[o].id_operacao == Escala) { glScalef(g.ops[o].x, g.ops[o].y, g.ops[o].z); }
    }

    if (g.cor != NULL) {
        red = g.cor->r;
        green = g.cor->g;
        blue = g.cor->b;
    }

    for (int j = 0; j < g.numero_ficheiros; j++) {
        for (int i = 0; i < g.figs[j].numero_matrizes; i++) {
            desenha(g.figs[j].di[i].linhas, g.figs[j].di[i].colunas, *(g.figs[j].matrizes + i));
        }
    }

    if (g.inside != NULL)
        for (int i = 0; i < g.filhos; i++)
            desenhaListaGrupo(g.inside[i]);

    glPopMatrix();
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

    //desenhaEixos();

    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);// type);//
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

    for (int i = 0; i < numero_grupos; i++) {
        red = 0;
        green = 0;
        blue = 250;
        desenhaListaGrupo(grupos[i]);
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


void preencheListaGrupo(grupo g) {
    for (int j = 0; j < g.numero_ficheiros; j++)
        lee3d(g.ficheiros[j], g.figs);

    if (g.inside != NULL)
        for (int i = 0; i < g.filhos; i++)
            preencheListaGrupo(g.inside[i]);
}

int main(int argc, char **argv) {
    if (argc > 1) {
        numero_grupos = abrir_xml_file(argv[1], &grupos);

        for (int i = 0; i < numero_grupos; i++)
            preencheListaGrupo(grupos[i]);

        // put GLUT init here
        glutInit(&argc, argv);
        time1 = glutGet(GLUT_ELAPSED_TIME);
        glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
        glutInitWindowPosition(100, 100);
        glutInitWindowSize(1920, 1080);
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