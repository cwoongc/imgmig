package com.cwoongc.st11.de_closing.pd_prd_desc.plan_generator;

import com.cwoongc.st11.image_migrator.exception.PlanGeneratingException;
import com.cwoongc.st11.image_migrator.img_url_source.ImgURLSource;
import com.cwoongc.st11.image_migrator.plan.PlanGenerator;
import com.cwoongc.st11.image_migrator.url_replacer.URLReplacer;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.Date;

@Slf4j
@Component
public class PdPrdDescPlanGenerator extends PlanGenerator {

    public PdPrdDescPlanGenerator(ImgURLSource imgURLSource, URLReplacer urlReplacer, Gson gson) {
        this.imgUrlSource = imgURLSource;
        this.urlReplacer = urlReplacer;
        this.gson = gson;
    }

    public PdPrdDescPlanGenerator() {
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
        return "PD_PRD_DESC";
    }

    @Override
    protected Date getLastGenDate() {
        return this.lastGenDate;
    }


    @Override
    protected String createDownloadPlanItem(ResultSet rs) {

        String planItem = null;

        StringBuilder sb1 = null;
        StringBuilder sb2 = null;

        try {
            String prdDescContClob = rs.getString("prd_desc_cont_clob");

            if(prdDescContClob != null && !prdDescContClob.isEmpty()) {

                Long prdNo = rs.getLong("prd_no");
                Long selMnbdNo = rs.getLong("sel_mnbd_no");
                Long prdDescNo = rs.getLong("prd_desc_no");
                String prdDescTypCd = rs.getString("prd_desc_typ_cd");
                String prdDtlTypCd = rs.getString("prd_dtl_typ_cd");

                String imgJsonObjFormat = "{\"url\":\"%s\",\"filepath\":\"%s\"}";
                Document doc = Jsoup.parse(prdDescContClob);
                Elements imgs = doc.getElementsByTag("img");
                for (Element img : imgs) {

                    if(sb2 == null) {
                        sb2 = new StringBuilder();
                        sb2.append('[');
                    }

                    String[] attrs = {"src", "data-original"};

                    for(int i=0;i<attrs.length;i++) {
                        String attr = img.attr(attrs[i]);

                        String filepath = urlReplacer.getImgFilePath(attr, getLastGenDate(), selMnbdNo);

                        if(filepath != null) {
                            if(sb2.length() > 1) sb2.append(',');

                            sb2.append(String.format(imgJsonObjFormat,attr.replaceAll("[\t\n\r]",""),filepath));
                        }

                    }

                }

                if(sb2 == null || sb2.toString().equals("[")) return null;

                sb2.append(']');

                sb1 = new StringBuilder();

                sb1.append(prdNo);
                sb1.append('\t');

                sb1.append(selMnbdNo);
                sb1.append('\t');

                sb1.append(prdDescNo);
                sb1.append('\t');

                sb1.append(prdDescTypCd);
                sb1.append('\t');

                sb1.append(prdDtlTypCd);
                sb1.append('\t');

                sb1.append(sb2.toString());
            } else return null;

            planItem = sb1.toString();

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
            String prdDescContClob = rs.getString("prd_desc_cont_clob");

            Long prdNo = rs.getLong("prd_no");
            Long selMnbdNo = rs.getLong("sel_mnbd_no");
            Long prdDescNo = rs.getLong("prd_desc_no");
            String prdDescTypCd = rs.getString("prd_desc_typ_cd");
            String prdDtlTypCd = rs.getString("prd_dtl_typ_cd");
            String html = "null";

            if(prdDescContClob != null ) {

                String checkString = prdDescContClob.replaceAll("[\\s]","");

                if(!checkString.isEmpty()) {

                    html = prdDescContClob.replaceAll("[\t\n\r]", "");

                    Document doc = Jsoup.parse(html);
                    Elements imgs = doc.getElementsByTag("img");

//                      int cnt = 0;

                    for (Element img : imgs) {

                        String[] attrs = {"src", "data-original"};

                        for (int i = 0; i < attrs.length; i++) {
                            String attr = img.attr(attrs[i]);

                            String newUrl = urlReplacer.replaceImgUrl(attr, getLastGenDate(), selMnbdNo);

                            if (newUrl != null) {
                                html = html.replace(attr, newUrl);
//                            cnt++;
                            }
                        }
                    }
                }
            }


//            if(cnt == 0) return null;

            sb = new StringBuilder();

            sb.append(prdNo);
            sb.append('\t');

            sb.append(selMnbdNo);
            sb.append('\t');

            sb.append(prdDescNo);
            sb.append('\t');

            sb.append(prdDescTypCd);
            sb.append('\t');

            sb.append(prdDtlTypCd);
            sb.append('\t');

            sb.append(html);

            planItem = sb.toString();

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new PlanGeneratingException(e.getMessage(), e);
        }

        return planItem;







    }
}
