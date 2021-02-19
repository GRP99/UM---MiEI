{-|
Module      : Tarefa2_2017li1g125
Description : Resolução da Tarefa 2 no âmbito da 1ª Fase do projeto de LI I

Código para a implementação da tarefa 2 conforme as regras estabelecidas no enunciado.
-}
module Tarefa2_2017li1g125 where

import LI11718

-- | Exemplo de Tabuleiros que servem de teste para a realização da Tarefa 2 do projeto.
testesT2 :: [Tabuleiro]
testesT2 = [
            [[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0],[Peca Lava 0,Peca (Curva Norte) 0,Peca Recta 0,Peca (Curva Este) 0,Peca Lava 0],[Peca Lava 0,Peca (Rampa Sul) 0,Peca Lava 0,Peca (Rampa Sul) 0,Peca Lava 0],[Peca Lava 0,Peca (Curva Oeste) 1,Peca Recta 1,Peca (Curva Sul) 1,Peca Lava 0],[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0]],
            [[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0],[Peca Lava 0,Peca (Curva Norte) 0,Peca Recta 0,Peca (Curva Este) 0,Peca Lava 0],[Peca Lava 0,Peca (Rampa Sul) 0,Peca Lava 1,Peca (Rampa Sul) 0,Peca Lava 0],[Peca Lava 0,Peca (Curva Oeste) 1,Peca Recta 1,Peca (Curva Sul) 1,Peca Lava 0],[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0]],
            [[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0],[Peca Lava 0,Peca (Curva Norte) 0,Peca Recta 0,Peca (Curva Este) 0,Peca Lava 0],[Peca Lava 0,Peca (Rampa Sul) 0,Peca Lava 0,Peca (Rampa Sul) 0,Peca Lava 0],[Peca Lava 0,Peca (Curva Oeste) 1,Peca Recta 1,Peca (Curva Sul) 1,Peca Lava 0]],
            [[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0],[Peca Lava 0,Peca (Curva Norte) 0,Peca Recta 0,Peca (Curva Este) 0,Peca Lava 0],[Peca Lava 0,Peca (Rampa Sul) 0,Peca Lava 0,Peca (Rampa Sul) 0,Peca Lava 0],[Peca Lava 0,Peca (Curva Oeste) 1,Peca Recta 0,Peca (Curva Sul) 1,Peca Lava 0],[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0]],
            [[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0],[Peca Lava 0,Peca Recta 0,Peca Recta 0,Peca (Curva Este) 0,Peca Lava 0],[Peca Lava 0,Peca (Rampa Sul) 0,Peca Lava 0,Peca (Rampa Sul) 0,Peca Lava 0],[Peca Lava 0,Peca (Curva Oeste) 1,Peca Recta 1,Peca (Curva Sul) 1,Peca Lava 0],[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0]],
            [[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Lava 0],[Peca Lava 0,Peca (Curva Norte) 0,Peca Recta 0,Peca (Curva Este) 0,Peca Lava 0],[Peca Lava 0,Peca (Rampa Sul) 0,Peca Lava 0,Peca (Rampa Sul) 0,Peca Lava 0],[Peca Lava 0,Peca (Curva Oeste) 1,Peca Recta 1,Peca (Curva Sul) 1,Peca Lava 0],[Peca Lava 0,Peca Lava 0,Peca Lava 0,Peca Recta 0,Peca Lava 0]]
           ]
          
-- | Função que tem como objetivo receber um mapa e verificar se esse mapa é ou não válido, segundo as regras do enunciado.
valida :: Mapa -> Bool
valida (Mapa ((x,y),o) t) = (pecainicialvalida ((Mapa ((x,y),o) t))) && (validaPosicaoInicial (Mapa ((x,y),o) t)) && (validaAltura (Mapa ((x,y),o) t)) && (orientacaoInicialFinal (Mapa ((x,y),o) t)) && (alturatabuleiroLava t) && (contapecas (Mapa ((x,y),o) t))  && (tabuleiroRetangulo t)
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- | Função que devolve a peça inicial.
pecainicialTabuleiro :: Mapa -> Peca
pecainicialTabuleiro (Mapa p (h:t)) = (!!) ((!!) (h:t) (snd(fst p))) (fst(fst p))

-- | Testa se a peça inicial é Lava.
pecainicialvalida :: Mapa -> Bool
pecainicialvalida m = if pecainicialTabuleiro m == Peca Lava 0 then False else True

