package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.resultme.entity.Location;
import uz.resultme.payload.ApiResponse;
import uz.resultme.repository.LocationRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LocationService
{
    private final LocationRepository locationRepo;

    public ResponseEntity<ApiResponse<Location>> create(Location location)
    {
        ApiResponse<Location> response = new ApiResponse<>();
        try
        {
            locationRepo.save(location);
            response.setData(location);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<ApiResponse<Location>> findById(Long id)
    {
        ApiResponse<Location> response = new ApiResponse<>();
        if (!locationRepo.existsById(id))
        {
            response.setMessage("Location not found id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        response.setData(locationRepo.findById(id).get());
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<Location>>> findAll()
    {
        ApiResponse<List<Location>> response = new ApiResponse<>();
        response.setMessage("Found " + locationRepo.count() + " location(s)");
        response.setData(locationRepo.findAll());
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Location>> update(Long id, Location location)
    {
        ApiResponse<Location> response = new ApiResponse<>();
        if (!locationRepo.existsById(id))
        {
            response.setMessage("Location not found id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        location.setId(id);
        locationRepo.save(location);
        response.setData(location);
        response.setMessage("Location successfully updated");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id)
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!locationRepo.existsById(id))
        {
            response.setMessage("Location not found id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        locationRepo.deleteById(id);
        response.setMessage("Location successfully deleted");
        return ResponseEntity.status(200).body(response);
    }
}
