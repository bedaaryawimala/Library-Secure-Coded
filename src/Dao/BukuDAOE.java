package Dao;

import InterfaceDao.IShowForDropdown;
import Model.BukuE;
import Model.KomikE;
import Model.NovelE;
import java.util.*;
import java.sql.*;

/**
 *
 * @author Beda Arya Wimala - 230712345
 */
public class BukuDAOE extends Dao.BukuDAO implements IShowForDropdown<BukuE>{

    @Override
    public List<BukuE> showForDropdown()
    {
        String sql = "SELECT buku.*, komik.ilustrator, novel.cover FROM buku\n" +
                "LEFT JOIN komik ON buku.id_buku = komik.id_buku\n" +
                "LEFT JOIN novel ON buku.id_buku = novel.id_buku;";
        System.out.println("Fetching Data...");

        List<BukuE> list = new ArrayList<>();

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            BukuE b = null;

            if(rs!=null)
            {
                while(rs.next())
                {
                    if(rs.getString("jenis").equals("novel"))
                    {
                        b = new NovelE(
                                rs.getString("cover"),
                                rs.getString("id_buku"),
                                rs.getString("judul"),
                                rs.getString("jenis"),
                                rs.getInt("tahun_terbit"));
                    }else{
                        b = new KomikE(
                                rs.getString("id_buku"),
                                rs.getString("judul"),
                                rs.getString("jenis"),
                                rs.getInt("tahun_terbit"),
                                rs.getString("ilustrator"));
                    }
                    list.add(b);
                }
            }

        }catch(Exception e){
            System.out.println("Error Fetching data...");
        }
        return list;
    }
}
