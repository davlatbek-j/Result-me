package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Photo;
import uz.resultme.exception.IllegalPhotoTypeException;
import uz.resultme.exception.PhotoNotFoundExcpetion;
import uz.resultme.repository.PhotoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor

@Service
public class PhotoService
{
    @Value("${photo.upload.path}")
    private String photoUploadPath;

    private final PhotoRepository photoRepo;

    public Photo save(MultipartFile file)
    {
        if (!(file.getContentType().equals("image/png") ||
              file.getContentType().equals("image/svg+xml")||
                file.getContentType().equals("image/jpeg")))
        {
            throw new IllegalPhotoTypeException("Unsupported image type: " + file.getContentType());
        }

        try
        {
            File uploadDir = new File(photoUploadPath);

            if (!uploadDir.exists())
            {
                uploadDir.mkdirs();
            }
            Photo photo = new Photo();
            photoRepo.save(photo);

            String originalFilename = file.getOriginalFilename();
            File destinationFile = new File(uploadDir + "/" + photo.getId() + "-" + originalFilename);
//            File destinationFile = new File(uploadDir + "\\" + photo.getId() + "-" + originalFilename);
            file.transferTo(destinationFile);

            photo.setName(photo.getId() + "-" + originalFilename);
            photo.setFilepath(destinationFile.getAbsolutePath());
            photo.setType(file.getContentType());
            photo.setHttpUrl("http://213.230.91.55:9000/photo/" + photo.getName());

            return photoRepo.save(photo);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    public ResponseEntity<byte[]> findByNameOrId(String nameOrId)
    {
        try
        {
            Long id = null;
            try
            {
                id = Long.valueOf(nameOrId);
            } catch (NumberFormatException ignored)
            {
            }

            Photo photo = photoRepo.findByIdOrName(id, nameOrId);

            Path imagePath = Paths.get(photo.getFilepath());
            byte[] imageBytes = Files.readAllBytes(imagePath);

            if (photo.getType().equals("image/png"))
                return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
            else if (photo.getType().equals("image/jpeg"))
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
            else if (photo.getType().equals("image/svg+xml"))
            {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, "image/svg+xml");

                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            }

        } catch (IOException e)
        {
            e.printStackTrace();
            throw new PhotoNotFoundExcpetion(e.getMessage());
        }
        return null;
    }

}
