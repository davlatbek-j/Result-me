package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.payload.BackgroundDTO;
import uz.resultme.service.BackgroundService;

@RequiredArgsConstructor

@Controller
@RequestMapping("/background")
public class BackgroundController
{
    private final BackgroundService backgroundService;

    @PostMapping("/create")
    public ResponseEntity<BackgroundDTO> create(@RequestPart(name = "photo") MultipartFile file)
    {
        return backgroundService.create(file);
    }

}

