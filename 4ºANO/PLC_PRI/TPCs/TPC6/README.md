# 6º Trabalho Individual

Este trabalho consistiu na construção de um servidor desenvolvido em **Node.js** capaz de responder a pedidos de GET e POST na porta 7777 utilizando uma *API REST* configurada através do **JSON-Server** .

Para tal foi construida uma **SPA** (*Single Page Application*) capaz de submeter tarefas com uma descrição, responsável e uma data limite posteriormente essas são apresentadas numa tabela de tarefas pendentes ordenadas por data limite de forma ascendente e pelo responsável. Nesta tabela também é possível marcar cada tarefa como realizada ou cancelada, onde esta é retirada da presente tabela e é introduzida numa outra que possui todas as tarefas realizadas e canceladas também ordenadas pelos mesmos critérios.

Estes dados são armazenados num ficheiro *JSON* criado para o efeito.

Além disso, o servidor é capaz detetar pedidos que não suporta dando como resposta uma página html de erro.