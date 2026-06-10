package Controller;
import Dao.NovelDAO;
import java.util.List;
import Model.Novel;
import Model.Buku;
/**
 *
 * @author Beda Arya Wimala - 230712345
 */
public class NovelController {
    NovelDAO nDao = new NovelDAO();

    public void insertDataBuku(Novel nvl) {
        nvl.setId_buku("B" + nDao.generateId());
        nDao.insert(nvl);
    }

    public void insertDataNovel(Novel nvl) {
        nDao.insert(nvl);
    }

    public String showStringBuku() {
        List<Buku> listB = nDao.showData("novel");
        String bukuString = "";

        for(Buku b : listB) {
            bukuString += b.getString() + "\n";
        }
        return bukuString;
    }

    public void updateDataBuku(Novel nvl) {
        nDao.update(nvl, nvl.getId_buku(), nvl.getCover());
    }

    public void updateDataNovel(Buku buku, String idBuku, String cover) {
        nDao.update(buku, idBuku, cover);
    }

    public void deleteRoleNovel(String idBuku) {
        nDao.deleteoldRole(idBuku);
    }
}
