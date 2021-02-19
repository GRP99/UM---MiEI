/*
package Guiao4;

public class Ex02 {

    public static void main(String args[]) {
        BoundedBuffer b = new BoundedBuffer(10);
        int tc = 50; //tempo de producao em ms
        int tp = 100; // tempo de producao em ms
        int total_ops = 100; // no total, produzir 100 itens e consumir 100 itens
        int N = 10; // nº total de threads 
        int P; //nºprodutores
        int C; //nºconsumidores

        for (P = 1; P <= 9; P++) {
            C = N - P;
            System.out.println("Consumidores= " + C + ", Produtores =" + P);
            Thread[] threadArrayC = new Thread[C];
            Thread[] threadArrayP = new Thread[P];  
            int opsprod = total_ops/P;
            int rest = total_ops%P;
            for (int x = 0; x < P; x++) {
                threadArrayP[x] = new Thread(new Produtor1(b,tp,total_ops));
            }
            int opscons = total_ops/C;
            rest = total_ops%C;
            for (int x = 0; x < C; x++) {
                threadArrayC[x] = new Thread(new Consumidor1(b,tc,total_ops));
            }
            long startTime = System.currentTimeMillis() / 1000;
            for (int x = 0; x < P; x++) {
                threadArrayP[x].start();
            }
            for (int x = 0; x < C; x++) {
                threadArrayC[x].start();
            }
            for (int x = 0; x < P; x++) {
                try {
                    threadArrayP[x].join();
                } catch (InterruptedException e) {
                }
            }
            for (int x = 0; x < C; x++) {
                try {
                    threadArrayC[x].join();
                } catch (InterruptedException e) {
                }
            }
            long endTime = System.currentTimeMillis() / 1000;
            double debito = (double) total_ops / (endTime - startTime);
            System.out.println("Debito +" + debito);
        }
    }
}
*/