package vistasProvisionales;

import java.util.List;
import modelo.Pagodesalario;
import recursos.MaestroResource;


/**
 *
 * @author macbookpro
 */
public class AlumnosView {
    public static void main(String[] args){
        
        MaestroResource mResource = new MaestroResource();
        List<Pagodesalario> pagos = mResource.getPagosDeSalario(1);
        for(Pagodesalario e : pagos){
            System.out.println(e.toString());
        }
    }
}
