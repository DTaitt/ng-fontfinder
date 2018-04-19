package com.example.favoritesapi.controllers;


import com.example.favoritesapi.models.Favorite;
import com.example.favoritesapi.repositories.FavoriteRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.favoritesapi.models.Favorite;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class FavoritesController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @GetMapping("/")
    public Iterable<Favorite> findAllFavorites() {
        return favoriteRepository.findAll();
    }

    @GetMapping("/{favoriteId}")
    public Favorite findFavoriteById(@PathVariable String favoriteId) throws NotFoundException {

        Favorite foundFavorite = favoriteRepository.findOne(favoriteId);

        if (foundFavorite == null) {
            throw new NotFoundException("Favorite with ID of " + favoriteId + " was not found!");
        }


        return foundFavorite;
    }

    @DeleteMapping("/{favoriteId}")
    public HttpStatus deleteFavoriteById(@PathVariable String favoriteId) throws EmptyResultDataAccessException {
        favoriteRepository.delete(favoriteId);
        return HttpStatus.OK;
    }

    @PostMapping("/")
    public Favorite createNewFavorite(@RequestBody Favorite newFavorite) {
        return favoriteRepository.save(newFavorite);
    }

    @PatchMapping("/{favoriteId}")
    public Favorite updateFavoriteById(@PathVariable String favoriteId, @RequestBody Favorite favoriteRequest) throws NotFoundException {
        Favorite favoriteFromDb = favoriteRepository.findOne(favoriteId);

        if (favoriteFromDb == null) {
            throw new NotFoundException("Favorite with ID of " + favoriteId + " was not found!");
        }

        favoriteFromDb.setFamily(favoriteRequest.getFamily());
        favoriteFromDb.setCategory(favoriteRequest.getCategory());

        return favoriteRepository.save(favoriteFromDb);
    }

    @ExceptionHandler
    void handleFavoriteNotFound(
            NotFoundException exception,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler
    void handleDeleteNotFoundException(
            EmptyResultDataAccessException exception,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}
