package com.example.laptop.db.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ModelPage<T> {

    private List<T> data;

    private Pagination pagination;

    public ModelPage() {
    }

    public ModelPage(List<T> data) {
        this.data = data;
    }

    public ModelPage(T data) {
        this.data = new ArrayList<T>(Arrays.asList(data));
    }

    public ModelPage(List<T> data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }
}

