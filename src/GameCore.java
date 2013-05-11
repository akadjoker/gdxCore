
import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;


 abstract class GameCore implements ApplicationListener,InputProcessor {


	Random random = new Random(System.nanoTime());
	
	 public Array<Texture> Textures;
	 public Array<Sprite> Sprites;
	
	private SpriteBatch batch;  
	Integer frames = 0;
	
	float scale = 1f;
	
	private OrthographicCamera camera;
    private Rectangle viewport;

	private  int VIRTUAL_WIDTH = 640;
    private  int VIRTUAL_HEIGHT = 480;
    private  float ASPECT_RATIO ;

    public int LoadImage(String fname)
    {
    	 int realw;
    	 int realh;
    	 int imagew;
    	 int imageh;
     	 Pixmap image,pixmap;
    	 Texture tex;
    	
    	 image=new Pixmap(Gdx.files.internal(fname));
    	 realw=image.getWidth();
    	 imageh=image.getHeight();
    	 
    	 
    	 if (!MathUtils.isPowerOfTwo(image.getWidth()) || !MathUtils.isPowerOfTwo(image.getHeight()))
         {
        	//System.out.println("Image is not power of 2 :"+w+"x"+h);
        	
        	 imagew=MathUtils.nextPowerOfTwo(image.getWidth());
        	 imageh=MathUtils.nextPowerOfTwo(image.getHeight());
        	   
        	 
        //	 System.out.println("rescale Image to :"+imagew+"x"+imageh);
        	 
        	 
        	 pixmap=new Pixmap(imagew,imageh,Pixmap.Format.RGBA8888);
        	 pixmap.drawPixmap(image, 0,0);
        	 tex = new Texture(pixmap);	
        	 
        	 Textures.add(tex);
             return Textures.size-1;
        	    
         }
        
        
       //  System.out.println("Image is  power of 2 :"+w+"x"+h);
        // pixmap=new Pixmap(w,h,Pixmap.Format.RGBA8888);
    	// pixmap.drawPixmap(img, 0,0);
    	 tex = new Texture(image);
         
    	 
    	// Texture image= new Texture(Gdx.files.internal(fname));
    	 Textures.add(tex);
         return Textures.size-1;
    
    }
    public int CreateSprite(int imageindex)
    {
    	Sprite actor= new Sprite(Textures.get(imageindex));
    	//actor.setPosition(0, 0-this.VIRTUAL_HEIGHT);
    	//actor.setScale(2, 2);
    	Sprites.add(actor);
		return Sprites.size-1;
    }
    public int CreateSprite(int imageindex,int w,int h)
    {
    	Sprite actor= new Sprite(Textures.get(imageindex),w,h);
    //	actor.setOrigin(originX, originY)
    	//actor.setPosition(0, 0-this.VIRTUAL_HEIGHT);
    	Sprites.add(actor);
		return Sprites.size-1;
    }
    public int CreateSprite(int imageindex,int x,int y,int w,int h)
    {
    	Sprite actor= new Sprite(Textures.get(imageindex),x,y,w,h);
    //	actor.setPosition(0, 0-this.VIRTUAL_HEIGHT);
    	Sprites.add(actor);
		return Sprites.size-1;
    }
   //*****************
    public void SetSpritePosition(int index,float x,float y)
    {
      Sprite spr=	Sprites.get(index);
      if (spr!=null)
       {
    	spr.setPosition(x, y);
       }
    }
    
    public void SetSpriteX(int index,float x)
    {
      Sprite spr=	Sprites.get(index);
      if (spr!=null)
       {
    	spr.setPosition(x, spr.getY());
       }
    }
    
    public void SetSpriteY(int index,float y)
    {
      Sprite spr=	Sprites.get(index);
      if (spr!=null)
       {
    	spr.setPosition(spr.getX(), y);
       }
    }
    
    public void RotateSprite(int index,float a)
    {
      Sprite spr=	Sprites.get(index);
      if (spr!=null)
       {
    	spr.rotate(a*MathUtils.degreesToRadians);
       }
    }
   
    public void SetSpriteColor(int index,float r, float g, float b, float a)
    {
        Sprite spr=	Sprites.get(index);
        if (spr!=null)
         {
      	spr.setColor(r, g, b, a);
         }
      }
    public void SetSpriteFlip(int index,boolean x,boolean y )
    {
        Sprite spr=	Sprites.get(index);
        if (spr!=null)
         {
      	spr.flip(x, y);
         }
      }
      
       
    public boolean IsSpriteHit(int a,int b )
    {
        Sprite spra=	Sprites.get(a);
        Sprite sprb=	Sprites.get(b);
        
        if (spra!=null && sprb!=null )
         {
      	 return (spra.getBoundingRectangle().overlaps(sprb.getBoundingRectangle()));
         }
        return false;
      }
      
      
    
    public void SetSpriteAngle(int index,float a)
    {
      Sprite spr=	Sprites.get(index);
      if (spr!=null)
       {
    	spr.setRotation(a*MathUtils.radiansToDegrees);
       }
    }
    
    
   //*************
    public GameCore(int w,int h)
    {
    	VIRTUAL_WIDTH=w;
    	VIRTUAL_HEIGHT=h;
    	ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
    	
    	Sprites = new Array<Sprite>();
    	Textures = new Array<Texture>();
    	
    }
    
    
	public abstract void OnDraw();
	public abstract void OnLoad();
	public abstract void OnMove(float dt);

	
    //*********************************************
	@Override
	public void create() {
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		OnLoad();
	}
	

	

	@Override
	public void dispose() {		
	}

	@Override
	public void pause() {		
	}

	

	
	@Override
	public void render() {
		//camera.update();
		
		camera.setToOrtho(true);
      //  camera.apply(Gdx.gl10);
        
        

        // set viewport
           Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                       (int) viewport.width, (int) viewport.height);
        
 
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 45.f, 1.0f);
	

		OnMove(1);
		//batch.setProjectionMatrix(camera.combined);
	
		batch.begin();
		
		//public Array<Sprite> Sprites;
		
		for (Sprite spr : Sprites)
		{
			
			spr.draw(batch);
		//	batch.draw(spr);
		}
		OnDraw();
	//	batch.draw(face, 0,0);
		//batch.draw(face, 800-face.getWidth(),640-face.getHeight());
	
		batch.end();
		frames++;
	}

	@Override
	public void resize(int width, int height) {
		
		float aspectRatio = (float)width/(float)height;
	    //float scale = 1f;
	    Vector2 crop = new Vector2(0f, 0f);

	    if(aspectRatio > ASPECT_RATIO)
	    {
	        scale = (float)height/(float)VIRTUAL_HEIGHT;
	        crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
	    }
	    else if(aspectRatio < ASPECT_RATIO)
	    {
	        scale = (float)width/(float)VIRTUAL_WIDTH;
	        crop.y = (height - VIRTUAL_HEIGHT*scale)/2f;
	    }
	    else
	    {
	        scale = (float)width/(float)VIRTUAL_WIDTH;
	    }

	    float w = (float)VIRTUAL_WIDTH*scale;
	    float h = (float)VIRTUAL_HEIGHT*scale;
	    viewport = new Rectangle(crop.x, crop.y, w, h);
      //  System.out.println(crop.x+" "+crop.y+" "+w+" "+h);
        
	}

	@Override
	public void resume() {		
	}



	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean touchMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	
	}
