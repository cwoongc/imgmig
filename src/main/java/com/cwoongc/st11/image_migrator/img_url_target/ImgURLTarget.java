package com.cwoongc.st11.image_migrator.img_url_target;

public abstract class ImgURLTarget {

    public abstract String getDriverClassName();
    public abstract String getDataSourceUrl();
    public abstract String getUser();
    public abstract String getPassword();
    public abstract String getUpdateSql();

}



