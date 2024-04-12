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
    public ResponseEntity<String>  getCellValue(@RequestParam String cellId){
        return ResponseEntity.ok(spreadsheetService.getValue(cellId).toString());
    }
}
//    Design a spreadsheet backend which can support two operations:
//        You can write the code in any language and provide a main method /
//        tests to validate the code.

//        void setCellValue(String cellId, Object value)
//        int getCellValue(String cellId)

//      Example:
//        setCellValue("A1", 13)
//        setCellValue("A2", 14)
//          getCellValue("A1") -> 13

//        setCellValue("A3", "=A1+A2")
//          getCellValue("A3") -> 27

//        setCellValue("A4", "=A1+A2+A3")
//          getCellValue("A3") -> 54

//        ● Write this in a language of your choice
//        ● Mention edge cases and other situations you need to handle
//        ● Attach the response via email / GitHub repo link