package com.cwoongc.st11.image_migrator.url_replacer;

import java.util.Map;

public abstract class URLReplacer {


    protected abstract Map<String, String> getMappingTable();

    /**
     * Get a url string replaced.
     * @param oldUrl
     * @return
     */
    public abstract String replaceImgUrl(String oldUrl, Object... params);

    public abstract String getImgFilePath(String oldUrl, Object... params);









}
