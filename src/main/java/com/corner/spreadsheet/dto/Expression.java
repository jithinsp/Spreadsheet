package com.corner.spreadsheet.dto;

import lombok.Data;

@Data
public class Expression {
    private String cellId;
    private Object value;
}
