package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.expressions.ValExp;
import model.statements.IStmt;
import model.types.Type;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;
import utils.MyIList;

public class PrintStmt implements IStmt {
    Exp exp;

    public PrintStmt(Exp e) {
        this.exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> out = state.getOut();
        MyIHeap<Value> heap = state.getHeap();
        out.add(exp.eval(state.getSymTable(), heap));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("print(%s)", exp.toString());
    }
}
