package Dao;
import Connection.DBConnection;
import InterfaceDao.IDAO;
import java.sql.*;
import java.util.*;
import Model.Buku;
import Model.Komik;
import Model.Novel;
/**
 *
 * @author Beda Arya Wimala 230712345
 */
public class BukuDAO implements IDAO<Buku, String> {
    protected DBConnection dbCon = new DBConnection();

    @Override
    public void insert(Buku b) {
        String sql = "INSERT INTO `buku`(`id_buku`, `judul`, `jenis`, `tahun_terbit`) "
                + "VALUES (?, ?, ?, ?)";

        System.out.println("Adding buku...");

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, b.getId_buku());
            statement.setString(2, b.getJudul());
            statement.setString(3, b.getJenis());
            statement.setInt(4, b.getTahun_terbit());
            int result = statement.executeUpdate();
            System.out.println("Added " + result + " buku");
        } catch (Exception e) {
            System.out.println("Error adding buku...");
        }
    }

    @Override
    public Buku search(String id_buku) {
        String sql = "SELECT buku.*, komik.ilustrator, novel.cover FROM buku\n" +
                "LEFT JOIN komik ON buku.id_buku = komik.id_buku\n" +
                "LEFT JOIN novel ON buku.id_buku = novel.id_buku\n" +
                "WHERE buku.id_buku = ?";

        System.out.println("Searching Kendaraan...");
        Buku b = null;

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, id_buku);
            ResultSet rs = statement.executeQuery();

            if(rs != null) {
                while(rs.next()) {
                    if(rs.getString("jenis").equals("komik")) {
                        b = new Komik(
                                rs.getString("ilustrator"),
                                rs.getString("id_buku"),
                                rs.getString("judul"),
                                rs.getString("jenis"),
                                rs.getInt("tahun_terbit"));
                    } else {
                        b = new Novel(
                                rs.getString("cover"),
                                rs.getString("id_buku"),
                                rs.getString("judul"),
                                rs.getString("jenis"),
                                rs.getInt("tahun_terbit"));
                    }
                }

            }
            rs.close();
        } catch(Exception e) {
            System.out.println("Error Fetching data...");
        }
        return b;
    }

    @Override
    public List<Buku> showData(String jenis) {
        String sql = "SELECT buku.*, komik.ilustrator, novel.cover FROM buku\n" +
                "LEFT JOIN komik ON buku.id_buku = komik.id_buku\n" +
                "LEFT JOIN novel ON buku.id_buku = novel.id_buku\n" +
                "WHERE buku.jenis = ?";

        System.out.println("Fetching Data...");
        List<Buku> list = new ArrayList<>();

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, jenis);
            ResultSet rs = statement.executeQuery();
            Buku b = null;

            if(rs != null) {
                while(rs.next()) {
                    if(rs.getString("jenis").equals("komik")) {
                        b = new Komik(
                                rs.getString("ilustrator"),
                                rs.getString("id_buku"),
                                rs.getString("judul"),
                                rs.getString("jenis"),
                                rs.getInt("tahun_terbit"));
                    } else {
                        b = new Novel(
                                rs.getString("cover"),
                                rs.getString("id_buku"),
                                rs.getString("judul"),
                                rs.getString("jenis"),
                                rs.getInt("tahun_terbit"));
                    }
                    list.add(b);
                }
            }
            rs.close();

        } catch(Exception e) {
            System.out.println("Error Fetching data...");
        }
        return list;
    }

    @Override
    public void update(Buku b, String id_buku) {
        String sql = "UPDATE `buku` SET " +
                "`id_buku`=?, `judul`=?, `jenis`=?, `tahun_terbit`=? " +
                "WHERE id_buku=?";

        System.out.println("Updating buku...");

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, b.getId_buku());
            statement.setString(2, b.getJudul());
            statement.setString(3, b.getJenis());
            statement.setInt(4, b.getTahun_terbit());
            statement.setString(5, id_buku);
            int result = statement.executeUpdate();
            System.out.println("Edited " + result + " Buku " + id_buku);
        } catch(Exception e) {
            System.out.println("Error Updating Buku...");
        }
    }

    @Override
    public void delete(String id_buku) {
        String sql = "DELETE FROM `buku` WHERE `id_buku` = ?";

        System.out.println("Deleting Buku...");

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, id_buku);
            int result = statement.executeUpdate();
            System.out.println("Edited " + result + " Buku " + id_buku);
        } catch(Exception e) {
            System.out.println("Error Updating Buku...");
        }
    }

    public int generateId() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_buku, 2) AS SIGNED)) AS highest_number FROM buku WHERE id_buku LIKE 'B%';";

        System.out.println("Generating Id...");
        int id = 0;

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if(rs != null && rs.next()) {
                if(!rs.wasNull()) {
                    id = rs.getInt("highest_number") + 1;
                }
            }
        } catch(Exception e) {
            System.out.println("Error Fetching data...");
        }
        return id;
    }

    public boolean cekPerubahanJenis(String jenis, String id_buku) {
        String sql = "SELECT jenis != ? as result " +
                "FROM `buku` " +
                "WHERE id_buku = ?";

        System.out.println("Checking Result...");
        boolean result = false;

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, jenis);
            statement.setString(2, id_buku);
            ResultSet rs = statement.executeQuery();

            if(rs != null) {
                while(rs.next()) {
                    result = rs.getBoolean("result");
                }
                rs.close();
            }
        } catch(Exception e) {
            System.out.println("Error Fetching data...");
        }
        System.out.println("The result is " + result);
        return result;
    }
}
