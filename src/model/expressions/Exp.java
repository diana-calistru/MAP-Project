package model.expressions;

import exceptions.MyException;
import model.types.Type;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;

public interface Exp {
    Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws MyException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
