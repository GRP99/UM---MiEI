#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <wordexp.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <time.h>

struct SG{
     int sinal;     
};

struct PONTO{
	int linha;
};

struct AGR{
	int ultimalinha;
};

struct STOCK{
    int quantidade;
};

struct ART{
     int id;
     int preco;
};

struct VENDA{
	int codigo;
	int quantidade;
	int facturado;
};


int conta(char* filename){
	int res = 0;
	struct STOCK s;                                
	int f = open(filename,O_RDONLY,0666);
	if (f < 0) write(STDERR_FILENO,"Error.\n",8);
 	while(read(f,&s,sizeof(struct STOCK)) > 0){
    		res++;
	}
  	if (close(f) < 0) write(STDERR_FILENO,"Error.\n",8);
  	return res;
}

/*Funcao que encontra o preco de um dado ID*/
int encontraPreco(int id){
	struct ART a;
	int tamanho = conta("ARTIGOS.txt");
	int fa = open("ARTIGOS.txt",O_RDONLY,0666);
	if (id > tamanho){
		write(STDERR_FILENO,"Produto não existe.\n",21);
		return -1;
	}
	else{
		lseek(fa,(id-1)*sizeof(struct ART),SEEK_SET);
		read(fa,&a,sizeof(struct ART));
	}
	if (close(fa) < 0) write(STDERR_FILENO,"Error.\n",8);
	return a.preco;
}

/*Funcao que encontra o stock de um dado ID*/
int encontraSTOCK(int id){
	struct STOCK s;
	int tamanho = conta("STOCKS.txt");
	int fs = open("STOCKS.txt",O_RDONLY,0666);
	if (fs < 0) write(STDERR_FILENO,"Error.\n",8);
	if (id > tamanho){
		write(STDERR_FILENO,"Produto não existe.\n",21);
		return -1;
	}
	else{
		lseek(fs,(id-1)*sizeof(struct STOCK),SEEK_SET);
		read(fs,&s,sizeof(struct STOCK));
	}
	if (close(fs) < 0) write(STDERR_FILENO,"Error.\n",8);
	return s.quantidade;
}

/*Função que atualiza o stock de um dado ID*/
void atualizaSTOCK(int id, int novo){
	struct STOCK s;
	int fs = open("STOCKS.txt", O_RDWR, 0666);
	if (fs < 0) write(STDERR_FILENO,"Error.\n",8);
	lseek(fs,(id-1)*sizeof(struct STOCK),SEEK_SET);
	read(fs,&s,sizeof(struct STOCK));
	s.quantidade = novo + s.quantidade;	
	lseek(fs,(id-1)*sizeof(struct STOCK),SEEK_SET);
	write(fs,&s,sizeof(struct STOCK));
	if (close(fs) < 0) write(STDERR_FILENO,"Error.\n",8);
}

/*Função que adiciona uma venda*/
void adicionaVenda(int id, int q, float p){
	struct VENDA v;
	int fat = (abs(q) * p);
	v.codigo = id;
	v.quantidade = abs(q);
	v.facturado = fat;
	int fs = open("VENDAS.txt", O_WRONLY | O_APPEND);
	if (fs < 0) write(STDERR_FILENO,"Error.\n",8);
	write(fs,&v,sizeof(struct VENDA));
	if (close(fs) < 0) write(STDERR_FILENO,"Error.\n",8);
}

int tradutor(){
	int count=0;
	char buf[64];
	struct PONTO momento;
	struct VENDA vend;
	int fv = open("VENDAS.txt", O_RDONLY);
	int tmp = open("tempVENDAS.txt", O_RDWR | O_CREAT, 0666);
	if (tmp < 0) return 0;
	lseek(fv,0,SEEK_SET);
	read(fv,&momento,sizeof(struct PONTO));
	lseek(fv,(momento.linha) * sizeof(struct VENDA),SEEK_CUR);
	while (read(fv,&vend,sizeof(struct VENDA))){
		sprintf(buf,"%d %d %d\n",vend.codigo,vend.quantidade,vend.facturado);
		write(tmp,buf,strlen(buf));
		count++;
	}
	close(fv);
	close(tmp);
    	return count;
}

