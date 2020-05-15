package sqlNote;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoarderDAO {
	
	private Connection conn; //db Ŀ�ؼ� ��ü
	private static final String USERNAME = "root";
	private static final String PASSWORD = "1234";
	//DB���� ��� ���� 
	
	private static final String URL = "jdbc:mysql://localhost:3306/boarddb";
		
	public BoarderDAO() {

	//connection ��ü ���� �� DB�� ����
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			System.out.println("����̹� �ε� ����");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����̹� �ε� ����");
		}
	}
	
	public void insertinfo(Board board) {
		
		String sql = "insert into board values(?,?,?,?,?,?);";
		
		PreparedStatement pstmt = null;
		try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,null);
		pstmt.setString(2,board.getBoardTitle());
		pstmt.setString(3,board.getBoardPassword());
		pstmt.setString(4,board.getComboPublic());
		pstmt.setString(5,board.getWriterName());
		pstmt.setString(6,board.getTextContent());
		
		//������ ����
		pstmt.executeUpdate();
		System.out.println("������ ���� ����");
		
		} catch (Exception e) {
		System.out.println("������ ���� ����");
		} finally {
			try {
				if(pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}//finally
		
	}
	
	public void number() {
		//���� ���� ���� �ֱ� �� �ҷ��ͼ� ������� ��ȸ��ȣ�� �˷��ش�.
		String sqlId = "select id from board order by id desc limit 1;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sqlId);
			ResultSet rs = pstmt.executeQuery(sqlId);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				System.out.println("��ȸ ��ȣ�� "+id+"�Դϴ�.");
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null && !pstmt.isClosed())  {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void selectOne(Board board) {
		String sql = "select * from board where id="+board.getId()+";";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(sql);
			
			while(rs.next()) {
				String id = rs.getString("borderTitle");
				board.setBoardTitle(id);
				String password = rs.getString("borderPassword");
				board.setBoardPassword(password);
				String writername = rs.getString("writerName");
				board.setWriterName(writername);
				String textcontent = rs.getString("textContent");
				board.setTextContent(textcontent);
			}
			
		} catch (Exception e) {
		} finally {
			try {
				if(pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}