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
        /*
        IStmt stmt1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        uncheckedStmtList.add(stmt1);

        IStmt stmt2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValExp(new IntValue(2)),
                                new ArithExp('*', new ValExp(new IntValue(3)), new ValExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        uncheckedStmtList.add(stmt2);

        IStmt stmt3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValExp(new IntValue(2))),
                                        new AssignStmt("v", new ValExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

        uncheckedStmtList.add(stmt3);
         */

        IStmt l1 = new VarDeclStmt("v1", new RefType(new IntType()));
        IStmt l2 = new VarDeclStmt("v2", new RefType(new IntType()));
        IStmt l3 = new VarDeclStmt("v3", new RefType(new IntType()));
        IStmt l4 = new VarDeclStmt("cnt", new IntType());

        IStmt l5 = new NewStmt("v1", new ValExp(new IntValue(2)));
        IStmt l6 = new NewStmt("v2", new ValExp(new IntValue(3)));
        IStmt l7 = new NewStmt("v3", new ValExp(new IntValue(4)));

        IStmt l8 = new NewLatchStmt("cnt", new ReadHeapExp(new VarExp("v2")));

        IStmt l9 = new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValExp(new IntValue(10)))), new PrintStmt(new ReadHeapExp(new VarExp("v1"))));
        IStmt l10 = new CountDownStmt("cnt");

        IStmt l11 = new CompStmt(new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValExp(new IntValue(10)))), new PrintStmt(new ReadHeapExp(new VarExp("v2"))));

        IStmt l12 = new CompStmt(new WriteHeapStmt("v3", new ArithExp('*', new ReadHeapExp(new VarExp("v3")), new ValExp(new IntValue(10)))), new PrintStmt(new ReadHeapExp(new VarExp("v3"))));


        IStmt l13 = new ForkStmt(new CompStmt(l9, new CompStmt(l10,
                new ForkStmt(new CompStmt(l11, new CompStmt(l10, new CompStmt(
                        new ForkStmt(l12), l10)))))));

        IStmt l14 = new AwaitStmt("cnt");
        IStmt l15 = new PrintStmt(new ValExp(new IntValue(100)));

        IStmt stmt = new CompStmt(l1, new CompStmt(l2, new CompStmt(l3, new CompStmt(l4,
                new CompStmt(l5, new CompStmt(l6, new CompStmt(l7, new CompStmt(l8,
                        new CompStmt(l13, new CompStmt(l14, new CompStmt(l15, l10)))))))))));

        uncheckedStmtList.add(stmt);
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
