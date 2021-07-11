# Trabalho Prático Nº2
## Rede Overlay de anonimização do originador

## Objetivo
O objetivo deste trabalho foi desenhar e implementar uma rede Overlay de anonimização do originador. Os clientes de origem e os servidores de destino não foram implementados pois podem ser genéricos (ex: clientes e servidores HTTP, ou SSH ou
outros). O único componente que foi necessário desenhar e implementar foi o gateway de transporte para a rede overlay de anonimização designado por **AnonGW**. 

A rede é formada por múltiplas instâncias desse mesmo servidor (AnonGW). Esses agentes recebem pedidos TCP vindos dos clientes e pedidos UDP vindos dos seus pares. O objetivo principal foi desenhar um protocolo para funcionar sobre UDP que garanta a entrega ordenada (pela mesma ordem recebida) e confidencial (usando cifragem do conteúdo) dos dados de uma ou mais conexões de transporte TCP (multiplexagem de clientes). Não se lidou com a congestão da rede. Quanto às perdas que possam ocorrer, a recuperação de um pacote por retransmissão é apenas um requisito opcional considerado menos prioritário. Todos os dados recebidos de uma extremidade TCP devem ser reenviados pelo túnel UDP e vice-versa.