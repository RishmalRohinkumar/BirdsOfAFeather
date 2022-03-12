package com.example.birdsofafeatherteam14.filters;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;

public class QuarterFilterFactory implements IFilterFactory{
    private AppDatabase db;
    QuarterFilterFactory(AppDatabase db) {
        this.db = db;
    }

    @Override
    public Filter createFilter() {
        return new QuarterStudentFilter(this.db);
    }
}
