#include <stdlib.h>

#define _USE_MATH_DEFINES

#include <math.h>
#include <GL/glut.h>


#define POINT_COUNT 5

float camX = 0, camY, camZ = 5;
int startX, startY, tracking = 0;
int alpha = 0, beta = 0, r = 5;

// Points that make up the loop for catmull-rom interpolation
float p[POINT_COUNT][3] = {{-1, -1, 0},
                           {-1, 1,  0},
                           {1,  1,  0},
                           {0,  0,  0},
                           {1,  -1, 0}};
float antYY[3] = {0, 1, 0};

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


// given global t, returns the point in the curve
void getGlobalCatmullRomPoint(float gt, float *pos, float *deriv) {
    float t = gt * POINT_COUNT; // this is the real global t
    int index = floor(t);  // which segment
    t = t - index; // where within  the segment

    // indices store the points
    int indices[4];
    indices[0] = (index + POINT_COUNT - 1) % POINT_COUNT;
    indices[1] = (indices[0] + 1) % POINT_COUNT;
    indices[2] = (indices[1] + 1) % POINT_COUNT;
    indices[3] = (indices[2] + 1) % POINT_COUNT;

    getCatmullRomPoint(t, p[indices[0]], p[indices[1]], p[indices[2]], p[indices[3]], pos, deriv);
}


void changeSize(int w, int h) {
    // Prevent a divide by zero, when window is too short
    // (you cant make a window with zero width).
    if (h == 0)
        h = 1;

    // compute window's aspect ratio
    float ratio = w * 1.0 / h;

    // Reset the coordinate system before modifying
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();

    // Set the viewport to be the entire window
    glViewport(0, 0, w, h);

    // Set the correct perspective
    gluPerspective(45, ratio, 1, 1000);

    // return to the model view matrix mode
    glMatrixMode(GL_MODELVIEW);
}


void renderCatmullRomCurve() {
    float gt;
    float pos[4];
    float deriv[4];

    glBegin(GL_LINE_LOOP);
    for (gt = 0; gt < 1; gt += 0.01) {
        getGlobalCatmullRomPoint(gt, pos, deriv);
        glVertex3fv(pos);
    }
    glEnd();
}

void eixos() {
    glBegin(GL_LINES);
// X axis in red
    glColor3f(1.0f, 0.0f, 0.0f);
    glVertex3f(-0.3f, 0.0f, 0.0f);
    glVertex3f(0.3f, 0.0f, 0.0f);
// Y Axis in Green
    glColor3f(0.0f, 1.0f, 0.0f);
    glVertex3f(0.0f, -0.3f, 0.0f);
    glVertex3f(0.0f, 0.3f, 0.0f);
// Z Axis in Blue
    glColor3f(0.0f, 0.0f, 1.0f);
    glVertex3f(0.0f, 0.0f, -0.3f);
    glVertex3f(0.0f, 0.0f, 0.3f);
    glEnd();
}

void renderScene(void) {
    static float t = 0;
    float pos[3];
    float deriv[3];

    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glLoadIdentity();
    gluLookAt(camX, camY, camZ,
              0.0, 0.0, 0.0,
              0.0f, 1.0f, 0.0f);

    renderCatmullRomCurve();

    // apply transformations here
    glPushMatrix();
    getGlobalCatmullRomPoint(t, pos, deriv);
    glTranslatef(pos[0], pos[1], pos[2]);

    float xi[4];
    xi[0] = deriv[0];
    xi[1] = deriv[1];
    xi[2] = deriv[2];
    normalize(xi);

    float zi[4];
    cross(xi, antYY, zi);
    normalize(zi);

    float yi[4];
    cross(zi, xi, yi);
    normalize(yi);
    antYY[0] = yi[0];
    antYY[1] = yi[1];
    antYY[2] = yi[2];

    float m[16];
    buildRotMatrix(xi, yi, zi, m);
    glMultMatrixf(m);

    eixos();
    glColor3f(1.0f, 1.0f, 1.0f);
    glutWireTeapot(0.1);
    glPopMatrix();

    glutSwapBuffers();
    t += 0.001;
}


void processMouseButtons(int button, int state, int xx, int yy) {
    if (state == GLUT_DOWN) {
        startX = xx;
        startY = yy;
        if (button == GLUT_LEFT_BUTTON)
            tracking = 1;
        else if (button == GLUT_RIGHT_BUTTON)
            tracking = 2;
        else
            tracking = 0;
    } else if (state == GLUT_UP) {
        if (tracking == 1) {
            alpha += (xx - startX);
            beta += (yy - startY);
        } else if (tracking == 2) {
            r -= yy - startY;
            if (r < 3)
                r = 3.0;
        }
        tracking = 0;
    }
}


void processMouseMotion(int xx, int yy) {
    int deltaX, deltaY;
    int alphaAux, betaAux;
    int rAux;

    if (!tracking)
        return;

    deltaX = xx - startX;
    deltaY = yy - startY;

    if (tracking == 1) {

        alphaAux = alpha + deltaX;
        betaAux = beta + deltaY;

        if (betaAux > 85.0)
            betaAux = 85.0;
        else if (betaAux < -85.0)
            betaAux = -85.0;

        rAux = r;
    } else if (tracking == 2) {

        alphaAux = alpha;
        betaAux = beta;
        rAux = r - deltaY;
        if (rAux < 3)
            rAux = 3;
    }
    camX = rAux * sin(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
    camZ = rAux * cos(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
    camY = rAux * sin(betaAux * 3.14 / 180.0);
}


int main(int argc, char **argv) {

// inicialization
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
    glutInitWindowPosition(100, 100);
    glutInitWindowSize(1920, 1080);
    glutCreateWindow("CG@DI-UM");

// callback registration 
    glutDisplayFunc(renderScene);
    glutIdleFunc(renderScene);
    glutReshapeFunc(changeSize);

// mouse callbacks
    glutMouseFunc(processMouseButtons);
    glutMotionFunc(processMouseMotion);

// OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);

// enter GLUT's main cycle 
    glutMainLoop();

    return 1;
}