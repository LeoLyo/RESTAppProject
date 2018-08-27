package listeners;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMessages_es;

import beans.Admin;
import beans.BasicUser;
import beans.Operator;
import dao.AdminDAO;
import dao.BasicUserDAO;
import dao.DAOData;
import dao.FirmDAO;
import dao.OperatorDAO;
import dao.PhotoDAO;

public class Listener implements javax.servlet.ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ServletContext ctx = arg0.getServletContext();
		AdminDAO aDAO = (AdminDAO) ctx.getAttribute("adminDAO");
		OperatorDAO oDAO = (OperatorDAO) ctx.getAttribute("operatorDAO");
		PhotoDAO pDAO = (PhotoDAO) ctx.getAttribute("photoDAO");
		BasicUserDAO uDAO = (BasicUserDAO) ctx.getAttribute("userDAO");
		FirmDAO fDAO = (FirmDAO) ctx.getAttribute("firmDAO");

		if (aDAO == null) {
			aDAO = new AdminDAO();
		}

		if (oDAO == null) {
			oDAO = new OperatorDAO();
		}

		if (pDAO == null) {
			pDAO = new PhotoDAO();
		}

		if (uDAO == null) {
			uDAO = new BasicUserDAO();
		}
		if (fDAO == null){
			fDAO = new FirmDAO();
		}

		// String path = arg0.getServletContext().getRealPath("/") + "data.json";
		String path = "C:\\Users\\Anagnosti\\Desktop\\Database\\Data\\" + "data.json";
		ObjectMapper mapper = new ObjectMapper();

		// SimpleModule simpleModule = new SimpleModule();
		// simpleModule.addKeyDeserializer(Article.class, new ArticleKeyDeserializer());
		// mapper.registerModule(simpleModule);

		DAOData data = new DAOData(aDAO, oDAO, pDAO, uDAO, fDAO);
		System.out.println(data.getaDAO().findAll());

		try {
			mapper.writeValue(new File(path), data);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			FileUtils.cleanDirectory(new File("C:\\Users\\Anagnosti\\Desktop\\Database\\Verification_Files"));
			System.out.println("Validation files cleaned.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error deleting verification files "+e1);
		}
		System.out.println("CONTEXT INITIALIZED");
		ObjectMapper mapper = new ObjectMapper();
		ServletContext ctx = arg0.getServletContext();

		// SimpleModule simpleModule = new SimpleModule();
		// simpleModule.addKeyDeserializer(Article.class, new ArticleKeyDeserializer());
		// mapper.registerModule(simpleModule);

		// String path = arg0.getServletContext().getRealPath("/") + "data.json";
		String path = "C:\\Users\\Anagnosti\\Desktop\\Database\\Data\\" + "data.json";

		try {
			DAOData data = mapper.readValue(new File(path), DAOData.class);

			if (data.getuDAO() != null) {
				if (data.getuDAO().findAll().isEmpty()) {
					data.getuDAO().add(new BasicUser("rakomir", "rakrakrak","rakomir.utvarodizach@gmail.com","Rakoland"));
					data.getuDAO().find("rakomir").setActivated(true);
					data.getuDAO().find("rakomir").setuType(1);
				}
				ctx.setAttribute("userDAO", data.getuDAO());
			} else {
				BasicUserDAO uDAO = new BasicUserDAO();
				uDAO.add(new BasicUser("rakomir", "rakrakrak","rakomir.utvarodizach@gmail.com","Rakoland"));
				uDAO.find("rakomir").setActivated(true);
				uDAO.find("rakomir").setuType(1);
				ctx.setAttribute("userDAO", uDAO);
			}

			if (data.getaDAO() != null) {
				if (data.getaDAO().findAll().isEmpty()) {
					data.getaDAO().add(new Admin("admin", "admin"));
				}
				ctx.setAttribute("adminDAO", data.getaDAO());
			} else {
				AdminDAO aDAO = new AdminDAO();
				aDAO.add(new Admin("admin", "admin"));
				ctx.setAttribute("adminDAO", aDAO);
			}

			if (data.getoDAO() != null) {

				if (data.getoDAO().findAll().isEmpty()) {
					data.getoDAO().add(new Operator("khun", "blue_turtle"));
					data.getoDAO().find("khun").setFirstTime(false);
				}
				ctx.setAttribute("operatorDAO", data.getoDAO());
			} else {
				OperatorDAO oDAO = new OperatorDAO();
				oDAO.add(new Operator("khun", "blue_turtle"));
				oDAO.find("khun").setFirstTime(false);
				ctx.setAttribute("operatorDAO", oDAO);
			}

			if (data.getpDAO() != null) {
				ctx.setAttribute("photoDAO", data.getpDAO());
			} else {
				ctx.setAttribute("photoDAO", new PhotoDAO());
			}
			
			if (data.getfDAO() != null) {
				ctx.setAttribute("firmDAO", data.getfDAO());
			} else {
				ctx.setAttribute("firmDAO", new FirmDAO());
			}
			
			System.out.println("REGISTER TESTING FROM DATABASE IN LISTENER: "+data.getuDAO().find("rakomir"));

		} catch (JsonParseException e) {
			e.printStackTrace();
			
			BasicUserDAO uDAO = new BasicUserDAO();
			uDAO.add(new BasicUser("rakomir", "rakrakrak","rakomir.utvarodizach@gmail.com","Rakoland"));
			uDAO.find("rakomir").setActivated(true);
			uDAO.find("rakomir").setuType(1);
			ctx.setAttribute("userDAO", uDAO);
			
			System.out.println("Json parse exception");

			AdminDAO aDAO = new AdminDAO();
			aDAO.add(new Admin("admin", "admin"));
			ctx.setAttribute("adminDAO", aDAO);
			
			OperatorDAO oDAO = new OperatorDAO();
			oDAO.add(new Operator("khun", "blue_turtle"));
			oDAO.find("khun").setFirstTime(false);
			ctx.setAttribute("operatorDAO", oDAO);
			
			ctx.setAttribute("photoDAO", new PhotoDAO());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			
			BasicUserDAO uDAO = new BasicUserDAO();
			uDAO.add(new BasicUser("rakomir", "rakrakrak","rakomir.utvarodizach@gmail.com","Rakoland"));
			uDAO.find("rakomir").setActivated(true);
			uDAO.find("rakomir").setuType(1);
			ctx.setAttribute("userDAO", uDAO);
			
			System.out.println("Json mapping exception");
			
			AdminDAO aDAO = new AdminDAO();
			aDAO.add(new Admin("admin", "admin"));
			ctx.setAttribute("adminDAO", aDAO);
			
			OperatorDAO oDAO = new OperatorDAO();
			oDAO.add(new Operator("khun", "blue_turtle"));
			oDAO.find("khun").setFirstTime(false);
			ctx.setAttribute("operatorDAO", oDAO);
			
			ctx.setAttribute("photoDAO", new PhotoDAO());
		} catch (IOException e) {
			e.printStackTrace();
			
			BasicUserDAO uDAO = new BasicUserDAO();
			uDAO.add(new BasicUser("rakomir", "rakrakrak","rakomir.utvarodizach@gmail.com","Rakoland"));
			uDAO.find("rakomir").setActivated(true);
			uDAO.find("rakomir").setuType(1);
			ctx.setAttribute("userDAO", uDAO);
			
			System.out.println("io exception");
			
			AdminDAO aDAO = new AdminDAO();
			aDAO.add(new Admin("admin", "admin"));
			ctx.setAttribute("adminDAO", aDAO);
			
			OperatorDAO oDAO = new OperatorDAO();
			oDAO.add(new Operator("khun", "blue_turtle"));
			oDAO.find("khun").setFirstTime(false);
			ctx.setAttribute("operatorDAO", oDAO);
			
			ctx.setAttribute("photoDAO", new PhotoDAO());

		}
		

	}

}
