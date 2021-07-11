# Trabalho prático
## Troca de ficheiros

### Objetivos
No presente trabalho pretendeu-se implementar uma plataforma para partilha de ficheiros de música sob a forma de cliente/servidor em Java utilizando *sockets* e *threads*.

### Aplicação
As plataformas de troca de ficheiros como o SoundCloud permitem que músicos partilhem as suas criações diretamente com os seus fãs. Para o efeito, podem carregar ficheiros de música acompanhados de meta-informação variada (título, autor, interprete, género, ...). A meta-informação serve para que os ouvintes tomem conhecimento dos ficheiros partilhados e possam efetuar pesquisas. Tendo encontrado os ficheiros desejados, podem então descarregá-los para uso posterior. 

Tendo em conta que os ficheiros a serem trocados são de dimensão considerável, normalmente com vários MB, a concretização destes sistemas tem que prestar particular atenção aos recursos consumidos com o armazenamento, manipulação e transmissão destes ficheiros. Em particular, é importante a limitação do número de operações simultâneas que podem ser efetuadas para não sobrecarregar o sistema e a manutenção de uma justiça relativa entre os diferentes utilizadores.
