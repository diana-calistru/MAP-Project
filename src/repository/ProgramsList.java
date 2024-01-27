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

        IStmt l1 = new VarDeclStmt("a", new RefType(new IntType()));
        IStmt l2 = new NewStmt("a", new ValExp(new IntValue(20)));

        IStmt l3 = new ForStmt("v", new ValExp(new IntValue(0)), new ValExp(new IntValue(3)),
                new ArithExp('+', new VarExp("v"), new ValExp(new IntValue(1))), new ForkStmt(
                        new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('*', new VarExp("v"), new ReadHeapExp(new VarExp("a")))))));

        IStmt l4 = new PrintStmt(new ReadHeapExp(new VarExp("a")));

        IStmt l5 = new VarDeclStmt("v", new IntType());

        IStmt for_example = new CompStmt(l5, new CompStmt(l1, new CompStmt(l2, new CompStmt(l3, l4))));

        uncheckedStmtList.add(for_example);
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
