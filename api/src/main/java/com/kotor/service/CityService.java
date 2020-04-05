package com.kotor.service;

import com.kotor.model.City;

public interface CityService {
    City create(City city);
    City findById(long id);
    City findByName(String name);
}
