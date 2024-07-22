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
import uz.resultme.entity.cases.Case;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.cases.CaseDTO;
import uz.resultme.repository.CaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
public class CaseService {
    private final CaseRepository caseRepository;
    private final PhotoService photoService;


    public ResponseEntity<ApiResponse<Case>> create(Case caseRequest) {
        ApiResponse<Case> response = new ApiResponse<>();
        Case aCase = new Case();
        aCase.setTitleUz(caseRequest.getTitleUz());
        aCase.setTitleRu(caseRequest.getTitleRu());
        aCase.setDescriptionUz(caseRequest.getDescriptionUz());
        aCase.setDescriptionRu(caseRequest.getDescriptionRu());
        aCase.setNameUz(caseRequest.getNameUz());
        aCase.setNameRu(caseRequest.getNameRu());
        aCase.setAboutUz(caseRequest.getAboutUz());
        aCase.setAboutRu(caseRequest.getAboutRu());
        aCase.setLink(caseRequest.getLink());
        aCase.setRequestUz(caseRequest.getRequestUz());
        aCase.setRequestRu(caseRequest.getRequestRu());
        aCase.setEffect(caseRequest.getEffect());
        aCase.setCaseResult(caseRequest.getCaseResult());
        aCase.setActive(caseRequest.getActive());

        Case save = caseRepository.save(aCase);
        response.setMessage("Case created");
        response.setData(save);

        return ResponseEntity.ok(response);


    }

    public ResponseEntity<ApiResponse<Case>> uploadImage(Long id,MultipartFile mainPhoto, List<MultipartFile> gallery){
        ApiResponse<Case> response = new ApiResponse<>();

        if (!(mainPhoto.getContentType().equals("image/png") ||
                mainPhoto.getContentType().equals("image/svg+xml")))
        {
            response.setMessage("Invalid file , only image/png or image/svg+xml");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (!caseRepository.existsById(id)) {
            response.setMessage("Article with id " + id + " does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Case aCase = caseRepository.findById(id).get();
        Photo main = photoService.save(mainPhoto);
        aCase.setMainPhoto(main);

        aCase.setGallery(new ArrayList<>());
        for (MultipartFile multipartFile : gallery)
            aCase.getGallery().add(photoService.save(multipartFile));


        Case case1=caseRepository.save(aCase);
        response.setMessage("Photo succesfully saved");
        response.setData(case1);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<CaseDTO>> findById(Long id, String lang) {
        ApiResponse<CaseDTO> response = new ApiResponse<>();
        if (!caseRepository.existsById(id)) {
            response.setMessage("Case not found by id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Case aCase = caseRepository.findById(id).get();

        CaseDTO dto = new CaseDTO(aCase, lang);
        response.setMessage(lang);
        response.setData(dto);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Case>> findById(Long id) {
        ApiResponse<Case> response = new ApiResponse<>();
        if (!caseRepository.existsById(id)) {
            response.setMessage("Case not found by id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Case aCase = caseRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(aCase);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<List<CaseDTO>>> findAll(String lang) {
        ApiResponse<List<CaseDTO>> response = new ApiResponse<>();

        List<Case> caseList = caseRepository.findAll();

        List<CaseDTO> caseDTOList = new ArrayList<>();

        try {
            caseList.forEach(i -> caseDTOList.add(new CaseDTO(i, lang)));
            response.setMessage(lang);
            response.setData(caseDTOList);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        if (!caseRepository.existsById(id)) {
            ApiResponse<?> response = new ApiResponse<>();
            response.setMessage("Case not found by id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        try {
            caseRepository.deleteById(id);
            return ResponseEntity.ok().body(new ApiResponse<>("Succesfully deleted", null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<ApiResponse<Case>> update(Long id, Case newCase) {
        ApiResponse<Case> response = new ApiResponse<>();
        if (!caseRepository.existsById(id)) {
            response.setMessage("Case not found by id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Case oldCase=caseRepository.findById(id).get();

        if (newCase.getTitleUz()!=null||!newCase.getTitleUz().isEmpty()){
            oldCase.setTitleUz(newCase.getTitleUz());
        }
        if (newCase.getTitleRu()!=null||!newCase.getTitleRu().isEmpty()){
            oldCase.setTitleRu(newCase.getTitleRu());
        }
        if (newCase.getDescriptionUz()!=null||!newCase.getDescriptionUz().isEmpty()){
            oldCase.setDescriptionUz(newCase.getDescriptionUz());
        }
        if (newCase.getDescriptionRu()!=null||!newCase.getDescriptionRu().isEmpty()){
            oldCase.setDescriptionRu(newCase.getDescriptionRu());
        }
        if (newCase.getNameUz()!=null||!newCase.getNameUz().isEmpty()){
            oldCase.setNameUz(newCase.getNameUz());
        }
        if (newCase.getNameRu()!=null||!newCase.getNameRu().isEmpty()){
            oldCase.setNameRu(newCase.getNameRu());
        }
        if (newCase.getAboutUz()!=null||!newCase.getAboutUz().isEmpty()){
            oldCase.setAboutUz(newCase.getAboutUz());
        }
        if (newCase.getAboutRu()!=null||!newCase.getAboutRu().isEmpty()){
            oldCase.setAboutRu(newCase.getAboutRu());
        }
        if (newCase.getLink()!=null||!newCase.getLink().isEmpty()){
            oldCase.setLink(newCase.getLink());
        }
        if (newCase.getRequestUz()!=null||!newCase.getRequestUz().isEmpty()){
            oldCase.setRequestUz(newCase.getRequestUz());
        }
        if (newCase.getRequestRu()!=null||!newCase.getRequestRu().isEmpty()){
            oldCase.setRequestRu(newCase.getRequestRu());
        }
        if (newCase.getEffect()!=null||!newCase.getEffect().isEmpty()){
            oldCase.setEffect(newCase.getEffect());
        }
        if (newCase.getCaseResult()!=null||!newCase.getCaseResult().isEmpty()){
            oldCase.setCaseResult(newCase.getCaseResult());
        }
        if (newCase.getActive()!=null){
            oldCase.setActive(newCase.getActive());
        }



        oldCase.setId(id);
        Case save = caseRepository.save(oldCase);

        response.setMessage("Successfully updated");
        response.setData(save);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<ApiResponse<Case>> updatePhoto(Long id, MultipartFile newMainPhoto,List<MultipartFile> newGallery){
        ApiResponse<Case> response = new ApiResponse<>();

        if (!(newMainPhoto.getContentType().equals("image/png") ||
                newMainPhoto.getContentType().equals("image/svg+xml")))
        {
            response.setMessage("Invalid file , only image/png or image/svg+xml");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

            if (!caseRepository.existsById(id)) {
                response.setMessage("Case with id " + id + " does not exist");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        Case aCase = caseRepository.findById(id).get();

        Photo oldMain = aCase.getMainPhoto();
        List<Photo> oldGallery = aCase.getGallery();

        if (newMainPhoto == null || newMainPhoto.isEmpty())
            aCase.setMainPhoto(oldMain);
        else
            aCase.setMainPhoto(photoService.save(newMainPhoto));

        if (newGallery == null || newGallery.get(0).isEmpty())
            aCase.setGallery(oldGallery);
        else {
            aCase.setGallery(new ArrayList<>());
            for (MultipartFile multipartFile : newGallery)
                if (multipartFile.getSize() > 0)
                    aCase.getGallery().add(photoService.save(multipartFile));
        }



        aCase.setId(id);
        Case save = caseRepository.save(aCase);

        response.setMessage("Successfully updated");
        response.setData(save);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
