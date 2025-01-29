package exploration.basics;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ThreadCreationTest {
    private final Logger log = LoggerFactory.getLogger(ThreadCreationTest.class);

    @Test
    public void createThread(){
        log.info("testing creation method running");
        System.out.println("###############33333");
        Thread th = new ThreadCreation();
        th.start();
    }
}
