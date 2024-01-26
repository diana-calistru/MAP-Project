package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.values.Value;
import model.types.Type;
import utils.MyIDictionary;
import utils.MyIHeap;

public class AssignStmt implements IStmt {
    String id;
    Exp exp;

    public AssignStmt(String str, Exp e) {
        this.id = str;
        this.exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();
        if(symTable.isDefined(id)) {
            Value val = exp.eval(symTable, heap);
            Type type = symTable.lookup(id).getType();
            if(val.getType().equals(type))
                symTable.update(id, val);
            else throw new MyException("the type of the declared variable and the type of the expression do not match");
        } else throw new MyException(String.format("variable %s was not declared", id));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typeexp = exp.typecheck(typeEnv);
        if (typevar.equals(typeexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return id + "= " + exp;
    }
}
