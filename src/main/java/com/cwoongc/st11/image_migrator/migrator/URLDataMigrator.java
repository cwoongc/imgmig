package com.cwoongc.st11.image_migrator.migrator;

import com.cwoongc.st11.image_migrator.exception.ImgDownloadingException;
import com.cwoongc.st11.image_migrator.exception.URLDataMigrationException;
import com.cwoongc.st11.image_migrator.img_url_target.ImgURLTarget;
import com.cwoongc.st11.image_migrator.plan.PlanItemListProcessor;
import com.cwoongc.st11.image_migrator.plan.PlanItemProcessor;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public abstract class URLDataMigrator {

    protected ImgURLTarget imgURLTarget;
    protected abstract ImgURLTarget getImgURLTarget();
    protected File planFile;
    protected abstract File getPlanFile();
    protected Gson gson;
    protected abstract Gson getGson();
    protected final int DEFAULT_PLAN_ITEM_PROCESSOR_SIZE = 3;

    protected abstract PlanItemProcessor createMigrationPlanItemProcessor(String planItem, final Connection conn, final PreparedStatement ps);


    protected abstract PlanItemListProcessor createMigrationPlanItemListProcessor(List<String> planItemList, final Connection conn, final PreparedStatement ps);


    public void migrateList(Integer... planItemProcessorSize) {

        if (planFile == null) throw new URLDataMigrationException("The plan file is null.");

        int threadSize = planItemProcessorSize[0] == null || planItemProcessorSize[0] < 1 ? DEFAULT_PLAN_ITEM_PROCESSOR_SIZE : planItemProcessorSize[0];

        try {
            Class.forName(imgURLTarget.getDriverClassName());

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(planFile), "UTF-8"))) {

                String planItem = null;

                ExecutorService pool = Executors.newFixedThreadPool(threadSize);

                Set<Future<List<Object>>> futures = new HashSet<>();

                int planItemCnt = 0;
                List<String> planItemList = new ArrayList<String>();

                Connection conn = null;
                PreparedStatement ps = null;

                while ((planItem = br.readLine()) != null) {

                    planItemList.add(planItem);
                    if(planItemList.size() <= 10) continue;

                    conn = DriverManager.getConnection(imgURLTarget.getDataSourceUrl(), imgURLTarget.getUser(), imgURLTarget.getPassword());
                    conn.setAutoCommit(false);

                    String updateSql = imgURLTarget.getUpdateSql();
                    ps = conn.prepareStatement(updateSql);

                    PlanItemListProcessor planItemListProcessor = createMigrationPlanItemListProcessor(planItemList, conn, ps);

                    planItemList = new ArrayList<String>();

                    Future<List<Object>> future = pool.submit(planItemListProcessor);

                    futures.add(future);
                }

                if(planItemList.size() > 0) {

                    conn = DriverManager.getConnection(imgURLTarget.getDataSourceUrl(), imgURLTarget.getUser(), imgURLTarget.getPassword());
                    conn.setAutoCommit(false);

                    String updateSql = imgURLTarget.getUpdateSql();
                    ps = conn.prepareStatement(updateSql);

                    PlanItemListProcessor planItemListProcessor = createMigrationPlanItemListProcessor(planItemList, conn, ps);

                    Future<List<Object>> future = pool.submit(planItemListProcessor);

                    futures.add(future);
                }

                List<Future<List<Object>>> doneWorks = new ArrayList<>();

                while (futures.size() > 0) {

                    for (Future<List<Object>> f : futures) {
                        if (f.isDone()) {
                            f.get();
                            doneWorks.add(f);
                        }
                    }

                    futures.removeAll(doneWorks);
                    doneWorks.clear();
                }

                pool.shutdown();

            } catch (FileNotFoundException e) {
                log.error(e.getMessage(), e);
                throw new URLDataMigrationException(e.getMessage(), e);

            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new ImgDownloadingException(e.getMessage(), e);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new URLDataMigrationException(e.getMessage(), e);
            }


        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new URLDataMigrationException(e.getMessage(), e);
        }
    }


    public void migrate(Integer... planItemProcessorSize) {

        if (planFile == null) throw new URLDataMigrationException("The plan file is null.");

        int threadSize = planItemProcessorSize[0] == null || planItemProcessorSize[0] < 1 ? DEFAULT_PLAN_ITEM_PROCESSOR_SIZE : planItemProcessorSize[0];

        try {
            Class.forName(imgURLTarget.getDriverClassName());

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(planFile), "UTF-8"))) {

                String planItem = null;

                ExecutorService pool = Executors.newFixedThreadPool(threadSize);

                Set<Future<Object>> futures = new HashSet<>();

                while ((planItem = br.readLine()) != null) {

                    Connection conn = DriverManager.getConnection(imgURLTarget.getDataSourceUrl(), imgURLTarget.getUser(), imgURLTarget.getPassword());
                    String updateSql = imgURLTarget.getUpdateSql();
                    PreparedStatement ps = conn.prepareStatement(updateSql);

                    PlanItemProcessor planItemProcessor = createMigrationPlanItemProcessor(planItem, conn, ps);

                    Future<Object> future = pool.submit(planItemProcessor);

                    futures.add(future);
                }

                List<Future<Object>> doneWorks = new ArrayList<>();

                while (futures.size() > 0) {

                    for (Future<Object> f : futures) {
                        if (f.isDone()) {
                            f.get();
                            doneWorks.add(f);
                        }
                    }

                    futures.removeAll(doneWorks);
                    doneWorks.clear();
                }

                pool.shutdown();

            } catch (FileNotFoundException e) {
                log.error(e.getMessage(), e);
                throw new URLDataMigrationException(e.getMessage(), e);

            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new ImgDownloadingException(e.getMessage(), e);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new URLDataMigrationException(e.getMessage(), e);
            }


        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new URLDataMigrationException(e.getMessage(), e);
        }
    }




}
