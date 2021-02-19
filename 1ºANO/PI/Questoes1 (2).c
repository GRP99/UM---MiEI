#include <stdio.h>
#include <string.h>
#include <ctype.h>

//26
void insere (int v[], int N, int x){
    int i,j;
    for (j=N,i=N-1;v[i]>=x && j!=0;j--,i--){
        v[j]=v[i];
    }
    v[j]=x;
}

//  
void merge (int r[],int a[],int na, int b[], int nb){
    int ia=0, ib=0, ir;
    for (ir=0; ir < na+nb; ir++){
        if (a[ia] < b[ib]) {r[ir] = a[ia]; ia++;}
        else {r[ir] = b[ib]; ib++;}
        if (ia==na){
            for (ir = ir + 1; ib < nb; ir++, ib++){
                r[ir] = b[ib];
            }
        }
        if (ib==nb){
            for (ir = ir + 1; ia < na; ir++, ia++){
                r[ir] = a[ia];
            }
         } 
    }
}

//28
int crescente (int a[], int i, int j){
    int ia, ib, r=1;
    for (ia=i,ib=i+1; ib<=j && r !=0; ia++,ib++){
        if (a[ia]>a[ib]) r = 0;
    }
    return r;
}

//29
int retiraNeg (int v[], int N){
    int i=0,j=0;
    int aux[N];
    while (i < N){
        if (v[i] < 0) {i++;}
        else {aux[j] = v[i]; i++;j++;}
    }
    for (i=0; i < j; i++){v[i]=aux[i];}
    return j;
}

//30
int conta (int valor, int v[], int t){
    int i,c=0;
    for (i=0; i<t; i++){
        if (v[i] == valor) {c++;}
    }
    return c;
}
int menosFreq (int v[], int N){
    int i, n, x=0, f=N+1;
    for (i=0; i<N; i++){
        n = conta (v[i],v,N);
        if (f > n) {f=n; x=v[i];}
    }
    return x;
}

//31
int conta (int valor, int v[], int t){
    int i,c=0;
    for (i=0; i<t; i++){
        if (v[i] == valor) {c++;}
    }
    return c;
}
int maisFreq (int v[], int N){
    int i, n, x=0, f=0;
    for (i=0; i<N; i++){
        n = conta (v[i],v,N);
        if (f < n) {f=n; x=v[i];}
    }
    return x;
}

//32
int sequencia (int pos, int v[], int t){
    int i=pos,j=pos+1,c=1;
    while (v[i]<=v[j]){i++;j++;c++;}
    return c;
}
int maxCresc (int v[], int N){
    int i,n,x=0;
    for (i = 0; i < N; ++i){
        n = sequencia(i,v,N);
        if (n > x) x=n;
    }
    return x;
}

//33
int pertence (int x, int v[], int t){
       int i,r=0;
       for (i=0; i<t && r!=1; i++){
           if (v[i] == x) r=1;
       }
       return r;
}   
int elimRep (int v[], int N) {
       int i=1, j=1;
       while (i<N){
           if (pertence(v[i], v, i)) i++;
           else {v[j]=v[i];j++;i++;}
       }
       return j;
}

//34
int pertence (int x, int v[], int t){
       int i,r=0;
       for (i=0; i<t && r!=1; i++){
           if (v[i] == x) r=1;
       }
       return r;
}   
int elimRepOrd (int v[], int N) {
       int i=1, j=1;
       while (i<N){
           if (pertence(v[i], v, i)) i++;
           else {v[j]=v[i];j++;i++;}
       }
       return j;
}

//35
int comunsOrd (int a[], int na, int b[], int nb){
    int ia=0,ib=0,r=0;
    while (ia<na && ib<nb){
        if (a[ia]==b[ib]){ia++;ib++;r++;}
        else if (a[ia]<b[ib]){ia++;}
             else {ib++;}
    }
    return r;
}

//36
int elem (int n, int var, int v[]){
    int i, r=0;
    for (i=0;i<n && r!=1; i++){
        if (v[i]==var){r=1;}
    }
    return r;
}
int comuns (int a[],int na,int b[],int nb){
    int x=0,j;
    for (j=0;j<na;j++){
        if (elem(nb,a[j],b)) {x++;}
    }
    return x;
}

//37
int minInd (int v[],int n){
    int i,ind = 0, min = v[0];
    for (i=0; i<n; i++){
        if (v[i]<min) {min=v[i];ind=i;}
    }
    return ind;
}

