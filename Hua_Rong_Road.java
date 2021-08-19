package GUI.Hua_Rong_Road;

import javax.swing.*;
import java.awt.event.*;


import java.awt.*;
public class Hua_Rong_Road extends JFrame implements MouseListener , KeyListener,ActionListener{
    Person person[] = new Person[10];//用于放置人物
    //方向按钮，用于设置边界
    JButton left;//左边界
    JButton right;//右边界
    JButton above;//上边界
    JButton below;//下边界
    
    JButton restart;
    Hua_Rong_Road(){
        init();
        setBounds(100,100,320,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        validate();
    }
    void init(){
        setLayout(null);
        restart = new JButton("重新开始");
        add(restart);
        restart.setBounds(100,320,120,35);
        restart.addActionListener(this);
        String name[] = {"曹操","关羽","张","刘","周","黄","兵","兵","兵","兵"};
        for(int k = 0;k < name.length; k++){
            person[k] = new Person(k,name[k]);
            person[k].addKeyListener(this);
            person[k].addMouseListener(this);
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
        left = new JButton();
        right = new JButton();
        below = new JButton();
        above = new JButton();
        add(left);
        add(right);
        add(above);
        add(below);
        left.setBounds(49,49,5,260);
        right.setBounds(254,49,5,260);
        above.setBounds(49,49,210,5);
        below.setBounds(49,304,210,5);        
        validate();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        Person man = (Person)e.getSource();
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        go(man,below);
        if(e.getKeyCode() == KeyEvent.VK_UP)
        go(man,above);
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        go(man,left);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        go(man,right);
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
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
        Person man = (Person)e.getSource();
        int x = -1;
        int y = -1;
        x = e.getX();
        y = e.getY();
        int width = man.getBounds().width;
        int height = man.getBounds().height;
        if(y > height/2)
            go(man,below);
        if(y < height/2)
            go(man,above);
        if(x > width/2)
            go(man,right);
        if(x < width/2)
            go(man,left);
    }
    void go(Person man, JButton direction){
        boolean isMove = true;
        Rectangle manRect = man.getBounds();
        int x = man.getBounds().x;
        int y = man.getBounds().y;
        if(direction == below) y += 50;
        if(direction == above) y -= 50;
        if(direction == left) x -= 50;
        if(direction == right) x += 50;
        manRect.setLocation(x,y);
        Rectangle dirRect = direction.getBounds();
        for(int k = 0;k < 10; k++){
            Rectangle personRect = person[k].getBounds();
            if(personRect.intersects(manRect) && man.number != k){
                isMove = false;
            }

        }
        if(manRect.intersects(dirRect)) isMove = false;
        if(isMove)man.setLocation(x,y);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        new Hua_Rong_Road();
    }
}
