package model.types;

import model.values.RefValue;
import model.values.Value;

public class RefType implements Type {
    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() { return inner;}

    public boolean equals(Object another) {
        if (another instanceof RefType type)
            return inner.equals(type.getInner());
        else
            return false;
    }
    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }
}
