package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Background;
import uz.resultme.entity.Photo;
import uz.resultme.entity.cases.Case;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.BackgroundDTO;
import uz.resultme.repository.BackgroundRepository;

@RequiredArgsConstructor

@Service
public class BackgroundService
{
    private final BackgroundRepository backgroundRepo;
    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<BackgroundDTO>> create(Boolean active)
    {
        ApiResponse<BackgroundDTO> response = new ApiResponse<>();


        Background background = new Background();
        background.setActive(active);
        backgroundRepo.save(background);

        response.setMessage("Created");
        response.setData(new BackgroundDTO(background));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<ApiResponse<Background>> uploadPhoto(Long id, MultipartFile file){
        ApiResponse<Background> response = new ApiResponse<>();

        if (!(file.getContentType().equals("image/png") ||
                file.getContentType().equals("image/svg+xml")))
        {
            response.setMessage("Invalid file , only image/png or image/svg+xml");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (!backgroundRepo.existsById(id)) {
            response.setMessage("Background with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Background background=backgroundRepo.findById(id).get();
        background.setPhoto(photoService.save(file));
        Background save = backgroundRepo.save(background);

        response.setMessage("Successfully updated");
        response.setData(save);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id)
    {
        ApiResponse<BackgroundDTO> response = new ApiResponse<>();

        if (!backgroundRepo.existsById(id))
        {
            response.setMessage("Background not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        backgroundRepo.deleteById(id);
        response.setMessage("Deleted");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    public ResponseEntity<ApiResponse<BackgroundDTO>> getById(Long id)
    {
        ApiResponse<BackgroundDTO> response = new ApiResponse<>();
        if (!backgroundRepo.existsById(id))
        {
            response.setMessage("Background not found by id: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Background background = backgroundRepo.findById(id).get();
        response.setMessage("Found");
        response.setData(new BackgroundDTO(background));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
