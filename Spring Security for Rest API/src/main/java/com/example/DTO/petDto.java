package com.example.DTO;

import com.example.model.Color;

import java.time.LocalDate;

public class petDto {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private Color color;
    private Long ownerId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
}
