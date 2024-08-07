package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Background;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.BackgroundDTO;
import uz.resultme.service.BackgroundService;

@RequiredArgsConstructor

@Controller
@RequestMapping("/background")
public class BackgroundController
{
    private final BackgroundService backgroundService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BackgroundDTO>> create(
            @RequestParam(name = "active") Boolean active
            )
    {
        return backgroundService.create(active);
    }

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<Background>> create(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "active") MultipartFile file
    )
    {
        return backgroundService.uploadPhoto(id,file);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<BackgroundDTO>> getById(@PathVariable(name = "id") Long id)
    {
       return backgroundService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteById(@PathVariable Long id)
    {
       return backgroundService.deleteById(id);
    }

}

