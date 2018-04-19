package com.example.setsapi.controllers;


import com.example.setsapi.models.FontSet;
import com.example.setsapi.repositories.FontSetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;


import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(FontSetsController.class)
public class FontSetsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private FontSet newFontSet;

    private FontSet updatedSecondFontSet;


    @Autowired
    private ObjectMapper jsonObjectMapper;


    @MockBean
    private FontSetRepository mockFontSetRepository;

    @Before
    public void setUp() {
        FontSet firstFontSet = new FontSet(
                Long.valueOf(1),
                "a font set name"
        );

        FontSet secondFontSet = new FontSet(
                Long.valueOf(2),
                "another font set name"
        );

        newFontSet = new FontSet(
                Long.valueOf(3),
                "a new font set name"
        );
        given(mockFontSetRepository.save(newFontSet)).willReturn(newFontSet);

        updatedSecondFontSet = new FontSet(
                Long.valueOf(5),
                "an updated font set name"
        );
        given(mockFontSetRepository.save(updatedSecondFontSet)).willReturn(updatedSecondFontSet);

        Iterable<FontSet> mockFontSets =
                Stream.of(firstFontSet, secondFontSet).collect(Collectors.toList());

        given(mockFontSetRepository.findAll()).willReturn(mockFontSets);
        given(mockFontSetRepository.findOne(1L)).willReturn(firstFontSet);
        given(mockFontSetRepository.findOne(4L)).willReturn(null);
        doAnswer(invocation -> {
            throw new EmptyResultDataAccessException("ERROR MESSAGE FROM MOCK!!!", 1234);
        }).when(mockFontSetRepository).delete(4L);


    }

    @Test
    public void findAllFontSets_success_returnsStatusOK() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllFontSets_success_returnAllFontSetsAsJSON() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void findAllFontSets_success_returnFavoriteIdForEachFontSet() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].favoriteId", is(1)));
    }

    @Test
    public void findAllFontSets_success_returnFontSetNameForEachFontSet() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].fontSetName", is("a font set name")));
    }
//
//    @Test
//    public void findAllFontSets_success_returnLastNameForEachFontSet() throws Exception {
//
//        this.mockMvc
//                .perform(get("/"))
//                .andExpect(jsonPath("$[0].lastName", is("Person")));
//    }

    @Test
    public void findFontSetById_success_returnsStatusOK() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findFontSetById_success_returnFavoriteId() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(jsonPath("$.favoriteId", is(1L)));
    }

    @Test
    public void findFontSetById_success_returnFontSetName() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(jsonPath("$.fontSetName", is("a font set name")));
    }
//
//    @Test
//    public void findFontSetById_success_returnLastName() throws Exception {
//
//        this.mockMvc
//                .perform(get("/1"))
//                .andExpect(jsonPath("$.lastName", is("Person")));
//    }

    @Test
    public void findFontSetById_failure_setNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(get("/4"))
                .andExpect(status().reason(containsString("FontSet with ID of 4 was not found!")));
    }

    @Test
    public void deleteFontSetById_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(delete("/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFontSetById_success_deletesViaRepository() throws Exception {

        this.mockMvc.perform(delete("/1"));

        verify(mockFontSetRepository, times(1)).delete(1L);
    }

    @Test
    public void deleteFontSetById_failure_setNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(delete("/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createFontSet_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newFontSet))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void createFontSet_success_returnsFavoriteId() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newFontSet))
                )
                .andExpect(jsonPath("$.favoriteId", is(Long.valueOf(3))));
    }

    @Test
    public void createFontSet_success_returnsFontSetName() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newFontSet))
                )
                .andExpect(jsonPath("$.fontSetName", is("a new font set name")));
    }
//
//    @Test
//    public void createFontSet_success_returnsLastName() throws Exception {
//
//        this.mockMvc
//                .perform(
//                        post("/")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(jsonObjectMapper.writeValueAsString(newFontSet))
//                )
//                .andExpect(jsonPath("$.lastName", is("FontSet")));
//    }

    @Test
    public void updateFontSetById_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFontSet))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void updateFontSetById_success_returnsUpdatedFavoriteId() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFontSet))
                )
                .andExpect(jsonPath("$.favoriteId", is(Long.valueOf(5))));
    }

    @Test
    public void updateFontSetById_success_returnsUpdatedFontSetName() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFontSet))
                )
                .andExpect(jsonPath("$.fontSetName", is("an updated font set name")));
    }

//    @Test
//    public void updateFontSetById_success_returnsUpdatedLastName() throws Exception {
//
//        this.mockMvc
//                .perform(
//                        patch("/1")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFontSet))
//                )
//                .andExpect(jsonPath("$.lastName", is("Info")));
//    }

    @Test
    public void updateFontSetById_failure_setNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(
                        patch("/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFontSet))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFontSetById_failure_setNotFoundReturnsNotFoundErrorMessage() throws Exception {

        this.mockMvc
                .perform(
                        patch("/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFontSet))
                )
                .andExpect(status().reason(containsString("FontSet with ID of 4 was not found!")));
    }




}

