package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import javafx.util.Pair;
import model.PrgState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyISemaphoreTable;

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
        MyISemaphoreTable semaphoreTable = state.getSemaphoreTable();
        if (symTable.isDefined(id)) {
            if (symTable.lookup(id).getType().equals(new IntType())) {
                IntValue index = (IntValue) symTable.lookup(id);
                if (semaphoreTable.getSemaphoreTable().containsKey(index.getVal())) {
                    Pair<Integer, List<Integer>> foundSemaphore = semaphoreTable.get(index.getVal());
                    if (foundSemaphore.getValue().contains(state.getId()))
                        foundSemaphore.getValue().remove((Integer) state.getId());
                    semaphoreTable.update(index.getVal(), new Pair<>(foundSemaphore.getKey(), foundSemaphore.getValue()));
                } else throw new MyException("Index not in the semaphore table!");
            } else throw new MyException("Index must be of type int!");
        } else throw new MyException("Index not in symbol table!");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(id).equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException(String.format("%s is not int!", id));
        }
    }

    @Override
    public String toString() {
        return String.format("release(%s)", id);
    }
}
