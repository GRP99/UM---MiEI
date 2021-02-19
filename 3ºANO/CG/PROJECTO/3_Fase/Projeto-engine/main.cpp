#define _CRT_SECURE_NO_WARNINGS

#include <stdlib.h>
#include <GL/glew.h>
#include <GL/glut.h>
#include <math.h>
#include "CatmullRom.h"
#include "OurXml.h"
#include "Our3d.h"

int ficheiros = 0;

grupo *grupos;
int numero_grupos = 0;

int red = 0;
int green = 0;
int blue = 250;

float time2;

float alfa = 0.0f, beta = 0.0f, radius = 5.0f;
float camX, camY, camZ;

float lx = 600.0f, ly = 600.0f, lz = 600.0f; //coordenadas para o lookAt
float xx = 0.0f, yy = 0.0f, zz = 0.0f; // coordenadas para a camara
float angleY = 0.0f, angleX = 0.0f, angleZ = 0.0f; //deslocar pelos eixos
GLenum type = GL_FILL;


void spherical2Cartesian() {
    camX = radius * cos(beta) * sin(alfa);
    camY = radius * sin(beta);
    camZ = radius * cos(beta) * cos(alfa);
}


void drawVBO(grupo gr) {
    for (int i = 0; i < gr.pointer_vbo; i++) {
        glBindBuffer(GL_ARRAY_BUFFER, gr.vertices[i]);
        glVertexPointer(3, GL_FLOAT, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, gr.indices[i]);
        glDrawElements(GL_TRIANGLES, gr.numero_indices[i], GL_UNSIGNED_INT, 0);
    }
}


void toVBO(int linhas, int colunas, ponto **matriz, grupo *g) {
    if (g->pointer_vbo >= g->size_vbo) {
        g->size_vbo *= 2;
        g->vertices = (GLuint *) realloc(g->vertices, sizeof(GLuint) * g->size_vbo);
        g->indices = (GLuint *) realloc(g->indices, sizeof(GLuint) * g->size_vbo);
        g->numero_indices = (int *) realloc(g->numero_indices, sizeof(int) * g->size_vbo);
    }

    float *p = (float *) malloc(sizeof(float) * linhas * colunas * 3);
    int index = 0;

    for (int i = 0; i < linhas; i++) {
        for (int j = 0; j < colunas; j++) {
            p[index++] = matriz[i][j].x;
            p[index++] = matriz[i][j].y;
            p[index++] = matriz[i][j].z;

        }
    }

    unsigned int *v = (unsigned int *) malloc(sizeof(unsigned int) * (linhas - 1) * (colunas - 1) * 2 * 3);
    int index_indices = 0;

    for (int i = 0; i < linhas - 1; i++) {
        for (int j = 0; j < colunas - 1; j++) {
            v[index_indices++] = colunas * (i + 1) + j + 1;
            v[index_indices++] = colunas * i + j;
            v[index_indices++] = colunas * (i + 1) + j;

            v[index_indices++] = colunas * (i + 1) + j + 1;
            v[index_indices++] = colunas * i + j + 1;
            v[index_indices++] = colunas * i + j;
        }
    }

    glGenBuffers(1, &(g->vertices[g->pointer_vbo]));
    glBindBuffer(GL_ARRAY_BUFFER, (g->vertices[g->pointer_vbo]));
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * linhas * colunas * 3, p, GL_STATIC_DRAW);

    glGenBuffers(1, &(g->indices[g->pointer_vbo]));
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, (g->indices[g->pointer_vbo]));
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(unsigned int) * (linhas - 1) * (colunas - 1) * 2 * 3, v,
                 GL_STATIC_DRAW);

    g->numero_indices[g->pointer_vbo] = index_indices;
    g->pointer_vbo++;
}


// given global t, returns the point in the curve
void getGlobalCatmullRomPoint(float gt, int numpontos, float **p, float *pos, float *deriv) {
    float t = gt * numpontos; // this is the real global t
    int index = floor(t);  // which segment
    t = t - index; // where within  the segment

    // indices store the points
    int indices[4];
    indices[0] = (index + numpontos - 1) % numpontos;
    indices[1] = (indices[0] + 1) % numpontos;
    indices[2] = (indices[1] + 1) % numpontos;
    indices[3] = (indices[2] + 1) % numpontos;

    getCatmullRomPoint(t, p[indices[0]], p[indices[1]], p[indices[2]], p[indices[3]], pos, deriv);
}


void renderCatmullRomCurve(int numpontos, float **p) {
    float gt;
    float pos[4];
    float deriv[4];

    glBegin(GL_LINE_LOOP);
    for (gt = 0; gt < 1; gt += 0.01) {
        getGlobalCatmullRomPoint(gt, numpontos, p, pos, deriv);
        glVertex3fv(pos);
    }
    glEnd();
}


