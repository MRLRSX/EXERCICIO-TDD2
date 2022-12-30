package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.devsuperior.bds02.services.exception.DatabaseException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public Page<EventDTO> findAllPaged(Pageable pageable){
        return eventRepository.findAll(pageable).map(EventDTO::new);
    }

    @Transactional(readOnly = true)
    public EventDTO findById(Long id){
        Optional<Event> event = eventRepository.findById(id);
        return new EventDTO(event.orElseThrow(()-> new ResourceNotFoundException("Entity Not Found !!!")));
    }

    @Transactional
    public EventDTO update(EventDTO eventDTO, Long id){
        try {
            Event event = eventRepository.getOne(id);
            copyEventDTOToEvent(eventDTO, event);
            event = eventRepository.save(event);
            return new EventDTO(event);
        }catch (EntityNotFoundException erro){
            throw new ResourceNotFoundException("Entity not Found !!!");
        }
    }

    @Transactional
    public EventDTO insert(EventDTO eventDTO){
        Event event = new Event();
        copyEventDTOToEvent(eventDTO, event);
        event = eventRepository.save(event);
        return new EventDTO(event);
    }

    @Transactional
    public void deleteById(Long id){
        try {
            eventRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyEventDTOToEvent(EventDTO eventDTO, Event event){

        event.setName(eventDTO.getName());
        event.setDate(eventDTO.getDate());
        event.setUrl(eventDTO.getUrl());
        City city = cityRepository.getOne(eventDTO.getCityId());
        event.setCity(city);
    }

}
