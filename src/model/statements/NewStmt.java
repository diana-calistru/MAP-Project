package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;

import java.io.IOException;

public class NewStmt implements IStmt {
    private String id;
    private Exp e;
    public NewStmt(String vname, Exp e) {
        this.id = vname;
        this.e = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();
        if(symTable.isDefined(id) && symTable.lookup(id).getType() instanceof RefType ) {
            Type type = ((RefValue) symTable.lookup(id)).getLocationType();
            Value val = e.eval(symTable, heap);

            if (type.equals(val.getType())) {
                int address = state.getHeap().allocate(val);
                symTable.update(id, new RefValue(address, type));
            } else throw new MyException("the type of the declared variable and the type of the expression do not match");
        } else throw new MyException(String.format("variable %s was not declared", id));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = e.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return "new(" + id +
                ", " + e +
                ")";
    }
}

