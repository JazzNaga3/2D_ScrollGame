package Game_2D;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import Game_2D.MainWindow.Status;

public class Stage {
	
	static int BLOCK_SIZE = 30; //ブロックサイズ
	static int ROW = 15; //行数
	static int COLUMN = 30; //列数
	static int STAGE_WIDTH = BLOCK_SIZE * COLUMN; //ステージの長さ
	static int STAGE_HEIGHT = BLOCK_SIZE * ROW; //ステージの高さ
	
	BufferedImage[] stage_img; //ステージ画像
	BufferedImage[] block_img; //ブロック画像
	BufferedImage[] enemy_img; //敵画像
	BufferedImage[] dark_img = new BufferedImage[3]; //ゲームオーバー時画像
	BufferedImage goal_img;
	
	static int[] BackgroundImage_w; //背景画像横サイズ
	static int[] BackgroundImage_h; //背景画像縦サイズ
	static int BlockImage_w; //ブロック画像横サイズ
	static int BlockImage_h; //ブロック画像縦サイズ
	static int[] EnemyImage_w; //敵画像横サイズ
	static int[] EnemyImage_h; //敵画像縦サイズ
	

	static double NearEnemy_x; //最短の敵のx座標
	static double NearEnemy_y; //最短の敵のy座標
	static double r = 10; //敵(円)の半径
	
	static int[][] map;
	
	static int[][] forest_map = { //ステージ見取り図
			
			//0→なし,1→ブロック,2→敵,3→エリア外,4→ゴール
			
		
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,2,0,0,0,0,0,4},
			{1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
			{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3} //ゲームオーバーエリア
		
	};
	
	static int[][] desert_map = { //砂漠ステージ見取り図
			
			
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,2,0,0,0,0,0,1,0,0,0,1,0,2,0,0,0,0,0,0,0,0,0,4},
			{1,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}, //ゲームオーバーエリア
			{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}
	};
	
	static int[][] snow_mountain_map = { //雪山ステージ見取り図
			
			
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,2,0,0,0,0,0,0,0,0,0,4},
			{1,1,1,1,0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}, //ゲームオーバーエリア
			{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}
	};
	
	
	//コンストラクタ
	public Stage(BufferedImage[] s_img,BufferedImage[] b_img,BufferedImage[] e_img,BufferedImage g_img){
		
		this.stage_img = s_img;
		this.block_img = b_img;
		this.enemy_img  = e_img;
		this.goal_img = g_img;
		
		BackgroundImage_w = new int[stage_img.length];
		BackgroundImage_h = new int[stage_img.length];
		EnemyImage_w = new int[enemy_img.length];
		EnemyImage_h = new int[enemy_img.length];
		
		for(int i = 0; i < stage_img.length; i++) {
			
			BackgroundImage_w[i] = stage_img[i].getWidth(); 
			BackgroundImage_h[i] = stage_img[i].getHeight();	
			EnemyImage_w[i] = enemy_img[i].getWidth(); 
			EnemyImage_h[i] = enemy_img[i].getHeight();
			dark_img[i] = new BufferedImage(BackgroundImage_w[i],BackgroundImage_h[i],BufferedImage.TYPE_INT_RGB);
		
			for(int y = 0; y < BackgroundImage_h[i]; y++)
				for(int x = 0; x < BackgroundImage_w[i]; x++) {
					
					//-----画像を暗くする処理-----
					//下記のURL先のサイトを参考にしました
					//https://tommyproguram.blogspot.com/2015/03/java.html
					int color = stage_img[i].getRGB(x, y);
	                int red   = (color & 0xff0000) >> 16;
	                int green = (color & 0x00ff00) >> 8;
	                int blue  = (color & 0x0000ff) >> 0;
	                 
	                red   = red / 3;
	                green = green / 3;
	                blue  = blue / 3;
	                
	                //色変換
	                red  = (red << 16)  & 0xff0000;
	                green = (green << 8) & 0x00ff00;
	                blue = (blue << 0)  & 0x0000ff;
	                color = 0xff000000 | red | green | blue;
	                dark_img[i].setRGB(x, y, color);
	                //-----------------------------
				}
		}
		
	}		
		
		
	//ステージ作成(プレイヤーの位置によってブロック配置)
	public void draw_stage(Graphics g,int ScreenOut_x,int ScreenOut_y) {
		
		//背景
		if(MainWindow._status_ == Status.Play) { //通常時
			if(MainWindow.getStageNumber() == 0)
				g.drawImage(stage_img[0],0,0, null);
			else if(MainWindow.getStageNumber() == 1)
				g.drawImage(stage_img[1],0,0,null);
			else if(MainWindow.getStageNumber() == 2)
				g.drawImage(stage_img[2],0,0,null);
		}else if(MainWindow._status_ == Status.GameOver ||MainWindow._status_ ==  Status.GameClear){ //ゲームオーバー時
			if(MainWindow.getStageNumber() == 0)
				g.drawImage(dark_img[0],0,0, null);
			else if(MainWindow.getStageNumber() == 1)
				g.drawImage(dark_img[1],0,0,null);
			else if(MainWindow.getStageNumber() == 2)
				g.drawImage(dark_img[2],0,0,null);
		}
		
		//画面領域設定
		int CameraStartX = toBlock(-ScreenOut_x);	
		int CameraEndX = CameraStartX + toBlock(MainWindow.WIDTH) + 1;
		CameraEndX = Math.min(CameraEndX, COLUMN);
		
		int CameraStartY = toBlock(-ScreenOut_y);
		int CameraEndY = CameraStartY + toBlock(MainWindow.HEIGHT) + 1;
		CameraEndY = Math.min(CameraEndY, ROW);
		
		//ステージ描画
		if(MainWindow.getStageNumber() == 0) { //森ステージ	
			
			for(int y = CameraStartY; y < CameraEndY; y++) {
				for(int x = CameraStartX; x < CameraEndX; x++){
		
					
					switch(forest_map[y][x]) {
					
						case 1: //画面上からステージが構築
							g.drawImage(block_img[0],toPixel(x) + ScreenOut_x,toPixel(y) + ScreenOut_y,null);
							break;
							
						case 2: //敵描画
							g.drawImage(enemy_img[0],toPixel(x) + ScreenOut_x - 2,toPixel(y) + ScreenOut_y + 3,null);
							storage(toPixel(x),toPixel(y));
							break;
						
						case 4:
							g.drawImage(goal_img,toPixel(x) + ScreenOut_x - BLOCK_SIZE,toPixel(y) + ScreenOut_y - BLOCK_SIZE,null);
							break;
					}
				}
			}
			
		}else if(MainWindow.getStageNumber() == 1) { //砂漠ステージ
			
			for(int y = CameraStartY; y < CameraEndY; y++) {
				for(int x = CameraStartX; x < CameraEndX; x++){
					
					switch(desert_map[y][x]) {
					
						case 1:
							g.drawImage(block_img[1],toPixel(x) + ScreenOut_x,toPixel(y) + ScreenOut_y,null);
							break;
							
						case 2:
							g.drawImage(enemy_img[1],toPixel(x) + ScreenOut_x - 2,toPixel(y) + ScreenOut_y + 3,null);
							storage(toPixel(x),toPixel(y));
							break;
						
						case 4:
							g.drawImage(goal_img,toPixel(x) + ScreenOut_x - BLOCK_SIZE,toPixel(y) + ScreenOut_y - BLOCK_SIZE,null);
							break;
					}
				}
				
			}
		}else if(MainWindow.getStageNumber() == 2) { //雪山ステージ
		
			for(int y = CameraStartY; y < CameraEndY; y++) {
				for(int x = CameraStartX; x < CameraEndX; x++){
					
					switch(snow_mountain_map[y][x]) {
					
						case 1:
							g.drawImage(block_img[2],toPixel(x) + ScreenOut_x,toPixel(y) + ScreenOut_y,null);
							break;
							
						case 2:
							g.drawImage(enemy_img[2],toPixel(x) + ScreenOut_x - 2,toPixel(y) + ScreenOut_y + 3,null);
							storage(toPixel(x),toPixel(y));
							break;
						
						case 4:
							g.drawImage(goal_img,toPixel(x) + ScreenOut_x - BLOCK_SIZE,toPixel(y) + ScreenOut_y - BLOCK_SIZE,null);
							break;
					}
				}
				
			}
		}
	}
	

	
