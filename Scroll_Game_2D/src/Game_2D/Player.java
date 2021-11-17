package Game_2D;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Player{
	
	//キャラサイズ(仮)
	static final int WIDTH = 30;
	static final int HEIGHT = 30;
	
	//徐々にジャンプ
	static boolean Gradually_jump = false;
	
    //座標
	static double x;
	static double y;
	
	//速度
	static double x_speed;
	static double y_speed;
	
	//走行速度
	static final double RUN_SPEED = 3;
	
	//ジャンプのスタート位置
	double jump_start = 0;
	
	//ジャンプの高さ
	final double JUMP_HEIGHT = 50;
	
	//重力加速度
	static final double GRAVITY = 4.25;
	
	static double dt = 7; 
	
	//プレイヤー画像
	BufferedImage player_img[] = new BufferedImage[3];
	
	//プレイヤー(円)の半径
	double r = 10;
		
	//コンストラクタ
	public Player(double player_x,double player_y,BufferedImage img[]){
		x = player_x;
		y = player_y;
		x_speed = 0;
		y_speed = 0;
		player_img = img;
	}
	
	
    //左方向速度
	public void Left_accelerated() {
		x_speed = - RUN_SPEED;
	}
	
	//右方向速度
	public void Right_accelerated() {
		x_speed = RUN_SPEED;
	}
	
	//ジャンプ
	public void jump() {
		MainWindow.JUMP_JUDGE = false;
		Gradually_jump = true;
	}
	
	//停止
	public void stop() {
		x_speed = 0;
	}
	
	//座標更新
	public void update() {
		//重力
		y_speed += GRAVITY;
		if(y_speed >= 15) { //最大降下速度
			y_speed = 15;
		}
		
		//座標予測値
		double next_x = x + x_speed;
		double next_y = y + y_speed;
		
		//-----x座標当たり判定検査-----
		Point cx = Stage.hit_circle(next_x,y,r);
		Point px = Stage.hit_block(next_x,y);
		
		if(cx == null && px == null) { //当たって無い時
			x = next_x;
		}else if(px != null){ //当たっている時
			if(x_speed > 0) { //右
				x = Stage.toPixel(px.x) - WIDTH;
			}else if(x_speed < 0) { //左
				x = Stage.toPixel(px.x + 1);
			}
			x_speed = 0;
		}
		//-----------------------------
		
		//-----y座標当たり判定検査-----
		Point cy = Stage.hit_circle(x, next_y, r);
		Point py = Stage.hit_block(x, next_y);
		
		if(cy == null && py == null) {
			y = next_y;
		}else if(py != null){
			if(y_speed > 0) { //下
				y = Stage.toPixel(py.y) - HEIGHT;
				y_speed = 0;  
			}else if(y_speed < 0) { //上
				y = Stage.toPixel(py.y + 1);
			}
			y_speed = 0;
				
		}
		//----------------------------

		//-----ジャンプ処理-----
		if(Gradually_jump == true) { //ジャンプしてる間実行
			
			jump_start = get_y();
			
			if(y >= jump_start - JUMP_HEIGHT) { //ジャンプできる高さまで繰り返される
				y_speed -= dt;
				dt -= 0.4;
			}
			if(dt <= 0 || y_speed >= 0){
				dt = 7;
				Gradually_jump = false;
			}
		}
		//----------------------
		
	}
	
	//x座標取得
	public static double get_x() {
		return x;
	}
	
	//y座標取得
	public static double get_y() {
		return y;
	}
	
	//描画
	public void draw(Graphics g,int ScreenOut_x,int ScreenOut_y) {
		
		//向きによってキャラの画像を変える
		if(x_speed > 0)
			g.drawImage(player_img[1],(int)x + ScreenOut_x,(int)y + ScreenOut_y,null);//右
		else if(x_speed < 0)
			g.drawImage(player_img[2],(int)x + ScreenOut_x,(int)y + ScreenOut_y,null);//左
		else
			g.drawImage(player_img[0],(int)x + ScreenOut_x,(int)y + ScreenOut_y,null);//正面
			
	}
	
	

}
