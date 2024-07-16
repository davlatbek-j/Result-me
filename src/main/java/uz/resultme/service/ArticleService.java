package uz.resultme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Photo;
import uz.resultme.entity.article.Article;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.article.ArticleDTO;
import uz.resultme.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class ArticleService {

    private final ArticleRepository articleRepo;
    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<Article>> create(Article articleRequest) {

        ApiResponse<Article> response = new ApiResponse<>();
        Article article = new Article();
        article.setTitleUz(articleRequest.getTitleUz());
        article.setTitleRu(articleRequest.getTitleRu());
        article.setThemeUz(articleRequest.getThemeUz());
        article.setThemeRu(articleRequest.getThemeRu());
        article.setPlan(articleRequest.getPlan());
        article.setActive(articleRequest.getActive());

        Article saved = articleRepo.save(article);
        response.setMessage("Successfully created");
        response.setData(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    public ResponseEntity<ApiResponse<Article>> uploadImage(Long id, MultipartFile file) {
        ApiResponse<Article> response = new ApiResponse<>();

        if (!articleRepo.existsById(id)) {
            response.setMessage("Article with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Article article = articleRepo.findById(id).get();
        Photo main = photoService.save(file);
        article.setMainPhoto(main);
        Article saved = articleRepo.save(article);
        response.setMessage("Photo succesfully saved");
        response.setData(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<Article>> uploadGallery(Long id, List<MultipartFile> gallery) {
        ApiResponse<Article> response = new ApiResponse<>();
        if (!articleRepo.existsById(id)) {
            response.setMessage("Article with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Article article = articleRepo.findById(id).get();

        article.setGallery(new ArrayList<>());
        for (MultipartFile multipartFile : gallery)
            article.getGallery().add(photoService.save(multipartFile));

        Article saved = articleRepo.save(article);
        response.setMessage("Gallery succesfully saved");
        response.setData(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    public ResponseEntity<ApiResponse<ArticleDTO>> getById(Long id, String lang) {
        ApiResponse<ArticleDTO> response = new ApiResponse<>();
        if (!articleRepo.existsById(id)) {
            response.setMessage("Article with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Article article = articleRepo.findById(id).get();
        response.setMessage("Found article with id " + id);

        response.setData(new ArticleDTO(article, lang));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse<Article>> getById(Long id) {
        ApiResponse<Article> response = new ApiResponse<>();
        if (!articleRepo.existsById(id)) {
            response.setMessage("Article with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Article article = articleRepo.findById(id).get();
        response.setMessage("Found article with id " + id);
        response.setData(article);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<List<ArticleDTO>>> findAll(String lang) {
        ApiResponse<List<ArticleDTO>> response = new ApiResponse<>();
        List<Article> articles = articleRepo.findAll();
        response.setMessage("Found " + articles.size() + " article(s)");
        response.setData(new ArrayList<>());
        articles.forEach(i -> response.getData().add(new ArticleDTO(i, lang)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Article>> update(Long id, String json, MultipartFile newMainPhoto, MultipartFile newBodyPhoto, List<MultipartFile> newGallery) {
        ApiResponse<Article> response = new ApiResponse<>();
        if (!articleRepo.existsById(id)) {
            response.setMessage("Article with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Article newArticle = articleRepo.findById(id).get();

            Photo oldMain = newArticle.getMainPhoto();
            Photo oldBody = newArticle.getBodyPhoto();
            List<Photo> oldGallery = newArticle.getGallery();

            if (json != null) {
                ObjectMapper objMapper = new ObjectMapper();
                newArticle = objMapper.readValue(json, Article.class);
            }

            if (newMainPhoto == null || newMainPhoto.isEmpty())
                newArticle.setMainPhoto(oldMain);
            else
                newArticle.setMainPhoto(photoService.save(newMainPhoto));

            if (newGallery == null || newGallery.get(0).isEmpty())
                newArticle.setGallery(oldGallery);
            else {
                newArticle.setGallery(new ArrayList<>());
                for (MultipartFile multipartFile : newGallery)
                    if (multipartFile.getSize() > 0)
                        newArticle.getGallery().add(photoService.save(multipartFile));
            }

            if (newBodyPhoto == null || newBodyPhoto.isEmpty())
                newArticle.setBodyPhoto(oldBody);
            else {
                newArticle.setBodyPhoto(photoService.save(newBodyPhoto));
            }

            newArticle.setId(id);
            articleRepo.save(newArticle);

            response.setMessage("Successfully updated");
            response.setData(newArticle);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<ApiResponse<Article>> delete(Long id) {
        ApiResponse<Article> response = new ApiResponse<>();
        try {
            articleRepo.deleteById(id);
            response.setMessage("Successfully deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error deleting article" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
