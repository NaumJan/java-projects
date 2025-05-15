package com.example.Controller;

import com.example.DTO.ownerDto;
import com.example.service.OwnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @Test
    @WithMockUser(roles = "USER")
    public void getOwnerById_ShouldReturnOwnerDto() throws Exception {
        ownerDto testOwner = new ownerDto();
        testOwner.setId(1L);
        testOwner.setName("Петя Пупкин");
        testOwner.setBirthDate(LocalDate.of(1995, 10, 12));

        when(ownerService.getOwnerById(1L)).thenReturn(testOwner);

        mockMvc.perform(get("/api/owners/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Петя Пупкин"))
                .andExpect(jsonPath("$.birthDate").value("1995-10-12"));
    }

    @Test
    public void getOwnerById_Unauthenticated_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/owners/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }
}