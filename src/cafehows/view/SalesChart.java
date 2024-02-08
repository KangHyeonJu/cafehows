//package cafehows.view;
//
//import java.awt.*;
//import java.awt.event.*;
//import java.util.ArrayList;
//
//import javax.swing.*;
//
//import cafehows.model.CafeDAO;
//import cafehows.model.MenuDTO;
//
//public class SalesChart extends JFrame{
//	
//	private ArrayList<MenuDTO> menuList= new ArrayList<>();
//	//private ArrayList<Double> ratioList= new ArrayList<>();
//	//private ArrayList<Double> angleList= new ArrayList<>();
//	double[] ratioList= new double[5]; 
//	double[] angleList= new double[5]; 
//	
//	
//    public void paint(Graphics g){
//    	getRatio();
//    	getAngle();
//        g.setColor(Color.yellow);
//        g.fillArc(100,100,200,200,0,(int)angleList[0]);
//        g.setColor(Color.blue);
//        g.fillArc(100,100,200,200,(int)angleList[0],(int)angleList[1]);
//        g.setColor(Color.green);
//        g.fillArc(100,100,200,200,(int)(angleList[0]+angleList[1]),(int)angleList[2]);
//        g.setColor(Color.red);
//        g.fillArc(100,100,200,200,(int)(angleList[0]+angleList[1]+angleList[2]),(int)angleList[3]);
//        g.setColor(Color.orange);
//        g.fillArc(100,100,200,200,(int)(angleList[0]+angleList[1]+angleList[2]+angleList[3]),(int)angleList[4]);
//
//    }
//    
//    public SalesChart(){
//    	for(MenuDTO dto :CafeDAO.getInstance().getMenuSales() )
//    	{
//    		menuList.add(dto);
//    	}
//    	
//    }
//    public double[] getRatio(){
//    	int numWhole = 0;
//    	int count=0;
//    	for(MenuDTO dto : menuList) {
//    		numWhole +=dto.getCumCount(); 
//    		count++;
//    		if(count==5) break;
//    	}
//    	count=0;
//    	for(MenuDTO dto : menuList) {
//    		int numPart = dto.getCumCount();
//    		ratioList[count]=(((double)numPart/(double)numWhole)*100);
//    		count++;
//    		if(count==5) break;
//    	}
//      
//        return ratioList;
//    }
//    public double[] getAngle(){
//    	for(int i=0;i<5;i++) {
//        double angle =3.6*ratioList[i];
//        angleList[i]=angle;
//        }
//        return angleList;
//    }
//
//}
