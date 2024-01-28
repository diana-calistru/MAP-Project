package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIToySemaphoreTable;
import utils.Tuple;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStmt implements IStmt {
    private final String id;
    private static final Lock lock = new ReentrantLock();
    public ReleaseStmt(String s) {
        this.id = s;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIToySemaphoreTable semaphoreTable = state.getToySemaphore();
        if (symTable.isDefined(id)) {
            if (symTable.lookup(id).getType().equals(new IntType())) {
                IntValue index = (IntValue) symTable.lookup(id);
                if (semaphoreTable.containsKey(index.getVal())) {
                    Tuple<Integer, List<Integer>, Integer> foundSemaphore = semaphoreTable.get(index.getVal());
                    if (foundSemaphore.getSecond().contains(state.getId()))
                        foundSemaphore.getSecond().remove((Integer) state.getId());
                    semaphoreTable.update(index.getVal(), new Tuple<>(foundSemaphore.getFirst(), foundSemaphore.getSecond(), foundSemaphore.getThird()));
                } else throw new MyException("Index not in the semaphore table!");
            } else throw new MyException("Index must be of type int!");
        } else throw new MyException("Index not in symbol table!");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("release(%s)", id);
    }
}
