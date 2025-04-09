package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;
    private String breed;

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "pet_friends",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<Pet> friends = new ArrayList<>();

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
    public Owner getOwner() { return owner; }
    public void setOwner(Owner owner) { this.owner = owner; }
    public List<Pet> getFriends() { return friends; }
    public void setFriends(List<Pet> friends) { this.friends = friends; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet)) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}