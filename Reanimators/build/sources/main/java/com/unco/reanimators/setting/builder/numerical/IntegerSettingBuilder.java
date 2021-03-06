package com.unco.reanimators.setting.builder.numerical;

import com.unco.reanimators.setting.impl.numerical.IntegerSetting;
import com.unco.reanimators.setting.impl.numerical.NumberSetting;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class IntegerSettingBuilder extends NumericalSettingBuilder<Integer> {
    @Override
    public NumberSetting build() {
        return new IntegerSetting(initialValue, predicate(), consumer(), name, visibilityPredicate(), min, max);
    }

    @Override
    public IntegerSettingBuilder withMinimum(Integer minimum) {
        return (IntegerSettingBuilder) super.withMinimum(minimum);
    }

    @Override
    public NumericalSettingBuilder withName(String name) {
        return (IntegerSettingBuilder) super.withName(name);
    }

    @Override
    public IntegerSettingBuilder withListener(BiConsumer<Integer, Integer> consumer) {
        return (IntegerSettingBuilder) super.withListener(consumer);
    }

    @Override
    public IntegerSettingBuilder withMaximum(Integer maximum) {
        return (IntegerSettingBuilder) super.withMaximum(maximum);
    }

    @Override
    public IntegerSettingBuilder withRange(Integer minimum, Integer maximum) {
        return (IntegerSettingBuilder) super.withRange(minimum, maximum);
    }

    @Override
    public IntegerSettingBuilder withValue(Integer value) {
        return (IntegerSettingBuilder) super.withValue(value);
    }

    @Override
    public IntegerSettingBuilder withConsumer(BiConsumer<Integer, Integer> consumer) {
        return (IntegerSettingBuilder) super.withConsumer(consumer);
    }

    @Override
    public IntegerSettingBuilder withRestriction(Predicate<Integer> predicate) {
        return (IntegerSettingBuilder) super.withRestriction(predicate);
    }

    @Override
    public IntegerSettingBuilder withVisibility(Predicate<Integer> predicate) {
        return (IntegerSettingBuilder) super.withVisibility(predicate);
    }
}
