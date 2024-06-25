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
import uz.resultme.service.PhotoService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/partner")
public class PartnerController {
    private final PartnerService partnerService;

    @PostMapping("/add")
    ResponseEntity<PartnerDTO> addPartner(@RequestParam(name = "photo") MultipartFile file){

        return partnerService.addPartner(file);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PartnerDTO>> updatePartner(@PathVariable Long id,
                                                 @RequestParam(name = "@photo") MultipartFile file
                                                 ,@RequestParam(name = "url") String url) {
        return partnerService.updatePartner(id,file,url);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePartner(@PathVariable Long id) {
       return partnerService.deleteById(id);
    }
    @GetMapping
    public List<Partner> getAllPartners() {
        return partnerService.getAllPartners();
    }

    /*@GetMapping("/{url}")
    public Partner getPartnerByUrl(@PathVariable String url){
        return partnerService.getPartnerByUrl(url);
    }*/
}
