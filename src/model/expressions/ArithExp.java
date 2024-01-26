package model.expressions;

import exceptions.MyException;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;

public class ArithExp implements Exp {

    Exp exp1;
    Exp exp2;
    char operator; 

    public ArithExp(char op, Exp e1, Exp e2) {
        this.exp1 = e1;
        this.exp2 = e2;
        this.operator = op;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws MyException {
        Value v1, v2;
        v1 = exp1.eval(table, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = exp2.eval(table, heap);
            if(v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if(operator == '+') return new IntValue(n1+n2);
                if(operator == '-') return new IntValue(n1-n2);
                if(operator == '*') return new IntValue(n1*n2);
                if(operator == '/')
                    if(n2 == 0) throw new MyException("division by zero");
                    else return new IntValue(n1/n2);
            }else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
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
