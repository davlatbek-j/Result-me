package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.resultme.entity.Contact;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.ContactDto;
import uz.resultme.service.ContactService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContactDto>>> getAllContacts() {
        return contactService.getAllContact();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactDto>> getContactById(@PathVariable Long id) {
        return contactService.findById(id);
    }
/*
    @PostMapping
   // public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }*/

  /*  @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contactDetails) {

    }*/

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
       return contactService.deleteById(id);
    }

}
