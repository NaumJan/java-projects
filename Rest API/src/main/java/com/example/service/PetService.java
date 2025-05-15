package com.example.service;

import com.example.DTO.petDto;
import com.example.model.Color;
import com.example.model.Owner;
import com.example.model.Pet;
import com.example.repository.petRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    private final petRepository petRepository;

    public PetService(petRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Page<petDto> getPetsByColor(Color color, Pageable pageable) {
        if (color != null) {
            return petRepository.findByColor(color, pageable).map(this::mapToDTO);
        } else {
            return petRepository.findAll(pageable).map(this::mapToDTO);
        }
    }

    public petDto getPetById(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
        return mapToDTO(pet);
    }

    private petDto mapToDTO(Pet pet) {
        petDto dto = new petDto();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBirthDate(pet.getBirthDate());
        dto.setBreed(pet.getBreed());
        dto.setColor(pet.getColor());
        dto.setOwnerId(pet.getOwner() != null ? pet.getOwner().getId() : null);
        return dto;
    }
    public petDto createPet(petDto petDto) {
        Pet pet = new Pet();
        pet.setName(petDto.getName());
        pet.setBirthDate(petDto.getBirthDate());
        pet.setBreed(petDto.getBreed());
        pet.setColor(petDto.getColor());

        if (petDto.getOwnerId() != null) {
            Owner owner = new Owner();
            owner.setId(petDto.getOwnerId());
            pet.setOwner(owner);
        }

        Pet savedPet = petRepository.save(pet);
        return mapToDTO(savedPet);
    }

    public petDto updatePet(Long id, petDto petDto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        pet.setName(petDto.getName());
        pet.setBirthDate(petDto.getBirthDate());
        pet.setBreed(petDto.getBreed());
        pet.setColor(petDto.getColor());

        if (petDto.getOwnerId() != null) {
            Owner owner = new Owner();
            owner.setId(petDto.getOwnerId());
            pet.setOwner(owner);
        }

        Pet updatedPet = petRepository.save(pet);
        return mapToDTO(updatedPet);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}
