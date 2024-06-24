package uz.resultme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Case;
import uz.resultme.entity.Photo;
import uz.resultme.payload.*;
import uz.resultme.repository.CaseRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class CaseService
{
    private final CaseRepository caseRepository;
    private final PhotoService photoService;


    public ResponseEntity<ApiResponse<Case>> create(String caseJson, MultipartFile mainPhoto, List<MultipartFile> gallery)
    {
        ApiResponse<Case> response = new ApiResponse<>();
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();

            Case aCase = objectMapper.readValue(caseJson, Case.class);

            aCase.setMainPhoto(photoService.save(mainPhoto));

            aCase.setGallery(new ArrayList<>());
            for (MultipartFile multipartFile : gallery)
                aCase.getGallery().add(photoService.save(multipartFile));

            Case save = caseRepository.save(aCase);
            response.setMessage("Case created");
            response.setData(save);

            return ResponseEntity.ok(response);

        } catch (JsonProcessingException e)
        {
            response.setMessage("Error on parsing json ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<ApiResponse<CaseDTO>> findById(Long id, String lang)
    {
        ApiResponse<CaseDTO> response = new ApiResponse<>();
        if (!caseRepository.existsById(id))
        {
            response.setMessage("Case not found by id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Case aCase = caseRepository.findById(id).get();

        CaseDTO dto = new CaseDTO(aCase, lang);
        response.setMessage(lang);
        response.setData(dto);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<List<CaseDTO>>> findAll(String lang)
    {
        ApiResponse<List<CaseDTO>> response = new ApiResponse<>();

        List<Case> caseList = caseRepository.findAll();

        List<CaseDTO> caseDTOList = new ArrayList<>();

        try
        {
            caseList.forEach(i -> caseDTOList.add(new CaseDTO(i, lang)));
            response.setMessage(lang);
            response.setData(caseDTOList);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e)
        {
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id)
    {
        if (!caseRepository.existsById(id))
        {
            ApiResponse<?> response = new ApiResponse<>();
            response.setMessage("Case not found by id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        try
        {
            caseRepository.deleteById(id);
            return ResponseEntity.ok().body(new ApiResponse<>("Succesfully deleted", null));
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<ApiResponse<Case>> update(Long id, String caseJson, MultipartFile newMainPhoto, List<MultipartFile> newGallery)
    {
        ApiResponse<Case> response = new ApiResponse<>();
        if (!caseRepository.existsById(id))
        {
            response.setMessage("Case not found by id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        try
        {
            Case newCase = caseRepository.findById(id).get();
            Photo oldMainPhoto = newCase.getMainPhoto();
            List<Photo> oldGallery = newCase.getGallery();

            newCase.setId(id);

            if (caseJson != null)
            {
                ObjectMapper objectMapper = new ObjectMapper();
                newCase = objectMapper.readValue(caseJson, Case.class);
            }

            if (newMainPhoto != null) //If not empty
                newCase.setMainPhoto(photoService.save(newMainPhoto));
            else
                newCase.setMainPhoto(oldMainPhoto);

            if (newGallery != null) //If not empty
            {
                newCase.setGallery(new ArrayList<>());

                for (MultipartFile multipartFile : newGallery)
                    newCase.getGallery().add(photoService.save(multipartFile));
            } else
                newCase.setGallery(oldGallery);

            newCase.setId(id);
            Case save = caseRepository.save(newCase);

            response.setMessage("Updated");
            response.setData(save);
            return ResponseEntity.ok(response);

        } catch (JsonProcessingException e)
        {
            response.setMessage("Error on parsing json ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
