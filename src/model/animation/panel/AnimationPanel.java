package model.animation.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.animation.drawable.DrawableObj;

public class AnimationPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7381708487081172311L;
	
	public static int BACKGROUND = 0;
	public static int MIDGROUND = 1; 
	public static int FOREGROUND = 2; 
	
	AnimationPanel self = this;
	BufferedImage backBuffer;
	Graphics2D bbg2d;
	Timer timer;
	int width;
	int height;
	int _fps_milli;
	
	Color backgroundColor; 
	
	ArrayList<DrawableObj> drawables = new ArrayList<DrawableObj>();
	
	public AnimationPanel(int width, int height, int fps) {
		
		this.width = width;
		this.height = height;
				
		backBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		_fps_milli = (int) (1000/fps);
		
		bbg2d = (Graphics2D) backBuffer.getGraphics();
		
		timer = new Timer(_fps_milli, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(self.backgroundColor != null){
					bbg2d.setColor(backgroundColor);
					bbg2d.fillRect(0, 0, self.width, self.height);
				}
				
				for(int i=0; i<drawables.size(); i++){
					drawables.get(i).drawSelf(bbg2d);
				}
				self.repaint();
			}
		});
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g; 
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.drawImage(backBuffer, 0, 0, width, height, this);
	}
	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public void clearDrawables(){
		drawables = new ArrayList<DrawableObj>();
	}
	
	public void addToDrawableList(DrawableObj drawable){
		drawables.add(drawable);
	}
	
	public void startAnimation(){
		timer.start();
	}
	public void stopAnimation(){
		timer.stop();
	}

	
	
	/* Mouse Listener */

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		arg0.consume();
		for(DrawableObj drawable : drawables){
			if(drawable instanceof MouseListener){
				MouseListener ml = (MouseListener) drawable;
				ml.mouseClicked(arg0);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

		arg0.consume();
		for(DrawableObj drawable : drawables){
			if(drawable instanceof MouseListener){
				MouseListener ml = (MouseListener) drawable;
				ml.mouseEntered(arg0);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

		arg0.consume();
		for(DrawableObj drawable : drawables){
			if(drawable instanceof MouseListener){
				MouseListener ml = (MouseListener) drawable;
				ml.mouseExited(arg0);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		arg0.consume();
		for(DrawableObj drawable : drawables){
			if(drawable instanceof MouseListener){
				MouseListener ml = (MouseListener) drawable;
				ml.mousePressed(arg0);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		arg0.consume();
		for(DrawableObj drawable : drawables){
			if(drawable instanceof MouseListener){
				MouseListener ml = (MouseListener) drawable;
				ml.mouseReleased(arg0);
			}
		}
	}
	
	
	/* Mouse Motion Listener */
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		
		arg0.consume();
		for(DrawableObj drawable : drawables){
			if(drawable instanceof MouseMotionListener){
				MouseMotionListener ml = (MouseMotionListener) drawable;
				ml.mouseDragged(arg0);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
		arg0.consume();
		for(DrawableObj drawable : drawables){
			if(drawable instanceof MouseMotionListener){
				MouseMotionListener ml = (MouseMotionListener) drawable;
				ml.mouseMoved(arg0);
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		
		arg0.consume();
		for(DrawableObj drawable : drawables){
			if(drawable instanceof MouseWheelListener){
				MouseWheelListener ml = (MouseWheelListener) drawable;
				ml.mouseWheelMoved(arg0);
			}
		}
		
	}
}
