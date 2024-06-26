package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.resultme.entity.Entrance;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.EntranceDTO;
import uz.resultme.service.EntranceService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/entrance")
public class EntranceController
{
    private final EntranceService entranceService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Entrance>> createEntrance(@RequestBody Entrance entrance)
    {
        entrance.setId(null);
        return entranceService.create(entrance);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<EntranceDTO>> getEntranceById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return entranceService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<EntranceDTO>>> getEntranceById(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return entranceService.findAll(lang);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Entrance>> updateEntrance(
            @PathVariable Long id,
            @RequestBody Entrance entrance)
    {
        return entranceService.update(id, entrance);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteEntrance(@PathVariable Long id)
    {
        return entranceService.deleteById(id);
    }

}
