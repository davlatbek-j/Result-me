package uz.resultme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uz.resultme.payload.ApiResponse;
import uz.resultme.payload.google.sheets.RowsWrapper;
import uz.resultme.service.GoogleSheetsService;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/sheets")
public class GoogleSheetsController {

    @Autowired
    private GoogleSheetsService googleSheetsService;

    @GetMapping("/data")
    public ResponseEntity<ApiResponse<RowsWrapper>> getSheetData(@RequestParam String spreadsheetId, @RequestParam String range) {
        try {
            return googleSheetsService.getSpreadsheetDataAsJson(spreadsheetId, range);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
