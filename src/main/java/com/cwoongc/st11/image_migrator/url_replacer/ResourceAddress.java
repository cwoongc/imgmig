package com.cwoongc.st11.image_migrator.url_replacer;

public interface ResourceAddress {

    String getImgServerProtocolAndAuthority();
    String getReplaceImgUrlPathFormat();
    String getImgDownloadFilepathFormat();


}
