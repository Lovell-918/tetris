package laiba;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author laiba
 */
public class Block {
    private BlockShape type;
    private Color color;

    /**
     * 不同的布局产生不同的形状
     */
    private Pane pane;
    private int xMax=Controller.X_MAX;
    private int yMax=Controller.Y_MAX;
    private int size=Controller.SIZE;
    Rectangle a=new Rectangle(size-1,size-1);
    Rectangle b=new Rectangle(size-1,size-1);
    Rectangle c=new Rectangle(size-1,size-1);
    Rectangle d=new Rectangle(size-1,size-1);

    private static final int BOUNDARY_I=100;
    private static final int BOUNDARY_J=200;
    private static final int BOUNDARY_L=300;
    private static final int BOUNDARY_O=400;
    private static final int BOUNDARY_S=500;
    private static final int BOUNDARY_T=600;
    //private static final int BOUNDARY_Z=100;

    private static final int INIT_X=350;
    private static final int INIT_Y=400;

    /**
     * 图形的翻转时使用
     */
    private int form;

    public BlockShape getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    Block(Pane pane,BlockShape type){
        make(pane,type);
        a.setX(a.getX()+INIT_X);
        b.setX(b.getX()+INIT_X);
        c.setX(c.getX()+INIT_X);
        d.setX(d.getX()+INIT_X);
        a.setY(a.getY()+INIT_Y);
        b.setY(b.getY()+INIT_Y);
        c.setY(c.getY()+INIT_Y);
        d.setY(d.getY()+INIT_Y);
        setVisual(true);
    }

    Block(Pane pane){
        form=1;
        double rand=Math.random()*700;
        if(rand<BOUNDARY_I){
            make(pane,BlockShape.I);
        }else if(rand<BOUNDARY_J){
            make(pane,BlockShape.J);
        }else if(rand<BOUNDARY_L){
            make(pane,BlockShape.L);
        }else if(rand<BOUNDARY_O){
            make(pane,BlockShape.O);
        }else if(rand<BOUNDARY_S){
            make(pane,BlockShape.S);
        }else if(rand<BOUNDARY_T){
            make(pane,BlockShape.T);
        }else {
            make(pane,BlockShape.Z);
        }
    }

    private void make(Pane pane,BlockShape type){
        this.type=type;
        setColor();
        this.pane=pane;
        initForm();
    }

    private void setColor(){
        switch (type){
            case I:
                color=Color.RED;
                break;
            case J:
                color=Color.ORANGE;
                break;
            case L:
                color=Color.YELLOW;
                break;
            case O:
                color=Color.YELLOWGREEN;
                break;
            case S:
                color=Color.GREEN;
                break;
            case T:
                color=Color.BLUE;
                break;
            case Z:
                color=Color.PURPLE;
                break;
            default:
                break;
        }
        a.setFill(color);
        b.setFill(color);
        c.setFill(color);
        d.setFill(color);
    }

    /**
     * 初始化图形
     */
    private void initForm(){
        form=0;
        switch (type){
            case I:
                a.setX(xMax/2.0-2*size);
                a.setY(0);
                b.setX(xMax/2.0-size);
                b.setY(0);
                c.setX(xMax/2.0);
                c.setY(0);
                d.setX(xMax/2.0+size);
                d.setY(0);
                break;
            case J:
                b.setX(xMax/2.0-size);
                b.setY(0);
                a.setY(b.getY());
                a.setX(b.getX()-size);
                c.setY(b.getY());
                c.setX(b.getX()+size);
                d.setX(c.getX());
                d.setY(c.getY()+size);
                break;
            case L:
                b.setY(size);
                b.setX(xMax/2.0-size);
                a.setY(b.getY());
                a.setX(b.getX()-size);
                c.setY(b.getY());
                c.setX(b.getX()+size);
                d.setY(b.getY()-size);
                d.setX(c.getX());
                break;
            case O:
                a.setX(xMax/2.0-size);
                a.setY(0);
                b.setX(a.getX());
                b.setY(a.getY()+size);
                c.setX(b.getX()-size);
                c.setY(b.getY());
                d.setX(c.getX());
                d.setY(a.getY());
                break;
            case S:
                b.setX(xMax/2.0-size);
                b.setY(0);
                a.setX(xMax/2.0);
                a.setY(0);
                c.setX(b.getX());
                c.setY(b.getY()+size);
                d.setY(c.getY());
                d.setX(c.getX()-size);
                break;
            case T:
                b.setX(xMax/2.0-size);
                b.setY(size);
                a.setY(b.getY());
                a.setX(b.getX()+size);
                c.setX(b.getX()-size);
                c.setY(b.getY());
                d.setX(b.getX());
                d.setY(0);
                break;
            case Z:
                c.setX(xMax/2.0-size);
                c.setY(0);
                d.setX(c.getX()-size);
                d.setY(c.getY());
                b.setY(c.getY()+size);
                b.setX(c.getX());
                a.setX(c.getX()+size);
                a.setY(b.getY());
                break;
            default:
                break;
        }
    }

    void setVisual(boolean bool){
        if (bool){
            pane.getChildren().addAll(a,b,c,d);
        }
    }

    /**
     * 运动
     */
    void moveRight(){
        a.setX(a.getX()+size);
        b.setX(b.getX()+size);
        c.setX(c.getX()+size);
        d.setX(d.getX()+size);
    }
    void moveLeft(){
        a.setX(a.getX()-size);
        b.setX(b.getX()-size);
        c.setX(c.getX()-size);
        d.setX(d.getX()-size);
    }
    void moveDown(){
        a.setY(a.getY()+size);
        b.setY(b.getY()+size);
        c.setY(c.getY()+size);
        d.setY(d.getY()+size);
    }

    /**
     * 判断各方块是否可以移动
     * @param x 向右移动x单位
     * @param y 向下移动y单位
     */
    boolean isCanMove(String name, int x, int y){
        Rectangle rectangle;
        switch (name){
            case "a":
                rectangle=a;
                break;
            case "b":
                rectangle=b;
                break;
            case "c":
                rectangle=c;
                break;
            default:
                rectangle=d;
                break;

        }
        boolean xF=false;
        boolean yF=false;

        xF= x>=0?rectangle.getX()+x*size<=xMax-size:rectangle.getX()+x*size>=0;
        yF= y>=0?rectangle.getY()+y*size<=yMax-size:rectangle.getY()+y*size>=0;

        return xF&&yF&&
                Controller.MESH[(int)rectangle.getX()/size+x]
                        [(int)rectangle.getY()/size+y]==0;
    }

    /**
     * 图形变换
     * TODO
     */
    void turn(){

    }

}
