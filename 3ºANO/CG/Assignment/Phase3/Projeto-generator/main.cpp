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

ponto *guarda_pontos;
int pointer_pontos;

int patches;
struct patch {
    int *indices;
    int pointer_indices;
};

patch *patchss;

ponto ***final; // array de patches com matriz de pontos


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
        fclose(pFile);
    }
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


void iniciapatchesbezier(char *str) {
    guarda_pontos = (ponto *) malloc(sizeof(ponto) * 10);
    pointer_pontos = 0;
    int size_pontos = 10;
    int auxz;

    FILE *pFile;
    pFile = fopen(str, "r");

    if (pFile != NULL) {
        fscanf(pFile, "%d \n", &patches);
        patchss = (patch *) malloc(sizeof(patch) * patches);

        for (int i = 0; i < patches; i++) {
            char line[1024];
            fgets(line, 1024, pFile);

            char aux[10];
            int p_aux = 0;

            int size_indices_p = 10;
            patchss[i].indices = (int *) malloc(sizeof(int) * size_indices_p);
            patchss[i].pointer_indices = 0;

            for (int j = 0; j < 1024 && line[j] != '\0'; j++) {
                if (line[j] == ',' || line[j] == '\n') {
                    aux[p_aux] = '\0';
                    if (patchss[i].pointer_indices >= size_indices_p) {
                        size_indices_p *= 2;
                        patchss[i].indices = (int *) realloc(patchss[i].indices, sizeof(int) * size_indices_p);
                    }
                    patchss[i].indices[patchss[i].pointer_indices] = atoi(aux);
                    patchss[i].pointer_indices++;
                    p_aux = 0;
                } else {
                    aux[p_aux] = line[j];
                    p_aux++;
                }
            }
        }

        int pontos;
        fscanf(pFile, "%d \n", &pontos);
        auxz = pontos;
        pointer_pontos = 0;
        guarda_pontos = (ponto *) malloc(sizeof(ponto) * pontos);

        for (int i = 0; i < pontos; i++) {
            ponto auxiliar;
            fscanf(pFile, "%f, %f, %f \n", &(auxiliar.x), &(auxiliar.y), &(auxiliar.z));
            guarda_pontos[pointer_pontos] = auxiliar;
            pointer_pontos++;
        }
    }
}


