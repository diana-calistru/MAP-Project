package model.values;

import model.types.BoolType;
import model.types.Type;

public class BoolValue implements Value {

    boolean val;

    public BoolValue(boolean v) {
        this.val = v;
    }
    public boolean getVal() {
        return val;
    }
    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        if (val)
            return "true";
        else
            return "false";
    }
    public boolean equals(Object another) {
        if(another instanceof BoolValue) {
            BoolValue value = (BoolValue) another;
            if(value.getVal() == this.getVal())
                return true;
        }

        return false;
    }
}
