package Dao;
import Connection.DBConnection;
import InterfaceDao.IDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model.*;
/**
 *
 * @author Beda Arya Wimala - 230712345
 */
public class PeminjamanDAO implements IDAO<Peminjaman, String>{
    protected DBConnection dbCon = new DBConnection();


    @Override
    public void insert(Peminjaman p)
    {
        String sql = "INSERT INTO `peminjaman` "
                + "(`id_peminjam`, `id_buku`, `tanggal_peminjaman`, `tanggal_pengembalian`, `wilayah`, `genre`)"
                + " VALUES "
                + "(?, ?, ?, ?, ?, ?)";
        System.out.println("Adding Peminjaman...");

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, p.getId_Peminjam());
            statement.setString(2, p.getId_Buku());
            statement.setString(3, p.getTanggal_meminjam());
            statement.setString(4, p.getTanggal_mengembalikan());
            statement.setString(5, p.getWilayah());
            statement.setString(6, p.getGenre());
            int result = statement.executeUpdate();
            System.out.println("Added " + result + " Peminjaman");
        }catch(Exception e){
            System.out.println("Error adding Peminjaman");
        }
    }

    @Override
    public List<Peminjaman> showData(String query){
        String sql = "SELECT PJ.id_peminjaman, PJ.id_peminjam, PJ.id_buku, PJ.tanggal_peminjaman, "
                + "PJ.tanggal_pengembalian, PJ.wilayah, PJ.genre, "
                + "P.nama, P.umur, P.notelp, "
                + "B.judul, B.jenis, B.tahun_terbit, K.ilustrator, N.cover "
                + "FROM peminjaman PJ "
                + "JOIN peminjam P ON PJ.id_peminjam = P.id_peminjam "
                + "JOIN buku B ON PJ.id_buku = B.id_buku "
                + "LEFT JOIN komik K ON B.id_buku = K.id_buku "
                + "LEFT JOIN novel N ON B.id_buku = N.id_buku "
                + "WHERE (P.nama LIKE ? OR B.judul LIKE ?)";

        System.out.println("Mengambil data PeminjamanBuku...");
        List<Peminjaman> listPeminjaman= new ArrayList<>();
        BukuE be = null;

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            String keyword = "%" + query + "%";
            statement.setString(1, keyword);
            statement.setString(2, keyword);
            ResultSet rs = statement.executeQuery();

            if(rs != null){
                while(rs.next()){
                    PeminjamE p = new PeminjamE(
                            rs.getInt("id_peminjam"),
                            rs.getString("nama"),
                            rs.getInt("umur"),
                            rs.getString("notelp")
                    );

                    if(rs.getString("jenis").equals("novel")){
                        be = new NovelE(
                                rs.getString("cover"),
                                rs.getString("id_buku"),
                                rs.getString("judul"),
                                rs.getString("jenis"),
                                rs.getInt("tahun_terbit"));
                    } else {
                        be = new KomikE(
                                rs.getString("id_buku"),
                                rs.getString("judul"),
                                rs.getString("jenis"),
                                rs.getInt("tahun_terbit"),
                                rs.getString("ilustrator"));
                    };

                    Peminjaman PJ = new Peminjaman(
                            rs.getInt("id_peminjaman"),
                            rs.getInt("id_peminjam"),
                            rs.getString("id_buku"),
                            rs.getString("genre"),
                            rs.getString("wilayah"),
                            rs.getString("tanggal_peminjaman"),
                            rs.getString("tanggal_pengembalian"),
                            p,
                            be
                    );

                    listPeminjaman.add(PJ);
                }
                rs.close();
            }

        }catch(Exception e){
            System.out.println("Error Fetching data...");
        }
        System.out.println("Berhasil");
        return listPeminjaman;
    }

    @Override
    public void update(Peminjaman pj, String id) {
        String sql = "UPDATE `peminjaman` SET "
                + "`id_peminjam`= ?, `id_buku`= ?, `genre`= ?, "
                + "`wilayah`= ?, `tanggal_peminjaman`= ?, "
                + "`tanggal_pengembalian` = ? "
                + "WHERE `id_peminjaman` = ?";

        System.out.println("Editing Peminjaman Buku...");

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, pj.getId_Peminjam());
            statement.setString(2, pj.getId_Buku());
            statement.setString(3, pj.getGenre());
            statement.setString(4, pj.getWilayah());
            statement.setString(5, pj.getTanggal_meminjam());
            statement.setString(6, pj.getTanggal_mengembalikan());
            statement.setString(7, id);
            int result = statement.executeUpdate();
            System.out.println("Edited " + result + " Peminjaman" + id);
        } catch (Exception e) {
            System.out.println("Error Updating Peminjaman...");
        }
    }

    @Override
    public void delete(String id){
        String sql = "DELETE FROM peminjaman "
                + "WHERE id_peminjaman = ?";

        System.out.println("Deleting PeminjamanBuku...");

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, id);
            int result = statement.executeUpdate();
            System.out.println("Delete" + result + " Peminjaman" + id);
        }catch(Exception e){
            System.out.println("Error Deleting Peminjaman...");
        }
    }

    @Override
    public Peminjaman search(String data){
        return null;
    }
}
