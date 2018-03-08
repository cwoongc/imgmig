package com.cwoongc.st11.de_closing.pd_prd_desc.img_url_source;

import com.cwoongc.st11.image_migrator.img_url_source.ImgURLSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"production-all","production-all-test"})
@Component
public class PdPrdDescProductionTestImgURLSource extends ImgURLSource {

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
        return "select a.prd_no\n" +
                "    ,a.sel_mnbd_no\n" +
                "    ,c.prd_desc_no\n" +
                "    ,c.prd_desc_typ_cd\n" +
                "    ,c.prd_dtl_typ_cd\n" +
                "    ,c.prd_desc_cont_clob\n" +
                "from pd_prd a, dev_pub.temp$pd_prd_desc_0308_bak c, (select prd_no from tmall.PD_SMT_EDTR_PAGE b group by prd_no) b\n" +
                "where 1=1\n" +
                "    and b.prd_no = a.prd_no\n" +
                "    and a.sel_stat_cd in ('102','103','104')\n" +
                "    and a.prd_no = c.prd_no\n" +
                "    and c.prd_dtl_typ_cd in ('09','10','11','12')\n" +
                "    and c.prd_desc_typ_cd in ('02','12','13','14','15','16')\n" +
                "    and c.clob_typ_yn = 'Y'";
    }
}
