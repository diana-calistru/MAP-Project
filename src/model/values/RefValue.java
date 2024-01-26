package model.values;

import model.types.RefType;
import model.types.Type;

public class RefValue implements Value {
    int address;
    Type locationType;
    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }
    public int getAddress() { return address;}

    public Type getLocationType() {return locationType;}
    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }

    public boolean equals (Object another) {
        if (another instanceof RefValue refValue)
            return (address == refValue.getAddress()) && locationType.equals(refValue.locationType);
        return false;
    }
}
