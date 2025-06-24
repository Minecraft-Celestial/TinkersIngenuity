package com.xiaoyue.tinkers_ingenuity.content.shared.material;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import slimeknights.tconstruct.library.materials.definition.Material;

import javax.annotation.Nullable;

public record MaterialDefinitionData(int tier, int order, boolean craftable, boolean hidden, @Nullable ICondition condition) {

    public Material getMate(ResourceLocation id) {
        return new Material(id, tier, order, craftable, hidden);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int tier = 4;
        private int order = 2;
        private boolean craftable;
        private boolean hidden = false;
        private ICondition condition = null;

        public Builder tier(int tier) {
            this.tier = tier;
            return this;
        }

        public Builder order(int order) {
            this.order = order;
            return this;
        }

        public Builder craftable() {
            this.craftable = true;
            return this;
        }

        public Builder hidden() {
            this.hidden = true;
            return this;
        }

        public Builder orCondition(ICondition... conditions) {
            this.condition = new OrCondition(conditions);
            return this;
        }

        public MaterialDefinitionData build() {
            return new MaterialDefinitionData(this.tier, this.order, this.craftable, this.hidden, this.condition);
        }
    }
}
