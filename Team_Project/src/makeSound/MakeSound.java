package makeSound;

import java.io.FileInputStream;

import javazoom.jl.player.Player;


public class MakeSound extends Thread{
	Boolean stop = false; //��� ���� ��ų ����
	
	@Override
	public void run() { //start() ȣ��� ����
		while(!stop) { 
			try {
				FileInputStream	fis = new FileInputStream("notice.mp3"); //���� ���� ���
				Player player = new Player(fis);
				player.play(); //�÷��̾� ����
				Thread.sleep(2000); //���� �Ƚ�Ű�� 2�ʸ��� ���� ���  
				System.out.println("ȿ���� ��� ��");
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		System.out.println("ȿ���� ����");
		}
	//���� ������ų �޼���
	public void soundStop(Boolean stop) {
		this.stop = stop;
	}
}