//38
void somasAc (int v[],int Ac[],int N){
    int i,j;
    for (i=0; i<N; i++){
        Ac[i]=0;
        for (j=0; j<=i; j++){
            Ac[i]=Ac[i]+v[j];
        }
    }
}

//39
int triSup (int N, float m[N][N]) {
    int i,j,r=1;
    for (i=0; i<N; i++){
        for (j=0; j<N; j++){
            if (j<i && m[i][j]!=0){r=0;}
        }
    }
    return r;
}

//40
void transposta (int N, float m[N][N]) {
    int i,j;
    for (i=0; i<N; i++){
        for (j=i+1; j<N; j++){
            if (j != i){
            int aux = m[i][j];
            m[i][j] = m[j][i];
            m[j][i] = aux;
            }
        }
    }
}

//41
void addTo (int N,int M, int a[N][M], int b[N][M]){
    int i,j;
    for (i=0; i<N; i++){
        for (j=0; j<M; j++){
            a[i][j] = a[i][j] + b[i][j];
        }
    }
}

//42
int unionSet (int N, int v1[N], int v2[N], int r[N]){
    int i;
    for (i=0; i<N; i++){
        if (v1[i] == 1 || v2[i] == 1) r[i]=1;
        else r[i] = 0;
    }
    return i;
}

//43
int intersectSet (int N, int v1[N], int v2[N], int r[N]){
    int i;
    for (i=0; i<N; i++){
        r[i]=v1[i]*v2[i];
    }
    return i;
}

//44
int intersectMSet (int N, int v1[N], int v2[N], int r[N]){
    int i;
    for (i=0; i<N; i++){
        if (v1[i] < v2[i]) r[i] = v1[i];
        else r[i] = v2[i];
    }
    return i;
}   

//45
int unionMSet (int N, int v1[N], int v2[N], int r[N]){
    int i;
    for (i=0; i<N; i++){
        r[i] = v1[i] + v2[i];
    }
    return i;
} 

//46
int cardinalMSet (int N, int v[N]){
    int i,c=0;
    for (i=0;i<N;i++){
        c = c + v[i];
    }
    return c;
}

//47
typedef enum movimento {Norte, Oeste, Sul, Este} Movimento;
typedef struct posicao {int x, y;} Posicao;
Posicao posFinal (Posicao inicial, Movimento mov[], int N){
    int i;
    for (i=0;i<N; i++){
        if (mov[i] == Norte) inicial.y = inicial.y + 1;
        else if (mov[i] == Sul) inicial.y = inicial.y - 1;
             else if (mov[i] == Oeste) inicial.x = inicial.x - 1;
                  else inicial.x = inicial.x + 1;
    }
    return inicial;
}

//48
typedef enum movimento {Norte, Oeste, Sul, Este} Movimento;
typedef struct posicao {int x, y;} Posicao;
int caminho (Posicao inicial, Posicao final, Movimento mov[], int N){
    int i,c=0;
    for (i=0; inicial.x != final.x || inicial.y != final.y; i++){
        if (inicial.x < final.x) {mov[i] = Este; inicial.x = inicial.x +1;c++;}
        else if (inicial.x > final.x) {mov[i] = Oeste; inicial.x = inicial.x -1;c++;}
             else if (inicial.y < final.y) {mov[i] = Norte; inicial.y = inicial.y +1;c++;}
                  else if (inicial.y > final.y) {mov[i]=Sul; inicial.y = inicial.y -1;c++;}
                       else break;

    }
    if (c <= N) return c;
    else return -1;
}

//49
typedef struct posicao {int x, y;} Posicao;
int maiscentral (Posicao pos[], int N) {
    int i,ind=0,min=N;
    for (i=0; i<N; i++){
        if ((((pos[i].x)*(pos[i].x)) + ((pos[i].y)*(pos[i].y))) < min) {
            min = (((pos[i].x)*(pos[i].x)) + ((pos[i].y)*(pos[i].y))); 
            ind = i;
        }
    }
    return ind;
}

//50
int vizinhos (Posicao p, Posicao pos[], int N) {
    int i,c=0;
    for (i=0;i<N;i++){
        if ((p.y == pos[i].y) && (p.x +1 == pos[i].x)) {c++;}
        else if ((p.y == pos[i].y) && (p.x -1 == pos[i].x)) {c++;}
             else if ((p.y +1 == pos[i].y) && (p.x == pos[i].x)) {c++;}
                  else if ((p.y -1 == pos[i].y) && (p.x == pos[i].x)) {c++;}
    }
    return c;
}