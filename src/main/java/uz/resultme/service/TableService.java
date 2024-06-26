package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.resultme.entity.MyTable;
import uz.resultme.exception.LanguageNotSupportException;
import uz.resultme.payload.ApiResponse;
import uz.resultme.repository.service.ServiceEntityRepository;
import uz.resultme.repository.TableRepository;
import uz.resultme.repository.service.ServiceOptionRepository;

@RequiredArgsConstructor
@Service
public class TableService
{
    private final TableRepository tableRepo;
    private final ServiceEntityRepository serviceEntityRepo;
    private final ServiceOptionRepository serviceOptionRepo;

    public ResponseEntity<ApiResponse<MyTable>> create(Long optionId, String lang, String sheetId, String sheetName)
    {
        ApiResponse<MyTable> response = new ApiResponse<>();
        if (!serviceOptionRepo.existsById(optionId))
        {
            response.setMessage("Service Option not found id: " + optionId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        MyTable myTable = new MyTable();
        myTable.setSheetId(sheetId);
        myTable.setRange(sheetName + "!A:Z");
        myTable.setHttpUrl(
                "http://localhost:9000/sheets/data?sheet-id=" + sheetId + "&range=" + sheetName + "!A:Z");

        switch (lang.toLowerCase())
        {
            case "uz":
            {
                myTable.setLang("uz");
                serviceOptionRepo.updateTableUrlUz(optionId, myTable.getHttpUrl());
                break;
            }
            case "ru":
            {
                myTable.setLang("ru");
                serviceOptionRepo.updateTableUrlRu(optionId, myTable.getHttpUrl());
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
        tableRepo.save(myTable);
        response.setMessage("Table created");
        response.setData(myTable);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
