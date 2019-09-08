package cn.pn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.pn.dao.*")
public class PnUserProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PnUserProviderApplication.class, args);
	}

}
