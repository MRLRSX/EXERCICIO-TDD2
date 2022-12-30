package com.devsuperior.bds02.resource;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value="/event")
public class EventResource {
    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventDTO>> findAllPaged(@PageableDefault(size = 12, sort = "name")Pageable pageable){
        Page<EventDTO> eventDTO = eventService.findAllPaged(pageable);
        return ResponseEntity.ok().body(eventDTO);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<EventDTO> findById(@PathVariable Long id) {
        EventDTO dto = eventService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<EventDTO> insert(@RequestBody EventDTO dto) {
        dto = eventService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody EventDTO dto) {
        dto = eventService.update(dto, id);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
