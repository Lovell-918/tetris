package laiba;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author laiba
 */
public class Controller {
    static final int SIZE=35;
    static final int X_MAX=10*SIZE;
    static final int Y_MAX=18*SIZE;

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
        setPress();
    }

    void stop(){
        game=false;
        pause=true;
    }

    void pause(){
        pause=!pause;
    }

    /**
     * 图形向下移动的方法
     */
    void down(){
        if(current.isCanMove("a",0,1)&&current.isCanMove("b",0,1)
                &&current.isCanMove("c",0,1)
                &&current.isCanMove("d",0,1)){
            current.moveDown();
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
                    cleanRows();
                }
            });
        }
        Main.game=isGameOver();
    }

    private void paint(){
        paint=new Block(pane,next.getType());
    }

    /**
     * 设置监听键盘的行为
     */
    private void setPress(){
        scene.setOnKeyPressed(event -> {
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
                        down();
                        break;
                    case UP:
                        current.turn();
                        break;
                    default:
                        break;
                }
            }
        });
    }


    /**
     * 是否切换方块对象
     */
    private boolean isShouldChange(){
        return judge(current.a)||judge(current.b)||judge(current.c)||judge(current.d);
    }
    private boolean judge(Rectangle r){
        return (int)r.getY()/SIZE==17||MESH[(int)r.getX()/SIZE][(int)r.getY()/SIZE+1]==1;
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
        ArrayList<Node> leftNodeList=new ArrayList<>();

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
        ArrayList<Integer> lineToMoveList = new ArrayList<>(lineList);
        Collections.reverse(lineToMoveList);
        if(lineList.size()>0){
            for(Node node:pane.getChildren()){
                if(node instanceof Rectangle){
                    nodeList.add(node);
                }
            }
            score+=10*lineList.size();
            lines+=lineList.size();
            //先消除可以消除的全部行
            while (lineList.size()>0){
                for(Node node:nodeList){
                    Rectangle rectangle=(Rectangle)node;
                    if(rectangle.getY()==lineList.get(0)*SIZE&&rectangle.getX()<X_MAX){
                        MESH[(int)rectangle.getX()/SIZE][(int)rectangle.getY()/SIZE]=0;
                        pane.getChildren().remove(node);
                    }
                }
                lineList.remove(0);
            }
            nodeList.clear();
            //获得剩余的node
            for(Node node:pane.getChildren()){
                if(node instanceof Rectangle){
                    leftNodeList.add(node);
                }
            }
            if(lineToMoveList.size()>0){
                //剩下的图形下移
                while (lineToMoveList.size()>0){
                    for(Node node:leftNodeList){
                        Rectangle rectangle=(Rectangle)node;
                        if(rectangle.getY()<lineToMoveList.get(0)*SIZE&&rectangle.getX()<X_MAX){
                            rectangle.setY(rectangle.getY()+SIZE*lineToMoveList.size());
                        }
                    }
                    lineToMoveList.remove(0);
                }
                MESH=new int[X_MAX/SIZE][Y_MAX/SIZE];
                for(Node node:pane.getChildren()){
                    if(node instanceof Rectangle){
                        nodeList.add(node);
                    }
                }
                for(Node node:nodeList){
                    Rectangle r=(Rectangle)node;
                    if(r.getX()<X_MAX&&r!=next.a&&r!=next.b&&r!=next.c&&r!=next.d
                    &&r!=current.a&&r!=current.b&&r!=current.c&&r!=current.d){
                        MESH[(int)r.getX()/SIZE][(int)r.getY()/SIZE]=1;
                    }
                }
            }
        }
    }

    private boolean isGameOver(){
        if(current.isOnTheTop()){
            top++;
        }else {
            top=0;
        }
        return top<2;
    }

}
