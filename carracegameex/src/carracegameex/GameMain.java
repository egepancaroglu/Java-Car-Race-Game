package carracegameex;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import javax.swing.JFrame;

public class GameMain extends JFrame {

	
	
	private static final long serialVersionUID = 1L;
	public GameMain() {
		initUI();
	}
	
	
	private void initUI() {
        setSize(700, 800);
        GamePanel panel = new GamePanel(getWidth(), getHeight());
        add(panel);

        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                panel.getTimer().stop();
                System.exit(0);
            }
        });
        setTitle(setTitleCenter("Car Race Game"));

        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
	}

    private String setTitleCenter(String str){
        
        setFont(new Font("System", Font.PLAIN, 14));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth(str);
        int y = fm.stringWidth(" ");
        int z = getWidth() / 2 - (x / 2);
        int w = z / y;
        String pad = "";
        
        pad = String.format("%"+w+"s", pad);
        
        return pad + str;
    }
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new GameMain();
			}
		});
	}

}
