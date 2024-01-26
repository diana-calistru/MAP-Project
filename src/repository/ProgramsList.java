package repository;

import exceptions.MyException;
import model.expressions.ArithExp;
import model.expressions.ReadHeapExp;
import model.expressions.ValExp;
import model.expressions.VarExp;
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

        IStmt s4_line1 = new VarDeclStmt("varf", new StringType());
        IStmt s4_line2 = new AssignStmt("varf", new ValExp(new StringValue("test.in")));
        IStmt s4_line3 = new OpenRFileStmt(new VarExp("varf"));
        IStmt s4_line4 = new VarDeclStmt("varc", new IntType());
        IStmt s4_line5 = new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), new PrintStmt(new VarExp("varc")));
        IStmt s4_line6 = new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), new PrintStmt(new VarExp("varc")));
        IStmt s4_line7 = new CloseRFileStmt(new VarExp("varf"));

        IStmt stmt4 = new CompStmt(s4_line1, new CompStmt(s4_line2, new CompStmt(s4_line3,
                new CompStmt(s4_line4, new CompStmt(s4_line5, new CompStmt(s4_line6, s4_line7))))));

        uncheckedStmtList.add(stmt4);

        IStmt s5_line1 = new VarDeclStmt("v", new RefType(new IntType()));
        IStmt s5_line2 = new NewStmt("v", new ValExp(new IntValue(20)));
        IStmt s5_line3 = new PrintStmt(new ReadHeapExp(new VarExp("v")));
        IStmt s5_line4 = new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        IStmt s5_line5 = new NewStmt("a", new VarExp("v"));
        IStmt s5_line6 = new VarDeclStmt("r", new RefType(new RefType(new RefType(new IntType()))));
        IStmt s5_line7 = new NewStmt("r", new VarExp("a"));
        IStmt s5_line8 = new WriteHeapStmt("v", new ValExp(new IntValue(40)));
        IStmt s5_line9 = new PrintStmt(new ReadHeapExp(new VarExp("v")));
        IStmt s5_line10 = new PrintStmt(new ReadHeapExp(new VarExp("a")));
        IStmt s5_line11 = new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))));

        IStmt stmt5 = new CompStmt(s5_line1, new CompStmt(s5_line2, new CompStmt(s5_line3, new CompStmt(s5_line4,
                new CompStmt(s5_line5, new CompStmt(s5_line6, new CompStmt(s5_line7, new CompStmt(s5_line8,
                        new CompStmt(s5_line9, new CompStmt(s5_line10, s5_line11))))))))));

        uncheckedStmtList.add(stmt5);

        IStmt line1 = new VarDeclStmt("v", new IntType());
        IStmt line2 = new VarDeclStmt("a", new RefType(new IntType()));
        IStmt line3 = new AssignStmt("v", new ValExp(new IntValue(10)));
        IStmt line4 = new NewStmt("a", new ValExp(new IntValue(22)));

        IStmt line5 = new WriteHeapStmt("a", new ValExp(new IntValue(30)));
        IStmt line6 = new AssignStmt("v", new ValExp(new IntValue(32)));
        IStmt line7 = new PrintStmt(new VarExp("v"));
        IStmt line8 = new PrintStmt(new ReadHeapExp(new VarExp("a")));

        IStmt line9 = new ForkStmt(new CompStmt(line5,
                new CompStmt(line6,
                        new CompStmt(line7, line8))));

        IStmt line10 = new PrintStmt(new VarExp("v"));
        IStmt line11 = new PrintStmt(new ReadHeapExp(new VarExp("a")));

        IStmt stmt7 = new CompStmt(line1,
                new CompStmt(line2,
                        new CompStmt(line3,
                                new CompStmt(line4,
                                        new CompStmt(line9,
                                                new CompStmt(line10, line11))))));

        uncheckedStmtList.add(stmt7);
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