void multMatrix(float **m1, int l1, int c1, float **m2, int l2, int c2, float ***result, int *lres, int *cres) {
    if (c1 == l2) {
        float **res = (float **) malloc(sizeof(float *) * l1);

        for (int t = 0; t < l1; t++)
            res[t] = (float *) malloc(sizeof(float) * c2);

        *lres = l1;
        *cres = c2;

        for (int i = 0; i < l1; i++)// linhas da primeira
            for (int j = 0; j < c2; j++) // colunas da segunda
            {
                float resu = 0;
                for (int k = 0; k < c1; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        *result = res;
    }
}


void Bezier(float u, float v, float **p, float ***resu, int *lres, int *cres) { // p  e matriz com os pontos
    float **bezierM = (float **) malloc(sizeof(float *) * 4);
    for (int t = 0; t < 4; t++) bezierM[t] = (float *) malloc(sizeof(float) * 4);
    bezierM[0][0] = -1.0f;
    bezierM[0][1] = 3.0f;
    bezierM[0][2] = -3.0f;
    bezierM[0][3] = 1.0f;


    bezierM[1][0] = 3.0f;
    bezierM[1][1] = -6.0f;
    bezierM[1][2] = 3.0f;
    bezierM[1][3] = 0;

    bezierM[2][0] = -3.0f;
    bezierM[2][1] = 3.0f;
    bezierM[2][2] = 0;
    bezierM[2][3] = 0;

    bezierM[3][0] = 1.0f;
    bezierM[3][1] = 0;
    bezierM[3][2] = 0;
    bezierM[3][3] = 0;


    float **uz = (float **) malloc(sizeof(float *) * 1);
    uz[0] = (float *) malloc(sizeof(float) * 4);
    uz[0][0] = u * u * u;
    uz[0][1] = u * u;
    uz[0][2] = u;
    uz[0][3] = 1;

    float **um;
    int lum;
    int cum;

    float **ump;
    int lump;
    int cump;

    float **umpm;
    int lumpm;
    int cumpm;

    float **vz = (float **) malloc(sizeof(float *) * 4);
    for (int t = 0; t < 4; t++)vz[t] = (float *) malloc(sizeof(float) * 1);
    vz[0][0] = v * v * v;
    vz[1][0] = v * v;
    vz[2][0] = v;
    vz[3][0] = 1;

    multMatrix(uz, 1, 4, bezierM, 4, 4, &um, &lum, &cum);
    multMatrix(um, lum, cum, p, 4, 4, &ump, &lump, &cump);
    multMatrix(ump, lump, cump, bezierM, 4, 4, &umpm, &lumpm, &cumpm);
    multMatrix(umpm, lumpm, cumpm, vz, 4, 1, resu, lres, cres);
}

void todosPontosBezier(int tesselation) {
    float iter = (float) 1 / (tesselation - 1);

    final = (ponto ***) malloc(sizeof(ponto **) * patches);

    for (int pat = 0; pat < patches; pat++) {
        final[pat] = (ponto **) malloc(sizeof(ponto *) * tesselation);

        for (int i = 0; i < tesselation; i++) final[pat][i] = (ponto *) malloc(sizeof(ponto) * tesselation);

        float **x = (float **) malloc(sizeof(float *) * 4);
        int k = 0;

        for (int i = 0; i < 4; i++) {
            x[i] = (float *) malloc(sizeof(float) * 4);
            for (int j = 0; j < 4; j++) {
                x[i][j] = guarda_pontos[patchss[pat].indices[k++]].x;
                int t = 100;
            }
        }

        k = 0;
        float **y = (float **) malloc(sizeof(float *) * 4);
        for (int i = 0; i < 4; i++) {
            y[i] = (float *) malloc(sizeof(float) * 4);
            for (int j = 0; j < 4; j++) {
                y[i][j] = guarda_pontos[patchss[pat].indices[k++]].y;
            }
        }

        k = 0;
        float **z = (float **) malloc(sizeof(float *) * 4);
        for (int i = 0; i < 4; i++) {
            z[i] = (float *) malloc(sizeof(float) * 4);
            for (int j = 0; j < 4; j++) {
                z[i][j] = guarda_pontos[patchss[pat].indices[k++]].z;
            }
        }
        int lx, cx;
        float **aux_x;
        float it = 0;

        for (int i = 0; i < tesselation; i++) {
            float ut = 0;
            for (int j = 0; j < tesselation; j++) {
                float **aux_x;
                float **aux_y;
                float **aux_z;
                int lx, cx;

                Bezier(it, ut, x, &aux_x, &lx, &cx);
                final[pat][i][j].x = aux_x[0][0];

                int ly, cy;
                Bezier(it, ut, y, &aux_y, &ly, &cy);
                final[pat][i][j].y = aux_y[0][0];

                int lz, cz;
                Bezier(it, ut, z, &aux_z, &lz, &cz);
                final[pat][i][j].z = aux_z[0][0];

                ut += iter;
            }
            it += iter;
        }
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
        } else if (strcmp(argv[1], "bezier") == 0) {
            if (argc == 5) {  // ./     bezier  control_points   tesselation(divisions) output
                int tesselation = atoi(argv[3]);

                iniciapatchesbezier(argv[2]);

                char *output = strdup(argv[4]);

                todosPontosBezier(tesselation);

                for (int k = 0; k < patches; k++) {
                    escreveFicheiro(tesselation, tesselation, final[k], output);
                }
            }
        }
    }
    return 1;
}