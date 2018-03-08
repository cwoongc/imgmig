package com.cwoongc.st11.de_closing;

import com.cwoongc.st11.de_closing.common.DesignEditorClosingURLReplacer;
import com.cwoongc.st11.de_closing.pd_opt_dtl_image.downloader.PdOptDtlImageImgDownLoader;
import com.cwoongc.st11.de_closing.pd_opt_dtl_image.img_url_source.PdOptDtlImageLocalImgURLSource;
import com.cwoongc.st11.de_closing.pd_opt_dtl_image.img_url_source.PdOptDtlImageProductionImgURLSource;
import com.cwoongc.st11.de_closing.pd_opt_dtl_image.img_url_source.PdOptDtlImageProductionTestImgURLSource;
import com.cwoongc.st11.de_closing.pd_opt_dtl_image.img_url_source.PdOptDtlImageStageImgURLSource;
import com.cwoongc.st11.de_closing.pd_opt_dtl_image.img_url_target.*;
import com.cwoongc.st11.de_closing.pd_opt_dtl_image.migrator.PdOptDtlImageURLDataMigrator;
import com.cwoongc.st11.de_closing.pd_opt_dtl_image.plan_generator.PdOptDtlImagePlanGenerator;
import com.cwoongc.st11.de_closing.pd_opt_value.downloader.PdOptValueImgDownLoader;
import com.cwoongc.st11.de_closing.pd_opt_value.img_url_source.PdOptValueLocalImgURLSource;
import com.cwoongc.st11.de_closing.pd_opt_value.img_url_source.PdOptValueProductionImgURLSource;
import com.cwoongc.st11.de_closing.pd_opt_value.img_url_source.PdOptValueProductionTestImgURLSource;
import com.cwoongc.st11.de_closing.pd_opt_value.img_url_source.PdOptValueStageImgURLSource;
import com.cwoongc.st11.de_closing.pd_opt_value.img_url_target.*;
import com.cwoongc.st11.de_closing.pd_opt_value.migrator.PdOptValueURLDataMigrator;
import com.cwoongc.st11.de_closing.pd_opt_value.plan_generator.PdOptValuePlanGenerator;
import com.cwoongc.st11.de_closing.pd_prd_desc.downloader.PdPrdDescImgDownLoader;
import com.cwoongc.st11.de_closing.pd_prd_desc.img_url_source.PdPrdDescLocalImgURLSource;
import com.cwoongc.st11.de_closing.pd_prd_desc.img_url_source.PdPrdDescProductionImgURLSource;
import com.cwoongc.st11.de_closing.pd_prd_desc.img_url_source.PdPrdDescProductionTestImgURLSource;
import com.cwoongc.st11.de_closing.pd_prd_desc.img_url_source.PdPrdDescStageImgURLSource;
import com.cwoongc.st11.de_closing.pd_prd_desc.img_url_target.*;
import com.cwoongc.st11.de_closing.pd_prd_desc.migrator.PdPrdDescURLDataMigrator;
import com.cwoongc.st11.de_closing.pd_prd_desc.plan_generator.PdPrdDescPlanGenerator;
import com.cwoongc.st11.image_migrator.ImageMigrator;
import com.cwoongc.st11.image_migrator.factory.ImgMigrationPrototypeFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;

@Configuration
public class DEClosingMain {

    @Profile("local-all-test")
    @Bean
    CommandLineRunner localAllTest(final ApplicationContext ctx) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                ArrayList<ImgMigrationPrototypeFactory> factories = new ArrayList<ImgMigrationPrototypeFactory>();

                ImgMigrationPrototypeFactory<PdPrdDescPlanGenerator, PdPrdDescLocalImgURLSource, DesignEditorClosingURLReplacer, PdPrdDescImgDownLoader, PdPrdDescLocalTestImgURLTarget, PdPrdDescURLDataMigrator> pdPrdDesc = new ImgMigrationPrototypeFactory<PdPrdDescPlanGenerator, PdPrdDescLocalImgURLSource, DesignEditorClosingURLReplacer, PdPrdDescImgDownLoader,PdPrdDescLocalTestImgURLTarget, PdPrdDescURLDataMigrator>(
                        PdPrdDescPlanGenerator.class, PdPrdDescLocalImgURLSource.class, DesignEditorClosingURLReplacer.class, PdPrdDescImgDownLoader.class, PdPrdDescLocalTestImgURLTarget.class, PdPrdDescURLDataMigrator.class, ctx
                );

