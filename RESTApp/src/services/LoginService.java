package services;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.prism.Image;

import beans.Admin;
import beans.BasicUser;
import beans.Cart;
import beans.Firm;
import beans.Operator;
import beans.Photo;
import beans.User;
import dao.AdminDAO;
import dao.BasicUserDAO;
import dao.FirmDAO;
import dao.OperatorDAO;
import dao.PhotoDAO;


@Path("")
public class LoginService {

	@Context
	ServletContext ctx;
	String contextPath;
	private ReentrantLock ankh;

	/* -1 : unknown
		0: basic user (buyer)
		1: seller
		2: operator
		3: admin
		4: firm 
	 */
	
	
	public LoginService() {

	}

	@PostConstruct
	public void init() {

		if (ctx.getAttribute("userDAO") == null) {
			contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDAO", new BasicUserDAO(contextPath));
		}
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User ud, @Context HttpServletRequest request) throws URISyntaxException {
		BasicUserDAO userDao = (BasicUserDAO) ctx.getAttribute("userDAO");
		OperatorDAO operatorDao = (OperatorDAO) ctx.getAttribute("operatorDAO");
		AdminDAO adminDao = (AdminDAO) ctx.getAttribute("adminDAO");
		
		User loggedUser = userDao.find(ud.getUsername().toLowerCase(), ud.getPassword());
		if (loggedUser == null) {
			System.out.println("Login: nobody in users");
			loggedUser = operatorDao.find(ud.getUsername().toLowerCase(), ud.getPassword());
			if (loggedUser == null) {
				System.out.println("Login: nobody in operators");
				loggedUser = adminDao.find(ud.getUsername().toLowerCase(), ud.getPassword());
				if (loggedUser == null) {
					System.out.println("Login: nobody in admins");
					return Response.status(400).entity("Invalid username and/or password. Couldn't find user. Sorry.").build();
				}
			}		
		}
		
		request.getSession().setAttribute("user", loggedUser);
		if(loggedUser.getuType()==1) {
			BasicUser target = (BasicUser) loggedUser;
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			if(target.getdTimer().isEmpty() && !target.getwTimer().isEmpty()) {
				LocalDateTime weekTime = LocalDateTime.parse(target.getwTimer(), formatter);
				long wNoOfDaysBetween = ChronoUnit.DAYS.between(weekTime, now);
				if(wNoOfDaysBetween>=8) {
					target.setWimage(0);
					target.setwTimer("");
				}
			}else if(!target.getdTimer().isEmpty() && target.getwTimer().isEmpty()) {
				LocalDateTime dayTime = LocalDateTime.parse(target.getdTimer(), formatter);
				long dNoOfDaysBeetween = ChronoUnit.DAYS.between(dayTime, now);
				if(dNoOfDaysBeetween>=3) {
					target.setDimage(0);
					target.setdTimer("");
				}else {
					target.setwTimer(target.getdTimer());
				}
			}
			else if(!target.getdTimer().isEmpty() && !target.getwTimer().isEmpty()) {
				LocalDateTime dayTime = LocalDateTime.parse(target.getdTimer(), formatter);
				LocalDateTime weekTime = LocalDateTime.parse(target.getwTimer(), formatter);
				long dNoOfDaysBeetween = ChronoUnit.DAYS.between(dayTime, now);
				long wNoOfDaysBetween = ChronoUnit.DAYS.between(weekTime, now);
				
				if(dNoOfDaysBeetween>=3) {
					target.setDimage(0);
					target.setdTimer("");
				}
				if(wNoOfDaysBetween>=8) {
					target.setWimage(0);
					target.setwTimer("");
				}
			}
		}
		return Response.status(200).build();
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(BasicUser ud, @Context HttpServletRequest request) {

		BasicUserDAO userDao = (BasicUserDAO) ctx.getAttribute("userDAO");
		OperatorDAO operatorDao = (OperatorDAO) ctx.getAttribute("operatorDAO");
		AdminDAO adminDao = (AdminDAO) ctx.getAttribute("adminDAO");
		contextPath = ctx.getRealPath("");

		if (ud.getEmail() == null || ud.getPassword() == null || ud.getCountry() == null || ud.getUsername() == null) {
			return Response.status(400).entity("Invalid request!").build();
		}

		if (!ud.getUsername().matches("^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")) {
			return Response.status(400).entity("Invalid username format! Bloody regex.").build();
		}

		if (ud.getEmail().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$")) {
			return Response.status(400).entity("Invalid email format!").build();
		}

		for (BasicUser u : userDao.findAll()) {
			if (u.getEmail().equals(ud.getEmail())) {
				return Response.status(400).entity("Email is already in use!").build();
			}
		}
		
		if(operatorDao.find(ud.getUsername()) != null) {
			return Response.status(400).entity("Username already exists").build();			
		}
		
		if(adminDao.find(ud.getUsername()) != null) {
			return Response.status(400).entity("Username already exists").build();			
		}

		BasicUser nUser = new BasicUser(ud.getUsername().toLowerCase(), ud.getPassword(), ud.getEmail(), ud.getCountry());
		if (userDao.add(nUser)) {
			
			/*sendConfirmationMail(ud.getEmail());*/
			try {
				String path="C:\\Users\\Anagnosti\\Desktop\\Database\\Verification_Files\\verification_for_"+ud.getUsername().toLowerCase()+".txt";
				PrintWriter writer = new PrintWriter(new File(path), "UTF-8");
				String message="http://localhost:8080/RESTApp/#!/verification_successful/"+ud.getUsername().toLowerCase();
				writer.print(message);
				System.out.println("Verification file for "+ud.getUsername().toLowerCase()+" has been created.");
				writer.close();
			}
			catch (Exception e) {
				System.out.println("Error creating verification file. for user "+ud.getUsername().toLowerCase()+e);
			}
			System.out.println("REGISTER TESTING FROM FRONTEND: "+"username: "+ud.getUsername()+"|email: "+ud.getEmail()+"|password: "+ud.getPassword()+"|country: "+ud.getCountry());
			return Response.ok(nUser).build();
		} else {
			return Response.status(400).entity("Username already exists").build();
		}

	}
	
	@POST
	@Path("/add-operator")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addOperator(Operator ud, @Context HttpServletRequest request) {

		BasicUserDAO userDao = (BasicUserDAO) ctx.getAttribute("userDAO");
		OperatorDAO operatorDao = (OperatorDAO) ctx.getAttribute("operatorDAO");
		AdminDAO adminDao = (AdminDAO) ctx.getAttribute("adminDAO");
		
		contextPath = ctx.getRealPath("");

		if (ud.getPassword() == null || ud.getUsername() == null) {
			return Response.status(400).entity("Invalid request!").build();
		}

		if (!ud.getUsername().matches("^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")) {
			return Response.status(400).entity("Invalid username format!").build();
		}
		
		if(userDao.find(ud.getUsername()) != null) {
			return Response.status(400).entity("Username already exists").build();			
		}
		
		if(adminDao.find(ud.getUsername()) != null) {
			return Response.status(400).entity("Username already exists").build();			
		}

		Operator nOp = new Operator(ud.getUsername().toLowerCase(), ud.getPassword());
		
		if (operatorDao.add(nOp)) {
			System.out.println("Operator: " + nOp.getUsername() + " | " + nOp.getPassword());
			return Response.ok(nOp).build();

		} else {
			return Response.status(400).entity("Username already exists").build();
		}

	}
	
	
	@GET
	@Path("/logout")
	public Response logout(@Context HttpServletRequest request) throws URISyntaxException {
		request.getSession().invalidate();
		java.net.URI location = new java.net.URI("../index.html");
		return Response.temporaryRedirect(location).build();
	}

	@GET
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@Context HttpServletRequest request) {
		ArrayList<User> usr = new ArrayList<User>();
		usr.add((User) request.getSession().getAttribute("user"));
		return Response.ok((User) request.getSession().getAttribute("user")).build();
	}

	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getCustomers(@Context HttpServletRequest request) {
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		ArrayList<User> cust = new ArrayList<User>();
		for (User u : dao.findAll()) {
			if (u.getuType()==0) {
				cust.add(u);
			}
		}
		return cust;
	}

