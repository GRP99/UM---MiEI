#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <signal.h>
#include <stdlib.h>
#include <string.h>
#include <wordexp.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>

struct SG{
     int sinal;     
};

struct STR{
    char informacao[1024]; 
};

struct STO{
    int quantidade;
};

struct ART{
     int id;
     int pre;
};

char* my_itoa(int num, char *str){
    if(str == NULL) return NULL;
    sprintf(str, "%d", num);
    return str;
}

ssize_t myreadln(int fildes, void *buf, size_t nbyte){
    size_t i = 0;
    ssize_t n = 1;
    char c = ' ';
    while ((i < nbyte) && (n > 0) && (c != '\n')) {
        n = read(fildes, &c, 1);
        if (n && (c != '\n')) {
            ((char*) buf)[i] = c;
            i++;
        }
    }
    if (i < nbyte) ((char*) buf)[i] = 0;
    else {
        i--;
        ((char*) buf)[i] = 0;
    }
    if (n < 0) return n;
    if ((n == 0) && (i == 0)) return (-1);
    return i;
}

int main(int argc, char* argv[]){
     int exit_flag = 0, background_flag = 0, n = 0, flag=0, sos=0, socorro=0;
     int codigo = 0, val = 0;
     int fs, fa, fst;
     char buff[1024];
     char agr[64];
     pid_t pid;
     struct STR infS;
     struct ART infA;
     struct STO infSTO;
     struct SG ativa;
     wordexp_t p;

     fa = open("ARTIGOS.txt",O_RDONLY,0666);
     while (read(fa,&infA,sizeof(struct ART)) > 0){
          flag = 1;
     }
     if (flag == 0) codigo = 1;
     else codigo = 1 + infA.id;
     close(fa);

     while (!exit_flag) {
          background_flag = 0;

          n = myreadln(STDIN_FILENO, buff, 1024);

          while ((n > 0) && (buff[n - 1] == ' ')) {
               n--;
               buff[n] = 0;
          }

          if ((n > 1) && (buff[n - 1] == '&') && (buff[n - 2] == ' ')) {
               background_flag = 1;
               buff[n - 1] = 0;
               buff[n - 2] = 0;
          }

          if ((n > 0) && (!wordexp(buff, &p, 0))) {

               if (strcmp("exit",p.we_wordv[0])) {

                    if (!(strcmp("i",p.we_wordv[0]))){
                         pid = fork();
                         if (pid < 0) write(STDERR_FILENO,"Error in forks.\n",17);
                         if (pid == 0){
                              fs = open("STRINGS.txt", O_WRONLY | O_APPEND);
                              if (fs < 0){
                                    write(STDERR_FILENO,"Error opening file STRINGS.\n",29); 
                                   _exit(-1);
                              }
                              strncpy(infS.informacao,p.we_wordv[1],sizeof(infS.informacao));
                              infS.informacao[sizeof(infS.informacao) - 1] = '\0';
                              write(fs,&infS,sizeof(struct STR));
                              if (close(fs) < 0){
                                   write(STDERR_FILENO,"Error closing file STRINGS.\n",29);
                                   _exit(-1);
                              } 
                              fa = open("ARTIGOS.txt", O_WRONLY | O_APPEND);
                              if (fa < 0){
                                    write(STDERR_FILENO,"Error opening file ARTIGOS.\n",29); 
                                   _exit(-1);
                              }
                              infA.id = codigo;
                              infA.pre = atoi(p.we_wordv[2]);
                              write(fa,&infA,sizeof(struct ART));
                              if (close(fa) < 0){
                                   write(STDERR_FILENO,"Error closing file ARTIGOS.\n",29);
                                   _exit(-1);
                              }
                              fst = open("STOCKS.txt",O_WRONLY | O_APPEND);
                              if (fst < 0){
                                    write(STDERR_FILENO,"Error opening file STOCKS.\n",28); 
                                   _exit(-1);
                              }
                              infSTO.quantidade = 0;
                              write(fa,&infSTO,sizeof(struct STO));
                              if (close(fst) < 0){
                                   write(STDERR_FILENO,"Error closing file STOCKS.\n",28);
                                   _exit(-1);  
                              }
                              _exit(-1);
                         }
                         wordfree(&p);
                         if (!background_flag){
                              waitpid(pid,NULL,0);
                              char* buffer;
                              buffer = malloc(sizeof(codigo));
                              my_itoa(codigo,buffer);
                              write(STDOUT_FILENO,"-> ID: ",7);
                              puts(buffer);
                              codigo++;
                         }
                    }

                    else{
                         if(!(strcmp("n",p.we_wordv[0]))){
                              pid = fork();
                              if (pid < 0) write(STDERR_FILENO,"Error in forks.\n",17);
                              if (pid == 0){
                                   val = atoi(p.we_wordv[1]);
                                   fs = open("STRINGS.txt",O_RDWR,0666);
                                   if (fs < 0){
                                        write(STDERR_FILENO,"Error opening file STRINGS.\n",29);
                                        _exit(-1);
                                   }
                                   if ((val > codigo) || (val < 0)){
                                        write(STDOUT_FILENO,"That ID don't exist.Try again.\n",32);
                                        _exit(-1);
                                   }
                                   strncpy(infS.informacao,p.we_wordv[2],sizeof(infS.informacao));
                                   infS.informacao[sizeof(infS.informacao) - 1] = '\0';
                                   lseek(fs,(val-1)*sizeof(struct STR),SEEK_SET);
                                   write(fs,&infS,sizeof(struct STR));
                                   if (close(fs) < 0) write(STDERR_FILENO,"Error closing file STRINGS.\n",29);
                                   _exit(-1);
                              }
                              wordfree(&p);
                              if (!background_flag) waitpid(pid,NULL,0);
                         }
                         else{
                              if(!(strcmp("p",p.we_wordv[0]))){
                                   pid = fork();
                                   if (pid < 0) write(STDERR_FILENO,"Error in forks.\n",17);
                                   if (pid == 0){
                                        val = atoi(p.we_wordv[1]);
                                        fa = open("ARTIGOS.txt",O_RDWR,0666);
                                        if (fa < 0){
                                             write(STDERR_FILENO,"Error opening file ARTIGOS.\n",29);
                                             _exit(-1);
                                        }
                                        if ((val > codigo) || (val < 0)){
                                             write(STDOUT_FILENO,"That ID don't exist.Try again.\n",32);
                                             _exit(-1);
                                        }
                                        infA.id = val;
                                        infA.pre = atoi(p.we_wordv[2]);
                                        lseek(fa,(val-1)*sizeof(struct ART),SEEK_SET);
                                        write(fa,&infA,sizeof(struct ART));
                                        if (close(fa) < 0){
                                             write(STDERR_FILENO,"Error closing file ARTIGOS.\n",29);
                                             _exit(-1);
                                        }
                                        _exit(-1);
                                   }
                                   wordfree(&p);
                                   if (!background_flag) waitpid(pid,NULL,0); 
                              }
                              else {
                                   if (!(strcmp("a",p.we_wordv[0]))){
                                        pid = fork();
                                        if (pid < 0) write(STDERR_FILENO,"Error in forks.\n",17);
                                        if (pid == 0){
                                             socorro = open("pm",O_RDONLY);
                                             sos = open("pa",O_WRONLY);
                                             if (sos < 0){
                                                  write(STDERR_FILENO,"Server to agregate is off.\n",28);
                                                  return -1;
                                             }
                                             if (socorro < 0){
                                                  write(STDERR_FILENO,"Server to agregate is off.\n",28);
                                                  return -1;
                                             }
                                          	ativa.sinal = 1;
                                             write(sos,&ativa,sizeof(struct SG));
                                             ativa.sinal = -1;
                                             while(ativa.sinal < 0){
                                                  read(socorro,&ativa,sizeof(struct SG));
                                             }
                                             sprintf(agr,"Foram agregadas %d",ativa.sinal);
                                             write(STDOUT_FILENO,&agr,strlen(agr));
                                             close(sos);
                                             close(socorro);
                                             _exit(-1);
                                        }
                                        wordfree(&p);
                                        if (!background_flag) waitpid(pid,NULL,0); 
                                   }
                                   else write(STDERR_FILENO,"Invalid command.\n",18);
                              }
                         }
                    }
               }
               else exit_flag = 1;
          }

          else{
               if (n < 0) exit_flag = 1;
               if (n > 0) write(STDERR_FILENO,"Error with wordexp_t.\n",23);
          }
     }

     return 0;
}