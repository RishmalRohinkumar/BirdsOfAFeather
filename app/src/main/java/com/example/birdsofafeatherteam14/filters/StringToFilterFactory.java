package com.example.birdsofafeatherteam14.filters;

import com.example.birdsofafeatherteam14.filters.IFilterFactory;
import com.example.birdsofafeatherteam14.filters.NoneFilterFactory;
import com.example.birdsofafeatherteam14.filters.QuarterFilterFactory;
import com.example.birdsofafeatherteam14.filters.RecentFilterFactory;
import com.example.birdsofafeatherteam14.filters.SmallFilterFactory;
import com.example.birdsofafeatherteam14.model.db.AppDatabase;

public class StringToFilterFactory {

    public static IFilterFactory getFactoryFromString(String s, AppDatabase db) {
        switch (s) {
            case "None":
                return new NoneFilterFactory(db);
            case "Quarter":
                return new QuarterFilterFactory(db);
            case "Recent":
                return new RecentFilterFactory(db);
            case "Small":
                return new SmallFilterFactory(db);
            default:
                return null;
        }
    }
}
