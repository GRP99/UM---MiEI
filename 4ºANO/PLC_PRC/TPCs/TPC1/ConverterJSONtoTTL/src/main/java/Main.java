import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject dataset = (JSONObject) parser.parse(new FileReader("json\\dataset.json"));

            JSONArray ucs = (JSONArray) dataset.get("ucs");
            JSONArray professores = (JSONArray) dataset.get("professores");
            JSONArray alunos = (JSONArray) dataset.get("alunos");

            File myFile = new File("uc-con.ttl");
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName() + "\n");
            } else {
                System.out.println("File already exists.\n");
            }

            FileWriter fr = new FileWriter(myFile, false);

            // Header -> define Object Properties , Data properties and Classes
            fr.write("@prefix : <http://www.di.uminho.pt/prc2021/uc#> .\n" +
                    "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n" +
                    "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n" +
                    "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n" +
                    "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n" +
                    "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" +
                    "@base <http://www.di.uminho.pt/prc2021/uc> .\n" +
                    "\n" +
                    "<http://www.di.uminho.pt/prc2021/uc> rdf:type owl:Ontology .\n" +
                    "\n" +
                    "#################################################################\n" +
                    "#    Object Properties\n" +
                    "#################################################################\n" +
                    "\n" +
                    "###  http://www.di.uminho.pt/prc2021/uc#ensina\n" +
                    ":ensina rdf:type owl:ObjectProperty ;\n" +
                    "        owl:inverseOf :éEnsinaPor ;\n" +
                    "        rdfs:domain :Professor ;\n" +
                    "        rdfs:range :UnidadeCurricular .\n" +
                    "\n" +
                    "\n" +
                    "###  http://www.di.uminho.pt/prc2021/uc#frequenta\n" +
                    ":frequenta rdf:type owl:ObjectProperty ;\n" +
                    "           owl:inverseOf :éFrequentadaPor ;\n" +
                    "           rdfs:domain :Aluno ;\n" +
                    "           rdfs:range :UnidadeCurricular .\n" +
                    "\n" +
                    "\n" +
                    "###  http://www.di.uminho.pt/prc2021/uc#éAlunoDe\n" +
                    ":éAlunoDe rdf:type owl:ObjectProperty ;\n" +
                    "          owl:inverseOf :éProfessorDe .\n" +
                    "\n" +
                    "\n" +
                    "###  http://www.di.uminho.pt/prc2021/uc#éEnsinaPor\n" +
                    ":éEnsinaPor rdf:type owl:ObjectProperty .\n" +
                    "\n" +
                    "\n" +
                    "###  http://www.di.uminho.pt/prc2021/uc#éFrequentadaPor\n" +
                    ":éFrequentadaPor rdf:type owl:ObjectProperty .\n" +
                    "\n" +
                    "\n" +
                    "###  http://www.di.uminho.pt/prc2021/uc#éProfessorDe\n" +
                    ":éProfessorDe rdf:type owl:ObjectProperty ;\n" +
                    "              owl:propertyChainAxiom ( :ensina\n" +
                    "                                       :éFrequentadaPor\n" +
                    "                                     ) .\n" +
                    "\n" +
                    "\n" +
                    "#################################################################\n" +
                    "#    Data properties\n" +
                    "#################################################################\n" +
                    "\n" +
                    "###  http://www.di.uminho.pt/prc2021/uc#anoLetivo\n" +
                    ":anoLetivo rdf:type owl:DatatypeProperty .\n" +
                    "\n" +
                    "\n" +
                    "###  http://www.di.uminho.pt/prc2021/uc#designação\n" +
                    ":designação rdf:type owl:DatatypeProperty .\n" +
                    "\n" +
                    "\n" +
                    "###  http://www.di.uminho.pt/prc2021/uc#nome\n" +
                    ":nome rdf:type owl:DatatypeProperty .\n" +
                    "\n" +
                    "\n" +
                    "#################################################################\n" +
                    "#    Classes\n" +
                    "#################################################################\n" +
                    "\n" +
                    "###  http://www.di.uminho.pt/prc2021/uc#Aluno\n" +
                    ":Aluno rdf:type owl:Class ;\n" +
                    "       rdfs:subClassOf [ rdf:type owl:Restriction ;\n" +
                    "                         owl:onProperty :frequenta ;\n" +
                    "                         owl:someValuesFrom :UnidadeCurricular\n" +
                    "                       ] .\n" +
                    "\n" +
                    "\n" +
                    "###  http://www.di.uminho.pt/prc2021/uc#Professor\n" +
                    ":Professor rdf:type owl:Class ;\n" +
                    "           rdfs:subClassOf [ rdf:type owl:Restriction ;\n" +
                    "                             owl:onProperty :ensina ;\n" +
                    "                             owl:someValuesFrom :UnidadeCurricular\n" +
                    "                           ] .\n" +
                    "\n" +
                    "\n" +
                    "###  http://www.di.uminho.pt/prc2021/uc#UnidadeCurricular\n" +
                    ":UnidadeCurricular rdf:type owl:Class .\n" +
                    "\n" +
                    "\n" +
                    "#################################################################\n" +
                    "#    Individuals\n" +
                    "#################################################################\n\n");

            // UCs
            boolean flag_ucs = true;
            StringBuilder stringBuilderUCs = new StringBuilder();
            for (Object o : ucs) {
                JSONObject uc = (JSONObject) o;
                String id = (String) uc.get("id");
                String designacao = (String) uc.get("designacao");
                String anoLetivo = (String) uc.get("anoLetivo");

                fr.write("###  http://www.di.uminho.pt/prc2021/uc#" + id + "\n" +
                        ":" + id + " rdf:type owl:NamedIndividual ,\n" +
                        "                  :UnidadeCurricular ;\n" +
                        "         :anoLetivo \"" + anoLetivo + "\" ;\n" +
                        "         :designação \"" + designacao + "\" .\n\n\n");

                if (flag_ucs) {
                    stringBuilderUCs.append(":" + id);
                    flag_ucs = false;
                } else {
                    stringBuilderUCs.append(" ,\n                     :" + id);
                }
            }
            stringBuilderUCs.append(" ;\n");

            // Professores
            for (Object o : professores) {
                JSONObject professor = (JSONObject) o;
                String id = (String) professor.get("id");

                JSONObject name = (JSONObject) professor.get("nome");

                String first = (String) name.get("primeiro");
                String last = (String) name.get("ultimo");

                String firstlast = first + " " + last;

                JSONArray ucs_prof = (JSONArray) professor.get("ensina");

                if (ucs_prof != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    boolean flag = true;
                    for (Object uc : ucs_prof) {
                        String string_uc = (String) uc;
                        if (flag) {
                            stringBuilder.append(":" + string_uc);
                            flag = false;
                        } else {
                            stringBuilder.append(" ,\n             :" + string_uc);
                        }
                    }
                    stringBuilder.append(" ;\n");
                    fr.write("###  http://www.di.uminho.pt/prc2021/uc#" + id + "\n" +
                            ":" + id + " rdf:type owl:NamedIndividual ,\n" +
                            "              :Professor ;\n" +
                            "     :ensina " + stringBuilder.toString() +
                            "     :nome \"" + firstlast + "\" .\n\n\n");
                } else {
                    fr.write("###  http://www.di.uminho.pt/prc2021/uc#" + id + "\n" +
                            ":" + id + " rdf:type owl:NamedIndividual ,\n" +
                            "              :Professor ;\n" +
                            "    :nome \"" + firstlast + "\" .\n\n\n");
                }
            }

            //Alunos
            for (Object o : alunos) {
                JSONObject student = (JSONObject) o;

                JSONObject name = (JSONObject) student.get("nome");

                String first = (String) name.get("primeiro");
                String last = (String) name.get("ultimo");

                String firstlast = first + " " + last;

                fr.write("###  http://www.di.uminho.pt/prc2021/uc#" + first.toLowerCase() + last.toLowerCase() + "\n" +
                        ":" + first.toLowerCase() + last.toLowerCase() + " rdf:type owl:NamedIndividual ;\n" +
                        "          :frequenta " + stringBuilderUCs.toString() +
                        "          :nome \"" + firstlast + "\" .\n\n\n");

            }

            fr.write("###  Generated by the Converter JSON to TTL (grp) https://github.com/GRP99/PRC2021/tree/main/TPC1");
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
