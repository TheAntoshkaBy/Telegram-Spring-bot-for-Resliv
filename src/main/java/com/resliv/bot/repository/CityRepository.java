package com.resliv.bot.repository;


import com.resliv.bot.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CityRepository extends JpaRepository<City, Long> {
    ArrayList<City> findByName(String name);
}
