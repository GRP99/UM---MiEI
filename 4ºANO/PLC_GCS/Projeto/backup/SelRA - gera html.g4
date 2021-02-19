grammar SelRA;

@header {
    import java.io.*;
    import java.util.*;
    import java.text.*;
    import java.util.stream.*;
}

@members {
    public File myFile;

    class Aluno{
        String id;
        int idade;
        ArrayList<String> carateristicas;
    }

    class Recurso{
        String id;
        String tipo;
        String descricao;
        int idademin;
        int idademax;
        ArrayList<String> carateristicas;

        public String toString() {
            return "Recurso " + id + ", tipo= " + tipo + ", descricao= " + descricao + ", idade ideal=[" + idademin + "," + idademax + "], carateristicas= " + carateristicas + ";";
        }
    }

    class Conceito{
        String id;
        String descricao;
    }

    class Ensinamento{
        String idRecurso;
        String idConceito;
    }

    public void criaFicheiro(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date d = new Date();
        try{
            String namefile = dateFormat.format(d) + ".html";
            myFile = new File(namefile);
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName() + "\n");
            } else {
                System.out.println("File already exists.\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void criaEstrutura(){
        String s = "<!DOCTYPE html> \n <html> \n <head> \n <title>SelRA</title> \n <meta charset=\"utf-8\" /> \n </head> \n <body> \n";
        try{
            FileWriter fr = new FileWriter(myFile, true);
            fr.write(s);
            fr.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void escreveFicheiro(File f, String s){
        try{
            FileWriter fr = new FileWriter(f, true);
            fr.write(s);
            fr.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fechaEstrutura(){
        String s = "</body> \n </html> \n";
        try{
            FileWriter fr = new FileWriter(myFile, true);
            fr.write(s);
            fr.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

selRA
    @init{ criaFicheiro(); criaEstrutura(); } :
        dicionariocaracteristicas[myFile] HIFEN
        recursos[myFile] HIFEN
        alunos[myFile] HIFEN
        conceitos[myFile] HIFEN
        ensinamentos[myFile, $recursos.mapRecursos, $conceitos.mapConceitos] HIFEN
        questoes[myFile, $recursos.mapRecursos, $alunos.mapAlunos, $conceitos.mapConceitos, $ensinamentos.listEnsinamentos, $dicionariocaracteristicas.mapCaracteristicas] PONTO
        {   fechaEstrutura();
            try {
                java.awt.Desktop.getDesktop().browse(myFile.toURI());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        };

dicionariocaracteristicas[File f]
    returns [HashMap<String,ArrayList<String>> mapCaracteristicas]
    @init { $mapCaracteristicas = new HashMap<String,ArrayList<String>>(); } :
        lc1=linhacaracteristica[$f, $mapCaracteristicas]                    { $mapCaracteristicas = $lc1.mapCaracteristicasOut; }
        ( PONTOVIRGULA lc2=linhacaracteristica[$f, $mapCaracteristicas]     { $mapCaracteristicas = $lc2.mapCaracteristicasOut; } )* ;

linhacaracteristica[File f, HashMap<String,ArrayList<String>> mapCaracteristicasIn]
    returns [HashMap<String,ArrayList<String>> mapCaracteristicasOut]
    @init { $mapCaracteristicasOut = new HashMap<String,ArrayList<String>>(); } :
            perfil DOISPONTOS
            LPAR sinonimos[$f] RPAR                 {   if(!($mapCaracteristicasIn.containsKey($perfil.text))){
                                                            if(!($sinonimos.lista.contains($perfil.text))){
                                                                $sinonimos.lista.add(($perfil.text).toLowerCase());
                                                            }
                                                            $mapCaracteristicasIn.put(($perfil.text).toLowerCase(),$sinonimos.lista);
                                                        }
                                                        else{
                                                            String s = "<p> ERRO: Perfil " + $perfil.text + " já referido... </p>\n" ;
                                                            escreveFicheiro($f,s);
                                                        }
                                                        $mapCaracteristicasOut = $mapCaracteristicasIn;
                                                    };

perfil : STR ;

sinonimos[File f]
    returns [ArrayList<String> lista]
    @init { $lista = new ArrayList<String>(); }:
        s1=sinonimo[$f, $lista]               { $lista = $s1.listaOut; }
        ( VIRGULA s2=sinonimo[$f, $lista]     { $lista = $s2.listaOut; } )* ;

sinonimo[File f, ArrayList<String> listaIn]
    returns [ArrayList<String> listaOut]
    @init { $listaOut = new ArrayList<String>(); } :
        desc        {   if(!($listaIn.contains($desc.text))){
                            $listaIn.add(($desc.text).toLowerCase());
                        }
                        else{
                            String s = "<p> ERRO: Característica " + $desc.text + " já referida... </p>\n";
                            escreveFicheiro($f,s);
                        }
                        $listaOut = $listaIn;
                    };
        
desc : STR ;

recursos[File f]
    returns[HashMap<String,Recurso> mapRecursos]
    @init { $mapRecursos = new HashMap<String,Recurso>(); } :
        r1=recurso[$f, $mapRecursos]                   { $mapRecursos = $r1.mapRecursosOut; }
        ( PONTOVIRGULA r2=recurso[$f, $mapRecursos]    { $mapRecursos = $r2.mapRecursosOut; } )* ;

recurso[File f, HashMap<String,Recurso> mapRecursosIn]
    returns [HashMap<String,Recurso> mapRecursosOut]
    @init { $mapRecursosOut = new HashMap<String,Recurso>(); } :
        idrecurso tipo desc intervaloidade[$f,$idrecurso.text] LPAR c=carateristicas[$f] RPAR
                                                                                       {    Recurso r = new Recurso();
                                                                                            r.id = $idrecurso.text;
                                                                                            r.tipo = $tipo.text;
                                                                                            r.descricao = $desc.text;
                                                                                            r.idademin = $intervaloidade.min;
                                                                                            r.idademax = $intervaloidade.max;
                                                                                            r.carateristicas = $c.listCarateristicas;
                                                                                            if(!($mapRecursosIn.containsKey($idrecurso.text))){
                                                                                                $mapRecursosIn.put($idrecurso.text,r);
                                                                                            }
                                                                                            else{
                                                                                               String s = "<p>ERRO: Recurso " + $idrecurso.text + " já referido...</p>\n" ;
                                                                                               escreveFicheiro($f,s);
                                                                                            }
                                                                                            $mapRecursosOut = $mapRecursosIn;
                                                                                        };

tipo : STR ;

idrecurso : IDR ;

intervaloidade[File f, String id]
    returns[int max, int min]
    @init{ $max=0; $min=0; } :
        LPAR idademin HIFEN idademax RPAR       {   if($idademax.max>=$idademin.min) {
                                                        $max=$idademax.max;
                                                        $min=$idademin.min;
                                                    }
                                                    else{
                                                        String s = "<p>ERRO: Intervalo de idades incorrecto no recurso " + $id + "</p>\n";
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

carateristicas[File f]
    returns[ArrayList<String> listCarateristicas]
    @init { $listCarateristicas = new ArrayList<String>(); } :
        c1=caracteristica[$f,$listCarateristicas]               { $listCarateristicas = $c1.listCarateristicasOut; }
        ( VIRGULA c2=caracteristica[$f,$listCarateristicas]     { $listCarateristicas = $c2.listCarateristicasOut; } )* ;

caracteristica[File f, ArrayList<String> listCarateristicasIn]
    returns [ArrayList<String> listCarateristicasOut]
    @init { $listCarateristicasOut = new ArrayList<String>(); } :
        desc        {   if(!($listCarateristicasIn.contains($desc.text))){
                            $listCarateristicasIn.add(($desc.text).toLowerCase());
                        }
                        else{
                            String s = "<p> ERRO: Característica " + $desc.text + " já referida... </p>\n" ;
                            escreveFicheiro($f,s);
                        }
                        $listCarateristicasOut = $listCarateristicasIn;
                    };

alunos[File f]
    returns[HashMap<String,Aluno> mapAlunos]
    @init { $mapAlunos = new HashMap<String,Aluno>(); } :
        a1=aluno[$f, $mapAlunos]                    { $mapAlunos = $a1.mapAlunosOut; }
        ( PONTOVIRGULA a2=aluno[$f, $mapAlunos]     { $mapAlunos = $a2.mapAlunosOut; } )* ;

aluno[File f, HashMap<String,Aluno> mapAlunosIn]
    returns [HashMap<String,Aluno> mapAlunosOut]
    @init {$mapAlunosOut = new HashMap<String,Aluno>();} :
        idaluno nome_aluno idade LPAR c=carateristicas[$f] RPAR
                                                    {   Aluno a = new Aluno();
                                                        a.id = $idaluno.text;
                                                        a.idade = Integer.parseInt($idade.text);
                                                        a.carateristicas = $c.listCarateristicas;
                                                        if(!($mapAlunosIn.containsKey($idaluno.text))){
                                                            $mapAlunosIn.put($idaluno.text,a);
                                                        }
                                                        else{
                                                            String s = "<p>ERRO: Aluno " + $idaluno.text + "já referido...</p>\n";
                                                            escreveFicheiro($f,s);
                                                        }
                                                        $mapAlunosOut = $mapAlunosIn;
                                                    };

idaluno : IDA ;
nome_aluno : STR ;

conceitos[File f]
    returns[HashMap<String,Conceito> mapConceitos]
    @init { $mapConceitos = new HashMap<String,Conceito>(); } :
        c1=conceito[$f, $mapConceitos]                  { $mapConceitos= $c1.mapConceitosOut; }
        ( PONTOVIRGULA c2=conceito[$f, $mapConceitos]   { $mapConceitos = $c2.mapConceitosOut; } )* ;

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
                                        String s = "<p>ERRO: Conceito " + $idconceito.text + " já referido...</p> \n";
                                        escreveFicheiro($f,s);
                                    }
                                    $mapConceitosOut = $mapConceitosIn;
                                };

idconceito : IDC ;

ensinamentos[File f, HashMap<String,Recurso> mapRecursos, HashMap<String,Conceito> mapConceitos]
    returns[ArrayList<Ensinamento> listEnsinamentos]
    @init { $listEnsinamentos = new ArrayList<Ensinamento>(); } :
    e1=ensinamento[$f,$listEnsinamentos,$mapRecursos, $mapConceitos]                           { $listEnsinamentos = $e1.listEnsinamentosOut; }
    ( PONTOVIRGULA e2=ensinamento[$f,$listEnsinamentos,$mapRecursos, $mapConceitos]            { $listEnsinamentos = $e2.listEnsinamentosOut; } )* ;

ensinamento[File f, ArrayList<Ensinamento> listEnsinamentosIn, HashMap<String,Recurso> mapRecursos, HashMap<String,Conceito> mapConceitos]
    returns [ArrayList<Ensinamento> listEnsinamentosOut]
    @init { $listEnsinamentosOut = new ArrayList<Ensinamento>(); } :
        idrecurso
        ENSINA
        idconceito          {   if(!($mapRecursos.containsKey($idrecurso.text))){
                                    String s = "<p>ERRO: Recurso " + $idrecurso.text + " inexistente...</p>\n" ;
                                    escreveFicheiro($f,s);
                                }
                                else {
                                    if(!($mapConceitos.containsKey($idconceito.text))){
                                        String s = "<p>ERRO: Conceito " + $idconceito.text + " inexistente...</p>\n";
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
                                            String s = "<p>ERRO: Ensinamento já referido...</p>\n";
                                            escreveFicheiro($f,s);
                                        }
                                    }
                                }
                                $listEnsinamentosOut = $listEnsinamentosIn;
                            };

questoes[File f, HashMap<String,Recurso> mapRecursos, HashMap<String,Aluno> mapAlunos, HashMap<String,Conceito> mapConceitos, ArrayList<Ensinamento> listEnsinamentos, HashMap<String,ArrayList<String>> mapCaracteristicas]
    @init{ String header = "<h1 style=\"text-align:center\"> Respostas às Questões Fornecidas </h1>"; escreveFicheiro($f,header);} :
    questao[$f, $mapRecursos, $mapAlunos, $mapConceitos, $listEnsinamentos, $mapCaracteristicas]
    ( questao[$f, $mapRecursos, $mapAlunos, $mapConceitos, $listEnsinamentos, $mapCaracteristicas] )* ;

questao[File f, HashMap<String,Recurso> mapRecursos, HashMap<String,Aluno> mapAlunos, HashMap<String,Conceito> mapConceitos, ArrayList<Ensinamento> listEnsinamentos, HashMap<String,ArrayList<String>> mapCaracteristicas] :
    ENSINAR
    idconceito
    AO
    idaluno
    PONTOINTERROGACAO           {   if(!($mapConceitos.containsKey($idconceito.text))){
                                        String s = "<p>ERRO: Conceito " + $idconceito.text + " inexistente...</p>\n";
                                        escreveFicheiro($f,s);
                                    }
                                    else {
                                        if(!($mapAlunos.containsKey($idaluno.text))){
                                            String s = "<p>ERRO: Aluno " + $idaluno.text + " inexistente...</p>\n";
                                            escreveFicheiro($f,s);
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

                                            // NORMALIZAR AS CARACTERISTICAS DOS ALUNOS
                                            ArrayList<String> listCarateristicasNormalizadas = new ArrayList<String>();
                                            for(String caract : listCarateristicas){
                                                for(HashMap.Entry<String,ArrayList<String>> entry : $mapCaracteristicas.entrySet()){
                                                    ArrayList<String> sinonimos = new ArrayList<String>();
                                                    sinonimos = entry.getValue();
                                                    if(sinonimos.contains(caract)){
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
                                                ArrayList<String> listCarateristicasRecursos = new ArrayList<String>();
                                                listCarateristicasRecursos = r.carateristicas;

                                                // NORMALIZAR AS CARACTERISTICAS DOS RECURSOS
                                                ArrayList<String> listCarateristicasRecursosNormalizadas = new ArrayList<String>();
                                                for(String caract : listCarateristicasRecursos){
                                                    for(HashMap.Entry<String,ArrayList<String>> entryRecurso : $mapCaracteristicas.entrySet()){
                                                        ArrayList<String> sinonimos = new ArrayList<String>();
                                                        sinonimos = entryRecurso.getValue();
                                                        if(sinonimos.contains(caract)){
                                                            if(!(listCarateristicasRecursosNormalizadas.contains(entryRecurso.getKey()))){
                                                                listCarateristicasRecursosNormalizadas.add(entryRecurso.getKey());
                                                            }
                                                        }
                                                    }
                                                }

                                                double percentagem = 0.0;

                                                ArrayList<String> matching = new ArrayList<String>();
                                                matching = (ArrayList<String>) listCarateristicasNormalizadas.stream().filter(listCarateristicasRecursosNormalizadas::contains).collect(Collectors.toList());

                                                //VERIFICAR SE A CARACTERISTICA DE UM ALUNO ESTA PRESENTE NA LISTA DE CARACTERISTICAS
                                                if(matching.size() != 0){
                                                    percentagem = ( (double)  matching.size() / listCarateristicasRecursosNormalizadas.size() ) * 100  ;
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


                                            String questao = "<h3> Questão :  ENSINAR " + $idconceito.text + " AO " + $idaluno.text + " ? </h3> \n";
                                            escreveFicheiro($f,questao);
                                            String info = "<p> Para ensinar o conceito " + $idconceito.text + " ao aluno " + $idaluno.text + " cuja idade é " + a.idade +" que possui as seguintes caracteristicas : " + listCarateristicas.toString() + ", existe os seguintes recursos:</p>\n";
                                            escreveFicheiro($f,info);
                                            if (resposta.size() == 0){
                                                String semrecursos = "<ul> <li> Não existe recursos disponíveis ... </li> </ul> \n";
                                                escreveFicheiro($f,semrecursos);
                                            }
                                            else{
                                                for (Map.Entry<Double,HashSet<Recurso>> entry : resposta.entrySet()) {
                                                    String corresp = "<ul> <li> Com uma correspondência de " + entry.getKey() + "% ... <ul> \n";
                                                    escreveFicheiro($f,corresp);
                                                    for(Recurso r : entry.getValue()){
                                                        String resp = " <li>" + r.toString() + "</li>\n";
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