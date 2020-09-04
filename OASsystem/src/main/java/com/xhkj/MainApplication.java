package com.xhkj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动程序
 * 
 * @author xhkj
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableCaching
public class MainApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(MainApplication.class, args);
        System.out.println("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※ﾞ  \n" +
                "   _____ _             _             _____                             \n" +
                "  / ____| |           | |           / ____|                            \n" +
                " | (___ | |_ __ _ _ __| |_         | (___  _   _  ___ ___ ___  ___ ___ \n" +
                "  \\___ \\| __/ _` | '__| __|         \\___ \\| | | |/ __/ __/ _ \\/ __/ __|\n" +
                "  ____) | || (_| | |  | |_          ____) | |_| | (_| (_|  __/\\__ \\__ \\\n" +
                " |_____/ \\__\\__,_|_|   \\__|        |_____/ \\__,_|\\___\\___\\___||___/___/\n" +
                "                                                                       \n" +
                " ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
    }
}
