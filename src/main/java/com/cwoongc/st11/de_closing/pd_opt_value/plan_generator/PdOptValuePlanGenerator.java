package com.cwoongc.st11.de_closing.pd_opt_value.plan_generator;

import com.cwoongc.st11.image_migrator.exception.PlanGeneratingException;
import com.cwoongc.st11.image_migrator.img_url_source.ImgURLSource;
import com.cwoongc.st11.image_migrator.plan.PlanGenerator;
import com.cwoongc.st11.image_migrator.url_replacer.URLReplacer;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.Date;

@Slf4j
@Component
public class PdOptValuePlanGenerator extends PlanGenerator {

    public PdOptValuePlanGenerator(ImgURLSource imgURLSource, URLReplacer urlReplacer, Gson gson) {
        this.imgUrlSource = imgURLSource;
        this.urlReplacer = urlReplacer;
        this.gson = gson;
    }

    public PdOptValuePlanGenerator(){}

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
        return "PD_OPT_VALUE";
    }

    @Override
    protected Date getLastGenDate() {
        return this.lastGenDate;
    }

    @Override
    protected String createDownloadPlanItem(ResultSet rs) {
        String planItem = null;

        StringBuilder sb = null;

        try {
            String dgstExtNm = rs.getString("dgst_ext_nm");

            if(dgstExtNm != null && !dgstExtNm.isEmpty()) {

                Long selMnbdNo = rs.getLong("sel_mnbd_no");
                Long prdNo = rs.getLong("prd_no");
                Long optItemNo = rs.getLong("opt_item_no");
                Long optValueNo = rs.getLong("opt_value_no");

                String filepath = urlReplacer.getImgFilePath(dgstExtNm, getLastGenDate(), selMnbdNo);

                if(filepath == null) return null;

                sb = new StringBuilder();

                sb.append(selMnbdNo);
                sb.append('\t');

                sb.append(prdNo);
                sb.append('\t');

                sb.append(optItemNo);
                sb.append('\t');

                sb.append(optValueNo);
                sb.append('\t');

                sb.append(dgstExtNm);
                sb.append('\t');

                sb.append(filepath);
            } else return null;

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

        try {
            String dgstExtNm = rs.getString("dgst_ext_nm");

            if(dgstExtNm != null && !dgstExtNm.isEmpty()) {

                Long selMnbdNo = rs.getLong("sel_mnbd_no");
                Long prdNo = rs.getLong("prd_no");
                Long optItemNo = rs.getLong("opt_item_no");
                Long optValueNo = rs.getLong("opt_value_no");

                String newUrl = urlReplacer.replaceImgUrl(dgstExtNm, getLastGenDate(), selMnbdNo);

                if(newUrl == null) return null;

                sb = new StringBuilder();

                sb.append(selMnbdNo);
                sb.append('\t');

                sb.append(prdNo);
                sb.append('\t');

                sb.append(optItemNo);
                sb.append('\t');

                sb.append(optValueNo);
                sb.append('\t');

                sb.append(newUrl);

            } else return null;

            planItem = sb.toString();

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new PlanGeneratingException(e.getMessage(), e);
        }

        return planItem;
    }
}
