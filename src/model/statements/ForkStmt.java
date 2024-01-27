package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.types.Type;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;
import utils.MyIStack;
import utils.MyStack;

import java.io.IOException;

public class ForkStmt implements IStmt {
    IStmt argument;
    public ForkStmt(IStmt arg) {
        this.argument = arg;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        MyStack<IStmt> stack = new MyStack<>();
        PrgState forked = new PrgState(stack, state.getSymTable().deepcopy(), state.getOut(), argument, state.getFileTable(), state.getHeap(), state.getSemaphoreTable());
        return forked;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        argument.typecheck(typeEnv.deepcopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Fork the following: " + argument.toString();
    }
}
