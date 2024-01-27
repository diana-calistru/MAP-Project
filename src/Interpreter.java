import controller.Controller;
import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;
import repository.IRepository;
import repository.Repository;
import utils.*;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.io.BufferedReader;
import java.io.IOException;

class Interpreter {
    public static void main(String[] args) throws MyException, FileNotFoundException, IOException {
        /*
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

        IStmt ex1 = new CompStmt(line1,
                new CompStmt(line2,
                        new CompStmt(line3,
                                new CompStmt(line4,
                                        new CompStmt(line9,
                                                new CompStmt(line10, line11))))));

        MyIDictionary<String, Type> typeEnvironment = new MyDictionary<>();
        try {
            ex1.typecheck(typeEnvironment);
        }catch (MyException e) {
             System.out.println(e.getMessage());
             return;
        }

        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, Value> symTable = new MyDictionary<>();
        MyList<Value> output = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyHeap<Value> heap = new MyHeap<>();
        PrgState prg1 = new PrgState(exeStack, symTable, output, ex1, fileTable, heap);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        IStmt line1 = new VarDeclStmt("v", new RefType(new IntType()));
        IStmt line2 = new NewStmt("v", new ValExp(new IntValue(20)));
        IStmt line3 = new PrintStmt(new ReadHeapExp(new VarExp("v")));

        IStmt line4 = new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        IStmt line5 = new NewStmt("a", new VarExp("v"));

        IStmt line6 = new VarDeclStmt("r", new RefType(new RefType(new RefType(new IntType()))));
        IStmt line7 = new NewStmt("r", new VarExp("a"));

        IStmt line8 = new WriteHeapStmt("v", new ValExp(new IntValue(40)));
        IStmt line9 = new PrintStmt(new ReadHeapExp(new VarExp("v")));
        IStmt line10 = new PrintStmt(new ReadHeapExp(new VarExp("a")));
        IStmt line11 = new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))));

        IStmt ex1 = new CompStmt(line1,
                new CompStmt(line2,
                        new CompStmt(line3,
                                new CompStmt(line4,
                                        new CompStmt(line5,
                                                new CompStmt(line6,
                                                        new CompStmt(line7,
                                                                new CompStmt(line8,
                                                                        new CompStmt(line9,
                                                                                new CompStmt(line10, line11))))))))));

        IStmt line12 = new VarDeclStmt("n", new IntType());
        IStmt line13 = new AssignStmt("n", new ValExp(new IntValue(4)));
        IStmt whilebracket = new CompStmt(
                new PrintStmt(new VarExp("n")),
                new AssignStmt("n", new ArithExp('-', new VarExp("n"), new ValExp(new IntValue(1)))));
        IStmt line14 = new WhileStmt(new RelationalExp(">", new VarExp("n"), new ValExp(new IntValue(0))), whilebracket);
        IStmt line15 = new PrintStmt(new VarExp("n"));


        IStmt ex2 = new CompStmt(line12,
                new CompStmt(line13,
                        new CompStmt(line14, line15)));

        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, Value> symTable = new MyDictionary<>();
        MyList<Value> output = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyHeap<Value> heap = new MyHeap<>();
        PrgState prg1 = new PrgState(exeStack, symTable, output, ex1, fileTable, heap);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        MyStack<IStmt> exeStack2 = new MyStack<>();
        MyDictionary<String, Value> symTable2 = new MyDictionary<>();
        MyList<Value> output2 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable2 = new MyDictionary<>();
        MyHeap<Value> heap2 = new MyHeap<>();
        PrgState prg2 = new PrgState(exeStack2, symTable2, output2, ex2, fileTable2, heap2);
        IRepository repo2 = new Repository(prg2, "log1.txt");
        Controller ctr2 = new Controller(repo2);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", "example 1", ctr1));
        menu.show();

         */
    }


}
