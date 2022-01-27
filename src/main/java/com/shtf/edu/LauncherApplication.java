package com.shtf.edu;

import com.shtf.edu.utils.time.TimeStampHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * LauncherApplication class
 *
 * @author chenlingyu
 * @date 2020/4/26 9:57
 */
@Slf4j
@SpringBootApplication
@EnableAsync
public class LauncherApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(LauncherApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        log.info("\n----------------------------------------------------------\n\t" +
                "启动成功,当前时间：" + TimeStampHelper.getNow().toString() + "/\n\t" +
                "服务地址:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + "/\n\t" +
                "swagger-ui: \thttp://" + ip + ":" + port  + "/swagger-ui.html\n\t" +
                "Doc: \t\thttp://" + ip + ":" + port  + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