                ImgMigrationPrototypeFactory<PdOptValuePlanGenerator, PdOptValueLocalImgURLSource, DesignEditorClosingURLReplacer, PdOptValueImgDownLoader, PdOptValueLocalTestImgURLTarget, PdOptValueURLDataMigrator> pdOptValue = new ImgMigrationPrototypeFactory<PdOptValuePlanGenerator, PdOptValueLocalImgURLSource, DesignEditorClosingURLReplacer, PdOptValueImgDownLoader, PdOptValueLocalTestImgURLTarget, PdOptValueURLDataMigrator>(
                        PdOptValuePlanGenerator.class, PdOptValueLocalImgURLSource.class, DesignEditorClosingURLReplacer.class, PdOptValueImgDownLoader.class, PdOptValueLocalTestImgURLTarget.class, PdOptValueURLDataMigrator.class, ctx
                );

                ImgMigrationPrototypeFactory<PdOptDtlImagePlanGenerator, PdOptDtlImageLocalImgURLSource, DesignEditorClosingURLReplacer, PdOptDtlImageImgDownLoader, PdOptDtlImageLocalTestImgURLTarget, PdOptDtlImageURLDataMigrator> pdOptDtlImage = new ImgMigrationPrototypeFactory<PdOptDtlImagePlanGenerator, PdOptDtlImageLocalImgURLSource, DesignEditorClosingURLReplacer, PdOptDtlImageImgDownLoader,PdOptDtlImageLocalTestImgURLTarget, PdOptDtlImageURLDataMigrator>(
                        PdOptDtlImagePlanGenerator.class, PdOptDtlImageLocalImgURLSource.class, DesignEditorClosingURLReplacer.class, PdOptDtlImageImgDownLoader.class, PdOptDtlImageLocalTestImgURLTarget.class, PdOptDtlImageURLDataMigrator.class, ctx
                );

                factories.add(pdPrdDesc);
                factories.add(pdOptValue);
                factories.add(pdOptDtlImage);

                ImageMigrator imageMigrator = new ImageMigrator(factories);

