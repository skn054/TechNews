package com.example.bhanu.technologynews;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by bhanu kiran on 10/08/2016.
 */
public class NewsContract  {

    public static final String CONTENT_AUTHORITY = "com.example.bhanu.technologynews";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH="TechNews";

    public static final class NewsEntry implements BaseColumns
    {

        public static final Uri CONTENT_URI =BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final String TABLE_NAME ="TechNews";
        public static final String DESC="description";
        public static final String URL="url";
        public static final String IMAGE="image";
    }


}
