package sqlNote;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RootController implements Initializable {
	
	@FXML TextField txtTitle; //����
	@FXML PasswordField txtPassword; //��й�ȣ
	@FXML ComboBox<String> comboPublic; //�޺��ڽ�
	@FXML TextArea txtContent; // ����
	@FXML TextField textField; //��ȸ��ȣ
	@FXML TextField writerName; //�ۼ���
	
	BoarderDAO board = new BoarderDAO();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	//����
	public void handleBtnRegAction(ActionEvent event) {
		board.insertinfo(new Board(txtTitle.getText(),txtPassword.getText(),
				comboPublic.getSelectionModel().getSelectedItem(),writerName.getText(),txtContent.getText()));
		board.number();
	}
	
	//�ʱ�ȭ 
	public void handleBtnClear(ActionEvent event) {
		txtTitle.setText("");
		txtPassword.setText("");
		txtContent.setText("");
		writerName.setText("");
		textField.setText("");
	}
	
	//��ȸ
	public void handleBtnSelect(ActionEvent event) {
		Board boardd = new Board();
		//��ȸ��ȣ �Է�
		boardd.setId(Integer.parseInt(textField.getText()));
		board.selectOne(boardd);
		
		txtTitle.setText(boardd.getBoardTitle());
		txtPassword.setText(boardd.getBoardPassword());
		txtContent.setText(boardd.getTextContent());
		writerName.setText(boardd.getWriterName());
		
	}
}