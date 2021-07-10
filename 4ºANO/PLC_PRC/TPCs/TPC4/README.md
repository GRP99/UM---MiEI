### TPC: SPARQL

No presente trabalho pretendeu-se desenvolver uma aplicação WEB de forma a consultar a informação sobre os elementos, grupos e períodos presentes numa ontologia disponibilizada acerca da tabela periódica, para tal foi necessário previamente criar um repositório no **GraphDB** e posteriormente efetuar o carregamento da ontologia para o sistema. Esta aplicação WEB foi desenvolvida com recurso à *framework Express*.

Na página inicial desta aplicação é apresentado uma breve explicação da aplicação do que esta é, além de que na parte superior encontra-se sempre disponíveis ligações para consultar a lista individual de todos os elementos, grupos e períodos que foram importados para o sistema utilizado.

Em cada uma destas listas recorreu-se às *Datatables* do *jQuery* pois permite apresentar a informação ordenada conforme a preferência do utilizador, permite que a tabela se torne mais compacta e ainda permite efetuar uma pesquisa sobre um determinado campo presente na tabela.

Em cada linha da tabela é apresentado um botão que remete para a página individual de cada indivíduo, onde nesta é possível observar mais informações sobre este e visualizar quais as relações entre eles (por exemplo, os elementos de um grupo ou período) existindo a possibilidade de consultar a informação individualizada do individuo em causa. 