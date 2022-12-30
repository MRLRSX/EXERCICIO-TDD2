package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exception.DatabaseException;
import com.devsuperior.bds02.services.exception.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public Page<CityDTO> findAllPaged(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityDTO::new);
    }

    @Transactional(readOnly = true)
    public CityDTO findById(Long id) {
        Optional<City> result = cityRepository.findById(id);
        City city = result.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found !!!"));
        return new CityDTO(city);
    }

    @Transactional
    public CityDTO insert(CityDTO cityDTO) {
        City city = new City();
        city.setName(cityDTO.getName());
        city = cityRepository.save(city);
        return new CityDTO(city);
    }

    @Transactional
    public CityDTO update(CityDTO cityDTO, Long id) {
        try {
            City city = cityRepository.getOne(id);
            city.setName(cityDTO.getName());
            city = cityRepository.save(city);
            return new CityDTO(city);
        } catch (EntityNotFoundException erro) {
            throw new ResourceNotFoundException("Entity Not Found !!!");
        }
    }


    public void deleteById(Long id) {
        try {
            cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }

    }
}
