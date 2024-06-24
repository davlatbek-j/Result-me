package uz.resultme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Article;
import uz.resultme.entity.Photo;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.ArticleDTO;
import uz.resultme.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class ArticleService
{

    private final ArticleRepository articleRepository;
    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<Article>> create(String jsonArticle, MultipartFile mainPhoto, List<MultipartFile> gallery)
    {
        ApiResponse<Article> response = new ApiResponse<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            Article article = objectMapper.readValue(jsonArticle, Article.class);

            Photo main = photoService.save(mainPhoto);
            article.setMainPhoto(main);

            article.setGallery(new ArrayList<>());
            for (MultipartFile multipartFile : gallery)
                article.getGallery().add(photoService.save(multipartFile));

            Article saved = articleRepository.save(article);
            response.setMessage("Successfully created");
            response.setData(saved);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (JsonProcessingException e)
        {
            response.setMessage("Error on parsing json " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<ApiResponse<ArticleDTO>> getById(Long id, String lang)
    {
        ApiResponse<ArticleDTO> response = new ApiResponse<>();
        if (!articleRepository.existsById(id))
        {
            response.setMessage("Article with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Article article = articleRepository.findById(id).get();
        response.setMessage("Found article with id " + id);

        response.setData(new ArticleDTO(article,lang));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
