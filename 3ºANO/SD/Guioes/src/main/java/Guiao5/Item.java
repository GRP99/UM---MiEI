package Guiao5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Item {
    ReentrantLock lock;
    Condition isEmpty;
    private int quantity;

    public Item() {
        this.quantity = 0;
        this.lock = new ReentrantLock();
        this.isEmpty = this.lock.newCondition();
    }

    public void supply(int quant) {
        this.lock.lock();
        this.quantity += quant;
        isEmpty.signalAll();
        this.lock.unlock();
    }

    public void consume() {
        this.lock.lock();
        try {
            while (this.quantity == 0) {
                this.isEmpty.await();
            }
            this.quantity--;
        }
        catch (InterruptedException e) {} 
        finally {
            this.lock.unlock();
        }
    }
}
