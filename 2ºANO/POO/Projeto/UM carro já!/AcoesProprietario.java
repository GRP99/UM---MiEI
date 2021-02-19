import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;

public class AcoesProprietario implements Serializable{
    private static RedeClientes redeC;
    private static RedeProprietarios redeP;

    public String editPass(String p, String pass){
        redeP = SISTEMA.getRedeP();
        Proprietario aux = new Proprietario(redeP.getProprietario(p));
        aux.setPass(pass);
        redeP.trocaProprietario(aux);
        SISTEMA.setRedeP(redeP);
        return ("Password alterado com sucesso.");
    }

    public String editNome(String p, String nome){
        redeP = SISTEMA.getRedeP();
        Proprietario aux = new Proprietario(redeP.getProprietario(p));
        aux.setNome(nome);
        redeP.trocaProprietario(aux);
        SISTEMA.setRedeP(redeP);
        return ("Nome alterado com sucesso.");
    }

    public String editMorada(String p, String morada){
        redeP = SISTEMA.getRedeP();
        Proprietario aux = new Proprietario(redeP.getProprietario(p));
        aux.setMorada(morada);
        redeP.trocaProprietario(aux.clone());
        SISTEMA.setRedeP(redeP.clone());
        return("Morada alterada com sucesso.");
    }

    public String editNascimento(String p, LocalDate dn){
        redeP = SISTEMA.getRedeP();
        Proprietario aux = new Proprietario(redeP.getProprietario(p));
        aux.setData(dn);
        redeP.trocaProprietario(aux);
        SISTEMA.setRedeP(redeP);
        return("Data de nascimento alterada com sucesso.");
    }

    public String verperfil(String p){
        redeP = SISTEMA.getRedeP();
        Proprietario aux = new Proprietario(redeP.getProprietario(p));      
        String dados = aux.toString();
        return dados;
    }

    public String criaGasolina(String p,String desc,String mat,int vel,double pre,int cons,int aut,Coordenadas lv){
        redeP = SISTEMA.getRedeP();
        Proprietario aux = new Proprietario(redeP.getProprietario(p));
        CarroGasolina cg = new CarroGasolina(desc,true,mat,vel,pre,cons,0,aut,lv);
        try{
            aux.addVeiculo(cg);
        }
        catch(VeiculoException e){
            return("Veiculo já existe.");
        }
        redeP.trocaProprietario(aux);
        SISTEMA.setRedeP(redeP);
        return("Veiculo registado.");
    }  

    public String criaElectrico(String p,String desc,String mat,int vel,double pre,int cons,int aut,Coordenadas lv){
        redeP = SISTEMA.getRedeP();
        Proprietario aux = new Proprietario(redeP.getProprietario(p));
        CarroElectrico ce = new CarroElectrico(desc,true,mat,vel,pre,cons,0,aut,lv);
        try{
            aux.addVeiculo(ce);
        }
        catch(VeiculoException e){
            return("Veiculo já existe.");
        }
        redeP.trocaProprietario(aux);
        SISTEMA.setRedeP(redeP);
        return("Veiculo registado.");
    }

    public String criaHibrido(String p,String desc,String mat,int vel,double pre,int cons,int aut,Coordenadas lv){
        redeP = SISTEMA.getRedeP();
        Proprietario aux = new Proprietario(redeP.getProprietario(p));       
        CarroHibrido ch = new CarroHibrido(desc,true,mat,vel,pre,cons,0,aut,lv);
        try{
            aux.addVeiculo(ch);
        }
        catch(VeiculoException e){
            return ("Veiculo já existe.");
        }
        redeP.trocaProprietario(aux);
        SISTEMA.setRedeP(redeP);
        return ("Veiculo registado.");
    }

    public Map<String,Veiculos> giveVeiculos(String p){
        redeP = SISTEMA.getRedeP();
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        Map<String,Veiculos> list = new TreeMap<String,Veiculos>();
        list = prop.getVeiculos();
        return list;
    }

    public String apagarVeiculo(String p,Veiculos v){
        redeP = SISTEMA.getRedeP();
        Proprietario aux = new Proprietario (redeP.getProprietario(p));
        aux.remVeiculo(v);
        redeP.trocaProprietario(aux);
        SISTEMA.setRedeP(redeP);
        return ("Veiculo removido com sucesso.");
    }

    public String marcaIndis(String p, String mat){
        redeP = SISTEMA.getRedeP();
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        if (prop.existeVeiculo(mat)){
            Veiculos v = prop.getVeiculo(mat);
            v.setEstado(false);
            prop.trocaVeiculo(v);
            redeP.trocaProprietario(prop);
            SISTEMA.setRedeP(redeP);
            return ("Veiculo " + v.getMatricula() + " marcado como indisponivel.");
        }
        else{
            return("Nao tem nenhum veiculo com essa matricula.");
        }
    }

