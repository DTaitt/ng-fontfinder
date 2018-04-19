package com.example.setsapi.repositories;

import com.example.setsapi.models.FontSet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public interface FontSetRepository extends CrudRepository<FontSet, Long> {

}