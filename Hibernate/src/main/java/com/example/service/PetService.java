package com.example.service;

import com.example.dao.PetDao;
import com.example.model.Color;
import com.example.model.Owner;
import com.example.model.Pet;
import java.util.List;

public class PetService {
    private final PetDao petDao;

    public PetService(PetDao petDao) {
        this.petDao = petDao;
    }

    public void addFriends(Pet pet1, Pet pet2) {
        if (pet1 == null || pet2 == null) {
            throw new IllegalArgumentException("Pets cannot be null");
        }
        if (pet1.equals(pet2)) {
            throw new IllegalArgumentException("Pet cannot be friend with itself");
        }
        pet1.getFriends().add(pet2);
        pet2.getFriends().add(pet1);
        petDao.update(pet1);
        petDao.update(pet2);
    }

    public List<Pet> getPetsByColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }
        return petDao.getAll().stream()
                .filter(pet -> pet.getColor() == color)
                .toList();
    }

    public void transferPetToNewOwner(Pet pet, Owner newOwner) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        pet.setOwner(newOwner);
        petDao.update(pet);
    }
}