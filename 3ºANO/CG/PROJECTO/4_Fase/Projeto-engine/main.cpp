#define _CRT_SECURE_NO_WARNINGS
#define _USE_MATH_DEFINES

#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <math.h>
#include <vector>
#include <cstring>
#include <GL/glew.h>
#include <GL/glut.h>
#include "CatmullRom.h"
#include <IL/il.h>
#include "OurXml.h"
#include "Our3d.h"

#define _PI_ 3.14159

int ficheiros = 0;

grupo *grupos;
int numero_grupos = 0;

int red = 0;
int green = 0;
int blue = 250;

float mat_dif[] = {0.8, 0.2, 0.2, 1.0};
float mat_amb[] = {0.2, 0.05, 0.05, 1.0};

float mat_emi[] = {0.0, 0.0, 0.0, 1.0};
float mat_spe[] = {1.0, 1.0, 1.0, 1.0};
float mat_shi = 0;

float time2;

int numero_luzes;
luz *luzes;

float alfa = 0, beta = 0, radius = 100.0f;
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


int textcorrespond(int num_fig, grupo g) {
    int i = 0;
    for (int j = 0; j < g.numero_ficheiros; j++) {
        for (int k = 0; k < g.figs[j].numero_matrizes; k++) {
            if (num_fig == 0) return i;
            num_fig--;
        }
        i++;
    }
}


void drawVBO(grupo gr) {
    for (int i = 0; i < gr.pointer_vbo; i++) {
        glBindTexture(GL_TEXTURE_2D, 0);
        int text = textcorrespond(i, gr);

        if (gr.path_texturas[text] != NULL) {
            glColor3f(1, 1, 1);
            glBindTexture(GL_TEXTURE_2D, gr.texturas[text]);
            glBindBuffer(GL_ARRAY_BUFFER, gr.texture[text]);
            glTexCoordPointer(2, GL_FLOAT, 0, 0);
        }

        glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_dif);
        glMaterialfv(GL_FRONT, GL_AMBIENT, mat_amb);
        glMaterialfv(GL_FRONT, GL_EMISSION, mat_emi);
        glMaterialfv(GL_FRONT, GL_SPECULAR, mat_spe);
        glMaterialf(GL_FRONT, GL_SHININESS, mat_shi);

        if (gr.mats[text].dif_spec_emi_amb[0] == true) {
            glMaterialfv(GL_FRONT, GL_DIFFUSE, gr.mats[text].dif);
        }
        if (gr.mats[text].dif_spec_emi_amb[1] == true) {
            glMaterialfv(GL_FRONT, GL_SPECULAR, gr.mats[text].spec);
        }
        if (gr.mats[text].dif_spec_emi_amb[2] == true) {
            glMaterialfv(GL_FRONT, GL_EMISSION, gr.mats[text].emi);
        }
        if (gr.mats[text].dif_spec_emi_amb[3] == true) {
            glMaterialfv(GL_FRONT, GL_AMBIENT, gr.mats[text].amb);
        }

        glBindBuffer(GL_ARRAY_BUFFER, gr.normais[i]);
        glNormalPointer(GL_FLOAT, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, gr.vertices[i]);
        glVertexPointer(3, GL_FLOAT, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, gr.indices[i]);
        glDrawElements(GL_TRIANGLES, gr.numero_indices[i], GL_UNSIGNED_INT, 0);
    }
}

void drawLuzes() {
    for (int i = 0; i < numero_luzes; i++) {
        switch (luzes[i].tipo) {
            case Point:
                glLightfv(GL_LIGHT0 + i, GL_POSITION, luzes[i].pos);
                glLightf(GL_LIGHT0 + i, GL_QUADRATIC_ATTENUATION, luzes[i].atenuacao);
                break;

            case Directional:
                glLightfv(GL_LIGHT0 + i, GL_POSITION, luzes[i].pos);
                break;

            case Spot:
                glLightfv(GL_LIGHT0 + i, GL_POSITION, luzes[i].pos);
                glLightfv(GL_LIGHT0 + i, GL_SPOT_DIRECTION, luzes[i].direcao);
                glLightf(GL_LIGHT0 + i, GL_SPOT_CUTOFF, luzes[i].cutoff);
                glLightf(GL_LIGHT0 + i, GL_SPOT_EXPONENT, luzes[i].exponent);
                glLightf(GL_LIGHT0 + i, GL_QUADRATIC_ATTENUATION, luzes[i].atenuacao);
                break;

            default:
                break;
        }
    }
}


