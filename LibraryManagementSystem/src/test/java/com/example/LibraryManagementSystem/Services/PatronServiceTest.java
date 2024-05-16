package com.example.LibraryManagementSystem.Services;

import com.example.LibraryManagementSystem.DTOS.BookDTO;
import com.example.LibraryManagementSystem.DTOS.PatronDTO;
import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.Models.Patron;
import com.example.LibraryManagementSystem.repositories.PatronRepository;
import com.example.LibraryManagementSystem.utils.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;
    private PatronService patronService;
    @Mock
    private ModelMapper modelMapper;
    @BeforeEach
    void setUp() {
        this.patronService = new PatronService(this.patronRepository, this.modelMapper);
    }


    @Test
    public void getALL_Patrons_Succeed() {
        List<Patron> patrons = new ArrayList<>();
        Patron patron = new Patron("Hassan", "Ali", Date.valueOf("2001-01-05"), "hassan_ali1296@yahoo.com", "Elmandara",
                "02156214551",
                "male",
                "Backend developer at ..");
        Patron patron2 = new Patron("Omar", "Ali", Date.valueOf("2001-01-05"), "omar_ali1296@yahoo.com", "Elmandara",
                "45114822",
                "male",
                "Frontend developer at ..");
        patrons.add(patron);
        patrons.add(patron2);
        List<PatronDTO> patronDTOS = new ArrayList<>();
        PatronDTO patronDTO = new PatronDTO("Hassan", "Ali", Date.valueOf("2001-01-05"), "hassan_ali1296@yahoo.com", "Elmandara",
                "02156214551",
                "male",
                "Backend developer at ..");
        PatronDTO patronDTO1 = new PatronDTO("Omar", "Ali", Date.valueOf("2001-01-05"), "omar_ali1296@yahoo.com", "Elmandara",
                "45114822",
                "male",
                "Frontend developer at ..");
        patronDTOS.add(patronDTO);
        patronDTOS.add(patronDTO1);
        Mockito.when(patronRepository.findAll()).thenReturn(patrons);
        Mockito.when(modelMapper.map(Mockito.any(Patron.class), Mockito.eq(PatronDTO.class)))
                .thenAnswer(invocation -> {
                    Patron patron11 = invocation.getArgument(0);
                    return new PatronDTO(patron11.getFirstName(), patron11.getLastName(), patron11.getBirthday(),
                            patron11.getEmail(), patron11.getAddress(), patron11.getPhone(), patron11.getGender(),
                            patron11.getDetails());
                });
        assertEquals(patronDTOS, patronService.getAllPatrons());
    }

    @Test
    public void createPatron_fails_If_FirstName_OR_LastName_Null() {
        PatronDTO patronDTO = new PatronDTO(null, "Ali", Date.valueOf("2001-01-05"), "hassan_ali1296@yahoo.com", "Elmandara",
                "02156214551",
                "mail",
                "Backend developer at ..");
        var exp = assertThrows(DataIntegrityViolationException.class, () -> patronService.createPatron(patronDTO));
        assertEquals(exp.getMessage(), "first name and last name are required");
    }

    @Test
    public void createPatron_fails_If_Email_Null() {
        PatronDTO patronDTO = new PatronDTO("Hassan", "Ali", Date.valueOf("2001-01-05"), null, "Elmandara",
                "02156214551",
                "mail",
                "Backend developer at ..");
        var exp = assertThrows(DataIntegrityViolationException.class, () -> patronService.createPatron(patronDTO));
        assertEquals(exp.getMessage(), "Email should not be NULL");
    }

    @Test
    public void createPatron_fails_If_Email_isNotUnique() {
        PatronDTO patronDTO = new PatronDTO("Hassan", "Ali", Date.valueOf("2001-01-05"), "hassan_ali1296@yahoo.com", "Elmandara",
                "02156214551",
                "male",
                "Backend developer at ..");
        Mockito.when(patronRepository.existsByEmail(patronDTO.getEmail())).thenReturn(true);
        var exp = assertThrows(DataIntegrityViolationException.class, () -> patronService.createPatron(patronDTO));
        assertEquals(exp.getMessage(), "Email should be Unique");
    }

    @Test
    public void createPatron_Succeed() {
        PatronDTO patronDTO = new PatronDTO("Hassan", "Ali", Date.valueOf("2001-01-05"), "hassan_ali1296@yahoo.com", "Elmandara",
                "02156214551",
                "male",
                "Backend developer at ..");
        Patron patron = new Patron("Hassan", "Ali", Date.valueOf("2001-01-05"), "hassan_ali1296@yahoo.com", "Elmandara",
                "02156214551",
                "male",
                "Backend developer at ..");
        Mockito.when(patronRepository.existsByEmail(patronDTO.getEmail())).thenReturn(false);
        Mockito.when(patronRepository.save(patron)).thenReturn(patron);
        Mockito.when(modelMapper.map(patronDTO, Patron.class)).thenReturn(patron);
        Mockito.when(modelMapper.map(patron, PatronDTO.class)).thenReturn(patronDTO);
        assertEquals(patronDTO, patronService.createPatron(patronDTO));
    }

    @Test
    public void getPatron_succeed_IfPatron_exist() {
        int patronId = 1;
        Patron patron = new Patron("Afsha", "Ali", Date.valueOf("2001-01-05"), "Afsha6@yahoo.com", "Elmandara",
                "0215621455551",
                "male",
                "Frontend developer at ..");
        PatronDTO patronDTO = new PatronDTO("Afsha", "Ali", Date.valueOf("2001-01-05"), "Afsha6@yahoo.com", "Elmandara",
                "0215621455551",
                "male",
                "Frontend developer at ..");
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(true);
        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        Mockito.when(modelMapper.map(patron, PatronDTO.class)).thenReturn(patronDTO);
        assertEquals(patronDTO, patronService.getPatron(patronId));
    }

    @Test
    public void getPatron_fails_IfPatron_doesNotExist() {
        int patronId = 10;
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(false);
        var exp = assertThrows(ResourceNotFoundException.class, () -> patronService.getPatron(patronId));
        assertEquals(exp.getMessage(), String.format("Patron with ID %s not found", patronId));
    }

    @Test
    public void deletePatron_fails_IfPatron_doesNotExist() {
        int patronId = 10;
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(false);
        var exp = assertThrows(ResourceNotFoundException.class, () -> patronService.deletePatron(patronId));
        assertEquals(exp.getMessage(), String.format("Patron with ID %s not found", patronId));
    }

    @Test
    public void updatePatron_Succeed() {
        int patronId = 1;
        Patron patron = new Patron("Omar", "Ali", Date.valueOf("2001-01-05"), "hassan_ali1296@yahoo.com", "Elmandara",
                "02156214551",
                "mail",
                "Backend developer at ..");
        PatronDTO patronDTO = new PatronDTO("Omar", "Ali", Date.valueOf("2001-01-05"), "Afsha6@yahoo.com", "Shelton",
                "02156214551",
                "male",
                "Backend developer at ..");
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(true);
        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        Mockito.when(patronRepository.existsByEmail(patronDTO.getEmail())).thenReturn(false);
        Mockito.when(patronRepository.save(patron)).thenReturn(patron);
        Mockito.when(modelMapper.map(patron, PatronDTO.class)).thenReturn(patronDTO);
        assertEquals(patronDTO, patronService.updatePatron(patronId, patronDTO));
    }

    @Test
    public void updatePatron_fails_IfPatron_doesNotExist() {
        int patronId = 10;
        PatronDTO patronDTO = new PatronDTO("Omar", "Ali", Date.valueOf("2001-01-05"), "Afsha6@yahoo.com", "Shelton",
                "02156214551",
                "male",
                "Backend developer at ..");
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(false);
        var exp = assertThrows(ResourceNotFoundException.class, () -> patronService.updatePatron(patronId, patronDTO));
        assertEquals(exp.getMessage(), String.format("Patron with ID %s not found", patronId));
    }

    @Test
    public void updatePatron_fails_IfPatron_firstName_Null() {
        int patronId = 1;
        Patron patron = new Patron("Omar", "Ali", Date.valueOf("2001-01-05"), "hassan_ali1296@yahoo.com", "Elmandara",
                "02156214551",
                "mail",
                "Backend developer at ..");
        PatronDTO patronDTO = new PatronDTO(null, "Ali", Date.valueOf("2001-01-05"), "Afsha6@yahoo.com", "Shelton",
                "02156214551",
                "male",
                "Backend developer at ..");
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(true);
        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        var exp = assertThrows(DataIntegrityViolationException.class, () -> patronService.updatePatron(patronId, patronDTO));
        assertEquals(exp.getMessage(), "first name and last name are required");
    }

    @Test
    public void updatePatron_fails_IfPatron_lastName_Null() {
        int patronId = 1;
        Patron patron = new Patron("Omar", "Ali", Date.valueOf("2001-01-05"), "hassan_ali1296@yahoo.com", "Elmandara",
                "02156214551",
                "mail",
                "Backend developer at ..");
        PatronDTO patronDTO = new PatronDTO("Omar", null, Date.valueOf("2001-01-05"), "Afsha6@yahoo.com", "Shelton",
                "02156214551",
                "male",
                "Backend developer at ..");
        Mockito.when(patronRepository.existsById(patronId)).thenReturn(true);
        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        var exp = assertThrows(DataIntegrityViolationException.class, () -> patronService.updatePatron(patronId, patronDTO));
        assertEquals(exp.getMessage(), "first name and last name are required");
    }
}
