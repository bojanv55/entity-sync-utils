package me.vukas.common.entity.operation;

import me.vukas.common.base.MapStack;
import me.vukas.common.entity.IgnoredFields;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static me.vukas.common.base.Objects.createNewObjectOfType;
import static me.vukas.common.base.Objects.isStringOrPrimitiveOrWrapped;

public class Clone {
    private Diff diff;
    private Patch patch;

    public final Stack<Object> clonedElements = new Stack<Object>();
    private final Map<Object, Object> originalToRevisedElements = new HashMap<Object, Object>();

    @SuppressWarnings("unchecked")
    private Clone(Builder builder) {
        this.patch = new Patch.Builder().build();

        this.diff = new Diff(builder.typesToIgnoredFields, this);
    }

    protected Clone(Diff diff){
        this.patch = new Patch.Builder().build();
        this.diff = diff;
    }

    public <T> T clone(T original){
        if(this.originalToRevisedElements.containsKey(original)){
            return (T) this.originalToRevisedElements.get(original);
        }
        Class originalClass = original == null ? null : original.getClass();
        T cloned = (T) createNewObjectOfType(originalClass);
        this.clonedElements.push(original);

        if (originalClass!=null && !isStringOrPrimitiveOrWrapped(originalClass)) {
            this.originalToRevisedElements.put(original, cloned);
        }


        cloned = this.patch.patch(cloned, this.diff.diff(cloned, original));
        this.clonedElements.pop();
        return cloned;
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
