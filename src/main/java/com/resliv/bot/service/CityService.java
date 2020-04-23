package com.resliv.bot.service;

import com.resliv.bot.entity.City;
import com.resliv.bot.repository.CityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional(readOnly = true)
    public List<City> findByName(String name) {
        return cityRepository.findByName(name);
    }
}
