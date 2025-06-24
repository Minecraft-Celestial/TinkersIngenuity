package com.xiaoyue.tinkers_ingenuity.content.shared.json.variable;

import slimeknights.mantle.data.loadable.primitive.BooleanLoadable;
import slimeknights.mantle.data.loadable.primitive.EnumLoadable;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;

public record LevelingFormula(float factor, Type operation, boolean single) {

    public static final EnumLoadable<Type> TYPE_LOADER = new EnumLoadable<>(Type.class);
    public static final RecordLoadable<LevelingFormula> LOADER = RecordLoadable.create(
            FloatLoadable.ANY.requiredField("factor", LevelingFormula::factor),
            TYPE_LOADER.requiredField("operation", LevelingFormula::operation),
            BooleanLoadable.DEFAULT.defaultField("single", false, LevelingFormula::single),
            LevelingFormula::new
    );

    public static LevelingFormula add(float factor) {
        return new LevelingFormula(factor, LevelingFormula.Type.addition, false);
    }

    public static LevelingFormula mulBase(float factor) {
        return new LevelingFormula(factor, LevelingFormula.Type.multiplier_base, false);
    }

    public static LevelingFormula mulTotal(float factor) {
        return new LevelingFormula(factor, LevelingFormula.Type.multiplier_total, false);
    }

    public float apply(float origin, ModifierEntry entry) {
        return this.apply(origin, entry.getLevel());
    }

    public float apply(float origin, int lv) {
        int finalLv = this.single ? 1 : lv;
        return switch (this.operation) {
            case addition -> origin + this.factor * (float) finalLv;
            case multiplier_base -> origin * (1.0F + this.factor * (float) finalLv);
            case multiplier_total -> origin * this.factor * (float) finalLv;
        };
    }

    public enum Type {
        addition,
        multiplier_base,
        multiplier_total;
    }
}
