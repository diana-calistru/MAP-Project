package repository;

import exceptions.MyException;
import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;
import utils.MyDictionary;
import utils.MyIDictionary;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

public class ProgramsList {
    private List<IStmt> uncheckedStmtList;
    private List<IStmt> stmtList;

    public ProgramsList() {
        this.stmtList = new ArrayList<>();
        this.uncheckedStmtList = new ArrayList<>();
        this.createStmts();
        this.addStmts();
    }

    private void createStmts() {
        IStmt cond_l1 = new VarDeclStmt("a", new RefType(new IntType()));
        IStmt cond_l2 = new VarDeclStmt("b", new RefType(new IntType()));
        IStmt cond_l3 = new VarDeclStmt("v", new IntType());

        IStmt cond_l4 = new NewStmt("a", new ValExp(new IntValue(0)));
        IStmt cond_l5 = new NewStmt("b", new ValExp(new IntValue(0)));

        IStmt cond_l6 = new WriteHeapStmt("a", new ValExp(new IntValue(1)));
        IStmt cond_l7 = new WriteHeapStmt("b", new ValExp(new IntValue(2)));

        IStmt cond_l8 = new ConditionalAssignStmt("v", new RelationalExp("<", new ReadHeapExp(new VarExp("a")), new ReadHeapExp(new VarExp("b"))), new ValExp(new IntValue(100)), new ValExp(new IntValue(200)));

        IStmt cond_l9 = new PrintStmt(new VarExp("v"));

        IStmt cond_l10 = new ConditionalAssignStmt("v", new RelationalExp(">", new ArithExp('-', new ReadHeapExp(new VarExp("b")), new ValExp(new IntValue(2))), new ReadHeapExp(new VarExp("a"))), new ValExp(new IntValue(100)), new ValExp(new IntValue(200)));

        IStmt cond_l11 = new PrintStmt(new VarExp("v"));

        IStmt cond_assign_example = new CompStmt(cond_l1, new CompStmt(cond_l2, new CompStmt(cond_l3, new CompStmt(cond_l4,
                new CompStmt(cond_l5, new CompStmt(cond_l6, new CompStmt(cond_l7, new CompStmt(cond_l8, new CompStmt(cond_l9, new CompStmt(cond_l10, cond_l11))))))))));

        uncheckedStmtList.add(cond_assign_example);

        IStmt l1 = new VarDeclStmt("v1", new RefType(new IntType()));
        IStmt l2 = new VarDeclStmt("v2", new RefType(new IntType()));
        IStmt l3 = new VarDeclStmt("v3", new RefType(new IntType()));
        IStmt l4 = new VarDeclStmt("cnt", new IntType());

        IStmt l5 = new NewStmt("v1", new ValExp(new IntValue(2)));
        IStmt l6 = new NewStmt("v2", new ValExp(new IntValue(3)));
        IStmt l7 = new NewStmt("v3", new ValExp(new IntValue(4)));

        IStmt l8 = new NewLatchStmt("cnt", new ReadHeapExp(new VarExp("v2")));

        IStmt l9 = new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValExp(new IntValue(10)))), new PrintStmt(new ReadHeapExp(new VarExp("v1"))));

        IStmt l10 = new CompStmt(new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValExp(new IntValue(10)))), new PrintStmt(new ReadHeapExp(new VarExp("v2"))));

        IStmt l11 = new CompStmt(new WriteHeapStmt("v3", new ArithExp('*', new ReadHeapExp(new VarExp("v3")), new ValExp(new IntValue(10)))), new PrintStmt(new ReadHeapExp(new VarExp("v3"))));

        IStmt l12 = new CountDownStmt("cnt");

        IStmt l13 = new ForkStmt(new CompStmt(l9, new CompStmt(l12, new ForkStmt(new CompStmt(l10, new CompStmt(l12, new ForkStmt(new CompStmt(l11, l12))))))));
        IStmt l14 = new CompStmt(new ForkStmt(l10), l12);
        IStmt l15 = new CompStmt(new ForkStmt(l11), l12);

        IStmt l16 = new AwaitStmt("cnt");
        IStmt l17 = new PrintStmt(new ValExp(new IntValue(100)));

        IStmt count_down_example = new CompStmt(l1, new CompStmt(l2, new CompStmt(l3, new CompStmt(l4,
                new CompStmt(l5, new CompStmt(l6, new CompStmt(l7, new CompStmt(l8, new CompStmt(l13,
                        new CompStmt(l16, new CompStmt(l17, new CompStmt(l12, l17))))))))))));
        uncheckedStmtList.add(count_down_example);
    }
    private void addStmts() {

        for (IStmt stmt : uncheckedStmtList) {
            MyIDictionary<String, Type> typeEnvironment = new MyDictionary<>();
            try {
                stmt.typecheck(typeEnvironment);
                this.stmtList.add(stmt);
            }catch (MyException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

    }
    public List<IStmt> getStmtList() {
        return this.stmtList;
    }
}
