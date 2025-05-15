package com.example.Service;
import com.example.DTO.ownerDto;
import com.example.model.Owner;
import com.example.repository.ownerRepository;
import com.example.service.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {

    @Mock
    private ownerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    public void getOwnerById_ShouldReturnOwner() {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("John Doe");
        owner.setBirthDate(LocalDate.of(1980, 1, 1));

        given(ownerRepository.findById(1L)).willReturn(Optional.of(owner));

        ownerDto result = ownerService.getOwnerById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void getAllOwners_ShouldReturnEmptyList() {
        given(ownerRepository.findAll()).willReturn(Collections.emptyList());

        List<ownerDto> result = ownerService.getAllOwners();

        assertTrue(result.isEmpty());
    }

    @Test
    public void createOwner_ShouldReturnSavedOwner() {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("John Doe");
        owner.setBirthDate(LocalDate.of(1980, 1, 1));

        ownerDto input = new ownerDto();
        input.setName("John Doe");
        input.setBirthDate(LocalDate.of(1980, 1, 1));

        given(ownerRepository.save(any(Owner.class))).willReturn(owner);

        ownerDto result = ownerService.createOwner(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void updateOwner_ShouldReturnUpdatedOwner() {
        Owner existingOwner = new Owner();
        existingOwner.setId(1L);
        existingOwner.setName("Old Name");
        existingOwner.setBirthDate(LocalDate.of(1980, 1, 1));

        Owner updatedOwner = new Owner();
        updatedOwner.setId(1L);
        updatedOwner.setName("New Name");
        updatedOwner.setBirthDate(LocalDate.of(1980, 1, 1));

        ownerDto input = new ownerDto();
        input.setName("New Name");
        input.setBirthDate(LocalDate.of(1980, 1, 1));

        given(ownerRepository.findById(1L)).willReturn(Optional.of(existingOwner));
        given(ownerRepository.save(any(Owner.class))).willReturn(updatedOwner);

        ownerDto result = ownerService.updateOwner(1L, input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Name", result.getName());
    }

    @Test
    public void deleteOwner_ShouldCallRepository() {
        ownerService.deleteOwner(1L);
        verify(ownerRepository).deleteById(1L);
    }
}