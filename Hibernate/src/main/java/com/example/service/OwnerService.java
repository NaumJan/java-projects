package com.example.service;

import com.example.dao.OwnerDao;
import com.example.model.Owner;
import com.example.model.Pet;
import java.time.LocalDate;
import java.util.List;

public class OwnerService {
    private final OwnerDao ownerDao;

    public OwnerService(OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
    }

    public Owner createOwner(String name, LocalDate birthDate) {
        if (name == null || name.isBlank() || birthDate == null) {
            throw new IllegalArgumentException("Name and birth date cannot be null or empty");
        }

        Owner owner = new Owner();
        owner.setName(name);
        owner.setBirthDate(birthDate);
        return ownerDao.save(owner);
    }

    public void addPetToOwner(Owner owner, Pet pet) {
        if (owner == null || pet == null) {
            throw new IllegalArgumentException("Owner and Pet cannot be null");
        }
        pet.setOwner(owner);
        owner.getPets().add(pet);
        ownerDao.update(owner);
    }

    public List<Pet> getOwnerPets(long ownerId) {
        Owner owner = ownerDao.getById(ownerId);
        return owner != null ? owner.getPets() : List.of();
    }

    public void deleteOwnerWithPets(long ownerId) {
        Owner owner = ownerDao.getById(ownerId);
        if (owner != null) {
            owner.getPets().forEach(pet -> pet.setOwner(null));
            ownerDao.deleteById(ownerId);
        }
    }
}