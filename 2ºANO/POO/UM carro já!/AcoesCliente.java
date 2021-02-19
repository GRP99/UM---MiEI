    import java.io.Serializable;
import java.time.LocalDate;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Map;

public class AcoesCliente implements Serializable{
    private static RedeClientes redeC;
    private static RedeProprietarios redeP;

    public String editPass(String c, String pass){
        redeC = SISTEMA.getRedeC();
        Cliente aux = new Cliente(redeC.getCliente(c));
        aux.setPass(pass);
        redeC.trocaCliente(aux);
        SISTEMA.setRedeC(redeC);
        String res = "Password alterada com sucesso.";
        return res;
    }

    public String editNome(String c, String nome){
        redeC = SISTEMA.getRedeC();
        Cliente aux = new Cliente(redeC.getCliente(c));
        aux.setNome(nome);
        redeC.trocaCliente(aux);
        SISTEMA.setRedeC(redeC);
        String res = "Nome alterado com sucesso.";
        return res;
    }

    public String editMorada(String c, String morada){
        redeC = SISTEMA.getRedeC();
        Cliente aux = new Cliente(redeC.getCliente(c));
        aux.setMorada(morada);
        redeC.trocaCliente(aux);
        SISTEMA.setRedeC(redeC);
        String res = "Morada alterada com sucesso.";
        return res;
    }

    public String editNascimento(String c, LocalDate dn){
        redeC = SISTEMA.getRedeC();
        Cliente aux = new Cliente(redeC.getCliente(c));
        aux.setData(dn);
        redeC.trocaCliente(aux);
        SISTEMA.setRedeC(redeC);
        String res = "Data de nascimento alterada com sucesso.";
        return res;
    }

    public String editLocal(String c,Coordenadas l){
        redeC = SISTEMA.getRedeC();
        Cliente aux = new Cliente(redeC.getCliente(c));        
        aux.setLocal(l);
        redeC.trocaCliente(aux);
        SISTEMA.setRedeC(redeC);
        String res = "Mudou de localizacao com sucesso.";
        return res;
    }

    public String verperfil(String c){
        redeC = SISTEMA.getRedeC();
        Cliente aux = redeC.getCliente(c);        
        String dados = aux.toString();
        return dados;
    }

    public String maisproximo (String c, Coordenadas dest, String tipo){
        String val = " ";
        double dist = 13;
        double auxaux = 0;
        Veiculos v = null;
        Proprietario pact = new Proprietario();
        redeC = SISTEMA.getRedeC();
        redeP = SISTEMA.getRedeP();
        Cliente atual = new Cliente(redeC.getCliente(c));
        Coordenadas cli = new Coordenadas (atual.getLocal());
        System.out.print('\u000C');
        Map<String,Proprietario> tm = new TreeMap<String,Proprietario>();
        tm = redeP.getRede();
        ArrayList<Veiculos> teste = new ArrayList<Veiculos>();
        String tipoclasse = String.join("","Carro",tipo);
        if (tm.size() == 0){
            return ("Nenhum veiculo registado.");
        }
        else{
            for (Proprietario p : tm.values()){
                Map<String,Veiculos> hm = new TreeMap<String,Veiculos>();
                hm = p.getVeiculos();
                for(Veiculos aux: hm.values()){
                    auxaux = cli.distancia(aux.getLocal());
                    if (auxaux < dist && aux.getEstado() && aux.getClass().getSimpleName().equals(tipoclasse)){
                        v = aux.clone();
                        pact = p.clone();
                        dist = auxaux;
                        teste.add(v.clone());
                    }
                }
            }
            if (teste.size() == 0){
                return ("Não existem veiculos disponiveis perto de si num raio de 13km.");
            }
            else{
                double percurso = v.getLocal().distancia(dest);
                if (v.getLimite() < (percurso * v.getConsumo())){
                    return ("Veiculo mais proximo não tem autonomia para o seu percurso.");
                }
                pact.addPedido(atual,v);
                redeP.trocaProprietario(pact);
                atual.setDestino(dest);
                redeC.trocaCliente(atual);
                SISTEMA.setRedeP(redeP);
                SISTEMA.setRedeC(redeC);
                StringBuilder s = new StringBuilder();
                s.append("Veiculo mais proximo " + v.getMatricula());
                s.append(System.lineSeparator());
                s.append("Preco da viagem base é " + (percurso*v.getPreco()) + " este valor pode ser superior mediante o transito ou a metereologia.");
                s.append(System.lineSeparator());
                s.append("Pedido efectuado com sucesso!");
                s.append(System.lineSeparator());
                return s.toString();
            }
        }
    }

