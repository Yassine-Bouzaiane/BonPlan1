/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Config.Connexion;
import Entite.Categorie;
import Entite.Etablissement;
import Entite.Budget;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;


/**
 *
 * @author user
 */
public class ServiceEtablissement implements IServices.IServiceEtablissement{
     
    Connection con ;

    public ServiceEtablissement() {
        con = Connexion.getInstance().getCon();
    }
    
    
    

    @Override
    public void ajouterEtablissement(Etablissement e) {
        String sql = "INSERT INTO etablissement(nom_etablissement,adresse_etablissement,telephone_etablissement,horaire_travail,description_etablissement,photo_etablissement,photo_patente,code_postal"
                + ",position,budget,site_web,id_categorie,id,enabled,longitude,latitude) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm;
        try {
            stm = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1,e.getNom_etablissement());
            stm.setString(2,e.getAdresse_etablissement());
            stm.setInt(3,e.getTelephone_etablissement());
            stm.setString(4,e.getHoraire_travail());
            stm.setString(5,e.getDescription_etablissement());
            stm.setString(6,e.getPhoto_etablissement());
            stm.setString(7,e.getPhoto_patente());
            stm.setInt(8,e.getCode_postal());
            stm.setString(9,e.getPosition());
            stm.setString(10,e.getBudget().getName());
            stm.setString(11,e.getSite_web());
            
            ServiceCategorie sc = new ServiceCategorie();
          //  stm.setInt(12, 2);
    stm.setInt(12,sc.afficherCategorie(e.getCategorie().getId_categorie()).getId_categorie());

        stm.setInt(13,1);
//           e.getUtilisateur().getId_user();
stm.setInt(14, e.getEnabled());
//            int res = stm.executeUpdate();
stm.setString(15, String.valueOf(e.getLong()));
stm.setString(16, String.valueOf(e.getLat()));

            stm.executeUpdate();
            ResultSet reset = stm.getGeneratedKeys();
            int id=0;
            while(reset.next()){
                id = reset.getInt(1);
                e.setId_etablissement(id);
                System.out.println("Add Done etablissement "+id);
            
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    @Override
    public void modifierEtablissement(Etablissement e) {
        try
        {
        String update = "UPDATE etablissement SET  nom_etablissement = ? , adresse_etablissement = ? , telephone_etablissement = ? , horaire_travail = ? , "
                + "description_etablissement = ? , photo_etablissement = ? ,photo_patente = ? , code_postal = ? , position = ? , budget = ? ,site_web = ? , id_categorie = ? , id = ?  "
                + "WHERE id_etablissement = ?";
        PreparedStatement statement2 = con.prepareStatement(update);
        
        statement2.setString(1, e.getNom_etablissement());
        statement2.setString(2, e.getAdresse_etablissement());      
        statement2.setInt(3, e.getTelephone_etablissement());
        statement2.setString(4, e.getHoraire_travail());
        statement2.setString(5, e.getDescription_etablissement());
        statement2.setString(6, e.getPhoto_etablissement());
        statement2.setString(7, e.getPhoto_patente());
        statement2.setInt(8, e.getCode_postal());
        statement2.setString(9, e.getPosition());
        statement2.setString(10, e.getBudget().toString());
        statement2.setString(11,e.getSite_web());
     //   statement2.setInt(12, e.getCategorie().getId_categorie());
       // statement2.setInt(13, e.getUtilisateur().getId_user());
ServiceCategorie sc = new ServiceCategorie();
statement2.setInt(12,sc.afficherCategorie(e.getCategorie().getId_categorie()).getId_categorie());

//  statement2.setInt(12, 2);
       statement2.setInt(13,1);
       statement2.setInt(14,21);
     //  statement2.setInt(14, e.getId_etablissement());
        
        
        statement2.executeUpdate();
        System.out.println(""+e.getNom_etablissement()+" modifiee !!!");
        
        }
        catch(SQLException ex)
                {
                    System.out.println(ex.getMessage());
                    System.err.println(""+e.getNom_etablissement()+" non modifiee");
                }
    }

    @Override
    public void supprimerEtablissement(Etablissement e) {
        
        try 
        {
        String delete = "DELETE FROM etablissement WHERE id_etablissement=?";
        PreparedStatement st2 = con.prepareStatement(delete);
        st2.setInt(1,e.getId_etablissement());
            System.out.println(e.getId_etablissement());
        st2.executeUpdate();
        System.out.println(""+e.getId_etablissement()+" supprimee !!!");
        
        }
        catch (SQLException ex)
        {

                    System.err.println("SQLException: "+ex.getMessage());
                           } //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Etablissement chercherEtablissement(int id) {
         Etablissement e = new Etablissement();
        try
        {
        String select = "SELECT * FROM etablissement WHERE id_etablissement = '"+id+"' ";
        Statement statement1 = con.createStatement();
        ResultSet result = statement1.executeQuery(select);
       
        while (result.next()) 
        {  e.setId_etablissement(result.getInt("id_etablissement"));
            e.setNom_etablissement(result.getString("nom_etablissement"));
            e.setAdresse_etablissement(result.getString("adresse_etablissement"));  
            e.setTelephone_etablissement(result.getInt("telephone_etablissement"));
            e.setHoraire_travail(result.getString("horaire_travail"));
            e.setDescription_etablissement(result.getString("description_etablissement"));
            e.setPhoto_etablissement(result.getString("photo_etablissement"));
            e.setPhoto_patente(result.getString("photo_patente"));
            e.setCode_postal(result.getInt("code_postal"));
            e.setPosition(result.getString("position"));
            e.setSite_web(result.getString("site_web"));
            e.setBudget(Budget.valueOf(result.getString("budget")));
            ServiceCategorie sc= new ServiceCategorie();
           e.setCategorie(sc.afficherCategorie(result.getInt("id_categorie")));
         //  e.getUtilisateur().setId_user(result.getInt("id"));
            }
        }
        catch (SQLException ex)
                {
                    System.err.println("SQLException: "+ex.getMessage());
                    System.err.println("SQLSTATE: "+ex.getSQLState());
                    System.err.println("VnedorError: "+ex.getErrorCode());
                }
        return e; //To change body of generated methods, choose Tools | Templates.
    }
@Override
    public Etablissement chercherEtablissementParNom(String nom) {
         Etablissement e = new Etablissement();
        try
        {
        String select = "SELECT * FROM etablissement WHERE nom_etablissement = '"+nom+"' ";
        Statement statement1 = con.createStatement();
        ResultSet result = statement1.executeQuery(select);
       
        while (result.next()) 
        {  e.setId_etablissement(result.getInt("id_etablissement"));
            e.setNom_etablissement(result.getString("nom_etablissement"));
            e.setAdresse_etablissement(result.getString("adresse_etablissement"));  
            e.setTelephone_etablissement(result.getInt("telephone_etablissement"));
            e.setHoraire_travail(result.getString("horaire_travail"));
            e.setDescription_etablissement(result.getString("description_etablissement"));
            e.setPhoto_etablissement(result.getString("photo_etablissement"));
            e.setPhoto_patente(result.getString("photo_patente"));
            e.setCode_postal(result.getInt("code_postal"));
            e.setPosition(result.getString("position"));
            e.setSite_web(result.getString("site_web"));
            e.setLong(result.getDouble("longitude"));
            e.setLat(result.getDouble("latitude"));
            e.setBudget(Budget.valueOf(result.getString("budget")));                 
            ServiceCategorie sc = new ServiceCategorie();
            e.setCategorie(sc.afficherCategorie(result.getInt("id_categorie")));
            
            
//            e.getUtilisateur().setId_user(result.getInt("id"));
            }
        }
        catch (SQLException ex)
                {
                    System.err.println("SQLException: "+ex.getMessage());
                    System.err.println("SQLSTATE: "+ex.getSQLState());
                    System.err.println("VnedorError: "+ex.getErrorCode());
                }
        return e; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public List<Etablissement> listEtablissement() {
        
           
        List<Etablissement> le=new ArrayList<>();
        try 
        {
        Statement stm = Connexion.getInstance().getCon().createStatement();
            ResultSet result= 
                    stm.executeQuery("select * from `etablissement` where enabled=1 ");
            while(result.next()){
            Etablissement e = new Etablissement();
            e.setId_etablissement(result.getInt("id_etablissement"));
            e.setNom_etablissement(result.getString("nom_etablissement"));
            e.setAdresse_etablissement(result.getString("adresse_etablissement"));  
            e.setTelephone_etablissement(result.getInt("telephone_etablissement"));
            e.setHoraire_travail(result.getString("horaire_travail"));
            e.setDescription_etablissement(result.getString("description_etablissement"));
            e.setPhoto_etablissement(result.getString("photo_etablissement"));
            e.setPhoto_patente(result.getString("photo_patente"));
            e.setCode_postal(result.getInt("code_postal"));
            e.setPosition(result.getString("position"));
            e.setBudget(Budget.valueOf(result.getString("budget")));
            e.setSite_web(result.getString("site_web"));
            e.setLat(result.getDouble("latitude"));
            e.setLong(result.getDouble("longitude"));
            ServiceCategorie sc = new ServiceCategorie();
            e.setCategorie(sc.afficherCategorie(result.getInt("id_categorie")));
       
//            e.getUtilisateur().setId_user(result.getInt("id"));
            
            
            le.add(e);

        } 
    }   
        catch (SQLException ex)
                {
                    System.err.println("SQLException: "+ex.getMessage());
                    System.err.println("SQLSTATE: "+ex.getSQLState());
                    System.err.println("VnedorError: "+ex.getErrorCode());
                }
        return le;
} //To change body of generated methods, choose Tools | Templates.

   
    @Override
    public List<Etablissement> listDemandesEtablissement() {
        
           
        List<Etablissement> le=new ArrayList<>();
        try 
        {
        Statement stm = Connexion.getInstance().getCon().createStatement();
            ResultSet result= 
                    stm.executeQuery("select * from `etablissement` where enabled=0 ");
            while(result.next()){
            Etablissement e = new Etablissement();
            e.setId_etablissement(result.getInt("id_etablissement"));
            e.setNom_etablissement(result.getString("nom_etablissement"));
            e.setAdresse_etablissement(result.getString("adresse_etablissement"));  
            e.setTelephone_etablissement(result.getInt("telephone_etablissement"));
            e.setHoraire_travail(result.getString("horaire_travail"));
            e.setDescription_etablissement(result.getString("description_etablissement"));
            e.setPhoto_etablissement(result.getString("photo_etablissement"));
            e.setPhoto_patente(result.getString("photo_patente"));
            e.setCode_postal(result.getInt("code_postal"));
            e.setPosition(result.getString("position"));
            e.setBudget(Budget.valueOf(result.getString("budget")));
            e.setSite_web(result.getString("site_web"));
         
            //ServiceCategorie sc = new ServiceCategorie();
            //e.setCategorie(sc.afficherCategorie(result.getInt("id_categorie")));
            
            
//            e.getUtilisateur().setId_user(result.getInt("id"));
            
            e.setEnabled(0);
            le.add(e);

        } 
    }   
        catch (SQLException ex)
                {
                    System.err.println("SQLException: "+ex.getMessage());
                    System.err.println("SQLSTATE: "+ex.getSQLState());
                    System.err.println("VnedorError: "+ex.getErrorCode());
                }
        return le;
}  
    public ObservableList<PieChart.Data> StatEtablissementParCategorie() {
        ArrayList<PieChart.Data> list = new ArrayList<PieChart.Data>();
        try {
            PreparedStatement st = con.prepareStatement("SELECT r.nom_categorie, count( id_etablissement)  from etablissement e , categorie r where r.id_categorie=e.id_categorie and r.enabled=1 GROUP by r.id_categorie");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new PieChart.Data(rs.getString(1), rs.getInt(2)));
            }
            ObservableList<PieChart.Data> observableList;
            observableList = FXCollections.observableList(list);
            //System.out.println("ici" + observableList.size());
            return observableList;

        } catch (SQLException ex) {
            Logger.getLogger(ServiceEtablissement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
   
}