void desenhaListaGrupo(grupo g) {
    glPushMatrix();

    for (int o = 0; o < g.numero_op; o++) {

        if (g.ops[o].estatico == False) {
            if (g.ops[o].id_operacao == Translacao && g.ops[o].numero_pontos > 0) {
                renderCatmullRomCurve(g.ops[o].numero_pontos, g.ops[o].pontos);
                float delta = time2 - g.ops[o].tempoAnterior;

                if (delta >= 1 / 60) {
                    g.ops[o].tempoAnterior = time2;
                    g.ops[o].tempoAcumulador = (float) delta / g.ops[o].time + g.ops[o].tempoAcumulador;
                    float x = g.ops[o].tempoAcumulador;
                }

                float pos[3], deriv[3];
                getGlobalCatmullRomPoint(g.ops[o].tempoAcumulador, g.ops[o].numero_pontos, g.ops[o].pontos,
                                         pos, deriv);
                glTranslatef(pos[0], pos[1], pos[2]);

                float xi[4];
                xi[0] = deriv[0];
                xi[1] = deriv[1];
                xi[2] = deriv[2];
                normalize(xi);

                float zi[4];
                cross(xi, g.ops[o].antYY, zi);
                normalize(zi);

                float yi[4];
                cross(zi, xi, yi);
                normalize(yi);
                g.ops[o].antYY[0] = yi[0];
                g.ops[o].antYY[1] = yi[1];
                g.ops[o].antYY[2] = yi[2];

                float m[16];
                buildRotMatrix(xi, yi, zi, m);
                glMultMatrixf(m);

            } else if (g.ops[o].id_operacao == Rotacao) {
                float delta = time2 - g.ops[o].tempoAnterior;

                if (delta >= 1 / 60) {
                    g.ops[o].tempoAnterior = time2;
                    g.ops[o].tempoAcumulador = (float) (delta * 360) / g.ops[o].time + g.ops[o].tempoAcumulador;
                    float x = g.ops[o].tempoAcumulador;
                }

                glRotatef(g.ops[o].tempoAcumulador, g.ops[o].x, g.ops[o].y, g.ops[o].z);
            }

        } else if (g.ops[o].estatico == True) {
            if (g.ops[o].id_operacao == Translacao) {
                glTranslatef(g.ops[o].x, g.ops[o].y, g.ops[o].z);
            } else if (g.ops[o].id_operacao == Rotacao) {
                glRotatef(g.ops[o].a, g.ops[o].x, g.ops[o].y, g.ops[o].z);
            } else if (g.ops[o].id_operacao == Escala) { glScalef(g.ops[o].x, g.ops[o].y, g.ops[o].z); }
        }
    }

    if (g.cor != NULL) {
        red = g.cor->r;
        green = g.cor->g;
        blue = g.cor->b;
    }

    glColor3f(red, green, blue);
    drawVBO(g);

    if (g.inside != NULL)
        for (int i = 0; i < g.filhos; i++) {
            desenhaListaGrupo(g.inside[i]);
        }

    glPopMatrix();
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
    gluPerspective(45.0f, ratio, 1.0f, 2000.0f);
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

    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);// type);//
    glTranslatef(xx, yy, zz);
    glRotatef(angleX, 1.0, 0.0, 0.0);
    glRotatef(angleY, 0.0, 1.0, 0.0);
    glRotatef(angleZ, 0.0, 0.0, 1.0);

    time2 = (float) glutGet(GLUT_ELAPSED_TIME) / 1000.0f;

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
    for (int j = 0; j < g.numero_ficheiros; j++) {
        lee3d(g.ficheiros[j], g.figs);
    }
    if (g.inside != NULL) {
        for (int i = 0; i < g.filhos; i++) {
            preencheListaGrupo(g.inside[i]);
        }
    }
}


void preencheVBOGrupo(grupo *g) {
    g->size_vbo = 1;
    g->vertices = (GLuint *) malloc(sizeof(GLuint) * g->size_vbo);
    g->indices = (GLuint *) malloc(sizeof(GLuint) * g->size_vbo);
    g->numero_indices = (int *) malloc(sizeof(int) * g->size_vbo);
    g->pointer_vbo = 0;

    for (int j = 0; j < g->numero_ficheiros; j++) {
        for (int i = 0; i < g->figs[j].numero_matrizes; i++) {
            toVBO(g->figs[j].di[i].linhas, g->figs[j].di[i].colunas, g->figs[j].matrizes[i], g);
        }
    }

    if (g->inside != NULL) {
        for (int i = 0; i < g->filhos; i++) {
            preencheVBOGrupo(&(g->inside[i]));
        }
    }
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
    glutReshapeFunc(changeSize);
    glutIdleFunc(renderScene);

    // Callback registration for keyboard processing
    glutKeyboardFunc(processKeys);
    glutSpecialFunc(processSpecialKeys);

#ifndef __APPLE__
    glewInit();
#endif

    //  OpenGL settings
    glEnableClientState(GL_VERTEX_ARRAY);
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glPolygonMode(GL_FRONT, GL_FILL);

    spherical2Cartesian();

    // Preencher as estruturas
    numero_grupos = abrir_xml_file(argv[1], &grupos);

    for (int i = 0; i < numero_grupos; i++) {
        preencheListaGrupo(grupos[i]);
        preencheVBOGrupo(grupos + i);
    }

    // enter GLUT's main cycle
    glutMainLoop();

    return 1;
}