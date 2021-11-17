package Game_2D;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainWindow extends JPanel implements Runnable,KeyListener,ActionListener{
	
    //パネルサイズ
	public static final int WIDTH = 450;
	public static final int HEIGHT = 300;
	
	//キー状態 true = 押されている状態
	public static boolean LeftKeyPressed;
	public static boolean RightKeyPressed;
	public static boolean SpaceKeyPressed;
	public static boolean JUMP_JUDGE = true;
	
	//player作成
	Player py1;
	
	//stage作成
	Stage stage;
	public static int stage_number;
	
	//ゲームループ
	Thread th;
	
	//画像
	public BufferedImage[] block_img = new BufferedImage[3];
	public BufferedImage[] player_img = new BufferedImage[3];
	public BufferedImage[] stage_img = new BufferedImage[3];
	public BufferedImage[] enemy_img = new BufferedImage[3];
	public BufferedImage goal_img;
	
	//ホームウインドウ
	JLabel lb = new JLabel();//UI調整用
	JLabel lb1 = new JLabel("～勇者の大冒険～");
	JLabel lb2_1 = new JLabel("スペースキー...ジャンプ   矢印キー(左右)...移動");
	JLabel lb2_2 = new JLabel("ステージを選択してください");
	Panel p1 = new Panel();
	Panel p2 = new Panel();
	Panel p3 = new Panel();
	Button b1 = new Button("森(easy)");
	Button b2 = new Button("砂漠(normal)");
	Button b3 = new Button("雪山(hard)");
	Button b4 = new Button("コンテニュー");
	Button b5 = new Button("ステージ選択");
	
	JFrame jf = new JFrame();
	
	//ステータス
	public static enum Status{
		Home, //Home画面
		Play, //Play画面
		GameOver, //GameOver画面
		GameClear //GameClear画面
	};
	public static Status _status_ = Status.Home;
	
	public static boolean home_once = true;
	public static boolean button_once = true;
	
	//コンストラクタ
	public MainWindow() {
		
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		
		try {
			//画像ファイル読込
			//画像は下記サイトのゲームフリー素材を使用しました。
			//https://pipoya.net/sozai/
			
			//ブロック
			block_img[0] = ImageIO.read(new FileInputStream("block_green.png"));
			block_img[1] = ImageIO.read(new FileInputStream("block_brown.png"));
			block_img[2] = ImageIO.read(new FileInputStream("block_blue.png"));
			
			//プレイヤー画像
			player_img[0] = ImageIO.read(new FileInputStream("yuusya.png"));
			player_img[1] = ImageIO.read(new FileInputStream("yuusya_right.png"));
			player_img[2] = ImageIO.read(new FileInputStream("yuusya_left.png"));
			
			//ステージ画像
			stage_img[0] = ImageIO.read(new FileInputStream("forest.jpg")); //森
			stage_img[1] = ImageIO.read(new FileInputStream("desert.jpg")); //砂漠
			stage_img[2] = ImageIO.read(new FileInputStream("snow_mountain.jpg")); //雪山
			
			//敵画像
			//-----森ステージ-----
			enemy_img[0] = ImageIO.read(new FileInputStream("forest_enemy.png")); 
			//-----砂漠ステージ-----
			enemy_img[1] = ImageIO.read(new FileInputStream("desert_enemy.png")); 
			//-----雪山ステージ
			enemy_img[2] = ImageIO.read(new FileInputStream("snow_mountain_enemy.png")); 
			
			//ゴール画像
			goal_img = ImageIO.read(new FileInputStream("goal.png"));
			
		}catch(Exception e) {//失敗した時
			return;
		}
		
		//インスタンス化(生成)
		stage = new Stage(stage_img,block_img,enemy_img,goal_img);
		py1 = new Player(0,HEIGHT - Player.HEIGHT,player_img);
		
		//キーへのアクセス許可
		addKeyListener(this);
		requestFocus();
		
		
		//ゲームスレッド
		th = new Thread(this);
		th.start();
		
		
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(e.getSource() == b1) { //森
			stage_number = 0;
			_status_ = Status.Play;
			Stage.map = Stage.forest_map;
			Stage.NearEnemy_x = 0;
			Stage.NearEnemy_y = 0;
			removeAll();
		}else if(e.getSource() == b2) { //砂漠
			stage_number = 1;
			_status_ = Status.Play;
			Stage.map = Stage.desert_map;
			removeAll();
		}else if(e.getSource() == b3) { //雪山
			stage_number = 2;
			_status_ = Status.Play;
			Stage.map = Stage.snow_mountain_map;
			removeAll();
		}else if(e.getSource() == b4) { //コンテニュー
			_status_ = Status.Play;
			home_once = true;
			button_once = true;
			Player.x = 0;
			Player.y = HEIGHT - Player.HEIGHT;
			Player.x_speed = 0;
			Player.y_speed = 0;
			removeAll();
		}else if(e.getSource() == b5) { //Home画面
			_status_ = Status.Home;
			home_once = true;
			button_once = true;
			Player.x = 0;
			Player.y = HEIGHT - Player.HEIGHT;
			Player.x_speed = 0;
			Player.y_speed = 0;
			removeAll();			
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	//キーを押した時
	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		
		switch(k) {
		
			case KeyEvent.VK_LEFT:
				LeftKeyPressed = true;
				break;
				
			case KeyEvent.VK_RIGHT:
				RightKeyPressed = true;
				break;
		
			case KeyEvent.VK_SPACE:
				SpaceKeyPressed = true;
				break;
		}
	}

	//キーを離した時
	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		
		switch(k) {
		
			case KeyEvent.VK_LEFT:
				LeftKeyPressed = false;
				break;
			
			case KeyEvent.VK_RIGHT:
				RightKeyPressed = false;
				break;
				
			case KeyEvent.VK_SPACE:
				SpaceKeyPressed = false;
				JUMP_JUDGE = true;
				break;
			
	     }
		
	}

	@Override
	public void run() { //繰り返し並列処理
		
		while(true) {
			
			switch(_status_) {
			
				case Home:
					repaint();
					try {
						Thread.sleep(16);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
					
				case Play:
					//キー操作
					if(LeftKeyPressed) { 
						py1.Left_accelerated();
					}else if(RightKeyPressed) {
						py1.Right_accelerated();
					}else {
						py1.stop(); //止まる
					}
					
					if(SpaceKeyPressed)
						if(JUMP_JUDGE == true) //キー押したままのジャンプ防止
								py1.jump();
				
					py1.update(); //playerの動き
					repaint();
					try {
						Thread.sleep(16); //FPS60くらい
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
					
				case GameOver:
					repaint();
					try {
						Thread.sleep(16); //FPS60くらい
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
					
				case GameClear:
					repaint();
					try {
						Thread.sleep(16); //FPS60くらい
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
			}
			
		}
		
	}
	
	//画面のスクロール機能は下記のURL先のサイトを参考に作りました。
	//https://aidiary.hatenablog.com/entry/20050624/1255786339
	public void paint(Graphics g) { //runメソッド内のrepaintで呼び出される
		super.paintComponent(g);
		
		//-------ここからスクロール関係のコード--------
		//ScreenOut_x,yはマイナスの値になる
		int ScreenOut_x = MainWindow.WIDTH / 2 - (int)Player.get_x() - Player.WIDTH / 2;
		int ScreenOut_y = MainWindow.HEIGHT / 2 - (int)Player.get_y();
		
		//ScreenOut_xが基準点にきたらスクリーン固定
		ScreenOut_x = Math.min(ScreenOut_x, 0); //左角
		ScreenOut_x = Math.max(ScreenOut_x, MainWindow.WIDTH - Stage.STAGE_WIDTH); //右角
		ScreenOut_y = Math.min(ScreenOut_y, 0); //上
		ScreenOut_y = Math.max(ScreenOut_y, MainWindow.HEIGHT - Stage.STAGE_HEIGHT); //下
		//---------------------------------------------

		switch(_status_) {
		
			case Home:
				if(home_once){
					//----------タイトル画面----------
					setLayout(new GridLayout(3,1));
					
					add(p1); //タイトル
					add(p2); //説明
					add(p3); //ステージ選択
					b1.addActionListener(this);
					b2.addActionListener(this);
					b3.addActionListener(this);
					
					//タイトル
					p1.setLayout(new BorderLayout());
					lb.setOpaque(true);
					lb.setBackground(Color.BLACK); 
					lb1.setOpaque(true);
					lb1.setHorizontalAlignment(JLabel.CENTER); //文字の位置
					lb1.setFont(new Font("明朝体",Font.ROMAN_BASELINE,45)); //フォント
					lb1.setBackground(Color.BLACK); //背景
					lb1.setForeground(Color.WHITE); //手前
					p1.add(lb,BorderLayout.CENTER);
					p1.add(lb1,BorderLayout.PAGE_END); //パネルlayout
					
					
					//説明
					p2.setLayout(new GridLayout(2,1));
					lb2_1.setOpaque(true);
					lb2_1.setHorizontalAlignment(JLabel.CENTER);
					lb2_1.setBackground(Color.BLACK);
					lb2_1.setForeground(Color.WHITE);
					lb2_2.setOpaque(true);
					lb2_2.setHorizontalAlignment(JLabel.CENTER);
					lb2_2.setBackground(Color.BLACK);
					lb2_2.setForeground(Color.WHITE);
					p2.add(lb2_1,BorderLayout.PAGE_START);
					p2.add(lb2_2,BorderLayout.PAGE_END);
					
					
					//ステージ選択
					p3.setLayout(new BorderLayout());
					
					b1.setPreferredSize(new Dimension(WIDTH / 3,0));
					b1.setFont(new Font("明朝体",Font.ROMAN_BASELINE,20));
					b1.setBackground(Color.BLACK);
					b1.setForeground(Color.GREEN);
					p3.add(b1,BorderLayout.LINE_START);
					
					b2.setPreferredSize(new Dimension(WIDTH / 3,0));
					b2.setFont(new Font("明朝体",Font.ROMAN_BASELINE,20));
					b2.setBackground(Color.BLACK);
					b2.setForeground(Color.ORANGE);
					p3.add(b2,BorderLayout.CENTER);
					
					b3.setPreferredSize(new Dimension(WIDTH / 3,0));
					b3.setFont(new Font("明朝体",Font.ROMAN_BASELINE,20));
					b3.setBackground(Color.BLACK);
					b3.setForeground(Color.BLUE);
					p3.add(b3,BorderLayout.LINE_END);
					//---------------------------------
					home_once = false;
				}
				break;
				
			case Play:
				//ステージ描画
				stage.draw_stage(g,ScreenOut_x,ScreenOut_y);
				
				//プレイヤー描画
				py1.draw(g,ScreenOut_x,ScreenOut_y);
				break;
			
			case GameOver:
				//ステージ描画
				stage.draw_stage(g,ScreenOut_x,ScreenOut_y);
				
				//プレイヤー描画
				py1.draw(g,ScreenOut_x,ScreenOut_y);
				
				//GameOver画面
				Font font1 = new Font("MSPゴシック",Font.BOLD,40);
				g.setFont(font1);
				g.setColor(Color.RED);
				g.drawString("GameOver!",WIDTH / 2 - 100,HEIGHT / 3);
				
				//コンテニューボタンとステージ選択画面へボタン
				if(button_once) {
					Font font2 = new Font("MSPゴシック",Font.BOLD,10);
					g.setFont(font2);
					b4.setBounds(100,HEIGHT / 2,100,50);
					b4.addActionListener(this);
					add(b4);
					b5.setBounds(250,HEIGHT / 2,100,50);
					b5.addActionListener(this);
					add(b5);
					button_once = false;
				}
				jf.getContentPane();
				jf.pack();
				setVisible(true);
				
				break;
			
			case GameClear:
				//ステージ描画
				stage.draw_stage(g,ScreenOut_x,ScreenOut_y);
				
				//プレイヤー描画
				py1.draw(g,ScreenOut_x,ScreenOut_y);
				
				//GameOver画面
				Font font3 = new Font("MSPゴシック",Font.BOLD,40);
				g.setFont(font3);
				g.setColor(Color.ORANGE);
				g.drawString("GameClear!",WIDTH / 2 - 100,HEIGHT / 3);
				
				//コンテニューボタンとステージ選択画面へボタン
				if(button_once) {
					Font font2 = new Font("MSPゴシック",Font.BOLD,10);
					g.setFont(font2);
					b4.setBounds(100,HEIGHT / 2,100,50);
					b4.addActionListener(this);
					add(b4);
					b5.setBounds(250,HEIGHT / 2,100,50);
					b5.addActionListener(this);
					add(b5);
					button_once = false;
				}
				setVisible(true);
				break;
				
				
		}
		
	}
	
	static int getStageNumber() { //ステージ番号
		return stage_number;
	}
	
	

		

}
