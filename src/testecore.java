import com.badlogic.gdx.graphics.g2d.Sprite;


public class testecore extends GameCore{

	public  float mx=0;
	public testecore(int w, int h) {
		super(w, h);
	}

	@Override
	public void OnDraw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnMove(float dt) {
		
		mx++;
		
		if (mx>400) mx=0;
		
		 SetSpritePosition(0,mx,100);
		 SetSpritePosition(1,200,100);
		 //RotateSprite(1,10.1f);
		//SetSpriteAngle(1,180);
		
		if (IsSpriteHit(0,1))
		{
			RotateSprite(1,100.1f);
			SetSpriteColor(0,1,0,0,1);
		} else 	SetSpriteColor(0,1,1,1,1);
		// TODO Auto-generated method stub
	//	System.out.println(mx);
	}

	@Override
	public void OnLoad() {
		 LoadImage("assets/alien.png");
		 LoadImage("assets/ship.png");
		 CreateSprite(0,16,16);
		 CreateSprite(1);
		
	}




}
