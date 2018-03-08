package com.cwoongc.st11.image_migrator.factory;

import com.cwoongc.st11.image_migrator.downloader.ImgDownLoader;
import com.cwoongc.st11.image_migrator.img_url_source.ImgURLSource;
import com.cwoongc.st11.image_migrator.img_url_target.ImgURLTarget;
import com.cwoongc.st11.image_migrator.migrator.URLDataMigrator;
import com.cwoongc.st11.image_migrator.plan.PlanGenerator;
import com.cwoongc.st11.image_migrator.url_replacer.URLReplacer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ImgMigrationPrototypeFactory<PG extends PlanGenerator,IUS extends ImgURLSource,UR extends URLReplacer,IDL extends ImgDownLoader, IUT extends ImgURLTarget, UDM extends URLDataMigrator> {

    private static Gson gson;
    private Class<PG> classPG;
    private Class<IUS> classIUS;
    private Class<UR> classUR;
    private Class<IDL> classIDL;
    private Class<IUT> classIUT;
    private Class<UDM> classUDM;

    private ApplicationContext ctx;

    public ImgMigrationPrototypeFactory(Class<PG> classPG, Class<IUS> classIUS, Class<UR> classUR, Class<IDL> classIDL, Class<IUT> classIUT, Class<UDM> classUDM, ApplicationContext ctx) {
        this.classPG = classPG;
        this.classIUS = classIUS;
        this.classUR = classUR;
        this.classIDL = classIDL;
        this.classIUT = classIUT;
        this.classUDM = classUDM;
        this.ctx = ctx;

        if(gson == null && ctx == null) gson = new GsonBuilder().serializeNulls().create();
    }


    public PG createPlanGenerator(IUS imgURLSource, UR urlReplacer) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException {

        PG planGenerator = null;

        if(ctx != null) {
            planGenerator = ctx.getBean(classPG);

            Field imgUrlSourceField = classPG.getSuperclass().getDeclaredField("imgUrlSource");
            Field urlReplacerField = classPG.getSuperclass().getDeclaredField("urlReplacer");
            Field gsonField = classPG.getSuperclass().getDeclaredField("gson");

            imgUrlSourceField.setAccessible(true);
            urlReplacerField.setAccessible(true);
            gsonField.setAccessible(true);

            imgUrlSourceField.set(planGenerator, ctx.getBean(classIUS));
            urlReplacerField.set(planGenerator, ctx.getBean(classUR));
            gsonField.set(planGenerator, ctx.getBean(Gson.class));


        } else {
            planGenerator = classPG.getConstructor(classIUS, classUR, gson.getClass()).newInstance(createImgURLSource(), createURLReplacer(), gson);
        }

        return planGenerator;
    }



    public IUS createImgURLSource() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        IUS imgURLSource = null;

        if(ctx != null) {
            imgURLSource = ctx.getBean(classIUS);
        } else {
            imgURLSource = classIUS.getConstructor().newInstance();
        }

        return imgURLSource;
    }

    public UR createURLReplacer() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        UR urlReplacer = null;

        if(ctx != null) {
            urlReplacer = ctx.getBean(classUR);
        } else {
            urlReplacer = classUR.getConstructor().newInstance();
        }

        return urlReplacer;
    }

    public IDL createImgDownLoader(File imgDownLoadPlan) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        IDL imgDownLoader = null;

        if(ctx != null) {
            imgDownLoader = ctx.getBean(classIDL);

            Field planFileField = imgDownLoader.getClass().getSuperclass().getDeclaredField("planFile");
            planFileField.setAccessible(true);

            planFileField.set(imgDownLoader,imgDownLoadPlan);

            Field gsonField = imgDownLoader.getClass().getSuperclass().getDeclaredField("gson");
            gsonField.setAccessible(true);
            gsonField.set(imgDownLoader, ctx.getBean(Gson.class));

        } else {
            imgDownLoader = classIDL.getConstructor(imgDownLoadPlan.getClass(), Gson.class).newInstance(imgDownLoadPlan, gson);
        }

        return imgDownLoader;
    }

    public IUT createImgURLTarget() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        IUT imgURLTarget = null;

        if(ctx != null) {
            imgURLTarget = ctx.getBean(classIUT);
        } else {
            imgURLTarget = classIUT.getConstructor().newInstance();
        }

        return imgURLTarget;
    }

    public UDM createURLDataMigrator(File urlDataMigrationPlan) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        UDM urlDataMigrator = null;

        if(ctx != null) {
            urlDataMigrator = ctx.getBean(classUDM);

            Field imgUrlTargetField = classUDM.getSuperclass().getDeclaredField("imgURLTarget");
            imgUrlTargetField.setAccessible(true);
            imgUrlTargetField.set(urlDataMigrator, ctx.getBean(classIUT));


            Field planFileField = urlDataMigrator.getClass().getSuperclass().getDeclaredField("planFile");
            planFileField.setAccessible(true);
            planFileField.set(urlDataMigrator,urlDataMigrationPlan);

            Field gsonField = urlDataMigrator.getClass().getSuperclass().getDeclaredField("gson");
            gsonField.setAccessible(true);
            gsonField.set(urlDataMigrator, ctx.getBean(Gson.class));
        } else {
            urlDataMigrator = classUDM.getConstructor(classIUT, Gson.class).newInstance(createImgURLTarget(), gson);
        }

        return urlDataMigrator;
    }


}
