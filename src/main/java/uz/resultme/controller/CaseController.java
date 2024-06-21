package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Case;
import uz.resultme.payload.ApiResponse;
import uz.resultme.service.CaseService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/case")
public class CaseController
{

    private final CaseService caseService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Case>> createCase(
            @RequestParam(name = "json") String caseJson,
            @RequestParam(name = "main-photo") MultipartFile mainPhoto,
            @RequestParam(name = "gallery") List<MultipartFile> gallery)
    {
        return caseService.create(caseJson,mainPhoto,gallery);
    }
}
