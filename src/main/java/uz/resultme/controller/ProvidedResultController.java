package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.resultme.entity.ProvidedResult;
import uz.resultme.payload.ApiResponse;
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

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<ProvidedResult>>> getAll()
    {
        return resultService.getAll();
    }

    @PutMapping("/edit/{id}")
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
