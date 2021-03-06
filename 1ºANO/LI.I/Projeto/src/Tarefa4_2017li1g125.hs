{-|
Module      : Tarefa4_2017li1g125
Description : Módulo da Tarefa 4 para LI1 17/18

Módulo para a realização da Tarefa 4 de LI1 em 2017/18.
-}
module Tarefa4_2017li1g125 where

import LI11718

{-|
O testes a serem considerados pelo sistema de /feedback/
para a função 'atualiza'.
-}
testesT4 :: [(Tempo,Jogo,Acao)]
testesT4 = [(1,Jogo m (Propriedades 1 2 3 2 5 30) [Carro (2.5,1.5) 0 (1,1)]  [3] [[]], Acao True False False False Nothing), 
            (1,Jogo m (Propriedades 1 2 3 2 5 30) [Carro (3.5,3.5) 0 (1,1)]  [3] [[]], Acao False False False False Nothing), 
            (1,Jogo m (Propriedades 1 2 3 2 5 30) [Carro (2.5,1.5) 0 (1,1)]  [3] [[]], Acao True True True True Nothing),           
            (1,Jogo m (Propriedades 1 2 3 2 5 30) [Carro (2.5,1.5) 0 (1,1)]  [3] [[]], Acao False True False False Nothing),  
            (1,Jogo m (Propriedades 1 2 3 2 5 30) [Carro (2.5,1.5) 0 (1,1)]  [3] [[]], Acao False False True False Nothing),
            (1,Jogo m (Propriedades 1 2 3 2 5 30) [Carro (2.5,1.5) 0 (1,1)]  [3] [[]], Acao False False False True Nothing)]
            
m = Mapa ((2,1),Este) [[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0],
                       [Peca Lava 0,Peca (Curva Norte) (-2),Peca (Rampa Este) (-2),Peca Recta (-1),Peca (Rampa Oeste) (-2),Peca (Curva Este) (-2),Peca Lava 0],
                       [Peca Lava 0,Peca (Rampa Sul) (-2),Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca (Rampa Sul) (-2),Peca Lava 0],
                       [Peca Lava 0,Peca Recta (-1),Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Recta (-1),Peca Lava 0],
                       [Peca Lava 0,Peca (Rampa Norte) (-2),Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca (Rampa Norte) (-2),Peca Lava 0],
                       [Peca Lava 0,Peca (Curva Oeste) (-2),Peca (Rampa Este) (-2),Peca Recta (-1),Peca (Rampa Oeste) (-2),Peca (Curva Sul) (-2),Peca Lava 0],
                       [Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0]]
{-|
Função usada para atualizar o estado do jogo dadas as
ações de um jogador num determinado período de tempo. 
-}
atualiza :: Tempo -- ^ a duração da ação
         -> Jogo  -- ^ o estado do jogo
         -> Int   -- ^ o identificador do jogador
         -> Acao  -- ^ a ação tomada pelo jogador
         -> Jogo  -- ^ o estado atualizado do jogo
atualiza t e j a = Jogo a1 a2 a3 a4 a5
        where a1 = mapa e
              a2 = pista e
              a3 = atualizaCarros e t j a
              a4 = atualizaNitro a j t (nitros e)
              a5 = atualizaHistorico e j a
------------------------------------------------------------------------------------------------------------------------------

-- | Função que converte graus para radianos.
grauTOradiano :: Double -> Double
grauTOradiano a = (a * pi) / 180
-- | Função que converte polar para cartesianos.
polarToCartesiano :: Ponto -> Ponto
polarToCartesiano (x,y) = (x * cos y, x * sin y)
-- | Função que determina o ângulo da direção inicial.
angulodirecaoinicial :: Velocidade -> Angulo
angulodirecaoinicial (x,y) | x == 0 && y > 0 = pi/2
                           | x == 0 && y < 0 = pi/2
                           | y == 0 && x > 0 = 0
                           | y == 0 && x < 0 = pi
                           | otherwise       = atan(y/x)
----------------------------------------------------------

-- | Função que obtém a força do atrito como uma percentagem da velocidade inicial com sentido oposto à velocidade.
vetorAtrito :: Angulo -> Ponto -> Propriedades -> Ponto
vetorAtrito a (x,y) p = (k_atrito p * (-x), k_atrito p * (-y))

