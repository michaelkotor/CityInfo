package com.kotor.controller;

import com.kotor.model.City;
import com.kotor.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/city")
    public City createCity(@RequestBody City city) {
        return cityService.create(city);
    }

    @GetMapping("/city/{id}")
    public City findById(@PathVariable long id) {
        return cityService.findById(id);
    }

    @GetMapping("/city")
    public City findByName(@RequestParam String name) {
        System.out.println(name);
        return cityService.findByName(name);
    }
}