	@GET
	@Path("/sellers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getSellers(@Context HttpServletRequest request) {
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		ArrayList<User> sell = new ArrayList<User>();
		for (User u : dao.findAll()) {
			if (u.getuType()==1) {
				sell.add(u);
			}
		}
		return sell;
	}

	@DELETE
	@Path("/delete-user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeUser(@Context HttpServletRequest request) {
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		User current = (User) request.getSession().getAttribute("user");
		
		if (current.getuType()!=0 && current.getuType()!=1) {
			return Response.status(400).build();

		} else {
			dao.remove(current.getUsername());
			return Response.status(200).build();

		}
	}

	@PUT
	@Path("/evolve")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response evolveUser(User us, @Context HttpServletRequest request) {
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		User target = dao.find(us.getUsername());
		 
		if (target == null) {
			return Response.status(400).build();
		} else {
			target.setuType(1);
			System.out.println("Evolution completed for: " + us.getUsername());
			return Response.ok(target).build();

		}
	}
	
	//NEEDS FIXING ?? NAH BRAH
	
	@PUT
	@Path("/verify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyUser(User us, @Context HttpServletRequest request) {
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		User target = dao.find(us.getUsername());
		
		if (! (target.getuType() == 0)||target==null) {
			return Response.status(400).build();
		}
		 else {
			target.setActivated(true);
			System.out.println("RAK IS GOD: " + target.getUsername()+", "+target.getPassword()+", "+target.getuType()+", "+target.isActivated());
			return Response.status(200).build();

		}
	}
	
