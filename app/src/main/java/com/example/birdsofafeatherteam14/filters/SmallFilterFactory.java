package com.example.birdsofafeatherteam14.filters;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;

public class SmallFilterFactory implements IFilterFactory{
    private AppDatabase db;
    SmallFilterFactory(AppDatabase db) {
        this.db = db;
    }

    @Override
    public Filter createFilter() {
        return new SmallStudentFilter(this.db);
    }
}
