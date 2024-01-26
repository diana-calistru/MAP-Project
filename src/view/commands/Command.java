package view.commands;

import exceptions.FileNotFoundException;
import exceptions.MyException;

import java.io.IOException;

public abstract class Command {
    private String key, description;
    public Command(String k, String d) {
        this.key = k;
        this.description = d;
    }
    public abstract void execute() throws MyException, FileNotFoundException, IOException;
    public String getKey() { return key;}
    public String getDescription() { return description;}
}
