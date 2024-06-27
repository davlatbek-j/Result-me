package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import uz.resultme.entity.Contact;
import uz.resultme.entity.Phone;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.ContactDto;
import uz.resultme.repository.ContactRepository;
import uz.resultme.repository.PhoneRepository;
import uz.resultme.repository.PhotoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactService {


    private final ContactRepository contactRepository;
    private final PhotoRepository photoRepository;
    private final PhoneRepository phoneRepository;

    public ResponseEntity<ApiResponse<List<ContactDto>>> getAllContact() {
        List<Contact> all = contactRepository.findAll();
        ApiResponse<List<ContactDto>> response = new ApiResponse<>();
        response.setMessage("Successfully found " + all.size() + " item(s)");
        response.setData(new ArrayList<>());
        all.forEach(i -> response.getData().add(new ContactDto(i)));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<ContactDto>> findById(Long id) {
        ApiResponse<ContactDto> response = new ApiResponse<>();
        if (!contactRepository.existsById(id)) {
            response.setMessage("Service not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setMessage("Successfully found");
        contactRepository.findById(id).ifPresent(contact -> response.setData(new ContactDto(contact)));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ContactDto> create(
            String instagram,
            String telegram,
            String facebook,
            List<Phone> phone,
            String address) {
        List<Phone> phoneList=new ArrayList<>();
        phone.forEach(phone1 -> {
            Phone phone2=phoneRepository.save(phone1);
            phoneList.add(phone2);
        });
        Contact contact=new Contact();
        contact.setPhones(phoneList);
        contact.setFacebook(facebook);
        contact.setInstagram(instagram);
        contact.setTelegram(telegram);
        contact.setAddress(address);
        contactRepository.save(contact);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ContactDto(contact));

    }

    public ResponseEntity<ApiResponse<ContactDto>> update(
            Long id,
            String instagram,
            String telegram,
            String facebook,
            List<Phone> phone,
            String location) {

        ApiResponse<ContactDto> apiResponse = new ApiResponse<>();
        if (!contactRepository.existsById(id)) {
            apiResponse.setMessage("Service not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        Contact contact = contactRepository.findById(id).get();
        contact.setFacebook(facebook);
        contact.setInstagram(instagram);
        contact.setTelegram(telegram);
        contact.setPhones(phone);
        contact.setAddress(location);
        contactRepository.save(contact);


        apiResponse.setMessage("Successfully updated");
        apiResponse.setData(new ContactDto(contact));

        return ResponseEntity.ok(apiResponse);


    }

    public ResponseEntity<?> deleteById(Long id) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        if (!contactRepository.existsById(id)) {
            apiResponse.setMessage("Service not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        contactRepository.deleteById(id);
        apiResponse.setMessage("Successfully deleted");
        return ResponseEntity.ok(apiResponse);
    }
}
