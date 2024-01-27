package main;

import controller.Controller;
import exceptions.MyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PrgState;
import model.statements.IStmt;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;
import repository.IRepository;
import repository.ProgramsList;
import repository.Repository;
import utils.*;

import java.io.BufferedReader;
import java.util.Map;
import java.util.concurrent.Executors;

public class MainGUI extends Application {
    Button runButton;
    ListView<String> listView;
    ObservableList<String> programs;
    ProgramsList prgList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Programs to run");
        runButton = new Button("Run program"); //button1.setText("Click me"); or this

        listView = new ListView<>(); // create a ListView to display info later on

        this.initializeProgramList(); // initialize the list with all the strings associated to the programs we can run
        listView.setItems(programs); // display the programs in the list view

        // Set up event handler for the "Run program" button
        runButton.setOnAction(event -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                runSelectedProgram(selectedIndex);
            }
        });

        VBox layout = new VBox();
        layout.getChildren().addAll(listView, runButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 800, 500); // width x height
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeProgramList() {
        this.prgList = new ProgramsList();
        this.programs = FXCollections.observableArrayList();

        for (IStmt prg : prgList.getStmtList()) {
           programs.add(prg.toString());
        }
    }

    private void runSelectedProgram(int selectedIndex) {
        if (selectedIndex >= 0 && selectedIndex < prgList.getStmtList().size()) {
            IStmt selectedProgram = prgList.getStmtList().get(selectedIndex);

            MyStack<IStmt> exeStack = new MyStack<>();
            MyDictionary<String, Value> symTable = new MyDictionary<>();
            MyList<Value> output = new MyList<>();
            MyDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
            MyHeap<Value> heap = new MyHeap<>();
            MySemaphoreTable semaphoreTable = new MySemaphoreTable();
            PrgState prg1 = new PrgState(exeStack, symTable, output, selectedProgram, fileTable, heap, semaphoreTable);
            IRepository repo1 = new Repository(prg1, "log1.txt");
            Controller ctr = new Controller(repo1);

            ctr.executor = Executors.newFixedThreadPool(2);
            ProgramRunningWindow infoWindow = new ProgramRunningWindow(ctr);

        }
    }
}
