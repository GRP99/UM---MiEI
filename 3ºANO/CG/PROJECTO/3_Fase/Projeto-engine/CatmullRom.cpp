#define _CRT_SECURE_NO_WARNINGS

#include <math.h>


void buildRotMatrix(float *x, float *y, float *z, float *m) {
    m[0] = x[0];
    m[1] = x[1];
    m[2] = x[2];
    m[3] = 0;
    m[4] = y[0];
    m[5] = y[1];
    m[6] = y[2];
    m[7] = 0;
    m[8] = z[0];
    m[9] = z[1];
    m[10] = z[2];
    m[11] = 0;
    m[12] = 0;
    m[13] = 0;
    m[14] = 0;
    m[15] = 1;
}


void cross(float *a, float *b, float *res) {
    res[0] = a[1] * b[2] - a[2] * b[1];
    res[1] = a[2] * b[0] - a[0] * b[2];
    res[2] = a[0] * b[1] - a[1] * b[0];
}


void normalize(float *a) {
    float l = sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
    a[0] = a[0] / l;
    a[1] = a[1] / l;
    a[2] = a[2] / l;
}


float length(float *v) {
    float res = sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    return res;
}


void multMatrixVector(float *m, float *v, float *res) {
    for (int j = 0; j < 4; ++j) {
        res[j] = 0;
        for (int k = 0; k < 4; ++k) {
            res[j] += v[k] * m[j * 4 + k];
        }
    }
}


void getCatmullRomPoint(float t, float *p0, float *p1, float *p2, float *p3, float *pos, float *deriv) {
    // catmull-rom matrix
    float m[4][4] = {{-0.5f, 1.5f,  -1.5f, 0.5f},
                     {1.0f,  -2.5f, 2.0f,  -0.5f},
                     {-0.5f, 0.0f,  0.5f,  0.0f},
                     {0.0f,  1.0f,  0.0f,  0.0f}};

    float matrixT[4] = {t * t * t, t * t, t, 1};
    float matrixTDeriv[4] = {3 * (t * t), 2 * t, 1, 0};

    float matrixPX[4] = {p0[0], p1[0], p2[0], p3[0]};
    float matrixPY[4] = {p0[1], p1[1], p2[1], p3[1]};
    float matrixPZ[4] = {p0[2], p1[2], p2[2], p3[2]};

    // Compute A = M * P
    float matrixAX[4];
    float matrixAY[4];
    float matrixAZ[4];

    multMatrixVector((float *) m, matrixPX, matrixAX);
    multMatrixVector((float *) m, matrixPY, matrixAY);
    multMatrixVector((float *) m, matrixPZ, matrixAZ);

    // Compute pos = T * A
    pos[0] = (matrixT[0] * matrixAX[0]) + (matrixT[1] * matrixAX[1]) + (matrixT[2] * matrixAX[2]) +
             (matrixT[3] * matrixAX[3]);
    pos[1] = (matrixT[0] * matrixAY[0]) + (matrixT[1] * matrixAY[1]) + (matrixT[2] * matrixAY[2]) +
             (matrixT[3] * matrixAY[3]);
    pos[2] = (matrixT[0] * matrixAZ[0]) + (matrixT[1] * matrixAZ[1]) + (matrixT[2] * matrixAZ[2]) +
             (matrixT[3] * matrixAZ[3]);

    // compute deriv = T' * A
    deriv[0] = (matrixTDeriv[0] * matrixAX[0]) + (matrixTDeriv[1] * matrixAX[1]) + (matrixTDeriv[2] * matrixAX[2]) +
               (matrixTDeriv[3] * matrixAX[3]);
    deriv[1] = (matrixTDeriv[0] * matrixAY[0]) + (matrixTDeriv[1] * matrixAY[1]) + (matrixTDeriv[2] * matrixAY[2]) +
               (matrixTDeriv[3] * matrixAY[3]);
    deriv[2] = (matrixTDeriv[0] * matrixAZ[0]) + (matrixTDeriv[1] * matrixAZ[1]) + (matrixTDeriv[2] * matrixAZ[2]) +
               (matrixTDeriv[3] * matrixAZ[3]);
}