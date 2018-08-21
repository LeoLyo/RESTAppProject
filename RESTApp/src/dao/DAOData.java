package dao;

public class DAOData {
	

	private AdminDAO aDAO;
	private OperatorDAO oDAO;
	private PhotoDAO pDAO;
	private BasicUserDAO uDAO;
	private FirmDAO fDAO;
	
	public AdminDAO getaDAO() {
		return aDAO;
	}
	public void setaDAO(AdminDAO aDAO) {
		this.aDAO = aDAO;
	}
	public OperatorDAO getoDAO() {
		return oDAO;
	}
	public void setoDAO(OperatorDAO oDAO) {
		this.oDAO = oDAO;
	}
	public PhotoDAO getpDAO() {
		return pDAO;
	}
	public void setpDAO(PhotoDAO pDAO) {
		this.pDAO = pDAO;
	}
	public BasicUserDAO getuDAO() {
		return uDAO;
	}
	public void setuDAO(BasicUserDAO uDAO) {
		this.uDAO = uDAO;
	}
	public DAOData(AdminDAO aDAO, OperatorDAO oDAO, PhotoDAO pDAO, BasicUserDAO uDAO, FirmDAO fDAO) {
		super();
		this.aDAO = aDAO;
		this.oDAO = oDAO;
		this.pDAO = pDAO;
		this.uDAO = uDAO;
		this.fDAO = fDAO;
	}
	public DAOData() {
		super();
	}
	public FirmDAO getfDAO() {
		return fDAO;
	}
	public void setfDAO(FirmDAO fDAO) {
		this.fDAO = fDAO;
	}

	

}
