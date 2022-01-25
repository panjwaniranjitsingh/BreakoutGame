package com.rspgames.breakoutgame;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final BreakoutGame game;
    Texture ballImage;
    Texture pedalImage;
    Texture brickImage;
    OrthographicCamera camera;
    Rectangle pedal,ball;
    Array<Rectangle> bricks;
    int totalBricks=21;
    int bricksbroken=0;

    float pedalSpeed=500;
    float ballSpeed=50;
    int ballXdir=-2,ballYdir=-4;
    boolean gameOver=false;
    float ScreenWidth=1280,ScreenHeight=720;
    public GameScreen(BreakoutGame game) {
        this.game=game;

        ballImage= new Texture(Gdx.files.internal("ball.png"));
        pedalImage = new Texture(Gdx.files.internal("pedal.png"));
        brickImage = new Texture(Gdx.files.internal("brick.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false,ScreenWidth,ScreenHeight);

        pedal=new Rectangle();
        pedal.x=ScreenWidth/2-100;
        pedal.y=20;

        pedal.width=200;
        pedal.height=25;

        
        ball = new Rectangle();
        ball.x=ScreenWidth/2;
        ball.y=ScreenHeight/2;
        
        ball.width=20;
        ball.height=20;

        bricks= new Array<Rectangle>();
        spawnBricks();
    }

    private void spawnBricks() {
        for(int i=0;i<totalBricks;i++) {
            Rectangle newBrick = new Rectangle();
            newBrick.x = i*60+10;
            newBrick.y = MathUtils.random(ScreenHeight*2/3, 680);
            newBrick.width = 52;
            newBrick.height = 20;
            bricks.add(newBrick);
        }
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Score: " + bricksbroken, 0, ScreenHeight-10);
        game.batch.draw(pedalImage, pedal.x, pedal.y, pedal.width, pedal.height);
        game.batch.draw(ballImage, ball.x, ball.y, ball.width,ball.height);
        for (Rectangle brick : bricks) {
            game.batch.draw(brickImage, brick.x, brick.y);
        }

        if(!gameOver) {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            pedal.x = touchPos.x - 100;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            pedal.x -= pedalSpeed * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            pedal.x += pedalSpeed * Gdx.graphics.getDeltaTime();


        if (pedal.x < 0)
            pedal.x = 0;
        if (pedal.x > ScreenWidth - pedal.width)
            pedal.x = ScreenWidth - pedal.width;


            ball.x += ballXdir* Gdx.graphics.getDeltaTime() * ballSpeed;
            ball.y += ballYdir* Gdx.graphics.getDeltaTime() * ballSpeed;
            if (ball.x < 0 || ball.x > (ScreenWidth - ball.width))
                ballXdir = -ballXdir;
            if (ball.y > (ScreenHeight-ball.height) )
                ballYdir = -ballYdir;
            if (ball.overlaps(pedal)) {
                ballYdir = -ballYdir;
            }
            if(ball.y < 1)
                gameOver=true;

            Iterator<Rectangle> iter = bricks.iterator();
            while (iter.hasNext()) {
                Rectangle brick = iter.next();
                if (brick.overlaps(ball)) {
                    ballYdir=-ballYdir;
                    bricksbroken++;
                    iter.remove();
                    if(bricksbroken==totalBricks) {
                        gameOver=true;
                    }
                }
            }
        }
        else
            game.font.draw(game.batch, "Game Over!!! \nPress R to play again", ScreenWidth/2, ScreenHeight/2);
        game.batch.end();
        if (Gdx.input.isKeyPressed(Keys.R) && gameOver) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }
}
