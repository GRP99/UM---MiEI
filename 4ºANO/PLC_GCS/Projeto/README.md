# Projeto
Desenvolvimento de uma linguagem de domínio específico projetada de forma a implementar um sistema de apoio ao professor que lhe permita escolher um recurso de aprendizagem adequado a um dado aluno para ensino de um determinado conceito de programação de modo a tentar maximizar a eficácia do processo de ensino/aprendizagem num curso de Introdução à Programação. Esta linguagem foi uma maneira de expressar a solução para problema proposto de maneira mais simples e direta que a utilização de uma linguagem de programação especifica.

## Contextualização
Tal como foi referido previamente foi desenvolvido uma linguagem, sendo necessário descrever a estrutura das frases e palavras em linguagem natural. Para tal foi utilizado uma **Gramática de Atributos** pois permite a definição local do significado de cada símbolo num estilo declarativo além disso a atribuição do valor de cada símbolo é local.

## Linguagem
Com base nos conceitos básicos na linguagem desenvolvida SelRA apresentados o sistema deve propor os recursos mais adequados para tal propomos a seguinte [linguagem](SelRA.g4).

## Resultados
Após todo o processo de construção, povoação e implementação do sistema foi necessário apresentar os resultados obtidos conforme as nossas convicções : breve, claro e sucinto. Para tal foi construído um [servidor](___server___/server.js) em *Node.js* que fornece páginas HTML mediante pedidos efetuado no browser. E de salientar que as estruturas construídas originam um [ficheiro JSON](___server___/db.json) pois com o auxílio do *JSON-Server* facilitou a apresentação da informação.