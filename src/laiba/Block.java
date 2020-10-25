package laiba;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block {
    private BlockShape type;
    private Color color;

    //不同的布局产生不同的形状
    private Pane pane;
    private int xMax=Controller.X_MAX;
    private int yMax=Controller.Y_MAX;
    private int size=Controller.SIZE;
    Rectangle a=new Rectangle(size-1,size-1);
    Rectangle b=new Rectangle(size-1,size-1);
    Rectangle c=new Rectangle(size-1,size-1);
    Rectangle d=new Rectangle(size-1,size-1);
    //图形的翻转时使用
    private int form;

    public BlockShape getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    Block(Pane pane,String name){
        //TODO
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
        }
    }
}
