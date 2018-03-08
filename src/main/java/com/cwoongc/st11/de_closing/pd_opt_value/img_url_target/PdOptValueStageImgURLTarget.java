package com.cwoongc.st11.de_closing.pd_opt_value.img_url_target;

import com.cwoongc.st11.image_migrator.img_url_target.ImgURLTarget;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"stage-all"})
public class PdOptValueStageImgURLTarget extends ImgURLTarget {
    @Override
    public String getDriverClassName() {
        return "oracle.jdbc.OracleDriver";
    }

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
        return "update pd_opt_value\n" +
                "set dgst_ext_nm = ?,\n" +
                "    update_dt = sysdate,\n" +
                "    update_no = 10000276\n" +
                "where prd_no = ?\n" +
                "    and opt_item_no = ?\n" +
                "    and opt_value_no = ?\n";
    }
}
