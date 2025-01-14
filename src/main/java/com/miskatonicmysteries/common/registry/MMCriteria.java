package com.miskatonicmysteries.common.registry;

import com.miskatonicmysteries.common.criterion.LevelUpCriterion;
import com.miskatonicmysteries.common.criterion.RiteCastCriterion;
import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;

public class MMCriteria {
    public static final RiteCastCriterion RITE_CAST = new RiteCastCriterion();
    public static final LevelUpCriterion LEVEL_UP = new LevelUpCriterion();

    public static void init() {
        CriterionRegistry.register(RITE_CAST);
        CriterionRegistry.register(LEVEL_UP);
    }
}
