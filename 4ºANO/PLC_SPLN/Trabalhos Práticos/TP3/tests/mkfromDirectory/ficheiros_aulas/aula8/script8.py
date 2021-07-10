#!/usr/bin/python3
import spacy
from jjcli import *

nlp = spacy.load('pt_core_news_sm')

c=clfilter()

for text in c.slurp():
    doc = nlp(text)

    for a in doc.sents:
        print(a)
        print(a.ents)

        for token in a:
            if token.pos_ == "VERB":
                print(token.lemma_)

            # print(token.text, token.pos_, token.dep_,  token.lemma_)

