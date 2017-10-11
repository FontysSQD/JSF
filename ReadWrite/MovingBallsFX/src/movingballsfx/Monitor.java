package movingballsfx;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * JSF3_w07_StartProject Created by Sven de Vries on 10-10-2017
 */
public class Monitor {
    Lock monLock = new ReentrantLock();
    Condition okToRead = monLock.newCondition();
    Condition okToWrite = monLock.newCondition();
    int writersActive;
    int writersWaiting;
    int readersActive;
    int readersWaiting;

    public void enterReader() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive > 0) {
                readersWaiting++;
                okToRead.await();
                readersWaiting--;
            }
            readersActive++;
        }
        catch (InterruptedException ex){
            readersWaiting--;
            throw ex;
        }
        finally {
            monLock.unlock();
        }
    }

    public void exitReader() {
        monLock.lock();
        try {
            readersActive--;
            if (readersActive == 0) {
                okToWrite.signal();
            }
        }
        finally {
            monLock.unlock();
        }
    }

    public void enterWriter() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive > 0 || readersActive > 0) {
                writersWaiting++;
                okToWrite.await();
                writersWaiting--;
            }
            writersActive++;
        }
        catch (InterruptedException ex){
            writersWaiting--;
            throw ex;
        }
        finally {
            monLock.unlock();
        }
    }

    public void exitWriter() {
        monLock.lock();
        try {
            writersActive--;
            if (writersWaiting > 0) {
                okToWrite.signal();
            }
            else{
                okToRead.signalAll();
            }
        }
        finally {
            monLock.unlock();
        }
    }
}
