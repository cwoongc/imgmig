package com.cwoongc.st11.de_closing.pd_opt_dtl_image.img_url_source;

import com.cwoongc.st11.image_migrator.img_url_source.ImgURLSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"production-all-test"})
@Component
public class PdOptDtlImageProductionTestImgURLSource extends ImgURLSource {
    @Override
    public String getDriverClassName() {
        return "oracle.jdbc.OracleDriver";
    }

    @Override
    public String getDataSourceUrl() {
        return "jdbc:oracle:thin:@(description=(address_list=(address=(host=172.18.185.79)(protocol=tcp)(port=1527))(address=(host=172.18.185.146)(protocol=tcp)(port=1527))(address=(host=172.18.185.150)(protocol=tcp)(port=1527))(load_balance=off)(failover=on))(connect_data=(SERVER=DEDICATED)(service_name= TMALL)))";
    }

    @Override
    public String getUser() {
        return "tmall";
    }

    @Override
    public String getPassword() {
        return "tmall#ved1";
    }

    @Override
    public String getExtractingSql() {
//        return "select di.prd_no\n" +
//                "    ,p.sel_mnbd_no\n" +
//                "    ,di.opt_item_no\n" +
//                "    ,di.opt_value_no\n" +
//                "    ,di.dtl1_ext_nm\n" +
//                "    ,di.dtl2_ext_nm\n" +
//                "    ,di.dtl3_ext_nm\n" +
//                "    ,di.dtl4_ext_nm\n" +
//                "    ,di.dtl5_ext_nm\n" +
//                "from pd_prd p, pd_opt_item i, dev_pub.temp$pd_opt_value_0308_bak v,dev_pub.temp$pd_opt_dtl_image_0309_bak di\n" +
//                "where exists(select 1 from tmall.pd_smt_edtr_page g where g.prd_no = p.prd_no)\n" +
//                "    and p.sel_stat_cd in ('102','103','104','105')\n" +
//                "    and p.prd_no = i.prd_no\n" +
//                "    and i.opt_item_no = 1\n" +
//                "    and i.prd_no = v.prd_no\n" +
//                "    and v.opt_item_no = 1\n" +
//                "    and di.prd_no = p.prd_no\n" +
//                "    and di.opt_item_no = 1\n" +
//                "    and di.opt_value_no = v.opt_value_no\n" +
//                "    and (dtl1_ext_nm is not null or dtl2_ext_nm is not null or dtl3_ext_nm is not null or dtl4_ext_nm is not null or dtl5_ext_nm is not null)"
//                +"\n  and p.prd_no in (1974188916,1990696445,1898214660,1613022788,1980759047)";

        return "select di.prd_no\n" +
                "    ,p.sel_mnbd_no\n" +
                "    ,di.opt_item_no\n" +
                "    ,di.opt_value_no\n" +
                "    ,di.dtl1_ext_nm\n" +
                "    ,di.dtl2_ext_nm\n" +
                "    ,di.dtl3_ext_nm\n" +
                "    ,di.dtl4_ext_nm\n" +
                "    ,di.dtl5_ext_nm\n" +
                "from pd_prd p, pd_opt_item i, dev_pub.temp$pd_opt_value_0308_bak v,dev_pub.temp$pd_opt_dtl_image_0309_bak di\n" +
                "where exists(select 1 from tmall.pd_smt_edtr_page g where g.prd_no = p.prd_no)\n" +
                "    and p.sel_stat_cd in ('102','103','104','105')\n" +
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
