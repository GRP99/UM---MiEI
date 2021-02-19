#ifndef AULA1_OUR3D_H
#define AULA1_OUR3D_H

#define Translacao 0
#define Rotacao 1
#define Escala 2

#define True 1
#define False 0

#define Point 1
#define Directional 2
#define Spot 3

#include <GL/glew.h>


struct ponto {
    float x; // dir
    float y; // up
    float z; // to us
};


struct dimensao {
    int linhas;
    int colunas;
};

struct operacao {
    int id_operacao;
    int estatico;

    float time;
    float tempoAnterior;
    float tempoAcumulador;
    float numero_pontos;
    float **pontos;
    float antYY[3];

    float a;
    float x;
    float y;
    float z;
};


struct figura {
    int numero_matrizes;
    ponto ***matrizes;
    dimensao *di;
};


struct color {
    int r;
    int g;
    int b;
};


struct luz {
    int tipo;

    float pos[4];

    float atenuacao;

    float direcao[3];
    float exponent;
    float cutoff;
};

struct material_cor {
    bool dif_spec_emi_amb[4];
    float dif[4];
    float spec[4];
    float emi[4];
    float amb[4];
};


struct grupo {
    GLuint *vertices, *indices, *normais, *texture;

    int *numero_indices;
    int size_vbo;
    int pointer_vbo;

    int numero_op;
    operacao *ops;

    figura *figs;

    int numero_ficheiros;
    char **ficheiros;

    char **path_texturas;
    unsigned int *texturas;

    material_cor *mats;

    color *cor;

    grupo *inside;
    int filhos;
    int size_filhos;
};


int lee3d(char *, figura *);

#endif