package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.resultme.entity.MyTable;
import uz.resultme.payload.ApiResponse;
import uz.resultme.service.TableService;

@RequiredArgsConstructor

@Controller
@RequestMapping("/table")
public class TableController
{
    private final TableService tableService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<MyTable>> createTable(
            @RequestParam("option-value-id") Long optionId,
            @RequestParam("option-value-lang") String lang,
            @RequestParam(name = "spreadsheet-id",defaultValue = "1iTLpTVHwYxfpg-puUAlSqc2DqTkNlyRC-8lIOcQQOVc",required = false) String sheetId,
            @RequestParam(name = "sheet-name") String sheetName)
    {
        return tableService.create(optionId,lang,sheetId,sheetName);
    }

}

