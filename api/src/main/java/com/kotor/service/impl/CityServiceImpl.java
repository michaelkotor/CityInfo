package com.kotor.service.impl;

import com.kotor.model.City;
import com.kotor.repository.CityRepository;
import com.kotor.service.CityService;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City create(City city) {
        return cityRepository.create(city);
    }

    @Override
    public City findById(long id) {
        return cityRepository.findById(id);
    }

    @Override
    public City findByName(String name) {
        return cityRepository.findByName(name);
    }
}
