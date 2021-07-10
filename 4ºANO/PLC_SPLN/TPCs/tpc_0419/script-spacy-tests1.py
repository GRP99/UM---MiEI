# Importar a classe do idioma Português
from spacy.lang.pt import Portuguese
# Criar um objeto nlp
nlp = Portuguese()

texto = '''Difícil? Sim, muito. Impossível? Nada disso. O FC Porto tem 90 (ou 120) minutos para inverter uma derrota de 0x2 contra o Chelsea que conta como se no Dragão se tivesse tratado e até ao apito final nesta segunda mão em Sevilha esse tal «impossível» é palavra proibida. 
O resultado do primeiro jogo não transmite a tendência das equipas em campo e esse encontro de há uma semana mostrou-nos bem que o FC Porto pode jogar de olhos nos olhos contra os multi-milionários de Stamford Bridge. Se a derrota foi construída nos detalhes, porque não acreditar que uma vitória também o pode ser?
A equipa portuguesa conta desta vez com mais dois argumentos de peso para lutar pela reviravolta: Sérgio Oliveira e Mehdi Taremi, um deles o melhor marcador dos dragões na competição e o outro um ponta-de-lança que também já foi decisivo nesta prova e que interrompeu em Tondela uma série de jogos sem marcar. Sabendo que é imperativo concretizar bem melhor do que no primeiro jogo, são dois regressos bem-vindos.
O treinador dos portistas vê-se assim com algumas decisões importantes a tomar: se Sérgio Oliveira estiver em pleno dificilmente ficará fora do onze, mas Grujic foi uma peça importante na primeira mão na forma como condicionou o jogo natural da formação inglesa. Na frente poderá ser recuperada a dupla Marega-Taremi, já tantas vezes utilizada, ou voltar a ver-se algo de diferente (Luis Díaz de novo, talvez...?).
Do outro lado, Kanté e Pulisic parecem estar agora em pleno a nível físico - ambos chegaram a entrar na primeira mão - mas há uma grande dúvida na defesa devido à condição física de Christensen e na véspera do encontro surgiu a informação de que Mateo Kovacic não estará disponível, o que, a confirmar-se, deverá acelerar o regresso de Kanté ao onze inicial dos blues.
Jogue quem jogar, o Chelsea terá opções de luxo no onze e também no banco, ou não tivessem no primeiro jogo em Sevilha entrado nomes como Giroud, Pulisic, Kanté... enfim, jogadores de qualidade incontestável e que a qualquer momento podem resolver também esta partida, como aconteceu na primeira. Só com índices de concentração nos píncaros e sem pequenas grandes falhas individuais como as que se viram na semana passada será possível o FC Porto conseguir algo de completamente diferente no marcador.
A campanha da equipa azul e branca nesta Liga dos Campeões já muito honra o clube e o futebol português e será para mais tarde recordar (em particular a vitória na eliminatória contra a Juventus)... mas porque não continuar a sonhar com a tal nova página de história em Sevilha? Se for preciso algo mais, há aqui inspiração... e também aqui.
Atentos às desatenções : O FC Porto fraquejou duas vezes no plano individual em termos defensivos, o Chelsea aproveitou para marcar dois golos. O primeiro duelo em Sevilha mostrou que os blues podem aproveitar qualquer falha e se os dragões procurarem inverter a desvantagem com demasiada ânsia... essas falhas podem surgir. A equipa portista está avisada para essa hipótese, mas o Chelsea também.
Marcar primeiro: É quase obrigatório para o FC Porto marcar primeiro nesta terça-feira para ir a tempo de sonhar e os cinco golos já marcados nos primeiros 15 minutos em jogos desta Liga dos Campeões mostram que os dragões não tardam a acordar sob os holofotes dos milhões. O Chelsea não é uma equipa com tendência para dominar um jogo inteiro, aceitará recuar e se o FC Porto conseguir descobrir rapidamente o caminho... dá para acreditar.
'''
# Processar o texto
doc = nlp(texto)

# Imprimir o texto do documento
print(doc.text)

# O objeto nlp com uma string, o spaCy faz a toquenização do texto e cria um objeto do tipo documento
# Imprimir o texto do primeito token
print(doc[0].text)

# Imprimir uma partição
print(doc[11:14].text)

# Iterar os tokens de um documento
for token in doc:
    # Se o token é composto por algarismos numéricos
    if token.like_num:
        # Selecionar o próximo token
        next_token = doc[token.i + 1]
        # Verificar o texto do proximo token é igual a "jogo"
        if next_token.text == "jogo":
            print("Refere-se sobre o jogo:", token.text)