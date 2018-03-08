package com.cwoongc.st11.de_closing.pd_opt_value.img_url_target;

import com.cwoongc.st11.image_migrator.img_url_target.ImgURLTarget;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"production-all","production-mig"})
public class PdOptValueProductionImgURLTarget extends ImgURLTarget {

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
