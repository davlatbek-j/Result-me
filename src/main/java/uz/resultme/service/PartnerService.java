package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Partner;
import uz.resultme.entity.Photo;
import uz.resultme.entity.article.Article;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.PartnerDTO;
import uz.resultme.repository.PartnerRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PartnerService
{
    private final PartnerRepository partnerRepository;
    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<Partner>> addPartner( Partner partner)
    {

        ApiResponse<Partner> response = new ApiResponse<>();



        Partner partner1 = new Partner();
        partner1.setPartnerUrl(partner.getPartnerUrl());
        Partner save = partnerRepository.save(partner1);

        response.setMessage("Created");
        response.setData(save);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<ApiResponse<Partner>> uploadImage(Long id ,MultipartFile file){
        ApiResponse<Partner> response = new ApiResponse<>();

        if (!(file.getContentType().equals("image/png") ||
                file.getContentType().equals("image/svg+xml")))
        {
            response.setMessage("Invalid file , only image/png or image/svg+xml");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (!partnerRepository.existsById(id)) {
            response.setMessage("Article with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Partner partner = partnerRepository.findById(id).get();
        Photo main = photoService.save(file);
        partner.setPhoto(main);

        Partner save = partnerRepository.save(partner);
        response.setMessage("Photo succesfully saved");
        response.setData(save);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<Partner>> updatePartner(Long id, Partner partner)
    {
        ApiResponse<Partner> response = new ApiResponse<>();
        if (!partnerRepository.existsById(id))
        {
            response.setMessage("Partner with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Partner newPartner = partnerRepository.findById(id).get();
       // Photo oldPhoto = newPartner.getPhoto();

        /*if (file == null || file.isEmpty())
        {
            newPartner.setPhoto(oldPhoto);
        } else
        {
            newPartner.setPhoto(photoService.save(file));
        }*/
        if (partner.getPartnerUrl() == null || partner.getPartnerUrl().isEmpty())
        {
            newPartner.setPartnerUrl(newPartner.getPartnerUrl());
        } else
        {
            newPartner.setPartnerUrl(partner.getPartnerUrl());
        }
        newPartner.setId(id);
        partnerRepository.save(newPartner);

        response.setMessage("Successfully updated");
        response.setData(newPartner);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<ApiResponse<Partner>> updateImage(Long id,MultipartFile file){

        ApiResponse<Partner> response = new ApiResponse<>();

        if (!(file.getContentType().equals("image/png") ||
                file.getContentType().equals("image/svg+xml")))
        {
            response.setMessage("Invalid file , only image/png or image/svg+xml");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (!partnerRepository.existsById(id))
        {
            response.setMessage("Partner with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Partner newPartner = partnerRepository.findById(id).get();
         Photo oldPhoto = newPartner.getPhoto();

        if (file == null || file.isEmpty())
        {
            newPartner.setPhoto(oldPhoto);
        } else
        {
            newPartner.setPhoto(photoService.save(file));
        }

        newPartner.setId(id);
        partnerRepository.save(newPartner);

        response.setMessage("Successfully updated");
        response.setData(newPartner);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    public ResponseEntity<ApiResponse<PartnerDTO>> getById(Long id)
    {
        ApiResponse<PartnerDTO> response = new ApiResponse<>();
        if (!partnerRepository.existsById(id)) {
            response.setMessage("Partner not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Partner partner = partnerRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(new PartnerDTO(partner));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<List<PartnerDTO>>> findAll()
    {
        ApiResponse<List<PartnerDTO>> response = new ApiResponse<>();
        List<Partner> partners = partnerRepository.findAll();
        response.setMessage("Found " + partners.size() + " partner(s)");
        response.setData(new ArrayList<>());
        partners.forEach(i -> response.getData().add(new PartnerDTO(i)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Partner>> delete(Long id)
    {
        ApiResponse<Partner> response = new ApiResponse<>();

        if (!partnerRepository.existsById(id))
        {
            response.setMessage("Partner with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try
        {
            partnerRepository.deleteById(id);
            response.setMessage("Successfully deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
