package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.resultme.entity.ProvidedResult;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.ProvidedResultDTO;
import uz.resultme.service.ResultService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/provided-results")
public class ProvidedResultController
{
    private final ResultService resultService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProvidedResult>> create(@RequestBody ProvidedResult providedResult)
    {
        return resultService.save(providedResult);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ProvidedResultDTO>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return resultService.findById(id,lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<ProvidedResultDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return resultService.getAll(lang);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ProvidedResult>> update(@PathVariable Long id, @RequestBody ProvidedResult providedResult)
    {
        return resultService.update(id,providedResult);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id)
    {
       return resultService.deleteById(id);
    }
}
