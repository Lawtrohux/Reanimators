package com.unco.reanimators.setting.impl.numerical;

import com.unco.reanimators.setting.converter.AbstractBoxedNumberConverter;
import com.unco.reanimators.setting.converter.BoxedDoubleConverter;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class DoubleSetting extends NumberSetting<Double> {

    private static final BoxedDoubleConverter converter = new BoxedDoubleConverter();

    public DoubleSetting(Double value, Predicate<Double> restriction, BiConsumer<Double, Double> consumer, String name, Predicate<Double> visibilityPredicate, Double min, Double max) {
        super(value, restriction, consumer, name, visibilityPredicate, min, max);
    }

    @Override
    public AbstractBoxedNumberConverter converter() {
        return converter;
    }

}
