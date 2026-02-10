package View;

import static AppConfig.AppConfig.PATH_JSON;
import Model.Auto;
import Model.Camion;
import Model.Color;
import Model.Moto;
import Model.Vehiculo;
import View.Botones.BtnActualizar;
import View.Botones.BtnAgregar;
import View.Botones.BtnEliminar;
import View.Botones.BtnLeer;
import View.Botones.BtnModificarPrecios;
import View.Botones.BtnesOrdenamiento;
import View.Botones.PanelExportar;
import View.Botones.PanelFiltrado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import service.GestorVehiculos;
import finalprogramacionii.Test;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TablaVehiculosView {
    private VBox view;
    private TableView<Vehiculo> table;
    private ObservableList<Vehiculo> data;
    private GestorVehiculos<Vehiculo> gestor;
    
    private VBox formularioContainer;
    

    public TablaVehiculosView(BorderPane root, GestorVehiculos<Vehiculo> gestor) {
        
        table = new TableView<>();
        data = FXCollections.observableArrayList();
        

        // Columnas
        TableColumn<Vehiculo, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        
        TableColumn<Vehiculo, String> colPatente = new TableColumn<>("Patente");
        colPatente.setCellValueFactory(new PropertyValueFactory<>("patente"));

        TableColumn<Vehiculo, Integer> colModelo = new TableColumn<>("Año");
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        TableColumn<Vehiculo, String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));

        TableColumn<Vehiculo, Color> colColor = new TableColumn<>("Color");
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));

        TableColumn<Vehiculo, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colPrecio.setCellFactory(col -> new TableCell<Vehiculo, Double>() {
            @Override
            protected void updateItem(Double precio, boolean empty) {
                super.updateItem(precio, empty);

                if (empty || precio == null) {
                    setText(null);
                } else {
                    // Formato con separadores de miles
                    DecimalFormat df = new DecimalFormat("#,###.00");
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
                    symbols.setGroupingSeparator('.');  // Punto para miles
                    symbols.setDecimalSeparator(',');   // Coma para decimales
                    df.setDecimalFormatSymbols(symbols);

                    setText("$" + df.format(precio));
                }
            }
        });

        table.getColumns().addAll(colTipo, colPatente, colModelo, colMarca, colColor, colPrecio);
        
        
        data.setAll(gestor.getLista());
        table.setItems(data);
        
        PanelFiltrado panelFiltrado = new PanelFiltrado(root, data, gestor);
        Button btnAgregar = new BtnAgregar(root, gestor);
        Button btnEliminar = new BtnEliminar(table, data, gestor);
        Button btnActualizar = new BtnActualizar(root, table, gestor); 
        Button btnLeer = new BtnLeer(table);
        Button btnVolver = new Button("← Volver");
        BtnesOrdenamiento botonesOrdenamiento = new BtnesOrdenamiento(data, gestor);
        BtnModificarPrecios btnModificarPrecios = new BtnModificarPrecios(gestor, table, data);
        PanelExportar pnlExportar = new PanelExportar(gestor);
        btnVolver.setOnAction(e -> { 
            BienvenidaView bienvenida = new BienvenidaView(root, gestor);
            root.setCenter(bienvenida.getView()); 
        });
        
        
        
        HBox botonesBox = new HBox(10, btnAgregar, btnEliminar, btnActualizar, btnLeer);
        
        botonesBox.setAlignment(Pos.CENTER);
        botonesBox.setPadding(new Insets(15));
        
        view = new VBox(10, panelFiltrado.getPanel(), table, botonesBox,
                botonesOrdenamiento.getPanel(), btnModificarPrecios.getView(),
                pnlExportar.getView(), btnVolver);
        view.setAlignment(Pos.CENTER);
         
    }

    public VBox getView() {
        return view;
    }
    
    
}

