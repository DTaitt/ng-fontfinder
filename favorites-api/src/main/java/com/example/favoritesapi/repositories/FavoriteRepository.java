package com.example.favoritesapi.repositories;

import com.example.favoritesapi.models.Favorite;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public interface FavoriteRepository extends CrudRepository<Favorite, String> {

}