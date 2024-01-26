package repository;

import exceptions.MyException;
import model.PrgState;
import model.statements.IStmt;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> programs;
    private String logFilePath;

    public Repository() {
        programs = new ArrayList<PrgState>();
        logFilePath = "OutputFile.txt";
    }

    public Repository(PrgState p, String s) {
        programs = new ArrayList<PrgState>();
        programs.add(p);
        logFilePath = s;
    }

    //@Override
    //public PrgState getCrtPrg() {
    //    return programs.get(0);
    //}

    @Override
    public void add(PrgState program) {
        programs.add(program);
    }

    @Override
    public List<PrgState> getPrgList() {
        return programs;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        this.programs = prgList;
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException, IOException {
        BufferedWriter logFile = new BufferedWriter(new FileWriter(logFilePath, true));

        logFile.write("--------------------[START EXECUTION]------------------\n");

        logFile.write("PrgState ID: " + prgState.getId() + "\n");
        logFile.write("\t\t\t[EXECUTION STACK]\n");
        for (IStmt elem : prgState.getExeStack().reverse()) {
            logFile.write("\t" + elem.toString());
            logFile.write("\n");
        }
        logFile.write("\n");

        logFile.write("\t\t\t[SYMBOL TABLE]\n");
        for (String key : prgState.getSymTable().getContent().keySet()) {
            logFile.write(String.format("\t%s -> %s\n", key, prgState.getSymTable().lookup(key).toString()));
        }
        logFile.write("\n");

        logFile.write("\t\t\t[HEAP]\n");
        for (Integer address : prgState.getHeap().getContent().keySet()) {
            logFile.write(String.format("\t%d: %s\n", address, prgState.getHeap().lookup(address).toString()));
        }
        logFile.write("\n");

        logFile.write("\t\t\t[OUTPUT LOG]\n");
        for (Value val : prgState.getOut().getList()) {
            logFile.write("\t" + val.toString());
            logFile.write("\n");
        }
        logFile.write("\n");

        logFile.write("\t\t\t[FILE TABLE]\n");
        for (Value val : prgState.getFileTable().getKeySet()) {
            logFile.write("\t" + val.toString() + " -> " +
                    String.valueOf(prgState.getFileTable().lookup((StringValue) val)));
            logFile.write("\n");
        }
        logFile.write("\n");
        logFile.write("--------------------[END EXECUTION]--------------------\n");
        logFile.close();
    }

    @Override
    public String toString() {
        return "Repository{" + programs + '}';
    }
}
