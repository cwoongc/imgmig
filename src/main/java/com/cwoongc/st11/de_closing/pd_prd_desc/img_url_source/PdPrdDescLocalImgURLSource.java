package com.cwoongc.st11.de_closing.pd_prd_desc.img_url_source;

import com.cwoongc.st11.image_migrator.img_url_source.ImgURLSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local-all","local-all-test"})
@Component
public class PdPrdDescLocalImgURLSource extends ImgURLSource {

    @Override
    public String getDriverClassName() {
        return "oracle.jdbc.OracleDriver";
    }

    @Override
    public String getDataSourceUrl() {
        return "jdbc:oracle:thin:@10.40.30.245:10101:TMALL";
    }


    @Override
    public String getUser() {
        return "tmall";
    }

    @Override
    public String getPassword() {
        return "tmall#stg1";
    }

    @Override
    public String getExtractingSql() {
//        return "select a.prd_no\n" +
//                "    ,a.sel_mnbd_no\n" +
//                "    ,c.prd_desc_no\n" +
//                "    ,c.prd_desc_typ_cd\n" +
//                "    ,c.prd_dtl_typ_cd\n" +
//                "    ,c.prd_desc_cont_clob\n" +
//                "from pd_prd a, pd_prd_desc c, (select prd_no from tmall.PD_SMT_EDTR_PAGE b group by prd_no) b\n" +
//                "where 1=1\n" +
//                "    and b.prd_no = a.prd_no\n" +
//                "    and a.sel_stat_cd in ('102','103','104')\n" +
//                "    and a.prd_no = c.prd_no\n" +
//                "    and c.prd_dtl_typ_cd in ('09','10','11','12')\n" +
//                "    and c.prd_desc_typ_cd in ('02','12','13','14','15','16')\n" +
//                "    and c.clob_typ_yn = 'Y'";

        return "select a.prd_no\n" +
                "            ,a.sel_mnbd_no\n" +
                "            ,c.prd_desc_no\n" +
                "            ,c.prd_desc_typ_cd\n" +
                "            ,c.prd_dtl_typ_cd\n" +
                "            ,c.prd_desc_cont_clob\n" +
                "        from pd_prd a, temp$pd_prd_desc c\n" +
                "        where a.prd_no = c.prd_no";


    }
}