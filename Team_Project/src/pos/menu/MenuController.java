package pos.menu;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MenuController implements Initializable{

	@FXML ChoiceBox<String> choiceBox;
	@FXML TableView<Menu> table;
	@FXML TextField tfName;
	@FXML TextField tfPrice;
	@FXML Button btnAdd;
	@FXML Button btnDel;
	
	private ObservableList<Menu> menuList = FXCollections.observableArrayList();//tableView�� ������ ����Ʈ
	private ObservableList<String> col = FXCollections.observableArrayList();	//choiceBox�� ������ ����Ʈ
	private MenuDAO dao = MenuDAO.getinstance();	//DB
	
	private String no;
	private String name;
	private String price;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateTable();
		table.setItems(menuList);
		//���̺� Į���� ����
		TableColumn<Menu, ?> toNo = table.getColumns().get(0);
		toNo.setCellValueFactory(new PropertyValueFactory<>("no"));
		toNo.setStyle("-fx-aliment : CENTER");
		
		TableColumn<Menu, ?> toName = table.getColumns().get(1);
		toName.setCellValueFactory(new PropertyValueFactory<>("name"));
		toName.setStyle("-fx-aliment : CENTER");
		
		TableColumn<Menu, ?> toPrice = table.getColumns().get(2);
		toPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		toPrice.setStyle("-fx-aliment : CENTER");
		
		//choiceBox�� ������ �߰�.
		choiceBox.setItems(col);
		col.addAll("�Ľ�Ÿ", "������ũ","�ʶ���","����","������", "����", "��","��Ÿ");
		//ù��° ������ ����
		choiceBox.getSelectionModel().selectFirst();
		
		//������ ���ڸ� �޴°ɷ�
		tfPrice.textProperty().addListener( (ob, olds, news) -> {
			if (!news.matches("\\d*")) {
				tfPrice.setText(news.replaceAll("[^\\d]", ""));
	        }
		});
		//�߰� ��ư
		btnAdd.setOnAction( e -> btnAddAction(e));
		//���� ��ư
		btnDel.setOnAction( e -> btnDelAction(e));
	}
	
	private void updateTable() {
		//DB���� �޴��� �����´�.
		menuList.clear();
		List<Menu> list = dao.selectAll();
		for(Menu m : list) {
			menuList.add(m);
		}
	}
	private void btnAddAction(ActionEvent event) {
		//�߰� ��ư�� ������ �� �ؽ�Ʈ�ʵ��� ������
		//�޴� ��ü�� ����� DB�� insert
		no = choiceBox.getSelectionModel().getSelectedItem();
		name = tfName.getText();
		price = tfPrice.getText();
		if(!no.equals(null) && !name.equals("") && !price.equals("")) {
			Menu menu = new Menu(no, name, price);
			dao.insert(menu);
			tfName.clear();
			tfPrice.clear();
			updateTable();
		}
	}
	
	private void btnDelAction(ActionEvent event) {
		String name = table.getSelectionModel().getSelectedItem().getName();
		dao.delete(name);
		updateTable();
	}
}
