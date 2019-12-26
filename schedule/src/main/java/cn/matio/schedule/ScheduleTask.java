package cn.matio.schedule;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mawt
 * @description
 * @date 2019/12/11
 */
/*
 3.1 Web应用中的启动和关闭问题
我们知道通过spring加载或初始化的Bean，在服务停止的时候，spring会自动卸载（销毁）。但是由于线程是JVM级别的，如果用户在Web应用中启动了一个线程，那么这个线程的生命周期并不会和Web应用保持一致。也就是说，即使Web应用停止了，这个线程依然没有结束（死亡）。

解决方法：
1）当前对象是通过spring初始化
spring在卸载（销毁）实例时，会调用实例的destroy方法。通过实现DisposableBean接口覆盖destroy方法实现。在destroy方法中主动关闭线程。
2）当前对象不是通过spring初始化（管理）
那么我们可以增加一个Servlet上下文监听器，在Servlet服务停止的时候主动关闭线程
public class MyTaskListenter implements ServletContextListener{
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        //关闭线程或线程池
    }
    //省略...
}

 */
@Component
public class ScheduleTask implements DisposableBean, ApplicationContextAware {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ApplicationContext applicationContext;

    /**
     * 1.3.2 固定间隔任务
     * 下一次的任务执行时间，是从方法最后一次任务执行结束时间开始计算。并以此规则开始周期性的执行任务。
     * 举个栗子：
     * 添加一个work()方法，每隔10秒执行一次。
     * 例如：假设work()方法在第0秒开始执行，方法执行了12秒，那么下一次执行work()方法的时间是第22秒
     */
    @Scheduled(fixedDelay = 5000)
    public void fixedDelay() {
        System.out.println("每隔五秒钟执行一次： " + dateFormat.format(new Date()));
    }

    /*
    1.3.3 固定频率任务
         按照指定频率执行任务，并以此规则开始周期性的执行调度。
        举个栗子：
        添加一个work()方法，每10秒执行一次。
        注意：当方法的执行时间超过任务调度频率时，调度器会在当前方法执行完成后立即执行下次任务。
        例如：假设work()方法在第0秒开始执行，方法执行了12秒，那么下一次执行work()方法的时间是第12秒。
     */
    @Scheduled(fixedRate = 5000)    //fixedRate表示每隔5秒执行一次
    public void fixedRate() {
        System.out.println("每隔五秒钟执行一次： " + dateFormat.format(new Date()));
    }

    /**
     * cron表达式：
     * 第一位，表示秒，取值 0-59
     * 第二位，表示分，取值 0-59
     * 第三位，表示小时，取值 0-23
     * 第四位，日期，取值 1-31
     * 第五位，月份，取值 1-12
     * 第六位，星期几，取值 1-7
     * 第七位，年份，可以留空，取值 1970-2099
     * <p>
     * (*) 星号：可以理解为“每”的意思，每秒、没分
     * (?) 问号：只能出现在日期和星期这两个位置，表示这个位置的值不确定
     * (-) 表达一个范围，如在小时字段中使用 10-12 ，表示从10点到12点
     * (,) 逗号，表达一个列表值，如在星期字段中使用 1,2,4 ，则表示星期一、星期二、星期四
     * (/) 斜杠，如 x/y ，x是开始值，y是步长，如在第一位(秒)使用 0/15，表示从0秒开始，每15秒
     * <p>
     * 官方解释：
     * 0 0 3 * * ?         每天 3 点执行
     * 0 5 3 * * ?         每天 3 点 5 分执行
     * 0 5 3 ? * *         每天 3 点 5 分执行
     * 0 5/10 3 * * ?      每天 3 点 5 分，15 分，25 分，35 分，45 分，55 分这几个点执行
     * 0 10 3 ? * 1        每周星期天的 3 点10 分执行，注：1 表示星期天
     * 0 10 3 ? * 1#3      每个月的第三个星期的星期天 执行，#号只能出现在星期的位置
     * <p>
     * 注：第六位(星期几)中的数字可能表达不太正确，可以使用英文缩写来表示，如：Sun
     */
    @Scheduled(cron = "0 30 11 ? * *")
    public void cron() {
        System.out.println("在指定时间 " + dateFormat.format(new Date()) + "执行");
    }

    @Override
    public void destroy() throws Exception {
        //关闭线程或线程池
        ThreadPoolTaskScheduler scheduler = (ThreadPoolTaskScheduler)applicationContext.getBean("scheduler");
        scheduler.shutdown();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
