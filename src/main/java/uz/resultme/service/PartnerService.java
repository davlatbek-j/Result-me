package uz.resultme.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Article;
import uz.resultme.entity.Partner;
import uz.resultme.entity.Photo;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.ArticleDTO;
import uz.resultme.payload.PartnerDTO;
import uz.resultme.repository.PartnerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PartnerService {
    private final PartnerRepository partnerRepository;
    private final PhotoService photoService;

    public ResponseEntity<PartnerDTO> addPartner(MultipartFile file,String url){

        ApiResponse<Partner> response=new ApiResponse<>();
        ObjectMapper objectMapper = new ObjectMapper();
        if (file.getContentType() == null ||
                !file.getContentType().equals("image/png")||!file.getContentType().equals("image/svg"))
        {
            throw new RuntimeException("Unsupported content type: " + file.getContentType());
        }

        Photo photo = photoService.save(file);
        Partner partner=new Partner(photo,url);
        partnerRepository.save(partner);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PartnerDTO(partner));
    }

    public ResponseEntity<ApiResponse<Partner>> updatePartner(Long id, MultipartFile file, String url) {
        ApiResponse<Partner> response=new ApiResponse<>();
        if (!partnerRepository.existsById(id)){
            response.setMessage("Partner with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Partner newPartner=partnerRepository.findById(id).get();
        Photo oldPhoto=newPartner.getPhoto();

        if (file==null|| file.isEmpty()){
            newPartner.setPhoto(oldPhoto);
        }else {
            newPartner.setPhoto(photoService.save(file));
        }
        if(url==null||url.isEmpty()){
            newPartner.setPartnerUrl(newPartner.getPartnerUrl());
        }else {
            newPartner.setPartnerUrl(url);
        }
        newPartner.setId(id);
        partnerRepository.save(newPartner);

        response.setMessage("Successfully updated");
        response.setData(newPartner);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    public ResponseEntity<ApiResponse<PartnerDTO>> getByUrl(String url)
    {
        ApiResponse<PartnerDTO> response = new ApiResponse<>();
        Partner partner = partnerRepository.findPartnerByPartnerUrl(url);
        response.setMessage("Found partner with url " + url);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<List<PartnerDTO>>> findAll()
    {
        ApiResponse<List<PartnerDTO>> response = new ApiResponse<>();
        List<Partner> partners = partnerRepository.findAll();
        response.setMessage("Found " + partners.size() + " article(s)");
        response.setData(new ArrayList<>());
        partners.forEach(i -> response.getData().add(new PartnerDTO(i)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Partner>> delete(Long id)
    {
        ApiResponse<Partner> response = new ApiResponse<>();
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
