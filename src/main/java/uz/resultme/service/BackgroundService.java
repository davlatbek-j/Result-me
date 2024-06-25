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

    public ResponseEntity<ApiResponse<BackgroundDTO>> create(MultipartFile file)
    {
        ApiResponse<BackgroundDTO> response = new ApiResponse<>();
        if (file.getContentType() != null &&
          !(file.getContentType().equals("image/png") ||
            file.getContentType().equals("image/svg+xml")) )
        {
            response.setMessage("Unsupported image type: " + file.getContentType());
            return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        Photo photo = photoService.save(file);

        Background background = new Background(photo);
        backgroundRepo.save(background);

        response.setMessage("Created");
        response.setData(new BackgroundDTO(background));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
