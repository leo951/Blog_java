import java.sql.*;
import java.text.DateFormat;


public class Blog {
	
	static Connection cnx;
	static Statement st;
	static ResultSet rst;

	
	public static void Main(String[] args) {
		// TODO Auto-generated method stub
		//Test
		
		DateFormat fullDateFormat = DateFormat.getDateTimeInstance(
		        DateFormat.FULL,
		        DateFormat.FULL);
		
		Ajouter(1, "Léo", "Test", "Je test l'insertion", "blablabla", fullDateFormat);
		RechercherParTitre("Test");
		Modifier(1, "Léo Peyré", "Test de la modification", "J'espère que ca fonctionne", "La modification devrait à present etre faite", fullDateFormat);
		SupprimerParTitre("Test");
		
		
		try {
			cnx = connecterDB();
			st = cnx.createStatement();
			rst = st.executeQuery("SELECT * from blog_bdd");
			
			while(rst.next()) {
				System.out.println(rst.getInt("id") + "\t");
				System.out.println(rst.getString("auteur") + "\t");
				System.out.println(rst.getString("titre") + "\t");
				System.out.println(rst.getString("description") + "\t");
				System.out.println(rst.getString("texte") + "\t");
				System.out.println(rst.getDate("date") + "\t");
				
			}
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static  Connection connecterDB() throws SQLException {
    	try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver ok");
            String url = "jdbc:mysql://localhost:3306/blog_bdd";
            String utilisateur = "root";
            String motDePasse = "";
            Connection cnx= DriverManager.getConnection(url,utilisateur,motDePasse);
            System.out.println("connection bien etablie");
            return cnx;
        }
    	catch(ClassNotFoundException e) {
            //A Gérer
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        	return cnx;
    	}
    }

	

	public static void Ajouter(int id, String auteur, String titre, String description, String texte, DateFormat fullDateFormat) {

		try {
			String query = "INSERT INTO tb_article VALUES("+id+",'"+auteur+"','"+titre+"','"+description+"','"+texte+"',"+fullDateFormat+")";
			cnx=connecterDB();
			st = cnx.createStatement();
			st.executeUpdate(query);
			System.out.println("Texte bien ajouté");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		}
	
	public static void SupprimerParTitre(String titre) {
		try {
			String query = "DELETE FROM tb_article WHERE titre"+titre;
			cnx = connecterDB();
			st=cnx.createStatement();
			st.executeUpdate(query);
			System.out.println("Article bien supprimer");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void RechercherParTitre(String titre) {
		try {
			String query = "SELECT FROM tb_article WHERE titre"+titre;
			cnx = connecterDB();
			st=cnx.createStatement();
			rst = st.executeQuery(query);
			rst.last();
			int nbrRow = rst.getRow();
			if(nbrRow == 1) {
				System.out.println("Article bien trouvé");
			}
			else {
				System.out.println("Article non trouvé");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void Modifier(int id, String n_auteur, String n_titre, String n_description, String n_texte, DateFormat fullDateFormat) {
		try {
			String query = "UPDATE tb_article SET auteur='"+n_auteur
					+"', titre='"+n_titre
					+"', description='"+n_description
					+"', texte='"+n_texte
					+"', date="+fullDateFormat
					+" WHERE id="+id;
			cnx = connecterDB();
			st=cnx.createStatement();
			st.executeUpdate(query);
			System.out.println("Article bien modifié");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}

