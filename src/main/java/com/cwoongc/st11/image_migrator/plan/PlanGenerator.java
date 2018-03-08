package com.cwoongc.st11.image_migrator.plan;

import com.cwoongc.st11.image_migrator.exception.PlanGeneratingException;
import com.cwoongc.st11.image_migrator.img_url_source.ImgURLSource;
import com.cwoongc.st11.image_migrator.url_replacer.URLReplacer;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public abstract class PlanGenerator{

    protected ImgURLSource imgUrlSource;

    protected URLReplacer urlReplacer;

    protected Gson gson;

    protected Date lastGenDate;

    protected abstract ImgURLSource getImgUrlSource();

    protected abstract URLReplacer getUrlReplacer();

    protected abstract Gson getGson();

    protected abstract String getPlanSrcName();

    protected abstract Date getLastGenDate();

    protected abstract String createDownloadPlanItem(ResultSet rs);

    protected abstract String createMigrationPlanItem(ResultSet rs);

    public File[] generatePlanFiles() throws PlanGeneratingException {

        File downloadPlanFile = null;
        File migrationPlanFile = null;

        try {
            Class.forName(imgUrlSource.getDriverClassName());

            lastGenDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("_yyyyMMdd_HHmmss_SSS");
            String genTime = sdf.format(lastGenDate);

            OutputStreamWriter oswD = null;
            OutputStreamWriter oswM = null;


            try(Connection conn = DriverManager.getConnection(imgUrlSource.getDataSourceUrl(), imgUrlSource.getUser(), imgUrlSource.getPassword());
                PreparedStatement ps = conn.prepareStatement(
                        imgUrlSource.getExtractingSql()
                );
                ResultSet rs =  ps.executeQuery();
            ) {

                File planDir = new File("./plan");
                planDir.mkdirs();

                downloadPlanFile = new File(planDir, this.getPlanSrcName() + genTime + "_DOWNLOAD" + ".tsv");
                migrationPlanFile = new File(planDir, this.getPlanSrcName() + genTime + "_MIG" + ".tsv");


                oswD = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(downloadPlanFile,false)),"UTF-8");
                oswM = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(migrationPlanFile,false)),"UTF-8");

                int cntD = 0;
                int cntM = 0;

                while(rs.next()) {
                    String downloadPlanItem = createDownloadPlanItem(rs);

                    if(downloadPlanItem != null) {

                        if(cntD != 0) {
                            oswD.write(System.lineSeparator());
                        }
                        cntD++;

//                        if(log.isDebugEnabled()) {
//                            log.debug("Plan Item Generated!! [Download] : "+ downloadPlanItem);
//                        }


                        oswD.write(downloadPlanItem);
                        oswD.flush();
                    }

                    String migrationPlanItem = createMigrationPlanItem(rs);
                    if(migrationPlanItem != null) {

                        if(cntM != 0) {
                            oswM.write(System.lineSeparator());
                        }
                        cntM++;

//                        if(log.isDebugEnabled()) {
//                            log.debug("Plan Item Generated!! [Migration] : "+ migrationPlanItem);
//                        }

                        oswM.write(migrationPlanItem);
                        oswM.flush();
                    }
                }

            } catch (SQLException e) {
                log.error(e.getMessage(),e);
                throw new PlanGeneratingException(e.getMessage(), e);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
                throw new PlanGeneratingException(e.getMessage(), e);
            } finally {
                if(oswD != null ) try {oswD.close();} catch (IOException e) {}
                if(oswM != null ) try {oswM.close();} catch (IOException e) {}

            }


        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(),e);
            throw new PlanGeneratingException(e.getMessage(), e);
        }

        return new File[]{downloadPlanFile,migrationPlanFile};

    }
}
