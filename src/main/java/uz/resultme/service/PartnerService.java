package uz.resultme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Partner;
import uz.resultme.entity.Photo;
import uz.resultme.entity.ServiceEntity;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.PartnerDTO;
import uz.resultme.payload.ServiceEntityDTO;
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
            throw new RuntimeException("Unsupported content type: " + file.getContentType());
        }

        Photo photo = photoService.save(file);
        Partner partner=new Partner(photo);
        partnerRepository.save(partner);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PartnerDTO(partner));
    }

    public ResponseEntity<ApiResponse<PartnerDTO>> updatePartner(Long id, MultipartFile file, String url) {

        ApiResponse<PartnerDTO> apiResponse=new ApiResponse<>();
        if (!partnerRepository.existsById(id))
        {
            apiResponse.setMessage("Service not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        Partner partner = partnerRepository.findById(id).get();
        partner.setPhoto(photoService.save(file));
        partner.setPartnerUrl(url);
        partnerRepository.save(partner);


        apiResponse.setMessage("Successfully updated");
        apiResponse.setData(new PartnerDTO(partner));

        return ResponseEntity.ok(apiResponse);









    }

    public ResponseEntity<?> deleteById(Long id)
    {
        ApiResponse<Object> response = new ApiResponse<>();
        if (!partnerRepository.existsById(id))
        {
            response.setMessage("Not Found by id:" + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        partnerRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }




    }

