package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Article;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.ArticleDTO;
import uz.resultme.service.ArticleService;

import java.util.List;

@RequiredArgsConstructor

@RequestMapping("/article")
@Controller
public class ArticleController
{
    private final ArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Article>> create(
            @RequestParam(name = "json") String jsonArticle,
            @RequestPart(name = "main-photo") MultipartFile mainPhoto,
            @RequestPart(name = "gallery") List<MultipartFile> gallery)
    {
        return articleService.create(jsonArticle, mainPhoto, gallery);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ArticleDTO>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accepted-Language") String lang)
    {
        return articleService.getById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> findAll(
            @RequestHeader(value = "Accepted-Language") String lang)
    {
        return articleService.findAll(lang);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Article>> update(
            @PathVariable Long id,
            @RequestParam(name = "json",required = false)String json,
            @RequestPart(name = "main-photo",required = false)MultipartFile mainPhoto,
            @RequestPart(name = "gallery",required = false) List<MultipartFile> gallery)
    {
        return articleService.update(id,json,mainPhoto,gallery);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Article>> delete(@PathVariable Long id)
    {
        return articleService.delete(id);
    }
}