    public String marcaDisp(String p,String mat){
        redeP = SISTEMA.getRedeP();
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        if (prop.existeVeiculo(mat)){
            Veiculos v = prop.getVeiculo(mat);
            v.setEstado(true);
            prop.trocaVeiculo(v);
            redeP.trocaProprietario(prop);
            SISTEMA.setRedeP(redeP);
            return ("Veiculo " + v.getMatricula() + " marcado como disponivel.");
        }
        else{
            return ("Nao tem nenhum veiculo com essa matricula.");
        }
    }

    public String abasteceG(String p, String mat, int aut){
        redeP = SISTEMA.getRedeP();
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        if (prop.existeVeiculo(mat)){
            Veiculos v = prop.getVeiculo(mat);
            if (v.getClass().getSimpleName().equals("CarroGasolina")){
                v.abastece(aut);
                prop.trocaVeiculo(v);
                redeP.trocaProprietario(prop);
                SISTEMA.setRedeP(redeP);
                return ("Foi abastecido " + aut + " ao carro gasolina " + mat);
            }
            else{
                return ("Não existe nenhum carro a gasolina com essa matricula.");
            }
        }
        else{
            return ("Não existe nenhum carro com essa matricula.");
        }
    }

    public String abasteceE(String p, String mat, int aut){
        redeP = SISTEMA.getRedeP();
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        if (prop.existeVeiculo(mat)){
            Veiculos v = prop.getVeiculo(mat);
            if (v.getClass().getSimpleName().equals("CarroElectrico")){
                v.abastece(aut);
                prop.trocaVeiculo(v);
                redeP.trocaProprietario(prop);
                SISTEMA.setRedeP(redeP);
                return ("Foi abastecido " + aut + " ao carro electrico " + mat);
            }
            else{
                return ("Não existe nenhum carro electrico com essa matricula.");
            }
        }
        else{
            return("Não existe nenhum carro com essa matricula.");
        }
    }

    public String abasteceH(String p, String mat, int aut){
        redeP = SISTEMA.getRedeP();
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        if (prop.existeVeiculo(mat)){
            Veiculos v = prop.getVeiculo(mat);
            if (v.getClass().getSimpleName().equals("CarroHibrido")){
                v.abastece(aut);
                prop.trocaVeiculo(v);
                redeP.trocaProprietario(prop);
                SISTEMA.setRedeP(redeP);
                return ("Foi abastecido " + aut + " ao carro hibrido " + mat);
            }
            else{
                return ("Não existe nenhum carro hibrido com essa matricula.");
            }
        }
        else{
            return ("Não existe nenhum carro com essa matricula.");
        }
    }

    public String alteraP(String p, String mat, double novo){
        redeP = SISTEMA.getRedeP();
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        if (prop.existeVeiculo(mat)){
            Veiculos v = prop.getVeiculo(mat);
            v.setPreco(novo);
            prop.trocaVeiculo(v);
            redeP.trocaProprietario(prop);
            SISTEMA.setRedeP(redeP);
            return ("Preco alterado com sucesso");
        }
        else{
            return ("Não existe nenhum veiculo com essa matricula.");
        }
    }

    public double verFact(String p, String mat, LocalDate di, LocalDate df){
        double f = 0;
        redeP = SISTEMA.getRedeP();
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        if (prop.existeVeiculo(mat)){
            Veiculos v = prop.getVeiculo(mat);
            f = v.getFacturado();
            TreeSet<Aluguer> aux = new TreeSet<Aluguer>(new AluguerComparator());
            aux = v.getHistorico();
            Iterator<Aluguer> it = aux.descendingIterator();
            if(aux.isEmpty()){
                return f;
            }
            else{
                while(it.hasNext()){
                    Aluguer a = it.next();
                    if (a.getData().isAfter(di) && a.getData().isBefore(df)){
                        f = f + a.getPreco();
                    }
                } 
            }
        }
        return f;
    }

    public Map<String,Cliente> givePedidos(String p){
        redeP = SISTEMA.getRedeP();
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        Map<String,Cliente> list = new HashMap<String,Cliente>();
        list = prop.getPedidos();
        return list;
    }

    public String giveInfo(String p, String mat){
        redeP = SISTEMA.getRedeP();
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        Cliente cli = prop.getPedido(mat);
        String emailC = cli.getEmail();
        Coordenadas d = new Coordenadas(cli.getDestino());
        Coordenadas c = new Coordenadas(cli.getLocal());
        Veiculos vei = prop.getVeiculo(mat);
        Coordenadas v = new Coordenadas(vei.getLocal());
        double dist = c.distancia(v);
        int tempo = (int) ((dist/4) * 60);
        StringBuilder s = new StringBuilder();
        s.append("O cliente " + emailC + " pretende alugar o veiculo " + mat + " para ir até " + d.toString() + ".");
        s.append(System.lineSeparator());
        s.append("O cliente vai demorar cerca de " + tempo + " min até chegar.");
        s.append(System.lineSeparator());
        return s.toString();
    }

