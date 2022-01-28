package com.nylee;

import com.nylee.common.config.CustomConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


@SpringBootApplication(exclude = QuartzAutoConfiguration.class)
@EnableScheduling
@WebListener
public class CommunityApplication extends SpringBootServletInitializer implements ServletContextListener {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.profiles.active}")
	private String active;

	public static String commonActive;

	private static ConfigurableApplicationContext context;

	@Autowired
	CustomConfig customConfig;

	public static void main(String[] args) {
		context = SpringApplication.run(CommunityApplication.class, args);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("init start! profiles: " + active);

		String filePath = event.getServletContext().getInitParameter("filePath");
		File file = new File(filePath);

		try {
			if (active.equals("local")) {
				ObjectInputStream ois
						= new ObjectInputStream(new FileInputStream(filePath));
				// 파일 읽어서 바로 setAttribute해주기
				event.getServletContext().setAttribute("list", ois.readObject());
				ois.close();
//				sshConnection = new SSHConnection(
//						customConfig.getValue("custom.ssh-user"),
//						customConfig.getValue("custom.ssh-pw"),
//						Integer.parseInt(customConfig.getValue("custom.ssh-lport")),
//						Integer.parseInt(customConfig.getValue("custom.ssh-rport"))
//				);
			}
			commonActive = active.replaceAll("[0-9]","");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void restart() {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(CommunityApplication.class, args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();
	}

}