	@PUT
	@Path("/find-user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findUser(User us, @Context HttpServletRequest request) {
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		System.out.println("What is wrong [nothing, just so you know, friend]: " + us.getUsername());
		BasicUser target = dao.find(us.getUsername());

		if (target == null) {
			return Response.status(400).build();
		}
		
		return Response.ok(target).build();

	}

	
	@PUT
	@Path("/add-test-picture")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTestPicture(BasicUser us, @Context HttpServletRequest request) {
		//ankh.lock();
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		//System.out.println("Butterfly: "+us.getUsername());
		BasicUser target = dao.find(us.getUsername());
		
		if (! (target.getuType() == 0)||target==null) {
			System.out.println("console.log()");
			//ankh.unlock();
			return Response.status(400).build();
		}
		 else {
			target.addPictureToTest(us.getTest().get(0));
			System.out.println("RAKOMIR SLIKA: " + target.getUsername()+" | " + target.getTest().size() + " | " + target.getTest().toString());
			//ankh.unlock();
			return Response.status(200).build();

		}
	}
	
	
	@PUT
	@Path("/approve-ware")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response approveWare(BasicUser us, @Context HttpServletRequest request) {
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		BasicUser target = dao.find(us.getUsername());
		
		if(target.getuType()!=1 || target==null) {
			System.out.println("Classic standard output");
			return Response.status(400).build();
		}
		else {
			target.approvePhoto(us.getPhotos().get(0).getName());
			return Response.status(200).build();
		}
		
	}
	
	@POST
	@Path("/add-card")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCard(BasicUser us, @Context HttpServletRequest request) {
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		BasicUser target = dao.find(us.getUsername());
		
		if(target==null){
			System.out.println("Something went wrong");
			return Response.status(400).build();
		}/*
		else if(!(target.getuType() == 1) && !(target.getuType() == 3)) {
			System.out.println("Not merchant nor admin.");
			return Response.status(400).build();
		}
		else if(target.getuType() == 0) {
			System.out.println("Peasants can't use the Merchant Corner.");
			return Response.status(400).build();
		}*/
		
		else {
			target.addCard(us.getCards().get(0));
			System.out.println(us.getUsername() + " now has one more card to spare out of a total of " + us.getCards().size() + ".");
			return Response.ok(target).build();
		}

	}
	

	@PUT
	@Path("/change-password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePassword(User us, @Context HttpServletRequest request) {
		BasicUserDAO udao = (BasicUserDAO) ctx.getAttribute("userDAO");
		OperatorDAO oDao = (OperatorDAO) ctx.getAttribute("operatorDAO");
		AdminDAO aDao = (AdminDAO) ctx.getAttribute("adminDAO");
		
		System.out.println("Bee: "+us.getUsername());
		User target = udao.find(us.getUsername());
		if (target==null) {
			System.out.println("Not a user.");
			target = oDao.find(us.getUsername());
			if(target==null) {
				System.out.println("Not an operator.");
				target = aDao.find(us.getUsername());
				if(target==null) {
					System.out.println("Not an admin. Well, bloody hell..");
					return Response.status(400).build();
				}
			}
		}
		 
			System.out.println("RAKOMIR NEKADA: " + target.getUsername()+" | " + target.getPassword());
			target.setPassword(us.getPassword());
			System.out.println("RAKOMIR TRENUTNO: " + target.getUsername()+" | " + target.getPassword());
			return Response.status(200).build();

		
	}
	
