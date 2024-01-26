package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;
import utils.MyIStack;

public class IfStmt implements IStmt {

    Exp exp;
    IStmt thenStmt;
    IStmt elseStmt;

    public IfStmt(Exp e, IStmt th, IStmt el) {
        this.exp = e;
        this.thenStmt = th;
        this.elseStmt = el;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();

        Value result = exp.eval(symTable, heap);
        if(!(result instanceof BoolValue))
            throw new MyException(String.format("Given expression %s is not a conditional expression", exp.toString()));
        else {
            boolean valueEval = ((BoolValue) result).getVal();
            if(valueEval)
                exeStack.push(thenStmt);
            else
                exeStack.push(elseStmt);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenStmt.typecheck(typeEnv.deepcopy());
            elseStmt.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }else
            throw new MyException("The condition of IF does not have the type bool");
    }

    @Override
    public String toString() {
        return "If (" + exp.toString() + ")" +
                " then (" + thenStmt.toString() + ")" +
                " else (" + elseStmt.toString() + ")";
    }
}
