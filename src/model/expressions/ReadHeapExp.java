package model.expressions;

import exceptions.MyException;
import model.types.IntType;
import model.types.RefType;
import model.types.Type;
import model.values.IntValue;
import model.values.RefValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;

public class ReadHeapExp implements Exp {
    private Exp e;

    public ReadHeapExp(Exp e) {
        this.e = e;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws MyException {
        Value val = e.eval(table, heap);
        if (val instanceof RefValue refValue) {
            int address = refValue.getAddress();
            if (heap.isDefined(address)) {
                Value heapValue = heap.lookup(address);
                return heapValue;
            } else
                throw new MyException("the address is not a key in the Heap table");
        }else
            throw new MyException("the expression could not be evaluated to a RefValue");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = e.typecheck(typeEnv);
        if(typ instanceof RefType reft) {
            return reft.getInner();
        }else
            throw new MyException("the rH argument is not a RefType");
    }

    @Override
    public String toString() {
        return "rH(" + e + ")";
    }
}