	@PUT
	@Path("/operator-first-time-change")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response operatorFirstTimeChange(Operator op, @Context HttpServletRequest request) {
		OperatorDAO dao = (OperatorDAO) ctx.getAttribute("operatorDAO");
		System.out.println("Owl: "+op.getUsername());
		Operator target = dao.find(op.getUsername());
		
		if(target==null) {
			System.out.println("Target is null.");
			return Response.status(400).build();

		}
		else if (!(target.isFirstTime())) {
			System.out.println("Not the operator's first time.");
			return Response.status(400).build();
		}
		 else {
			target.setFirstTime(false);
			System.out.println("RAKOMIR NIJE VISE PRVI PUT: " + target.getUsername()+" | " + target.getPassword() + " | " + target.isFirstTime());
			return Response.status(200).build();

		}
	}
	
	/*
	@POST
	@Path("/start-test")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response startTest(User us, @Context HttpServletRequest request) {
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		BasicUser target = dao.find(us.getUsername());
		
		if (! (target.getuType() == 0)||target==null) {
			return Response.status(400).build();
		}
		 else {
			target.setTest(((BasicUser)us).getTest());
			System.out.println("RAKOMIR ZAUVJEK: " + target.getUsername()+" | " + target.getTest().size() + " | " + target.getTest().toString());
			return Response.status(200).build();

		}
	}
	*/
	
	@PUT
	@Path("/block")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response blockUser(User us, @Context HttpServletRequest request) {
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		BasicUser target = dao.find(us.getUsername());
		User current = (User) request.getSession().getAttribute("user");

		if ((current.getuType() != 2) && (target.getuType() == 3|| target.getuType() == 2 || target==null)) {
			return Response.status(400).build();
		}
		 else {
				target.setBlocked(true);
				System.out.println("KHM: " + target.getUsername()+", "+target.getuType()+", now is blocked: "+target.isBlocked());
				return Response.status(200).build();
		
		}
	}
	
	@PUT
	@Path("/unblock")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response unblockUser(User us, @Context HttpServletRequest request) {
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		BasicUser target = dao.find(us.getUsername());
		User current = (User) request.getSession().getAttribute("user");

		if ((current.getuType() != 2) && (target.getuType() == 3|| target.getuType() == 2 || target==null)) {
			return Response.status(400).build();
		}
		 else {
			 	target.setBlocked(false);
				System.out.println("WEQ: " + target.getUsername()+", "+target.getuType()+", now is blocked: "+target.isBlocked());
				return Response.status(200).build();

			
		}
	}
	
	
	/*
	@PUT
	@Path("/global-timer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response globalTimer(Photo newPhotogragh, @Context HttpServletRequest request) {
		//BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		BasicUser current = (BasicUser) request.getSession().getAttribute("user");
		
		if(current==null) {
			System.out.println("Error not logged in");
			return Response.status(400).build();
		}
		else if(current.getuType() != 1) {
			System.out.println("Not a merchant");
			return Response.status(400).build();
		}else {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatedDateTime = now.format(formatter);
			current.addPhotographToAuction(newPhotogragh);
			current.setdTimer(formatedDateTime);
			current.setwTimer(formatedDateTime);
			current.incrementDimage();
			current.incrementWimage();
			System.out.println("GNOW: " + formatedDateTime);
			System.out.println("New image: " + current.getPhotos().size() + " | " + current.getPhotos().get(0).getName());
			return Response.status(200).build();
		}
	}*/
	
	@PUT
	@Path("/gt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response globalTimer(BasicUser us, @Context HttpServletRequest request) {
		//BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		BasicUser current = (BasicUser) request.getSession().getAttribute("user");
		
		if(current==null) {
			System.out.println("Error not logged in");
			return Response.status(400).build();
		}
		else if(current.getuType() != 1) {
			System.out.println("Not a merchant");
			return Response.status(400).build();
		}else {
			
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatedDateTime = now.format(formatter);
			current.addPhotographToAuction(us.getPhotos().get(0));
			current.setdTimer(formatedDateTime);
			current.setwTimer(formatedDateTime);
			current.incrementDimage();
			current.incrementWimage();
			System.out.println("GNOW: " + formatedDateTime);
			System.out.println("New image: " + current.getPhotos().size() + " | " + current.getPhotos().get(0).getName());
			return Response.status(200).build();
		}
	}
	
