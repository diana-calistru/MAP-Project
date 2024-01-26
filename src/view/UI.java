package view;

import controller.Controller;
import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.expressions.ArithExp;
import model.expressions.Exp;
import model.expressions.ValExp;
import model.expressions.VarExp;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;
import repository.Repository;
import utils.MyDictionary;
import utils.MyList;
import utils.MyStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class UI {
    public void mainMenu() {
        System.out.println("------MAIN MENU------");
        System.out.println("1. Problem 1");
        System.out.println("2. Problem 2");
        System.out.println("3. Problem 3");
        System.out.println("4. Exit");
        System.out.print("> ");
    }

    /*
    private void programExecutor(IStmt ex) throws MyException, IOException, FileNotFoundException {
        MyStack<IStmt> exeStack = new MyStack<IStmt>();
        MyDictionary<String, Value> symTable = new MyDictionary<String, Value>();
        MyList<Value> output = new MyList<Value>();
        MyDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        PrgState exercise = new PrgState(exeStack, symTable, output, ex, fileTable);
        Repository repo = new Repository();
        repo.add(exercise);
        Controller app = new Controller(repo);

        app.allSteps();
        for (String logElem : exercise.showLog()) {
            System.out.println(logElem);
        }
    }

    void problem1() throws MyException, IOException, FileNotFoundException {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new BoolType()),
                new CompStmt(new AssignStmt("v", new ValExp( new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        programExecutor(ex1);
    }

    void problem2() throws MyException, IOException, FileNotFoundException {
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValExp( new IntValue(2)),
                                new ArithExp('*', new ValExp( new IntValue(3)),
                                        new ValExp( new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+',
                                        new VarExp("a"),
                                        new ValExp( new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));

        programExecutor(ex2);
    }

    void problem3() throws MyException, IOException, FileNotFoundException {
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValExp( new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValExp( new IntValue(2))),
                                        new AssignStmt("v", new ValExp( new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));

        programExecutor(ex3);
    }


    public void inputParser() throws MyException, IOException, FileNotFoundException {
        Scanner input = new Scanner(System.in);
        int option = input.nextInt();
        switch(option) {
            case 1:
                problem1();
                break;
            case 2:
                problem2();
                break;
            case 3:
                problem3();
                break;
            case 4:
                System.exit(0);
        }
    }
     */
}
