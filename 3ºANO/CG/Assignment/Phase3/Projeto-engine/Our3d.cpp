#define _CRT_SECURE_NO_WARNINGS

#include <cstdio>
#include "Our3d.h"
#include <stdlib.h>

int lee3d(char *str, figura *fig) {
    FILE *pFile;
    pFile = fopen(str, "r");

    int r;

    if (pFile != NULL) {
        int linhas = 0;
        int colunas = 0;

        figura temp;
        int size_fig = 1;
        temp.matrizes = (ponto ***) malloc(sizeof(ponto **) * size_fig);
        temp.di = (dimensao *) malloc(sizeof(dimensao) * size_fig);

        for (r = 0; fread(&linhas, sizeof(int), 1, pFile) > 0; r++) {
            fread(&colunas, sizeof(int), 1, pFile);

            ponto **matriz = (ponto **) malloc(linhas * sizeof(ponto *));
            for (int i = 0; i < linhas; i++) matriz[i] = (ponto *) malloc(colunas * sizeof(ponto));

            for (int i = 0; i < linhas; ++i) {
                for (int j = 0; j < colunas; ++j) {
                    fread(matriz[i] + j, sizeof(ponto), 1, pFile);
                }
            }

            if (r >= size_fig) {
                size_fig *= 2;
                temp.matrizes = (ponto ***) realloc(temp.matrizes, sizeof(ponto **) * size_fig);
                temp.di = (dimensao *) realloc(temp.di, sizeof(dimensao) * size_fig);
            }

            dimensao d;
            d.linhas = linhas;
            d.colunas = colunas;
            temp.matrizes[r] = matriz;
            temp.di[r] = d;
        }
        temp.numero_matrizes = r;
        *(fig) = temp;
    }
    return r;
}