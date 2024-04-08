package app.persistence;

import app.entities.Bund;
import app.exceptions.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class BundMapper {
    public static List<Bund> getBunds(ConnectionPool connectionPool) throws DatabaseException {
        List<Bund> bundList = new ArrayList<>();
        String sql = "SELECT bund_id, bund_name, bund_price FROM public.bund";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                int bundID = rs.getInt("bund_id");
                String bundName = rs.getString("bund_name");
                double bundPrice = rs.getDouble("bund_price");
                bundList.add(new Bund(bundID, bundName, bundPrice));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving bunds: " + e.getMessage());
        }
        return bundList;
    }

    public static Bund getBundsById(int bottomId, ConnectionPool connectionPool) throws DatabaseException {
        Bund bund = null;
        String sql = "SELECT bund_id, bund_name, bund_price FROM public.bund WHERE bund_id = ?";



        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);

        ) {
            ps.setInt(1, bottomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int bundID = rs.getInt("bund_id");
                String bundName = rs.getString("bund_name");
                double bundPrice = rs.getDouble("bund_price");
                bund = new Bund(bundID, bundName, bundPrice);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving bunds: " + e.getMessage());
        }
        return bund;
    }
}