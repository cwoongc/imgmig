package com.cwoongc.st11.de_closing.common;

import com.cwoongc.st11.image_migrator.url_replacer.ResourceAddress;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local-all","local-all-test","stage-all","stage-all-test","stage-download-test","stage-mig-test"})
public class DesignEditorClosingStageResourceAddress implements ResourceAddress {
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
        return "/data1/upload/stdown/editorImg/%s/%d/%s";
    }


}
