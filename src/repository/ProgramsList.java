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

        IStmt stmt = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("cnt", new IntType()),
                        new CompStmt(
                                new NewStmt("v1", new ValExp(new IntValue(1))),
                                new CompStmt(
                                        new CreateSemaphoreStmt("cnt", new ReadHeapExp(new VarExp("v1"))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new AcquireStmt("cnt"),
                                                                new CompStmt(
                                                                        new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValExp(new IntValue(10)))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                new ReleaseStmt("cnt")
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new ForkStmt(
                                                                new CompStmt(
                                                                        new AcquireStmt("cnt"),
                                                                        new CompStmt(
                                                                                new WriteHeapStmt("v1", new ArithExp('*',  new ReadHeapExp(new VarExp("v1")), new ValExp(new IntValue(10)))),
                                                                                new CompStmt(
                                                                                        new WriteHeapStmt("v1", new ArithExp('*',  new ReadHeapExp(new VarExp("v1")), new ValExp(new IntValue(2)))),
                                                                                        new CompStmt(
                                                                                                new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                new ReleaseStmt("cnt")
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStmt(
                                                                new AcquireStmt("cnt"),
                                                                new CompStmt(
                                                                        new PrintStmt(new ArithExp('-', new ReadHeapExp(new VarExp("v1")), new ValExp(new IntValue(1)))),
                                                                        new ReleaseStmt("cnt")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
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
