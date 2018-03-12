package com.cwoongc.st11.image_migrator.plan;

import java.util.List;
import java.util.concurrent.Callable;

public class PlanItemListProcessor implements Callable<List<Object>> {

    private final List<String> planItemList;
    private final PlanItemListProcessorLogic planItemListProcessorLogic;

    @Override
    public List<Object> call() throws Exception {
        return this.planItemListProcessorLogic.processPlanItemList(planItemList);
    }

    public PlanItemListProcessor(List<String> planItemList, PlanItemListProcessorLogic planItemListProcessorLogic) {
        this.planItemList = planItemList;
        this.planItemListProcessorLogic = planItemListProcessorLogic;
    }
}
