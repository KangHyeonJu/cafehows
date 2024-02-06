package cafehows.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SalesChart extends JPanel{
	  int kor=50,eng=30,math=70;
      public void paint(Graphics g){
          g.clearRect(0,0, getWidth(),getHeight());
          g.drawLine(50,250,350,250);

          for(int i=1;i<11;i++){
              g.drawString(i*10+"",25,255-(20*i));
              g.drawLine(50,250-(20*i),350,250-(20*i));
          }
          g.drawLine(50,20,50,250);
          g.drawString("국어",100,270);
          g.drawString("영어",200,270);
          g.drawString("수학",300,270);
          g.setColor(Color.BLUE);

          if(kor>0) g.fillRect(110,250-kor*2,10,kor*2);
          if(eng>0) g.fillRect(210,250-eng*2,10,eng*2);
          if(math>0) g.fillRect(310,250-math*2,10,math*2);
      }
      void setScore(int kor,int eng,int math){
          this.kor  = kor;
          this.eng = eng;
          this.math = math;
      }
   

}
