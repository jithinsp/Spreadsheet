package com.corner.spreadsheet.dto;

import lombok.Data;

@Data
public class Expression<T> {
    private String cellId;
    private T value;
}
