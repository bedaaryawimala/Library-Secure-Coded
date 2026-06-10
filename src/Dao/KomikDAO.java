package Dao;
import InterfaceDao.IBukuDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import Model.Buku;
import Model.Komik;
/**
 *
 * @author Beda Arya Wimala - 230712345
 */
public class KomikDAO extends BukuDAO implements IBukuDAO{
    public void insert(Komik Ko) {
        super.insert(Ko);
        insertNewRole(Ko);
    }

    public void insertNewRole(Komik K) {
        String sql = "INSERT INTO `komik`(`id_buku`, `ilustrator`) VALUES (?, ?)";

        System.out.println("Adding buku...");

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, K.getId_buku());
            statement.setString(2, K.getIlustrator());
            int result = statement.executeUpdate();
            System.out.println("Added " + result + " buku");
        } catch (Exception e) {
            System.out.println("Error adding buku...");
        }
    }

    @Override
    public void deleteoldRole(String id_buku) {
        String sql = "DELETE FROM `komik` WHERE `id_buku` = ?";
        System.out.println("Deleting komik...");

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, id_buku);
            int result = statement.executeUpdate();
            System.out.println("Edited " + result + " buku " + id_buku);
        } catch(Exception e) {
            System.out.println("Error Updating buku...");
        }
    }

    public void updateRole(Buku b, String id_buku) {
        String sql = "UPDATE `komik` SET `ilustrator` = ? WHERE `id_buku` = ?";

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

    public void update(Buku b, String id_buku, String ilustrator) {
        Komik k = new Komik(ilustrator, b.getId_buku(), b.getJudul(), b.getJenis(), b.getTahun_terbit());
        if(cekPerubahanJenis("komik", id_buku)) {
            deleteoldRole(id_buku);
            insertNewRole(k);
        } else {
            updateRole(k , id_buku);
        }
        super.update(k, id_buku);
    }
}
