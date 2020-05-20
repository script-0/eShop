
import com.eshop.entities.Client;
import com.eshop.metier.EntitiesImpl.ClientImpl;
import com.eshop.metier.IClient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author TontonLaForce
 */
public class Projet_eShop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IClient metier = new ClientImpl();
//        metier.createClient("monthe", "brice", "monthebrice@gmail.com");
//        metier.createClient("monthe", "brice", "monthebrice@gma");
//        metier.updateClient(1, new Client("tontonlaforce" , "fulbert", "meonthe@gmail.com" , 132 , true));
//        metier.deleteClient(1);
//        metier.deleteClient(2);
        metier.readClient();
    }
    
}
