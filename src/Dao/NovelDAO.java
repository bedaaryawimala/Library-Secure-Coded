package Dao;
import InterfaceDao.IBukuDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import Model.Buku;
import Model.Novel;
/**
 *
 * @author Beda Arya Wimala - 230712345
 */
public class NovelDAO extends BukuDAO implements IBukuDAO{

    public void insert(Novel nvl) {
        super.insert(nvl);
        insertNewRole(nvl);
    }

    public void insertNewRole(Novel nvl) {
        String sql = "INSERT INTO `novel`(`id_buku`, `cover`) VALUES (?, ?)";

        System.out.println("Adding buku...");

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, nvl.getId_buku());
            statement.setString(2, nvl.getCover());
            int result = statement.executeUpdate();
            System.out.println("Added " + result + " buku");
        } catch (Exception e) {
            System.out.println("Error adding buku...");
        }
    }

    public void updateRole(Buku b, String id_buku) {
        String sql = "UPDATE `novel` SET `cover` = ? WHERE `id_buku` = ?";

        System.out.println("Updating Jenis buku...");

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, b.getSpecial());
            statement.setString(2, id_buku);
            int result = statement.executeUpdate();
            System.out.println("Edited " + result + " buku " + id_buku);
        } catch(Exception e) {
            System.out.println("Error Updating buku...");
        }
    }

    @Override
    public void deleteoldRole(String id_buku) {
        String sql = "DELETE FROM `novel` WHERE `id_buku` = ?";
        System.out.println("Deleting novel...");

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, id_buku);
            int result = statement.executeUpdate();
            System.out.println("Edited " + result + " buku " + id_buku);
        } catch(Exception e) {
            System.out.println("Error Updating buku...");
        }
    }

    public void update(Buku b, String id_buku, String cover) {
        Novel n = new Novel(cover, b.getId_buku(), b.getJudul(), b.getJenis(), b.getTahun_terbit());
        if(cekPerubahanJenis("novel", id_buku)) {
            deleteoldRole(id_buku);
            insertNewRole(n);
        } else {
            updateRole(b, id_buku);
        }
        super.update(b, id_buku);
    }
}