-- | Função que devolve o tipo da peça em determinado ponto.
tipoPeca :: Mapa -> Ponto -> Tipo
tipoPeca (Mapa (p,o) t) (x,y) = aux ((!!) ((!!) t (truncate y)) (truncate x)) 
                              where aux :: Peca -> Tipo
                                    aux (Peca t a) = t
                                    
-- | Função que calcula a força da gravidade apenas nas rampas.
vetorGravidade :: Tipo -> Propriedades -> Velocidade
vetorGravidade t p | t == Rampa Norte = (0,k_peso p)
                   | t == Rampa Este  = (-k_peso p,0)
                   | t == Rampa Sul   = (0,- k_peso p)
                   | t == Rampa Oeste = (k_peso p,0)
                   | otherwise = (0,0)

-- | Função que calcula a força da aceleração.
vetorAceleracao :: Double -> Propriedades -> Ponto
vetorAceleracao x p = (cos (grauTOradiano x) * k_acel p,sin (grauTOradiano x) * k_acel p)

-- | Função que atualiza o vetor da velocidade.
vetorNitro:: Propriedades -> Velocidade -> Velocidade
vetorNitro p (x,y) = (x*k_nitro p, y*k_nitro p)

-- | Função que calcula a força dos pneus sendo um vetor perpendicular à direção do carro, no sentido oposto à velocidade.
vetorPneus :: Angulo -> Ponto -> Propriedades -> Ponto
vetorPneus a (x,y) p | abs(grauTOradiano a - angulodirecaoinicial (x,y)) > pi = ((k_pneus p * sin(angulodirecaoinicial (x,y)) - grauTOradiano a) * sqrt (x^2 + y^2), grauTOradiano a + pi/2)
                     | otherwise                                              = ((k_pneus p * sin(angulodirecaoinicial (x,y)) - grauTOradiano a) * sqrt (x^2 + y^2), grauTOradiano a - pi/2)

-- | Função que soma todos os vetores das forças.
somaVetores :: Ponto -> Tempo -> Angulo -> Mapa -> Velocidade -> Propriedades -> Bool -> Bool -> (Double,Double)
somaVetores pt t a m (x,y) p b b1 | b == False && b1 == False = (x + t*fst(vetorAtrito a (x,y) p) + t*fst(polarToCartesiano (vetorPneus a (x,y) p)) + t*fst(vetorGravidade (tipoPeca m pt) p) ,y + t*snd(vetorAtrito a (x,y) p) + t*snd(polarToCartesiano (vetorPneus a (x,y) p)) + t*snd(vetorGravidade (tipoPeca m pt) p))
                                  | b == True  && b1 == False = (x + t*fst(vetorAceleracao a p) + t*fst(vetorAtrito a (x,y) p) + t*fst(polarToCartesiano (vetorPneus a (x,y) p)) + t*fst(vetorGravidade (tipoPeca m pt) p) ,y + t*snd(vetorAceleracao a p)+ t*snd(vetorAtrito a (x,y) p) + t*snd(polarToCartesiano (vetorPneus a (x,y) p)) + t*snd(vetorGravidade (tipoPeca m pt) p))
                                  | b == False && b1 == True  = (x - t*fst(vetorAceleracao a p) + t*fst(vetorAtrito a (x,y) p) + t*fst(polarToCartesiano (vetorPneus a (x,y) p)) + t*fst(vetorGravidade (tipoPeca m pt) p) ,y - t*snd(vetorAceleracao a p)+ t*snd(vetorAtrito a (x,y) p) + t*snd(polarToCartesiano (vetorPneus a (x,y) p)) + t*snd(vetorGravidade (tipoPeca m pt) p))
                                  
-- | Função que atualiza a velocidade.
atualizaVelocidade :: Tempo -> Ponto -> Jogo -> Angulo -> Velocidade -> Acao -> Velocidade
atualizaVelocidade t x j a v (Acao ac tr es di Nothing)  | ac == True  && tr == False = somaVetores x t a (mapa j) v (pista j) True False
                                                         | ac == False && tr == True  = somaVetores x t a (mapa j) v (pista j) False True
                                                         | ac == False && tr == False = somaVetores x t a (mapa j) v (pista j) False False
                                                         | ac == True  && tr == True  = somaVetores x t a (mapa j) v (pista j) False False
