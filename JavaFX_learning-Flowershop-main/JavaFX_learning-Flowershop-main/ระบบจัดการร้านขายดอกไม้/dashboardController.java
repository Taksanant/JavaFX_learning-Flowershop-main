/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxapplication4;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;  
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.StageStyle;

/**
 *
 * @author Acer
 */
public class dashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button availableFlowers_addBtn;

    @FXML
    private Button availableFlowers_btn;

    @FXML
    private Button availableFlowers_clearBtn;

    @FXML
    private TableColumn<flowersData, String> availableFlowers_col_flowerID;

    @FXML
    private TableColumn<flowersData, String> availableFlowers_col_flowerName;

    @FXML
    private TableColumn<flowersData, String> availableFlowers_col_price;

    @FXML
    private Button availableFlowers_deleteBtn;

    @FXML
    private TextField availableFlowers_flowerID;

    @FXML
    private TextField availableFlowers_flowername;

    @FXML
    private AnchorPane availableFlowers_form;

    @FXML
    private TableColumn<flowersData, String> availableFlowers_imageVavailableFlowers_col_status;

    @FXML
    private ImageView availableFlowers_imageView;

    @FXML
    private Button availableFlowers_importBtn;

    @FXML
    private TextField availableFlowers_price;

    @FXML
    private TextField availableFlowers_search;

    @FXML
    private ComboBox<?> availableFlowers_status;

    @FXML
    private TableView<flowersData> availableFlowers_tableView;

    @FXML
    private Button availableFlowers_updateBtn;

    @FXML
    private Button close;

    @FXML
    private Label home_availableFlowers;

    @FXML
    private Button home_btn;

    @FXML
    private BarChart<?, ?> home_chart;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalCustomers;

    @FXML
    private Label home_totalIncome;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button minimize;

    @FXML
    private Button purchase_btn;

    @FXML
    private Button purchase_addCart;

    @FXML
    private TableColumn<customerData, String> purchase_col_flowerID;

    @FXML
    private TableColumn<customerData, String> purchase_col_flowerName;

    @FXML
    private TableColumn<customerData, String> purchase_col_price;

    @FXML
    private TableColumn<customerData, String> purchase_col_quantity;

    @FXML
    private ComboBox<?> purchase_flowerID;

    @FXML
    private ComboBox<?> purchase_flowerName;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private Button purchase_payBtn;

    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private TableView<customerData> purchase_tableView;

    @FXML
    private Label purchase_total;

    @FXML
    private Label username;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;

    public void homeAF(){
        String sql = "SELECT COUNT(id) FROM flowers WHERE status = 'Available'";
        
        connect = database.connectDb();
        
        try{
            int countAF = 0;
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                countAF = result.getInt("COUNT(id)");
            }
            home_availableFlowers.setText(String.valueOf(countAF));
            
            
        }catch(Exception e){
            e.printStackTrace();
            
        }
    
    }
    
    public void homeTI(){
         String sql = "SELECT SUM(total) FROM customer_info";
         
         connect = database.connectDb();
         
         try{
             double countTI =0;
             statement = connect.createStatement();
             result = statement.executeQuery(sql);
             if(result.next()){
                 countTI = result.getInt("SUM(total)");
             }
             
             home_totalIncome.setText("$" + String.valueOf(countTI));
         }catch(Exception e){
             e.printStackTrace();
         }
    }
    
    
    public void homeTC(){
        String sql = "SELECT COUNT(id) FROM customer_info";
        
        connect = database.connectDb();
        
        try{
            int countTC = 0;
            
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                countTC = result.getInt("COUNT(id)");
            }
            home_totalCustomers.setText(String.valueOf(countTC));
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void homeChart(){
        
        home_chart.getData().clear();
        
        String sql = "SELECT date, SUM(total) FROM customer_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 7";
        
        connect = database.connectDb();
        
        try{
            XYChart.Series chart = new XYChart.Series();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            
            home_chart.getData().add(chart);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void availableFlowersAdd() {

        String sql = "INSERT INTO flowers (flower_id, name, status, price, image, date)"
                + "VALUES(?,?,?,?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;

            if (availableFlowers_flowerID.getText().isEmpty()
                    || availableFlowers_flowername.getText().isEmpty()
                    || availableFlowers_status.getSelectionModel().getSelectedItem() == null
                    || availableFlowers_price.getText().isEmpty()
                    || getData.path == null || getData.path == "") {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Plese fill all the blank fields");
                alert.showAndWait();

            } else {

                //check if the flower id is already exist
                String checkData = "SELECT flower_id FROM flowers WHERE flower_id = '"
                        + availableFlowers_flowerID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Flower ID: " + availableFlowers_flowerID.getText() + " was already exist!!");
                    alert.showAndWait();
                } else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, availableFlowers_flowerID.getText());
                    prepare.setString(2, availableFlowers_flowername.getText());
                    prepare.setString(3, (String) availableFlowers_status.getSelectionModel().getSelectedItem());
                    prepare.setString(4, availableFlowers_price.getText());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(5, uri);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(6, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Added!");
                    alert.showAndWait();

                    //Show updated tableview
                    availableFlowersShowListData();

                    //clear all fields
                    availableFlowersClear();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableFlowersUpdate() {

        String uri = getData.path;
        if (!(uri == null || uri == "")) {
            uri = uri.replace("\\", "\\\\");
        }

        String sql = "UPDATE flowers SET name = '"
                + availableFlowers_flowername.getText() + "',status = '"
                + availableFlowers_status.getSelectionModel().getSelectedItem() + "',price = '"
                + availableFlowers_price.getText() + "',image ='" + uri + "' WHERE flower_id = '" + availableFlowers_flowerID.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (availableFlowers_flowerID.getText().isEmpty()
                    || availableFlowers_flowername.getText().isEmpty()
                    || availableFlowers_status.getSelectionModel().getSelectedItem() == null
                    || availableFlowers_price.getText().isEmpty()
                    || uri == null || uri == "") {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Plese fill all the blank fields");
                alert.showAndWait();

            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Flower ID: " + availableFlowers_flowerID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Suceesfully Updated!!");
                    alert.showAndWait();

                    //Show updated tableview
                    availableFlowersShowListData();

                    //clear all fields
                    availableFlowersClear();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableFlowersDelete() {
        String sql = "DELETE FROM flowers WHERE flower_id = '"
                + availableFlowers_flowerID.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (availableFlowers_flowerID.getText().isEmpty()
                    || availableFlowers_flowername.getText().isEmpty()
                    || availableFlowers_status.getSelectionModel().getSelectedItem() == null
                    || availableFlowers_price.getText().isEmpty()
                    || getData.path == null || getData.path == "") {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Plese fill all the blank fields");
                alert.showAndWait();

            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Flower ID: " + availableFlowers_flowerID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Suceesfully Deleted!!");
                    alert.showAndWait();

                    //Show updated tableview
                    availableFlowersShowListData();

                    //clear all fields
                    availableFlowersClear();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String listStatus[] = {"Available", "Not Available"};

    public void availableFlowerStatus() {

        List<String> listS = new ArrayList<>();

        for (String data : listStatus) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        availableFlowers_status.setItems(listData);

    }

    public void availableFlowersClear() {

        availableFlowers_flowerID.setText("");
        availableFlowers_flowername.setText("");
        availableFlowers_status.getSelectionModel().clearSelection();
        availableFlowers_price.setText("");
        getData.path = "";

        availableFlowers_imageView.setImage(null);
    }

    public void availableFlowersInsertImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            getData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 116, 139, false, true);
            availableFlowers_imageView.setImage(image);

        }
    }

    public ObservableList<flowersData> availableFlowersListData() {

        ObservableList<flowersData> listData = FXCollections.observableArrayList();
        String sql = "SELECT* FROM flowers";
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            flowersData flower;

            while (result.next()) {
                flower = new flowersData(result.getInt("flower_id"), result.getString("name"),
                        result.getString("status"),
                        result.getDouble("price"), result.getString("image"),
                        result.getDate("date"));

                listData.add(flower);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<flowersData> availableFlowersList;

    public void availableFlowersShowListData() {
        availableFlowersList = availableFlowersListData();

        availableFlowers_col_flowerID.setCellValueFactory(new PropertyValueFactory<>("flowerId"));
        availableFlowers_col_flowerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableFlowers_imageVavailableFlowers_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        availableFlowers_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        availableFlowers_tableView.setItems(availableFlowersList);

    }

    public void availableFlowerSearch() {
        FilteredList<flowersData> filter = new FilteredList<>(availableFlowersList, e -> true);

        availableFlowers_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(PrediateFlowerData -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (PrediateFlowerData.getFlowerId().toString().contains(searchKey)) {
                    return true;

                } else if (PrediateFlowerData.getName().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (PrediateFlowerData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (PrediateFlowerData.getPrice().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<flowersData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(availableFlowers_tableView.comparatorProperty());

        availableFlowers_tableView.setItems(sortList);
    }

    public void availableFlowerSelect() {
        flowersData flower = availableFlowers_tableView.getSelectionModel().getSelectedItem();
        int num = availableFlowers_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        availableFlowers_flowerID.setText(String.valueOf(flower.getFlowerId()));
        availableFlowers_flowername.setText(flower.getName());
        availableFlowers_price.setText(String.valueOf(flower.getPrice()));

        getData.path = flower.getImage();

        String uri = "file:" + flower.getImage();
        image = new Image(uri, 116, 139, false, true);
        availableFlowers_imageView.setImage(image);

    }

    public void purchaseAddTocart() {

        purchaseCustomerId();

        String sql = "INSERT INTO customer (customer_id, flower_id, name, quantity, price, date)" + "VALUES(?,?,?,?,?,?)";

        connect = database.connectDb();

        try {

            Alert alert;
            if (purchase_flowerID.getSelectionModel().getSelectedItem() == null || purchase_flowerName.getSelectionModel().getSelectedItem() == null || qty == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please choose the flower first");
                alert.showAndWait();
            } else {
                double priceData = 0;
                double totalPrice;
                String checkPrice = "SELECT name, price FROM flowers WHERE name = '"
                        + purchase_flowerName.getSelectionModel().getSelectedItem() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkPrice);
                if (result.next()) {
                    priceData = result.getDouble("price");
                }

                prepare = connect.prepareStatement(sql);
                prepare.setString(1, String.valueOf(customerId));
                prepare.setInt(2, (Integer) purchase_flowerID.getSelectionModel().getSelectedItem());
                prepare.setString(3, (String) purchase_flowerName.getSelectionModel().getSelectedItem());
                prepare.setString(4, String.valueOf(qty));

                totalPrice = (priceData * qty);

                prepare.setString(5, String.valueOf(totalPrice));

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(6, String.valueOf(sqlDate));

                prepare.executeUpdate();

                purchaseShowListData();
                purchaseDisplayTotal();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private double totalPrice = 0;

    public void purchaseDisplayTotal() {
        purchaseCustomerId();
        String sql = "SELECT SUM(price) FROM customer WHERE customer_id = '" + customerId + "'";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                totalPrice = result.getDouble("SUM(price)");
            }
            purchase_total.setText("$" + String.valueOf(totalPrice));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void purchasePay(){
        
        String sql = "INSERT INTO customer_info (customer_id, total, date) VALUES(?,?,?)";
        
        connect = database.connectDb();
        
        try{
            Alert alert;
            
            if(totalPrice == 0){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something wrong :3");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalPrice));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf(sqlDate));

                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful !! Thanks for purchase.");
                    alert.showAndWait();
                    
                    totalPrice = 0;
//                    Optional<ButtonType> option2 = alert.showAndWait();                    
//                    if(option2.get().equals(ButtonType.OK)){
//                        totalPrice = 0;
//                        PurchaseClear();
//                        
//                    }
                    }  
            }
            
        }catch(Exception e){e.printStackTrace();}
        
    }

    public void purchaseFlowerId() {
        String sql = "SELECT status, flower_id FROM flowers WHERE status = 'Available' ";

        connect = database.connectDb();

        try {
            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getInt("flower_id"));
            }

            purchase_flowerID.setItems(listData);

            purchaseFlowerName();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void purchaseFlowerName() {
        String sql = "SELECT flower_id, name FROM flowers WHERE flower_id = '" + purchase_flowerID.getSelectionModel().getSelectedItem() + "'";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("name"));
            }
            purchase_flowerName.setItems(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SpinnerValueFactory<Integer> spinner;

    public void purchaseSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        purchase_quantity.setValueFactory(spinner);

    }

    private int qty;

    public void purchaseQuantity() {
        qty = purchase_quantity.getValue();
    }

    public ObservableList<customerData> purchaseListData() {
        purchaseCustomerId();
        ObservableList<customerData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer WHERE customer_id = '" + customerId + "'";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            customerData customer;

            while (result.next()) {
                customer = new customerData(result.getInt("customer_id"), result.getInt("flower_id"),
                        result.getString("name"), result.getInt("quantity"),
                        result.getDouble("price"), result.getDate("date"));

                listData.add(customer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<customerData> purchaseListD;

    public void purchaseShowListData() {
        purchaseListD = purchaseListData();

        purchase_col_flowerID.setCellValueFactory(new PropertyValueFactory<>("flowerId"));
        purchase_col_flowerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        purchase_tableView.setItems(purchaseListD);
    }

    public int customerId;

    public void purchaseCustomerId() {

        String sql = "SELECT MAX(customer_id) FROM customer";

        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                customerId = result.getInt("Max(customer_id)");
            }
            int countData = 0;

            String checkInfo = "SELECT MAX(customer_id) FROM customer_info";

            prepare = connect.prepareStatement(checkInfo);
            result = prepare.executeQuery();

            if (result.next()) {
                countData = result.getInt("MAX(customer_id)");
            }

            if (customerId == 0) {
                customerId += 1;
            } else if (customerId == countData) {
                customerId = countData + 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUsername() {
        String user = getData.username;
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            availableFlowers_form.setVisible(false);
            purchase_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #594db4, #584dd9)");
            availableFlowers_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");
            
            homeAF();
            homeTI();
            homeTC();
            homeChart();

        } else if (event.getSource() == availableFlowers_btn) {
            home_form.setVisible(false);
            availableFlowers_form.setVisible(true);
            purchase_form.setVisible(false);

            availableFlowers_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #594db4, #584dd9)");
            home_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");

            //to show an update tableview once ypuclicked the available flower button
            availableFlowersShowListData();
            availableFlowerStatus();
            availableFlowerSearch();

        } else if (event.getSource() == purchase_btn) {
            home_form.setVisible(false);
            availableFlowers_form.setVisible(false);
            purchase_form.setVisible(true);

            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #594db4, #584dd9)");
            availableFlowers_btn.setStyle("-fx-background-color: transparent");
            home_btn.setStyle("-fx-background-color: transparent");

            purchaseShowListData();
            purchaseFlowerId();
            purchaseFlowerName();
            purchaseSpinner();
            purchaseDisplayTotal();

        }
    }
    private double x = 0.0;
    private double y = 0.0;

    public void logout() {
        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                //Hide your dashboard form
                logoutBtn.getScene().getWindow().hide();

                //Link your login form
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY((event.getScreenY() - y));

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);

                });
                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayUsername();

        homeAF();
        homeTI();
        homeTC();
        homeChart();
    
        availableFlowersShowListData();
        availableFlowerStatus();

        purchaseShowListData();
        purchaseFlowerId();
        purchaseFlowerName();
        purchaseSpinner();
        purchaseDisplayTotal();
    }

}