    public String maisbarato(String c, Coordenadas dest, String tipo){
        double preco = 1999;
        double precoaux = 0;
        Veiculos v = null;
        Proprietario pact = new Proprietario();
        redeC = SISTEMA.getRedeC();
        redeP = SISTEMA.getRedeP();
        Cliente atual = new Cliente(redeC.getCliente(c));
        Coordenadas cli = new Coordenadas(atual.getLocal());
        System.out.print('\u000C');
        Map<String,Proprietario> tm = new TreeMap<String,Proprietario>();
        tm = redeP.getRede();
        ArrayList<Veiculos> teste = new ArrayList<Veiculos>();
        String tipoclasse = String.join("","Carro",tipo);
        if (tm.size() == 0){
            return ("Nenhum veiculo registado.");
        }
        else{
            for (Proprietario p : tm.values()){
                Map<String,Veiculos> hm = new TreeMap<String,Veiculos>();
                hm = p.getVeiculos();
                for(Veiculos aux: hm.values()){
                    precoaux = aux.getPreco();
                    if (precoaux < preco && aux.getEstado() && aux.getClass().getSimpleName().equals(tipoclasse)){
                        v = aux.clone();
                        pact = p.clone();
                        preco = precoaux;
                        teste.add(v.clone());
                    }
                }
            }
            if (teste.size() == 0){
                return("Não existem veiculos disponiveis mais baratos do que 1999 euros por km.");
            }
            else{            
                double percurso = v.getLocal().distancia(dest);
                if (v.getLimite() < (percurso * v.getConsumo())){
                    return("Veiculo mais barato não tem autonomia para o seu percurso.");
                }
                pact.addPedido(atual,v);
                redeP.trocaProprietario(pact);
                atual.setDestino(dest);
                redeC.trocaCliente(atual);
                SISTEMA.setRedeC(redeC);
                SISTEMA.setRedeP(redeP);
                StringBuilder s = new StringBuilder();
                s.append("Veiculo mais barato " + v.getMatricula());
                s.append(System.lineSeparator());
                s.append("Preco da viagem base é " + (percurso*v.getPreco()) + " este valor pode ser superior mediante o transito ou a metereologia.");
                s.append(System.lineSeparator());
                s.append("Pedido efectuado com sucesso!");
                s.append(System.lineSeparator());
                return s.toString();
            }
        }
    }

    public String maisbarato2(String c, Coordenadas dest, double maxdis, String tipo){
        double auxaux = 0;
        double precoaux = 0;
        double preco = 1999;
        Veiculos v = null;
        Proprietario pact = new Proprietario();
        redeC = SISTEMA.getRedeC();
        redeP = SISTEMA.getRedeP();
        System.out.print('\u000C');
        Cliente atual = new Cliente(redeC.getCliente(c));
        Coordenadas cli = new Coordenadas (atual.getLocal());
        Map<String,Proprietario> tm = new TreeMap<String,Proprietario>();
        tm = redeP.getRede();
        ArrayList<Veiculos> teste = new ArrayList<Veiculos>();
        String tipoclasse = String.join("","Carro",tipo);
        if (tm.size() == 0){
            return ("Nenhum veiculo registado.");
        }
        else{
            for (Proprietario p : tm.values()){
                Map<String,Veiculos> hm = new TreeMap<String,Veiculos>();
                hm = p.getVeiculos();
                for(Veiculos aux: hm.values()){
                    auxaux = cli.distancia(aux.getLocal());
                    precoaux = aux.getPreco();
                    if (auxaux < maxdis && precoaux < preco && aux.getEstado() && aux.getClass().getSimpleName().equals(tipoclasse)){
                        v = aux.clone();
                        pact = p.clone();
                        preco = precoaux;
                        teste.add(v.clone());
                    }
                }
            }
            if (teste.size()==0){
                return ("Não existem veiculos disponiveis mais baratos do que 1999 euros por km num raio de " + maxdis);
            }
            else{
                double percurso = v.getLocal().distancia(dest);
                if (v.getLimite() < (percurso * v.getConsumo())){
                    return("Veiculo mais barato na distancia que esta disposto a percorrer não tem autonomia para o seu percurso.");
                }
                pact.addPedido(atual,v);
                redeP.trocaProprietario(pact);
                atual.setDestino(dest);
                redeC.trocaCliente(atual);
                SISTEMA.setRedeC(redeC);
                SISTEMA.setRedeP(redeP);
                StringBuilder s = new StringBuilder();
                s.append("Veiculo mais barato na distancia que esta disposto a percorrer " + v.getMatricula());
                s.append(System.lineSeparator());
                s.append("Preco da viagem base é " + (percurso*v.getPreco()) + " este valor pode ser superior mediante o transito ou a metereologia.");
                s.append(System.lineSeparator());
                s.append("Pedido efectuado com sucesso!");
                s.append(System.lineSeparator());
                return s.toString();
            }
        }
    }

