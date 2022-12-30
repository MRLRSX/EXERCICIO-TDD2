package com.devsuperior.bds02.resource;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value="/city")
public class CityResource {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<Page<CityDTO>> findAllPaged(@PageableDefault(size = 12, sort = {"name"}) Pageable pageable){
        Page<CityDTO> cityDTO = cityService.findAllPaged(pageable);
        return ResponseEntity.ok().body(cityDTO);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<CityDTO> findById(@PathVariable Long id) {
        CityDTO dto = cityService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<CityDTO> insert(@RequestBody CityDTO dto) {
        dto = cityService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CityDTO> update(@PathVariable Long id, @RequestBody CityDTO dto) {
        dto = cityService.update(dto, id);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