atualizaVelocidade t x j a v (Acao ac tr es di (Just n)) | ac == True  && tr == False = (fst(somaVetores x t a (mapa j) v (pista j) True False)  + t * fst(vetorNitro (pista j) v),  snd(somaVetores x t a (mapa j) v (pista j) True False)  + t * snd(vetorNitro (pista j) v))
                                                         | ac == False && tr == True  = (fst(somaVetores x t a (mapa j) v (pista j) False True)  + t * fst(vetorNitro (pista j) v),  snd(somaVetores x t a (mapa j) v (pista j) False True)  + t * snd(vetorNitro (pista j) v))
                                                         | ac == False && tr == False = (fst(somaVetores x t a (mapa j) v (pista j) False False) + t * fst(vetorNitro (pista j) v),  snd(somaVetores x t a (mapa j) v (pista j) False False) + t * snd(vetorNitro (pista j) v))
                                                         | ac == True  && tr == True  = (fst(somaVetores x t a (mapa j) v (pista j) False False) + t * fst(vetorNitro (pista j) v),  snd(somaVetores x t a (mapa j) v (pista j) False False) + t * snd(vetorNitro (pista j) v))
                                                         | otherwise = vetorNitro (pista j) v
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- | Função que atualiza a direção do carro.
atualizaDirecao :: Tempo -> Jogo -> Angulo -> Acao -> Angulo
atualizaDirecao t j a (Acao _ _ e d _) | e == True = a+t*k_roda (pista j)
                                       | d == True = a-t*k_roda (pista j)
                                       | otherwise = a
---------------------------------------------------------------------------

-- | Função que diminui a quantidade de tempo decorrido.
retiraNitro :: Tempo -> Tempo -> Tempo 
retiraNitro t 0  = t
retiraNitro t t1 | t1 - t == 0 = 0
                 | t1 - t >  0 = t1 - t
                 | otherwise   = 0

-- | Função que atualiza a quantidade de nitro.
atualizaNitro :: Acao -> Int -> Tempo -> [Tempo] -> [Tempo] 
atualizaNitro (Acao _ _ _ _ Nothing)  i tp (h:tl) = h : tl
atualizaNitro (Acao a t e d (Just n)) i tp (h:tl) | i == 0    = retiraNitro h tp : tl 
                                                  | otherwise = h : atualizaNitro (Acao a t e d (Just n)) (i-1) tp tl
-------------------------------------------------------------------------------------------------------------------------------------------------------
-- | Função que devolve a posição do carro.
posicaocarro :: Carro -> Posicao
posicaocarro (Carro (x,y) d v) = (truncate x,truncate y)

-- | Função que junta posições.
acrescentaposicao :: [Posicao] -> Posicao -> [Posicao]
acrescentaposicao [] p = [p]
acrescentaposicao l p  = l ++ [p] 

-- | Função que atualiza o histórico de posições dependente do identificador do jogador.
hist :: Int -> [[Posicao]] -> Carro -> [[Posicao]]
hist i (h:t) c | i == 0    = acrescentaposicao h (posicaocarro c) : t 
               | otherwise = h : hist (i-1) t c

-- | Função que atualiza o histórico de posições.
atualizaHistorico :: Jogo -> Int -> Acao -> [[Posicao]]
atualizaHistorico (Jogo m p c n h) i Acao{} = hist i h (selecionacarro c i)

-- | Função que devolve o carro correspondente ao jogador.
selecionacarro :: [Carro] -> Int -> Carro
selecionacarro c i = (!!) c i 

-- | Função que coloca o carro correspondente ao jogador na lista de carros.
colocacarro :: [Carro] -> Carro -> Int -> [Carro]
colocacarro [] c i = [c] 
colocacarro (h:t) c i | i == 0    = c : t
                      | otherwise = h : colocacarro t c (i-1)

-- | Função que atualiza a direção e velocidade do carro.
atualizaCarros :: Jogo -> Tempo -> Int -> Acao -> [Carro]
atualizaCarros j t i a = colocacarro (carros j) (aux j t (selecionacarro (carros j) i) i a) i
                      where aux :: Jogo -> Tempo -> Carro -> Int -> Acao -> Carro 
                            aux j t (Carro p d v) i a  = Carro p (atualizaDirecao t j d a) (atualizaVelocidade t p j d v a)
-----------------------------------------------------------------------------------------------------------------------------                    