//	当たり判定(Pointクラスで座標を保存)
	public static Point hit_block(double next_x,double next_y) {
		
		//座標取得
		double before_x = Player.get_x();
		double before_y = Player.get_y();
		
		double t = 0;
		
		//値交換
		if(before_x > next_x) {
			t = before_x;
			before_x = next_x;
			next_x = t;
		}
		
		if(before_y > next_y) {
			t = before_y;
			before_y = next_y;
			next_y = t;
		}
		
		//ピクセル単位からブロック単位に変換
		int from_x = toBlock(before_x);
		int from_y = toBlock(before_y);
		int to_x = toBlock(next_x + Player.WIDTH - 0.01);
		int to_y = toBlock(next_y + Player.HEIGHT - 0.01);
		
		for(int x = from_x; x <= to_x; x++ )
			for(int y = from_y; y <= to_y; y++) {
				
				Point p = new Point(x,y);
				
				//当たった場合
				if(x < 0 || x >= COLUMN) {//右端,左端のとき
					return p;
				}
				
				if(y < 0) { //上端,下端のとき
					return p;
				}
				
				if(map[y][x] == 1) { //ブロックがあるとき
					return p;
				}
				
				if(map[y][x] == 3) {
					MainWindow._status_ = Status.GameOver;
				}
				
				if(map[y][x] == 4) {
					MainWindow._status_ = Status.GameClear;
				}
				
			}
		
		//当たらなかった場合
		return null;
		
	}
	
	//円同士の当たり判定
	public static Point hit_circle(double cx,double cy,double radius) {
		
		Point p = new Point((int)cx,(int)cy);
		
		//キャラと敵の座標から距離を求める
		double dx = cx - NearEnemy_x;
		double dy = cy - NearEnemy_y;
		double dr = radius + r;
	
		
		//当たっているかどうか
			
			boolean result = (dx*dx + dy*dy) <= dr*dr;
			if(result) { 
				MainWindow._status_ = Status.GameOver;
				return p;
			}
			
		return null;
	
	}
	
	//敵座標保存
	public static void storage(int x,int y) { //敵座標の保存,未完成
		NearEnemy_x = x; 
		NearEnemy_y = y;
	}
			
	//Blockサイズに変更
	public static int toBlock(double d) {
		return (int)Math.floor(d / BLOCK_SIZE); //切り捨てにしないとブロックにキャラが埋まることがある
	}
	
	//pixelサイズに変更
	public static int toPixel(int i) {
		return i * BLOCK_SIZE;
	}

	
		
	
	
	
	
	

}
