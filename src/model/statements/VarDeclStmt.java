package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.types.*;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;
import utils.MyIDictionary;

public class VarDeclStmt implements IStmt {

    private String name;;
    private Type type;

    public VarDeclStmt(String symbolName, Type type) {
        this.name = symbolName;
        this.type = type;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if(symTable.isDefined(name))
            throw new MyException("variable is already declared");
        else {
            if(type instanceof IntType || type instanceof BoolType || type instanceof StringType || type instanceof RefType)
                symTable.update(name, type.defaultValue());
            else throw new MyException("the declared type is not allowed");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return name + "(" + type.toString() + ")";
    }
}
