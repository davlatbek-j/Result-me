package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.ServiceEntityDTO;
import uz.resultme.service.ServiceEntityService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/service")
public class ServiceController
{
    private final ServiceEntityService entityService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ServiceEntityDTO>> create(
            @RequestParam(name = "icon") MultipartFile file,
            @RequestParam(name = "json") String json)
    {
        return entityService.create(json, file);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ServiceEntityDTO>> findById(@PathVariable Long id)
    {
        return entityService.findById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<ServiceEntityDTO>>> findAll()
    {
        return entityService.findAll();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<ServiceEntityDTO>> edit(
            @PathVariable Long id,
            @RequestParam(name = "icon") MultipartFile file,
            @RequestParam(name = "json") String json)
    {
        return entityService.update(id,json,file);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id)
    {
        return entityService.deleteById(id);
    }

}
