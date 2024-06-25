package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.service.ServiceEntity;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.service.ServiceEntityDTO;
import uz.resultme.service.ServiceEntityService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/service")
public class ServiceController
{
    private final ServiceEntityService entityService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ServiceEntity>> create(
            @RequestPart(name = "icon") MultipartFile file,
            @RequestParam(name = "json") String json)
    {
        return entityService.create(json, file);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ServiceEntityDTO>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return entityService.findById(id,lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<ServiceEntityDTO>>> findAll(
            @RequestHeader(value = "Accept-Language")String lang)
    {
        return entityService.findAll(lang);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<ServiceEntity>> edit(
            @PathVariable Long id,
            @RequestParam(name = "icon",required = false) MultipartFile file,
            @RequestParam(name = "json",required = false) String json)
    {
        return entityService.update(id, json, file);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id)
    {
        return entityService.deleteById(id);
    }

}
