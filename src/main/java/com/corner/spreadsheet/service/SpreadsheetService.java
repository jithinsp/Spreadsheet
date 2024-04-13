package com.corner.spreadsheet.service;

public interface SpreadsheetService {
    Double getValue(String cellId);

    public <T> void setValue(String cellId, T value);
}
