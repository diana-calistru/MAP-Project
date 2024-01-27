package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.expressions.RelationalExp;
import model.types.Type;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;
import utils.MyIStack;

import java.io.IOException;

public class SwitchStmt implements IStmt {
    Exp exp;
    Exp exp1;
    Exp exp2;
    IStmt stmt1;
    IStmt stmt2;
    IStmt stmt3;

    public SwitchStmt(Exp e, Exp e1, IStmt s1, Exp e2, IStmt s2, IStmt s3) {
        this.exp = e;
        this.exp1 = e1;
        this.exp2 = e2;
        this.stmt1 = s1;
        this.stmt2 = s2;
        this.stmt3 = s3;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        MyIStack<IStmt> stack = state.getExeStack();
        IStmt stmt = new IfStmt(new RelationalExp("==", exp, exp1), stmt1, new IfStmt(new RelationalExp("==", exp, exp2), stmt2, stmt3));
        stack.push(stmt);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expType = exp.typecheck(typeEnv);
        Type exp1Type = exp1.typecheck(typeEnv);
        Type exp2Type = exp2.typecheck(typeEnv);

        if (expType.equals(exp1Type) && expType.equals(exp2Type)) {
            stmt1.typecheck(typeEnv.deepcopy());
            stmt2.typecheck(typeEnv.deepcopy());
            stmt3.typecheck(typeEnv.deepcopy());
            return typeEnv;
        } else throw  new MyException("The expression types do not match in the switch statement");

    }

    @Override
    public String toString() {
        return "switch(" + exp + ")(case " + exp1 + ": " + stmt1
                + ")(case " + exp2 + ": " + stmt2 + ")(default: " + stmt3 + ")";
    }
}
