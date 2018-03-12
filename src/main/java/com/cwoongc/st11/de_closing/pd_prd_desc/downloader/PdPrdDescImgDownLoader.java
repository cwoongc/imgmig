package com.cwoongc.st11.de_closing.pd_prd_desc.downloader;

import com.cwoongc.st11.image_migrator.downloader.ImgDownLoader;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessor;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessorLogic;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.MalformedJsonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Slf4j
@Component
public class PdPrdDescImgDownLoader extends ImgDownLoader {

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

                String imgUrlsJson = cols[5];

                try {

                    List<UrlFilepath> urlFilepathList = getGson().fromJson(imgUrlsJson, new TypeToken<List<UrlFilepath>>() {
                    }.getType());

                    for (UrlFilepath urlFilepath : urlFilepathList) {
                        String urlStr = urlFilepath.getUrl();
                        String filepath = urlFilepath.getFilepath();

                        try {
                            URL url = new URL(urlStr);

                            URLConnection conn = url.openConnection();
                            conn.setDoInput(true);

                            File parentDir = new File(filepath.substring(0, filepath.lastIndexOf("/")));
                            parentDir.mkdirs();

                            try (
                                    InputStream is = conn.getInputStream();
                                    BufferedInputStream bis = new BufferedInputStream(is);
                                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filepath, false))) {

                                byte[] b = new byte[1024];

                                int readBytes = 0;

                                while ((readBytes = bis.read(b, 0, b.length)) != -1) {

                                    bos.write(b, 0, readBytes);
                                }

                                bos.flush();

                            } catch (IOException e) {
                                log.error("Image downloading error occurred : " + urlStr + " : " + e.getMessage(), e);
                                continue;
                            }

                        } catch (MalformedURLException e) {
                            log.error("Image downloading error occurred : " + urlStr + " : " + e.getMessage(), e);
                            continue;
                        } catch (IOException e) {
                            log.error("Image downloading error occurred : " + urlStr + " : " + e.getMessage(), e);
                            continue;
                        }

                    }
                } catch (JsonSyntaxException e) {
                    log.error("Image downloading error occurred : "+ "prd_desc_no : [" + cols[2] + "], Json : " + imgUrlsJson + " : " + e.getMessage(), e);
                }
                return null;
            }
        }
        );

        return planItemProcessor;


    }

    public PdPrdDescImgDownLoader(File planFile, Gson gson) {
        this.planFile = planFile;
        this.gson = gson;
    }

    public PdPrdDescImgDownLoader() {}

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
