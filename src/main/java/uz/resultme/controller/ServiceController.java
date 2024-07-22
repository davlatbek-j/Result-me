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
            @RequestBody ServiceEntity serviceEntity)
    {
        return entityService.create(serviceEntity);
    }

    @PostMapping("/icon")
    public ResponseEntity<ApiResponse<ServiceEntity>> uploadIcon(
            @RequestParam(name = "id") Long id,
            @RequestPart(name = "icon") MultipartFile file)
    {
        return entityService.uploadPhoto(id,file);
    }



    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ServiceEntityDTO>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language",defaultValue = "ru") String lang)
    {
        return entityService.findById(id,lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<ServiceEntity>> findById(@PathVariable Long id)
    {
        return entityService.findById(id);
    }


    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<ServiceEntityDTO>>> findAll(
            @RequestHeader(value = "Accept-Language",defaultValue = "ru")String lang)
    {
        return entityService.findAll(lang);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<ServiceEntity>> edit(
            @PathVariable Long id,
            @RequestBody ServiceEntity service)
    {
        return entityService.update(id, service);
    }

    @PutMapping("/edit/icon/{id}")
    public ResponseEntity<ApiResponse<ServiceEntity>> edit(
            @PathVariable Long id,
            @RequestParam MultipartFile file)
    {
        return entityService.uploadPhoto(id, file);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id)
    {
        return entityService.deleteById(id);
    }

    @DeleteMapping("/delete-table")
    public ResponseEntity<?> deleteTableOfOption(@RequestParam("option-value-id") Long optionValueId)
    {
        return entityService.deleteTableOfOption(optionValueId);
    }

}
