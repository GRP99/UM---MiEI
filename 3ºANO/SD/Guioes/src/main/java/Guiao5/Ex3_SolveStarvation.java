package Guiao5;

class Reader_SolveStarvation implements Runnable {
    private RWLock_SolveStarvation rwlock;
    public Reader_SolveStarvation(RWLock_SolveStarvation rw) {
        this.rwlock = rw;
    }
    public void run() {
        System.out.println("Reader: vou ler ");
        rwlock.readLock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        rwlock.readUnlock();
        System.out.println("Reader: li ");
    }
}

class Writer_SolveStarvation implements Runnable {
    private RWLock_SolveStarvation rwlock;
    public Writer_SolveStarvation(RWLock_SolveStarvation rw) {
        this.rwlock = rw;
    }
    public void run() {
        System.out.println("Writer: vou escrever ");
        rwlock.writeLock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        rwlock.writeUnlock();
        System.out.println("Writer: escrevi ");
    }
}

public class Ex3_SolveStarvation {
    public static void main(String args[]) throws InterruptedException {
        RWLock_SolveStarvation rwlock = new RWLock_SolveStarvation();
        Thread[] threadWriter = new Thread[15];
        Thread[] threadReader = new Thread[15];
        for (int i = 0; i < 15; i++) {
            threadWriter[i] = new Thread(new Writer_SolveStarvation(rwlock));
            threadReader[i] = new Thread(new Reader_SolveStarvation(rwlock));
        }
        for (int i = 0; i < 15; i++) {
            threadWriter[i].start();
            threadReader[i].start();
        }
        for (int i = 0; i < 15; i++) {
            threadWriter[i].join();
            threadReader[i].join();
        }
        //Os escritores são executados primeiro e só posteriormente os leitores são todos executados!
    }
}
