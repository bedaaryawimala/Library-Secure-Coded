package Controller;

import Dao.PeminjamanDAO;
import java.util.List;
import Model.Peminjaman;
import Table.TablePeminjaman;

public class PeminjamanController {
    private PeminjamanDAO pjDao = new PeminjamanDAO();

    public void insertDataPeminjaman(Peminjaman pj){
        pjDao.insert(pj);
    }

    public TablePeminjaman showTable(String target){
        List<Peminjaman> dataPeminjaman = pjDao.showData(target);
        TablePeminjaman tablePeminjaman = new TablePeminjaman(dataPeminjaman);

        for (Peminjaman peminjaman : dataPeminjaman) {
            System.out.println(peminjaman.getPeminjamE().getNama());
        }

        return tablePeminjaman;
    }

    public List<Peminjaman> showDataPeminjaman(String target) {
        return pjDao.showData(target);
    }

    public void updatePeminjaman(Peminjaman pj, int id){
        pjDao.update(pj, Integer.toString(id));
    }

    public void updateDataPeminjaman(Peminjaman pj, String id) {
        pjDao.update(pj, id);
    }

    public void deletePeminjaman(int id){
        pjDao.delete(Integer.toString(id));
    }

    public void deleteDataPeminjaman(String id) {
        pjDao.delete(id);
    }
}
