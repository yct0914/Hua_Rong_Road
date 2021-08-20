package GUI.Hua_Rong_Road;

import javax.swing.*;
import java.awt.event.*;


import java.awt.*;
public class Hua_Rong_Road extends JFrame implements MouseListener , KeyListener,ActionListener,MouseMotionListener{
    Person person[] = new Person[10];//用于放置人物
    //方向按钮，用于设置边界
    JButton left;//左边界
    JButton right;//右边界
    JButton above;//上边界
    JButton below1, below2;//下边界
    JButton restart;//重新开始按钮
    JLabel clue;
    JLabel exit;
    boolean isPress = false;
    /**x0 y0为鼠标拖动时的初始位置 */
    int x0;
    int y0;
    /**四个方向的静态常量,用于移动函数go() */
    static final int UP = 1;
    static final int DOWN = 2;
    static final int LEFT = 3;
    static final int RIGHT = 4;

    /**构造函数 */
    Hua_Rong_Road(){
        init();
        setBounds(100,100,320,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        validate();
    }
    /**初始化函数 */
    void init(){
        setLayout(null);
        restart = new JButton("重新开始");
        add(restart);
        restart.setBounds(100,350,120,35);
        restart.addActionListener(this);//将restart按钮注册ActionListener监视器
        String name[] = {"曹操","关羽","张","刘","周","黄","兵","兵","兵","兵"};
        for(int k = 0;k < name.length; k++){//将人物(按钮)初始化,add,并注册监视器
            person[k] = new Person(k,name[k]);
            person[k].addKeyListener(this);
            person[k].addMouseListener(this);
            person[k].addMouseMotionListener(this);
            add(person[k]);
            
        }

        person[0].setBounds(104,54,100,100);
        person[1].setBounds(104,154,100,50);
        person[2].setBounds(54,154,50,100);
        person[3].setBounds(204,154,50,100);
        person[4].setBounds(54,54,50,100);
        person[5].setBounds(204,54,50,100);
        person[6].setBounds(54,254,50,50);
        person[7].setBounds(204,254,50,50);
        person[8].setBounds(104,204,50,50);
        person[9].setBounds(154,204,50,50);
        person[9].requestFocus();
        clue = new JLabel("将曹操从华容道中逃出关口!",SwingConstants.CENTER); 
        exit = new JLabel("关口",SwingConstants.CENTER);
        left = new JButton();
        right = new JButton();
        below1 = new JButton();
        below2 = new JButton();
        above = new JButton();
        add(left);
        add(right);
        add(above);
        add(below1);
        add(below2);
        add(clue);
        add(exit);
        left.setBounds(49,49,5,260);
        right.setBounds(254,49,5,260);
        above.setBounds(49,49,210,5);
        below1.setBounds(49,304,55,5); 
        below2.setBounds(204,304,55,5);      
        clue.setBounds(49,0,210,49);
        exit.setBounds(104,309,100,40);
        exit.setFont(new Font("宋体",Font.BOLD,20));
        clue.setFont(new Font("宋体",Font.BOLD,16));
        validate();
    }
    /**键盘移动人物(按钮) */
    @Override
    public void keyPressed(KeyEvent e) {
        Person man = (Person)e.getSource();
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        go(man,DOWN);
        if(e.getKeyCode() == KeyEvent.VK_UP)
        go(man,UP);
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        go(man,LEFT);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        go(man,RIGHT);
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {
       
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }
    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        isPress = false;//鼠标释放时重置该变量
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!isPress){//判断鼠标是否按下，为了只得到一组x0，y0的值
            x0 = e.getX();
            y0 = e.getY();
            isPress = true;
        }
        
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        Person man = (Person) e.getSource();
        /**拖拽时的鼠标坐标 */
        int x = e.getX();
        int y = e.getY();      
        /**此时得到焦点的组件 */
        int width = man.getBounds().width;
        int height = man.getBounds().height;
        if((y - y0) > height/2){
            go(man,DOWN);
            return;
        }
        if(y0 - y > height/2){
            go(man,UP);
            
            return;
        }
        if(x - x0 > width/2){
            go(man,RIGHT);
            
            return;
        }
        if(x0 - x > width/2){
            go(man,LEFT);
            
            return;
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    void go(Person man, int direction){
        boolean isMain = false;
        if(man == person[0]) isMain = true;
        boolean isMove = true;//用于确认棋子是否能移动
        Rectangle manRect = man.getBounds();//创建移动棋子的Bounds
        /**先将移动棋子的Bounds向指定方向移动 */
        int x = man.getBounds().x;
        int y = man.getBounds().y;
        if(direction == DOWN) y += 50;
        if(direction == UP) y -= 50;
        if(direction == LEFT) x -= 50;
        if(direction == RIGHT) x += 50;
        manRect.setLocation(x,y);
        /**再判断移动后的Bounds是否与其他棋子或边界(edge)相交
         * 如果不相交才移动棋子本身
         */
        Rectangle edgeRect[] = new Rectangle[6];//边界
        edgeRect[0] = above.getBounds();
        edgeRect[1] = below1.getBounds();
        edgeRect[2] = below2.getBounds();
        edgeRect[3] = left.getBounds();
        edgeRect[4] = right.getBounds();
        edgeRect[5] = exit.getBounds();
        for(int k = 0;k < 10; k++){//判断是否与其他棋子相交
            Rectangle personRect = person[k].getBounds();
            if(personRect.intersects(manRect) && man.number != k){
                isMove = false;
            }
        }
        Rectangle exitRect = exit.getBounds();//关口的Bounds
        for(Rectangle edge : edgeRect) if(manRect.intersects(edge)) isMove = false;//判断是否与边界相交
        /**完成任务条件 */
        if(exitRect.intersects(manRect) && isMain && edgeRect[1].intersects(manRect) && edgeRect[2].intersects(manRect)){
            JOptionPane.showMessageDialog(this, "恭喜你,成功帮助曹操逃出华容道!", "完成!", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new Hua_Rong_Road();
        }
        if(isMove)man.setLocation(x,y);//确认可以移动后,移动棋子的位置
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        new Hua_Rong_Road();
    }
}