-- | Função que testa se o mapa corresponde a uma trajectória, tal que começa na peça de partida com a orientação inicial e volta a chegar à peça de partida com a orientação inicial.
validaPosicaoInicial :: Mapa -> Bool
validaPosicaoInicial (Mapa p []) = False
validaPosicaoInicial m  = (aux (pecainicialTabuleiro m) m)
             where aux :: Peca -> Mapa -> Bool
                   aux (Peca c a) (Mapa (p,o) t) | c == Recta       && o == Norte = True
                                                 | c == Rampa Norte && o == Norte = True
                                                 | c == Curva Este  && o == Norte = True
                                                 | c == Curva Norte && o == Norte = True
                                                 | c == Recta       && o == Este  = True
                                                 | c == Rampa Este  && o == Este  = True
                                                 | c == Curva Sul   && o == Este  = True
                                                 | c == Curva Este  && o == Este  = True
                                                 | c == Recta       && o == Sul   = True
                                                 | c == Rampa Sul   && o == Sul   = True
                                                 | c == Curva Sul   && o == Sul   = True
                                                 | c == Curva Oeste && o == Sul   = True
                                                 | c == Recta       && o == Oeste = True
                                                 | c == Rampa Oeste && o == Oeste = True
                                                 | c == Curva Norte && o == Oeste = True
                                                 | c == Curva Oeste && o == Oeste = True
                                                 | otherwise = False



-- | Função que dada uma posição e um tabuleiro devolve a peça que se encontra nessa posição.
posicaonoTabuleiro :: Posicao -> Tabuleiro -> Peca
posicaonoTabuleiro (x,y) t = (!!) ((!!) t y) x

-- | Função que determinha a próxima posição dependente da orientação.
move :: (Posicao,Orientacao) -> Posicao
move ((x,y),Este)  = (x-1,y)
move ((x,y),Oeste) = (x+1,y)
move ((x,y),Norte) = (x,y+1)
move ((x,y),Sul)   = (x,y-1)

-- | Função que recebe uma posição juntamente com uma orientação e uma peça e determina a próxima posição com uma orientação associada.
posicaoOrientacao :: (Posicao,Orientacao) -> Posicao -> Peca -> (Posicao,Orientacao)
posicaoOrientacao ((x,y),Este)  (b,d) (Peca Recta a)         = ((x+1,y), Este)
posicaoOrientacao ((x,y),Oeste) (b,d) (Peca Recta a)         = ((x-1,y), Oeste)
posicaoOrientacao ((x,y),Norte) (b,d) (Peca Recta a)         = ((x,y-1), Norte)
posicaoOrientacao ((x,y),Sul)   (b,d) (Peca Recta a)         = ((x,y+1), Sul)
posicaoOrientacao ((x,y),Norte) (b,d) (Peca (Curva Norte) a) = ((x+1,y), Este)
posicaoOrientacao ((x,y),Sul)   (b,d) (Peca (Curva Sul) a)   = ((x-1,y), Oeste)
posicaoOrientacao ((x,y),Este)  (b,d) (Peca (Curva Este) a)  = ((x,y+1), Sul)
posicaoOrientacao ((x,y),Oeste) (b,d) (Peca (Curva Oeste) a) = ((x,y-1), Norte)
posicaoOrientacao ((x,y),Oeste) (b,d) (Peca (Curva Norte) a) = ((x,y+1), Sul)
posicaoOrientacao ((x,y),Este)  (b,d) (Peca (Curva Sul) a)   = ((x,y-1), Norte)
posicaoOrientacao ((x,y),Norte) (b,d) (Peca (Curva Este) a)  = ((x-1,y), Oeste)
posicaoOrientacao ((x,y),Sul)   (b,d) (Peca (Curva Oeste) a) = ((x+1,y), Este)
posicaoOrientacao ((x,y),Norte) (b,d) (Peca (Rampa Norte) a) = ((x,y-1), Norte)
posicaoOrientacao ((x,y),Sul)   (b,d) (Peca (Rampa Norte) a) = ((x,y+1), Sul)
posicaoOrientacao ((x,y),Norte) (b,d) (Peca (Rampa Sul) a)   = ((x,y-1), Norte)
posicaoOrientacao ((x,y),Sul)   (b,d) (Peca (Rampa Sul) a)   = ((x,y+1), Sul)
posicaoOrientacao ((x,y),Oeste) (b,d) (Peca (Rampa Oeste) a) = ((x-1,y), Oeste)
posicaoOrientacao ((x,y),Este)  (b,d) (Peca (Rampa Oeste) a) = ((x,y-1), Este)
posicaoOrientacao ((x,y),Oeste) (b,d) (Peca (Rampa Este) a)  = ((x-1,y), Oeste)
posicaoOrientacao ((x,y),Este)  (b,d) (Peca (Rampa Este) a)  = ((x+1,y), Este)
posicaoOrientacao ((x,y),z)     (b,d) (Peca t a)             = ((b,d),z)