int main(){
	int in,out,n, flag=1, fv, exit=1, status, v;
	int codigo=0, stock=0, quantidade=0, aux=0, preco=0, x, sms;
	char buff[1024];
	char* tok;
	struct AGR agregada;
	int sinal, tmp;
	char namefile[64];
	time_t rawtime;
	pid_t c_pid, pid;
	struct tm *info;
	struct SG ativa;
	struct PONTO momento;

	char* args[] = {"./ag",NULL};

	mkfifo("pipeI", 0777);
	mkfifo("pipeO", 0777);
	mkfifo("pa", 0777);
	mkfifo("pm", 0777);

	if (!fork()){

		while(exit){
			in = open("pipeI",O_RDONLY);
			if (in < 0){write(STDERR_FILENO,"Error opening pipeI.\n",22); return -1;}
			out = open("pipeO",O_WRONLY);
			if (in < 0){write(STDERR_FILENO,"Error opening pipeO.\n",22); return -1;}

			n = read(in,buff,1024);

			if (n > 0){

				if (!(strcmp(buff,"exit"))) exit=0;

				else {

					buff[(strlen(buff))] = '\0';
					tok = strtok(buff," ");
					codigo = atoi(tok);
					aux = 1;
					while(tok!=NULL){
						tok=strtok(NULL," ");
						if(tok != NULL){
							switch(aux){
								case 1:{quantidade=atoi(tok);aux++;}break;
								case 2:{write(STDERR_FILENO,"Invalid command.\n",18);return -1;}break;
							}
						}
					}

					if (aux == 1){
         					stock = encontraSTOCK(codigo);
         					preco = encontraPreco(codigo);
         					write(out,&stock,sizeof(int));
         					write(out,&preco,sizeof(int));
					}

					else if (aux == 2){
        						if (quantidade < 0){
        							preco = encontraPreco(codigo);
        							fv = open("VENDAS.txt", O_RDWR, 0666);
        							if (read(fv,&agregada,sizeof(struct AGR))) flag = 0;
       							if (flag){
         								agregada.ultimalinha = 0;
         								write(fv,&agregada,sizeof(struct AGR));
        							}	
         							close(fv);
         							adicionaVenda(codigo,quantidade,preco);
         						}
         						atualizaSTOCK(codigo,quantidade);
         						x = encontraSTOCK(codigo);
         						write(out,&x,sizeof(int));	
						}
				}
			}

         		if (n <= 0){
         			sinal = open("pa",O_RDONLY | O_NONBLOCK);
         			sms = open("pm",O_WRONLY);
         			if (read(sinal,&ativa,sizeof(struct SG))){
         				if (ativa.sinal){
         					v = open("VENDAS.txt", O_RDONLY, 0666);
         					lseek(v,0,SEEK_SET);
         					if(read(v,&momento,sizeof(struct PONTO))){
         						x = tradutor();
         						if (x == 0) return -1;
         						time(&rawtime);
         						info = localtime(&rawtime);
                				sprintf(namefile,"%d-%d-%dT%d:%d:%d.txt",(info->tm_year+1900),(info->tm_mon+1),info->tm_mday,info->tm_hour,info->tm_min,info->tm_sec);
                				tmp = open("tempVENDAS.txt",O_RDONLY);
                				out = open(namefile,O_WRONLY | O_TRUNC | O_CREAT, S_IRUSR | S_IRGRP | S_IWGRP | S_IWUSR);
                				dup2(tmp,0);
         						dup2(out,1);
         						close(tmp);
  								close(out);
  								c_pid = fork();
  								if (c_pid == 0){
  									execv(args[0],args);
  									perror("execlp failed");
  								}
  								else if (c_pid > 0){
    									if((pid = wait(&status)) < 0){
      										perror("wait");
      										_exit(1);
    									}
    									momento.linha = momento.linha + x;
    									lseek(v,0,SEEK_SET);
    									write(v,&momento,sizeof(struct PONTO));
    									ativa.sinal = x;
    									write(sms,&x,sizeof(int));
    									dup2(0,tmp);
    									dup2(1,out);
  									}
  									else{
  										perror("fork failed");
  										_exit(1);
  									} 
  								remove("tempVENDAS.txt"); 	
  							}
  							else write(STDERR_FILENO,"No sales have been recorded yet.\n",34);
         					close(v);		
         				}
         				else write(STDERR_FILENO,"Invalid command.\n",18);	
         			}
         			close(sinal);
         			close(sms);
         			exit = 0;
         		}
         	}
        close(in);
		close(out);
	}

	else wait(NULL);

	unlink("pipeI");
	unlink("pipeO");
	unlink("pa");
	unlink("pm");

	return 0;
}