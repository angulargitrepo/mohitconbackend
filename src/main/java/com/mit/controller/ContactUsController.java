package com.mit.controller;


import com.mit.model.ContactUs;
import com.mit.repository.ContactUsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contactUs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ContactUsController {

    @Autowired
    ContactUsRepository contactUsRepository;

    @GetMapping("/getAllContactUs")
    public List<ContactUs> getAllNotes() {
        log.trace("A TRACE Message");
        return contactUsRepository.findAll();
    }

    @PostMapping("/saveContactUs")
    public ContactUs createNote(@Valid @RequestBody ContactUs note) {
        return contactUsRepository.save(note);
    }

    @GetMapping("/contactUs/{id}")
    public ContactUs getNoteById(@PathVariable(value = "id") Long noteId) throws Exception {
        return contactUsRepository.findById(noteId)
                .orElseThrow(() -> new Exception("ContactUs Id : "+noteId +" not found"));
    }

    @PutMapping("/contactUs/{id}")
    public ContactUs updateNote(@PathVariable(value = "id") Long noteId,
                           @Valid @RequestBody ContactUs noteDetails) throws Exception {

        ContactUs note = contactUsRepository.findById(noteId)
                .orElseThrow(() -> new Exception("ContactUs Id : "+noteId +" not found"));

        ContactUs updatedNote = contactUsRepository.save(note);
        return updatedNote;
    }

    @DeleteMapping("/contactUs/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) throws Exception {
        ContactUs note = contactUsRepository.findById(noteId)
                .orElseThrow(() -> new Exception("ContactUs Id : "+noteId +" not found"));

        contactUsRepository.delete(note);

        return ResponseEntity.ok().build();
    }
}