-- | Função que devolve uma lista com as posições das peças existentes no tabuleiro juntamente com a orientação associada.
percurso :: Mapa -> [(Posicao,Orientacao)]
percurso (Mapa p []) = []
percurso (Mapa p t) = aux p (move p) t
                    where aux :: (Posicao,Orientacao) -> Posicao -> Tabuleiro -> [(Posicao,Orientacao)]
                          aux o (x,y) [] = []
                          aux o (x,y) t  | o == ((x,y),snd o) = [((x,y),(snd o))]
                                         | otherwise          = o : aux (posicaoOrientacao o (x,y) (posicaonoTabuleiro (fst o) t)) (x,y) t

-- | Função que testa se duas peças estão ligadas com a mesma altura.
auxValidaAltura :: Peca -> Orientacao -> Orientacao -> Peca -> Bool
auxValidaAltura (Peca t a) o o1 (Peca t1 a1) | t  == Recta       && a == a1                    = True
                                             | t  == Curva Norte && a == a1                    = True
                                             | t  == Curva Sul   && a == a1                    = True
                                             | t  == Curva Este  && a == a1                    = True
                                             | t  == Curva Oeste && a == a1                    = True
                                             | t  == Curva Oeste && a == a1                    = True
                                             | t  == Rampa Norte && o == Norte  && (a+1) == a1 = True
                                             | t  == Rampa Norte && o == Sul    && a == a1     = True
                                             | t  == Rampa Oeste && o == Oeste  && (a+1) == a1 = True
                                             | t  == Rampa Oeste && o == Este   && a == a1     = True
                                             | t  == Rampa Este  && o == Este   && (a+1) == a1 = True
                                             | t  == Rampa Este  && o == Oeste  && a == a1     = True
                                             | t  == Rampa Sul   && o == Sul    && (a+1) == a1 = True
                                             | t  == Rampa Sul   && o == Norte  && a == a1     = True
                                             | t1 == Rampa Norte && o1 == Norte && a == a1     = True
                                             | t1 == Rampa Norte && o1 == Sul   && a == a1+1   = True
                                             | t1 == Rampa Oeste && o1 == Oeste && a == a1     = True
                                             | t1 == Rampa Oeste && o1 == Este  && a == a1 +1  = True
                                             | t1 == Rampa Este  && o1 == Este  && a == a1     = True
                                             | t1 == Rampa Este  && o1 == Oeste && a == a1+1   = True
                                             | t1 == Rampa Sul   && o1 == Sul   && a == a1     = True
                                             | t1 == Rampa Sul   && o1 == Norte && a == (a1+1) = True 
                                             | otherwise                                       = False

-- | Função que testa se as peças do percurso estão ligadas a peças do percurso com alturas compatíveis.
validaAltura :: Mapa -> Bool
validaAltura (Mapa m []) = True
validaAltura (Mapa m t)  = aux (percurso (Mapa m t)) (t)
             where aux :: [(Posicao,Orientacao)] -> Tabuleiro -> Bool
                   aux [x] t =True
                   aux [] t = True
                   aux (((x,y),o):((xs,ys),os):s) (h:t) = auxValidaAltura (posicaonoTabuleiro (x,y) (h:t)) o os (posicaonoTabuleiro (xs,ys) (h:t)) && aux (((xs,ys),os):s) (h:t)



-- | Função testa se a peça coincide com a posição e a orientação.
auxorientacao :: Peca -> (Posicao,Orientacao) -> Bool
auxorientacao (Peca Recta 0)         (x,Norte) = True                   
auxorientacao (Peca (Curva Oeste) 0) (x,Norte) = True                           
auxorientacao (Peca (Rampa Norte) 0) (x,Norte) = True  
auxorientacao (Peca Recta 0)         (x,Este)  = True
auxorientacao (Peca (Curva Norte) 0) (x,Este)  = True
auxorientacao (Peca (Rampa Este) 0)  (x,Este)  = True                      
auxorientacao (Peca Recta 0)         (x,Sul)   = True                         
auxorientacao (Peca (Curva Este) 0)  (x,Sul)   = True                            
auxorientacao (Peca (Rampa Sul) 0)   (x,Sul)   = True         
auxorientacao (Peca Recta 0)         (x,Oeste) = True
auxorientacao (Peca (Curva Sul) 0)   (x,Oeste) = True                             
auxorientacao (Peca (Rampa Oeste) 0) (x,Oeste) = True 

-- | Função que testa se a posição e a orientação das peças estão corretas no tabuleiro.
orientacao :: (Posicao,Orientacao) -> Tabuleiro -> Bool
orientacao p (h:t) = aux ((!!) (h:t) (fst (move (p)))) (p)
                   where aux :: [Peca] -> (Posicao,Orientacao) -> Bool
                         aux (h:t) p = auxorientacao ((!!) (h:t) (snd (fst p))) p

