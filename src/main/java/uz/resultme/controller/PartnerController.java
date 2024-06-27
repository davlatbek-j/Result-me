package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Partner;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.PartnerDTO;
import uz.resultme.service.PartnerService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/partner")
public class PartnerController
{
    private final PartnerService partnerService;

    @PostMapping("/create")
    ResponseEntity<ApiResponse<PartnerDTO>> addPartner(
            @RequestParam(name = "photo") MultipartFile file,
            @RequestParam(name = "url") String url)
    {
        return partnerService.addPartner(file, url);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Partner>> updatePartner(
            @PathVariable Long id,
            @RequestParam(name = "photo") MultipartFile file,
            @RequestParam(name = "url") String url)
    {
        return partnerService.updatePartner(id, file, url);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePartner(@PathVariable Long id)
    {
        return partnerService.delete(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<PartnerDTO>>> getAllPartners()
    {
        return partnerService.findAll();
    }

    @GetMapping("/get/{url}")
    public ResponseEntity<ApiResponse<PartnerDTO>> getPartnerByUrl(@PathVariable String url)
    {
        return partnerService.getByUrl(url);
    }
}
