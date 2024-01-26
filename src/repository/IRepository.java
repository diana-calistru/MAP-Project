package repository;

import exceptions.MyException;
import model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {

    //PrgState getCrtPrg();
    void add(PrgState program);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
    void logPrgStateExec(PrgState prg) throws MyException, IOException;
}
