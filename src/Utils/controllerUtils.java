/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author Isaac
 */
public class controllerUtils{
    
     public void launchInterface(String path){
        
    }
    
    public static void launchTransition( Node b){
    ScaleTransition st = new ScaleTransition(Duration.seconds(2), b);
	st.setFromX(.8);
	st.setFromY(.8);
	st.setToX(1.6);
	st.setToY(1.6);
	st.play();
	st.setOnFinished(e -> {
		if(!b.isHover()) {
			ScaleTransition st2 = new ScaleTransition(Duration.seconds(1), b);
			st2.setToX(1);
			st2.setToY(1);
			st2.play();
		}
	});
	
	FadeTransition ft = new FadeTransition(Duration.seconds(1), b);
	ft.setFromValue(.2);
	ft.setToValue(1);
	ft.play();	
    
    }
}

