# 8º Trabalho Individual

Partindo do trabalho desenvolvido na aula pretendeu-se desenvolver a construção do **servidor** criado de modo a ser possível fazer o upload de múltiplos ficheiros em vez de um ficheiro de cada vez, para isso o servidor responde a pedidos GET e POST na porta 7701, guardando a meta informação num ficheiro *JSON* e os ficheiros em si numa pasta designada "fileStore".

Para tal ser possível foi necessário percorrer todos os ficheiros que o formulário fornece, extraindo a meta informação e efectuar a renomeação de pastas (de uploads para fileStore).