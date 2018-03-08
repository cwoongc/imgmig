package com.cwoongc.st11.de_closing.pd_opt_dtl_image.img_url_target;

import com.cwoongc.st11.image_migrator.img_url_target.ImgURLTarget;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"stage-all"})
public class PdOptDtlImageStageImgURLTarget extends ImgURLTarget {
    @Override
    public String getDriverClassName() {
        return "oracle.jdbc.OracleDriver";
    }

//    @Override
//    public String getDataSourceUrl() {
//        return "jdbc:oracle:thin:@10.40.30.245:10101:TMALL";
//    }
    @Override
    public String getDataSourceUrl() {
        return "jdbc:oracle:thin:@172.18.176.69:1525:TMALL";
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
    public String getUpdateSql() {
        return "update pd_opt_dtl_image\n" +
                "set dtl1_ext_nm = ?,\n" +
                "    dtl2_ext_nm = ?,\n" +
                "    dtl3_ext_nm = ?,\n" +
                "    dtl4_ext_nm = ?,\n" +
                "    dtl5_ext_nm = ?,\n" +
                "    update_dt = sysdate,\n" +
                "    update_no = 10000276\n" +
                "where prd_no = ?\n" +
                "    and opt_item_no = ?\n" +
                "    and opt_value_no = ?";
    }
}
