package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.types.BoolType;
import model.types.Type;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;
import utils.MyIStack;

import java.io.IOException;

public class ConditionalAssignStmt implements IStmt {
    String id;
    Exp exp1;
    Exp exp2;
    Exp exp3;

    public ConditionalAssignStmt(String str, Exp e1, Exp e2, Exp e3) {
        this.id = str;
        this.exp1 = e1;
        this.exp2 = e2;
        this.exp3 = e3;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        MyIStack<IStmt> stack = state.getExeStack();
        IStmt stmt = new IfStmt(exp1, new AssignStmt(id, exp2), new AssignStmt(id, exp3));
        stack.push(stmt);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type exp1Type = exp1.typecheck(typeEnv);
        if (exp1Type.equals(new BoolType())) {
            if (exp2.typecheck(typeEnv).equals(exp3.typecheck(typeEnv)))
                return typeEnv;
            else throw new MyException("the types of exp2 and exp3 do not match");
        } else throw new MyException("the first expression should be of Boolean type");
    }

    @Override
    public String toString() {
        return id + "=" + exp1 + "?" + exp2 + ":" + exp3;
    }
}
