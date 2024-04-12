package com.corner.spreadsheet.controller;

import com.corner.spreadsheet.dto.Expression;
import com.corner.spreadsheet.service.SpreadsheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpreadsheetController {
    @Autowired
    SpreadsheetService spreadsheetService;

    @PostMapping("setCellValue")
    public ResponseEntity<String> setCellValue(@RequestBody Expression expression){
        try{
            spreadsheetService.setValue(expression.getCellId(),expression.getValue());
            return ResponseEntity.ok("Cell value set successfully");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("getCellValue")
    public ResponseEntity<String> getCellValue(@RequestParam String cellId){
        try{
            return ResponseEntity.ok(spreadsheetService.getValue(cellId).toString());
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Cell Reference: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}