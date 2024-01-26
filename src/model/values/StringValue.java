package model.values;

import model.types.StringType;
import model.types.Type;

import java.util.Objects;

public class StringValue implements Value {

    String val;
    public StringValue(String v) {
        this.val = v;
    }
    public String getVal() {
        return val;
    }
    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return val;
    }

    public boolean equals(Object another) {
        if(another instanceof StringValue value) {
            if(Objects.equals(value.getVal(), this.getVal()))
                return true;
        }

        return false;
    }
}
