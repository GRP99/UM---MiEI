{-|
Module      : Tarefa3_2017li1g125
Description : Resolução da Tarefa 3 no âmbito da 1ª Fase do projeto de LI I

Código para a implementação da tarefa 3 conforme as regras estabelecidas no enunciado.
-}
module Tarefa3_2017li1g125 where

import LI11718

-- | Exemplo de caminhos que servem de teste para a realização da Tarefa 3 do projeto
testesT3 :: [(Tabuleiro,Tempo,Carro)]
testesT3 = [(tab1 , 2 , (Carro (2.5,6.5) 45 (0,-1))),(tab1, 1 , (Carro (6.1,4.8) 45 (-1,-1)))]

-- | Exemplo de um Tabuleiro para usar nos testes
tab1 :: Tabuleiro
tab1 = [[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0],
        [Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca (Curva Norte) 0,Peca Recta 0,Peca (Curva Este) 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0],
        [Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca (Curva Norte) 0,Peca (Curva Sul) 0,Peca Lava 0,Peca (Curva Oeste) 0,Peca Recta 0,Peca (Rampa Este) 0,Peca Recta 1,Peca (Curva Este) 1,Peca Lava 0],
        [Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Recta 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Recta 1,Peca Lava 0],
        [Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Recta 0,Peca (Curva Norte) 0,Peca Recta 0,Peca (Curva Este) 0,Peca Lava 0,Peca (Curva Norte) 0,Peca (Rampa Este) 0,Peca (Curva Sul) 1,Peca Lava 0],
        [Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Recta 0,Peca (Rampa Norte) (-1),Peca Lava 0,Peca Recta 0,Peca Lava 0,Peca Recta 0,Peca Lava 0,Peca Lava 0,Peca Lava 0],
        [Peca Lava 0,Peca (Curva Norte) 0,Peca Recta 0,Peca (Curva Sul) 0,Peca Recta (-1),Peca Lava 0,Peca (Curva Oeste) 0,Peca Recta 0,Peca (Curva Sul) 0,Peca Lava 0,Peca Lava 0,Peca Lava 0],
        [Peca Lava 0,Peca (Curva Oeste) 0,Peca Recta 0,Peca (Rampa Oeste) (-1),Peca (Curva Sul) (-1),Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0],
        [Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0]]

-- | Função que calcula o novo estado do carro após se ter movimentado durante o período de tempo dado.
movimenta :: Tabuleiro -> Tempo -> Carro -> Maybe Carro
movimenta m t (Carro p d v) = dPLavaNovo m p v t

-- | Função dado um Ponto, um vetor Velocidade e um periodo de tempo calcula o proximo estado do Ponto.
proximaPos :: Ponto -> Velocidade -> Tempo -> Ponto
proximaPos (x,y) (u,v) t = (x+(u*t),y+(v*t))
                                     
-- | Função que converte um Ponto, depois da sua translação, numa Posicao.
pontoParaPos :: Ponto -> Velocidade -> Tempo -> Posicao
pontoParaPos p v t = let (x,y) = proximaPos p v t
                     in (floor x,floor y)

-- | Função que dado um Tabuleiro e uma Posicao calcula a Peca que está nessa Posicao no Tabuleiro.            
posPecaTab :: Tabuleiro -> Posicao -> Maybe Peca
posPecaTab t (x,y) = elemMaybe (getElem t y) x 
           
-- | Função que dado um Nothing ou Just de uma lista de 'a' e um Inteiro devolve o elemento da lista na posicao desse Inteiro.
elemMaybe :: Maybe [a] -> Int -> Maybe a
elemMaybe Nothing _ = Nothing
elemMaybe (Just (h:t)) 0 = Just h
elemMaybe (Just (h:t)) n = elemMaybe (Just t) (n-1)

-- | Função que dado uma lista genérica de 'a' e um Inteiro devolve o elemento da lista na posicao desse Inteiro.
getElem :: [a] -> Int -> Maybe a
getElem [] _ = Nothing
getElem (h:t) 0 = Just h
getElem (h:t) n = getElem t (n-1)

-- | Função que dado um Tabuleiro, um Ponto, o vetor velocidade e tempo irá o Carro atualizado caso não seja destruido por lava.
dPLavaNovo :: Tabuleiro -> Ponto -> Velocidade -> Tempo -> Maybe Carro
dPLavaNovo m p v t | posPecaTab m (pontoParaPos p v t) == Just (Peca Lava 0) = Nothing
                   | otherwise = Just (Carro (proximaPos p v t) 45 v)

-- | Função que dado um Tabuleiro, um Ponto, o vetor velocidade e tempo irá devolver Nothing caso a peça resultante da translação seja do tipo Lava e Just 'peca _' caso contrário.
dPLavaAntigo :: Tabuleiro -> Ponto -> Velocidade -> Tempo -> Maybe Peca
dPLavaAntigo m p v t | posPecaTab m (pontoParaPos p v t) == Just (Peca Lava 0) = Nothing
                     | otherwise = posPecaTab m (pontoParaPos p v t)

-- | Função que 'converte' um Nothing de uma Maybe Peca num Nothing de um Maybe Carro.
maybeCarro :: Maybe Peca -> Maybe Carro
maybeCarro Nothing = Nothing

-- | Função que testa se uma 'Just' Peca está ou não num tunel, ou seja, se a altura da peça é ou não menor que 0.
estaNumTunel :: Maybe Peca -> Bool
estaNumTunel Nothing = False
estaNumTunel (Just (Peca t a)) | a < 0 = True
                               | otherwise = False
