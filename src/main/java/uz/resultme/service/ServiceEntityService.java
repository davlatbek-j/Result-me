package uz.resultme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Photo;
import uz.resultme.entity.service.ServiceEntity;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.service.ServiceEntityDTO;
import uz.resultme.repository.ServiceEntityRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class ServiceEntityService
{
    private final ServiceEntityRepository serviceRepo;
    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<ServiceEntity>> create(String json, MultipartFile file)
    {
        ApiResponse<ServiceEntity> apiResponse = new ApiResponse<>();

        if (!(file.getContentType().equals("image/png") ||
                file.getContentType().equals("image/svg+xml")))
        {
            apiResponse.setMessage("Invalid file , only image/png or image/svg+xml");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try
        {
            ServiceEntity serviceEntity = objectMapper.readValue(json, ServiceEntity.class);

            Photo icon = photoService.save(file);
            serviceEntity.setIcon(icon);

            apiResponse.setData(serviceRepo.save(serviceEntity));
            apiResponse.setMessage("Successfully created");

            return ResponseEntity.ok(apiResponse);

        } catch (JsonProcessingException e)
        {
            apiResponse.setMessage("Problem with processing json : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }

    }

    public ResponseEntity<ApiResponse<ServiceEntityDTO>> findById(Long id, String lang)
    {
        ApiResponse<ServiceEntityDTO> response = new ApiResponse<>();
        if (!serviceRepo.existsById(id))
        {
            response.setMessage("Service not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setMessage(lang);
        serviceRepo.findById(id).ifPresent(serviceEntity -> response.setData(new ServiceEntityDTO(serviceEntity, lang)));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<List<ServiceEntityDTO>>> findAll(String lang)
    {
        List<ServiceEntity> all = serviceRepo.findAll();
        ApiResponse<List<ServiceEntityDTO>> response = new ApiResponse<>();
        response.setMessage("Successfully found " + all.size() + " item(s)");
        response.setData(new ArrayList<>());
        all.forEach(i -> response.getData().add(new ServiceEntityDTO(i, lang)));
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<ApiResponse<ServiceEntity>> update(Long id, String json, MultipartFile newIcon)
    {
        ApiResponse<ServiceEntity> apiResponse = new ApiResponse<>();
        if (!serviceRepo.existsById(id))
        {
            apiResponse.setMessage("Service not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        try
        {
            ServiceEntity newServiceEntity = serviceRepo.findById(id).get();
            Photo oldIcon = newServiceEntity.getIcon();

            if (json != null)
            {
                ObjectMapper objectMapper = new ObjectMapper();
                newServiceEntity = objectMapper.readValue(json, ServiceEntity.class);
            }

            if (!newIcon.isEmpty())   //If not empty
                newServiceEntity.setIcon(photoService.save(newIcon));
            else
                newServiceEntity.setIcon(oldIcon);

            newServiceEntity.setId(id);
            apiResponse.setData(serviceRepo.save(newServiceEntity));
            apiResponse.setMessage("Successfully updated");

            return ResponseEntity.ok(apiResponse);
        } catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> deleteById(Long id)
    {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        if (!serviceRepo.existsById(id))
        {
            apiResponse.setMessage("Service not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        serviceRepo.deleteById(id);
        apiResponse.setMessage("Successfully deleted");
        return ResponseEntity.ok(apiResponse);
    }

}
