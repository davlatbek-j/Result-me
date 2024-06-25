package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.resultme.entity.service.MyTable;
import uz.resultme.payload.ApiResponse;
import uz.resultme.service.TableService;

@RequiredArgsConstructor

@Controller
@RequestMapping("/table")
public class TableController
{
    private final TableService tableService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<MyTable>> createTable(@RequestBody MyTable myTable)
    {
        return tableService.create(myTable);
    }
}