void computeNormal(ponto **p, int linha, int coluna, int i, int j, float *res) {
    float p3[3];
    if (i == 0) {
        p3[0] = p[i][j].x;
        p3[1] = p[i][j].y;
        p3[2] = p[i][j].z;
    } else {
        p3[0] = p[i - 1][j].x;
        p3[1] = p[i - 1][j].y;
        p3[2] = p[i - 1][j].z;

    }

    float p1[3];
    if (j == 0) {
        p1[0] = p[i][j].x;
        p1[1] = p[i][j].y;
        p1[2] = p[i][j].z;
    } else {

        p1[0] = p[i][j - 1].x;
        p1[1] = p[i][j - 1].y;
        p1[2] = p[i][j - 1].z;
    }

    float p4[3];
    if (i == linha - 1) {
        p4[0] = p[i][j].x;
        p4[1] = p[i][j].y;
        p4[2] = p[i][j].z;
    } else {
        p4[0] = p[i + 1][j].x;
        p4[1] = p[i + 1][j].y;
        p4[2] = p[i + 1][j].z;
    }

    float p2[3];
    if (j == coluna - 1) {
        p2[0] = p[i][j].x;
        p2[1] = p[i][j].y;
        p2[2] = p[i][j].z;
    } else {
        p2[0] = p[i][j + 1].x;
        p2[1] = p[i][j + 1].y;
        p2[2] = p[i][j + 1].z;
    }

    float v1[3] = {p2[0] - p1[0], p2[1] - p1[1], p2[2] - p1[2]};
    float v2[3] = {p4[0] - p3[0], p4[1] - p3[1], p4[2] - p3[2]};
    cross(v2, v1, res);
    normalize(res);
}

void toVBO(int linhas, int colunas, ponto **matriz, grupo *g) {
    if (g->pointer_vbo >= g->size_vbo) {
        g->size_vbo *= 2;
        g->normais = (GLuint *) realloc(g->normais, sizeof(GLuint) * g->size_vbo);
        g->texture = (GLuint *) realloc(g->texture, sizeof(GLuint) * g->size_vbo);
        g->texturas = (unsigned int *) realloc(g->texturas, sizeof(GLuint) * g->size_vbo);
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

    float *n = (float *) malloc(sizeof(float) * linhas * colunas * 3);
    index = 0;
    for (int i = 0; i < linhas; i++) {
        for (int j = 0; j < colunas; j++) {
            computeNormal(matriz, linhas, colunas, i, j, n + index);
            index += 3;
        }
    }

    float *t = (float *) malloc(sizeof(float) * linhas * colunas * 2);
    index = 0;
    for (int i = 0; i < linhas; i++) {
        for (int j = 0; j < colunas; j++) {
            t[index++] = 0 + j * ((float) 1 / (colunas - 1));
            t[index++] = 1 - i * ((float) 1 / (linhas - 1));
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

    glGenBuffers(1, &(g->normais[g->pointer_vbo]));
    glBindBuffer(GL_ARRAY_BUFFER, (g->normais[g->pointer_vbo]));
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * linhas * colunas * 3, n, GL_STATIC_DRAW);

    glGenBuffers(1, &(g->texture[g->pointer_vbo]));
    glBindBuffer(GL_ARRAY_BUFFER, (g->texture[g->pointer_vbo]));
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * linhas * colunas * 2, t, GL_STATIC_DRAW);

    g->numero_indices[g->pointer_vbo] = index_indices;
    g->pointer_vbo++;
}


int loadTexture(std::string s) {
    unsigned int t, tw, th;
    unsigned char *texData;
    unsigned int texID;

    ilInit();
    ilEnable(IL_ORIGIN_SET);
    ilOriginFunc(IL_ORIGIN_LOWER_LEFT);
    ilGenImages(1, &t);
    ilBindImage(t);
    ilLoadImage((ILstring) s.c_str());
    tw = ilGetInteger(IL_IMAGE_WIDTH);
    th = ilGetInteger(IL_IMAGE_HEIGHT);
    ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);
    texData = ilGetData();

    glGenTextures(1, &texID);

    glBindTexture(GL_TEXTURE_2D, texID);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, tw, th, 0, GL_RGBA, GL_UNSIGNED_BYTE, texData);
    glGenerateMipmap(GL_TEXTURE_2D);

    glBindTexture(GL_TEXTURE_2D, 0);

    return texID;
}


