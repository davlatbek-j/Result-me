package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.resultme.entity.Entrance;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.EntranceDTO;
import uz.resultme.repository.EntranceRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class EntranceService
{
    private final EntranceRepository entranceRepository;

    public ResponseEntity<ApiResponse<Entrance>> create(Entrance entrance)
    {
        ApiResponse<Entrance> response = new ApiResponse<>();
        try
        {
            entranceRepository.save(entrance);
            response.setMessage("Created");
            response.setData(entrance);
            return ResponseEntity.ok(response);
        } catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    public ResponseEntity<ApiResponse<EntranceDTO>> findById(Long id, String lang)
    {
        ApiResponse<EntranceDTO> response = new ApiResponse<>();
        if (!entranceRepository.existsById(id))
        {
            response.setMessage("Not Found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Entrance entrance = entranceRepository.findById(id).get();
        response.setMessage(lang);
        response.setData(new EntranceDTO(entrance, lang));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Entrance>> update(Long id, Entrance entrance)
    {
        ApiResponse<Entrance> response = new ApiResponse<>();
        if (!entranceRepository.existsById(id))
        {
            response.setMessage("Not Found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        entrance.setId(id);
        entranceRepository.save(entrance);
        response.setMessage("Updated");
        response.setData(entrance);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<List<EntranceDTO>>> findAll(String lang)
    {
        ApiResponse<List<EntranceDTO>> response = new ApiResponse<>();
        List<Entrance> entrances = entranceRepository.findAll();
        List<EntranceDTO> dtos = new ArrayList<>();
        entrances.forEach(i -> {dtos.add(new EntranceDTO(i, lang));});

        response.setMessage("Found " + dtos.size() + " Entrances");
        response.setData(dtos);
        return ResponseEntity.ok(response);

    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id)
    {
        ApiResponse<Entrance> response = new ApiResponse<>();
        if (!entranceRepository.existsById(id))
        {
            response.setMessage("Not Found by id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        entranceRepository.deleteById(id);
        response.setMessage("Deleted");
        return ResponseEntity.ok(response);
    }
}
