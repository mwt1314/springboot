package cn.matio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author mawt
 * @description
 * @date 2019/12/11
 */
@SpringBootApplication
@EnableScheduling   //注解开启定时任务支持
//@EnableAsync //启用多线程
public class ScheduleApp {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleApp.class, args);
    }

}
