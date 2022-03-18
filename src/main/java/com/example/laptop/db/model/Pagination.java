package com.example.laptop.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pagination {

    private int total = 0;

    private int page = 0;
}
