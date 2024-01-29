package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyILatchTable;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt {
    private final String var;
    private static final Lock lock = new ReentrantLock();
    public AwaitStmt(String s) {
        this.var = s;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyILatchTable latchTable = state.getLatchTable();
        if (symTable.isDefined(var)) {
            IntValue foundIndex = (IntValue) symTable.lookup(var);
            if (latchTable.containsKey(foundIndex.getVal())) {
                if (latchTable.get(foundIndex.getVal()) != 0) {
                    state.getExeStack().push(this);
                }
            } else throw new MyException("Index not found in the latch table!");
        } else throw new MyException("Variable not defined!");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            return typeEnv;
        } else throw new MyException(String.format("%s is not of int type!", var));
    }

    @Override
    public String toString() {
        return String.format("await(%s)", var);
    }
}
