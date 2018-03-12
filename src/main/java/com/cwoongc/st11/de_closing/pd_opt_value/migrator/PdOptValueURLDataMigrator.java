package com.cwoongc.st11.de_closing.pd_opt_value.migrator;

import com.cwoongc.st11.image_migrator.exception.URLDataMigrationException;
import com.cwoongc.st11.image_migrator.img_url_target.ImgURLTarget;
import com.cwoongc.st11.image_migrator.migrator.URLDataMigrator;
import com.cwoongc.st11.image_migrator.plan.PlanItemListProcessor;
import com.cwoongc.st11.image_migrator.plan.PlanItemListProcessorLogic;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessor;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessorLogic;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PdOptValueURLDataMigrator extends URLDataMigrator{

    public PdOptValueURLDataMigrator() {
    }

    public PdOptValueURLDataMigrator(ImgURLTarget imgURLTarget, Gson gson) {
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
                String selMnbdNo = cols[0];
                String prdNo = cols[1];
                String optItemNo = cols[2];
                String optValueNo = cols[3];
                String dgstExtNm = cols[4];


                int updatedRow = 0;

                try {
                    ps.setString(1,dgstExtNm);
                    ps.setLong(2,Long.parseLong(prdNo));
                    ps.setLong(3,Long.parseLong(optItemNo));
                    ps.setLong(4,Long.parseLong(optValueNo));

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

    @Override
    protected PlanItemListProcessor createMigrationPlanItemListProcessor(final List<String> planItemList, final Connection conn, final PreparedStatement ps) {
        return new PlanItemListProcessor(planItemList, new PlanItemListProcessorLogic() {
            @Override
            public List<Object> processPlanItemList(List<String> planItemList) {

                int[] updatedRows = null;

                try {

                    for(String planItem : planItemList) {

                        String[] cols = planItem.split("[\t]");
                        String selMnbdNo = cols[0];
                        String prdNo = cols[1];
                        String optItemNo = cols[2];
                        String optValueNo = cols[3];
                        String dgstExtNm = cols[4];


                        ps.setString(1, dgstExtNm);
                        ps.setLong(2, Long.parseLong(prdNo));
                        ps.setLong(3, Long.parseLong(optItemNo));
                        ps.setLong(4, Long.parseLong(optValueNo));

                        ps.addBatch();

                    }

                    updatedRows = ps.executeBatch();

                    conn.commit();


                } catch (SQLException e) {
                    log.error(e.getMessage(),e);
                    try {
                        conn.rollback();
                    } catch (SQLException e1) {
                        log.error(e1.getMessage(),e1);
                    }
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

                List<Object> ret = new ArrayList<>();

                for(int rows: updatedRows) {
                    ret.add(rows);
                }

                return ret;
            }
        });
    }
}
