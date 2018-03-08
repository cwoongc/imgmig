package com.cwoongc.st11.de_closing.common;

import com.cwoongc.st11.image_migrator.url_replacer.ResourceAddress;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"production-all-test"})
public class DesignEditorClosingProductionTestResourceAddress implements ResourceAddress {
    @Override
    public String getImgServerProtocolAndAuthority() {
        return "http://i.011st.com";
    }

    @Override
    public String getReplaceImgUrlPathFormat() {
        return "/editorImg/%s/%d/%s";
    }

    @Override
    public String getImgDownloadFilepathFormat() {
        return "/data1/upload/dec/editorImg/%s/%d/%s";
    }

}
