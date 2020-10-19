package com.jn;

import com.jn.primiary.beyondsoft.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@EnableFeignClients
@SpringCloudApplication
@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@EnableCaching
public class stdglApplication {

    public static void main(String[] args) {
//    	System.setProperty("es.set.netty,runtime.available.processors","false");
//    	System.setProperty("client.transport.sniff","false");
        SpringApplication.run(stdglApplication.class, args);
    }

//    @Override//为了打包springboot项目
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(this.getClass());
//    }

    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter(){
        return new OpenEntityManagerInViewFilter();
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}

