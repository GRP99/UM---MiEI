import java.util.List;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.io.Serializable;
import java.io.Serializable;
import static java.lang.System.out;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Random;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class Carregar implements Serializable{

    private static List<String> readFileBuffered(String fich) throws FileNotFoundException, IOException {
        List<String> linhas = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fich));
        String linha;
        while((linha = br.readLine()) != null ) {
            linhas.add(linha);
        }
        br.close();
        return linhas;
    }

    private static String removePrefix(String s, String prefix){
        if (s != null && prefix != null && s.startsWith(prefix)){
            return s.substring(prefix.length());
        }
        return s;
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) throw new IllegalArgumentException("max must be greater than min");
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private static double getRandomNumberInRange2(double min, double max) {
        if (min >= max) throw new IllegalArgumentException("max must be greater than min");
        double random = new Random().nextDouble();
        return min + (random * (max - min));
    }

    public static void criarAdmins(){
        RedeClientes rc = SISTEMA.getRedeC();
        RedeProprietarios rp = SISTEMA.getRedeP();
        LocalDate dadmaster = LocalDate.of(1991,01,01);
        double x = 6.6;
        double y = 6.6;
        Coordenadas localmaster = new Coordenadas(x,y);
        Cliente master = new Cliente("cli@admin.pt","Khalif Malik Ibn Shaman Brown","cli","Curral de Moinas",dadmaster,localmaster);
        try{
            rc.addCliente(master);
            SISTEMA.setRedeC(rc);
        }
        catch(AtoresException e){
            System.out.println("Cliente já existe");
        }
        LocalDate dadmin = LocalDate.of(1969,12,31);
        Proprietario admin = new Proprietario("prop@admin.pt","Ming Taz Pópó","prop","Oriente",dadmin,99);
        try{
            rp.addProprietario(admin);
            SISTEMA.setRedeP(rp);
        }
        catch(AtoresException e){
            System.out.println("Proprietário já existe");
        }
        Coordenadas sos = new Coordenadas(0,0);
        int v1 = getRandomNumberInRange(75,120);
        int v2 = getRandomNumberInRange(75,120);
        int v3 = getRandomNumberInRange(75,120);
        double p1 = getRandomNumberInRange2(1,3);
        double p2 = getRandomNumberInRange2(1,3);
        double p3 = getRandomNumberInRange2(1,3);
        double c1 = getRandomNumberInRange2(1,2);
        double c2 = getRandomNumberInRange2(1,2);
        double c3 = getRandomNumberInRange2(1,2);
        int a1 = getRandomNumberInRange(200,1000);
        int a2 = getRandomNumberInRange(200,1000);
        int a3 = getRandomNumberInRange(200,1000);
        Veiculos fachada1 = new CarroGasolina("",false,"GP-99-99",v1,p1,c1,13,a1,sos);
        Veiculos fachada2 = new CarroElectrico("",false,"99-GP-99",v2,p2,c2,6,a2,sos);
        Veiculos fachada3 = new CarroHibrido("",false,"99-99-GP",v3,p3,c3,99,a3,sos);
        try{
            admin.addVeiculo(fachada1);
            admin.addVeiculo(fachada2);
            admin.addVeiculo(fachada3);
            rp.trocaProprietario(admin);
            SISTEMA.setRedeP(rp);
        }
        catch(VeiculoException e){
            System.out.println("Veiculo já existe.");
        }
    }

    public static void leitura() throws IOException, FileNotFoundException{
        double preco; 
        int tempo;
        criarAdmins();
        RedeClientes rc = SISTEMA.getRedeC();
        RedeProprietarios rp = SISTEMA.getRedeP();
        List<String> codigos = readFileBuffered("logsPOO_carregamentoInicial.bak");
        for(String c : codigos){
            if (c.startsWith("--")){}
            else{
                if (c.equals("Logs")){}
                else{
                    if (c.startsWith("NovoProp:")){
                        rc = SISTEMA.getRedeC();
                        rp = SISTEMA.getRedeP();
                        String[] partes = c.split(",");
                        partes[0] = removePrefix(partes[0],"NovoProp:");
                        int ano = getRandomNumberInRange(1950,1999);
                        int mes = getRandomNumberInRange(1,12);
                        int dia = getRandomNumberInRange(1,28);
                        LocalDate calendar = LocalDate.of(ano,mes,dia);
                        Proprietario p = new Proprietario(partes[2],partes[0],partes[1],partes[3],calendar,0);
                        try{
                            rp.addProprietario(p);
                            SISTEMA.setRedeP(rp);
                        }
                        catch(AtoresException e){
                            System.out.println("Proprietário já existe");
                        }
                    }
                    if (c.startsWith("NovoCliente:")){
                        rc = SISTEMA.getRedeC();
                        rp = SISTEMA.getRedeP();
                        String[] partes = c.split(",");
                        partes[0] = removePrefix(partes[0],"NovoCliente:");
                        double x = Double.parseDouble(partes[4]);
                        double y = Double.parseDouble(partes[5]);
                        Coordenadas local = new Coordenadas(x,y);
                        int ano = getRandomNumberInRange(1950,1999);
                        int mes = getRandomNumberInRange(1,12);
                        int dia = getRandomNumberInRange(1,28);
                        LocalDate calendar = LocalDate.of(ano,mes,dia);
                        Cliente cli = new Cliente(partes[2],partes[0],partes[1],partes[3],calendar,local);
                        try{
                            rc.addCliente(cli);
                            SISTEMA.setRedeC(rc);
                        }
                        catch(AtoresException e){
                            System.out.println("Cliente já existe");
                        }
                    }
                    if (c.startsWith("NovoCarro:")){
                        rc = SISTEMA.getRedeC();
                        rp = SISTEMA.getRedeP();
                        String[] partes = c.split(",");
                        partes[0] = removePrefix(partes[0],"NovoCarro:");
                        double x = Double.parseDouble(partes[8]);
                        double y = Double.parseDouble(partes[9]);
                        Coordenadas local = new Coordenadas(x,y);
                        if (partes[0].equals("Gasolina")){
                            CarroGasolina cg = new CarroGasolina(partes[1],true,partes[2],Integer.parseInt(partes[4]),Double.parseDouble(partes[5]),Double.parseDouble(partes[6]),0,Integer.parseInt(partes[7]),local);
                            String email = partes[3].concat("@gmail.com");
                            Proprietario aux = new Proprietario(rp.getProprietario(email));
                            try{
                                aux.addVeiculo(cg);
                                rp.trocaProprietario(aux);
                                SISTEMA.setRedeP(rp);
                            }
                            catch(VeiculoException e){
                                System.out.println("Veiculo já existe.");
                            }
                        }
                        if (partes[0].equals("Hibrido")){
                            CarroHibrido ch = new CarroHibrido(partes[1],true,partes[2],Integer.parseInt(partes[4]),Double.parseDouble(partes[5]),Double.parseDouble(partes[6]),0,Integer.parseInt(partes[7]),local);
                            String email = partes[3].concat("@gmail.com");
                            Proprietario aux = new Proprietario(rp.getProprietario(email));
                            try{
                                aux.addVeiculo(ch);
                                rp.trocaProprietario(aux);
                                SISTEMA.setRedeP(rp);
                            }
                            catch(VeiculoException e){
                                System.out.println("Veiculo já existe.");
                            }
                        }
                        if (partes[0].equals("Electrico")){
                            CarroElectrico ch = new CarroElectrico(partes[1],true,partes[2],Integer.parseInt(partes[4]),Double.parseDouble(partes[5]),Double.parseDouble(partes[6]),0,Integer.parseInt(partes[7]),local);
                            String email = partes[3].concat("@gmail.com");
                            Proprietario aux = new Proprietario(rp.getProprietario(email));
                            try{
                                aux.addVeiculo(ch);
                                rp.trocaProprietario(aux);
                                SISTEMA.setRedeP(rp);
                            }
                            catch(VeiculoException e){
                                System.out.println("Veiculo já existe.");
                            }
                        }
                    }
                    if (c.startsWith("Aluguer:")){
                        rc = SISTEMA.getRedeC();
                        rp = SISTEMA.getRedeP();
                        String[] partes = c.split(",");
                        partes[0] = removePrefix(partes[0],"Aluguer:");
                        String email = partes[0].concat("@gmail.com");
                        if (!(rc.contains(email))){}
                        else{
                            Cliente individuo = new Cliente(rc.getCliente(email));
                            Proprietario p = rp.getProprietario("prop@admin.pt");
                            Coordenadas ini = individuo.getLocal();
                            double x = Double.parseDouble(partes[1]);
                            double y = Double.parseDouble(partes[2]);
                            Coordenadas fim = new Coordenadas(x,y);
                            double dist = ini.distancia(fim);
                            int factor = getRandomNumberInRange(1,4);
                            if (partes[3].equals("Gasolina")){
                                Veiculos v = p.getVeiculo("GP-99-99");
                                v.setDescricao(partes[4]);
                                tempo = (int) (dist / v.getVelocidade()) * 60;
                                preco = dist * v.getPreco();
                                if (factor == 2) tempo = tempo + 5;
                                if (factor == 3) tempo = tempo + 10; preco = preco + 0.5;
                                if (factor == 4) tempo = tempo + 15; preco = preco + 1.0;
                                Aluguer improv = new Aluguer(email,v,LocalDate.now(),ini,fim,dist,preco,tempo);
                                p.addAluguer(improv);
                                v.setDescricao(" ");
                                v.addFacturacao(preco);
                                v.setLocal(fim);
                                v.addAluguer(improv);
                                individuo.addAluguer(improv);
                                individuo.setLocal(fim);
                                p.trocaVeiculo(v.clone());
                                rp.trocaProprietario(p);
                                rc.trocaCliente(individuo);
                                SISTEMA.setRedeP(rp);
                                SISTEMA.setRedeC(rc);
                            }
                            if (partes[3].equals("Electrico")){
                                Veiculos v = p.getVeiculo("99-GP-99");
                                v.setDescricao(partes[4]);
                                tempo = (int) (dist / v.getVelocidade()) * 60;
                                preco = dist * v.getPreco();
                                if (factor == 2) tempo = tempo + 5;
                                if (factor == 3) tempo = tempo + 10; preco = preco + 0.5;
                                if (factor == 4) tempo = tempo + 15; preco = preco + 1.0;
                                Aluguer improv = new Aluguer(email,v,LocalDate.now(),ini,fim,dist,preco,tempo);
                                p.addAluguer(improv);
                                v.setDescricao(" ");
                                v.addFacturacao(preco);
                                v.setLocal(fim);
                                v.addAluguer(improv);
                                individuo.addAluguer(improv);
                                individuo.setLocal(fim);
                                p.trocaVeiculo(v.clone());
                                rp.trocaProprietario(p);
                                rc.trocaCliente(individuo);
                                SISTEMA.setRedeP(rp);
                                SISTEMA.setRedeC(rc);
                            }
                            if (partes[3].equals("Hibrido")){
                                Veiculos v = p.getVeiculo("99-99-GP");
                                v.setDescricao(partes[4]);
                                tempo = (int) (dist / v.getVelocidade()) * 60;
                                preco = dist * v.getPreco();
                                if (factor == 2) tempo = tempo + 5;
                                if (factor == 3) tempo = tempo + 10; preco = preco + 0.5;
                                if (factor == 4) tempo = tempo + 15; preco = preco + 1.0;
                                Aluguer improv = new Aluguer(email,v,LocalDate.now(),ini,fim,dist,preco,tempo);
                                p.addAluguer(improv);
                                v.setDescricao(" ");
                                v.addFacturacao(preco);
                                v.setLocal(fim);
                                v.addAluguer(improv);
                                individuo.addAluguer(improv);
                                individuo.setLocal(fim);
                                p.trocaVeiculo(v.clone());
                                rp.trocaProprietario(p);
                                rc.trocaCliente(individuo);
                                SISTEMA.setRedeP(rp);
                                SISTEMA.setRedeC(rc);
                            }
                        }
                    }
                    if (c.startsWith("Classificar:")){
                        rc = SISTEMA.getRedeC();
                        rp = SISTEMA.getRedeP();
                        String[] partes = c.split(",");
                        partes[0] = removePrefix(partes[0],"Classificar:");
                        if (partes[0].contains("-")){
                            Map<String,Proprietario> tm = new TreeMap<String,Proprietario>();
                            tm = rp.getRede();
                            for (Proprietario p : tm.values()){
                                Map<String,Veiculos> hm = new TreeMap<String,Veiculos>();
                                hm = p.getVeiculos();
                                for(Veiculos aux: hm.values()){
                                    if (aux.getMatricula().equals(partes[1])){
                                        int valor = Integer.parseInt(partes[1]);
                                        aux.classifica(valor);
                                        hm.put(partes[0],aux);
                                        p.setVeiculos(hm);
                                        rp.trocaProprietario(p);
                                        SISTEMA.setRedeP(rp);
                                    }
                                }
                            }
                        }
                        else{
                            String email = partes[0].concat("@gmail.com");
                            if (!(rp.contains(email))){}
                            else{
                                Proprietario aux = new Proprietario(rp.getProprietario(email));
                                int valor = Integer.parseInt(partes[1]);
                                aux.classifica(valor);
                                rp.trocaProprietario(aux);
                                SISTEMA.setRedeP(rp);
                            }
                        }
                    }
                }
            }
        }

    }
}

