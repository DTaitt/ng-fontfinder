package com.example.setsapi.controllers;


import com.example.setsapi.models.FontSet;
import com.example.setsapi.repositories.FontSetRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class FontSetsController {

    @Autowired
    private FontSetRepository fontSetRepository;

    @GetMapping("/")
    public Iterable<FontSet> findAllSets() {
        return fontSetRepository.findAll();
    }

    @GetMapping("/{setId}")
    public FontSet findSetById(@PathVariable Long setId) throws NotFoundException {

        FontSet foundFontSet = fontSetRepository.findOne(setId);

        if (foundFontSet == null) {
            throw new NotFoundException("FontSet with ID of " + setId + " was not found!");
        }


        return foundFontSet;
    }

    @DeleteMapping("/{setId}")
    public HttpStatus deleteSetById(@PathVariable Long setId) throws EmptyResultDataAccessException {
        fontSetRepository.delete(setId);
        return HttpStatus.OK;
    }

    @PostMapping("/")
    public FontSet createNewSet(@RequestBody FontSet newFontSet) {
        return fontSetRepository.save(newFontSet);
    }

    @PatchMapping("/{setId}")
    public FontSet updateSetById(@PathVariable Long setId, @RequestBody FontSet fontSetRequest) throws NotFoundException {
        FontSet fontSetFromDb = fontSetRepository.findOne(setId);

        if (fontSetFromDb == null) {
            throw new NotFoundException("FontSet with ID of " + setId + " was not found!");
        }

        fontSetFromDb.setSetName(fontSetRequest.getSetName());
        fontSetFromDb.setFavoriteId(fontSetRequest.getFavoriteId());
        fontSetFromDb.setSetName(fontSetRequest.getSetName());

        return fontSetRepository.save(fontSetFromDb);
    }

    @ExceptionHandler
    void handleSetNotFound(
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
