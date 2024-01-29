package main;

import controller.Controller;
import exceptions.MyException;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PrgState;
import model.statements.IStmt;
import model.values.IntValue;
import model.values.Value;
import javafx.scene.control.TableView;
import utils.MyIDictionary;
import utils.MyIHeap;
import utils.MyILatchTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ProgramRunningWindow {
    private final Controller controller;
    private final Stage stage;
    private final TextField numOfPrgStates;
    private final TableView<Map.Entry<Integer, Value>> heapTableView;
    private final ListView<Value> outListView;
    private ObservableList<Value> outList;
    private final ListView<Value> fileTableListView;
    private ObservableList<Value> fileTableList;
    private final ListView<PrgState> prgStateIdsListView;
    private ObservableList<PrgState> prgStateIdsList;
    private final TableView<Map.Entry<String, Value>> symTableView;
    private ObservableList<Map.Entry<String, Value>> symTableListItems;
    private final ListView<IStmt> exeStackListView;

    private ObservableList<IStmt> exeStackList;

    private final TableView<Map.Entry<Integer, Integer>> latchTableView;
    private ObservableList<Map.Entry<Integer, Integer>> latchTableListItems;
    private final Button oneStepButton;
    private int usedPrgStateId = 0;


    public ProgramRunningWindow (Controller ctr) {
        this.controller = ctr;
        this.stage = new Stage();

        this.numOfPrgStates = new TextField();
        this.numOfPrgStates.setEditable(false);

        this.heapTableView = new TableView<>();
        TableColumn<Map.Entry<Integer, Value>, Integer> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey()));

        TableColumn<Map.Entry<Integer, Value>, Value> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue()));
        heapTableView.getColumns().addAll(addressColumn, valueColumn);

        this.outListView = new ListView<>();
        this.fileTableListView = new ListView<>();
        this.prgStateIdsListView = new ListView<>();

        this.symTableView = new TableView<>();
        TableColumn<Map.Entry<String, Value>, String> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey()));

        TableColumn<Map.Entry<String, Value>, Value> valColumn = new TableColumn<>("Value");
        valColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue()));
        symTableView.getColumns().addAll(idColumn, valColumn);

        this.latchTableView = new TableView<>();
        TableColumn<Map.Entry<Integer, Integer>, Integer> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey()));

        TableColumn<Map.Entry<Integer, Integer>, Integer> latchValColumn = new TableColumn<>("Value");
        latchValColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue()));

        latchTableView.getColumns().addAll(locationColumn, latchValColumn);

        this.exeStackListView = new ListView<>();
        this.oneStepButton = new Button("Run One Step");

        initializeWindow();

    }
    private void initializeWindow() {
        stage.setTitle("Program Details");
        Label l1 = new Label("Program State Ids");
        Label l2 = new Label("Execution Stack");
        Label l3 = new Label("Heap Table");
        Label l4 = new Label("Output");
        Label l5 = new Label("Symbol Table");
        Label l6 = new Label("File Table");
        Label l7 = new Label("Latch Table");
        // Set up UI components
        VBox labeled1 = new VBox(l1, prgStateIdsListView);
        VBox labeled2 = new VBox(l2, exeStackListView);
        VBox labeled3 = new VBox(l3, heapTableView);
        VBox labeled4 = new VBox(l4, outListView);
        VBox labeled5 = new VBox(l5, symTableView);
        VBox labeled6 = new VBox(l6, fileTableListView);
        VBox labeled7 = new VBox(l7, latchTableView);

        HBox layout1 = new HBox();
        layout1.getChildren().addAll(
                labeled1,
                labeled2
        );
        layout1.setAlignment(Pos.CENTER);

        HBox layout2 = new HBox();
        layout2.getChildren().addAll(
                labeled3,
                labeled4
        );
        layout2.setAlignment(Pos.CENTER);

        HBox layout3 = new HBox();
        layout3.getChildren().addAll(
                labeled5,
                labeled6
        );
        layout3.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(
                numOfPrgStates,
                layout1,
                layout2,
                layout3,
                labeled7,
                oneStepButton
        );
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setScene(scene);

        oneStepButton.setOnAction(event -> {
            try {
                runOneStep();
                this.updateDisplay();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        prgStateIdsListView.setOnMouseClicked(event -> {
            PrgState selected = prgStateIdsListView.getSelectionModel().getSelectedItem();
            usedPrgStateId = selected.getId();
            this.updateDisplay();
        });

        updateDisplay();
        stage.show();
    }

    private void updateDisplay() {
        // Access the current program state
        PrgState currentPrgState = getCurrentPrgState();
        numOfPrgStates.setText(String.valueOf(controller.getRepo().getPrgList().size()));
        // Update the UI components as needed
        this.updateHeapTableView(currentPrgState.getHeap());
        this.updateOutListView();
        this.updateExeStackListView();
        this.updateFileTableView();
        this.updatePrgStateIdsListView();
        this.updateSymTableView(currentPrgState.getSymTable());
        this.updateLatchTableView(currentPrgState.getLatchTable());
    }


    private void runOneStep() throws MyException, InterruptedException {
        List<PrgState> prgStateList = controller.removeCompletedPrg(controller.getRepo().getPrgList());
        if (!prgStateList.isEmpty()) {
            prgStateList.get(0).getHeap().setContent(controller.garbageCollector(controller.allAddresses(prgStateList), prgStateList.get(0).getHeap().getContent()));

            controller.oneStepForAllPrg(prgStateList);

            controller.removeCompletedPrg(controller.getRepo().getPrgList());

            controller.getRepo().setPrgList(prgStateList);

            updateDisplay();
            updateDisplay();


        }else {
            controller.executor.shutdownNow();
            Stage stage = (Stage) oneStepButton.getScene().getWindow();
            stage.close();
        }
    }

    private PrgState getCurrentPrgState() {
        List<PrgState> prgStateList = controller.getRepo().getPrgList();
        if (!prgStateList.isEmpty()) {
            if (controller.getByID(usedPrgStateId) != null)
                return controller.getByID(usedPrgStateId);
            else {
                usedPrgStateId = prgStateList.get(0).getId();
                return prgStateList.get(0);
            }
        }
        return null;
    }

    private void updateHeapTableView(MyIHeap<Value> heap) {
        List<Map.Entry<Integer, Value>> heapEntries = new ArrayList<>(heap.getContent().entrySet());
        ObservableList<Map.Entry<Integer, Value>> observableHeapEntries = FXCollections.observableList(heapEntries);
        heapTableView.setItems(observableHeapEntries);
    }

    private void updateSymTableView(MyIDictionary<String, Value> symTable) {
        List<Map.Entry<String, Value>> symTableEntries = new ArrayList<>(symTable.getContent().entrySet());
        symTableListItems = FXCollections.observableList(symTableEntries);
        symTableView.setItems(symTableListItems);
        symTableView.refresh();
    }

    private void updateOutListView() {
        outList = FXCollections.observableArrayList(getCurrentPrgState().getOut().getList());
        outListView.setItems(outList);
    }

    private void updateFileTableView() {
        fileTableList = FXCollections.observableArrayList(getCurrentPrgState().getFileTable().getContent().keySet());
        fileTableListView.setItems(fileTableList);
    }

    private void updateExeStackListView() {
        exeStackList = FXCollections.observableArrayList(getCurrentPrgState().getExeStack().reverse());
        exeStackListView.setItems(exeStackList);
    }

    private void updatePrgStateIdsListView() {
        prgStateIdsList = FXCollections.observableArrayList(controller.getRepo().getPrgList());
        prgStateIdsListView.setItems(prgStateIdsList);
    }

    private void updateLatchTableView(MyILatchTable latchTable) {
        List<Map.Entry<Integer, Integer>> latchTableEntries = new ArrayList<>(latchTable.getLatchTable().entrySet());
        latchTableListItems = FXCollections.observableList(latchTableEntries);
        latchTableView.setItems(latchTableListItems);
        latchTableView.refresh();
    }
}
