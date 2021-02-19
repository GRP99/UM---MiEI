import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.IOException;

public class GereVendasController implements InterfGereVendasController,Serializable{
    private InterfGereVendasModel model; 
    private InterfGereVendasView view;

    public GereVendasController(){
        this.model = new GereVendasModel();
        this.view = new GereVendasView();
    }

    public InterfGereVendasModel getModel(){
        return this.model;
    }

    public InterfGereVendasView getView(){
        return this.view;
    }

    public void setView(InterfGereVendasView gvv){
        this.view = gvv;
    }

    public void setModel(InterfGereVendasModel gvm){
        this.model = gvm;
    }

    public void startController() throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente , ProdutoInexistente{
        Queries query = new Queries();
        engine(query);
    }
    
    public void engine(Queries query) throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente , ProdutoInexistente{
         boolean val1=true, val2=true, val3=true, val4=true;
        int opcao1=0, opcao2=0;
        List<Object> opcao3 = new ArrayList<>();
        List<Object> opcao4 = new ArrayList<>();
        while(val1){
            opcao1 = view.menuInicial();
            if (opcao1 == 0) val1 = false;
            if (opcao1 == 1){
                InterfGereVendasModel newmodel = new GereVendasModel();
                newmodel.createData();
                model = newmodel;
            }
            if (opcao1 == 2){
                while(val2){
                    opcao2 = view.menuEstatistico();
                    if (opcao2 == 1) query.queryEstatistica(model);
                    if (opcao2 == 0) val2=false;
                }
                val2=true;
            }
            if (opcao1 == 3){
                while(val3){
                    opcao3 = view.menuInteractivo();
                    if ((Integer) opcao3.get(0) == 0) val3=false;
                    if ((Integer) opcao3.get(0) == 1) query.query1(model);
                    if ((Integer) opcao3.get(0) == 2) query.query2(model,(Integer)opcao3.get(1));
                    if ((Integer) opcao3.get(0) == 3) query.query3(model, String.valueOf(opcao3.get(1)));
                    if ((Integer) opcao3.get(0) == 4) query.query4(model, String.valueOf(opcao3.get(1)));
                    if ((Integer) opcao3.get(0) == 5) query.query5(model, String.valueOf(opcao3.get(1)));
                    if ((Integer) opcao3.get(0) == 6) query.query6(model, (Integer)opcao3.get(1));
                    if ((Integer) opcao3.get(0) == 7) query.query7(model);
                    if ((Integer) opcao3.get(0) == 8) query.query8(model,(Integer)opcao3.get(1));
                    if ((Integer) opcao3.get(0) == 9) query.query9(model,String.valueOf(opcao3.get(1)),(Integer)opcao3.get(2));
                    if ((Integer) opcao3.get(0) == 10) val3=false;
                }
                val3 = true;
            }
            if(opcao1 == 4){
                while(val4){
                    opcao4 = view.menuObjectos();
                    if ((Integer) opcao4.get(0) == 0) val4=false;
                    if ((Integer) opcao4.get(0) == 1) model.guardaEstado(String.valueOf(opcao4.get(1)));
                    if ((Integer) opcao4.get(0) == 2) model.carregaEstado(String.valueOf(opcao4.get(1)));
                }
                val4=true;
            }
        }
    }
}
