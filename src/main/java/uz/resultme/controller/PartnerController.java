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
    ResponseEntity<ApiResponse<Partner>> addPartner(

            @RequestBody Partner partner)
    {
        return partnerService.addPartner(partner);
    }

    @PostMapping("/image")
    ResponseEntity<ApiResponse<Partner>> uploadImage(
           @RequestParam(name = "id") Long id,
            @RequestParam(name = "photo") MultipartFile file
    ){
        return partnerService.uploadImage(id,file);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<PartnerDTO>>> getAllPartners()
    {
        return partnerService.findAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<PartnerDTO>> getPartnerByUrl(@PathVariable Long id)
    {
        return partnerService.getById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Partner>> updatePartner(
            @PathVariable Long id,
            @RequestBody Partner partner)
    {
        return partnerService.updatePartner(id, partner);
    }


    @PutMapping("/update/image/{id}")
    public ResponseEntity<ApiResponse<Partner>> updatePartnerImage(
            @PathVariable Long id,
            @RequestParam(name = "photo") MultipartFile file
    ){
        return partnerService.updateImage(id,file);
}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePartner(@PathVariable Long id)
    {
        return partnerService.delete(id);
    }

}
