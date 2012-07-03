package model.animation.drawable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public abstract class RefreshingObj
{
	RefreshingObj self = this;
	Timer refreshTimer;
	
	public void setRefreshRate(double seconds){
		
		stopRefreshing();
		
		int delay = (int) (1000*seconds);
		refreshTimer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				self.step();
			}
		});
	}
	
	
	public void setFpsRate(int fps){
		
		stopRefreshing();
		int delay = (int) (1000/fps);
		refreshTimer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				self.step();
			}
		});
	}
	
	public void setDelayMillis(int delay){
		
		stopRefreshing();
		refreshTimer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				self.step();
			}
		});
	}
	
	
	public void startRefreshing(){
		if(refreshTimer != null){
			refreshTimer.start();
		}
	}
	
	public void stopRefreshing(){
		if(refreshTimer != null){
			refreshTimer.stop();
		}
	}
	
	public abstract void step();
}
