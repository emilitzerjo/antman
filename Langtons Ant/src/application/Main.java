package application;

import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class Main extends Application {

	private final int WIDTH = 1920;
	private final int HEIGHT = 1080;
	private double CELLSIZE = 0.5;
	private boolean animationRuns = false;
	private final short[][] grid = new short[WIDTH][HEIGHT];
	private Ant[] ants = new Ant[3];

	@Override
	public void start(Stage primaryStage) {
		try {
			for (int i = 0; i < ants.length; i++) {
				ants[i] = new Ant(grid);
			}
			Color[] colors = new Color[Ant.RULESET.length()];
			for (int i = 0; i < colors.length; i++) {
				float colorValue = 1 - (float) i / (colors.length - 1);
				colors[i] = new Color(colorValue, colorValue, colorValue, 1);
			}

			VBox root = new VBox();

			Canvas canvas = new Canvas(WIDTH * CELLSIZE, HEIGHT * CELLSIZE);
			root.getChildren().add(canvas);

			GraphicsContext gc = canvas.getGraphicsContext2D();
			AnimationTimer at = new AnimationTimer() {

				@Override
				public void handle(long now) {
					gc.setFill(Color.WHITE);
					gc.fillRect(0, 0, WIDTH * CELLSIZE, HEIGHT * CELLSIZE);
					for (Ant ant : ants) {
						for (int i = 0; i < 10000; i++) {
							ant.update();
						}

					}
					for (int i = 0; i < WIDTH; i++) {
						for (int k = 0; k < HEIGHT; k++) {
							if (grid[i][k] > 0) {
								gc.setFill(colors[grid[i][k]]);
								gc.fillRect(i * CELLSIZE, k * CELLSIZE,
										CELLSIZE, CELLSIZE);
							}
						}
					}
				}
			};

			Scene scene = new Scene(root, WIDTH * CELLSIZE, HEIGHT * CELLSIZE);
			scene.widthProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(
						ObservableValue<? extends Number> observableValue,
						Number oldSceneWidth, Number newSceneWidth) {
					canvas.setWidth(newSceneWidth.doubleValue());
					CELLSIZE = newSceneWidth.doubleValue() / WIDTH;
					if (scene.getHeight() < CELLSIZE * HEIGHT) {
						CELLSIZE = scene.getHeight() / HEIGHT;
						scene.setFill(Paint.valueOf("BLACK"));
					}
				}

			});
			scene.heightProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(
						ObservableValue<? extends Number> observableValue,
						Number oldSceneHeight, Number newSceneHeight) {
					canvas.setHeight(newSceneHeight.doubleValue());
					CELLSIZE = newSceneHeight.doubleValue() / HEIGHT;
					if (scene.getWidth() < CELLSIZE * WIDTH) {
						CELLSIZE = scene.getWidth() / WIDTH;
					}
				}
			});
			primaryStage.setScene(scene);
			primaryStage.setTitle("Antman");
			primaryStage.show();

			primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,
					new EventHandler<KeyEvent>() {

						@Override
						public void handle(KeyEvent event) {
							if (KeyCode.SPACE.equals(event.getCode())) {
								if (animationRuns) {
									animationRuns = false;
									at.stop();
								} else {
									animationRuns = true;
									at.start();
								}
							} else if (event.getCode().equals(KeyCode.P)) {
								WritableImage wimP = new WritableImage(WIDTH,
										HEIGHT);
								Canvas canvasP = new Canvas(WIDTH, HEIGHT);
								GraphicsContext gcP = canvasP
										.getGraphicsContext2D();
								for (int i = 0; i < WIDTH; i++) {
									for (int k = 0; k < HEIGHT; k++) {
										if (grid[i][k] > 0) {
											gcP.setFill(colors[grid[i][k]]);
											gcP.fillRect(i, k, 1, 1);
										}
									}
								}
								canvasP.snapshot(null, wimP);
								int counter = 0;
								File file = new File("CanvasImage" + counter
										+ ".png");
								while (file.exists()) {
									counter++;
									file = new File("CanvasImage" + counter
											+ ".png");
								}

								try {
									ImageIO.write(SwingFXUtils.fromFXImage(
											wimP, null), "png", file);
								} catch (Exception s) {
								}
							}

						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