    public String especifico(String c, Coordenadas dest, String mat, String tipo){
        Veiculos v = null;
        Proprietario pact = new Proprietario();
        redeC = SISTEMA.getRedeC();
        redeP = SISTEMA.getRedeP();
        Cliente atual = new Cliente(redeC.getCliente(c));
        Coordenadas cli = atual.getLocal();
        System.out.print('\u000C');
        Map<String,Proprietario> tm = new TreeMap<String,Proprietario>();
        tm = redeP.getRede();
        ArrayList<Veiculos> teste = new ArrayList<Veiculos>();
        String tipoclasse = String.join("","Carro",tipo);
        if (tm.size() == 0){
            return("Nenhum veiculo registado.");
        }
        else{
            for (Proprietario p : tm.values()){
                Map<String,Veiculos> hm = new TreeMap<String,Veiculos>();
                hm = p.getVeiculos();
                for (Veiculos aux: hm.values()){
                    if (aux.getMatricula().equals(mat) && aux.getEstado() && aux.getClass().getSimpleName().equals(tipoclasse)){
                        v = aux.clone();
                        pact = p.clone();
                        teste.add(v.clone());
                        break;
                    }
                }
            }
            if (teste.size()==0){
                return ("Não existe nenhum veiculo disponivel com essa matricula.");
            }
            else{
                double percurso = v.getLocal().distancia(dest);
                if (v.getLimite() < (percurso * v.getConsumo())){
                    return("Esse veiculo especifico não tem autonomia para o seu percurso.");
                }
                pact.addPedido(atual,v);
                redeP.trocaProprietario(pact);
                atual.setDestino(dest);
                redeC.trocaCliente(atual);
                SISTEMA.setRedeC(redeC);
                SISTEMA.setRedeP(redeP); 
                StringBuilder s = new StringBuilder();
                s.append("Veiculo especifico " + v.getMatricula());
                s.append(System.lineSeparator());
                s.append("Preco da viagem base é " + (percurso*v.getPreco()) + " este valor pode ser superior mediante o transito ou a metereologia.");
                s.append(System.lineSeparator());
                s.append("Pedido efectuado com sucesso!");
                s.append(System.lineSeparator());
                return s.toString();
            }
        }
    }

