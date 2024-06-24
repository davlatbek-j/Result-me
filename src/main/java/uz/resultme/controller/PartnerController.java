package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.PartnerDTO;
import uz.resultme.service.PartnerService;
import uz.resultme.service.PhotoService;

@RequiredArgsConstructor

@Controller
@RequestMapping("/partner")
public class PartnerController {
    private final PartnerService partnerService;

    @PostMapping("/add")
    ResponseEntity<PartnerDTO> addPartner(@RequestParam(name = "photo") MultipartFile file){

        return partnerService.addPartner(file);
    }

}
