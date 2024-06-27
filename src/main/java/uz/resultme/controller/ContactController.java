package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.resultme.entity.Phone;
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
    public ResponseEntity<ContactDto> create(@RequestParam String instagram,
                                             @RequestParam String telegram,
                                             @RequestParam String facebook,
                                             @RequestParam List<String> phone,
                                             @RequestParam String location)
    {
        return contactService.create(instagram, telegram, facebook, phone, location);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<ContactDto>>> getAllContacts()
    {
        return contactService.getAllContact();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ContactDto>> getContactById(@PathVariable Long id)
    {
        return contactService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ContactDto>> update(
            @PathVariable Long id,
            @RequestParam String instagram,
            @RequestParam String telegram,
            @RequestParam String facebook,
            @RequestParam List<String> phone,
            @RequestParam String location)
    {
        return contactService.update(id, instagram, telegram, facebook, phone, location);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id)
    {
        return contactService.deleteById(id);
    }
}
