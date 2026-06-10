package Controller;
import Dao.PeminjamDAOE;
import Model.PeminjamE;
import java.util.List;
/**
 *
 * @author Beda Arya Wimala - 230712345
 */
public class PeminjamControllerE extends Controller.PeminjamController{
    PeminjamDAOE pDaoe = new PeminjamDAOE();

    public List<PeminjamE> showListPeminjam()
    {
        List<PeminjamE> dataCustomer = pDaoe.showForDropdown();
        return dataCustomer;
    }

    public List<PeminjamE> showDataForDropdown() {
        return showListPeminjam();
    }
}
