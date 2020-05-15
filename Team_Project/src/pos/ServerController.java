package pos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pos.menu.Menu;
import pos.menu.MenuDAO;

public class ServerController implements Initializable{
	
	Stage serverStage = ServerMain.serverStage;
	
	//FXML ���
	private @FXML TabPane tab;
	private @FXML BorderPane borderPane;
	private @FXML GridPane home;	//���̺��� �ִ� ȭ��
	
	private Parent addMenu;			//�޴� ���� ȭ��
	private MenuDAO dao;
	
//	private InetAddress ip;				//IP
	private ExecutorService threadPool;	//������Ǯ
	private ServerSocket serverSocket;	//��������
	private Socket socket;
	private List<Client> client_list = new ArrayList<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources){
		try {
			addMenu = FXMLLoader.load(getClass().getResource("menu/menu.fxml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//������ �� ������
		tab.getSelectionModel().selectedItemProperty().addListener( (ob, oldT, newT) -> {
			System.out.println(newT.getText());
			String tab = newT.getText();
			if(tab.equals("AddMenu")){
				borderPane.setCenter(addMenu);
			}else if(tab.equals("Home")) {
				borderPane.setCenter(home);
			}
		});
		
		//���� ����
		startServer();
		serverStage.setOnCloseRequest(e -> stopServer());
	}
	
	private void startServer() {
		dao = MenuDAO.getinstance();
		try {
//			ip = InetAddress.getLocalHost();	//���� ��ǻ�� ������ �޾ƿ���
			threadPool = Executors.newFixedThreadPool(10);
			if(serverSocket == null) {
				serverSocket = new ServerSocket();
//				serverSocket.bind(new InetSocketAddress(ip.getHostAddress(), 5555));
				serverSocket.bind(new InetSocketAddress("localhost", 8888));
				System.out.println("���� ����");
			}
		} catch (Exception e) {
			e.printStackTrace();
			stopServer();
		}
		//���� ���� �� �� Ŭ���̾�Ʈ�� ������ ��ٸ���.
		if(serverSocket != null) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					while(true) {
						try {
							socket = serverSocket.accept();
							Client client = new Client(socket);
//							client.start();
							
						} catch (Exception e) {
							e.printStackTrace();
							stopServer();
							break;
						}
				
					}
				}
			};
			threadPool.execute(runnable);
		}
	}
	private void stopServer() {
		try {
			if(serverSocket != null && serverSocket.isClosed()) {
				serverSocket.close();
			}
			if(threadPool != null && threadPool.isShutdown()) {
				threadPool.shutdown();
			}
			for(Client c : client_list) {
				c.socket.close();
			}
			Platform.exit();
			System.exit(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class Client extends Thread{
		
		private int tableNo;			//���̺� ��ȣ
		private List<Menu> menu_list = new ArrayList<>();	//�� ���̺��� ���� �޴� ����Ʈ ������ �����ȴ�.
		private Socket socket;			//�������� �����ϴ� �� ���̺��� ����
		
		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
		private DataOutputStream dos;
		
		public Client(Socket socket) {
			this.socket = socket;
			client_Network();
		}
		
		private void client_Network() {
		try {
			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
			
			//ó�� ���� �� ���̺� �ѹ��� �޴´�.
			String tmp = dis.readUTF();
			
			if(tmp != null) {
				tableNo = Integer.parseInt(tmp);
				//�̹� �������ִ� ���̺����� Ȯ��.(��ȣ �ߺ��ȵǰ�)
				if(client_list.size() == 0) {
					dos.writeUTF("connOk/");
				}else {
					for(Client c : client_list) {
						if(c.tableNo == tableNo) {
							dos.writeUTF("connFail/");
							return;
						}else {
							dos.writeUTF("connOk/");
						}
					}
				}
				System.out.println(tableNo + "����");
			}
			//���ӵǸ� �޴� ������ ����
			String menu = "";
			for(Menu m : dao.selectAll()) {
				menu += m.getNo() + "$$" + m.getName() + "$$" + m.getPrice();
				menu += "@@";
				//�����ְ� �������� �����ϴ� ���̺��� �޴�����Ʈ���� �־��ش�.
				menu_list.add(m);
			}
			
			menu = menu.substring(0, menu.length()-2);
			System.out.println("�޴� : " + menu);
//			�Ľ�Ÿ$$�˸��� �ø���$$15000��@@
			if(menu != null) {
				dos.writeUTF(menu);
			}
			client_list.add(this);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		}
	}
}
