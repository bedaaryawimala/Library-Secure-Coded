package Controller;
import Dao.BukuDAOE;
import java.util.List;
import Model.BukuE;

public class BukuControllerE {
    BukuDAOE bDao = new BukuDAOE();


    public List<BukuE> showListBuku(){
        List<BukuE> dataBuku = bDao.showForDropdown();
        return dataBuku;
    }

    public List<BukuE> showDataForDropdown() {
        return showListBuku();
    }
}
