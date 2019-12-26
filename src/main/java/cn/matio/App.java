package cn.matio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author mawt
 * @description
 * @date 2019/12/11
 */

@SpringBootApplication
public class App {
@EnableScheduling

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}

