grammar SelRA;

@header {
    import java.io.*;
    import java.text.*;
    import java.util.*;
    import java.util.stream.*;
}

@members {
    public File myFile;
    public File fileError;

    class Perfil implements java.io.Serializable {
        String nome;
        ArrayList<String> carateristicas;

        public String toString() {
            return "{ \"perfil\": " + nome + ", \"carateristicas\": " + carateristicas + "}";
        }
    }

    class Recurso {
        String id;
        String tipo;
        String descricao;
        int idademin;
        int idademax;
        ArrayList<String> perfis;

        public String toString() {
            return "{ \"id\": \"" + id + "\", \"tipo\": " + tipo + ", \"descricao\": " + descricao + ", \"idade_ideal\": [" + idademin + "," + idademax + "], \"perfis\": " + perfis + "}";
        }

        public String toS() {
            return "Recurso " + id + ", Tipo= " + tipo + ", Descrição= " + descricao + ", Idade ideal=[" + idademin + "," + idademax + "], Perfis vocacionados= " + perfis + ";";
        }
    }

    class Aluno {
        String id;
        String nome;
        int idade;
        ArrayList<String> carateristicas;

        public String toString() {
            return "{ \"id\": \"" + id + "\", \"nome\": " + nome + ", \"idade\": " + idade + ", \"carateristicas\": " + carateristicas + "}";
        }
    }

    class Conceito {
        String id;
        String descricao;

        public String toString() {
            return "{ \"id\": \"" + id + "\", \"descricao\": " + descricao + "}";
        }
    }

    class Ensinamento {
        String idRecurso;
        String idConceito;

        public String toString() {
            return "{ \"recurso\": \"" + idRecurso + "\", \"conceito\": \"" + idConceito + "\"}";
        }
    }

    public void criaFicheiro() {
        try {
            String namefile = "___server___/output.html";
            myFile = new File(namefile);
            String errorfile = "___server___/error.html";
            fileError = new File(errorfile);
            if (myFile.createNewFile() && fileError.createNewFile()) {
                System.out.println("Files created: " + myFile.getName() + " and " + fileError.getName() + "\n");
            } else {
                System.out.println("File already exists.\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void escreveFicheiro(File f, String s) {
            try {
                FileWriter fr = new FileWriter(f, true);
                fr.write(s);
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void criaEstrutura() {
        String header = "<h2 style=\"text-align:center\"> Erros encontrados </h2>";
        try {
            FileWriter fr = new FileWriter(myFile, false);
            fr.write("");
            fr.close();
            FileWriter ferror = new FileWriter(fileError, false);
            ferror.write(header);
            ferror.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fechaEstrutura(){
        String s = "";
        try{
            FileWriter fr = new FileWriter(myFile, true);
            fr.write("");
            fr.close();
            FileWriter ferror = new FileWriter(fileError, true);
            ferror.write("");
            ferror.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

   class Escreve implements java.io.Serializable {
        HashMap<String, Aluno> alunos;
        HashMap<String, Recurso> recursos;
        HashMap<String, Conceito> conceitos;
        ArrayList<Ensinamento> ensinamentos;
        HashMap<String, Perfil> perfis;

        public String toString() {
            return "{ \"perfis\": " + perfis.values().toString() + ","
            + "\"recursos\": " + recursos.values().toString() + ","
            + "\n" +
            "\"conceitos\": " + conceitos.values().toString() + ","
            + "\n" +
            "\"alunos\": " + alunos.values().toString() + ","
            + "\n" +
            "\"ensinamentos\": " + ensinamentos.toString()
            + "\n }" ;
        }
    }


   public void escreve(Escreve e) {
        try {
            FileWriter fr = new FileWriter("___server___/db.json", false);
            fr.write(e.toString());
            fr.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
}

selRA
    @init{ criaFicheiro(); criaEstrutura(); } :
        perfis[fileError] HIFEN
        recursos[fileError] HIFEN
        alunos[fileError] HIFEN
        conceitos[fileError] HIFEN
        ensinamentos[fileError, $recursos.mapRecursos, $conceitos.mapConceitos] HIFEN { 
                                                                       Escreve e = new Escreve();
                                                                       e.recursos = $recursos.mapRecursos;
                                                                       e.alunos = $alunos.mapAlunos;
                                                                       e.conceitos = $conceitos.mapConceitos;
                                                                       e.ensinamentos = $ensinamentos.listEnsinamentos;
                                                                       e.perfis = $perfis.mapPerfis;
                                                                       escreve(e);
                                                                      }
        questoes[myFile, fileError, $recursos.mapRecursos, $alunos.mapAlunos, $conceitos.mapConceitos, $ensinamentos.listEnsinamentos, $perfis.mapPerfis] PONTO
        {fechaEstrutura();};

perfis[File f]
    returns [HashMap<String,Perfil> mapPerfis]
    @init { $mapPerfis = new HashMap<String,Perfil>(); } :
        p1=perfil[$f, $mapPerfis]                    { $mapPerfis = $p1.mapPerfisOut; }
        ( PONTOVIRGULA pn=perfil[$f, $mapPerfis]     { $mapPerfis = $pn.mapPerfisOut; } )* ;

perfil[File f, HashMap<String,Perfil> mapPerfisIn]
    returns [HashMap<String,Perfil> mapPerfisOut]
    @init { $mapPerfisOut = new HashMap<String,Perfil>(); } :
            desc DOISPONTOS
            LPAR carateristicas[$f] RPAR            {   Perfil p = new Perfil();
                                                        p.nome = $desc.text;
                                                        if(!($mapPerfisIn.containsKey($desc.text))){
                                                            if(!($carateristicas.listCarateristicas.contains($desc.text))){
                                                                $carateristicas.listCarateristicas.add($desc.text);
                                                            }
                                                            p.carateristicas = $carateristicas.listCarateristicas;
                                                            $mapPerfisIn.put($desc.text,p);
                                                        }
                                                        else{
                                                            String s = "<p style=\"padding-left:10px\"><b style=\"color:red;\">ERRO</b>: Perfil " + $desc.text + " já caracterizado... </p>\n" ;
                                                            escreveFicheiro($f,s);
                                                        }
                                                        $mapPerfisOut = $mapPerfisIn;
                                                    };

desc : STR ;

carateristicas[File f]
    returns[ArrayList<String> listCarateristicas]
    @init { $listCarateristicas = new ArrayList<String>(); } :
        c1=caracteristica[$f, $listCarateristicas]               { $listCarateristicas = $c1.listCarateristicasOut; }
        ( VIRGULA cn=caracteristica[$f, $listCarateristicas]     { $listCarateristicas = $cn.listCarateristicasOut; } )* ;

caracteristica[File f, ArrayList<String> listCarateristicasIn]
    returns [ArrayList<String> listCarateristicasOut]
    @init { $listCarateristicasOut = new ArrayList<String>(); } :
        desc        {   if(!($listCarateristicasIn.contains($desc.text))){
                            $listCarateristicasIn.add(($desc.text));
                        }
                        else{
                            String s = "<p style=\"padding-left:10px\"><b style=\"color:red;\">ERRO</b>: Característica " + $desc.text + " já referida... </p>\n" ;
                            escreveFicheiro($f,s);
                        }
                        $listCarateristicasOut = $listCarateristicasIn;
                    };

recursos[File f]
    returns[HashMap<String,Recurso> mapRecursos]
    @init { $mapRecursos = new HashMap<String,Recurso>(); } :
        r1=recurso[$f, $mapRecursos]                   { $mapRecursos = $r1.mapRecursosOut; }
        ( PONTOVIRGULA rn=recurso[$f, $mapRecursos]    { $mapRecursos = $rn.mapRecursosOut; } )* ;

recurso[File f, HashMap<String,Recurso> mapRecursosIn]
    returns [HashMap<String,Recurso> mapRecursosOut]
    @init { $mapRecursosOut = new HashMap<String,Recurso>(); } :
        idrecurso tipo desc intervaloidade[$f, $idrecurso.text] LPAR c=carateristicas[$f] RPAR
                                                                                       {    Recurso r = new Recurso();
                                                                                            r.id = $idrecurso.text;
                                                                                            r.tipo = $tipo.text;
                                                                                            r.descricao = $desc.text;
                                                                                            r.idademin = $intervaloidade.min;
                                                                                            r.idademax = $intervaloidade.max;
                                                                                            r.perfis = $c.listCarateristicas;
                                                                                            if(!($mapRecursosIn.containsKey($idrecurso.text))){
                                                                                                $mapRecursosIn.put($idrecurso.text,r);
                                                                                            }
                                                                                            else{
                                                                                               String s = "<p style=\"padding-left:10px\"><b style=\"color:red;\">ERRO</b>: Recurso " + $idrecurso.text + " já referido...</p>\n" ;
                                                                                               escreveFicheiro($f,s);
                                                                                            }
                                                                                            $mapRecursosOut = $mapRecursosIn;
                                                                                        };

idrecurso : IDR ;

tipo : STR;

intervaloidade[File f, String id]
    returns[int max, int min]
    @init{ $max=0; $min=0; } :
        LPAR idademin HIFEN idademax RPAR       {   if($idademax.max>=$idademin.min) {
                                                        $max = $idademax.max;
                                                        $min = $idademin.min;
                                                    }
                                                    else{
                                                        String s = "<p style=\"padding-left:10px\"><b style=\"color:red;\">ERRO</b>: Intervalo de idades incorrecto no recurso " + $id + "...</p>\n";
                                                        escreveFicheiro($f,s);
                                                    }
                                                };

idademin
    returns [int min]
    @init{ $min = 0; } :
        idade                       { $min = Integer.parseInt($idade.text); };

idademax
    returns [int max]
    @init{ $max = 0; } :
        idade                       { $max = Integer.parseInt($idade.text); };

idade : NUM ;

alunos[File f]
    returns[HashMap<String,Aluno> mapAlunos]
    @init { $mapAlunos = new HashMap<String,Aluno>(); } :
        a1=aluno[$f, $mapAlunos]                    { $mapAlunos = $a1.mapAlunosOut; }
        ( PONTOVIRGULA an=aluno[$f, $mapAlunos]     { $mapAlunos = $an.mapAlunosOut; } )* ;

aluno[File f, HashMap<String,Aluno> mapAlunosIn]
    returns [HashMap<String,Aluno> mapAlunosOut]
    @init { $mapAlunosOut = new HashMap<String,Aluno>(); } :
        idaluno nome idade LPAR c=carateristicas[$f] RPAR
                                                    {   Aluno a = new Aluno();
                                                        a.id = $idaluno.text;
                                                        a.nome = $nome.text;
                                                        a.idade = Integer.parseInt($idade.text);
                                                        a.carateristicas = $c.listCarateristicas;
                                                        if(!($mapAlunosIn.containsKey($idaluno.text))){
                                                            $mapAlunosIn.put($idaluno.text,a);
                                                        }
                                                        else{
                                                            String s = "<p style=\"padding-left:10px\"><b style=\"color:red;\">ERRO</b>: Aluno " + $idaluno.text + "já referido...</p>\n";
                                                            escreveFicheiro($f,s);
                                                        }
                                                        $mapAlunosOut = $mapAlunosIn;
                                                    };

nome : STR ;

idaluno : IDA ;

conceitos[File f]
    returns[HashMap<String,Conceito> mapConceitos]
    @init { $mapConceitos = new HashMap<String,Conceito>(); } :
        c1=conceito[$f, $mapConceitos]                  { $mapConceitos= $c1.mapConceitosOut; }
        ( PONTOVIRGULA cn=conceito[$f, $mapConceitos]   { $mapConceitos = $cn.mapConceitosOut; } )* ;

conceito[File f, HashMap<String,Conceito> mapConceitosIn]
    returns[HashMap<String,Conceito> mapConceitosOut]
    @init { $mapConceitosOut = new HashMap<String,Conceito>(); } :
        idconceito desc         {   Conceito c = new Conceito();
                                    c.id = $idconceito.text;
                                    c.descricao = $desc.text;
                                    if(!($mapConceitosIn.containsKey($idconceito.text))){
                                        $mapConceitosIn.put($idconceito.text,c);
                                    }
                                    else{
                                        String s = "<p style=\"padding-left:10px\"><b style=\"color:red;\">ERRO</b>: Conceito " + $idconceito.text + " já referido...</p> \n";
                                        escreveFicheiro($f,s);
                                    }
                                    $mapConceitosOut = $mapConceitosIn;
                                };

idconceito : IDC ;

ensinamentos[File f, HashMap<String,Recurso> mapRecursos, HashMap<String,Conceito> mapConceitos]
    returns[ArrayList<Ensinamento> listEnsinamentos]
    @init { $listEnsinamentos = new ArrayList<Ensinamento>(); } :
    e1=ensinamento[$f, $listEnsinamentos, $mapRecursos, $mapConceitos]                           { $listEnsinamentos = $e1.listEnsinamentosOut; }
    ( PONTOVIRGULA en=ensinamento[$f, $listEnsinamentos, $mapRecursos, $mapConceitos]            { $listEnsinamentos = $en.listEnsinamentosOut; } )* ;

ensinamento[File f, ArrayList<Ensinamento> listEnsinamentosIn, HashMap<String,Recurso> mapRecursos, HashMap<String,Conceito> mapConceitos]
    returns [ArrayList<Ensinamento> listEnsinamentosOut]
    @init { $listEnsinamentosOut = new ArrayList<Ensinamento>(); } :
        idrecurso
        ENSINA
        idconceito          {   if(!($mapRecursos.containsKey($idrecurso.text))){
                                    String s = "<p style=\"padding-left:10px\"><b style=\"color:red;\">ERRO</b>: Recurso " + $idrecurso.text + " inexistente...</p>\n" ;
                                    escreveFicheiro($f,s);
                                }
                                else {
                                    if(!($mapConceitos.containsKey($idconceito.text))){
                                        String s = "<p style=\"padding-left:10px\"><b style=\"color:red;\">ERRO</b>: Conceito " + $idconceito.text + " inexistente...</p>\n";
                                        escreveFicheiro($f,s);
                                    }
                                    else{
                                        Ensinamento e = new Ensinamento();
                                        e.idRecurso = $idrecurso.text;
                                        e.idConceito = $idconceito.text;
                                        if(!($listEnsinamentosIn.contains(e))){
                                            $listEnsinamentosIn.add(e);
                                        }
                                        else{
                                            String s = "<p style=\"padding-left:10px\"><b style=\"color:red;\">ERRO</b>: Ensinamento já referido...</p>\n";
                                            escreveFicheiro($f,s);
                                        }
                                    }
                                }
                                $listEnsinamentosOut = $listEnsinamentosIn;
                            };

questoes[File f, File ferro, HashMap<String,Recurso> mapRecursos, HashMap<String,Aluno> mapAlunos, HashMap<String,Conceito> mapConceitos, ArrayList<Ensinamento> listEnsinamentos, HashMap<String,Perfil> mapPerfis]
    @init{ String header = "<h2 style=\"text-align:center\"> Respostas às Questões Fornecidas </h2>"; escreveFicheiro($f,header);} :
    questao[$f, $ferro, $mapRecursos, $mapAlunos, $mapConceitos, $listEnsinamentos, $mapPerfis]
    ( questao[$f, $ferro, $mapRecursos, $mapAlunos, $mapConceitos, $listEnsinamentos, $mapPerfis] )* ;

questao[File f, File ferro, HashMap<String,Recurso> mapRecursos, HashMap<String,Aluno> mapAlunos, HashMap<String,Conceito> mapConceitos, ArrayList<Ensinamento> listEnsinamentos, HashMap<String,Perfil> mapPerfis] :
    ENSINAR
    idconceito
    AO
    idaluno
    PONTOINTERROGACAO           {   if(!($mapConceitos.containsKey($idconceito.text))){
                                        String s = "<p style=\"padding-left:10px\"><b style=\"color:red;\">ERRO</b>: Conceito " + $idconceito.text + " inexistente...</p>\n";
                                        escreveFicheiro($ferro,s);
                                    }
                                    else {
                                        if(!($mapAlunos.containsKey($idaluno.text))){
                                            String s = "<p style=\"padding-left:10px\"><b style=\"color:red;\">ERRO</b>: Aluno " + $idaluno.text + " inexistente...</p>\n";
                                            escreveFicheiro($ferro,s);
                                        }
                                        else{
                                            // GET ALUNO ESPECIFICO CM AS SUAS CARACTERISTICAS (MAPALUNOS)
                                            Aluno a = new Aluno();
                                            a = $mapAlunos.get($idaluno.text);
                                            ArrayList<String> listCarateristicas = new ArrayList<String>();
                                            listCarateristicas = a.carateristicas;

                                            // GET RECURSOS Q ENSINAM CONCEITO A ENSINAR (LISTENSINAMENTOS)
                                            HashMap<String,Recurso> recursosfiltrados = new HashMap<String,Recurso>();
                                            for(Ensinamento e : $listEnsinamentos){
                                                if(e.idConceito.equals($idconceito.text)){
                                                    Recurso r = new Recurso();
                                                    r = $mapRecursos.get(e.idRecurso);

                                                    // COMPARAR A IDADE DO ALUNO COM A IDADE IDEAL DO RECURSO
                                                    if(a.idade >= r.idademin && a.idade <= r.idademax){
                                                        recursosfiltrados.put(e.idRecurso,r);
                                                    }
                                                }
                                            }

                                            // NORMALIZAR AS CARACTERISTICAS DOS ALUNOS -> PASSAR PARA PERFIS
                                            ArrayList<String> listCarateristicasNormalizadas = new ArrayList<String>();
                                            for(String caract : listCarateristicas){
                                                for(HashMap.Entry<String,Perfil> entry : $mapPerfis.entrySet()){
                                                    ArrayList<String> perfil_caracteristicas = new ArrayList<String>();
                                                    perfil_caracteristicas = entry.getValue().carateristicas;
                                                    if(perfil_caracteristicas.contains(caract)){
                                                        if(!(listCarateristicasNormalizadas.contains(entry.getKey()))){
                                                            listCarateristicasNormalizadas.add(entry.getKey());
                                                        }
                                                    }
                                                }
                                            }

                                            // ASSOCIAR A CADA RECURSO QUE ENSINA O CONCEITO A MAIOR PERCENTAGEM ENCONTRADA
                                            HashMap<String,Double> correspondencias = new HashMap<String,Double>();

                                            // PERCORRER CADA RECURSO FILTRADO OBTER AS CARATERISTICAS DESTE
                                            for (HashMap.Entry<String,Recurso> entry : recursosfiltrados.entrySet()) {
                                                Recurso r = new Recurso();
                                                r = entry.getValue();
                                                ArrayList<String> listPerfisRecursos = new ArrayList<String>();
                                                listPerfisRecursos = r.perfis;

                                                // NORMALIZAR OS PERFIS DOS RECURSOS
                                                ArrayList<String> listPerfisRecursosNormalizadas = new ArrayList<String>();
                                                for(String caract : listPerfisRecursos){
                                                    for(HashMap.Entry<String,Perfil> entryRecurso : $mapPerfis.entrySet()){
                                                        ArrayList<String> perfil_caracteristicas = new ArrayList<String>();
                                                        perfil_caracteristicas = entryRecurso.getValue().carateristicas;
                                                        if(perfil_caracteristicas.contains(caract)){
                                                            if(!(listPerfisRecursosNormalizadas.contains(entryRecurso.getKey()))){
                                                                listPerfisRecursosNormalizadas.add(entryRecurso.getKey());
                                                            }
                                                        }
                                                    }
                                                }

                                                double percentagem = 0.0;

                                                ArrayList<String> matching = new ArrayList<String>();
                                                matching = (ArrayList<String>) listCarateristicasNormalizadas.stream().filter(listPerfisRecursosNormalizadas::contains).collect(Collectors.toList());

                                                //VERIFICAR SE A CARACTERISTICA DE UM ALUNO ESTA PRESENTE NA LISTA DE CARACTERISTICAS
                                                if(matching.size() != 0){
                                                    percentagem = ( (double)  matching.size() / listCarateristicasNormalizadas.size() ) * 100  ;
                                                }

                                                // ADICIONAR / ATUALIZAR A MAIOR PERCENTAGEM DO RECURSO
                                                if(correspondencias.containsKey(r.id)){
                                                    if(correspondencias.get(r.id) < percentagem){
                                                        correspondencias.replace(r.id,percentagem);
                                                    }
                                                }
                                                else{
                                                    correspondencias.put(r.id,percentagem);
                                                }
                                            }

                                            // ARVORE ORDENADA POR PERCENTAGEM QUE CONTEM UMA CONJUNTO DE RECURSOS UNICOS
                                            TreeMap<Double, HashSet<Recurso>> resposta = new TreeMap<Double,HashSet<Recurso>>();

                                            // ITERAR SOBRE AS CORRESPONDENCIAS DE IDRECURSO - PERCENTAGEM PARA OBTER OS RECURSOS
                                            for (HashMap.Entry<String,Double> entry : correspondencias.entrySet()) {
                                                Recurso r = new Recurso();
                                                r = recursosfiltrados.get(entry.getKey());

                                                HashSet<Recurso> listRecursos = new HashSet<Recurso>();
                                                if(resposta.containsKey(entry.getValue())){
                                                    listRecursos = resposta.get(entry.getValue());
                                                    listRecursos.add(r);
                                                    resposta.replace(entry.getValue(),listRecursos);
                                                }
                                                else{
                                                    listRecursos.add(r);
                                                    resposta.put(entry.getValue(),listRecursos);
                                                }
                                            }


                                            String questao = "<h4 style=\"padding-left:10px\"> <b>Questão:</b>  ENSINAR " + $idconceito.text + " AO " + $idaluno.text + " ? </h4> \n";
                                            escreveFicheiro($f,questao);
                                            String cc = $mapConceitos.get($idconceito.text).descricao;
                                            String aa = $mapAlunos.get($idaluno.text).nome;
                                            String info = "<p style=\"padding-left:10px\"> Para ensinar o conceito <b>" + cc + "</b>(" + $idconceito.text + ") ao aluno <b>" + aa + "</b>(" + $idaluno.text + "), cuja idade é " + a.idade +" que possui as seguintes caracteristicas : " + listCarateristicas.toString() + " sendo estas enquadradas nos seguintes perfis: " + listCarateristicasNormalizadas.toString() + ", existe os seguintes recursos: </p>\n";
                                            escreveFicheiro($f,info);
                                            if (resposta.size() == 0){
                                                String semrecursos = "<ul> <li> Não existe recursos disponíveis ... </li> </ul> \n";
                                                escreveFicheiro($f,semrecursos);
                                            }
                                            else{
                                                for (Map.Entry<Double,HashSet<Recurso>> entry : resposta.entrySet()) {
                                                    if (entry.getKey() < 40) {
                                                        String corresp = "<ul> <li> Com uma correspondência de <b style=\"color: red\"> " + entry.getKey() + "% </b> ... <ul> \n";
                                                        escreveFicheiro($f,corresp);
                                                    }
                                                    if (entry.getKey() >= 40 && entry.getKey() < 75) {
                                                        String corresp = "<ul> <li> Com uma correspondência de <b style=\"color: gold\"> " + entry.getKey() + "% </b> ... <ul> \n";
                                                        escreveFicheiro($f,corresp);
                                                    }
                                                    if (entry.getKey() >= 75) {
                                                        String corresp = "<ul> <li> Com uma correspondência de <b style=\"color: green\"> " + entry.getKey() + "% </b> ... <ul> \n";
                                                        escreveFicheiro($f,corresp);
                                                    }
                                                    //escreveFicheiro($f,corresp);
                                                    for(Recurso r : entry.getValue()){
                                                        String resp = " <li>" + r.toS() + "</li>\n";
                                                        escreveFicheiro($f,resp);
                                                    }
                                                    String fecho = "</ul> </li> </ul>";
                                                    escreveFicheiro($f,fecho);
                                                }
                                            }
                                            String hr = "<hr>";
                                            escreveFicheiro($f,hr);
                                        }
                                    }
                                };

//LEXER
AO : [aA][oO] ;

IDR : 'R'[0-9]+ ;

IDA : ('A'|'PG')[0-9]+ ;

IDC : 'C'[0-9]+ ;

NUM : [0-9]+ ;

ENSINAR : [eE][nN][sS][iI][nN][aA][rR] ;

ENSINA : [eE][nN][sS][iI][nN][aA] ;

HIFEN : '-' ;

PONTOVIRGULA : ';' ;

VIRGULA : ',' ;

PONTO : '.' ;

LPAR : '[' ;

RPAR : ']' ;

PONTOINTERROGACAO : '?';

DOISPONTOS : ':';

STR : '"' (~('"'))* '"' ;

WS: ('\r'? '\n' | ' ' | '\t')+ -> skip;