{-|
Module      : Tarefa5_2017li1g125
Description : Módulo da Tarefa 5 para LI1 17/18

Módulo para a realização da Tarefa 5 de LI1 em 2017/18.
-}
module Main where

import LI11718
import Graphics.Gloss                       
import Graphics.Gloss.Data.Picture          
import Graphics.Gloss.Interface.Pure.Game
import Tarefa3_2017li1g125
import Tarefa4_2017li1g125

{-|
Função principal usada para animar um jogo completo.
Compilar com o GHC.
-}
-- | Função principal que abre o jogo.
main :: IO ()
main = do
      inicio <- imgCarro
      joga inicio

type Estado = ((Float,Float),(Float,Float),Picture,Float,Teclas)
type Teclas = (Bool,Bool,Bool,Bool)

-- | Função que carrega a imagem do carro e define o estade inicial.
imgCarro :: IO Estado 
imgCarro = do carro <- loadBMP "carro.bmp"
              return ((1366,768),(683,384),carro,0,(False,False,False,False))

-- | Função que faz mover o carro para o lado.
moveCarro :: (Float,Float) -> Estado -> Estado
moveCarro (x,y) ((xm,ym),(xc,yc),c,t,te) = ((xm,ym),(arredonda xm (x + xc),arredonda ym (y + yc)),c,t,te)
    where arredonda l p = max 10 (min p (l-10))

-- | Função que desenha o sítio por onde o carro vai andar.
desenhaEstado :: Estado -> Picture
desenhaEstado ((xm,ym),(x,y),carro,time,t) = Pictures [borda,tabuleiro,figura,Scale 0.2 0.2 $ Text $ show $ time]
    where borda = Translate (-(xm+20)/2) (-(ym+20)/2) $ Color black (Polygon [(0,0),(0,ym + 20),(xm + 20,ym + 20),(xm + 20,0)])
          tabuleiro = Translate (-xm/2) (-ym/2) $ Color white (Polygon [(0,0),(0,ym),(xm,ym),(xm,0)])
          figura = Translate (-xm/2) (-ym/2) $ Translate x y carro 

-- | Função que faz o carro mover conforme uma determinada ação, neste caso, a ação é carregar num determinado botão.
reageEvento :: Event -> Estado -> Estado
reageEvento (EventKey (SpecialKey KeyUp)    Down _ _) (x,y,z,t,(t1,t2,t3,t4)) = moveCarro (0,5) (x,y,z,t,(True,t2,t3,t4))
reageEvento (EventKey (SpecialKey KeyDown)  Down _ _) (x,y,z,t,(t1,t2,t3,t4)) = moveCarro (0,-5) (x,y,z,t,(t1,True,t3,t4))
reageEvento (EventKey (SpecialKey KeyLeft)  Down _ _) (x,y,z,t,(t1,t2,t3,t4)) = moveCarro (-5,0) (x,y,z,t,(t1,t2,True,t4))
reageEvento (EventKey (SpecialKey KeyRight) Down _ _) (x,y,z,t,(t1,t2,t3,t4)) = moveCarro (5,0)  (x,y,z,t,(t1,t2,t3,True))
reageEvento (EventKey (SpecialKey KeyUp)    Up _ _)   (x,y,z,t,(t1,t2,t3,t4)) = moveCarro (0,0) (x,y,z,t,(False,t2,t3,t4))
reageEvento (EventKey (SpecialKey KeyDown)  Up _ _)   (x,y,z,t,(t1,t2,t3,t4)) = moveCarro (0,0) (x,y,z,t,(t1,False,t3,t4))
reageEvento (EventKey (SpecialKey KeyLeft)  Up _ _)   (x,y,z,t,(t1,t2,t3,t4)) = moveCarro (0,0) (x,y,z,t,(t1,t2,False,t4))
reageEvento (EventKey (SpecialKey KeyRight) Up _ _)   (x,y,z,t,(t1,t2,t3,t4)) = moveCarro (0,0)  (x,y,z,t,(t1,t2,t3,False))
reageEvento _ mapa = mapa 

-- | Função que altera o estado do jogo consoante um determinado período de tempo.
reageTempo :: Float -> Estado -> Estado
reageTempo t' (x,(px,py),z,t,(True,False,False,False))  = (x,(px,py+5),z,t+t',(True,False,False,False))
reageTempo t' (x,(px,py),z,t,(False,True,False,False))  = (x,(px,py-5),z,t+t',(False,True,False,False))
reageTempo t' (x,(px,py),z,t,(False,False,True,False))  = (x,(px-5,py),z,t+t',(False,False,True,False))
reageTempo t' (x,(px,py),z,t,(False,False,False,True))  = (x,(px+5,py),z,t+t',(False,False,False,True))
reageTempo t' (x,(px,py),z,t,(True,False,True,False))   = (x,(px-5,py+5),z,t+t',(True,False,True,False))
reageTempo t' (x,(px,py),z,t,(True,False,False,True))   = (x,(px+5,py+5),z,t+t',(True,False,False,True))
reageTempo t' (x,(px,py),z,t,(False,True,True,False))   = (x,(px-5,py-5),z,t+t',(False,True,True,False))
reageTempo t' (x,(px,py),z,t,(False,True,False,True))   = (x,(px+5,py-5),z,t+t',(False,True,False,True))
reageTempo t' (x,(px,py),z,t,(False,False,False,False)) = (x,(px,py),z,t+t',(False,False,False,False))
reageTempo t' (x,y,z,t,te) = (x,y,z,t+t',te)

-- | Função que cria o jogo com as funções já definidas.
joga :: Estado -> IO ()
joga inicio = play
    (InWindow "Micro Machines" (1366, 768) (0,0))  
    (greyN 0.5)                               
    200                                       
    inicio                                    
    desenhaEstado                             
    reageEvento                               
    reageTempo                                


    