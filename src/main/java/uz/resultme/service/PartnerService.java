package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Partner;
import uz.resultme.entity.Photo;
import uz.resultme.exception.IllegalPhotoTypeException;
import uz.resultme.payload.PartnerDTO;
import uz.resultme.repository.PartnerRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PartnerService {
    private final PartnerRepository partnerRepository;
    private final PhotoService photoService;

    public ResponseEntity<PartnerDTO> addPartner(MultipartFile file){
        if (file.getContentType() == null ||
                !file.getContentType().equals("image/png")||!file.getContentType().equals("image/svg"))
        {
            throw new IllegalPhotoTypeException("Unsupported image type: " + file.getContentType());

        }

        Photo photo = photoService.save(file);
        Partner partner=new Partner(photo);
        partnerRepository.save(partner);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PartnerDTO(partner));
    }

    public Partner updatePartner(Long id, Partner partnerDetails) {
        Optional<Partner> optionalPartner = partnerRepository.findById(id);
        if (optionalPartner.isPresent()) {
            Partner existingPartner = optionalPartner.get();
            existingPartner.setPhoto(partnerDetails.getPhoto());
            return partnerRepository.save(existingPartner);
        } else {
            throw new RuntimeException("Partner not found with id " + id);
        }
    }

    public void deletePartner(Long id) {
        if (partnerRepository.existsById(id)) {
            partnerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Partner not found with id " + id);
        }
    }
    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }

    public Partner getPartnerByUrl(String url){
        return partnerRepository.findPartnerByPartnerUrl(url);
    }
}
