package com.cwoongc.st11.image_migrator;

import com.cwoongc.st11.image_migrator.downloader.ImgDownLoader;
import com.cwoongc.st11.image_migrator.factory.ImgMigrationPrototypeFactory;
import com.cwoongc.st11.image_migrator.img_url_source.ImgURLSource;
import com.cwoongc.st11.image_migrator.migrator.URLDataMigrator;
import com.cwoongc.st11.image_migrator.plan.PlanGenerator;
import com.cwoongc.st11.image_migrator.url_replacer.URLReplacer;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

@Slf4j
public class ImageMigrator {

    private ArrayList<ImgMigrationPrototypeFactory> factories;
    private ArrayList<File[]> planFiles;

    public ImageMigrator(ArrayList<ImgMigrationPrototypeFactory> factories) {
        this.factories = factories;
    }

    public void migrate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        log.info("***** Start!!!");

        planFiles = new ArrayList<File[]>();

        //Plan files generating...
        for(int p=1; p<=factories.size();p++) {
            ImgMigrationPrototypeFactory factory = factories.get(p-1);

            ImgURLSource imgURLSource = factory.createImgURLSource();
            URLReplacer urlReplacer = factory.createURLReplacer();
            PlanGenerator planGenerator = factory.createPlanGenerator(imgURLSource, urlReplacer);

            log.info(String.format("***** 1.%d.1. Plan files generating [%s] [begin]",p,planGenerator.getClass().getSimpleName()));
            File[] downloadAndMigPlanFiles = planGenerator.generatePlanFiles();
            planFiles.add(downloadAndMigPlanFiles);
            log.info(String.format("***** 1.%d.2. Plan files generating [%s] [done]",p,planGenerator.getClass().getSimpleName()));
        }


        //Images downloading...
        for(int p=1; p<=factories.size();p++) {
            ImgMigrationPrototypeFactory factory = factories.get(p-1);
            ImgDownLoader imgDownLoader = factory.createImgDownLoader(planFiles.get(p-1)[0]);

            log.info(String.format("***** 2.%d.1. Images downloading [%s] [begin]",p,imgDownLoader.getClass().getSimpleName()));
            imgDownLoader.download(8);
            log.info(String.format("***** 2.%d.2. Images downloading [%s] [done]",p,imgDownLoader.getClass().getSimpleName()));
        }


        //URL data migration...
        for(int p=1; p<=factories.size();p++) {
            ImgMigrationPrototypeFactory factory = factories.get(p-1);
            URLDataMigrator urlDataMigrator = factory.createURLDataMigrator(planFiles.get(p-1)[1]);

            log.info(String.format("***** 3.%d.1. URL data is being migrated [%s] [begin]",p,urlDataMigrator.getClass().getSimpleName()));
            urlDataMigrator.migrateList(6);
            log.info(String.format("***** 3.%d.2. URL data is being migrated [%s] [done]",p,urlDataMigrator.getClass().getSimpleName()));
        }

        log.info("***** Done!!!");

    }
}
