package model.expressions;

import exceptions.MyException;
import model.types.Type;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;

public class ValExp implements Exp {
    Value e;

    public ValExp(Value v) {
        this.e = v;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws MyException {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public String toString() {
        return e.toString();
    }
}
