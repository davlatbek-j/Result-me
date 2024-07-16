package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Background;
import uz.resultme.entity.Photo;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.BackgroundDTO;
import uz.resultme.repository.BackgroundRepository;

@RequiredArgsConstructor

@Service
public class BackgroundService
{
    private final BackgroundRepository backgroundRepo;
    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<BackgroundDTO>> create(Boolean active, MultipartFile file)
    {
        ApiResponse<BackgroundDTO> response = new ApiResponse<>();
        if (file.getContentType() != null &&
                !(file.getContentType().equals("image/png") ||
                        file.getContentType().equals("image/svg+xml")))
        {
            response.setMessage("Unsupported image type: " + file.getContentType());
            return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        Photo photo = photoService.save(file);

        Background background = new Background(photo);
        background.setActive(active);
        backgroundRepo.save(background);

        response.setMessage("Created");
        response.setData(new BackgroundDTO(background));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
