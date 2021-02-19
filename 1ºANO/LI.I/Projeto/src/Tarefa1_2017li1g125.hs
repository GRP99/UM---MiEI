{-|
Module      : Tarefa1_2017li1g125
Description : Resolução da Tarefa 1 no âmbito da 1ª Fase do projeto de LI I

Código para a implementação da tarefa 1 conforme as regras estabelecidas no enunciado.
-}
module Tarefa1_2017li1g125 where

import LI11718

-- | Exemplo de caminhos que servem de teste para a realização da Tarefa 1 do projeto.
testesT1 :: [Caminho]
testesT1 = [ 
            [Avanca,CurvaDir,Sobe,CurvaDir,Avanca,CurvaDir,Desce,CurvaDir],
            [CurvaDir,CurvaDir,CurvaDir,CurvaDir],
            [Sobe,Avanca,Sobe,CurvaDir,CurvaDir,Desce,Avanca,Desce,CurvaDir,CurvaDir],
            [Avanca,CurvaEsq,Avanca,Avanca,Avanca,CurvaDir,CurvaEsq,CurvaDir,Avanca,CurvaDir,CurvaEsq,Avanca,Sobe,Avanca,CurvaDir,Avanca,CurvaDir,Desce,CurvaEsq,Avanca,CurvaDir,Avanca,CurvaDir,Avanca,CurvaEsq,Avanca,CurvaEsq,Desce,Avanca,CurvaDir,Sobe,Avanca,CurvaDir,CurvaDir],
            [Sobe,Sobe,Sobe,Sobe,Sobe,Avanca,Avanca,Avanca,CurvaEsq,Avanca,Sobe,CurvaEsq,Desce,Desce,Desce,Desce,Desce,Avanca,Avanca,Avanca,CurvaEsq,CurvaEsq,Desce,Desce,Desce,Desce,Desce,Avanca,Avanca,CurvaDir,CurvaDir,Avanca,Avanca,Sobe,Sobe,Sobe,Sobe,Avanca,CurvaEsq,CurvaEsq],
            [Avanca,CurvaEsq,Desce,CurvaDir,Avanca,CurvaDir,Desce,Avanca,CurvaEsq,CurvaDir,CurvaEsq,CurvaDir,CurvaDir,CurvaEsq,CurvaDir,CurvaEsq,CurvaEsq,Avanca, Avanca, Sobe,CurvaDir,CurvaDir,Avanca,Sobe,CurvaEsq,CurvaDir,Sobe,CurvaDir,CurvaEsq,CurvaDir,CurvaEsq,Sobe,CurvaDir,Desce,Avanca,Desce,Avanca,CurvaDir]
           ] 

-- | Função que tem como objetivo transformar um Caminho num Mapa seguindo todas as regras que são mencionadas no enunciado.
constroi :: Caminho -> Mapa
constroi c = Mapa (partida c,dirInit) t
          where t = atualizaCaminhoPosicaoemTabuleiro (passosemPecaPosicao c) (tudoLava (novadimensao c))

-------------------------------------------------------------------------------------------------------------------
-- | Roda a orientação para a direita.
rodaDir :: Orientacao -> Orientacao
rodaDir Norte = Este
rodaDir Este  = Sul
rodaDir Sul   = Oeste
rodaDir Oeste = Norte 

-- | Roda a orientação para a esquerda.
rodaEsq :: Orientacao -> Orientacao
rodaEsq Norte = Oeste
rodaEsq Oeste = Sul
rodaEsq Sul   = Este
rodaEsq Este  = Norte

-- | Move as coordenadas dependente da orientação dada.
mover :: Posicao -> Orientacao -> Posicao 
mover (x,y) Norte = (x,y-1)
mover (x,y) Este  = (x+1,y)
mover (x,y) Oeste = (x-1,y)
mover (x,y) Sul   = (x,y+1)

-- | Muda a orientação dependente do passo fornecido.
mudaOrientacao :: Passo -> Orientacao -> Orientacao
mudaOrientacao Avanca o   = o
mudaOrientacao CurvaDir o = rodaDir o
mudaOrientacao CurvaEsq o = rodaEsq o
mudaOrientacao Sobe o     = o
mudaOrientacao Desce o    = o

-- | Muda a altura dependente do passo fornecido.
mudaAltura :: Passo -> Altura -> Altura
mudaAltura Avanca a   = a
mudaAltura CurvaDir a = a
mudaAltura CurvaEsq a = a
mudaAltura Sobe a     = a + 1
mudaAltura Desce a    = a - 1

-- | Transforma um passo com uma dada orientacao e a altura numa Peca.
passoemPeca :: Passo -> Orientacao -> Altura -> Peca
passoemPeca Avanca o a   = Peca Recta a
passoemPeca CurvaDir o a = Peca (Curva o) a
passoemPeca CurvaEsq o a = Peca (Curva (rodaDir o)) a
passoemPeca Sobe o a     = Peca (Rampa o) a
passoemPeca Desce o a    = Peca (Rampa (rodaDir (rodaDir o))) (a-1)

-- | Ajusta a dimensao ao caminho
novadimensao :: Caminho -> (Int,Int)
novadimensao c = ((fst (dimensao c) - 1), snd (dimensao c) - 1)

-- | Transforma um caminho, uma lista de passos, numa lista com pares sendo o primeiro e o segundo a posição dessa peca.
passosemPecaPosicao :: [Passo] -> [(Peca,Posicao)]
passosemPecaPosicao c = aux (partida c) Este 0 c
            where aux :: Posicao -> Orientacao -> Altura -> [Passo] -> [(Peca,Posicao)]
                  aux _ _ _ [] = []
                  aux (x,y) o a (p:p1) = (passoemPeca p o a,(x,y)) : aux (mover (x,y) (mudaOrientacao p o)) (mudaOrientacao p o) (mudaAltura p a) p1

-- | Cria um tabuleiro apenas com Lava dado uma dimensao.
tudoLava :: (Int,Int) -> Tabuleiro
tudoLava (x,y) | x >= 0 && y >= 0 = linhadelava x : tudoLava (x,y-1) 
               | otherwise        = []
        where linhadelava :: Int -> [Peca] 
              linhadelava x | x >= 0    = Peca Lava 0 : linhadelava (x-1)
                            | otherwise = []

-- | Insere apenas uma peca consoante a posicao no tabuleiro.
atualizaPecaPosicaoemTabuleiro :: (Peca,Posicao) -> Tabuleiro -> Tabuleiro
atualizaPecaPosicaoemTabuleiro _ [] = []
atualizaPecaPosicaoemTabuleiro (p,(x,y)) (t:t1) | x == 0     = atualizaLinhaTabuleiro p y t : t1
                                                | otherwise  = t : atualizaPecaPosicaoemTabuleiro (p,((x-1),y)) t1
                        where atualizaLinhaTabuleiro :: Peca -> Int -> [Peca] -> [Peca]
                              atualizaLinhaTabuleiro p _ [] = []
                              atualizaLinhaTabuleiro p x (h:t) | x == 0    = p:t
                                                               | otherwise = h:atualizaLinhaTabuleiro p (x-1) t
                                                               
-- | Insere uma lista de pecas com posicoes associadas no tabuleiro.
atualizaCaminhoPosicaoemTabuleiro :: [(Peca,Posicao)] -> Tabuleiro -> Tabuleiro
atualizaCaminhoPosicaoemTabuleiro [] t = t
atualizaCaminhoPosicaoemTabuleiro ((p,(x,y)):p1) t = atualizaCaminhoPosicaoemTabuleiro p1 (atualizaPecaPosicaoemTabuleiro (p,(y,x)) t) 