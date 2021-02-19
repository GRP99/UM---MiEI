library( neuralnet ) # biblioteca de RNAs
library( hydroGOF )  # biblioteca de funções estatísticas
library( arules )    # biblioteca de análise de dados
library( leaps )     # biblioteca de preparação de dados

set.seed( 1234567890 )

setwd("C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/3ºANO/2Semestre/Sistemas de Representação de Conhecimento e Raciocínio/Fichas Praticas")

# ler dataset de um ficheiro csv
dados <- read.csv("ficha14_creditset.csv")

# mostrar a "cabeça" do dataset
head(dados)

# dividir os dados iniciais em casos para treino...
treino <- dados[1:800, ]

# ... e casos para teste:
teste <- dados[801:2000, ]

# seleção de variáveis mais significativas
funcao01 <- default10yr ~ income+age+loan+LTI
selecao01 <- regsubsets(funcao01,dados,nvmax=3)
summary(selecao01)

funcao02 <- default10yr ~ clientid+income+age+loan+LTI
selecao02 <- regsubsets(funcao02,dados,method="backward")
summary(selecao02)

# discretização de atributos
nomes <- c(1,2,3,4,5)
income <- discretize(dados$income,method = "frequency",categories = 5,labels = nomes )
dados$income <- as.numeric(income)

income <- discretize(dados$income,method = "cluster",categories = 4,labels = c(1,2,3,4) )
dados$income <- as.numeric(income)

# definição das camadas de entrada e saída da RNA
formula01 <- default10yr ~ LTI + age
formula02 <- default10yr ~ age+loan+LTI

# treinar a rede neuronal com default10yr como output
rnacredito <- neuralnet( formula01, treino, hidden = c(4), lifesign = "full", linear.output = FALSE, threshold = 0.1)
rede <- neuralnet( formula02, treino, hidden = c(3,2), threshold = 0.1)

# desenhar rede neuronal
plot(rnacredito, rep = "best")
plot(rede)

# definir variaveis de input para teste
teste.01 <- subset(teste, select = c("age","loan","LTI"))

# testar a rede com os novos casos
rede.resultados <- compute(rede, teste.01)

# imprimir resultados
resultados <- data.frame(atual = teste$default10yr, previsao = rede.resultados$net.result)

# imprimir resultados arrendondados
resultados$previsao <- round(resultados$previsao, digits=0)

# calcular o RMSE
rmse(c(teste$default10yr),c(resultados$previsao))