#include "build_3Dscenario.h"
#include "terrain.h"


float hf(float x, float y) {
    float r = 0;

    if (((float) ((int) floorf(x))) == x && ((float) ((int) floorf(y))) == y) r = h(x, y);
    else {
        int x1 = floorf(x);
        int x2 = x1 + 1;

        int z1 = floorf(y);
        int z2 = z1 + 1;

        float fz = y - z1;

        int h_x1_z = h(x1, z1) * (1 - fz) + h(x1, z2) * fz;
        int h_x2_z = h(x2, z1) * (1 - fz) + h(x2, z2) * fz;

        int fx = x - x1;

        r = h_x1_z * (1 - fx) + h_x2_z * fx;
    }
    return r;
}


void arvore(float tamanho) {
    glPushMatrix();

    glRotatef(-90, 1, 0, 0);
    glColor3f(0.55, 0.45, 0);
    glutSolidCone(0.05 * tamanho, tamanho, 25, 15);
    glTranslatef(0, 0, 0.25 * tamanho);

    int n = rand() % 2;
    switch (n) {
        case 0:
            glColor3f(0, 1, 0);
            break;
        case 1:
            glColor3f(0.0, 0.75, 0.25);
            break;
        default:
            break;
    }

    glutSolidCone(0.25 * tamanho, 0.75 * tamanho, 25, 15);

    glPopMatrix();
}


void plantaArvores(int numArvores) {
    float posX, posZ;
    srand(5); // start the random number sequence

    while (numArvores > 0) {
        posX = (rand() % (tw / 2));
        posZ = (rand() % (th / 2));

        if (rand() % 2 == 0)
            posX = -posX;
        if (rand() % 2 == 0)
            posZ = -posZ;

        if (posX >= 0 && posX < 25 && posX + 25 < (tw / 2)) posX += 25;
        else if (posX <= 0 && posX >= -25 && posX - 25 > -(tw / 2)) posX -= 25;

        if (posZ >= 0 && posZ < 25 && posZ + 25 < (th / 2)) posZ += 25;
        else if (posZ <= 0 && posZ >= -25 && posZ - 25 > -(th / 2)) posZ -= 25;

        glPushMatrix();

        glTranslatef(posX, hf(posX + (tw / 2.0), posZ + (th / 2.0)), posZ);
        arvore(10);

        glPopMatrix();

        numArvores--;
    }
}


void criaFiguras(int numTeapots) {
    float ang = 360 / numTeapots;
    float angAzul = 360 / (numTeapots / 2);

    // TORUS
    glPushMatrix();
    glTranslatef(0, hf(tw / 2, th / 2), 0);
    glColor3f(0.75, 0, 0.25);
    glutSolidTorus(1, 2, 15, 30);
    glPopMatrix();

    // TEAPOT AZUL
    glPushMatrix();

    glRotatef(circulo1, 0, 1, 0);
    glColor3f(0, 0, 1);

    for (int i = 0; i < numTeapots / 2; i++) {
        glPushMatrix();
        glTranslatef(10, 1, 0);
        glutSolidTeapot(1);
        glPopMatrix();
        glRotatef(angAzul, 0, 1, 0);

        glTranslatef(0, hf(tw / 2 + 10 * cos(angAzul * i), th / 2 - 10 * sin(angAzul * i)), 0);
    }
    glPopMatrix();

    // TEAPOT VERMELHO
    glPushMatrix();

    glRotatef(circulo2, 0, 1, 0);
    glColor3f(1, 0, 0);

    for (int i = 0; i < numTeapots; i++) {
        glPushMatrix();
        glTranslatef(20, 1, 0);
        glutSolidTeapot(1);
        glPopMatrix();
        glRotatef(ang, 0, 1, 0);

        glTranslatef(0, hf(tw / 2 + 20 * cos(angAzul * i), th / 2 - 20 * sin(angAzul * i)), 0);
    }
    glPopMatrix();
}