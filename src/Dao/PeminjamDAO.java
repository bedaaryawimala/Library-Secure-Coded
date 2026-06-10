package Dao;
import Connection.DBConnection;
import InterfaceDao.IDAO;
import java.sql.*;
import java.util.*;
import Model.Peminjam;
/**
 *
 * @author Beda Arya Wimala
 */
public class PeminjamDAO implements IDAO<Peminjam, Integer>{
    protected DBConnection dbCon = new DBConnection();

    @Override
    public void insert(Peminjam p) {
        String sql = "INSERT INTO `peminjam` (`nama`, `umur`, `notelp`) VALUES (?, ?, ?)";

        System.out.println("Adding peminjam...");
        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, p.getNama());
            statement.setInt(2, p.getUmur());
            statement.setString(3, p.getNotelp());
            int result = statement.executeUpdate();
            System.out.println("Added " + result + " peminjam");
        } catch (Exception e) {
            System.out.println("Error adding peminjam...");
        }
    }

    @Override
    public Peminjam search(Integer id_peminjam) {
        String sql = "SELECT * FROM peminjam WHERE id_peminjam = ?";
        System.out.println("Searching peminjam...");
        Peminjam p = null;

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, id_peminjam);
            ResultSet rs = statement.executeQuery();

            if(rs!=null){
                while (rs.next()) {
                    p = new Peminjam(rs.getInt("id_peminjam")
                            , rs.getString("nama")
                            , rs.getInt("umur")
                            , rs.getString("notelp"));
                }
                rs.close();
            }

        } catch (Exception e) {
            System.out.println("Error Fetching data...");
        }
        return p;
    }

    @Override
    public List<Peminjam> showData(Integer data) {
        String sql = "SELECT * FROM peminjam";
        System.out.println("Fetching Data...");
        List<Peminjam> p = new ArrayList<>();

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if(rs!=null)
            {
                while (rs.next()) {
                    p.add(new Peminjam(rs.getInt("id_peminjam")
                            ,rs.getString("nama")
                            ,rs.getInt("umur")
                            , rs.getString("notelp")));
                }
            }

        } catch (Exception e) {
            System.out.println("Error Fetching data...");
        }
        return p;
    }

    @Override
    public void update(Peminjam p, Integer id_peminjam) {
        String sql = "UPDATE `peminjam` SET `nama` = ?, `umur` = ?, `notelp` = ? WHERE `id_peminjam` = ?";

        System.out.println("Updating peminjam");
        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, p.getNama());
            statement.setInt(2, p.getUmur());
            statement.setString(3, p.getNotelp());
            statement.setInt(4, id_peminjam);
            int result = statement.executeUpdate();
            System.out.println("Edited " + result + " peminjam " + id_peminjam);
        } catch (Exception e) {
            System.out.println("Error Updating peminjam...");
        }
    }

    @Override
    public void delete(Integer id_peminjam) {
        String sql = "DELETE FROM `peminjam` WHERE `id_peminjam` = ?";
        System.out.println("Deleting peminjam...");

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, id_peminjam);
            int result = statement.executeUpdate();
            System.out.println("Edited " + result + " peminjam " + id_peminjam);
        } catch (Exception e) {
            System.out.println("Error Updating peminjam...");
        }
    }
}
