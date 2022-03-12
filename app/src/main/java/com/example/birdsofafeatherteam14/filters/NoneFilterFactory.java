package com.example.birdsofafeatherteam14.filters;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;

public class NoneFilterFactory implements IFilterFactory {
    private AppDatabase db;
    public NoneFilterFactory(AppDatabase db) {
        this.db = db;
    }

    @Override
    public Filter createFilter() {
        return new NoneStudentFilter(this.db);
    }
}
