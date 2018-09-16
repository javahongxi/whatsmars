package org.hongxi.whatsmars.javase.dp.observer;

import java.util.List;
import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		Button b = new Button();
		b.addActionListener(new MyActionListener());
		b.addActionListener(new MyActionListener2());
		b.buttonPressed();
	}
}

class Button {
	private List<ActionListener> listeners = new ArrayList<ActionListener>();
	
	public void buttonPressed() {
		ActionEvent ae = new ActionEvent(System.currentTimeMillis(), this);
		for(int i = 0; i < listeners.size(); i++) {
			ActionListener l = listeners.get(i);
			l.actionPerformed(ae);
		}
	}
	
	public void addActionListener(ActionListener l) {
		listeners.add(l);
	}
}

class MyActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("button pressed!");
	}
	
}
class MyActionListener2 implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("button pressed2!");
	}
	
}


interface ActionListener {
	void actionPerformed(ActionEvent e);
}

class ActionEvent {
	long when;
	Object source;
	
	public ActionEvent(long when, Object source) {
		this.when = when;
		this.source = source;
	}
}
