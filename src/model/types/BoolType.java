package model.types;

import model.values.BoolValue;
import model.values.Value;

public class BoolType implements Type {

    public boolean equals(Object another) {
        if(another instanceof BoolType)
            return true;
        else
            return false;
    }


    @Override
    public String toString() {
        return "Bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
