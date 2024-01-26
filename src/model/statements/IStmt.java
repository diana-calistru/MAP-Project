package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import utils.MyIDictionary;

import java.io.IOException;
import model.types.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException;
    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
