package com.example.bhanu.technologynews;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by bhanu kiran on 10/08/2016.
 */
public class NewsProvider extends ContentProvider {
    DbOpenHelper helper;
    @Override
    public boolean onCreate() {
        helper=new DbOpenHelper(getContext());
        return true;
    }
    static UriMatcher matcher;
    static
    {
        matcher=new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(NewsContract.CONTENT_AUTHORITY,NewsContract.PATH+"/#",1);
        matcher.addURI(NewsContract.CONTENT_AUTHORITY,NewsContract.PATH,2);
        //matcher.addURI(NewsContract.CONTENT_AUTHORITY,NewsContract.PATH+"/*",3);
    }


    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2) {
        SQLiteDatabase database=helper.getReadableDatabase();
        int match=matcher.match(uri);
        if(match==1)
        {
            s= NewsContract.NewsEntry._ID +" = "+uri.getPathSegments().get(1);
        }

        return database.query(NewsContract.NewsEntry.TABLE_NAME,strings,s,strings2,null,null,s2);

    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase database=helper.getWritableDatabase();
        long id=database.insert(NewsContract.NewsEntry.TABLE_NAME,null,contentValues);
        long rid=database.insertWithOnConflict(NewsContract.NewsEntry.TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(id>0)
        {
            Uri _uri= ContentUris.withAppendedId(NewsContract.NewsEntry.CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(uri,null);
            return _uri;
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase database=helper.getWritableDatabase();
        return database.delete(NewsContract.NewsEntry.TABLE_NAME,s,strings);

    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}