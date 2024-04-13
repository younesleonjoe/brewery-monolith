package com.younesleonjoe.brewerymonolith.controller;

import com.younesleonjoe.brewerymonolith.entity.Brewery;
import com.younesleonjoe.brewerymonolith.service.BreweryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/breweries")
@Controller
@RequiredArgsConstructor
public class BreweryController {

  private final BreweryService breweryService;

  @GetMapping({"/breweries", "breweries/index", "breweries/index.html", "breweries.html"})
  public String listBreweries(Model model) {

    model.addAttribute("breweries", breweryService.findAllBreweries());
    return "breweries/index";
  }

  @GetMapping("api/v1/breweries")
  public @ResponseBody List<Brewery> getBreweryJSON() {

    return breweryService.findAllBreweries();
  }
}
