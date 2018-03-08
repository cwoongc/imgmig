package com.cwoongc.st11.image_migrator.plan;

import java.util.concurrent.Callable;

public class PlanItemProcessor implements Callable<Object> {

    private final String planItem;
    private final PlanItemProcessorLogic planItemProcessorLogic;

    @Override
    public Object call() throws Exception {
        return this.planItemProcessorLogic.processPlanItem(planItem);
    }

    public PlanItemProcessor(String planItem, PlanItemProcessorLogic planItemProcessorLogic) {
        this.planItem = planItem;
        this.planItemProcessorLogic = planItemProcessorLogic;
    }
}