void leeTexturaGrupo(grupo *g) {
    for (int i = 0; i < g->numero_ficheiros; i++) {
        if (strcmp(g->path_texturas[i], "") != 0) {
            g->texturas[i] = loadTexture(g->path_texturas[i]);
        }
    }

    if (g->inside != NULL) {
        for (int i = 0; i < g->filhos; i++) {
            leeTexturaGrupo(&(g->inside[i]));
        }
    }
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
            } else if (g.ops[o].id_operacao == Escala) {
                glScalef(g.ops[o].x, g.ops[o].y, g.ops[o].z);
            }
        }
    }
    if (g.cor != NULL) {
        red = g.cor->r;
        green = g.cor->g;
        blue = g.cor->b;
    }
    glColor3f(red, green, blue);

    drawVBO(g);

    if (g.inside != NULL) {
        for (int i = 0; i < g.filhos; i++) {
            desenhaListaGrupo(g.inside[i]);
        }
    }
    glPopMatrix();
}


void desenhaEixos() {
    glBegin(GL_LINES);
    // Eixo dos X em Vermelho
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
    gluPerspective(45.0f, ratio, 1.0f, 2000.0f);
    // return to the model view matrix mode
    glMatrixMode(GL_MODELVIEW);
}


void renderScene(void) {
    // clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    // set the camera
    glLoadIdentity();
    gluLookAt(camX, camY, camZ, 0.0, 0.0, 0.0, 0.0f, 1.0f, 0.0f);

    drawLuzes();

    //desenhaEixos();

    glPolygonMode(GL_FRONT_AND_BACK, type);

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
            alfa = 0;
            beta = 0;
            spherical2Cartesian();
            break;
        case 'g':
            alfa -= 0.1;
            spherical2Cartesian();
            break;
        case 'h':
            alfa += 0.1;
            spherical2Cartesian();
            break;
        case 'n' :
            beta += 0.1f;
            if (beta > 1.5f)
                beta = 1.5f;
            spherical2Cartesian();
            break;
        case 'b':
            beta -= 0.1f;
            if (beta < -1.5f)
                beta = -1.5f;
            spherical2Cartesian();
            break;
        case 't' :
            radius -= 0.1f;
            if (radius < 0.1f)
                radius = 0.1f;
            spherical2Cartesian();
            break;
        case 'y':
            radius += 0.1f;
            spherical2Cartesian();
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
    g->normais = (GLuint *) malloc(sizeof(GLuint) * g->size_vbo);
    g->texture = (GLuint *) malloc(sizeof(GLuint) * g->size_vbo);
    g->texturas = (unsigned int *) malloc(sizeof(GLuint) * g->size_vbo);
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

void prepareLight() {
    GLfloat dark[4] = {0.2, 0.2, 0.2, 1.0};
    GLfloat white[4] = {1.0, 1.0, 1.0, 1.0};

    if (numero_luzes > 0);
    glEnable(GL_LIGHTING);

    for (int i = 0; i < numero_luzes; i++) {
        glEnable(GL_LIGHT0 + i);
        glLightfv(GL_LIGHT0 + i, GL_AMBIENT, dark);
        glLightfv(GL_LIGHT0 + i, GL_DIFFUSE, white);
        glLightfv(GL_LIGHT0 + i, GL_SPECULAR, white);
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
    glEnable(GL_TEXTURE_2D);

    spherical2Cartesian();

// Preencher as estruturas
    numero_grupos = abrir_xml_file(argv[1], &grupos);
    numero_luzes = exportLight(&(luzes));

    luz *t = luzes;
    grupo g = *grupos;

    for (int i = 0; i < numero_grupos; i++) {
        preencheListaGrupo(grupos[i]);
        preencheVBOGrupo(grupos + i);
        leeTexturaGrupo(grupos + i);
    }

    prepareLight();

    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_NORMAL_ARRAY);
    glEnableClientState(GL_TEXTURE_COORD_ARRAY);

// enter GLUT's main cycle
    glutMainLoop();

    return 1;
}