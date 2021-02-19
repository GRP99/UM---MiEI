grammar ComputacaoEscola;

@header {
   import java.util.*;
   import java.util.stream.*;
}

@members {
    class Aluno{
        String id;
        int idade;
        ArrayList<String> carateristicas;
    }

    class Recurso{
        String id;
        String descricao;
        int idademin;
        int idademax;
        ArrayList<String> carateristicas;

        public String toString() {
            return "Recurso " + id + ", descricao= " + descricao + ", idade ideal=[" + idademin + "," + idademax + "], carateristicas= " + carateristicas + ";";
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
}

computacaoescola:
    recursos HIFEN
    alunos HIFEN
    conceitos HIFEN
    ensinamentos[$recursos.mapRecursos,$conceitos.mapConceitos] HIFEN
    questoes[$recursos.mapRecursos, $alunos.mapAlunos, $conceitos.mapConceitos, $ensinamentos.listEnsinamentos] PONTO ;

recursos returns[HashMap<String,Recurso> mapRecursos]
    @init {$mapRecursos = new HashMap<String,Recurso>();} :
        r1=recurso[$mapRecursos]                   {$mapRecursos = $r1.mapRecursosOut;}
        ( PONTOVIRGULA r2=recurso[$mapRecursos]    {$mapRecursos = $r2.mapRecursosOut;} )* ;

recurso[HashMap<String,Recurso> mapRecursosIn]
    returns [HashMap<String,Recurso> mapRecursosOut]
    @init {$mapRecursosOut = new HashMap<String,Recurso>();} :
        idrecurso desc intervaloidade[$idrecurso.text] LPAR c=carateristicas RPAR       {   Recurso r = new Recurso();
                                                                                            r.id = $idrecurso.text;
                                                                                            r.descricao = $desc.text;
                                                                                            r.idademin = $intervaloidade.min;
                                                                                            r.idademax = $intervaloidade.max;
                                                                                            r.carateristicas = $c.listCarateristicas;
                                                                                            if(!($mapRecursosIn.containsKey($idrecurso.text))){
                                                                                                $mapRecursosIn.put($idrecurso.text,r);
                                                                                            }
                                                                                            else{
                                                                                                System.out.println("ERRO: Recurso " + $idrecurso.text + " ja referido...");
                                                                                            }
                                                                                            $mapRecursosOut = $mapRecursosIn;
                                                                                        };

idrecurso : IDR ;

desc : STR ;

intervaloidade[String id]
    returns[int max, int min]
    @init{ $max=0; $min=0; } :
        LPAR idademin HIFEN idademax RPAR       {   if($idademax.max>=$idademin.min) {
                                                        $max=$idademax.max;
                                                        $min=$idademin.min;
                                                    }
                                                    else{
                                                        System.out.println("ERRO: Intervalo de idades incorrecto no recurso " + $id );
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

carateristicas returns[ArrayList<String> listCarateristicas]
    @init {$listCarateristicas = new ArrayList<String>();} :
        c1=caracteristica[$listCarateristicas]               {$listCarateristicas = $c1.listCarateristicasOut;}
        ( VIRGULA c2=caracteristica[$listCarateristicas]     {$listCarateristicas = $c2.listCarateristicasOut;} )* ;

caracteristica[ArrayList<String> listCarateristicasIn]
    returns [ArrayList<String> listCarateristicasOut]
    @init {$listCarateristicasOut = new ArrayList<String>();} :
        desc        {   if(!($listCarateristicasIn.contains($desc.text))){
                            $listCarateristicasIn.add($desc.text);
                        }
                        else{
                            System.out.println("ERRO: Caracteristica " + $desc.text + " ja referida...");
                        }
                        $listCarateristicasOut = $listCarateristicasIn;
                    };

alunos returns[HashMap<String,Aluno> mapAlunos]
    @init {$mapAlunos = new HashMap<String,Aluno>();} :
        a1=aluno[$mapAlunos]                    {$mapAlunos = $a1.mapAlunosOut;}
        ( PONTOVIRGULA a2=aluno[$mapAlunos]     {$mapAlunos = $a2.mapAlunosOut;} )* ;

aluno[HashMap<String,Aluno> mapAlunosIn]
    returns [HashMap<String,Aluno> mapAlunosOut]
    @init {$mapAlunosOut = new HashMap<String,Aluno>();} :
        idaluno idade LPAR c=carateristicas RPAR    {   Aluno a = new Aluno();
                                                        a.id = $idaluno.text;
                                                        a.idade = Integer.parseInt($idade.text);
                                                        a.carateristicas = $c.listCarateristicas;
                                                        if(!($mapAlunosIn.containsKey($idaluno.text))){
                                                            $mapAlunosIn.put($idaluno.text,a);
                                                        }
                                                        else{
                                                            System.out.println("ERRO: Aluno " + $idaluno.text + "ja referido...");
                                                        }
                                                        $mapAlunosOut = $mapAlunosIn;
                                                    };

idaluno : IDA ;

conceitos returns[HashMap<String,Conceito> mapConceitos]
    @init {$mapConceitos = new HashMap<String,Conceito>();} :
        c1=conceito[$mapConceitos]                  {$mapConceitos= $c1.mapConceitosOut;}
        ( PONTOVIRGULA c2=conceito[$mapConceitos]   {$mapConceitos = $c2.mapConceitosOut;} )* ;

conceito[HashMap<String,Conceito> mapConceitosIn]
    returns[HashMap<String,Conceito> mapConceitosOut]
    @init {$mapConceitosOut = new HashMap<String,Conceito>();} :
        idconceito desc         {   Conceito c = new Conceito();
                                    c.id = $idconceito.text;
                                    c.descricao = $desc.text;
                                    if(!($mapConceitosIn.containsKey($idconceito.text))){
                                        $mapConceitosIn.put($idconceito.text,c);
                                    }
                                    else{
                                        System.out.println("ERRO: Conceito " + $idconceito.text + "ja referido...");
                                    }
                                    $mapConceitosOut = $mapConceitosIn;
                                };

idconceito : IDC ;

ensinamentos[HashMap<String,Recurso> mapRecursos, HashMap<String,Conceito> mapConceitos]
    returns[ArrayList<Ensinamento> listEnsinamentos]
    @init {$listEnsinamentos = new ArrayList<Ensinamento>();} :
    e1=ensinamento[$listEnsinamentos,$mapRecursos, $mapConceitos]                           {$listEnsinamentos = $e1.listEnsinamentosOut;}
    ( PONTOVIRGULA e2=ensinamento[$listEnsinamentos,$mapRecursos, $mapConceitos]            {$listEnsinamentos = $e2.listEnsinamentosOut;} )* ;

ensinamento[ArrayList<Ensinamento> listEnsinamentosIn, HashMap<String,Recurso> mapRecursos, HashMap<String,Conceito> mapConceitos]
    returns [ArrayList<Ensinamento> listEnsinamentosOut]
    @init {$listEnsinamentosOut = new ArrayList<Ensinamento>();} :
        idrecurso
        ENSINA
        idconceito          {   if(!($mapRecursos.containsKey($idrecurso.text))){
                                    System.out.println("ERRO: Recurso " + $idrecurso.text + " inexistente...");
                                }
                                else {
                                    if(!($mapConceitos.containsKey($idconceito.text))){
                                        System.out.println("ERRO: Conceito " + $idconceito.text + " inexistente...");
                                    }
                                    else{
                                        Ensinamento e = new Ensinamento();
                                        e.idRecurso = $idrecurso.text;
                                        e.idConceito = $idconceito.text;
                                        if(!($listEnsinamentosIn.contains(e))){
                                            $listEnsinamentosIn.add(e);
                                        }
                                        else{
                                            System.out.println("ERRO: Ensinamento ja referido...");
                                        }
                                    }
                                }
                                $listEnsinamentosOut = $listEnsinamentosIn;
                            };

questoes[HashMap<String,Recurso> mapRecursos, HashMap<String,Aluno> mapAlunos, HashMap<String,Conceito> mapConceitos, ArrayList<Ensinamento> listEnsinamentos] :
    questao[$mapRecursos, $mapAlunos, $mapConceitos, $listEnsinamentos]
    ( questao[$mapRecursos, $mapAlunos, $mapConceitos, $listEnsinamentos] )* ;

questao[HashMap<String,Recurso> mapRecursos, HashMap<String,Aluno> mapAlunos, HashMap<String,Conceito> mapConceitos, ArrayList<Ensinamento> listEnsinamentos] :
    ENSINAR
    idconceito
    AO
    idaluno
    PONTOINTERROGACAO           {   if(!($mapConceitos.containsKey($idconceito.text))){
                                        System.out.println("ERRO: Conceito " + $idconceito.text + " inexistente...");
                                    }
                                    else {
                                        if(!($mapAlunos.containsKey($idaluno.text))){
                                            System.out.println("ERRO: Aluno " + $idaluno.text + " inexistente...");
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

                                            // ASSOCIAR A CADA RECURSO QUE ENSINA O CONCEITO A MAIOR PERCENTAGEM ENCONTRADA
                                            HashMap<String,Double> correspondencias = new HashMap<String,Double>();

                                            // PERCORRER CADA RECURSO FILTRADO OBTER AS CARATERISTICAS DESTE
                                            for (HashMap.Entry<String,Recurso> entry : recursosfiltrados.entrySet()) {
                                                Recurso r = new Recurso();
                                                r = entry.getValue();
                                                ArrayList<String> listCarateristicasRecursos = new ArrayList<String>();
                                                listCarateristicasRecursos = r.carateristicas;

                                                double percentagem = 0.0;

                                                ArrayList<String> matching = new ArrayList<String>();
                                                matching = (ArrayList<String>) listCarateristicas.stream().filter(listCarateristicasRecursos::contains).collect(Collectors.toList());

                                                //VERIFICAR SE A CARACTERISTICA DE UM ALUNO ESTA PRESENTE NA LISTA DE CARACTERISTICAS
                                                if(matching.size() != 0){
                                                    percentagem = ( (double)  matching.size() / listCarateristicasRecursos.size() ) * 100  ;
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

                                            // ÁRVORE ORDENADA POR PERCENTAGEM QUE CONTEM UMA CONJUNTO DE RECURSOS UNICOS
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

                                            System.out.println("###########################################");
                                            System.out.println("> Para ensinar o conceito " + $idconceito.text + " ao aluno " + $idaluno.text + " cuja idade é " + a.idade +" que possui as seguintes caracteristicas : " + listCarateristicas.toString() + ", existe os seguintes recursos:");
                                            if (resposta.size() == 0){
                                                System.out.println(">>> Não existe recursos disponíveis ...");
                                            }
                                            else{
                                                for (TreeMap.Entry<Double,HashSet<Recurso>> entry : resposta.entrySet()) {
                                                    System.out.println(">>> Com uma correspondência de " + entry.getKey() + "% ...");
                                                    for(Recurso r : entry.getValue()){
                                                        System.out.println(r.toString());
                                                    }
                                                }
                                            }
                                            System.out.println("###########################################");
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

STR : '"' (~('"'))* '"' ;

WS: ('\r'? '\n' | ' ' | '\t')+ -> skip;