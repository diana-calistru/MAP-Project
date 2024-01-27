package model.expressions;

import exceptions.MyException;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;
import repository.Repository;
import utils.MyIDictionary;
import utils.MyIHeap;

public class RelationalExp implements Exp {
    String rel;
    Exp left, right;

    public RelationalExp(String relation, Exp l, Exp r) {
        this.rel = relation;
        this.left = l;
        this.right = r;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", left.toString(), rel.toString(), right.toString());
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws MyException {
        Value leftVal = left.eval(table, heap);
        Value rightVal = right.eval(table, heap);
        if (leftVal instanceof IntValue && rightVal instanceof IntValue) {
            int leftConvert = ((IntValue) leftVal).getVal();
            int rightConvert = ((IntValue) rightVal).getVal();
            return switch (rel) {
                case "<":
                    yield new BoolValue(leftConvert < rightConvert);
                case "<=":
                    yield new BoolValue(leftConvert <= rightConvert);
                case "==":
                    yield new BoolValue(leftConvert == rightConvert);
                case ">=":
                    yield new BoolValue(leftConvert >= rightConvert);
                case ">":
                    yield new BoolValue(leftConvert > rightConvert);
                case "!=":
                    yield new BoolValue(leftConvert != rightConvert);
                default:
                    throw new MyException(String.format("Operator %s not expected", rel));
            };
        }
        else
            throw new MyException(String.format("Unexpected value types %s, %s", leftVal.getType().toString(), rightVal.getType().toString()));
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = left.typecheck(typeEnv);
        typ2 = right.typecheck(typeEnv);
        if(typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            }else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }
}
