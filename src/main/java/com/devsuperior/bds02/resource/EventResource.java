package com.devsuperior.bds02.resource;

import com.devsuperior.bds02.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/event")
public class EventResource {
    @Autowired
    private EventService eventService;
}
