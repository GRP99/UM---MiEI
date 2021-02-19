package Guiao5;

class Reader implements Runnable{
    private RWLock rwlock;
    public Reader(RWLock rw){
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

class Writer implements Runnable{
    private RWLock rwlock;
    public Writer(RWLock rw){
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

public class Ex3 {
    public static void main(String args[]) throws InterruptedException{
        RWLock rwlock = new RWLock();
        Thread[] threadWriter = new Thread[15];
        Thread[] threadReader = new Thread[15];
        for (int i=0; i<15; i++) {
            threadWriter[i] = new Thread(new Writer(rwlock));
            threadReader[i] = new Thread(new Reader(rwlock));
        }
        for (int i=0; i<15; i++) {
            threadWriter[i].start();
            threadReader[i].start();            
        }
        for (int i=0; i<15; i++) {
            threadWriter[i].join();
            threadReader[i].join();
        }
        //Os leitores são executados primeiro e só posteriormente os escritores são todos executados! 
    }
}