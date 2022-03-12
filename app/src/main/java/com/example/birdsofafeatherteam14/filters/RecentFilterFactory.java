package com.example.birdsofafeatherteam14.filters;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;

public class RecentFilterFactory implements IFilterFactory{
    private AppDatabase db;
    RecentFilterFactory(AppDatabase db) {
        this.db = db;
    }

    @Override
    public Filter createFilter() {
        return new RecentStudentFilter(this.db);
    }
}