                imageMigrator.migrate();
            }
        };
    }


    @Profile("local-all")
    @Bean
    CommandLineRunner localAll(final ApplicationContext ctx) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                ArrayList<ImgMigrationPrototypeFactory> factories = new ArrayList<ImgMigrationPrototypeFactory>();

                ImgMigrationPrototypeFactory<PdPrdDescPlanGenerator, PdPrdDescLocalImgURLSource, DesignEditorClosingURLReplacer, PdPrdDescImgDownLoader, PdPrdDescLocalImgURLTarget, PdPrdDescURLDataMigrator> pdPrdDesc = new ImgMigrationPrototypeFactory<PdPrdDescPlanGenerator, PdPrdDescLocalImgURLSource, DesignEditorClosingURLReplacer, PdPrdDescImgDownLoader,PdPrdDescLocalImgURLTarget, PdPrdDescURLDataMigrator>(
                        PdPrdDescPlanGenerator.class, PdPrdDescLocalImgURLSource.class, DesignEditorClosingURLReplacer.class, PdPrdDescImgDownLoader.class, PdPrdDescLocalImgURLTarget.class, PdPrdDescURLDataMigrator.class, ctx
                );

                ImgMigrationPrototypeFactory<PdOptValuePlanGenerator, PdOptValueLocalImgURLSource, DesignEditorClosingURLReplacer, PdOptValueImgDownLoader, PdOptValueLocalImgURLTarget, PdOptValueURLDataMigrator> pdOptValue = new ImgMigrationPrototypeFactory<PdOptValuePlanGenerator, PdOptValueLocalImgURLSource, DesignEditorClosingURLReplacer, PdOptValueImgDownLoader, PdOptValueLocalImgURLTarget, PdOptValueURLDataMigrator>(
                        PdOptValuePlanGenerator.class, PdOptValueLocalImgURLSource.class, DesignEditorClosingURLReplacer.class, PdOptValueImgDownLoader.class, PdOptValueLocalImgURLTarget.class, PdOptValueURLDataMigrator.class, ctx
                );

                ImgMigrationPrototypeFactory<PdOptDtlImagePlanGenerator, PdOptDtlImageLocalImgURLSource, DesignEditorClosingURLReplacer, PdOptDtlImageImgDownLoader, PdOptDtlImageLocalImgURLTarget, PdOptDtlImageURLDataMigrator> pdOptDtlImage = new ImgMigrationPrototypeFactory<PdOptDtlImagePlanGenerator, PdOptDtlImageLocalImgURLSource, DesignEditorClosingURLReplacer, PdOptDtlImageImgDownLoader,PdOptDtlImageLocalImgURLTarget, PdOptDtlImageURLDataMigrator>(
                        PdOptDtlImagePlanGenerator.class, PdOptDtlImageLocalImgURLSource.class, DesignEditorClosingURLReplacer.class, PdOptDtlImageImgDownLoader.class, PdOptDtlImageLocalImgURLTarget.class, PdOptDtlImageURLDataMigrator.class, ctx
                );

                factories.add(pdPrdDesc);
                factories.add(pdOptValue);
                factories.add(pdOptDtlImage);

                ImageMigrator imageMigrator = new ImageMigrator(factories);

                imageMigrator.migrate();
            }
        };
    }





    @Profile("stage-all-test")
    @Bean
    CommandLineRunner stageAllTest(final ApplicationContext ctx) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                ArrayList<ImgMigrationPrototypeFactory> factories = new ArrayList<ImgMigrationPrototypeFactory>();

                ImgMigrationPrototypeFactory<PdPrdDescPlanGenerator, PdPrdDescStageImgURLSource, DesignEditorClosingURLReplacer, PdPrdDescImgDownLoader, PdPrdDescStageTestImgURLTarget, PdPrdDescURLDataMigrator> pdPrdDesc = new ImgMigrationPrototypeFactory<PdPrdDescPlanGenerator, PdPrdDescStageImgURLSource, DesignEditorClosingURLReplacer, PdPrdDescImgDownLoader,PdPrdDescStageTestImgURLTarget, PdPrdDescURLDataMigrator>(
                        PdPrdDescPlanGenerator.class, PdPrdDescStageImgURLSource.class, DesignEditorClosingURLReplacer.class, PdPrdDescImgDownLoader.class, PdPrdDescStageTestImgURLTarget.class, PdPrdDescURLDataMigrator.class, ctx
                );

                ImgMigrationPrototypeFactory<PdOptValuePlanGenerator, PdOptValueStageImgURLSource, DesignEditorClosingURLReplacer, PdOptValueImgDownLoader, PdOptValueStageTestImgURLTarget, PdOptValueURLDataMigrator> pdOptValue = new ImgMigrationPrototypeFactory<PdOptValuePlanGenerator, PdOptValueStageImgURLSource, DesignEditorClosingURLReplacer, PdOptValueImgDownLoader, PdOptValueStageTestImgURLTarget, PdOptValueURLDataMigrator>(
                        PdOptValuePlanGenerator.class, PdOptValueStageImgURLSource.class, DesignEditorClosingURLReplacer.class, PdOptValueImgDownLoader.class, PdOptValueStageTestImgURLTarget.class, PdOptValueURLDataMigrator.class, ctx
                );

                ImgMigrationPrototypeFactory<PdOptDtlImagePlanGenerator, PdOptDtlImageStageImgURLSource, DesignEditorClosingURLReplacer, PdOptDtlImageImgDownLoader, PdOptDtlImageStageTestImgURLTarget, PdOptDtlImageURLDataMigrator> pdOptDtlImage = new ImgMigrationPrototypeFactory<PdOptDtlImagePlanGenerator, PdOptDtlImageStageImgURLSource, DesignEditorClosingURLReplacer, PdOptDtlImageImgDownLoader,PdOptDtlImageStageTestImgURLTarget, PdOptDtlImageURLDataMigrator>(
                        PdOptDtlImagePlanGenerator.class, PdOptDtlImageStageImgURLSource.class, DesignEditorClosingURLReplacer.class, PdOptDtlImageImgDownLoader.class, PdOptDtlImageStageTestImgURLTarget.class, PdOptDtlImageURLDataMigrator.class, ctx
                );

                factories.add(pdPrdDesc);
                factories.add(pdOptValue);
                factories.add(pdOptDtlImage);

                ImageMigrator imageMigrator = new ImageMigrator(factories);

                imageMigrator.migrate();
            }
        };
    }

    @Profile("stage-all")
    @Bean
    CommandLineRunner stageAll(final ApplicationContext ctx) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                ArrayList<ImgMigrationPrototypeFactory> factories = new ArrayList<ImgMigrationPrototypeFactory>();

                ImgMigrationPrototypeFactory<PdPrdDescPlanGenerator, PdPrdDescStageImgURLSource, DesignEditorClosingURLReplacer, PdPrdDescImgDownLoader, PdPrdDescStageImgURLTarget, PdPrdDescURLDataMigrator> pdPrdDesc = new ImgMigrationPrototypeFactory<PdPrdDescPlanGenerator, PdPrdDescStageImgURLSource, DesignEditorClosingURLReplacer, PdPrdDescImgDownLoader,PdPrdDescStageImgURLTarget, PdPrdDescURLDataMigrator>(
                        PdPrdDescPlanGenerator.class, PdPrdDescStageImgURLSource.class, DesignEditorClosingURLReplacer.class, PdPrdDescImgDownLoader.class, PdPrdDescStageImgURLTarget.class, PdPrdDescURLDataMigrator.class, ctx
                );

                ImgMigrationPrototypeFactory<PdOptValuePlanGenerator, PdOptValueStageImgURLSource, DesignEditorClosingURLReplacer, PdOptValueImgDownLoader, PdOptValueStageImgURLTarget, PdOptValueURLDataMigrator> pdOptValue = new ImgMigrationPrototypeFactory<PdOptValuePlanGenerator, PdOptValueStageImgURLSource, DesignEditorClosingURLReplacer, PdOptValueImgDownLoader, PdOptValueStageImgURLTarget, PdOptValueURLDataMigrator>(
                        PdOptValuePlanGenerator.class, PdOptValueStageImgURLSource.class, DesignEditorClosingURLReplacer.class, PdOptValueImgDownLoader.class, PdOptValueStageImgURLTarget.class, PdOptValueURLDataMigrator.class, ctx
                );

                ImgMigrationPrototypeFactory<PdOptDtlImagePlanGenerator, PdOptDtlImageStageImgURLSource, DesignEditorClosingURLReplacer, PdOptDtlImageImgDownLoader, PdOptDtlImageStageImgURLTarget, PdOptDtlImageURLDataMigrator> pdOptDtlImage = new ImgMigrationPrototypeFactory<PdOptDtlImagePlanGenerator, PdOptDtlImageStageImgURLSource, DesignEditorClosingURLReplacer, PdOptDtlImageImgDownLoader,PdOptDtlImageStageImgURLTarget, PdOptDtlImageURLDataMigrator>(
                        PdOptDtlImagePlanGenerator.class, PdOptDtlImageStageImgURLSource.class, DesignEditorClosingURLReplacer.class, PdOptDtlImageImgDownLoader.class, PdOptDtlImageStageImgURLTarget.class, PdOptDtlImageURLDataMigrator.class, ctx
                );

                factories.add(pdPrdDesc);
                factories.add(pdOptValue);
                factories.add(pdOptDtlImage);

                ImageMigrator imageMigrator = new ImageMigrator(factories);

                imageMigrator.migrate();
            }
        };
    }

    @Profile("production-all-test")
    @Bean
    CommandLineRunner productionAllTest(final ApplicationContext ctx) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                ArrayList<ImgMigrationPrototypeFactory> factories = new ArrayList<ImgMigrationPrototypeFactory>();

                ImgMigrationPrototypeFactory<PdPrdDescPlanGenerator, PdPrdDescProductionTestImgURLSource, DesignEditorClosingURLReplacer, PdPrdDescImgDownLoader, PdPrdDescProductionTestImgURLTarget, PdPrdDescURLDataMigrator> pdPrdDesc = new ImgMigrationPrototypeFactory<PdPrdDescPlanGenerator, PdPrdDescProductionTestImgURLSource, DesignEditorClosingURLReplacer, PdPrdDescImgDownLoader,PdPrdDescProductionTestImgURLTarget, PdPrdDescURLDataMigrator>(
                        PdPrdDescPlanGenerator.class, PdPrdDescProductionTestImgURLSource.class, DesignEditorClosingURLReplacer.class, PdPrdDescImgDownLoader.class, PdPrdDescProductionTestImgURLTarget.class, PdPrdDescURLDataMigrator.class, ctx
                );

                ImgMigrationPrototypeFactory<PdOptValuePlanGenerator, PdOptValueProductionTestImgURLSource, DesignEditorClosingURLReplacer, PdOptValueImgDownLoader, PdOptValueProductionTestImgURLTarget, PdOptValueURLDataMigrator> pdOptValue = new ImgMigrationPrototypeFactory<PdOptValuePlanGenerator, PdOptValueProductionTestImgURLSource, DesignEditorClosingURLReplacer, PdOptValueImgDownLoader, PdOptValueProductionTestImgURLTarget, PdOptValueURLDataMigrator>(
                        PdOptValuePlanGenerator.class, PdOptValueProductionTestImgURLSource.class, DesignEditorClosingURLReplacer.class, PdOptValueImgDownLoader.class, PdOptValueProductionTestImgURLTarget.class, PdOptValueURLDataMigrator.class, ctx
                );

                ImgMigrationPrototypeFactory<PdOptDtlImagePlanGenerator, PdOptDtlImageProductionTestImgURLSource, DesignEditorClosingURLReplacer, PdOptDtlImageImgDownLoader, PdOptDtlImageProductionTestImgURLTarget, PdOptDtlImageURLDataMigrator> pdOptDtlImage = new ImgMigrationPrototypeFactory<PdOptDtlImagePlanGenerator, PdOptDtlImageProductionTestImgURLSource, DesignEditorClosingURLReplacer, PdOptDtlImageImgDownLoader,PdOptDtlImageProductionTestImgURLTarget, PdOptDtlImageURLDataMigrator>(
                        PdOptDtlImagePlanGenerator.class, PdOptDtlImageProductionTestImgURLSource.class, DesignEditorClosingURLReplacer.class, PdOptDtlImageImgDownLoader.class, PdOptDtlImageProductionTestImgURLTarget.class, PdOptDtlImageURLDataMigrator.class, ctx
                );

                factories.add(pdPrdDesc);
                factories.add(pdOptValue);
                factories.add(pdOptDtlImage);

                ImageMigrator imageMigrator = new ImageMigrator(factories);

                imageMigrator.migrate();
            }
        };
    }


    @Profile("production-all")
    @Bean
    CommandLineRunner productionAll(final ApplicationContext ctx) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                ArrayList<ImgMigrationPrototypeFactory> factories = new ArrayList<ImgMigrationPrototypeFactory>();

                ImgMigrationPrototypeFactory<PdPrdDescPlanGenerator, PdPrdDescProductionImgURLSource, DesignEditorClosingURLReplacer, PdPrdDescImgDownLoader, PdPrdDescProductionImgURLTarget, PdPrdDescURLDataMigrator> pdPrdDesc = new ImgMigrationPrototypeFactory<PdPrdDescPlanGenerator, PdPrdDescProductionImgURLSource, DesignEditorClosingURLReplacer, PdPrdDescImgDownLoader,PdPrdDescProductionImgURLTarget, PdPrdDescURLDataMigrator>(
                        PdPrdDescPlanGenerator.class, PdPrdDescProductionImgURLSource.class, DesignEditorClosingURLReplacer.class, PdPrdDescImgDownLoader.class, PdPrdDescProductionImgURLTarget.class, PdPrdDescURLDataMigrator.class, ctx
                );

                ImgMigrationPrototypeFactory<PdOptValuePlanGenerator, PdOptValueProductionImgURLSource, DesignEditorClosingURLReplacer, PdOptValueImgDownLoader, PdOptValueProductionImgURLTarget, PdOptValueURLDataMigrator> pdOptValue = new ImgMigrationPrototypeFactory<PdOptValuePlanGenerator, PdOptValueProductionImgURLSource, DesignEditorClosingURLReplacer, PdOptValueImgDownLoader, PdOptValueProductionImgURLTarget, PdOptValueURLDataMigrator>(
                        PdOptValuePlanGenerator.class, PdOptValueProductionImgURLSource.class, DesignEditorClosingURLReplacer.class, PdOptValueImgDownLoader.class, PdOptValueProductionImgURLTarget.class, PdOptValueURLDataMigrator.class, ctx
                );

                ImgMigrationPrototypeFactory<PdOptDtlImagePlanGenerator, PdOptDtlImageProductionImgURLSource, DesignEditorClosingURLReplacer, PdOptDtlImageImgDownLoader, PdOptDtlImageProductionImgURLTarget, PdOptDtlImageURLDataMigrator> pdOptDtlImage = new ImgMigrationPrototypeFactory<PdOptDtlImagePlanGenerator, PdOptDtlImageProductionImgURLSource, DesignEditorClosingURLReplacer, PdOptDtlImageImgDownLoader,PdOptDtlImageProductionImgURLTarget, PdOptDtlImageURLDataMigrator>(
                        PdOptDtlImagePlanGenerator.class, PdOptDtlImageProductionImgURLSource.class, DesignEditorClosingURLReplacer.class, PdOptDtlImageImgDownLoader.class, PdOptDtlImageProductionImgURLTarget.class, PdOptDtlImageURLDataMigrator.class, ctx
                );

                factories.add(pdPrdDesc);
                factories.add(pdOptValue);
                factories.add(pdOptDtlImage);

                ImageMigrator imageMigrator = new ImageMigrator(factories);

                imageMigrator.migrate();
            }
        };
    }

}