-- | Função que valida se a orientação inicial é compatível com a peça de partida.
orientacaoInicialFinal :: Mapa -> Bool 
orientacaoInicialFinal (Mapa p []) = False
orientacaoInicialFinal (Mapa p t) = aux p t
                      where aux :: (Posicao,Orientacao) -> Tabuleiro -> Bool
                            aux p [] = False
                            aux p (h:t) = orientacao p (h:t)



-- | Função que dado uma peça testa se essa é do tipo Lava e se tem altura 0.
alturapecaLava :: Peca -> Bool
alturapecaLava (Peca Lava 0) = True
alturapecaLava (Peca Lava a) | a > 0 = False
                             | a < 0 = False
                             | otherwise = False
alturapecaLava _ = True

-- | Função que testa se as peças do tipo Lava de uma lista de peças têm altura 0.
alturalinhaLava :: [Peca] -> Bool
alturalinhaLava [l] = alturapecaLava l
alturalinhaLava (h:t) | alturapecaLava h = alturalinhaLava t 
                      | otherwise = False

-- | Função que testa se as peças do tipo Lava de um tabuleiro têm altura 0.
alturatabuleiroLava :: Tabuleiro -> Bool
alturatabuleiroLava [] = True
alturatabuleiroLava (h:t) | alturalinhaLava h = alturatabuleiroLava t
                          | otherwise = False



-- | Função que conta o número de peças do tipo de Lava.
pecastotalLava :: Mapa -> Int
pecastotalLava (Mapa p [])    = 0     
pecastotalLava (Mapa p (h:t)) = aux (Peca Lava 0)(h) + pecastotalLava (Mapa p t) 
                    where aux ::  Peca -> [Peca] -> Int
                          aux p [] = 0
                          aux p (h:t) = if p == h  then 1 + aux p t else aux p t

-- | Função que retira as posições e as orientações repetidas.
retirarepetidos :: Eq a => [a] -> [a]
retirarepetidos [] = []
retirarepetidos (h:t) = h : retirarepetidos (aux h t)
                      where aux :: Eq a => a -> [a] -> [a]
                            aux _ [] = []
                            aux x (h:t) = if x == h then aux x t else h : aux x t

-- | Função que conta o número total de peças no tabuleiro.
pecastotalTabuleiro :: Mapa -> Int
pecastotalTabuleiro (Mapa p []) = 0 
pecastotalTabuleiro (Mapa p (h:t)) = (length h) + pecastotalTabuleiro (Mapa p t)
 
-- | Função que testa se o número total de peças no tabuleiro é igual à soma de peças do tipo Lava e sem ser do tipo Lava, recurso à função que define um percurso.
contapecas :: Mapa -> Bool
contapecas (Mapa p t) = if ((pecastotalLava (Mapa p t)) + (length (retirarepetidos (percurso (Mapa p t))))) == (pecastotalTabuleiro (Mapa p t)) then True else False



-- | Função que testa se uma lista de peças é só constituída por peças do tipo Lava e com altura 0.
linhaLava :: [Peca] -> Bool
linhaLava [Peca Lava 0] = True
linhaLava (h:t) | h == Peca Lava 0 = linhaLava t
                | otherwise = False

-- | Função que testa se a primeira e a última linha do tabuleiro são peças do tipo Lava e altura 0.
primeiraultimLinhaLava :: Tabuleiro -> Bool
primeiraultimLinhaLava (h:t) | linhaLava h = linhaLava (last t)
                             | otherwise = False

-- | Função que testa se a primeira e a última coluna do tabuleiro são peças do tipo Lava e altura 0.
primeiraultimaColunaLava :: Tabuleiro -> Bool
primeiraultimaColunaLava t = linhaLava (aux t)
                        where aux :: Tabuleiro -> [Peca]
                              aux [] = [] 
                              aux (h:t) = head h : last h : aux t
                
-- | Função que testa se o tabuleiro têm a primeira e a última linha assim como a primeira e a última coluna como uma peça do tipo Lava e altura 0.
tabuleiroLava :: Tabuleiro -> Bool
tabuleiroLava t = primeiraultimLinhaLava t && primeiraultimaColunaLava t 

-- | Função que testa se o tabuleiro tem o mesmo número de colunas em todas as linhas.
igualColunas :: Tabuleiro -> Bool
igualColunas [t] = True
igualColunas (h:t) | length h == length (head t) = igualColunas t 
                   | otherwise = False 

-- | Função que testa se o tabuleiro é rectângulo e tabuleiro de Lava.
tabuleiroRetangulo :: Tabuleiro -> Bool
tabuleiroRetangulo t = tabuleiroLava t && igualColunas t 