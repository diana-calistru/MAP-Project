package controller;

import exceptions.FileNotFoundException;
import exceptions.MyException;
import model.PrgState;
import model.statements.IStmt;
import model.values.RefValue;
import model.values.Value;
import repository.IRepository;
import utils.MyHeap;
import utils.MyIStack;


import javax.swing.*;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    IRepository repo;
    public ExecutorService executor;
    public Controller(IRepository r) {
        this.repo = r;
    }

    public IRepository getRepo() {
        return repo;
    }

    public PrgState getByID(int id) {
        for (PrgState prgState : repo.getPrgList()) {
            if (prgState.getId() == id)
                return prgState;
        }
        return null;
    }
    public void setRepo(IRepository r) {
        this.repo = r;
    }

    public Map<Integer, Value> garbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream().filter(p -> p.isNotComplete()).collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> { return p.oneStep();}))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).filter(p -> p != null).collect(Collectors.toList());

        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        repo.setPrgList(prgList);
    }
    public void allSteps() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while(!prgList.isEmpty()) {
            prgList.get(0).getHeap().setContent(garbageCollector(allAddresses(prgList), prgList.get(0).getHeap().getContent()));
            try {
                oneStepForAllPrg(prgList);
            }
            catch (Exception e) {
                throw new MyException(e.getMessage());
            }
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public List<Integer> allAddresses(List<PrgState> prgStates) {
        HashSet<Integer> allAddr = new HashSet<>();
        for(PrgState prg : prgStates){
            ConcurrentLinkedDeque<Integer> symTableAddr = prg.getSymTable().getContent().values()
                    .stream()
                    .filter(v -> v instanceof RefValue)
                    .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                    .collect(Collectors.toCollection(ConcurrentLinkedDeque :: new));

            symTableAddr.stream()
                    .forEach(a -> {
                        Value v = prg.getHeap().getContent().get(a);
                        if (v instanceof RefValue)
                            if (!symTableAddr.contains(((RefValue)v).getAddress()))
                                symTableAddr.add(((RefValue)v).getAddress());
                    });
            symTableAddr.forEach((Int) -> allAddr.add(Int));
        }
        return allAddr.stream().toList();
    }
}
