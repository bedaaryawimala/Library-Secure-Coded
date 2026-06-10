package Dao;
import Model.PeminjamE;
import InterfaceDao.IShowForDropdown;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Beda Arya Wimala - 230712345
 */
public class PeminjamDAOE extends Dao.PeminjamDAO implements IShowForDropdown<PeminjamE> {

    @Override
    public List<PeminjamE> showForDropdown()
    {
        String sql = "SELECT * FROM peminjam";
        System.out.println("Fetching Data...");
        List<PeminjamE> c = new ArrayList<>();

        try (Connection con = dbCon.makeConnection();
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if(rs != null)
            {
                while(rs.next())
                {
                    c.add(new PeminjamE(
                            rs.getInt("id_peminjam"),
                            rs.getString("nama"),
                            rs.getInt("umur"),
                            rs.getString("notelp")));
                }
            }
        }catch(Exception e){
            System.out.println("Error Fetching data...");
        }
        return c;
    }
}
