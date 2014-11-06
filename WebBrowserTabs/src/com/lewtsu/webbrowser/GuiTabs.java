package com.lewtsu.webbrowser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

public class GuiTabs {

	private GuiTabs instance;
	private JFrame jFrame;
	private JTabbedPane jTabs;
	private List<GuiWebBrowser> listGui = new LinkedList<GuiWebBrowser>();
	private JTextArea jta;
	private JScrollPane js;
	
	public GuiTabs() {
		instance = this;
		initLookAndFeel();
		initGui();
		addTab(new GuiWebBrowser(instance, jTabs.getTabCount(), "www.google.co.th"));
		addTab(new GuiWebBrowser(instance, jTabs.getTabCount(), "www.facebook.com"));
		addTab(new GuiWebBrowser(instance, jTabs.getTabCount(), "www.blognone.com"));
		addTab(new GuiWebBrowser(instance, jTabs.getTabCount(), "www.youtube.com"));
		addTab(new GuiWebBrowser(instance, jTabs.getTabCount(), "www.sritown.com"));
	}

	public void addTab(GuiWebBrowser gui) {
		jTabs.add(gui.getJPanel());
		listGui.add(gui);
		gui.setTabs();
		jTabs.setSelectedComponent(gui.getJPanel());
	}

	public void removeTab(int index) {
		if(jTabs.getTabCount() == 1)
			return;
		jTabs.remove(index);
		listGui.remove(index);
		for(int i = 0; i < listGui.size(); ++i) {
			GuiWebBrowser g = listGui.get(i);
			g.setIndex(i);
		}
	}

	private void initGui() {
		jFrame = new JFrame();
		jFrame.setTitle("��¸����Ѳ�� �����Ѳ��آ s5050337@kmitl.ac.th - Java Web Browser (Network Programming Class Assignment 2)");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		try {
			jFrame.setIconImage(ImageIO.read(getClass().getResourceAsStream("/favicon.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		jTabs = new JTabbedPane();
		
		
		jta = new JTextArea();
		jta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jta.setForeground(Color.RED);
		jta.setWrapStyleWord(true);
		jta.setLineWrap(true);
		jta.setEditable(false);
		jta.setSize(jFrame.getWidth() / 4, jta.getHeight());
		js = new JScrollPane(jta);
		js.setPreferredSize(new Dimension(jta.getWidth(), jta.getHeight()));
		js.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jFrame.add(jTabs, BorderLayout.CENTER);
		jFrame.add(js, BorderLayout.EAST);
		jFrame.setLocationRelativeTo(null);
		jFrame.addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				jta.setSize(jFrame.getWidth() / 4, jta.getHeight());
				js.setPreferredSize(new Dimension(jta.getWidth(), jta.getHeight()));
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				
			}
		});
		jFrame.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent arg0) {
				jta.setSize(jFrame.getWidth() / 4, jta.getHeight());
				js.setPreferredSize(new Dimension(jta.getWidth(), jta.getHeight()));
			}
		});
	}

	private void initLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e){
			e.printStackTrace();
		}		
	}

	public void setVisable(boolean visable) {
		jFrame.setVisible(visable);
	}

	public JTabbedPane getTabs() {
		return jTabs;
	}
	
	public void log(int tabIndex, String str) {
		jta.append("[TAB " + (tabIndex + 1) + "] " + str + "\r\n");
		jta.setCaretPosition(jta.getText().length());
	}

	public void log(String str) {
		jta.append(str + "\r\n");
		jta.setCaretPosition(jta.getText().length());
	}

}
