package uz.resultme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.resultme.entity.Case;
import uz.resultme.payload.ApiResponse;
import uz.resultme.repository.CaseRepository;

import java.util.List;

@RequiredArgsConstructor

@Service
public class CaseService
{
    private final CaseRepository caseRepository;

    public ResponseEntity<ApiResponse<Case>> create(String caseJson, MultipartFile mainPhoto, List<MultipartFile> gallery)
    {
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();

            Case aCase = objectMapper.readValue(caseJson, Case.class);

            return null;
        } catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }
}
