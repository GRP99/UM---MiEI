library(neuralnet)

setwd("C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/3ºANO/2Semestre/Sistemas de Representação de Conhecimento e Raciocínio/Fichas Praticas")

# ler dataset de treino de um CSV, separado por "," e casa decimal representada por "."
trainset <- read.csv("ficha13_normalizado.csv",header=TRUE,sep=",",dec=".")

head(trainset)

# definição da formula de treino
formulaRNA <- Avaliacao ~ Vencimento+Habitacao+Automovel+Cartao

# (1) treinar rede neuronal chamada "creditnet" com 5 nodos intermédios
creditnet <- neuralnet(formulaRNA, trainset, hidden = c(5), threshold = 0.1)

# testar a RNA com os casos usados para treino
teste <- subset(trainset, select = c("Vencimento","Habitacao","Automovel","Cartao"))
creditnet.results <- compute(creditnet, teste)
print(round(creditnet.results$net.result,digits=0))

# (2) treinar rede neuronal chamada "creditnet" com 6,4,2 nodos intermédios
creditnet <- neuralnet(formulaRNA, trainset, hidden = c(6,4,2), lifesign = "full", threshold = 0.01)

# imprimir resultados
print(creditnet)
print(creditnet$call)

creditnet$model.list

# desenhar rede neuronal
plot(creditnet)
plot(creditnet$covariate[,1],creditnet$response,type="l")
plot(creditnet$covariate[,1],creditnet$response,type="o")
plot(creditnet$covariate[,1],creditnet$response,type="p")

# criar dataframe para os casos de teste
test <- data.frame(Vencimento=0.4,Habitacao=0.2,Automovel=0.40,Cartao=0.1)
test[2,] <- data.frame(Vencimento=0.7,Habitacao=0.4,Automovel=0.55,Cartao=0.1)

# testar novos casos na rede neuronal
creditnet.results <- compute(creditnet, test)

# imprimir o resultado final (uso da função round como auxiliar para arredondar o resultado final)
print(round(creditnet.results$net.result))
print(round(creditnet.results$net.result,digits=1))

# testar a RNA com os casos usados para treino
teste <- subset(trainset, select = c("Vencimento","Habitacao","Automovel","Cartao"))
creditnet.results <- compute(creditnet, teste)
print(round(creditnet.results$net.result,digits=0))