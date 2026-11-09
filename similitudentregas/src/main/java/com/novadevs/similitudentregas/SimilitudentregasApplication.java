package com.novadevs.similitudentregas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(excludeName = {
        "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
        "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
public class SimilitudentregasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimilitudentregasApplication.class, args);
    }

}