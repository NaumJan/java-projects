package com.example.Controller;

import com.example.DTO.petDto;
import com.example.model.Color;
import com.example.service.PetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{id}")
    public petDto getPet(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @GetMapping
    public Page<petDto> getPets(
            @RequestParam(required = false) Color color,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        return petService.getPetsByColor(color, pageable);
    }

    @PostMapping
    public petDto createPet(@RequestBody petDto petDto) {
        return petService.createPet(petDto);
    }

    @PutMapping("/{id}")
    public petDto updatePet(@PathVariable Long id, @RequestBody petDto petDto) {
        return petService.updatePet(id, petDto);
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Long id) {
        petService.deletePet(id);
    }
}