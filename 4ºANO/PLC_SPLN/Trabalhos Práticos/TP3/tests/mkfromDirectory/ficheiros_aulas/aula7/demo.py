import spacy
nlp = spacy.load('pt_core_news_sm')

doc = nlp('''
            Viva a piloto de testes Sofia que nos demonstra coisas tão lindas.
            Não esquecer de fazer o trabalho de casa.
            O trabalho de casa é instalar e estudar o Spacy.
            A Universidade do Minho tem como reitor Rui Vieira de Castro.
        ''')

for a in doc.sents:
    print(a.ents)

for token in doc:
    print(token.text, token.pos_, token.dep_)