    public String aceitarPedido(String p, String mat){
        StringBuilder s = new StringBuilder();
        redeC = SISTEMA.getRedeC();
        redeP = SISTEMA.getRedeP();
        Random rand = new Random();
        int rand_int = rand.nextInt(4);
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        Cliente c = new Cliente(prop.getPedido(mat));
        Veiculos vei = prop.getVeiculo(mat);
        Cliente cli = new Cliente (c);
        LocalDate data = LocalDate.now();
        double dist = vei.getLocal().clone().distancia(c.getDestino().clone());
        int temp = (int) (dist / vei.getVelocidade()) * 60;
        double preco = dist * vei.getPreco();
        if (rand_int == 1) temp = temp + 5;
        if (rand_int == 2) temp = temp + 10; preco = preco + 0.5;
        if (rand_int == 3) temp = temp + 15; preco = preco + 1.0;
        Aluguer novo = new Aluguer(cli.getEmail(),vei,data,vei.getLocal(),c.getDestino(),dist,preco,temp);
        double gasto = 0;
        if (vei.getConsumo() == 0) gasto = dist;
        else gasto = vei.getConsumo() *  dist;
        double newautonomia = vei.getLimite() -  gasto;
        if (newautonomia > 0.1*vei.getLimite()) vei.setEstado(true);
        else{
            vei.setEstado(false);
            s.append("Posteriormente tem de abastacer o veiculo.");
            s.append(System.lineSeparator());
        }
        cli.setLocal(cli.getDestino());
        cli.setDestino(new Coordenadas(0,0));
        cli.addAluguer(novo);
        novo.setFlag(true);
        vei.setLimite((int) newautonomia);
        vei.setLocal(c.getDestino());
        vei.addFacturacao(preco);
        vei.addAluguer(novo);
        prop.remPedido(c,vei);
        prop.addAluguer(novo);
        prop.trocaVeiculo(vei);
        redeP.trocaProprietario(prop);
        redeC.trocaCliente(cli);
        SISTEMA.setRedeP(redeP);
        SISTEMA.setRedeC(redeC);
        s.append("Viagem concluida!");
        return s.toString();
    }

    public String rejeitarPedido(String p, String mat){
        redeC = SISTEMA.getRedeC();
        redeP = SISTEMA.getRedeP();
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        Cliente c = new Cliente(prop.getPedido(mat));
        Veiculos v = prop.getVeiculo(mat);
        Cliente cli = new Cliente(c);
        prop.remPedido(cli,v);
        cli.setDestino(new Coordenadas(0,0));
        redeP.trocaProprietario(prop);
        redeC.trocaCliente(cli);
        SISTEMA.setRedeP(redeP);
        SISTEMA.setRedeC(redeC);
        return ("Pedido rejeitado com sucesso!");
    }

    public ArrayList<Aluguer> verviagens(String p, LocalDate di, LocalDate df, String mat){
        redeP = SISTEMA.getRedeP();
        Proprietario prop = new Proprietario(redeP.getProprietario(p));
        ArrayList<Aluguer> lista = new ArrayList<Aluguer>();
        if (prop.existeVeiculo(mat)){
            Veiculos v = prop.getVeiculo(mat);
            TreeSet<Aluguer> aux = new TreeSet<Aluguer>(new AluguerComparator());
            aux = v.getHistorico();
            Iterator<Aluguer> it = aux.descendingIterator();
            if(aux.isEmpty()){
                return lista;
            }
            else{
                while(it.hasNext()){
                    Aluguer a = it.next();
                    if (a.getData().isAfter(di) && a.getData().isBefore(df)){
                        lista.add(a.clone());
                    }
                } 
            }
            return lista;
        }
        else{
            return lista;
        }
    }

    public ArrayList<Cliente> giveTop(String p){
        redeC = SISTEMA.getRedeC();
        redeP = SISTEMA.getRedeP();
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        Map<String,Cliente> tm = new TreeMap<String,Cliente>();
        tm = redeC.getRede();
        if (tm.size() == 0){
            return lista;
        }
        else{
            for (Cliente c: tm.values()){
                if (lista.size() <= 10){
                    lista.add(c.clone());
                    Collections.sort (lista, new Comparator() {
                            public int compare(Object o1, Object o2) {
                                Cliente c1 = (Cliente) o1;
                                Cliente c2 = (Cliente) o2;
                                return c1.getHistorico().size() < c1.getHistorico().size() ? -1 : (c1.getHistorico().size() > c2.getHistorico().size() ? +1 : 0);
                            }
                        });
                }
                else {
                    if (lista.get(0).getHistorico().size() <= c.getHistorico().size()){
                        lista.remove(0);
                        lista.add(0,c.clone());
                        Collections.sort (lista, new Comparator() {
                                public int compare(Object o1, Object o2) {
                                    Cliente c1 = (Cliente) o1;
                                    Cliente c2 = (Cliente) o2;
                                    return c1.getHistorico().size() < c1.getHistorico().size() ? -1 : (c1.getHistorico().size() > c2.getHistorico().size() ? +1 : 0);
                                }
                            });
                    }
                }
            }
            return lista;
        }
    }

    private void sair(){
        System.out.println("Prima Enter para sair");
        String x = Input.lerString();
        while(x.endsWith("\n")){
            x = Input.lerString();
        }
    }
}
