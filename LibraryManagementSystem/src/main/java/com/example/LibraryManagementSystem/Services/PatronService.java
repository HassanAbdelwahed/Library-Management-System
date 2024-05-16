package com.example.LibraryManagementSystem.Services;

import com.example.LibraryManagementSystem.DTOS.BookDTO;
import com.example.LibraryManagementSystem.DTOS.PatronDTO;
import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.Models.Patron;
import com.example.LibraryManagementSystem.repositories.PatronRepository;
import com.example.LibraryManagementSystem.utils.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatronService {
    private final PatronRepository patronRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public PatronService(PatronRepository patronRepository, ModelMapper modelMapper) {
        super();
        this.modelMapper = modelMapper;
        this.patronRepository = patronRepository;
    }

    @Cacheable(value="patron")
    public List<PatronDTO> getAllPatrons() {
        List<Patron> patrons = patronRepository.findAll();
        return patrons.stream().map(patron -> modelMapper.map(patron, PatronDTO.class))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "patron", key = "#patronId")
    public PatronDTO getPatron(int patronId) {
        if(patronRepository.existsById(patronId)) {
            Patron patron = patronRepository.findById(patronId).orElse(null);
            return modelMapper.map(patron, PatronDTO.class);
        }else {
            throw new ResourceNotFoundException(String.format("Patron with ID %s not found", patronId));
        }
    }

    public PatronDTO createPatron(PatronDTO patronDTO) {
        if (patronDTO.getFirstName() == null || patronDTO.getLastName() == null) {
            throw new DataIntegrityViolationException("first name and last name are required");
        }
        if (patronDTO.getEmail() == null) {
            throw new DataIntegrityViolationException("Email should not be NULL");
        }
        if (patronRepository.existsByEmail(patronDTO.getEmail()))
            throw new DataIntegrityViolationException("Email should be Unique");
        Patron patron = modelMapper.map(patronDTO, Patron.class);
        patron = patronRepository.save(patron);
        return modelMapper.map(patron, PatronDTO.class);
    }

    @CachePut(cacheNames = "patron", key = "#patronId")
    public PatronDTO updatePatron(int patronId, PatronDTO patronDTO) {
        if (patronRepository.existsById(patronId)) {
            Patron patron = patronRepository.findById(patronId).orElse(null);
            if (patronDTO.getFirstName() == null || patronDTO.getLastName() == null) {
                throw new DataIntegrityViolationException("first name and last name are required");
            }
            if (patronDTO.getEmail() == null) {
                throw new DataIntegrityViolationException("Email should not be NULL");
            }
            if (patronRepository.existsByEmail(patronDTO.getEmail()))
                throw new DataIntegrityViolationException("Email should be Unique");
            patron.setFirstName(patronDTO.getFirstName());
            patron.setLastName(patronDTO.getLastName());
            patron.setBirthday(patronDTO.getBirthday());
            patron.setEmail(patronDTO.getEmail());
            patron.setPhone(patronDTO.getPhone());
            patron.setGender(patronDTO.getGender());
            patron.setDetails(patronDTO.getDetails());
            patron = patronRepository.save(patron);
            return modelMapper.map(patron, PatronDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format("Patron with ID %s not found", patronId));
        }
    }

    @CacheEvict(cacheNames = "patron", key = "#patronId")
    public void deletePatron(int patronId) {
        if (patronRepository.existsById(patronId)) {
            patronRepository.deleteById(patronId);
        } else {
            throw new ResourceNotFoundException(String.format("Patron with ID %s not found", patronId));
        }
    }
}
