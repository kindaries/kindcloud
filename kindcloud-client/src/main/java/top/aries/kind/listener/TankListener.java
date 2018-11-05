package top.aries.kind.listener;

import top.aries.kind.panel.TankJPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-10-31 17:22
 * Created by IntelliJ IDEA.
 */
public class TankListener implements KeyListener {
    private TankJPanel tankJPanel = null;

    public TankListener(TankJPanel tankJPanel) {
        super();
        this.tankJPanel = tankJPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            tankJPanel.mytank.setDrect(0);
            tankJPanel.mytank.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            tankJPanel.mytank.setDrect(1);
            tankJPanel.mytank.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            tankJPanel.mytank.setDrect(2);
            tankJPanel.mytank.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            tankJPanel.mytank.setDrect(3);
            tankJPanel.mytank.moveLeft();
        }
        //发射子弹监听
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (tankJPanel.mytank.mybs.size() < 50)
                tankJPanel.mytank.shot();
        }
        tankJPanel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
