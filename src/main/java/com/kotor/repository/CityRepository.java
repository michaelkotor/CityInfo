package com.kotor.repository;

import com.kotor.model.City;

public interface CityRepository {
    City create(City city);
    City findById(long id);
}
