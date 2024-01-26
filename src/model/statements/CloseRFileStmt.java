package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;
import utils.MyIStack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    Exp exp;

    public CloseRFileStmt(Exp e) {
        this.exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> heap = state.getHeap();

        Value result = exp.eval(state.getSymTable(), heap);
        if (result instanceof StringValue expString) {
            BufferedReader fileDescriptor = fileTable.lookup(expString);
            fileDescriptor.close();
            fileTable.remove(expString);
            return null;
        } else
            throw new MyException(String.format("Expected string type for file name. Received %s instead", result.getType().toString()));
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("Close file descriptor in %s", exp.toString());
    }
}