    public String autonomia(String c, Coordenadas dest, int aut, String tipo){
        Veiculos v = null;
        Proprietario pact = new Proprietario();
        redeC = SISTEMA.getRedeC();
        redeP = SISTEMA.getRedeP();
        Cliente atual = new Cliente(redeC.getCliente(c));
        Coordenadas cli = atual.getLocal();
        System.out.print('\u000C');
        Map<String,Proprietario> tm = new TreeMap<String,Proprietario>();
        tm = redeP.getRede();
        ArrayList<Veiculos> teste = new ArrayList<Veiculos>();
        String tipoclasse = String.join("","Carro",tipo);
        if(tm.size() == 0){
            return (" Nenhum veiculo registado. ");
        }
        else{
            for (Proprietario p : tm.values()){
                Map<String,Veiculos> hm = new TreeMap<String,Veiculos>();
                hm = p.getVeiculos();
                for (Veiculos aux: hm.values()){
                    if (aux.getLimite() == aut && aux.getEstado() && aux.getClass().getSimpleName().equals(tipoclasse)){
                        v = aux.clone();
                        pact = p.clone();
                        teste.add(v.clone());
                    }
                }
            }
            if (teste.size()==0){
                return("Não existe nenhum veiculo disponivel com essa autonomia.");
            }
            else{
                double percurso = v.getLocal().distancia(dest);
                if (v.getLimite() < (percurso * v.getConsumo())){
                    return ("Autonomia indicada não é suficiente para o seu percurso.");
                }
                pact.addPedido(atual,v);
                redeP.trocaProprietario(pact);
                atual.setDestino(dest);
                redeC.trocaCliente(atual);
                SISTEMA.setRedeC(redeC);
                SISTEMA.setRedeP(redeP);
                StringBuilder s = new StringBuilder();
                s.append("Veiculo com uma autonomia indicada " + v.getMatricula());
                s.append(System.lineSeparator());
                s.append("Preco da viagem base é " + (percurso*v.getPreco()) + " este valor pode ser superior mediante o transito ou a metereologia.");
                s.append(System.lineSeparator());
                s.append("Pedido efectuado com sucesso!");
                return s.toString();
            }
        }
    }

    public String classifica(String c, String mat, LocalDate d, Coordenadas i, Coordenadas f, int cv, int cp){
        boolean flag = true;
        Veiculos v = null;
        Proprietario dono = new Proprietario();
        Aluguer procurado = new Aluguer();
        redeC = SISTEMA.getRedeC();
        redeP = SISTEMA.getRedeP();
        Cliente user = new Cliente(redeC.getCliente(c));
        TreeSet<Aluguer> histUser = new TreeSet<Aluguer>(new AluguerComparator());
        histUser = user.getHistorico();
        Iterator<Aluguer> it = histUser.descendingIterator();
        Map<String,Proprietario> tm = new TreeMap<String,Proprietario>();
        tm = redeP.getRede();
        ArrayList<Veiculos> teste = new ArrayList<Veiculos>();
        if (histUser.isEmpty()){
            return ("Nao tem nenhuma viagem realizada.");
        }
        else{
            for (Proprietario p : tm.values()){
                if (p.existeVeiculo(mat)){
                    v = p.getVeiculo(mat).clone();
                    dono = p.clone();
                    teste.add(v.clone());
                    break;
                } 
            }
            if (teste.size() == 0){
                return ("A matricula do veiculo que indicou nao existe.");
            }
            else{
                Aluguer aux = new Aluguer(c,v,d,i,f,0.0,0.0,0);
                while(it.hasNext() && flag){
                    Aluguer a = it.next();
                    if (a.equals(aux) && (!(a.getFlag()))){
                        flag = false;
                        procurado = new Aluguer(a);
                    }
                }
                if (flag){
                    return ("Não existe nenhum aluguer com essas caracteristicas.");
                }
                else{
                    histUser.remove(procurado);
                    procurado.setFlag(true);
                    user.addAluguer(procurado.clone());
                    redeC.trocaCliente(user);
                    dono.classifica(cp);
                    v.classifica(cv);
                    dono.trocaVeiculo(v);
                    redeP.trocaProprietario(dono);
                    SISTEMA.setRedeC(redeC);
                    SISTEMA.setRedeP(redeP);
                    return ("Classificacao foi atribuida com sucesso!");
                }
            }
        }
    }

    public ArrayList<Aluguer> verviagens(String c, LocalDate di, LocalDate df){
        redeC = SISTEMA.getRedeC();
        TreeSet<Aluguer> aux = new TreeSet<Aluguer>(new AluguerComparator());
        aux = redeC.getCliente(c).getHistorico();
        ArrayList<Aluguer> lista = new ArrayList<Aluguer>();
        Iterator<Aluguer> it = aux.descendingIterator();
        if(aux.isEmpty()){
            return lista;
        }
        else{
            while(it.hasNext()){
                Aluguer a = it.next();
                if (a.getData().isAfter(di) && a.getData().isBefore(df) && a.getFlag()){
                    lista.add(a.clone());
                }
            } 
        }
        return lista;
    }

    private void sair(){
        System.out.println("Prima Enter para sair");
        String x = Input.lerString();
        while(x.endsWith("\n")){
            x = Input.lerString();
        }
    }
}