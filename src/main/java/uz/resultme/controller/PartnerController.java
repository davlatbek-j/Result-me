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
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id, @RequestBody Partner partnerDetails) {
        try {
            Partner updatedPartner = partnerService.updatePartner(id, partnerDetails);
            return ResponseEntity.ok(updatedPartner);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        try {
            partnerService.deletePartner(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public List<Partner> getAllPartners() {
        return partnerService.getAllPartners();
    }
}
