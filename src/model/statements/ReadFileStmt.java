package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;
import utils.MyIList;
import utils.MyIStack;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    Exp fileName;
    String nameIntValue;

    public ReadFileStmt(Exp e, String n) {
        this.fileName = e;
        this.nameIntValue = n;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> heap = state.getHeap();

        if (symTable.isDefined(nameIntValue)) {
            Value result = fileName.eval(symTable, heap);
            if (result instanceof StringValue expString) {
                BufferedReader fileDescriptor = fileTable.lookup(expString);
                String line = fileDescriptor.readLine();
                int expectedValue = (line != null ? Integer.parseInt(line) : 0);
                IntValue convertedValue = new IntValue(expectedValue);
                symTable.update(nameIntValue, convertedValue);
                return null;
            } else
                throw new MyException(String.format("Expected string type for file name. Got %s instead", result.getType().toString()));
        } else
            throw new MyException(String.format("Name %s not found in table", nameIntValue));
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        fileName.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("Read from file descriptor %s into %s", fileName.toString(), nameIntValue);
    }
}
