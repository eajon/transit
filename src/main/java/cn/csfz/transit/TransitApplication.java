package cn.csfz.transit;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author eajon
 */
@SpringBootApplication
@EnableScheduling
public class TransitApplication {


    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TransitApplication.class);
        builder.headless(false).run(args);
    }

}
