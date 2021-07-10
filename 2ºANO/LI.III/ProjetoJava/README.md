# Projecto de Java
## GestVendas

### Objetivo
Pretendeu-se a criação de uma aplicação de uma aplicação Desktop em Java, baseada na utilização das interfaces e das colecções de JCF (“Java Collections Framework”), cujo objectivo foi a realização de consultas interactivas de informações relativas à gestão básica de uma cadeia de distribuição.

### Aplicação
Pretendeu-se desenvolver uma aplicação desktop Java que foi capaz de ler e armazenar em estruturas de dados (colecções de Java) adequadas as informações dos vários ficheiros, para que, se possa realizar diversas consultas (“queries”), algumas estatísticas e alguns testes de “performance”. A classe agregadora de todo o projecto de JAVA será a classe [GestVendas](src/GestVendas.java) e a aplicação final, que segue o padrão MVC, designa-se por [GestVendasAppMVC](src/GestVendasAppMVC.java).

A concepção da aplicação seguiu os princípios da programação com interfaces, ou seja, criou-se antes das classes as APIs, assim visando tornar a aplicação mais genérica e flexível.

Foi criado um Catálogo de Clientes (todos os códigos destes), um Catálogo de Produtos (códigos dos produtos disponíveis), uma Facturacao (relacionando produtos e suas vendas) e um registo de todas as compras realizadas em cada filial, por quem e quando, que designaremos por Filial. Devem ser consideradas inicilamente 3 filiais, identificadas por 1, 2 e 3.

O desenho e a implementação do código da aplicação tiveram em atenção três grandes grupos de funcionalidades:
- Leitura e validação de dados de memória secundária, a partir de ficheiros de texto, e população das estruturas de dados em memória central; Gravação da estrutura de dados (instância da classe GestVendas) em ficheiro de objectos;
- Queries: operações de consulta sobre as estruturas de dados;
- Medidas de performance do código e das estruturas de dados.