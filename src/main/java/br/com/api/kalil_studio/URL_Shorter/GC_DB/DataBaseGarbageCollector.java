package br.com.api.kalil_studio.URL_Shorter.GC_DB;

import br.com.api.kalil_studio.URL_Shorter.services.URLService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
@EnableScheduling
public class DataBaseGarbageCollector {

    private static final long GARBAGE_TIME_MS = 60000;

    final URLService urlService;


    public DataBaseGarbageCollector(URLService urlService) {
        this.urlService = urlService;
    }

    @Scheduled(fixedDelay = GARBAGE_TIME_MS)
    public void dataBaseGC() {
        runGC_DB();
    }

    private void runGC_DB() {
        long time = System.currentTimeMillis();
        int deletionsCount = urlService.cleanExpiredRegisters();
        if(deletionsCount != 0) {
            System.out.printf("%s : GC Database Deleted: %s | Elapsed: %s\r\n",
                    LocalDateTime.now(ZoneId.of("UTC")),
                    deletionsCount, System.currentTimeMillis() - time);
        }
    }
}
