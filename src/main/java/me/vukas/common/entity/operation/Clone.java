package me.vukas.common.entity.operation;

import me.vukas.common.base.Objects;
import me.vukas.common.entity.IgnoredFields;

import java.util.HashMap;
import java.util.Map;

public class Clone {
    private Diff diff;
    private Patch patch;

    @SuppressWarnings("unchecked")
    private Clone(Builder builder) {
        Diff.Builder diffBuilder = new Diff.Builder();
        for(IgnoredFields ignoredFields : builder.typesToIgnoredFields.values()){
            diffBuilder.ignoreFields(ignoredFields);
        }
        this.diff = diffBuilder.build();
        this.patch = new Patch.Builder().build();
    }

    public <T> T clone(T original){
        Class originalClass = original == null ? null : original.getClass();
        return (T) this.patch.patch(Objects.createNewObjectOfType(originalClass), this.diff.diff(Objects.createNewObjectOfType(originalClass), original));
    }

    public static class Builder {
        private final Map<Class, IgnoredFields> typesToIgnoredFields = new HashMap<Class, IgnoredFields>();

        public Builder ignoreFields(IgnoredFields ignoredFields) {
            this.typesToIgnoredFields.putIfAbsent(ignoredFields.getType(), ignoredFields);
            return this;
        }

        public Clone build() {
            return new Clone(this);
        }
    }
}
