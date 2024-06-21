package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.resultme.entity.ProvidedResult;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.ProvidedResultDTO;
import uz.resultme.repository.ProvidedResultRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ResultService
{
    private final ProvidedResultRepository resultRepo;

    public ResponseEntity<ApiResponse<ProvidedResult>> save(ProvidedResult providedResult)
    {
        ProvidedResult save = resultRepo.save(providedResult);
        ApiResponse<ProvidedResult> response = new ApiResponse<>();
        response.setMessage("Successfully created");
        response.setData(save);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<ApiResponse<List<ProvidedResultDTO>>> getAll(String lang)
    {
        List<ProvidedResult> allEntity = resultRepo.findAll();
        List<ProvidedResultDTO> dtoList = new ArrayList<>();
        ApiResponse<List<ProvidedResultDTO>> response = new ApiResponse<>();

        switch (lang)
        {
            case "uz":
            {
                response.setMessage("uz");
                allEntity.forEach(i -> dtoList.add(new ProvidedResultDTO(i, "uz")));
            }
            case "ru":
            {
                response.setMessage("ru");
                allEntity.forEach(i -> dtoList.add(new ProvidedResultDTO(i, "ru")));
            }
        }
        response.setData(dtoList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<ApiResponse<ProvidedResult>> update(Long id, ProvidedResult providedResult)
    {
        Optional<ProvidedResult> byId = resultRepo.findById(id);
        if (byId.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Not Found by id:" + id, null));

        providedResult.setId(id);
        ProvidedResult update = resultRepo.save(providedResult);
        ApiResponse<ProvidedResult> response = new ApiResponse<>();
        response.setMessage("Successfully updated");
        response.setData(update);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<?> deleteById(Long id)
    {
        ApiResponse<Object> response = new ApiResponse<>();
        if (!resultRepo.existsById(id))
        {
            response.setMessage("Not Found by id:" + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        resultRepo.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<ApiResponse<ProvidedResultDTO>> findById(Long id, String lang)
    {
        ApiResponse<ProvidedResultDTO> response = new ApiResponse<>();
        if (!resultRepo.existsById(id))
        {
            response.setMessage("Not Found by id:" + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ProvidedResult entity = resultRepo.findById(id).get();

        switch (lang)
        {
            case "uz":
            {
                response.setMessage("uz");
                response.setData(new ProvidedResultDTO(entity, "uz"));
                break;
            }
            case "ru":
            {
                response.setMessage("ru");
                response.setData(new ProvidedResultDTO(entity, "ru"));
                break;
            }
            default:
            {
                throw new RuntimeException("Invalid language " + lang);
            }
        }
        return ResponseEntity.ok(response);
    }
}
