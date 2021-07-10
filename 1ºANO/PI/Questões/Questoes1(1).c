#include <stdio.h>
#include <string.h>
#include <ctype.h>

//1
int main () {
    int x, max;
    printf ("Digite um numero: \n");
    scanf ("%d", &x);
    max = x;
    while (x != 0){
        printf ("Digite um numero: \n");
        scanf ("%d", &x);
        if (max < x) max = x;
    }
    printf ("Valor maximo digitado: %d", max);
    return 0;
}

//2
int main () {
    int i, media;
    int sum = 0;
    int n = 0;
    while (i!=0) {
        scanf ("%d", &i);
        sum+=i;
        n++;
    } 
    media = sum/n;
    printf("%d \n", media);
    return 0;
}

//3
int main () {
  int v[2];
  int elem, i;
  scanf("%d", &i);
  v[1]=v[0]=i;
  while (i!=0){
    scanf("%d", &i);
    if (i>v[0]) {v[0]=i;} 
    if (v[0]>v[1]) {
        elem=v[0];
        v[0]=v[1];
        v[1]=elem;
    }
  }
  printf("%d \n", v[0]);
  return 0;
}

//4
int bitsUm (int x){
    int r=0;
    while (x>0){
        if (x%2 != 0) {r+=1;}
            x/=2;
    }
    return r;
}

//5
int trailingZ (unsigned int n) {
    int zeros=0;
    while (n>0){
        if (n%2 == 0) zeros+=1;
        else break;
        n=n/2;
    }
    return zeros;
}

//6
int qDig (int n){
    int r = 0;
    while (n>0){
        r+=1;
        n/=10;
    }
    return r;
}

//7
char *strcat (char s1[], char s2[]){
    int i,j;
    for (i=0; s1[i]!='\0'; i++);
    for (j=0; s2[j]!='\0'; j++){
        s1[i]=s2[j];
        i++;
    }
    s1[i]='\0';
    return s1;
}

//8
char *strcpy(char *dest, char source[]){
    int i;
    for (i=0;source[i]; ++i){
        dest[i]=source[i];
    }
    dest[i]='\0';
    return dest;
}

//9
int mystrcmp (char s1[], char s2[]){
    int i;
    for (i=0; s1[i]==s2[i] && s1[i] != '\0'; i++);
    return (s1[i]-s2[i]);
}

//10
char *strstr (char s1[], char s2[]){
    int i,j=0;
    for (i = 0; s1[i] != '\0' && s2[j] != '\0'; i++){
        if (s1[i] == s2[j]) j++;
        else j = 0;
    }
    if (s2[j] == '\0') return s1 + (i-j);
    else return NULL;
}

//11
void strrev (char s[]){
    char temp;
    int i = 0;
    int f = strlen(s)-1;
    while (i < f){
        temp = s[i];
        s[i] = s[f];
        s[f] = temp;
        i++;
        f--;
    }
}

//12
void strnoV(char s[]){
    int i,j;
    j=0;
    for (i=0;s[i];i++){
        if(s[i]!='A' && s[i]!='E' && s[i]!='I' && s[i]!='O' && s[i]!='U' && s[i]!='a' && s[i]!='e' && s[i]!='i' && s[i]!='o' && s[i]!='u'){
            s[j]=s[i];
            j++;
        }
    }
    s[j]='\0';
}

//13
void truncW (char t[], int n){
    int i=0, j=0, contador=0;   
    while(t[i]!='\0'){
        if (t[i]==' ') contador = 0;
        else contador++;        
        if (contador > n) i++;
        else {t[j]=t[i];j++;i++;}
    }
    t[j]='\0';
}

//14
int conta(char x, char s[]) {
  int i, r = 0;
  for (i = 0; s[i] != '\0'; i++) {
    if (s[i] == x) r++;
  }
  return r;
}
char charMaisfreq(char s[]) {
  char r; int fr=0, i, x;
  for (i = 0; s[i] != '\0'; i++) {
    x = conta(s[i], s);
    if (x > fr) {r = s[i];fr = x;}
  }
  return r;
}

//15
int contaconsecutivos (int x, char a []){
    int i=x;
    int num = 0;
    while (a[x] == a[i]) {i++; num++;}
    return num;
}
int iguaisConsecutivos (char s[]){
    int max = 0;
    int x, i;
    for (i=0;s[i] != '\0'; i++){
        x = contaconsecutivos (i,s);
        if (x>max) max = x;
    }
    return max;
}

//16
int elemento (char s[], int n, char c){
    int i,res =0;
    for (i=0; !res && i<n; i++){
        if (s[i] == c) res = 1;
    }
    return res;
}
int difConsecutivos (char s[]){
    int res = 0, i;
    while (*s != '\0'){
        for (i=1;s[i] != '\0' && !elemento (s,i,s[i]); i++);
        if (res < i) res = i;
        s ++;
    }
    return res;
}

//17
int maiorPrefixo (char s1[], char s2[]){
    int i, conta=0;
    for (i=0; s1[i] != '\0' && s1[i] == s2[i]; i++){
        conta++;
    }
    return conta;
}

//18
int maiorSufixo (char s1 [], char s2 []) {
    int i = strlen (s1)-1;
    int j = strlen (s2)-1;
    int contador = 0;
    while (s1[i]==s2[j]){i--;j--; contador++;}
    return contador;
}

//19
int isPrefix(char s1[], char s2[]){
  int i=0;
  while (s1[i] == s2[i] && s1[i]!='\0' && s2[i]!='\0') i++;
  return (s1[i]=='\0');
}
int sufPref (char s1[], char s2[]){
    int i,max = strlen(s1);                               
    for(i=0; s1[i]!='\0'; i++){
      if (isPrefix(s1+i, s2)) return max-i;     
    }
    return 0;
}

//20
int contaPal (char s[]) {
  int i, conta=0, flag=1;
  for(i=0; s[i]!='\0'; i++){
      if (!isspace (s[i]))
        if (flag) conta++;
        flag=isspace(s[i]);
  }
  return conta;
}

//21
int contaVogais (char s[]){
    int conta=0,i=0;
    while (s[i] != '\0'){
        if(s[i]=='A'||s[i]=='E'||s[i]=='I'||s[i]=='O'||s[i]=='U'||s[i]=='a'||s[i]=='e'||s[i]=='i'||s[i]=='o'||s[i]=='u') {conta++;}
        i++;
    }
    return conta;
}

//22
int elem (char c, char s[]){
    int i;
    for (i=0;s[i];i++){
        if(c==s[i]) {return 1;}
    }
    return 0;
}
int contida (char a[], char b[]){
    int i;
    for (i=0;a[i];i++){
        if (!elem(a[i],b)) {return 0;}
    }
    return 1;
}

//23
int palindorome (char s[]){
    int i, j;
    j = strlen(s)-1;
    for (i=0; i<j; i++){
        if (s[i] != s[j]) {return 0;}
        j--;
    }
    return 1;
}

//24
int remRep (char x[]){
    int i, j;
    i=j=0;
    while (x[i] != '\0'){
        if (x[i] == x [i+1]) i++;
        else {
                x[j] = x[i];
                j++;
                i++;            
        }
    }
    x[j] = '\0';
    return j;
}

//25
int limpaEspacos (char t[]){
    int l=0, j=0;
    while(t[l]!='\0' ){
      if ((t[l])==' ' && t[j-1]== ' ') l++;
      else {t[j]=t[l]; l++; j++;}
    }
    t[j]='\0';
    return j;
}