#include <cstdio>
#include <stdlib.h>
#include "Our3d.h"

int lee3d(char *str, figura *fig, int num_figura) {
    int r;
    FILE *pFile;
    pFile = fopen(str, "r");
    if (pFile != NULL) {
        int linhas = 0;
        int colunas = 0;
        figura temp;
        int size_fig = 1;
        temp.matrizes = (ponto ***) malloc(sizeof(ponto **) * size_fig);
        temp.di = (dimensao *) malloc(sizeof(dimensao) * size_fig);

        for (r = 0; fread(&linhas, sizeof(int), 1, pFile) > 0; r++) {
            fread(&colunas, sizeof(int), 1, pFile);
            printf("Linhas %d :: Colunas %d", linhas, colunas);
            ponto **matriz = (ponto **) malloc(linhas * sizeof(ponto *));
            for (int i = 0; i < linhas; i++) {
                matriz[i] = (ponto *) malloc(colunas * sizeof(ponto));
            }
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
        *(fig + num_figura) = temp;
        return r;
    }
}