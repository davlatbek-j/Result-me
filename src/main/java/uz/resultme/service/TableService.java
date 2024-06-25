package uz.resultme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.resultme.entity.service.MyRow;
import uz.resultme.entity.service.MyTable;
import uz.resultme.exception.IllegalTableArgumentException;
import uz.resultme.payload.ApiResponse;
import uz.resultme.repository.TableRepository;

@RequiredArgsConstructor

@Service
public class TableService
{
    private final TableRepository tableRepo;

    public ResponseEntity<ApiResponse<MyTable>> create(MyTable myTable)
    {
        validateTable(myTable);

        ApiResponse<MyTable> response = new ApiResponse<>();
        MyTable save = tableRepo.save(myTable);
        response.setMessage("Successfully created");
        response.setData(save);
        return ResponseEntity.ok(response);
    }

    private void validateTable(MyTable myTable)
    {
        if (myTable == null)
            throw new NullPointerException("Table is null");

        if (myTable.getRow().size() != myTable.getY())
        {
            throw new IllegalTableArgumentException("Expected rows number: "+myTable.getY()+
                    "Actual rows number: "+myTable.getRow().toArray().length);
        }

        for (MyRow myRow : myTable.getRow())
        {
//            if (myRow.getValues().size())
        }

    }
}
