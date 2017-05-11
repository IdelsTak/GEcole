/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Assiste;
import ODB.OracleDBSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author DELL
 */
public class AssisteDAO implements DAO<Assiste> {
    
    private String            nomTable    = "ASSISTE"    ;
    private String            nomSequence = "SEQ_ID_AS" ;
    private String            requete     = ""         ;
    private Connection        session     = null       ;
    private PreparedStatement statement   = null       ;
    private ResultSet         resultat    = null       ;
    private boolean           valide      = false      ;
    private int               seq         =-1          ;
    
     public AssisteDAO(){
      session = OracleDBSingleton.getSession();
    }

    @Override
    public ObservableList<Assiste> getAll() {
         ObservableList<Assiste> liste = FXCollections.observableArrayList();
            try {
            requete = "SELECT * FROM ASSISTE" ;
            statement = session.prepareStatement(requete);
            resultat = statement.executeQuery();
            while (resultat.next()) {
                Assiste a = new Assiste();
                a.setRef_c(resultat.getInt("REF_C"));
                a.setRef_i(resultat.getInt("REF_I"));
                a.setRef_m(resultat.getString("REF_M"));
                a.setId_assiste(resultat.getInt("ID_ASSISTE"));
                liste.add(a);

            }
        } catch (Exception exception) {
            System.out.println("Classe : AssisteDAO.java\n"
                    + "Methode : getAll()\n"
                    + "Exception : " + exception);
        }
        return liste;
    }

    @Override
    public boolean delAll() {
        boolean valide = false;
        try {
            PreparedStatement statement = ODB.OracleDBSingleton.getSession().prepareStatement("DELETE FROM ASSISTE");
            if (statement.executeUpdate() != 0) {
                valide = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EleveDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valide;
    }

    @Override
    public int create(Assiste instance) {
        seq=-1;
        try {
            requete = "INSERT INTO " + nomTable + " (ID_ASSISTE , REF_I , REF_C , REF_M)  "
                      + "  VALUES ( " + seq_id_next() + " , ? , ? , ? )";
            statement = session.prepareStatement(requete);
            statement.setInt(1, instance.getRef_i());
            statement.setInt(2, instance.getRef_c());
            statement.setString(3, instance.getRef_m());
            if (statement.executeUpdate() != 0) {
                seq=seq_id_curr();
            }
        } catch (Exception exception) {
            System.out.println("Classe : AssisteDAO.java\n"
                    + "Methode : create(Assiste instance)\n"
                    + "Exception : " + exception);
        }
        
        return seq;
    }

    @Override
    public Assiste find(int id) {
       Assiste assiste = null;
        try {
            requete = "SELECT * FROM ASSISTE WHERE  ID_ASSISTE = ? ";
            statement = session.prepareStatement(requete);
            statement.setInt(1, id);
            resultat = statement.executeQuery();
            while (resultat.next()) {
                valide = true;
                assiste = new Assiste();
                assiste.setId_assiste(resultat.getInt("ID_ASSISTE"));
                assiste.setRef_i(Integer.parseInt(resultat.getString("REF_I")));
                assiste.setRef_m(resultat.getString("REF_M"));
                assiste.setRef_c(resultat.getInt("REF_C"));
            }

        } catch (Exception exception) {
            System.out.println("Classe : AssisteDAO.java\n"
                    + "Methode : findByID()\n"
                    + "Exception : " + exception);
        }
        return assiste;
    }
    
      public String getNomMatiere(int id) {
        try {
            requete = "SELECT NOM FROM MATIERE WHERE  ID_MATIERE = ? ";
            statement = session.prepareStatement(requete);
            statement.setInt(1, id);
            resultat = statement.executeQuery();
            while (resultat.next()) {
                valide = true;
                return resultat.getString("NOM");
            }
        } catch (Exception exception) {
            System.out.println("Classe : AssisteDAO.java\n"
                    + "Methode : findByID()\n"
                    + "Exception : " + exception);
        }
        return "";
    }
      

    @Override
    public boolean update(Assiste instance) {
        valide = false;
        try {
            requete = "UPDATE " + nomTable + " SET   "
                    + "REF_I           =  ?  ,"
                    + "REF_C        =  ?  ,"
                    + "REF_M =  ?  "
                    + "WHERE  ID_ASSISTE = ? ";
            statement = session.prepareStatement(requete);
            statement.setInt(1, instance.getRef_i());
            statement.setInt(2, instance.getRef_c());
            statement.setString(3, instance.getRef_m());
            statement.setInt(4, instance.getId_assiste());
            if(statement.executeUpdate()!=0){
                valide = true;
            }
        } catch (Exception exception) {
            System.out.println("Classe : AssisteDAO.java\n"
                    + "Methode : update(Assiste instance)\n"
                    + "Exception : " + exception);
        }
        return valide;
    }

    @Override
    public boolean delete(int id) {
        valide = false;
        try {
            requete = "DELETE FROM " + nomTable + " WHERE ( ID_ASSISTE = ? )";
            statement = session.prepareStatement(requete);
            statement.setInt(1, id);
            if (statement.executeUpdate() != 0){
                valide = true;
            }
        } catch (Exception exception) {
            System.out.println("Classe : AssisteDAO.java\n"
                    + "Methode : delete(id)\n"
                    + "Exception : " + exception);
        }
        return valide;
    }
    
    private int seq_id_next(){
        try {
            requete = "SELECT " +nomSequence+ ".nextval FROM DUAL";
            statement = session.prepareStatement(requete);
            resultat = statement.executeQuery();
            while (resultat.next()) {
                seq=resultat.getInt("NEXTVAL");
            }

        } catch (Exception exception) {
            System.out.println("Classe : ParentDAO.java\n"
                    + "Methode : seq_id_next\n"
                    + "Exception : " + exception);
        }
        System.out.println("sequence nextval "+seq);
        return seq;
    }
    
    public int seq_id_curr(){
    try {
            requete = "SELECT " +nomSequence+ ".currval FROM DUAL";
            statement = session.prepareStatement(requete);
            resultat = statement.executeQuery();
            while (resultat.next()) {
                seq=resultat.getInt("CURRVAL");
            }

        } catch (Exception exception) {
            System.out.println("Classe : ParentDAO.java\n"
                    + "Methode : seq_id_curr\n"
                    + "Exception : " + exception);
        }
       
        System.out.println("sequence curr  "+seq);
        return seq;
    }
    
}
