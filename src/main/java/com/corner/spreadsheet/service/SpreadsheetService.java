package com.corner.spreadsheet.service;

public interface SpreadsheetService {
    Double getValue(String cellId);

    void setValue(String cellId, Object value);
}
