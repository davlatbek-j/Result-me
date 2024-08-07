package uz.resultme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Photo;
import uz.resultme.entity.article.Article;
import uz.resultme.entity.service.OptionValue;
import uz.resultme.entity.service.ServiceEntity;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.service.ServiceEntityDTO;
import uz.resultme.repository.OptionValueRepository;
import uz.resultme.repository.TableRepository;
import uz.resultme.repository.service.ServiceEntityRepository;
import uz.resultme.repository.service.ServiceOptionRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class ServiceEntityService
{
    private final ServiceEntityRepository serviceRepo;
    private final PhotoService photoService;
    private final ServiceOptionRepository serviceOptionRepo;
    private final TableRepository tableRepo;
    private final OptionValueRepository optionValueRepo;

    public ResponseEntity<ApiResponse<ServiceEntity>> create(ServiceEntity service)
    {
        ApiResponse<ServiceEntity> apiResponse = new ApiResponse<>();


        ServiceEntity service1=new ServiceEntity();

       service1.setNameUz(service.getNameUz());
       service1.setNameRu(service.getNameRu());
       service1.setOption(service.getOption());
       service1.setActive(service.getActive());

        ServiceEntity save = serviceRepo.save(service1);

        apiResponse.setData(serviceRepo.save(save));
            apiResponse.setMessage("Successfully created");

            return ResponseEntity.ok(apiResponse);



    }

    public ResponseEntity<ApiResponse<ServiceEntity>> uploadPhoto(long id,MultipartFile file){
        ApiResponse<ServiceEntity> apiResponse = new ApiResponse<>();
        if (!(file.getContentType().equals("image/png") ||
                file.getContentType().equals("image/svg+xml")))
        {
            apiResponse.setMessage("Invalid file , only image/png or image/svg+xml");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }


        if (!serviceRepo.existsById(id)) {
            apiResponse.setMessage("Servise with id " + id + " does not exist");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }

        ServiceEntity serviceEntity= serviceRepo.findById(id).get();
        Photo icon = photoService.save(file);
        serviceEntity.setIcon(icon);

        ServiceEntity save = serviceRepo.save(serviceEntity);
        apiResponse.setMessage("Photo succesfully saved");
        apiResponse.setData(save);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
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


    public ResponseEntity<ApiResponse<ServiceEntity>> update(Long id, ServiceEntity serviceEntity)
    {
        ApiResponse<ServiceEntity> apiResponse = new ApiResponse<>();
        if (!serviceRepo.existsById(id))
        {
            apiResponse.setMessage("Service not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }

            ServiceEntity newServiceEntity = serviceRepo.findById(id).get();

        if (serviceEntity.getNameUz()!=null||!serviceEntity.getNameUz().isEmpty()){
            newServiceEntity.setNameUz(serviceEntity.getNameUz());
        }
        if (serviceEntity.getNameRu()!=null||!serviceEntity.getNameRu().isEmpty()){
            newServiceEntity.setNameRu(serviceEntity.getNameRu());
        }
        if (serviceEntity.getOption()!=null||!serviceEntity.getOption().isEmpty()){
            newServiceEntity.setOption(serviceEntity.getOption());
        }
        if (serviceEntity.getActive()!=null||!serviceEntity.getActive()){
            newServiceEntity.setActive(serviceEntity.getActive());
        }
            newServiceEntity.setId(id);
            apiResponse.setData(serviceRepo.save(newServiceEntity));
            apiResponse.setMessage("Successfully updated");

            return ResponseEntity.ok(apiResponse);

    }

    public ResponseEntity<ApiResponse<ServiceEntity>> updatePhoto(Long id,MultipartFile file){

        ApiResponse<ServiceEntity> apiResponse = new ApiResponse<>();
        if (!(file.getContentType().equals("image/png") ||
                file.getContentType().equals("image/svg+xml")))
        {
            apiResponse.setMessage("Invalid file , only image/png or image/svg+xml");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        ServiceEntity serviceEntity = serviceRepo.findById(id).get();
       if (file!=null){
           serviceEntity.setIcon(photoService.save(file));
       }
       serviceEntity.setId(id);
        ServiceEntity save = serviceRepo.save(serviceEntity);
        apiResponse.setData(save);
        apiResponse.setMessage("Successfully updated");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

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

    public ResponseEntity<?> deleteTableOfOption(Long optionValueId)
    {
        ApiResponse<?> response = new ApiResponse<>();

        if (!optionValueRepo.existsById(optionValueId))
        {
            response.setMessage("Option Value not found by id: " + optionValueId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        OptionValue optionValue = optionValueRepo.findById(optionValueId).get();
        optionValue.setTableUrlRu(null);
        optionValue.setTableUrlUz(null);
        optionValueRepo.save(optionValue);

        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<ServiceEntity>> findById(Long id)
    {
        ApiResponse<ServiceEntity> response = new ApiResponse<>();
        if (!serviceRepo.existsById(id))
        {
            response.setMessage("Service not found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ServiceEntity serviceEntity = serviceRepo.findById(id).get();
        response.setMessage("Found");
        response.setData(serviceEntity);
        return ResponseEntity.ok(response);
    }
}
