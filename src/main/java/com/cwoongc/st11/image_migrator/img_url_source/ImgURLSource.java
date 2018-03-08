package com.cwoongc.st11.image_migrator.img_url_source;


public abstract class ImgURLSource {

    public abstract String getDriverClassName();
    public abstract String getDataSourceUrl();
    public abstract String getUser();
    public abstract String getPassword();
    public abstract String getExtractingSql();
}
