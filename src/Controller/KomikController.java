package Controller;
import Dao.KomikDAO;
import java.util.List;
import Model.Komik;
import Model.Buku;
/**
 *
 * @author Beda Arya Wimala - 230712345
 */
public class KomikController {
    KomikDAO kDao = new KomikDAO();

    public void insertDataBuku(Komik kmk) {
        kmk.setId_buku("B" + kDao.generateId());
        kDao.insert(kmk);
    }

    public void insertDataKomik(Komik kmk) {
        kDao.insert(kmk);
    }

    public String showStringBuku() {
        List<Buku> listB = kDao.showData("komik");
        String bukuString = "";

        for(Buku b : listB) {
            bukuString += b.getString() + "\n";
        }
        return bukuString;
    }

    public void updateDataBuku(Komik K) {
        System.out.println(K.getIlustrator());
        kDao.update(K, K.getId_buku(), K.getIlustrator());
    }

    public void updateDataKomik(Buku buku, String idBuku, String ilustrator) {
        kDao.update(buku, idBuku, ilustrator);
    }

    public void deleteRoleKomik(String idBuku) {
        kDao.deleteoldRole(idBuku);
    }
}
