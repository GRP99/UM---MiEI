package Operations;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ManagerThreads {

    private ReentrantLock rL;
    private Condition send;
    private Condition receive;
    private int ticketSend;
    private int ticketCounterSend;
    private int ticketReceive;
    private int ticketCounterReceive;
    private int threadsSend;
    private int threadsReceive;
    private int maxThreadsBYType;
    private int maxThreadsTOSend;
    private int maxThreadsTOReceive;
    private int maxThreadsTOSendperClient;
    private Map<Integer, PrintWriter> outputsClients;
    private Map<Integer, Integer> threadsReceiveperClient;  // number of threads to receive per client

    public ManagerThreads() {
        this.rL = new ReentrantLock();
        this.send = rL.newCondition();
        this.receive = rL.newCondition();
        this.ticketSend = 0;
        this.ticketCounterSend = 0;
        this.ticketReceive = 0;
        this.ticketCounterReceive = 0;
        this.threadsSend = 0;
        this.threadsReceive = 0;
        this.maxThreadsBYType = 1;
        this.outputsClients = new HashMap<>();
        this.threadsReceiveperClient = new HashMap<>();
        this.maxThreadsTOSend = 4;
        this.maxThreadsTOReceive = 2;
        this.maxThreadsTOSendperClient = 2;
    }

    public int getTicketSend() {
        return this.ticketSend;
    }

    public int getTicketCounterSend() {
        return this.ticketCounterSend;
    }

    public int getTicketReceive() {
        return this.ticketReceive;
    }

    public int getTicketCounterReceive() {
        return this.ticketCounterReceive;
    }

    public void requestShipping(int id) {
        this.rL.lock();
        if (id >= 0) {
            int k = 0;
            if (this.threadsReceiveperClient.containsKey(id)) {
                k = this.threadsReceiveperClient.get(id);
            }
            k++;
            this.threadsReceiveperClient.put(id, k);
            while (k > this.maxThreadsTOSendperClient) {
                try {
                    this.send.await();
                    k = this.threadsReceiveperClient.get(id);
                } catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                    this.rL.unlock();
                }
            }
        }
        int ticket = this.ticketSend;
        this.ticketSend++;
        while (this.ticketCounterSend > ticket || this.threadsSend >= this.maxThreadsTOSend) {
            try {
                this.send.await();
            } catch (InterruptedException ie) {
                System.out.println(ie.getMessage());
                rL.unlock();
            }
        }
        this.threadsSend++;
        this.ticketCounterSend++;
        this.rL.unlock();
    }

    public void releaseShipping(int id) {
        this.rL.lock();
        if (id >= 0) {
            int aux = this.threadsReceiveperClient.get(id);
            aux = aux - 1;
            if (aux == 0) {
                this.threadsReceiveperClient.remove(id);
            } else {
                this.threadsReceiveperClient.put(id, aux);
            }
        }
        this.threadsSend = this.threadsSend - 1;
        this.send.signalAll();
        this.rL.unlock();
    }

    public void requestReceive() {
        this.rL.lock();
        int bilhete = this.ticketReceive;
        this.ticketReceive++;
        while (this.ticketCounterReceive != bilhete || this.threadsReceive >= this.maxThreadsTOReceive) {
            try {
                this.receive.await();
            } catch (InterruptedException ie) {
                System.out.println(ie.getMessage());
                this.rL.unlock();
            }
        }
        this.threadsReceive++;
        this.ticketCounterReceive++;
        this.rL.unlock();
    }

    public void freeReceive() {
        this.rL.lock();
        this.threadsReceive = this.threadsReceive - 1;
        this.receive.signalAll();
        this.rL.unlock();
    }

    public void addClient(int id, PrintWriter s) {
        this.outputsClients.put(id, s);
    }

    public void notifyClients(int idMusic, String title, String artist) {
        for (PrintWriter p : this.outputsClients.values()) {
            p.println("A NEW MUSIC has been added to the system, ID: " + idMusic + ", Title: " + title + ", Artist: " + artist);
            p.flush();
        }
    }

    public void notifyClientDownload(int idClient, int idMusic) {
        PrintWriter p = this.outputsClients.get(idClient);
        p.println("The download of the music with the ID: " + idMusic + " has been successfully performed!!!");
        p.flush();
    }

    public void removeCliente(int idClient) {
        this.outputsClients.remove(idClient);
    }
}
