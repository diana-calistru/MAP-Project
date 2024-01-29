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
import utils.MyILatchTable;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt {
    private final String var;
    private final Exp exp;
    private static final Lock lock = new ReentrantLock();
    public NewLatchStmt(String s, Exp e) {
        this.var = s;
        this.exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();
        MyILatchTable latchTable = state.getLatchTable();

        IntValue num1 = (IntValue) exp.eval(symTable, heap);
        int freeAddress = latchTable.getFreeAddress();
        latchTable.put(freeAddress, num1.getVal());

        if (symTable.isDefined(var)) {
            symTable.update(var, new IntValue(freeAddress));
        } else throw new MyException(String.format("%s is not defined in the symbol table!", var));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            if (exp.typecheck(typeEnv).equals(new IntType())) {
                return typeEnv;
            } else throw new MyException("Expression doesn't have the int type!");
        } else throw new MyException(String.format("%s is not of int type!", var));
    }

    @Override
    public String toString() {
        return String.format("newLatch(%s, %s)", var, exp);
    }
}
