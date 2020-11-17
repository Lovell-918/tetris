package laiba;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author laiba
 */
public class Main extends Application {

    static boolean game;
    private final int xMax=Controller.X_MAX;
    private final int yMax=Controller.Y_MAX;

    @Override
    public void start(Stage primaryStage) throws Exception{
        game=true;

        Pane pane=new Pane();
        //300为计分区域
        Scene gameScene=new Scene(pane,xMax+300,yMax);
        primaryStage.setResizable(true);
        //分隔计分区域的分隔线
        Line line=new Line(xMax,0,xMax,yMax);
        line.setStroke(Color.ANTIQUEWHITE);
        //显示分数
        Text scoreText=new Text("分数: ");
        scoreText.setStyle("-fx-font: 25 arial;");
        scoreText.setY(100);
        scoreText.setX(xMax+10);
        scoreText.setFill(Color.ORANGERED);
        //显示消除的行数
        Text level=new Text("行数: ");
        level.setStyle("-fx-font: 25 arial;");
        level.setY(200);
        level.setX(xMax+10);
        level.setFill(Color.MEDIUMVIOLETRED);
        //显示下一个方块
        Text next=new Text("下一个: ");
        next.setStyle("-fx-font: 30 arial;");
        next.setY(300);
        next.setX(xMax+10);
        next.setFill(Color.POWDERBLUE);
        pane.getChildren().addAll(line,scoreText,level,next);

        //设置按钮
        Button start=new Button("暂停/启动");
        start.setPrefSize(150,50);
        start.setLayoutX(xMax + 10);
        start.setLayoutY(yMax - 100);
        Button quit = new Button("退出");
        quit.setPrefSize(100,50);
        quit.setLayoutX(xMax + 180);
        quit.setLayoutY(yMax - 100);
        pane.getChildren().addAll(start,quit);

        //新建一个controller
        Controller controller=new Controller(gameScene,pane);
        primaryStage.setScene(gameScene);
        primaryStage.setTitle("!!!俄罗斯方块!!!");
        BackgroundFill backgroundFill=new BackgroundFill
                (Paint.valueOf("#73C6B6"), new CornerRadii(0), new Insets(0));
        Background background=new Background(backgroundFill);
        pane.setBackground(background);
        primaryStage.show();

        //设置按钮监听事件
        quit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));

        start.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> controller.pause());

        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(game&&!controller.pause){
                            controller.down();
                            scoreText.setText("分数: "+controller.score);
                            level.setText("行数: "+controller.lines);
                        }else if(!game){
                            controller.stop();
                            //结束游戏
                            Text over=new Text("游戏结束");
                            over.setFill(Color.RED);
                            over.setStyle("-fx-font: 100 arial;");
                            over.setY(yMax / 2);
                            over.setX(50);
                            pane.getChildren().add(over);
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask,0,400);

        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
