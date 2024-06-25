package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.resultme.entity.Location;
import uz.resultme.payload.ApiResponse;
import uz.resultme.service.LocationService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/location")
public class LocationController
{
    private final LocationService locationService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Location>> create(@RequestBody Location location)
    {
        return locationService.create(location);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Location>> get(@PathVariable Long id)
    {
        return locationService.findById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<Location>>> getAll()
    {
        return locationService.findAll();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Location>> update(@PathVariable Long id,@RequestBody Location location)
    {
        return locationService.update(id,location);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id)
    {
        return locationService.deleteById(id);
    }
}
