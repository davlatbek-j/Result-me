package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Background;
import uz.resultme.entity.Photo;
import uz.resultme.payload.BackgroundDTO;
import uz.resultme.repository.BackgroundRepository;
import uz.resultme.repository.PhotoRepository;

import java.io.IOException;

@RequiredArgsConstructor

@Service
public class BackgroundService
{
    private final BackgroundRepository backgroundRepo;
    private final PhotoService photoService;

    public ResponseEntity<BackgroundDTO> create(MultipartFile file)
    {
        System.out.println("file.getContentType() = " + file.getContentType());

        if (file.getContentType() == null ||
                !file.getContentType().equals("image/png"))
        {
            throw new RuntimeException("Unsupported content type: " + file.getContentType());
        }

        Photo photo = photoService.save(file);

        Background background = new Background(photo);
        backgroundRepo.save(background);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BackgroundDTO(background));
    }

}
