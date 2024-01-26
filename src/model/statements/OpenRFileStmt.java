package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIStack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt {
    Exp exp;

    public OpenRFileStmt(Exp e) {
        this.exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value result = exp.eval(state.getSymTable(), state.getHeap());
        if (result instanceof StringValue expString) {
            String expValue = expString.getVal();
            if (fileTable.isDefined(expString))
                throw new MyException(String.format("File name %s already exists.", expValue));
            BufferedReader fileDescriptor = new BufferedReader(new FileReader(expValue));
            fileTable.put(expString, fileDescriptor);
        } else
            throw new MyException(String.format("Expected string type for file name. Received %s instead", result.getType().toString()));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("Open file descriptor in %s", exp.toString());
    }
}
