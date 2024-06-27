package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.resultme.entity.MyTable;
import uz.resultme.exception.LanguageNotSupportException;
import uz.resultme.payload.ApiResponse;
import uz.resultme.repository.OptionValueRepository;
import uz.resultme.repository.service.ServiceEntityRepository;
import uz.resultme.repository.TableRepository;
import uz.resultme.repository.service.ServiceOptionRepository;

@RequiredArgsConstructor
@Service
public class TableService
{
    private final TableRepository tableRepo;
    private final OptionValueRepository optionValRepo;

    public ResponseEntity<ApiResponse<MyTable>> create(Long optionValueId, String optionValueLang, String sheetId, String sheetName)
    {
        ApiResponse<MyTable> response = new ApiResponse<>();

        if (!optionValRepo.existsById(optionValueId))
        {
            response.setMessage("Option Value does not exist id: " + optionValueId);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        MyTable myTable = new MyTable();
        myTable.setSheetId(sheetId);
        myTable.setRange(sheetName + "!A:Z");
        myTable.setHttpUrl(
                "http://localhost:9000/sheets/data?sheet-id=" + sheetId + "&range=" + sheetName + "!A:Z");

        switch (optionValueLang.toLowerCase())
        {
            case "uz":
            {
                myTable.setLang("uz");
                optionValRepo.updateTableUrlUz(optionValueId, myTable.getHttpUrl());
                break;
            }
            case "ru":
            {
                myTable.setLang("ru");
                optionValRepo.updateTableUrlRu(optionValueId, myTable.getHttpUrl());
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + optionValueLang);
        }
        tableRepo.save(myTable);
        response.setMessage("Table created");
        response.setData(myTable);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
