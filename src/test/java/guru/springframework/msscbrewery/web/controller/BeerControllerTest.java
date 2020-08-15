package guru.springframework.msscbrewery.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.XpathResultMatchers.*;

import java.util.UUID;

@SpringBootTest
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDto beerDto;

    @BeforeEach
    public void setUp(){
        beerDto = BeerDto.builder().id(UUID.randomUUID()).beerName("Beer1").beerStyle("PALE ALE").upc(1234567890L).build();
    }

    @Test
    public void getBeer() throws Exception{
        given(beerService.getBeerById(any(UUID.class))).willReturn(beerDto);
        mockMvc.perform(get("/api/v1/beer/"+beerDto.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.id",is(beerDto.getId().toString())))
                .andExpect((ResultMatcher) jsonPath("$.beerName",is("Beer1")));
    }
}
