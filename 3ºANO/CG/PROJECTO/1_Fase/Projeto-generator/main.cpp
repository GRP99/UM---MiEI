#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#include <stdio.h>
#include <math.h>
#include <cstring>
#endif


struct ponto {
    float x;
    float y;
    float z;
};


void escreveFicheiro(int linhas, int colunas, ponto **matriz, char *ficheiro) {
    FILE *pFile;
    pFile = fopen(ficheiro, "a");
    if (pFile != NULL) {
        fwrite(&linhas, sizeof(int), 1, pFile);
        fwrite(&colunas, sizeof(int), 1, pFile);
        for (int i = 0; i < linhas; ++i) {
            for (int j = 0; j < colunas; ++j) {
                fwrite(matriz[i] + j, sizeof(ponto), 1, pFile);
            }
        }
    }
    fclose(pFile);
}


void plano(float lado, char *ficheiro) {
    ponto p1;
    p1.x = (float) -lado / 2;
    p1.y = (float) 0;
    p1.z = (float) lado / 2;
    ponto p2;
    p2.x = (float) lado / 2;
    p2.y = (float) 0;
    p2.z = (float) lado / 2;
    ponto p3;
    p3.x = (float) lado / 2;
    p3.y = (float) 0;
    p3.z = (float) -lado / 2;
    ponto p4;
    p4.x = (float) -lado / 2;
    p4.y = (float) 0;
    p4.z = (float) -lado / 2;
    ponto **matriz = (ponto **) malloc(2 * sizeof(ponto *));
    for (int i = 0; i < 2; i++) {
        matriz[i] = (ponto *) malloc(2 * sizeof(ponto));
    }
    matriz[0][0] = p4;
    matriz[0][1] = p3;
    matriz[1][0] = p1;
    matriz[1][1] = p2;
    escreveFicheiro(2, 2, matriz, ficheiro);
}


int caixa(float comprimento, float largura, float altura, int divisoes, char *ficheiro) {
    float x, z, y; // limite superior
    float xx, zz, yy;// limite inferior
    float espacoC, espacoL, espacoA;
    float linhaX, linhaZ, linhaY;

    espacoC = (float) comprimento / (divisoes + 1);
    espacoL = (float) largura / (divisoes + 1);
    espacoA = (float) altura / (divisoes + 1);

    x = (float) comprimento / 2;
    xx = -x;
    z = (float) largura / 2;
    zz = -z;
    y = (float) altura / 2;
    yy = -y;

    ponto **matriz = (ponto **) malloc((divisoes + 2) * sizeof(ponto *));
    for (int i = 0; i < (divisoes + 2); i++) {
        matriz[i] = (ponto *) malloc((divisoes + 2) * sizeof(ponto));
    }
    ponto **matriz2 = (ponto **) malloc((divisoes + 2) * sizeof(ponto *));
    for (int i = 0; i < (divisoes + 2); i++) {
        matriz2[i] = (ponto *) malloc((divisoes + 2) * sizeof(ponto));
    }
    ponto p1;

    for (int a = 0; a < divisoes + 2; ++a) {
        linhaY = (espacoA * a) + yy;
        p1.y = linhaY;
        for (int b = 0; b < divisoes + 2; ++b) {
            linhaX = (espacoC * b) + xx;
            p1.x = linhaX;
            p1.z = z;
            matriz[divisoes + 2 - 1 - a][b] = p1;
            p1.z = zz;
            matriz2[divisoes + 2 - 1 - a][divisoes + 2 - 1 - b] = p1;

        }
    }
    escreveFicheiro(divisoes + 2, divisoes + 2, matriz, ficheiro);
    escreveFicheiro(divisoes + 2, divisoes + 2, matriz2, ficheiro);

    for (int c = 0; c < divisoes + 2; c++) {
        linhaY = (espacoA * c) + yy;
        p1.y = linhaY;
        for (int d = 0; d < divisoes + 2; d++) {
            linhaZ = (espacoL * d) + zz;
            p1.z = linhaZ;
            p1.x = x;
            matriz[divisoes + 2 - 1 - c][divisoes + 2 - 1 - d] = p1;
            p1.x = xx;
            matriz2[divisoes + 2 - 1 - c][d] = p1;
        }
    }
    escreveFicheiro(divisoes + 2, divisoes + 2, matriz, ficheiro);
    escreveFicheiro(divisoes + 2, divisoes + 2, matriz2, ficheiro);

    for (int e = 0; e < divisoes + 2; ++e) {
        linhaZ = (espacoL * e) + zz;
        p1.z = linhaZ;
        for (int g = 0; g < divisoes + 2; ++g) {
            linhaX = (espacoC * g) + xx;
            p1.x = linhaX;
            p1.y = y;
            matriz[e][g] = p1;
            p1.y = yy;
            matriz2[divisoes + 2 - 1 - e][g] = p1;
        }
    }
    escreveFicheiro(divisoes + 2, divisoes + 2, matriz, ficheiro);
    escreveFicheiro(divisoes + 2, divisoes + 2, matriz2, ficheiro);
}


