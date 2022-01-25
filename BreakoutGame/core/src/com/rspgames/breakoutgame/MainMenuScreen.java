package com.rspgames.breakoutgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final BreakoutGame game;
    OrthographicCamera camera;
    public MainMenuScreen(final BreakoutGame breakoutGame) {
        this.game = breakoutGame;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Breakout Game!!!\nTap anywhere to begin!\nPress S to start the game", 300, 360);
        game.batch.end();

        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.S)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    @Override
    public void show() {
    }
    @Override
    public void hide() {
    }
    @Override
    public void dispose() {
    }

}
