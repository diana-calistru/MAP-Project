package model.statements;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;
import utils.MyIDictionary;
import utils.MyIHeap;

import java.io.IOException;

public class WriteHeapStmt implements IStmt {
    private String variableName;
    private Exp expression;

    public WriteHeapStmt(String vname, Exp e) {
        this.variableName = vname;
        this.expression = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();

        if(symTable.isDefined(variableName) && symTable.lookup(variableName).getType() instanceof RefType && symTable.lookup(variableName) instanceof RefValue refValue && heap.isDefined(refValue.getAddress())) {
            Value expVal = expression.eval(symTable, heap);
            if (expVal.getType().equals(refValue.getLocationType())) {
                heap.update(refValue.getAddress(), expVal);
            } else throw new MyException("the type of the expression does not match the type of the variable");
        } else throw new MyException(String.format("the address is not a key in the Heap table or the variable %s is not properly defined", variableName));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(variableName);
        Type typexp = expression.typecheck(typeEnv);

        if (typevar instanceof RefType refvar) {
            if (refvar.getInner().equals(typexp)) {
                return typeEnv;
            } else
                throw new MyException("Right hand side and left hand side do not have matching types");
        } else
            throw new MyException("Function calles on non-heap type");
    }

    @Override
    public String toString() {
        return "wH(" + variableName + ", " + expression + ")";
    }
}
