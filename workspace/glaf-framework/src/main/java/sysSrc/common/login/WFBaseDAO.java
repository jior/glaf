package sysSrc.common.login;

import org.hibernate.Session;
import org.hibernate.Transaction;
 

import baseSrc.common.BaseDaoSupport;
import baseSrc.framework.BaseDAO;

public class WFBaseDAO extends BaseDAO {
	
	private Transaction transaction = null;
	private Session session = null;
	
	public BaseDaoSupport hds;
	
	public void setHds(BaseDaoSupport hds) {
		this.hds = hds;
	}
	
	public void openDBContext()
	{
		session = hds.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		 
	}
	
	public void closeDBContext()
	{
		if(null != transaction)
		{
			transaction.commit();
			session.close();
		}
	 
	}
	
	public void rollBackDBContext()
	{
		if(null != transaction)
		{
			transaction.commit();
			session.close();
		}
		
	 
	}
}
