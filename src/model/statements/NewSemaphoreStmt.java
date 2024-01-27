package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;
import utils.MyIToySemaphoreTable;
import utils.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphoreStmt implements IStmt {
    private final String id;
    private final Exp exp1;
    private final Exp exp2;
    private static final Lock lock = new ReentrantLock();
    public NewSemaphoreStmt(String s, Exp e1, Exp e2) {
        this.id = s;
        this.exp1 = e1;
        this.exp2 = e2;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();
        MyIToySemaphoreTable semaphoreTable = state.getToySemaphore();
        IntValue exp1Val = (IntValue) (exp1.eval(symTable, heap));
        IntValue exp2Val = (IntValue) (exp2.eval(symTable, heap));
        int freeAddress = semaphoreTable.getFreeAddress();
        semaphoreTable.put(freeAddress, new Tuple<>(exp1Val.getVal(), new ArrayList<>(), exp2Val.getVal()));
        if (symTable.isDefined(id) && symTable.lookup(id).getType().equals(new IntType()))
            symTable.update(id, new IntValue(freeAddress));
        else throw new MyException(String.format("%s in not defined in the symbol table!", id));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("newSemaphore(%s, %s, %s)", id, exp1, exp2);
    }
}
