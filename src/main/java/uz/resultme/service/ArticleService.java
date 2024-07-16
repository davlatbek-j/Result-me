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

    public ResponseEntity<ApiResponse<Article>> create(String jsonArticle/* List<MultipartFile> gallery*/) {
        ApiResponse<Article> response = new ApiResponse<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Article article = objectMapper.readValue(jsonArticle, Article.class);


           /* article.setGallery(new ArrayList<>());
            for (MultipartFile multipartFile : gallery)
                article.getGallery().add(photoService.save(multipartFile));*/

            Article saved = articleRepository.save(article);
            response.setMessage("Successfully created");
            response.setData(saved);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            response.setMessage("Error on parsing json");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ApiResponse<Article>> uploadImage(Long id, MultipartFile file) {
        ApiResponse<Article> response = new ApiResponse<>();

        if (!articleRepository.existsById(id)) {
            response.setMessage("Article with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Article article = articleRepository.findById(id).get();
        Photo main = photoService.save(file);
        article.setMainPhoto(main);
        Article saved = articleRepository.save(article);
        response.setMessage("Photo succesfully saved");
        response.setData(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<Article>> uploadGallery(Long id,List<MultipartFile> gallery){
        ApiResponse<Article> response = new ApiResponse<>();
        if (!articleRepository.existsById(id)) {
            response.setMessage("Article with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Article article = articleRepository.findById(id).get();

        article.setGallery(new ArrayList<>());
        for (MultipartFile multipartFile : gallery)
            article.getGallery().add(photoService.save(multipartFile));

        Article saved = articleRepository.save(article);
        response.setMessage("Gallery succesfully saved");
        response.setData(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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

        response.setData(new ArticleDTO(article, lang));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<List<ArticleDTO>>> findAll(String lang)
    {
        ApiResponse<List<ArticleDTO>> response = new ApiResponse<>();
        List<Article> articles = articleRepository.findAll();
        response.setMessage("Found " + articles.size() + " article(s)");
        response.setData(new ArrayList<>());
        articles.forEach(i -> response.getData().add(new ArticleDTO(i, lang)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Article>> update(Long id, String json, MultipartFile newMainPhoto, List<MultipartFile> newGallery)
    {
        ApiResponse<Article> response = new ApiResponse<>();
        if (!articleRepository.existsById(id))
        {
            response.setMessage("Article with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try
        {
            Article newArticle = articleRepository.findById(id).get();

            Photo oldMain = newArticle.getMainPhoto();
            List<Photo> oldGallery = newArticle.getGallery();

            if (json != null)
            {
                ObjectMapper objMapper = new ObjectMapper();
                newArticle = objMapper.readValue(json, Article.class);
            }

            if (newMainPhoto == null || newMainPhoto.isEmpty())
                newArticle.setMainPhoto(oldMain);
            else
                newArticle.setMainPhoto(photoService.save(newMainPhoto));

            if (newGallery == null || newGallery.get(0).isEmpty())
                newArticle.setGallery(oldGallery);
            else
            {
                newArticle.setGallery(new ArrayList<>());
                for (MultipartFile multipartFile : newGallery)
                    if (multipartFile.getSize() > 0)
                        newArticle.getGallery().add(photoService.save(multipartFile));
            }

            newArticle.setId(id);
            articleRepository.save(newArticle);

            response.setMessage("Successfully updated");
            response.setData(newArticle);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<ApiResponse<Article>> delete(Long id)
    {
        ApiResponse<Article> response = new ApiResponse<>();
        try
        {
            articleRepository.deleteById(id);
            response.setMessage("Successfully deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
