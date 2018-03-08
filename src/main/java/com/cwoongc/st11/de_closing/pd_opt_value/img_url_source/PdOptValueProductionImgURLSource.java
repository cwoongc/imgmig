package com.cwoongc.st11.de_closing.pd_opt_value.img_url_source;

import com.cwoongc.st11.image_migrator.img_url_source.ImgURLSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"production-all","production-all-test"})
@Component
public class PdOptValueProductionImgURLSource extends ImgURLSource {
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
        return "select p.sel_mnbd_no\n" +
                "    ,v.prd_no\n" +
                "    ,v.opt_item_no\n" +
                "    ,v.opt_value_no\n" +
                "    ,v.dgst_ext_nm\n" +
                "from pd_prd p, pd_opt_item i, pd_opt_value v\n" +
                "where exists(select 1 from tmall.pd_smt_edtr_page g where g.prd_no = p.prd_no)\n" +
                "    and p.sel_stat_cd in ('102','103','104')\n" +
                "    and p.prd_no = i.prd_no\n" +
                "    and i.opt_item_no = 1\n" +
                "    and i.prd_no = v.prd_no\n" +
                "    and v.opt_item_no = 1\n" +
                "    and v.dgst_ext_nm is not null";
    }
}
