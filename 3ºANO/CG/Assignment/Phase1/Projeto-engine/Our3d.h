#ifndef AULA1_OUR3D_H
#define AULA1_OUR3D_H


struct ponto {
    float x;
    float y;
    float z;
};

struct dimensao {
    int linhas;
    int colunas;
};

struct figura {
    int numero_matrizes;
    ponto ***matrizes;
    dimensao *di;
};


int lee3d(char *, figura *, int);

#endif