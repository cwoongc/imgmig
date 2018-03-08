package com.cwoongc.st11.de_closing.pd_opt_dtl_image.migrator;

import com.cwoongc.st11.image_migrator.exception.URLDataMigrationException;
import com.cwoongc.st11.image_migrator.img_url_target.ImgURLTarget;
import com.cwoongc.st11.image_migrator.migrator.URLDataMigrator;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessor;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessorLogic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

@Component
@Slf4j
public class PdOptDtlImageURLDataMigrator extends URLDataMigrator{

    public PdOptDtlImageURLDataMigrator() {
    }

    public PdOptDtlImageURLDataMigrator(ImgURLTarget imgURLTarget, Gson gson) {
        this.imgURLTarget = imgURLTarget;
        this.gson = gson;
    }

    @Override
    protected ImgURLTarget getImgURLTarget() {
        return this.imgURLTarget;
    }

    @Override
    protected File getPlanFile() {
        return this.planFile;
    }

    @Override
    protected Gson getGson() {
        return this.gson;
    }

    @Override
    protected PlanItemProcessor createMigrationPlanItemProcessor(String planItem, final Connection conn, final PreparedStatement ps) {
        return new PlanItemProcessor(planItem, new PlanItemProcessorLogic() {
            @Override
            public Object processPlanItem(String planItem) {

                String[] cols = planItem.split("[\t]");
                String prdNo = cols[0];
                String selMnbdNo = cols[1];
                String optItemNo = cols[2];
                String optValueNo = cols[3];
                String dtlExtNmJson = cols[4];

                Map<String,String> dtlExtNmMap = gson.fromJson(dtlExtNmJson, new TypeToken<Map<String,String>>(){}.getType());


                int updatedRow = 0;

                try {
                    if(dtlExtNmMap.get("dtl1_ext_nm") == null) ps.setNull(1, Types.NULL);
                    else ps.setString(1, dtlExtNmMap.get("dtl1_ext_nm"));

                    if(dtlExtNmMap.get("dtl2_ext_nm") == null) ps.setNull(2, Types.NULL);
                    else ps.setString(2, dtlExtNmMap.get("dtl2_ext_nm"));

                    if(dtlExtNmMap.get("dtl3_ext_nm") == null) ps.setNull(3, Types.NULL);
                    else ps.setString(3, dtlExtNmMap.get("dtl3_ext_nm"));

                    if(dtlExtNmMap.get("dtl4_ext_nm") == null) ps.setNull(4, Types.NULL);
                    else ps.setString(4, dtlExtNmMap.get("dtl4_ext_nm"));

                    if(dtlExtNmMap.get("dtl5_ext_nm") == null) ps.setNull(5, Types.NULL);
                    else ps.setString(5, dtlExtNmMap.get("dtl5_ext_nm"));

                    ps.setLong(6,Long.parseLong(prdNo));
                    ps.setLong(7,Long.parseLong(optItemNo));
                    ps.setLong(8,Long.parseLong(optValueNo));

                    updatedRow = ps.executeUpdate();

                } catch (SQLException e) {
                    log.error(e.getMessage(),e);
                    throw new URLDataMigrationException(e.getMessage(),e);
                } finally {
                    if(ps!=null) try {ps.close();} catch (SQLException e) {
                        log.error(e.getMessage(),e);
                    }
                    if(conn!=null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            log.error(e.getMessage(),e);
                        }
                    }
                }

                return updatedRow;
            }
        });
    }
}
