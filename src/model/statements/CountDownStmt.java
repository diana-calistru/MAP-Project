package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.expressions.ValExp;
import model.types.IntType;
import model.types.RefType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyILatchTable;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStmt implements IStmt {
    private final String var;
    private static final Lock lock = new ReentrantLock();
    public CountDownStmt(String s) {
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
                if (latchTable.get(foundIndex.getVal()) > 0) {
                    latchTable.update(foundIndex.getVal(), latchTable.get(foundIndex.getVal()) - 1);
                }
                state.getExeStack().push(new PrintStmt(new ValExp(new IntValue(state.getId()))));
            } else throw new MyException("Index not found in the latch table!");
        } else throw new MyException("Variable not defines");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else throw new MyException(String.format("%s is not of int type!", var));
    }

    @Override
    public String toString() {
        return String.format("countDown(%s)", var);
    }
}