	@PUT
	@Path("/dt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dayTimer(BasicUser us, @Context HttpServletRequest request) {
		//BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		BasicUser current = (BasicUser) request.getSession().getAttribute("user");
		
		if(current==null) {
			System.out.println("Error not logged in");
			return Response.status(400).build();
		}
		else if(current.getuType() != 1) {
			System.out.println("Not a merchant");
			return Response.status(400).build();
		}else if(current.getDimage()==0){
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatedDateTime = now.format(formatter);
			current.addPhotographToAuction(us.getPhotos().get(0));
			current.setdTimer(formatedDateTime);
			current.incrementDimage();
			System.out.println("DNOW WAS 0: " + formatedDateTime);
			System.out.println("New image: " + current.getPhotos().size() + " | " + current.getPhotos().get(0).getName());
			System.out.println("Dimage status: " + current.getDimage() + ", dTimer status: " + current.getdTimer());
			System.out.println("Wimage status: " + current.getWimage() + ", wTimer status: " + current.getwTimer());
			return Response.status(200).build();
		}else {
			current.addPhotographToAuction(us.getPhotos().get(0));
			current.incrementDimage();
			System.out.println("New image: " + current.getPhotos().size() + " | " + current.getPhotos().get(0).getName());
			System.out.println("Dimage status: " + current.getDimage() + ", dTimer status: " + current.getdTimer());
			System.out.println("Wimage status: " + current.getWimage() + ", wTimer status: " + current.getwTimer());
			return Response.status(200).build();
		}
	}
	
	@PUT
	@Path("/wt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response weekTimer(BasicUser us, @Context HttpServletRequest request) {
		//BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		BasicUser current = (BasicUser) request.getSession().getAttribute("user");
		
		if(current==null) {
			System.out.println("Error not logged in");
			return Response.status(400).build();
		}
		else if(current.getuType() != 1) {
			System.out.println("Not a merchant");
			return Response.status(400).build();
		}else if(current.getWimage()==0){
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatedDateTime = now.format(formatter);
			current.addPhotographToAuction(us.getPhotos().get(0));
			current.setwTimer(formatedDateTime);
			current.incrementDimage();
			current.incrementWimage();
			System.out.println("WNOW WAS 0: " + formatedDateTime);
			System.out.println("New image: " + current.getPhotos().size() + " | " + current.getPhotos().get(0).getName());
			System.out.println("Dimage status: " + current.getDimage() + ", dTimer status: " + current.getdTimer());
			System.out.println("Wimage status: " + current.getWimage() + ", wTimer status: " + current.getwTimer());
			return Response.status(200).build();
		}else {
			current.addPhotographToAuction(us.getPhotos().get(0));
			current.incrementDimage();
			current.incrementWimage();
			System.out.println("New image: " + current.getPhotos().size() + " | " + current.getPhotos().get(0).getName());
			System.out.println("Wimage status: " + current.getWimage() + ", wTimer status: " + current.getwTimer());
			System.out.println("Dimage status: " + current.getDimage() + ", dTimer status: " + current.getdTimer());
			return Response.status(200).build();
		}
	}
	
