package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.types.Type;
import utils.MyIDictionary;
import utils.MyIStack;

public class CompStmt implements IStmt {

    IStmt first;
    IStmt second;

    public CompStmt(IStmt f, IStmt s) {
        this.first = f;
        this.second = s;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }
}
