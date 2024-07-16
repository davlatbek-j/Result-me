package uz.resultme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.google.sheets.RowsWrapper;
import uz.resultme.service.GoogleSheetsService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/sheets")
public class GoogleSheetsController
{

    private final GoogleSheetsService googleSheetsService;

    @GetMapping("/data")
    public ResponseEntity<ApiResponse<RowsWrapper>> getSheetData(
            @RequestParam(name = "sheet-id") String spreadsheetId,
            @RequestParam(name = "range", defaultValue = "Sheet1!A:Z") String range)
    {
        return googleSheetsService.getSpreadsheetDataAsJson(spreadsheetId, range);
    }
}
