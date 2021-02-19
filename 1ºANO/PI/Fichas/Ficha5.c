#include <stdio.h>
// < Ordenação de vectores >

//1.
void insere (int v[], int N, int x){
	int i;
    for (i=N; i>0 && v[i-1]>x; i--){
    	v[i]=v[i-1];
    }
    v[i]=x;
}


//3.
int maxInd (int v[], int N){
	int i,max,imax;
	max = v[0];
	imax = 0;
	for (i=0; i < N; i++){
		if (max < v[i]) imax = i; 
	}
	return imax;
}

//4.
void maxSort (int v[], int N){
	int imax;
	while (N>1){
		imax = maxInd (N,v);
		swap (a,imax, N-1);
		N--;
	}
}



//9
void merge (int r[], int a[], int na, int b[], int nb){
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

//10
int partition (int v[], int a, int b){
	int aux [b-a+1];
	int mp=0,Mp=b-a,i;
	int pivot = v[b];
	for (i=a;i<b;i++){
		if (v[i] < pivot) {aux[mp++]=v[i++];}
		else {aux[Mp++]=v[i++];}
	}
	aux [mp] = pivot;
	for (i=0; i<b-a+1;i++){
		v[a+i]=aux[i];
	}
	return mp;
}