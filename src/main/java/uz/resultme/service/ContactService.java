package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.resultme.entity.Contact;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.ContactDto;
import uz.resultme.repository.ContactRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactService
{
    private final ContactRepository contactRepository;

    public ResponseEntity<ApiResponse<List<ContactDto>>> getAllContact(String lang)
    {
        List<Contact> all = contactRepository.findAll();
        ApiResponse<List<ContactDto>> response = new ApiResponse<>();
        response.setMessage("Successfully found " + all.size() + " item(s)");
        response.setData(new ArrayList<>());
        all.forEach(i -> response.getData().add(new ContactDto(i,lang)));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<ContactDto>> findById(Long id, String lang)
    {
        ApiResponse<ContactDto> response = new ApiResponse<>();
        if (!contactRepository.existsById(id))
        {
            response.setMessage("Service not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setMessage("Successfully found");
        contactRepository.findById(id).ifPresent(contact -> response.setData(new ContactDto(contact,lang)));
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<ApiResponse<Contact>> findById(Long id)
    {
        ApiResponse<Contact> response = new ApiResponse<>();
        if (!contactRepository.existsById(id))
        {
            response.setMessage("Service not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        contactRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(contactRepository.findById(id).get());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Contact>> create(Contact contact)
    {
        ApiResponse<Contact> response = new ApiResponse<>();
        try
        {
            Contact save = contactRepository.save(contact);
            response.setMessage("Successfully created");
            response.setData(save);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage("Error while creating contact: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<ApiResponse<Contact>> update(Long id,Contact contact)
    {
        ApiResponse<Contact> response = new ApiResponse<>();
        if (!contactRepository.existsById(id))
        {
            response.setMessage("Contact not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        contact.setId(id);
        contactRepository.save(contact);

        response.setMessage("Successfully updated");
        response.setData(contact);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteById(Long id)
    {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        if (!contactRepository.existsById(id))
        {
            apiResponse.setMessage("Contact not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        contactRepository.deleteById(id);
        apiResponse.setMessage("Successfully deleted");
        return ResponseEntity.ok(apiResponse);
    }
}
