package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.statements.IStmt;
import model.types.Type;
import utils.MyIDictionary;

public class NOPStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
