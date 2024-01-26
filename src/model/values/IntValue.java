package model.values;

import model.types.IntType;
import model.types.Type;

public class IntValue implements Value {

    int val;
    public IntValue(int v) {
        this.val = v;
    }
    public int getVal() {
        return val;
    }
    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.format("%d", val);
    }

    public boolean equals(Object another) {
        if(another instanceof IntValue value) {
            return value.getVal() == this.getVal();
        }

        return false;
    }
}
