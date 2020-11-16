package laiba;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * @author laiba
 */
public class Controller {
    static final int SIZE=35;
    static final int X_MAX=12*SIZE;
    static final int Y_MAX=24*SIZE;

    /**
     * 坐标网络
     */
    static int[][] MESH=new int[X_MAX/SIZE][Y_MAX/SIZE];

    private Scene scene;
    private Block current;
    private Block next;
    private Pane pane;
    private Block paint;
    private boolean game=true;
    private int top=0;
    int score=0, lines=0;
    boolean pause=true;

    Controller(Scene scene, Pane pane){
        this.current=new Block(pane);
        current.setVisual(true);
        this.next=new Block(pane);
        this.pane=pane;
        this.scene=scene;
        paint();

    }

    private void paint(){
        paint=new Block(pane,next.getType());
    }

    /**
     * 设置监听键盘的行为
     */
    private void setPress(){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(game&&!pause){
                    switch (event.getCode()){
                        case RIGHT:
                            if(current.isCanMove("a",1,0)&&current.isCanMove("b",1,0)
                                    &&current.isCanMove("c",1,0)
                                    &&current.isCanMove("d",1,0)){
                                current.moveRight();
                            }
                            break;
                        case LEFT:
                            if(current.isCanMove("a",-1,0)&&current.isCanMove("b",-1,0)
                                    &&current.isCanMove("c",-1,0)
                                    &&current.isCanMove("d",-1,0)){
                                current.moveLeft();
                            }
                            break;
                        case DOWN:
                            //TODO
                        default:
                            break;
                    }
                }
            }
        });
    }

    /**
     * 图形向下移动的方法
     */
    private void down(){
        if(current.isCanMove("a",0,1)&&current.isCanMove("b",0,1)
                &&current.isCanMove("c",0,1)
                &&current.isCanMove("d",0,1)){
            current.moveDown();
            score++;
        }
        if(isShouldChange()){
            setMESH();
            current=next;
            current.setVisual(true);
            exchange(current);
            next=new Block(pane);
            paint.remove();     //绘制预测的下一个形状
            paint();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //TODO
                }
            });

        }
    }

    /**
     * 是否切换方块对象
     */
    private boolean isShouldChange(){
        return judge(current.a)||judge(current.b)||judge(current.c)||judge(current.d);
    }
    private boolean judge(Rectangle r){
        return (int)r.getY()/SIZE==23||MESH[(int)r.getX()/SIZE][(int)r.getY()/SIZE+1]==1;
    }

    private void setMESH(){
        setData(current.a);
        setData(current.b);
        setData(current.c);
        setData(current.d);
    }
    private void setData(Rectangle r) {
        MESH[(int) r.getX() / SIZE][(int) r.getY() / SIZE] = 1;
    }

    /**
     * 修改监听对象
     */
    private void exchange(Block current){
        this.current=current;
    }

    /**
     * 消除行
     */
    private void cleanRows(){
        ArrayList<Node> nodeList=new ArrayList<>();
        ArrayList<Integer> lineList=new ArrayList<>();
        ArrayList<Integer> lineToMoveList=new ArrayList<>();
        ArrayList<Node> newNodeList=new ArrayList<>();

        int full=0;

        for(int i=0; i<MESH[0].length; ++i){
            for(int j=0; j<MESH.length; ++j){
                if(MESH[j][i]==1){
                    full++;
                }
            }
            if(full==MESH.length){
                lineList.add(i);
            }
            full=0;
        }
        for (int i=0; i<lineList.size(); ++i){
            lineToMoveList.add(lineList.get(lineList.size()-i-1));
        }
        //TODO
    }
}
