package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.resultme.entity.Contact;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.ContactDto;
import uz.resultme.service.ContactService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController
{
    private final ContactService contactService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Contact>> create(@RequestBody Contact contact)
    {
        return contactService.create(contact);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ContactDto>> getContactById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "ru") String lang)
    {
        return contactService.findById(id, lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<Contact>> getContactById(
            @PathVariable Long id)
    {
        return contactService.findById(id);
    }


    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<ContactDto>>> getAllContacts(
            @RequestHeader(value = "Accept-Language", defaultValue = "ru") String lang)
    {
        return contactService.getAllContact(lang);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Contact>> update(@PathVariable Long id, @RequestBody Contact contact)
    {
        return contactService.update(id, contact);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id)
    {
        return contactService.deleteById(id);
    }
}
