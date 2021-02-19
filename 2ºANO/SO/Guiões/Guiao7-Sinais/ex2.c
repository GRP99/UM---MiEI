#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

/*Programa que receb como argumento uma lista de comandos
e corre os programas, escalonando-os de forma rotativa em intervalos de 1 segundo*/

#define NEXT(current) (((current) + 1) % num_processes)

int* pids;
int num_processes;

void switchGears(int pidStop, int pidStart)
{
    if (pidStop != -1)
        kill(pidStop, SIGSTOP);
    kill(pidStart, SIGCONT);
}

void babyDed()
{
    int i;
    int pid;
    while ((pid = waitpid(-1, NULL, WNOHANG)) > 0) {
        for (i = 0; pid != pids[i] && i < num_processes; i++)
            ;
        num_processes--;
        while (i < num_processes) {
            pids[i] = pids[i + 1];
            i++;
        }
    }
    signal(SIGCHLD, babyDed);
}

int main(int argc, char** argv)
{
    pids = (int*)malloc(sizeof(int) * (argc - 1));
    for (int i = 0; i < argc - 1; i++) {
        printf("Launching %s\n", argv[i + 1]);
        if (!(pids[i] = fork())) {
            execlp(argv[i + 1], argv[i + 1], NULL);
            _exit(1);
        }
        kill(pids[i], SIGSTOP);
    }
    int current = 0;
    num_processes = argc - 1;
    signal(SIGCHLD, babyDed);
    int first = 1;
    while (num_processes > 0) {
        sleep(1);
        if (num_processes < 1)
            break;
        printf("Num babies: %d\n", num_processes);
        printf("Switching %d <-> %d\n", current, NEXT(current));
        printf("%d <-> %d\n", pids[current], pids[NEXT(current)]);
        switchGears(first ? -1 : pids[current], pids[NEXT(current)]);
        current = NEXT(current);
        first = 0;
    }
    free(pids);
    return 0;
}