void esfera(float raio, int fatias, int camadas, char *ficheiro) {
    if (fatias > 0 && camadas > 0 && raio > 0) {
        ponto **matriz = (ponto **) malloc((camadas + 2) * sizeof(ponto *));
        for (int i = 0; i < camadas + 2; i++) {
            matriz[i] = (ponto *) malloc((fatias + 1) * sizeof(ponto));
        }

        ponto topo, baixo;
        topo.x = 0;
        topo.y = raio;
        topo.z = 0;
        baixo.x = 0;
        baixo.y = -raio;
        baixo.z = 0;
        for (int i = 0; i < fatias + 1; i++) {
            matriz[0][i] = topo;
            matriz[camadas + 1][i] = baixo;
        }

        float altura_por_stack = (float) (raio * 2) / (camadas + 1);
        float angulo_por_slice = (float) (2 * M_PI / fatias);
        float raio_local;
        ponto p1;

        for (int i = 1; i < camadas + 1; i++) {
            float altura_da_stack = raio - altura_por_stack * i;
            p1.y = altura_da_stack;
            float angulo = -angulo_por_slice;
            raio_local = sqrt(raio * raio - altura_da_stack * altura_da_stack);
            int sinal;

            for (int j = 0; j < fatias; j++) {
                angulo += angulo_por_slice;
                p1.x = raio_local * cos(angulo);
                if (angulo > M_PI) sinal = 1;
                else sinal = -1;
                p1.z = sinal * sqrt(raio_local * raio_local - p1.x * +p1.x);
                matriz[i][j] = p1;
            }
        }

        // coloca o ultima posicao da coluna igual a primeira coluna
        for (int i = 0; i < camadas + 1; i++) {
            matriz[i][fatias] = matriz[i][0];
        }
        escreveFicheiro(camadas + 2, fatias + 1, matriz, ficheiro);
    }
}


void cone(float raio, float altura, int fatias, int camadas, char *ficheiro) {
    if (fatias > 0 && camadas > 0 && raio > 0) {
        ponto **matriz = (ponto **) malloc((camadas + 2) * sizeof(ponto *));
        for (int i = 0; i < camadas + 2; i++) {
            matriz[i] = (ponto *) malloc((fatias + 1) * sizeof(ponto));
        }

        ponto topo, bottom;
        topo.x = 0;
        topo.y = altura;
        topo.z = 0;
        bottom.x = 0;
        bottom.y = 0;
        bottom.z = 0;
        for (int i = 0; i < fatias + 1; i++) {
            matriz[0][i] = topo;
            matriz[camadas + 1][i] = bottom;
        }

        float altura_por_stack = (float) (altura) / (camadas);
        float angulo_por_slice = (float) (2 * M_PI / fatias);
        float raio_local;
        ponto p1;

        for (int i = 1; i < camadas + 1; i++) {
            float angulo = -angulo_por_slice;
            float altura_da_stack = altura - altura_por_stack * i;
            int sinal;
            raio_local = raio - (raio * altura_da_stack) / altura;
            p1.y = altura_da_stack;

            for (int j = 0; j < fatias; j++) {
                angulo += angulo_por_slice;
                p1.x = raio_local * cos(angulo);
                if (angulo > M_PI) sinal = 1;
                else sinal = -1;
                p1.z = sinal * sqrt(raio_local * raio_local - p1.x * +p1.x);
                matriz[i][j] = p1;
            }
        }
        for (int i = 0; i < camadas + 1; i++) {
            matriz[i][fatias] = matriz[i][0];
        }
        escreveFicheiro(camadas + 2, fatias + 1, matriz, ficheiro);
    }
}



int main(int argc, char **argv) {
    if (argc > 1) {
        if (strcmp(argv[1], "plane") == 0) {
            if (argc == 4) {
                remove(argv[3]);
                plano(atof(argv[2]), argv[3]);
            } else if (argc == 3) {
                remove(argv[3]);
                plano(10, argv[2]);
            }
        } else if (strcmp(argv[1], "box") == 0) {
            if (argc == 7) {
                remove(argv[6]);
                caixa(atof(argv[2]), atof(argv[3]), atof(argv[4]), atoi(argv[5]), argv[6]);
            }
            if (argc == 6) {
                remove(argv[5]);
                caixa(atof(argv[2]), atof(argv[3]), atof(argv[4]), 0, argv[5]);
            }
        } else if (strcmp(argv[1], "sphere") == 0) {
            if (argc == 6) {
                remove(argv[5]);
                esfera(atof(argv[2]), atoi(argv[3]), atoi(argv[4]), argv[5]);
            }
        } else if (strcmp(argv[1], "cone") == 0) {
            if (argc == 7) {
                remove(argv[6]);
                cone(atof(argv[2]), atof(argv[3]), atoi(argv[4]), atoi(argv[4]), argv[6]);
            }
        }
    }
    return 1;
}