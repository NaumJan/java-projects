package com.example.Controller;

import com.example.DTO.ownerDto;
import com.example.service.OwnerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{id}")
    public ownerDto getOwner(@PathVariable Long id) {
        return ownerService.getOwnerById(id);
    }

    @GetMapping
    public List<ownerDto> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @PostMapping
    public ownerDto createOwner(@RequestBody ownerDto ownerDto) {
        return ownerService.createOwner(ownerDto);
    }

    @PutMapping("/{id}")
    public ownerDto updateOwner(@PathVariable Long id, @RequestBody ownerDto ownerDto) {
        return ownerService.updateOwner(id, ownerDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
    }
}
