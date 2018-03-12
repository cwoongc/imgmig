package com.cwoongc.st11.de_closing.pd_opt_value.downloader;

import com.cwoongc.st11.image_migrator.downloader.ImgDownLoader;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessor;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessorLogic;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
@Component
public class PdOptValueImgDownLoader extends ImgDownLoader{

    public PdOptValueImgDownLoader(File planFile, Gson gson) {
        this.planFile = planFile;
        this.gson = gson;
    }

    public PdOptValueImgDownLoader() {}

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


                String urlStr = cols[4];
                urlStr = urlStr.replaceAll("[\t\n\r]","");
                String filepath = cols[5];

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
                    }

                } catch (MalformedURLException e) {
                    log.error("Image downloading error occurred : "+ urlStr + " : "  + e.getMessage(), e);
                } catch (IOException e) {
                    log.error("Image downloading error occurred : "+ urlStr + " : "  + e.getMessage(), e);
                }
                return null;
            }
        }
        );

        return planItemProcessor;
    }



}
