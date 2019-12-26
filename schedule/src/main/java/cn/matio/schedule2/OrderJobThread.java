package cn.matio.schedule2;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

/**
 * @author mawt
 * @description
 * @date 2019/12/11
 */
@Component
public class OrderJobThread implements SchedulingConfigurer {

    private String name = "测试"; //调用set方法可以动态设置日志名

    private String cron = "* 0/10 * * * ?"; //调用set方法可动态设置时间规则

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(new Runnable() {

            @Override
            public void run() {
                System.out.println(name + " --- > 开始");
                //业务逻辑
                System.out.println(name + " --- > 结束");
            }
        }, cron);
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
