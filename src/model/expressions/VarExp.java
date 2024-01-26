package model.expressions;

import exceptions.MyException;
import model.types.Type;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;

public class VarExp implements Exp {
    String id;

    public VarExp(String str) {
        this.id = str;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws MyException {
        if(table.isDefined(id))
            return table.lookup(id);
        else throw new MyException(String.format("variable %s is not defined", id));
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
