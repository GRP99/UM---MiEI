import java.util.Date;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Iterator;
import java.io.*;

public class HoteisInc implements Serializable {
    private static Map<String,Comparator<Hotel>> comparadores = new HashMap<>();

    public static void juntaOrdenacao(String nome, Comparator<Hotel> ordem) {
        comparadores.put(nome, ordem);
    }

    public static Comparator<Hotel> getOrdem(String nome) {
        return comparadores.get(nome);
    }

    private String nome;
    private Map<String,Hotel> hoteis;

    public HoteisInc() {
        nome = "HoteisInc";
        hoteis = new HashMap<String, Hotel>();
    }

    public HoteisInc(HoteisInc c) {
        this.nome = c.getNome();
        this.hoteis = c.getHoteis();
    }

    public HoteisInc(String nome, Map<String, Hotel> hoteis) {
        this.nome = nome; 
        this.hoteis = new HashMap<String,Hotel>();
        setHoteis(hoteis);
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome; 
    }

    private Map<String, Hotel> getHoteis() {
        return this.hoteis.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    private void setHoteis(Map<String, Hotel> hoteis) {
        this.hoteis = hoteis.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }   

    public boolean existeHotel(String cod) {
        return this.hoteis.containsKey(cod);
    }

    public int quantos() {
        return this.hoteis.size();
    }

    public int quantos(String loc) {
        return (int) this.hoteis.values().stream().filter( h -> h.getLocalidade().equals(loc)).count();                    
    }

    public int quantosT(String tipo) {
        int c = 0;
        for(Hotel h : this.hoteis.values()) {
            String cn = h.getClass().getSimpleName();
            if(cn.equals(tipo)) c++;
        }
        return c;
    }

    public Hotel getHotel(String cod) throws HotelInexistenteException {
        Hotel h;
        if(!hoteis.containsKey(cod)) throw new HotelInexistenteException(cod);
        h = hoteis.get(cod).clone();
        return h;
    }

    public void adiciona(Hotel h) {
        this.hoteis.put(h.getCodigo(), h.clone());
    }

    public List<Hotel> getHoteisAsList() {
        return hoteis.values().stream().map(Hotel::clone).collect(Collectors.toList());
    }

    public void adiciona(Set<Hotel> hs) {
        for(Hotel h : hs) {
            adiciona(h);
        }
    }

    public void mudaPara(String epoca) {
        for(Hotel h : hoteis.values()) {
            if(h instanceof HotelStandard) {
                HotelStandard h2 = (HotelStandard) h;
                if(epoca.equals("alta")) h2.setEpocaAlta(true);
                else h2.setEpocaAlta(false);
            }            
        }
    }

    public long total100() {
        long total = 0;
        for(Hotel h : hoteis.values()) {
            total += h.getNumeroQuartos() * h.getPrecoBaseQuarto();
        }
        return total;
    }

    public TreeSet<Hotel> ordenarHoteis() {
        TreeSet<Hotel> t = new TreeSet<Hotel>();
        hoteis.values().forEach(h -> {t.add(h.clone());});
        return t;
    }

    public TreeSet<Hotel> ordenarHoteis(Comparator<Hotel> c) {
        TreeSet<Hotel> t = new TreeSet<Hotel>(c);
        hoteis.values().forEach(h -> {t.add(h.clone());});
        return t;
    }

    public List<Hotel> ordenarHoteisList(Comparator<Hotel> c) {
        List<Hotel> l = new ArrayList<Hotel>();
        hoteis.values().forEach(h -> {l.add(h.clone());});
        l.sort(c);
        return l;     
    }

    public List<Hotel> ordenarHoteisListStream(Comparator<Hotel> c) {
        return this.hoteis.values().stream().map(Hotel::clone).sorted(c).collect(Collectors.toList());   
    }

    public Iterator<Hotel> ordenarHoteis(String criterio) {
        TreeSet<Hotel> r = ordenarHoteis(getOrdem(criterio));
        return r.iterator();
    }

    public List<CartaoPontos> daoPontos() {
        return hoteis.values().stream().filter(h -> h instanceof CartaoPontos).map(Hotel::clone).map(h -> (CartaoPontos) h).collect(Collectors.toList());
    }

    public HoteisInc clone() {
        return new HoteisInc(this);
    }

    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        HoteisInc hi = (HoteisInc) obj;
        return hi.getNome().equals(nome) && hi.getHoteis().equals(hoteis);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Hotel h: this.hoteis.values()){
            sb.append(h.toString());
        }
        return sb.toString();
    }

    /**
     * Método que guarda o estado de uma instância num ficheiro de texto.
     */
    public void escreveEmFicheiroTxt(String nomeFicheiro) throws FileNotFoundException {
        PrintWriter fich = new PrintWriter(nomeFicheiro);
        for(Hotel h: this.hoteis.values()){
            fich.println(h.toString());
        }
        fich.flush();
        fich.close();
    }
    
    private static Hotel csv2Hotel(String csv) {
        Hotel h = null;
        String[] atributos; 
        String tipo, codigo, nome, localidade;
        Double precoB;
        int quartos, estrelas;
        atributos = csv.split(",");
        tipo = atributos[0];
        codigo = atributos[1];
        nome = atributos[2];
        localidade = atributos[3];
        try {
            precoB = Double.parseDouble(atributos[4]);
        } catch (NumberFormatException | NullPointerException e) {return null;}
        try {
            quartos = Integer.parseInt(atributos[5]);
        }  catch (NumberFormatException | NullPointerException e) {return null;} 
        try {
            estrelas = Integer.parseInt(atributos[6]);
        }  catch (NumberFormatException | NullPointerException e) {return null;}
        switch (tipo) {
            case "Standard": boolean epocaAlta = Boolean.parseBoolean(atributos[7]);
            h = new HotelStandard(codigo, nome, localidade, precoB, epocaAlta, quartos, estrelas);
            break;
            case "Premium":  double taxaP = Double.parseDouble(atributos[7]);
            h = new HotelPremium(codigo, nome, localidade, precoB, taxaP, quartos, estrelas);
            break;
            case "Discount":  double ocupa = Double.parseDouble(atributos[7]);
            h = new HotelDiscount(codigo, nome, localidade, precoB, quartos, estrelas, ocupa);
            break;
        }
        return h;
    }
    private static List<String> lerCSV(String fich) throws FileNotFoundException, IOException {
        List<String> linhas = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fich));
        String linha;
        while((linha = br.readLine()) != null ) {
            linhas.add(linha);
        }
        br.close();
        return linhas;
    }
    /**
     * Método que lê um ficheiro de texto com linhas com informação de hotel.
     */
    public static HoteisInc importaCSV(String nome, String fich) throws FileNotFoundException, IOException {
        HoteisInc hi = new HoteisInc();
        hi.setNome(nome);
        List<String> hoteisCSV = HoteisInc.lerCSV(fich);
        hoteisCSV.forEach(s -> hi.adiciona(HoteisInc.csv2Hotel(s)));
        return hi;
    }

    public void guardaEstado(String nomeFicheiro) throws FileNotFoundException,IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this); 
        oos.flush();
        oos.close();
    }

    public static HoteisInc carregaEstado(String nomeFicheiro) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(nomeFicheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);
        HoteisInc h = (HoteisInc) ois.readObject();
        ois.close();
        return h;
    }
}