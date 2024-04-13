package com.corner.spreadsheet.service;

import com.corner.spreadsheet.entity.Spreadsheet;
import com.corner.spreadsheet.repository.SpreadsheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class SpreadsheetServiceImpl implements SpreadsheetService{
    @Autowired
    SpreadsheetRepository spreadsheetRepository;

    @Override
    public Double getValue(String cellId) {
        Spreadsheet spreadsheet = spreadsheetRepository.findByCellId(cellId);
        return spreadsheet.getValue();
    }

    @Override
    public <T> void setValue(String cellId, T value) {
        Spreadsheet spreadsheet = spreadsheetRepository.findByCellId(cellId);
        if (spreadsheet == null) {
            spreadsheet = new Spreadsheet();
            spreadsheet.setCellId(cellId);
        }
        
        if (value instanceof String) {
            String stringValue = (String) value;
            if (stringValue.startsWith("=")) {
                double result = evaluate(stringValue);
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

    private double evaluate(String stringValue) {
        String formula = stringValue.substring(1);

        String[] cells = formula.split("[+\\-*/]");
        String[] operators = formula.split("[A-Z]\\d+");
        operators = Arrays.stream(operators)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        Queue<String> cellsQueue = new LinkedList<>();
        for (String cell : cells) {
            if (!cell.isEmpty()) {
                cellsQueue.add(cell);
            }
        }
        double result = spreadsheetRepository.findByCellId(cellsQueue.poll()).getValue();
        for(int i=0; i<cells.length-1;i++){
            Spreadsheet cellValue = spreadsheetRepository.findByCellId(cellsQueue.poll());
                switch (operators[i]){
                    case "+" : result += cellValue.getValue(); break;
                    case "/" :
                        if (cellValue.getValue() == 0) {
                        throw new ArithmeticException("Divide by zero error");
                        }
                        result /= cellValue.getValue(); break;
                    case "*" : result *= cellValue.getValue(); break;
                    case "-" : result -= cellValue.getValue(); break;
                    default: throw new IllegalArgumentException("Invalid operator: " + operators[i]);
                }
        }
        return result;
    }
}