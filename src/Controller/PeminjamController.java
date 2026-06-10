package Controller;
import Model.Peminjam;
import Dao.PeminjamDAO;
import java.util.List;

/**
 *
 * @author Beda Arya Wimala - 230712345
 */
public class PeminjamController {
    PeminjamDAO pDao = new PeminjamDAO();

    public void insertDataPeminjam(Peminjam c) {
        pDao.insert(c);
    }

    public List<Peminjam> showDataPeminjam() {
        return pDao.showData(0);
    }

    public String showAllStringPeminjam() {
        List<Peminjam> listP = pDao.showData(0);
        String peminjamString = "";

        for(Peminjam p : listP) {
            peminjamString += p.getId_peminjam() + ". " + p.getNama() +
                    " | " + p.getUmur() + " | " + p.getNotelp() + "\n";
        }
        return peminjamString;
    }

    public Peminjam searchPeminjamById(int id_peminjam) {
        return pDao.search(id_peminjam);
    }

    public Peminjam searchDataPeminjam(int id_peminjam) {
        return searchPeminjamById(id_peminjam);
    }

    public void updateDataPeminjam(Peminjam p, int id_peminjam) {
        pDao.update(p, id_peminjam);
    }

    public void deleteDataPeminjam(int id_peminjam) {
        pDao.delete(id_peminjam);
    }

}
