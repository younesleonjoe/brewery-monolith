package com.younesleonjoe.brewerymonolith.controller;

import com.younesleonjoe.brewerymonolith.entity.Beer;
import com.younesleonjoe.brewerymonolith.enums.BeerStyleEnum;
import com.younesleonjoe.brewerymonolith.repository.BeerInventoryRepository;
import com.younesleonjoe.brewerymonolith.repository.BeerRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/beers")
@Controller
@RequiredArgsConstructor
public class BeerController {

  // TODO add service
  private final BeerRepository beerRepository;
  private final BeerInventoryRepository beerInventoryRepository;

  @RequestMapping("/find")
  public String findBeers(Model model) {

    model.addAttribute("beer", Beer.builder().build());
    return "beers/findBeers";
  }

  @GetMapping
  public String processFindFormReturnMany(Beer beer, BindingResult result, Model model) {

    // TODO add service
    // TODO get paging data from view

    Page<Beer> beerPage =
        beerRepository.findAllByName(
            beer.getName(), createPageRequest(0, 10, Sort.Direction.DESC, "name"));

    List<Beer> beerList = beerPage.getContent();

    if (beerList.isEmpty()) {

      result.rejectValue("name", "error.notFound", "Not Found");
      return "beers/findBeers";

    } else if (beerList.size() == 1) {

      beer = beerList.getFirst();
      return "redirect:/beers/" + beer.getId();

    } else {

      model.addAttribute("selections", beerList);
      return "beers/beerList";
    }
  }

  @GetMapping("/{id}")
  public ModelAndView showBeer(@PathVariable UUID id) {

    // TODO add service

    ModelAndView view = new ModelAndView("beers/beerDetails");
    view.addObject(beerRepository.findById(id).get());
    return view;
  }

  @GetMapping("/new")
  public String initCreateForm(Model model) {
    model.addAttribute("beer", Beer.builder().build());
    model.addAttribute("beerStyles", BeerStyleEnum.values());
    return "beers/createBeer";
  }

  @PostMapping("/new")
  public String processCreateForm(Beer beer) {

    // TODO add service

    Beer newBeer =
        Beer.builder()
            .name(beer.getName())
            .style(beer.getStyle())
            .minOnHand(beer.getMinOnHand())
            .price(beer.getPrice())
            .quantityToBrew(beer.getQuantityToBrew())
            .upc(beer.getUpc())
            .build();

    Beer savedBeer = beerRepository.save(newBeer);
    return "redirect:/beers/" + savedBeer.getId();
  }

  @GetMapping("/{id}/edit")
  public String initUpdateForm(@PathVariable UUID id, Model model) {

    if (beerRepository.findById(id).isPresent()) {
      model.addAttribute("beer", beerRepository.findById(id).get());
      model.addAttribute("beerStyles", BeerStyleEnum.values());
    }
    return "beers/createOrUpdateBeer";
  }

  @PostMapping("/{id}/edit")
  public String processUpdateForm(@Validated Beer beer, BindingResult result) {

    if (result.hasErrors()) {

      return "beers/createOrUpdateBeer";

    } else {

      // TODO add service
      Beer savedBeer = beerRepository.save(beer);
      return "redirect:/beers/" + savedBeer.getId();
    }
  }

  private PageRequest createPageRequest(
      int page, int size, Sort.Direction sortDirection, String propertyName) {
    return PageRequest.of(page, size, Sort.by(sortDirection, propertyName));
  }
}
