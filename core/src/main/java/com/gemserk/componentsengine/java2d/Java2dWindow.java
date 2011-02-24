package com.gemserk.componentsengine.java2d;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.java2d.renderstrategy.Java2dRenderStrategy;
import com.gemserk.componentsengine.java2d.renderstrategy.VolatileImageJava2dRenderStrategy;
import com.google.inject.Inject;

public abstract class Java2dWindow {

	protected static final Logger logger = LoggerFactory.getLogger(Java2dWindow.class);

	private Canvas canvas;

	@Inject
	KeyboardInput keyboardInput;

	@Inject
	MouseInput mouseInput;

	private String title;
	
	private Dimension dimension; 

	public MouseInput getMouseInput() {
		return mouseInput;
	}

	public KeyboardInput getKeyboardInput() {
		return keyboardInput;
	}

	public Java2dWindow(String title, int width, int height) {
		this.title = title;
		this.dimension = new Dimension(width, height);
	}

	long fps = 0;

	private boolean done;

	public long getFrameTime() {
		return 1000 / getFramesPerSecond();
	}

	public long getFramesPerSecond() {
		return fps;
	}
	
	Java2dRenderStrategy renderStrategy;

	private void internalRender() {
		renderStrategy.render(this);
	}

	public void start() {
		
		createContainer();

		init();

		done = false;

		long frames = 0;
		long time = 0;

		double t = 0.0;
		final double dt = 0.01;

		double currentTime = 0.001 * System.currentTimeMillis();
		double accumulator = dt;

		while (!done) {

			double newTime = 0.001 * System.currentTimeMillis();
			double frameTime = newTime - currentTime;
			if (frameTime > 0.25)
				frameTime = 0.25;
			currentTime = newTime;

			accumulator += frameTime;

			while (accumulator >= dt) {
				
				// ¿should input polling be inside the while of the accumulator?
				keyboardInput.poll();
				mouseInput.poll();
				
				// previousState = currentState;
				// integrate ( currentState, t, dt )
				update((int) (dt * 1000));

				t += dt;
				accumulator -= dt;
			}

			// double alpha = accumulator / dt;

			// State state = currentState*alpha + previousState * ( 1.0 - alpha );

			// render( state ); using interpolated state

			internalRender();

			frames++;
			time += frameTime * 1000;

			if (time >= 1000) {
				time -= 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}

		}
	}

	private void createContainer() {
		
		canvas = new Canvas();

		Frame frame = new Frame(title);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				done = true;
				logger.info("exit from main window");
				System.exit(0);
			}
		});

		frame.add(canvas);
		frame.pack();
		frame.setSize(dimension);
		frame.setVisible(true);

		frame.addKeyListener(keyboardInput);
		frame.addMouseListener(mouseInput);
		frame.addMouseMotionListener(mouseInput);
		frame.addMouseWheelListener(mouseInput);

		canvas.addKeyListener(keyboardInput);
		canvas.addMouseListener(mouseInput);
		canvas.addMouseMotionListener(mouseInput);
		canvas.addMouseWheelListener(mouseInput);

		// canvas.createBufferStrategy(2);

		renderStrategy = new VolatileImageJava2dRenderStrategy(canvas);
		// renderStrategy = new BufferStrategyJava2dRenderStrategy(canvas);
		
	}

	protected abstract void init();

	public abstract void render(Graphics2D graphics2d);

	public abstract void update(int delta);

}