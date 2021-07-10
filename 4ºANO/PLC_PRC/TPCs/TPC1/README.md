### TPC: gerar um dataset em TTL

1. Gerar um [dataset](dataset.json) em JSON de acordo com os requisitos;
2. Criar um [conversor](ConverterJSONtoTTL/src/main/java/Main.java), em Java, de JSON para [TTL](uc-con.ttl) de acordo com a ontologia definida na aula.
3. Verificar se	o sistema consegue [inferir](uc-con-inf.ttl):
    * Quais os professores e alunos que ensinam e frequentam uma determinada unidade curricular, respectivamente;
    * Quais as unidades curriculares que um professor ensina bem como quais são os seus alunos;
    * Quais são os professores dos alunos;

Requisitos mínimos para o dataset:
- 4 UCs;
- 4 docentes;
- 200 alunos.
