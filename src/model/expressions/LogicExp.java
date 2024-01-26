package model.expressions;

import exceptions.MyException;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;

public class LogicExp implements Exp {
    Exp exp1;
    Exp exp2;
    String operator;

    public LogicExp(Exp e1, String op, Exp e2) {
        this.exp1 = e1;
        this.exp2 = e2;
        this.operator = op;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws MyException {
        Value v1, v2;
        v1 = exp1.eval(table, heap);
        if (v1.getType().equals(new BoolType())) {
            v2 = exp2.eval(table, heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                boolean n1, n2;
                n1 = b1.getVal();
                n2 = b2.getVal();
                if (operator.equals("and"))
                    return new BoolValue(n1 && n2);
                if (operator.equals("or"))
                    return new BoolValue(n1||n2);
            } else
                throw new MyException("second operand is not a boolean");
        } else
            throw new MyException("first operand is not a boolean");
        return null;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = exp1.typecheck(typeEnv);
        typ2 = exp2.typecheck(typeEnv);
        if(typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            }else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString() {
        return exp1.toString() + operator + exp2.toString();
    }
}
