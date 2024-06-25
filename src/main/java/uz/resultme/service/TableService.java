package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.resultme.entity.MyTable;
import uz.resultme.entity.service.ServiceEntity;
import uz.resultme.payload.ApiResponse;
import uz.resultme.repository.ServiceEntityRepository;
import uz.resultme.repository.TableRepository;

@RequiredArgsConstructor
@Service
public class TableService
{
    private final TableRepository tableRepo;
    private final ServiceEntityRepository serviceEntityRepository;

    public ResponseEntity<ApiResponse<MyTable>> create(Long serviceId, String sheetId, String sheetName)
    {
        ApiResponse<MyTable> response = new ApiResponse<>();
        if (!serviceEntityRepository.existsById(serviceId))
        {
            response.setMessage("Service not found id: " + serviceId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ServiceEntity serviceEntity = serviceEntityRepository.findById(serviceId).get();

        MyTable myTable = new MyTable();
        myTable.setSheetId(sheetId);
        myTable.setRange(sheetName+"!A:Z");
        myTable.setHttpUrl(
                "http://localhost:9000/sheets/data?sheet-id=" + sheetId+"&range=" + sheetName + "!A:Z");

        tableRepo.save(myTable);
        return null;
    }
}
