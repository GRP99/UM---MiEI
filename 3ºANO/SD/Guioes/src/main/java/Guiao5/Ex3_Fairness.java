package Guiao5;

class Reader_Fairness implements Runnable{
    private RWLock_Fairness rwlock;
    public Reader_Fairness(RWLock_Fairness rw){
        this.rwlock = rw;
    }
    public void run(){
        System.out.println("Reader: vou ler ");
        rwlock.readLock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        rwlock.readUnlock();
        System.out.println("Reader: li ");
    }
}

class Writer_Fairness implements Runnable{
    private RWLock_Fairness rwlock;
    public Writer_Fairness(RWLock_Fairness rw){
        this.rwlock = rw;
    }
    public void run(){
        System.out.println("Writer: vou escrever ");
        rwlock.writeLock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        rwlock.writeUnlock();
        System.out.println("Writer: escrevi ");
    }
}

public class Ex3_Fairness {
    public static void main(String args[]) throws InterruptedException{
        RWLock_Fairness rwlock = new RWLock_Fairness();
        Thread[] threadWriter = new Thread[15];
        Thread[] threadReader = new Thread[15];
        for (int i=0; i<15; i++) {
            threadWriter[i] = new Thread(new Writer_Fairness(rwlock));
            threadReader[i] = new Thread(new Reader_Fairness(rwlock));
        }
        for (int i=0; i<15; i++) {
            threadWriter[i].start();
            threadReader[i].start();            
        }
        for (int i=0; i<15; i++) {
            threadWriter[i].join();
            threadReader[i].join();
        }
    }
}