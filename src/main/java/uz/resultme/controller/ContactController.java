package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.resultme.entity.Contact;
import uz.resultme.entity.Phone;
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

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ContactDto>>> getAllContacts() {
        return contactService.getAllContact();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactDto>> getContactById(@PathVariable Long id) {
        return contactService.findById(id);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        return contactService.deleteById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<ContactDto> create(@RequestParam String instagram,
                                            @RequestParam String telegram,
                                             @RequestParam String facebook,
                                             @RequestParam List<Phone> phone,
                                             @RequestParam String location) {
        return contactService.create(instagram, telegram, facebook, phone, location);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<ContactDto>> update(
            @PathVariable Long id,
            @RequestParam String instagram,
            @RequestParam String telegram,
            @RequestParam String facebook,
            @RequestParam List<Phone> phone,
            @RequestParam String location) {
        return contactService.update(id, instagram, telegram, facebook, phone, location);
    }

}
