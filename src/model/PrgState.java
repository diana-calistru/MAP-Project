package model;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.values.StringValue;
import model.values.Value;
import model.statements.IStmt;
import utils.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class PrgState {

    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    ArrayList<String> log;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    MyIHeap<Value> heap;
    MyILatchTable latchTable;
    private static int nextId = 1;
    private static synchronized int generateId() {
        return nextId++;
    }
    private int id;


    IStmt originalProgram; // optional field, but good to have

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, IStmt prg, MyIDictionary<StringValue, BufferedReader> ftbl, MyIHeap<Value> hp, MyILatchTable latch) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        // originalProgram = deepCopy(prg); // recreate the entire original program
        stk.push(prg);
        log = new ArrayList<>();
        fileTable = ftbl;
        heap = hp;
        latchTable = latch;

        this.id = generateId();
    }
    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }
    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }
    public MyIList<Value> getOut() {
        return out;
    }
    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }
    public MyIHeap<Value> getHeap() { return this.heap; }
    public MyILatchTable getLatchTable() { return this.latchTable; }
    public int getId() { return this.id;}

    public void setExeStack(MyIStack<IStmt> stk) {
        this.exeStack = stk;
    }
    public void setSymTable(MyIDictionary<String, Value> symtbl) {
        this.symTable = symtbl;
    }
    public void setOut(MyIList<Value> ot) {
        this.out = ot;
    }
    public void setLatchTable(MyILatchTable newLatchTable) { this.latchTable = newLatchTable;}

    public Boolean isNotComplete() { return !(exeStack.isEmpty()); }

    @Override
    public String toString() {
        return "PrgState: " + id;
    }

    public PrgState oneStep() throws MyException, FileNotFoundException, IOException {
        if (exeStack.isEmpty()) throw new MyException("PrgState stack is empty");
        {
            IStmt currentStmt = exeStack.pop();
            return currentStmt.execute(this);
        }
    }
}
