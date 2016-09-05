package com.example.bhanu.technologynews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bhanu kiran on 10/08/2016.
 */
public class DbOpenHelper  extends SQLiteOpenHelper{
    static final String DATABASE_NAME = "technews.db";
    private static final int DATABASE_VERSION = 2;
    public DbOpenHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE " + NewsContract.NewsEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                NewsContract.NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data

                NewsContract.NewsEntry.DESC+ " TEXT NOT NULL, " +
                NewsContract.NewsEntry.URL+ " TEXT NOT NULL," +

                NewsContract.NewsEntry.IMAGE + " TEXT NOT NULL, " +


                // Set up the location column as a foreign key to location table.


                // To assure the application have just one weather entry per day
                // per location, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + NewsContract.NewsEntry.DESC + ", " +
                NewsContract.NewsEntry.URL +", "+ NewsContract.NewsEntry.IMAGE + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsContract.NewsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
