package com.corner.spreadsheet.repository;

import com.corner.spreadsheet.entity.Spreadsheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpreadsheetRepository extends JpaRepository<Spreadsheet, UUID> {
    Spreadsheet findByCellId(String cellId);
}
