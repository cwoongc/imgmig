package com.cwoongc.st11.de_closing.pd_opt_dtl_image.downloader;

import com.cwoongc.st11.image_migrator.downloader.ImgDownLoader;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessor;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessorLogic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

@Slf4j
@Component
public class PdOptDtlImageImgDownLoader extends ImgDownLoader {

    @Override
    protected File getPlanFile() {
        return this.planFile;
    }

    @Override
    protected Gson getGson() {
        return this.gson;
    }

    @Override
    protected PlanItemProcessor createDownloadPlanItemProcessor(String planItem) {

        PlanItemProcessor planItemProcessor = new PlanItemProcessor(planItem, new PlanItemProcessorLogic() {
            @Override
            public Object processPlanItem(String planItem) {

                String[] cols = planItem.split("[\t]");

                String imgUrlsJson = cols[4];

                Map<String, UrlFilepath> urlFilepathMap = getGson().fromJson(imgUrlsJson, new TypeToken<Map<String,UrlFilepath>>(){}.getType());

                for(Map.Entry<String,UrlFilepath> urlFilepath : urlFilepathMap.entrySet()) {

                    String columnLabel = urlFilepath.getKey();
                    PdOptDtlImageImgDownLoader.UrlFilepath urlFilepathObj = urlFilepath.getValue();

                    if(urlFilepathObj == null) {
                        continue;
                    }


                    String urlStr = urlFilepathObj.getUrl();
                    String filepath = urlFilepathObj.getFilepath();

                    try {
                        URL url = new URL(urlStr);

                        URLConnection conn = url.openConnection();
                        conn.setDoInput(true);

                        File parentDir = new File(filepath.substring(0,filepath.lastIndexOf("/")));
                        parentDir.mkdirs();

                        try (
                                InputStream is = conn.getInputStream();
                                BufferedInputStream bis = new BufferedInputStream(is);
                                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filepath,false))) {

                            byte[] b = new byte[1024];

                            int readBytes = 0;

                            while((readBytes = bis.read(b,0,b.length))!= -1) {

                                bos.write(b,0,readBytes);
                            }

                            bos.flush();

                        } catch (IOException e) {
                            log.error("Image downloading error occurred : "+ urlStr + " : "  + e.getMessage(), e);
                            continue;
                        }

                    } catch (MalformedURLException e) {
                        log.error("Image downloading error occurred : "+ urlStr + " : "  + e.getMessage(), e);
                        continue;
                    } catch (IOException e) {
                        log.error("Image downloading error occurred : "+ urlStr + " : "  + e.getMessage(), e);
                        continue;
                    }

                }
                return null;
            }
        });


        return planItemProcessor;
    }

    public PdOptDtlImageImgDownLoader(File planFile, Gson gson) {
        this.planFile = planFile;
        this.gson = gson;
    }

    public PdOptDtlImageImgDownLoader() {}

    public class UrlFilepath {
        private String url;
        private String filepath;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }
    }


}
