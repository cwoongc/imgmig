package com.cwoongc.st11.de_closing.pd_prd_desc.img_url_target;

import com.cwoongc.st11.image_migrator.img_url_target.ImgURLTarget;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local-all-test"})
public class PdPrdDescLocalTestImgURLTarget extends ImgURLTarget {

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
    public String getUpdateSql() {
//        return "update temp$pd_prd_desc_0306\n" +
//                "set prd_dtl_typ_cd = ?,\n" +
//                "    prd_desc_cont_clob = ?,\n" +
//                "    update_dt = sysdate,\n" +
//                "    update_no = 10000276\n" +
//                "where prd_desc_no = ?";


        return "update temp$pd_prd_desc\n" +
                "set prd_dtl_typ_cd = ?,\n" +
                "    prd_desc_cont_clob = ?,\n" +
                "    update_dt = sysdate,\n" +
                "    update_no = 10000276\n" +
                "where prd_desc_no = ?";
    }

}
