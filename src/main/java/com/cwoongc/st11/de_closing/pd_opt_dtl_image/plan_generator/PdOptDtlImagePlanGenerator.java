package com.cwoongc.st11.de_closing.pd_opt_dtl_image.plan_generator;

import com.cwoongc.st11.image_migrator.exception.PlanGeneratingException;
import com.cwoongc.st11.image_migrator.img_url_source.ImgURLSource;
import com.cwoongc.st11.image_migrator.plan.PlanGenerator;
import com.cwoongc.st11.image_migrator.url_replacer.URLReplacer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


@Slf4j
@Component
public class PdOptDtlImagePlanGenerator extends PlanGenerator {

    public PdOptDtlImagePlanGenerator(ImgURLSource imgURLSource, URLReplacer urlReplacer, Gson gson) {
        this.imgUrlSource = imgURLSource;
        this.urlReplacer = urlReplacer;
        this.gson = gson;
    }


    public PdOptDtlImagePlanGenerator() {
    }

    @Override
    protected ImgURLSource getImgUrlSource() {
        return this.imgUrlSource;
    }


    @Override
    protected URLReplacer getUrlReplacer() {
        return this.urlReplacer;
    }

    @Override
    protected Gson getGson() {
        return this.gson;
    }

    @Override
    protected String getPlanSrcName() {
        return "PD_OPT_DTL_IMAGE";
    }

    @Override
    protected Date getLastGenDate() {
        return this.lastGenDate;
    }


    @Override
    protected String createDownloadPlanItem(ResultSet rs) {
        String planItem = null;

        StringBuilder sb = null;

        Map<String, Map<String,String>> imgUrlmap = new TreeMap<>();

        try {

            Long prdNo = rs.getLong("prd_no");
            Long selMnbdNo = rs.getLong("sel_mnbd_no");
            Long optItemNo = rs.getLong("opt_item_no");
            Long optValueNo = rs.getLong("opt_value_no");

            for(int i=1; i<=5; i++) {
                String columnLabel = "dtl" + i + "_ext_nm";
                String imgUrl = rs.getString(columnLabel);
                Map<String,String> urlFilepath = new HashMap<>();

                if(imgUrl == null || imgUrl.replaceAll("[\\s]","").trim().isEmpty()) { // 컬럼이 비었다.

                    imgUrlmap.put(columnLabel, null); // 해당 컬럼은 다운로드 대상이 아니다. 컬럼명과 null 기록

                } else {
                    imgUrl = imgUrl.replaceAll("[\t\n\r]","");

                    String filepath = urlReplacer.getImgFilePath(imgUrl, getLastGenDate(), selMnbdNo);

                    if(filepath == null) { //대상 url이 아니다.

                        imgUrlmap.put(columnLabel, null); //해당 컬럼은 다운로드 대상이 아니다. 컬럼명과 null 기록
                    } else { // 대상 url 이다.
                        urlFilepath.put("url",imgUrl);
                        urlFilepath.put("filepath",filepath);
                        imgUrlmap.put(columnLabel, urlFilepath); // 해당 컬럼은 다운로드 대상, 컬럼명과 url : filepath 기록.
                    }
                }
            }

            String dtlExtNmJson = gson.toJson(imgUrlmap, new TypeToken<Map<String,Map<String,String>>>(){}.getType());

            sb = new StringBuilder();

            sb.append(prdNo);
            sb.append('\t');

            sb.append(selMnbdNo);
            sb.append('\t');

            sb.append(optItemNo);
            sb.append('\t');

            sb.append(optValueNo);
            sb.append('\t');

            sb.append(dtlExtNmJson);
            sb.append('\t');

            planItem = sb.toString();

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new PlanGeneratingException(e.getMessage(), e);
        }

        return planItem;
    }

    @Override
    protected String createMigrationPlanItem(ResultSet rs) {
        String planItem = null;

        StringBuilder sb = null;

        Map<String, String> imgUrlmap = new TreeMap<>();

        try {

            Long prdNo = rs.getLong("prd_no");
            Long selMnbdNo = rs.getLong("sel_mnbd_no");
            Long optItemNo = rs.getLong("opt_item_no");
            Long optValueNo = rs.getLong("opt_value_no");

            for(int i=1; i<=5; i++) {
                String columnLabel = "dtl" + i + "_ext_nm";
                String imgUrl = rs.getString(columnLabel);
                Map<String,String> urlFilepath = new HashMap<>();

                if(imgUrl == null || imgUrl.isEmpty()) { // 컬럼이 비었다.

                    imgUrlmap.put(columnLabel, null); // 해당 컬럼은 마이그레이션 대상이 아니다(내용무). 컬럼명과 null 기록

                } else {
                    String replacedUrl = urlReplacer.replaceImgUrl(imgUrl, getLastGenDate(), selMnbdNo);

                    if(replacedUrl == null) { //대상 url이 아니다.
                        imgUrlmap.put(columnLabel, imgUrl); //해당 컬럼은 마이그레이션 대상이 아니다(대상url아님). 컬럼명과 원래 url 기록
                    } else { // 대상 url 이다.
                        imgUrlmap.put(columnLabel, replacedUrl); // 해당 컬럼은 마이그래이션 대상, 컬럼명과 교체한 url 기록.
                    }
                }
            }

            String dtlExtNmJson = gson.toJson(imgUrlmap, new TypeToken<Map<String,String>>(){}.getType());

            sb = new StringBuilder();

            sb.append(prdNo);
            sb.append('\t');

            sb.append(selMnbdNo);
            sb.append('\t');

            sb.append(optItemNo);
            sb.append('\t');

            sb.append(optValueNo);
            sb.append('\t');

            sb.append(dtlExtNmJson);
            sb.append('\t');

            planItem = sb.toString();

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new PlanGeneratingException(e.getMessage(), e);
        }

        return planItem;
    }


}
