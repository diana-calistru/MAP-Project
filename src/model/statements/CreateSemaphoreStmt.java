package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import javafx.util.Pair;
import model.PrgState;
import model.expressions.Exp;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;
import utils.MyISemaphoreTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStmt implements IStmt {
    private final String id;
    private final Exp exp;
    private static final Lock lock = new ReentrantLock();
    public CreateSemaphoreStmt(String s, Exp e) {
        this.id = s;
        this.exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        MyISemaphoreTable semaphoreTable = state.getSemaphoreTable();
        IntValue expVal = (IntValue) exp.eval(symTable, heap);
        int freeAddress = semaphoreTable.getFreeAddress();
        semaphoreTable.put(freeAddress, new Pair<>(expVal.getVal(), new ArrayList<>()));
        if (symTable.isDefined(id) && symTable.lookup(id).getType().equals(new IntType()))
            symTable.update(id, new IntValue(freeAddress));
        else throw new MyException(String.format("Error for variable %s: not defined/does not have int type!", id));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(id).equals(new IntType())) {
            if (exp.typecheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else throw new MyException("Expression is not of int type!");
        } else throw new MyException(String.format("%s is not of type int!", id));
    }

    @Override
    public String toString() {
        return String.format("createSemaphore(%s, %s)", id, exp);
    }
}
