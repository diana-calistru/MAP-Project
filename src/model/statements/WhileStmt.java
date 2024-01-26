package model.statements;

import exceptions.FileNotFoundException;
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

import java.io.IOException;

public class WhileStmt implements IStmt {
    Exp expression; // the condition in the while
    IStmt stmtToExecute;

    public WhileStmt(Exp e, IStmt s) {
        this.expression = e;
        this.stmtToExecute = s;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();
        Value expVal = expression.eval(symTable, heap);

        if (expVal.getType() instanceof BoolType) {
            boolean executor = ((BoolValue) expVal).getVal();
            if (executor) {
                exeStack.push(new WhileStmt(expression, stmtToExecute));
                exeStack.push(stmtToExecute);
            }
            return null;
        } else throw new MyException("Expression was not evaluated as boolean");
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmtToExecute.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }else
            throw new MyException("The condition of while does not have type bool");
    }

    @Override
    public String toString() {
        return String.format("while (%s), do (%s)", expression.toString(), stmtToExecute.toString());
    }
}
