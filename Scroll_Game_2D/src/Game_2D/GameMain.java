package Game_2D;

import java.awt.Container;

import javax.swing.JFrame;

public class GameMain extends JFrame{
	
	//コンストラクタ
	GameMain(){
		
		setTitle("勇者の大冒険");
		MainWindow mw = new MainWindow();
		Container content = getContentPane();
		content.add(mw);
		pack();		 
	}

	public static void main(String[] args) {//メイン
		GameMain gamemain = new GameMain();
		gamemain.setDefaultCloseOperation(EXIT_ON_CLOSE);
		gamemain.setVisible(true);
	}

}
