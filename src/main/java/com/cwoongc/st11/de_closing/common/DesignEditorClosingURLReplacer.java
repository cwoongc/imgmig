package com.cwoongc.st11.de_closing.common;

import com.cwoongc.st11.image_migrator.url_replacer.ResourceAddress;
import com.cwoongc.st11.image_migrator.url_replacer.URLReplacer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Design editor closing시에 교체 되어야할 url에 대한 old-new 맵핑테이블과 replacer를 제공한다.
 */
@Slf4j
@Component
public class DesignEditorClosingURLReplacer extends URLReplacer{

    @Autowired
    private ResourceAddress resourceAddress;

    private static final String ST11_IMG_SERVER_PROTOCOL_AND_AUTHORITY = "http://i.011st.com";
    private static final String ST11_IMG_FILE_PATH = "/data1/upload/dec/editorImg/%s/%d/%s";
    private static final String ST11_IMG_URL = "/editorImg/%s/%d/%s";

    Map<String, String> mappingTable;



    @Override
    protected Map<String, String> getMappingTable() {
        if(mappingTable == null) {
            mappingTable = new HashMap<String, String>() {
                {
                    put("http://de.11st.co.kr", resourceAddress.getImgServerProtocolAndAuthority());
                    put("http://mars-dev.skplanet.com", resourceAddress.getImgServerProtocolAndAuthority());
                    put("http://mars.skplanet.com", resourceAddress.getImgServerProtocolAndAuthority());
                    put("http://de.011st.com", resourceAddress.getImgServerProtocolAndAuthority());
                }
            };
        }
        return this.mappingTable;
    }

    @Override
    public String replaceImgUrl(String oldUrl, Object... params) {

        if(oldUrl == null || oldUrl.isEmpty()) return null;


        String replacedUrl = null;

        try {

//            if(log.isDebugEnabled()) {
//                log.debug("OLDURL : " + oldUrl);
//            }

            if(!oldUrl.startsWith("http")) {
//                if(log.isDebugEnabled()){
//                    log.debug("NO HTTP[S} PROTOCOL!!!");
//                }

                return null;
            }

            URL url = new URL(oldUrl);


            String oldProtocolAndAuthority = url.getProtocol() + "://" + url.getAuthority();

            if(getMappingTable().containsKey(oldProtocolAndAuthority)) {

                String newProtocolAndAuthority = getMappingTable().get(oldProtocolAndAuthority);

                Date date = (Date) params[0];
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String today = sdf.format(date);

                Long memNo = (Long) params[1];

                String fileName = oldUrl.substring(oldUrl.lastIndexOf('/')+1);

                replacedUrl = newProtocolAndAuthority + String.format(resourceAddress.getReplaceImgUrlPathFormat(),today,memNo,fileName);

//                if(log.isDebugEnabled()) {
//                    log.debug("Replaced Url : " + replacedUrl);
//                }

            }

        } catch (MalformedURLException e) {
            log.error(e.getMessage(),e);
            e.printStackTrace();
            return null;
        }

        return replacedUrl;
    }

    @Override
    public String getImgFilePath(String oldUrl, Object... params) {

        if(oldUrl == null || oldUrl.isEmpty()) return null;

        String imgFilePath = null;

        try {

//            if(log.isDebugEnabled()) {
//                log.debug("OLDURL : " + oldUrl);
//            }

            if(!oldUrl.startsWith("http")) {
//                if(log.isDebugEnabled()){
//                    log.debug("NO HTTP[S} PROTOCOL!!!");
//                }

                return null;
            }

            URL url = new URL(oldUrl);

            String oldProtocolAndAuthority = url.getProtocol() + "://" + url.getAuthority();

            if(getMappingTable().containsKey(oldProtocolAndAuthority)) {


                Date date = (Date) params[0];
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String today = sdf.format(date);

                Long memNo = (Long) params[1];

                String fileName = url.getFile();

                if(url.getQuery() != null) {
                    fileName = fileName.replace(url.getQuery(), "");
                    fileName = fileName.replaceAll("\\?","");
                }

                fileName = fileName.substring(fileName.lastIndexOf('/')+1);

                imgFilePath = String.format(resourceAddress.getImgDownloadFilepathFormat(),today,memNo,fileName);

//                if(log.isDebugEnabled()) {
//                    log.debug("Created imgFilePath : " + imgFilePath);
//                }
            }

        } catch (MalformedURLException e) {
            log.error(e.getMessage(),e);
            e.printStackTrace();
            return null;
        }

        return imgFilePath;
    }
}
