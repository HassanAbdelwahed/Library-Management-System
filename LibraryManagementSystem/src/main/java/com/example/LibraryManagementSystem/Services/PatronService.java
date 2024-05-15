package com.example.LibraryManagementSystem.Services;

import com.example.LibraryManagementSystem.DTOS.PatronDTO;
import com.example.LibraryManagementSystem.Models.Patron;
import com.example.LibraryManagementSystem.repositories.PatronRepository;
import com.example.LibraryManagementSystem.utils.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatronService {
    private final PatronRepository patronRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        super();
        this.patronRepository = patronRepository;
    }

    public List<PatronDTO> getAllPatrons() {
        List<Patron> patrons = patronRepository.findAll();
        return patrons.stream().map(patron -> modelMapper.map(patron, PatronDTO.class))
                .collect(Collectors.toList());
    }

    public PatronDTO getPatron(int patronId) {
        Patron patron = patronRepository.findById(patronId).orElse(null);
        if(patron != null) {
            return modelMapper.map(patron, PatronDTO.class);
        }else {
            throw new ResourceNotFoundException(String.format("Patron with ID %s not found", patronId));
        }
    }

    public PatronDTO createPatron(PatronDTO patronDTO) {
        Patron patron = modelMapper.map(patronDTO, Patron.class);
        patron = patronRepository.save(patron);
        return modelMapper.map(patron, PatronDTO.class);
    }

    public PatronDTO updatePatron(int patronId, PatronDTO patronDTO) {
        Patron patron = patronRepository.findById(patronId).orElse(null);
        if (patron != null) {
            patron.setFirstName(patronDTO.getFirstName());
            patron.setLastName(patronDTO.getLastName());
            patron.setBirthday(patronDTO.getBirthday());
            patron.setEmail(patronDTO.getEmail());
            patron.setPhone(patronDTO.getPhone());
            patron.setPassword(patronDTO.getPassword());
            patron.setGender(patronDTO.getGender());
            patron.setDetails(patronDTO.getDetails());
            patron = patronRepository.save(patron);
            return modelMapper.map(patron, PatronDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format("Patron with ID %s not found", patronId));
        }
    }

    public void deletePatron(int patronId) {
        Patron patron = patronRepository.findById(patronId).orElse(null);
        if (patron != null) {
            patronRepository.deleteById(patronId);
        } else {
            throw new ResourceNotFoundException(String.format("Patron with ID %s not found", patronId));
        }
    }
}