	@PUT
	@Path("/bw")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response buyWares(BasicUser us, @Context HttpServletRequest request) {
		BasicUser current = (BasicUser) request.getSession().getAttribute("user");
		
		if(current==null) {
			System.out.println("Error not logged in");
			return Response.status(400).build();
		}else {
			Cart nCart = new Cart();
			int cash=0;
			for(int i=0;i<us.getPhotos().size();i++) {
				String sPrice = us.getPhotos().get(i).getlPrice();
				System.out.println("Cena: " + sPrice);
				//int warePrice = Integer.parseInt(sPrice);
				//cash+=warePrice;
				nCart.getPhotos().put(us.getPhotos().get(i).getId(), us.getPhotos().get(i));
				String wareName = us.getPhotos().get(i).getName();
				String wareAuthor = us.getPhotos().get(i).getAuthor();
				String wareDateOfAuctioning = us.getPhotos().get(i).getDateOfAuctioning();
				String sOriginalWidth = us.getPhotos().get(i).getOriginalWidth();
				String sOriginalHeigth = us.getPhotos().get(i).getOriginalHeight();
				String sNewWidth = us.getPhotos().get(i).getNewWidth();
				
				int originalWidth = Integer.parseInt(sOriginalWidth);
				int originalHeight = Integer.parseInt(sOriginalHeigth);
				int newWidth = Integer.parseInt(sNewWidth);
				int newHeight = originalHeight*newWidth/originalWidth;
				
				String biteArray = us.getPhotos().get(i).getByteArray();
				byte[] imageByteArray = Base64.getDecoder().decode(biteArray);
				ByteArrayInputStream bis = new ByteArrayInputStream(imageByteArray);
			     try {
					BufferedImage inputImage = ImageIO.read(bis);
					BufferedImage outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());
					Graphics2D g2d = outputImage.createGraphics();
			        g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
			        g2d.dispose();
			        String newImageName=wareName+"by"+wareAuthor+"on"+wareDateOfAuctioning+".png";
			        String path="C:\\Users\\Anagnosti\\Desktop\\Database\\wares\\"+newImageName;
			        ImageIO.write(outputImage, "png", new File(path));
			        System.out.println(newImageName + " has been bought, saved in the adequate storage unit.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatedDateTime = now.format(formatter);
			nCart.setDateID(formatedDateTime);
			current.addToHistory(formatedDateTime, nCart);
			//current.receiveIncome(cash);
			//System.out.println("Current amount on user: " + current.getCards().get(0).getMoney());
			return Response.status(200).build();
		}
	}
	
	
	@GET
	@Path("/ousers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOusers(@Context HttpServletRequest request) {
		User current = (User) request.getSession().getAttribute("user");
		
		if (current == null) {
			System.out.println("o >>");
			return Response.status(400).build();
			
		}

		if (current.getuType() == 2) {
			Collection<BasicUser> allOperatorUsers= ((BasicUserDAO) ctx.getAttribute("userDAO")).findAll();
			return Response.ok(allOperatorUsers).build();
		} else {
			System.out.println("Unknown ousers error");
			return Response.status(400).build();
		}

	}
	
	@GET
	@Path("/usersb")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsersb(@Context HttpServletRequest request) {
		Collection<BasicUser> allUsersb = ((BasicUserDAO) ctx.getAttribute("userDAO")).findAll();
		if(allUsersb.isEmpty()) {
			System.out.println("No users exist atm.");
			return Response.status(400).build();
		}
		else {
			return Response.ok(allUsersb).build();

		}
	
	}
	
	@GET
	@Path("/userso")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserso(@Context HttpServletRequest request) {
		Collection<Operator> allUserso = ((OperatorDAO) ctx.getAttribute("operatorDAO")).findAll();
		if(allUserso.isEmpty()) {
			System.out.println("No users exist atm.");
			return Response.status(400).build();
		}
		else {
			return Response.ok(allUserso).build();

		}
	
	}
	
	@GET
	@Path("/usersa")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsersa(@Context HttpServletRequest request) {
		
		Collection<Admin> allUsersa = ((AdminDAO) ctx.getAttribute("adminDAO")).findAll();
		if(allUsersa.isEmpty()) {
			System.out.println("No users exist atm.");
			return Response.status(400).build();
		}
		else {
			System.out.println("CHECK: " + allUsersa.size());
			return Response.ok(allUsersa).build();

		}
	
	}
	//Does not work for some reason
	/*
	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<BasicUser> getUsers(@Context HttpServletRequest request) {
		User current = (User) request.getSession().getAttribute("user");

		if (current == null) {
			return null;
		}

		if (current.getuType() == 3) {
			return ((BasicUserDAO) ctx.getAttribute("userDAO")).findAll();
		} else {
			return null;
		}

	}*/
	//ADD THIS TO RAK'S TURTLE COLLECTION
	//ALL HAIL RAK
	@PUT
	@Path("/photo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPhoto(Photo ph, @Context HttpServletRequest request) {
		PhotoDAO dao = (PhotoDAO) ctx.getAttribute("photoDAO");
		Photo photo = dao.find(ph.getId(),ph.getByteArray());
		
		if (photo==null) {
			return Response.status(400).build();
		}
		 else {
			return Response.ok(photo).build();

		}
	}
	
	@PUT
	@Path("/find-ware")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findWare(Photo ph, @Context HttpServletRequest request) {
		PhotoDAO dao = (PhotoDAO) ctx.getAttribute("photoDAO");
		Photo photo = dao.find(ph.getId());
		if (photo==null) {
			System.out.println("No photo found for set id: " + ph.getId());
			return Response.status(400).build();
		}
		 else {
			return Response.ok(photo).build();

		}
	}

	@DELETE
	@Path("/delete-photo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePhoto(Photo ph, @Context HttpServletRequest request) {
		PhotoDAO dao = (PhotoDAO) ctx.getAttribute("photoDAO");
		BasicUserDAO udao = (BasicUserDAO) ctx.getAttribute("userDAO");
		Photo photo = dao.find(ph.getId(),ph.getByteArray());

		BasicUser current = (BasicUser) request.getSession().getAttribute("user");
		BasicUser seller = udao.find(current.getUsername());

		if(seller == null) {
			return Response.status(400).build();			
		}
		if (photo==null) {
			return Response.status(400).build();
		}
		 else {

		 	if(current.owns(photo)) {
		 		dao.remove(photo.getId());
		 		current.removePhoto(photo.getId());
				return Response.ok().build();
		 	}

			return Response.status(400).build();

		}
	}
	


	@GET
	@Path("/firms")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFirms(@Context HttpServletRequest request) {
		User current = (User) request.getSession().getAttribute("user");

		if (current == null) {
			return Response.status(400).build();
		}

		if (current.getuType() == 2 || current.getuType() == 3) {
			return Response.ok(((FirmDAO) ctx.getAttribute("firmDAO")).findAll()).build();
		} else {
			return Response.status(400).build();
		}

	}

	@GET
	@Path("/firm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFirm(Firm fm, @Context HttpServletRequest request) {
		User current = (User) request.getSession().getAttribute("user");
		FirmDAO dao = (FirmDAO) ctx.getAttribute("firmDAO");
		Firm target = dao.find(fm.getPib());
		if (target == null) {
			return Response.status(400).build();
		}

		if (current.getuType() == 2 || current.getuType() == 3) {
			return Response.ok(target).build();
		} else {
			return Response.status(400).build();
		}

	}


	
	
	
	//Has problems, mate
	/*
	@POST
	@Path("/check-username")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkUsername(User us, @Context HttpServletRequest request) {
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		User target = dao.find(us.getUsername());

		if (target == null) {
			return Response.status(200).build();

		}else{
			return Response.status(400).build();
		}
	}*/
	
	

	@GET
	@Path("/cart")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCart(@Context HttpServletRequest request) {
		User current = (User) request.getSession().getAttribute("user");
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		BasicUser target = dao.find(current.getUsername());
		
		if (target == null) {
			return Response.status(400).build();
		}

		return Response.ok(target.getCart()).build();
	}

	@GET
	@Path("/history")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHistory(@Context HttpServletRequest request) {
		User current = (User) request.getSession().getAttribute("user");
		BasicUserDAO dao = (BasicUserDAO) ctx.getAttribute("userDAO");
		BasicUser target = dao.find(current.getUsername());
		
		if (target == null) {
			return Response.status(400).build();
		}

		return Response.ok(target.getHistory()).build();
	}

	/*private void sendConfirmationMail(String userEmail) {
		final String email = "thegootwizard@gmail.com";
		final String password = "alchemythegootwizard";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mail sa kog saljes"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(userEmail));
			message.setSubject("Just Your Random Email Verification");
			message.setText("Thank you for registering for the Web Rest random app that has no point"
					+ " whatsoever apart from giving the developer experience and a fair grade.	"
					+ "In order to complete the registration and help the developre with their	"
					+ "goal, click on the following link and activate your email. Not doing this	"
					+ "will make you unable to access the cool content on the website!");
			message.setText("The link to click: "+"http:/localhost:8080/RESTApp/#!/verification_successful");
			message.setText("\n Thank you for participating in this event! take care!");

			Transport.send(message);

			System.out.println("Sent verification email.");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}*/
	
	
}
