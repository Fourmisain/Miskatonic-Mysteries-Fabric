package com.miskatonicmysteries.common.criterion;

import com.google.gson.JsonObject;
import com.miskatonicmysteries.common.feature.recipe.rite.Rite;
import com.miskatonicmysteries.common.lib.Constants;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class RiteCastCriterion extends AbstractCriterion<RiteCastCriterion.Conditions> {
    private static final Identifier ID = new Identifier(Constants.MOD_ID, "rite_cast");

    public Identifier getId() {
        return ID;
    }

    public RiteCastCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) throws NullPointerException {
        Identifier riteId = new Identifier(JsonHelper.getString(jsonObject, "rite"));
        return Rite.RITES.containsKey(riteId) ? new RiteCastCriterion.Conditions(extended, Rite.RITES.get(riteId)) : null;
    }

    public void trigger(ServerPlayerEntity player, Rite rite) {
        this.test(player, (conditions) -> conditions.matches(rite));
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final Rite rite;

        public Conditions(EntityPredicate.Extended player, Rite item) {
            super(RiteCastCriterion.ID, player);
            this.rite = item;
        }

        public boolean matches(Rite rite) {
            return this.rite.id.equals(rite.id);
        }

        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.addProperty("rite", this.rite.id.toString());
            return jsonObject;
        }
    }
}