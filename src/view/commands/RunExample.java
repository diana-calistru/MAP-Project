package view.commands;

import controller.Controller;
import exceptions.FileNotFoundException;
import exceptions.MyException;

import javax.naming.ldap.Control;
import java.io.IOException;

public class RunExample extends Command {
    Controller ctr;
    public RunExample(String key, String desc, Controller c) {
        super(key, desc);
        this.ctr = c;
    }
    @Override
    public void execute() throws MyException, FileNotFoundException, IOException {
        ctr.allSteps();
    }
}
