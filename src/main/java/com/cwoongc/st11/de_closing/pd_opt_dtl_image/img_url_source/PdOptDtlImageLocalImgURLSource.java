package com.cwoongc.st11.de_closing.pd_opt_dtl_image.img_url_source;

import com.cwoongc.st11.image_migrator.img_url_source.ImgURLSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local-all","local-all-test"})
@Component
public class PdOptDtlImageLocalImgURLSource extends ImgURLSource {
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
        return "select di.prd_no\n" +
                "    ,p.sel_mnbd_no\n" +
                "    ,di.opt_item_no\n" +
                "    ,di.opt_value_no\n" +
                "    ,di.dtl1_ext_nm\n" +
                "    ,di.dtl2_ext_nm\n" +
                "    ,di.dtl3_ext_nm\n" +
                "    ,di.dtl4_ext_nm\n" +
                "    ,di.dtl5_ext_nm\n" +
                "from pd_prd p, pd_opt_item i, pd_opt_value v, pd_opt_dtl_image di\n" +
                "where exists(select 1 from tmall.pd_smt_edtr_page g where g.prd_no = p.prd_no)\n" +
                "    and p.sel_stat_cd in ('102','103','104')\n" +
                "    and p.prd_no = i.prd_no\n" +
                "    and i.opt_item_no = 1\n" +
                "    and i.prd_no = v.prd_no\n" +
                "    and v.opt_item_no = 1\n" +
                "    and di.prd_no = p.prd_no\n" +
                "    and di.opt_item_no = 1\n" +
                "    and di.opt_value_no = v.opt_value_no\n" +
                "    and (dtl1_ext_nm is not null or dtl2_ext_nm is not null or dtl3_ext_nm is not null or dtl4_ext_nm is not null or dtl5_ext_nm is not null)";
    }
}


