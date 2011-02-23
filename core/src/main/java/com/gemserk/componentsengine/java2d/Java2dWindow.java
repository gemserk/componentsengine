package com.gemserk.componentsengine.java2d;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Java2dWindow {

	protected static final Logger logger = LoggerFactory.getLogger(Java2dWindow.class);

	private final Canvas canvas;

	protected final KeyboardInput keyboardInput;

	protected final MouseInput mouseInput;

	public MouseInput getMouseInput() {
		return mouseInput;
	}

	public KeyboardInput getKeyboardInput() {
		return keyboardInput;
	}

	public Java2dWindow(String title, int width, int height) {

		keyboardInput = new KeyboardInput();
		mouseInput = new MouseInput();

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
		frame.setSize(new Dimension(width, height));
		frame.setVisible(true);

		frame.addKeyListener(keyboardInput);
		frame.addMouseListener(mouseInput);
		frame.addMouseMotionListener(mouseInput);

		canvas.addKeyListener(keyboardInput);
		canvas.addMouseListener(mouseInput);
		canvas.addMouseMotionListener(mouseInput);
		canvas.addMouseWheelListener(mouseInput);

		// canvas.createBufferStrategy(2);

		renderStrategy = new VolatileImageJava2dRenderStrategy(canvas);
		// renderStrategy = new BufferStrategyJava2dRenderStrategy(canvas);

	}

	private static interface Java2dRenderStrategy {

		void render(Java2dWindow window);

	}

	static class BufferStrategyJava2dRenderStrategy implements Java2dRenderStrategy {

		private final Canvas canvas;

		public BufferStrategyJava2dRenderStrategy(Canvas canvas) {
			this.canvas = canvas;
			this.canvas.createBufferStrategy(2);
		}

		@Override
		public void render(Java2dWindow window) {
			BufferStrategy bufferStrategy = canvas.getBufferStrategy();
			Graphics2D graphics2d = (Graphics2D) bufferStrategy.getDrawGraphics();

			window.render(graphics2d);

			bufferStrategy.show();
			graphics2d.dispose();
			graphics2d = null;
		}

	}

	static class VolatileImageJava2dRenderStrategy implements Java2dRenderStrategy {

		private final Canvas canvas;

		private VolatileImage backBufferImage = null;

		public VolatileImageJava2dRenderStrategy(Canvas canvas) {
			this.canvas = canvas;
			GraphicsConfiguration gc = this.canvas.getGraphicsConfiguration();
			createBackBufferImage(gc);
		}

		@Override
		public void render(Java2dWindow window) {
			GraphicsConfiguration gc = canvas.getGraphicsConfiguration();

			int validated = backBufferImage.validate(gc);
			if (validated == VolatileImage.IMAGE_INCOMPATIBLE) {
				createBackBufferImage(gc);
			}

			window.render((Graphics2D) backBufferImage.getGraphics());

			Graphics graphics = canvas.getGraphics();
			graphics.drawImage(backBufferImage, 0, 0, null);
		}

		private void createBackBufferImage(GraphicsConfiguration gc) {
			if (backBufferImage == null)
				backBufferImage = gc.createCompatibleVolatileImage(canvas.getWidth(), canvas.getHeight());
		}

	}

	long fps = 0;

	private boolean done;

	public long getFrameTime() {
		return 1000 / getFramesPerSecond();
	}

	public long getFramesPerSecond() {
		return fps;
	}

	public void start() {

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

	protected abstract void init();

	Java2dRenderStrategy renderStrategy;

	private void internalRender() {

		renderStrategy.render(this);

	}

	public abstract void render(Graphics2D graphics2d);

	public abstract void update(int delta);

}