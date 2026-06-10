package Controller;
import Model.Buku;
import Dao.BukuDAO;
import java.util.List;
/**
 *
 * @author Beda Arya Wimala - 230712345
 */
public class BukuController {
    BukuDAO bDao = new BukuDAO();

    public String generateId() {
        return "B" + bDao.generateId();
    }

    public List<Buku> showDataBuku(String jenis) {
        return bDao.showData(jenis);
    }

    public Buku searchDataBuku(String id) {
        return bDao.search(id);
    }

    public void insertDataBuku(Buku buku) {
        bDao.insert(buku);
    }

    public void updateDataBuku(Buku buku, String id) {
        bDao.update(buku, id);
    }

    public void deleteDataBuku(String id) {
        bDao.delete(id);
    }
}
