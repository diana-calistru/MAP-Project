package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.expressions.RelationalExp;
import model.expressions.VarExp;
import model.types.IntType;
import model.types.Type;
import utils.MyIDictionary;
import utils.MyIStack;

import java.io.IOException;

public class ForStmt implements IStmt {
    String id;
    Exp exp1;
    Exp exp2;
    Exp exp3;
    IStmt stmt;

    public ForStmt(String s, Exp e1, Exp e2, Exp e3, IStmt st) {
        this.id = s;
        this.exp1 = e1;
        this.exp2 = e2;
        this.exp3 = e3;
        this.stmt = st;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        MyIStack<IStmt> stack = state.getExeStack();
        IStmt convertedStmt = new CompStmt(new AssignStmt(id, exp1),
                new WhileStmt(new RelationalExp("<", new VarExp(id), exp2), new CompStmt(stmt, new AssignStmt(id, exp3))));
        stack.push(convertedStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type exp1Type = exp1.typecheck(typeEnv);
        Type exp2Type = exp2.typecheck(typeEnv);
        Type exp3Type = exp3.typecheck(typeEnv);

        if (exp1Type.equals(new IntType()) && exp2Type.equals(new IntType()) && exp3Type.equals(new IntType()))
            return typeEnv;
        else throw new MyException("The for statement is invalid");

    }

    @Override
    public String toString() {
        return String.format("for(%s=%s; %s<%s; %s=%s) {%s}", id, exp1, id, exp2, id, exp3, stmt);
    }
}
