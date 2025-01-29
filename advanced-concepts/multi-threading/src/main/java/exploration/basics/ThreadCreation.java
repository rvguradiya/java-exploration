package exploration.basics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// extends the Thread class and override run method
public class ThreadCreation extends  Thread {

    // create logger object for logging
    private final Logger log = LoggerFactory.getLogger(ThreadCreation.class);

    // thread will run thid method
    @Override
    public void run() {
        log.info("Thread is Running");
    }
}




