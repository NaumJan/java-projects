package com.example.Controller;

import com.example.DTO.petDto;
import com.example.model.Color;
import com.example.service.CustomUserDetails;
import com.example.service.PetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public petDto getPet(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<petDto> getPets(
            @RequestParam(required = false) Color color,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        return petService.getPetsByColor(color, pageable);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public petDto createPet(@RequestBody petDto petDto, Authentication authentication) {
        if (!hasPermission(petDto, authentication)) {
            throw new org.springframework.security.access.AccessDeniedException("You do not have permission to create this pet");
        }
        return petService.createPet(petDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public petDto updatePet(@PathVariable Long id, @RequestBody petDto petDto, Authentication authentication) {
        if (!hasPermission(petDto, authentication)) {
            throw new org.springframework.security.access.AccessDeniedException("You do not have permission to update this pet");
        }
        return petService.updatePet(id, petDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void deletePet(@PathVariable Long id, Authentication authentication) {
        petDto pet = petService.getPetById(id);
        if (!hasPermission(pet, authentication)) {
            throw new org.springframework.security.access.AccessDeniedException("You do not have permission to delete this pet");
        }
        petService.deletePet(id);
    }

    private boolean hasPermission(petDto petDto, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return false;
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser().getOwner() != null &&
                userDetails.getUser().getOwner().getId().equals(petDto.getOwnerId());
    }
}