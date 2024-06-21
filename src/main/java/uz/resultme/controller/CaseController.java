package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Case;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.CaseDTO;
import uz.resultme.service.CaseService;
import uz.resultme.service.ServiceEntityService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/case")
public class CaseController
{

    private final CaseService caseService;
    private final ServiceEntityService serviceEntityService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Case>> createCase(
            @RequestParam(name = "json") String caseJson,
            @RequestParam(name = "main-photo") MultipartFile mainPhoto,
            @RequestParam(name = "gallery") List<MultipartFile> gallery)
    {
        return caseService.create(caseJson,mainPhoto,gallery);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<CaseDTO>> getCaseById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return caseService.findById(id,lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<CaseDTO>>> getCaseById(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return caseService.findAll(lang);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCaseById(@PathVariable Long id)
    {
       return caseService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Case>> updateCaseById(
            @PathVariable Long id,
            @RequestParam(name = "json",required = false) String caseJson,
            @RequestParam(name = "main-photo",required = false) MultipartFile mainPhoto,
            @RequestParam(name = "gallery",required = false) List<MultipartFile> gallery)
    {
        return caseService.update(id,caseJson,mainPhoto,gallery);
    }

}
