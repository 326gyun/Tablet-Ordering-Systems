package kitchen;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pos.menu.Menu;

public class OrderBoardController implements Initializable {
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		
	}
	
	
//    private ObservableList<HBox> replaceMenu(ObservableList<HBox> ol, Menu m){
//        ObservableList<HBox> tempOl = ol;
//        try {
//           //�� �޴� ������
//           Parent node = FXMLLoader.load(getClass().getResource("menuItem.fxml"));
//           Label labelName = (Label)node.lookup("#labelName");
//           Label labelPrice = (Label)node.lookup("#labelPrice");
//           labelName.setText(m.getName());
//           labelPrice.setText(m.getPrice());
//           node.setOnMouseClicked(e -> {
//              if(e.getClickCount() == 2) {
//                 System.out.println("�޴��̸� : " + labelName.getText() + "�޴����� : " + labelPrice.getText());
//                 addOrdertable(labelName.getText());
//              }
//           });
//           if(tempOl.size() == 0) {
//              HBox hbox = new HBox();
//              hbox.setSpacing(10);
//              hbox.getChildren().add(node);
//              tempOl.add(hbox);
//           }else if(tempOl.get(tempOl.size()-1).getChildren().size() % 3 == 0 ) {
//              HBox hbox = new HBox();
//              hbox.setSpacing(10);
//              hbox.getChildren().add(node);
//              tempOl.add(hbox);
//           }else {
//              tempOl.get(tempOl.size()-1).getChildren().add(node);
//           }
//           //���콺 ����Ŭ�� �׼�
//           
//        }catch (Exception e) {
//        }
//        return tempOl;
//     }
}
