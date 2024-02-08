package cafehows.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import cafehows.model.MenuDTO;

public class SalesChart extends JPanel{
	
	private ArrayList<MenuDTO> menuList= new ArrayList<>();
	private ArrayList<Double> ratioList= new ArrayList<>();
	private ArrayList<Double> angleList= new ArrayList<>();
	
    public void paint(Graphics g){
        g.setColor(Color.yellow);
        g.fillArc(100,100,200,200,0,Integer.parseInt(String.valueOf(Math.round(angleList.get(0)))));
        g.setColor(Color.blue);
        g.fillArc(100,100,200,200,angleList.get(0),angleList.get(1));
        g.setColor(Color.green);
        g.fillArc(100,100,200,200,120,160);
        g.setColor(Color.red);
        g.fillArc(100,100,200,200,280,0);
        g.setColor(Color.orange);
        g.fillArc(100,100,200,200,280,0);

    }
    
    public SalesChart(ArrayList<MenuDTO> menuList){
    	this.menuList =menuList;
    }
    public ArrayList<Double> getRatio(){
    	int numWhole = 0;
    	int count=0;
    	for(MenuDTO dto : menuList) {
    		numWhole +=dto.getCumCount(); 
    		count++;
    		if(count==5) break;
    	}
    	count=0;
    	for(MenuDTO dto : menuList) {
    		int numPart = dto.getCumCount();
    		ratioList.add ( ((double)numPart/(double)numWhole)*100);
    		if(count==5) break;
    	}
      
        return ratioList;
    }
    public ArrayList<Double> getAngle(){
    	for(int i=0;i<5;i++) {
        double angle =3.6*ratioList.get(i);
        angleList.add(angle);
        }
        return angleList;
    }

}
