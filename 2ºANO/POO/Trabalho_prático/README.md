# Trabalho prático
## UM carro já!

### Aplicação
Na aplicação desenvolvida, um proprietário de um automóvel pode registar o seu veículo na aplicação UMCarroJá! e este ser alugado por um cliente registado nessa mesma aplicação. Após o cliente escolher o veículo a alugar, o proprietário terá de aceitar (ou não) o aluguer pedido. 

Para tal, um histórico de feedback de aluguer de clientes é mantido, bem como do automóvel (para permitir aos clientes melhor escolherem o veículo que desejam). Uma vez que os veículos são particulares, eles encontram-se estacionados na rua, e não concentrados numa agência de aluguer. Assim, a localização (coordenadas GPS) de um veículo é importante para a escolha por parte de um potencial cliente. 

Um veículo tem ainda a indicação do preço que é cobrado por quilometro percorrido, a quantidade de combustível existente no carro (percentagem de gasolina e/ou bateria), o consumo médio por quilómetro, bem como informação geral do automóvel e do seu proprietário.

Pretendeu-se que a aplicação desenvolvida suporte toda a funcionalidade que permita um utilizador alugar e realizar uma viagem num dos veículos da UMCarroJá! O processo deve abranger todos os mecanismos de criação de clientes, proprietários, automóveis e posteriormente a escolha e aluguer de um automóvel, e a imputação do preço quando o mesmo é devolvido.

Pretendeu-se ainda que o sistema guarde registo de todas as operações efetuadas e que depois tenha mecanismos para as disponibilizar (exemplo: alugueres de um cliente, extrato de aluguer de um carro num determinado período, valor faturado por um proprietário num determinado período, etc.).


### Funcionalidades
Cada perfil de utilizador deve apenas conseguir aceder às informações e funcionalidades respetivas.

Os clientes da UMCarroJá! podem:
- solicitar o aluguer de um carro mais próximo das suas coordenadas;
- solicitar o aluguer do carro mais barato;
- solicitar o aluguer do carro mais barato dentro de uma distância que estão dispostos a percorrer a pé;
- solicitar o aluguer de um carro específico;
- solicitar o aluguer de um carro com uma autonomia desejada.

Os proprietários podem:
- sinalizar que um dos seus carros está disponível para aluguer;
- abastecer o veículo;
- alterar o preço por km;
- aceitar/rejeitar o aluguer de um determinado cliente;
- registar quanto custou a viagem.