package com.corner.spreadsheet.service;

import com.corner.spreadsheet.entity.Spreadsheet;
import com.corner.spreadsheet.repository.SpreadsheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpreadsheetServiceImpl implements SpreadsheetService{
    @Autowired
    SpreadsheetRepository spreadsheetRepository;

    public Double getValue(String cellId) {
        Spreadsheet spreadsheet = spreadsheetRepository.findByCellId(cellId);
        return spreadsheet.getValue();
    }

    public void setValue(String cellId, Object value) {
        Spreadsheet spreadsheet = spreadsheetRepository.findByCellId(cellId);
        if (spreadsheet == null) {
            spreadsheet = new Spreadsheet();
            spreadsheet.setCellId(cellId);
        }
        if (value instanceof String) {
            String stringValue = (String) value;
            if (stringValue.startsWith("=")) {
                String formula = stringValue.substring(1);
                double result = evaluate(formula);
                spreadsheet.setValue(result);
            } else {
                throw new IllegalArgumentException("Invalid Expression: " +stringValue);
            }
        } else if (value instanceof Integer || value instanceof Double) {
            double numericValue = Double.parseDouble(value.toString());
            spreadsheet.setValue(numericValue);
        } else {
            throw new IllegalArgumentException("Invalid Expression");
        }
        spreadsheetRepository.save(spreadsheet);
    }

    private double evaluate(String formula) {
        double result =0;
        String[] cells = formula.split("[+]");
        for (String cell : cells) {
            Spreadsheet cellValue = spreadsheetRepository.findByCellId(cell);
            if(cellValue!=null){
                result += cellValue.getValue();
            } else {
                throw new IllegalArgumentException("Invalid cell reference: " + cell);
            }
        }
        return result;
    }
}