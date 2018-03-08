package com.cwoongc.st11.de_closing.pd_prd_desc.migrator;

import com.cwoongc.st11.image_migrator.exception.URLDataMigrationException;
import com.cwoongc.st11.image_migrator.img_url_target.ImgURLTarget;
import com.cwoongc.st11.image_migrator.migrator.URLDataMigrator;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessor;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessorLogic;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.*;

@Component
@Slf4j
public class PdPrdDescURLDataMigrator extends URLDataMigrator{

    public PdPrdDescURLDataMigrator() {
    }

    public PdPrdDescURLDataMigrator(ImgURLTarget imgURLTarget, Gson gson) {
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
                String prdDescNo = cols[2];
                String prdDescTypCd = cols[3];
                String prdDtlTypCd = cols[4];
                String prdDescContClob = cols[5];

                int updatedRow = 0;

                try {
                    String newPrdDtlTypCd = getNewPrdDtlTypeCode(prdDtlTypCd);
                    if(newPrdDtlTypCd == null) {
                        ps.setNull(1, Types.NULL);
                    } else {
                        ps.setString(1, newPrdDtlTypCd);
                    }

                    Clob html = conn.createClob();
                    html.setString(1, prdDescContClob);

                    ps.setClob(2, html);
                    ps.setLong(3, Long.parseLong(prdDescNo));

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

            private String getNewPrdDtlTypeCode(String prdDtlTypeCode) {
                String newPrdDtlTypeCode = null;

                switch (prdDtlTypeCode.trim()) {
                    case "09": newPrdDtlTypeCode = null;
                    break;
                    case "10": newPrdDtlTypeCode = "05";
                    break;
                    case "11": newPrdDtlTypeCode = "06";
                    break;
                    case "12": newPrdDtlTypeCode = "06";
                    break;
                    default: newPrdDtlTypeCode = prdDtlTypeCode.trim();
                    break;
                }
                return newPrdDtlTypeCode;
            }



        });
    }
}
