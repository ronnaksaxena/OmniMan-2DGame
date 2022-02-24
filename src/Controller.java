import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.*;
import java.awt.*;

import javax.swing.JPanel;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.lang.Math.*; //Math.PI Math.sin(double radians) Math.cos(double radians) all of type double

/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

   (MIT LICENSE ) e.g do what you want with this :-) 
 */ 

//Singeton pattern
	
public class Controller implements KeyListener, MouseListener, MouseMotionListener  {

	private static boolean KeyAPressed= false;
	private static boolean KeySPressed= false;
	private static boolean KeyDPressed= false;
	private static boolean KeyWPressed= false;
	private static boolean KeySpacePressed= false;
	private static boolean KeyXPressed = false; //shortcut for game over
	private static boolean KeyZPressed = false; //shortcut for game won
	private static boolean mouseClicked = false;
	public static int mouseX = 0;
	public static int mouseY = 0;
	public static int mouseClicks = 0;

	private static final Controller instance = new Controller();



	public Controller() {

	}


	public static Controller getInstance(){
		return instance;
	}

	@Override
	// Key pressed , will keep triggering 
	public void keyTyped(KeyEvent e) { 

	}

	//to take in inputs
	@Override
	public void keyPressed(KeyEvent e) 
	{ 
		switch (e.getKeyChar()) 
		{
		case 'a':setKeyAPressed(true);break;  
		case 's':setKeySPressed(true);break;
		case 'w':setKeyWPressed(true);break;
		case 'd':setKeyDPressed(true);break;
		case 'x':setKeyXPressed(true);break;
		case 'z':setKeyZPressed(true);break;
		case ' ':setKeySpacePressed(true);break;   
		default:
			//System.out.println("Controller test:  Unknown key pressed");
			break;
		}  

		// You can implement to keep moving while pressing the key here . 

	}

	@Override
	public void keyReleased(KeyEvent e) 
	{ 
		switch (e.getKeyChar()) 
		{
		case 'a':setKeyAPressed(false);break;  
		case 's':setKeySPressed(false);break;
		case 'w':setKeyWPressed(false);break;
		case 'd':setKeyDPressed(false);break;
		case 'x':setKeyXPressed(false);break;
		case 'z':setKeyZPressed(false);break;
		case ' ':setKeySpacePressed(false);break;   
		default:
			//System.out.println("Controller test:  Unknown key pressed");
			break;
		}  
		//upper case 

	}


	public boolean isKeyAPressed() {
		return KeyAPressed;
	}


	public void setKeyAPressed(boolean keyAPressed) {
		KeyAPressed = keyAPressed;
	}


	public boolean isKeySPressed() {
		return KeySPressed;
	}


	public void setKeySPressed(boolean keySPressed) {
		KeySPressed = keySPressed;
	}


	public boolean isKeyDPressed() {
		return KeyDPressed;
	}


	public void setKeyDPressed(boolean keyDPressed) {
		KeyDPressed = keyDPressed;
	}


	public boolean isKeyWPressed() {
		return KeyWPressed;
	}


	public void setKeyWPressed(boolean keyWPressed) {
		KeyWPressed = keyWPressed;
	}

	public void setKeyXPressed(boolean keyXPressed) {
		KeyXPressed = keyXPressed;
	}
	
	public boolean isKeyXPressed() {
		return KeyXPressed;
	}
	
	public void setKeyZPressed(boolean keyZPressed) {
		KeyZPressed = keyZPressed;
	}
	
	public boolean isKeyZPressed() {
		return KeyZPressed;
	}


	public boolean isKeySpacePressed() {
		return KeySpacePressed;
	}

	


	public void setKeySpacePressed(boolean keySpacePressed) {
		KeySpacePressed = keySpacePressed;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.mouseClicks += 1;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.setMousePressed(true);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.setMousePressed(false);

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void setMousePressed(boolean mouseStatus) {
		mouseClicked = mouseStatus;
	}
	
	public boolean getMousePressed() {
		return mouseClicked;
	}
	


	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	public int getMouseClicks() {
		return Controller.mouseClicks;
	}
	
	

}

/*
 * 
 * KEYBOARD :-) . can you add a mouse or a gamepad 

 *@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ @@@@@@@@@@@@@@@

  @@@     @@@@    @@@@    @@@@    @@@@     @@@     @@@     @@@     @@@     @@@  

  @@@     @@@     @@@     @@@@     @@@     @@@     @@@     @@@     @@@     @@@  

  @@@     @@@     @@@     @@@@    @@@@     @@@     @@@     @@@     @@@     @@@  

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@     @@@     @@@     @@@      @@      @@@     @@@     @@@     @@@     @@@     @

@     @@@   W   @@@     @@@      @@      @@@     @@@     @@@     @@@     @@@     @

@@    @@@@     @@@@    @@@@    @@@@    @@@@     @@@     @@@     @@@     @@@     @

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@N@@@@@@@@@@@@@@@@@@@@@@@@@@@

@@@     @@@      @@      @@      @@      @@@     @@@     @@@     @@@     @@@    

@@@   A   @@@  S     @@  D     @@      @@@     @@@     @@@     @@@     @@@     @@@    

@@@@ @  @@@@@@@@@@@@ @@@@@@@    @@@@@@@@@@@@    @@@@@@@@@@@@     @@@@   @@@@@   

    @@@     @@@@    @@@@    @@@@    $@@@     @@@     @@@     @@@     @@@     @@@

    @@@ $   @@@      @@      @@ /Q   @@ ]M   @@@     @@@     @@@     @@@     @@@

    @@@     @@@      @@      @@      @@      @@@     @@@     @@@     @@@     @@@

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@       @@@                                                @@@       @@@       @

@       @@@              SPACE KEY       @@@        @@ PQ     

@       @@@                                                @@@        @@        

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * 
 * 
 * 
 * 
 * 
 */
