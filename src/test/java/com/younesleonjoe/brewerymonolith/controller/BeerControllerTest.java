package com.younesleonjoe.brewerymonolith.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.younesleonjoe.brewerymonolith.entity.Beer;
import com.younesleonjoe.brewerymonolith.enums.BeerStyleEnum;
import com.younesleonjoe.brewerymonolith.repository.BeerRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class BeerControllerTest {

  @Mock BeerRepository beerRepository;
  @InjectMocks BeerController beerController;
  List<Beer> beerList;
  UUID id;
  Beer beer;
  MockMvc mockMvc;
  Page<Beer> beers;
  Page<Beer> pagedResponse;

  @BeforeEach
  void setUp() {
    beer =
        Beer.builder()
            .id(UUID.randomUUID())
            .name("beer")
            .style(BeerStyleEnum.ALE)
            .minOnHand(25)
            .price(BigDecimal.TEN)
            .quantityToBrew(15)
            .upc("1234567890L")
            .build();
    beerList = new ArrayList<Beer>();
    beerList.add(Beer.builder().build());
    beerList.add(Beer.builder().build());
    pagedResponse = new PageImpl<>(beerList);

    final String tmpId = "493410b3-dd0b-4b78-97bf-289f50f6e74f";
    id = UUID.fromString(tmpId);

    mockMvc = MockMvcBuilders.standaloneSetup(beerController).build();
  }

  @Test
  void findBeers() throws Exception {
    mockMvc
        .perform(get("/beers/find"))
        .andExpect(status().isOk())
        .andExpect(view().name("beers/findBeers"))
        .andExpect(model().attributeExists("beer"));
    verifyNoInteractions(beerRepository);
  }

  @Test
  void processFindFormReturnMany() throws Exception {

    when(beerRepository.findAllByName(anyString(), Mockito.any(Pageable.class)))
        .thenReturn(pagedResponse);

    mockMvc
        .perform(get("/beers").flashAttr("beer", beer))
        .andExpect(status().isOk())
        .andExpect(view().name("beers/beerList"))
        .andExpect(model().attribute("selections", hasSize(2)));
  }

  @Test
  void showBeer() throws Exception {

    when(beerRepository.findById(id)).thenReturn(Optional.of(Beer.builder().id(id).build()));

    mockMvc
        .perform(get("/beers/" + id))
        .andExpect(status().isOk())
        .andExpect(view().name("beers/beerDetails"))
        .andExpect(model().attribute("beer", hasProperty("id", is(id))));
  }

  @Test
  void initCreateForm() throws Exception {

    mockMvc
        .perform(get("/beers/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("beers/createBeer"))
        .andExpect(model().attributeExists("beer"));

    verifyNoInteractions(beerRepository);
  }

  @Test
  void processCreateForm() throws Exception {

    when(beerRepository.save(ArgumentMatchers.any())).thenReturn(Beer.builder().id(id).build());

    mockMvc
        .perform(post("/beers/new").flashAttr("beer", beer))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/beers" + "/" + id));
    verify(beerRepository).save(ArgumentMatchers.any());
  }

  @Test
  void initUpdateForm() throws Exception {

    when(beerRepository.findById(id)).thenReturn(Optional.of(Beer.builder().id(id).build()));

    mockMvc
        .perform(get("/beers/" + id + "/edit"))
        .andExpect(status().isOk())
        .andExpect(view().name("beers/createOrUpdateBeer"))
        .andExpect(model().attributeExists("beer"));

    verifyNoMoreInteractions(beerRepository);
  }

  @Test
  void processUpdateForm() throws Exception {

    when(beerRepository.save(ArgumentMatchers.any())).thenReturn(Beer.builder().id(id).build());

    mockMvc
        .perform(post("/beers/" + id + "/edit"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/beers/" + id));

    verify(beerRepository).save(ArgumentMatchers.any());
  }
}
