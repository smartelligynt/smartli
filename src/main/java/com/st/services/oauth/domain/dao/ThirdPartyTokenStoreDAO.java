package com.st.services.oauth.domain.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.st.services.oauth.domain.entity.ThirdPartyTokenStoreEntity;
@Component("thirdPartyTokenStoreDAO")
public class ThirdPartyTokenStoreDAO {
	
	@Autowired
	@Qualifier("hibernate4AnnotatedSessionFactory")
	private SessionFactory sessionFactory;


    

	public void save(ThirdPartyTokenStoreEntity thirdPartyTokenStoreEntity) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(thirdPartyTokenStoreEntity);
		tx.commit();
		session.close();
	}

	public List<ThirdPartyTokenStoreEntity> getByCustomerIdAndTP(String customerId, String tpName) {
		Session session = this.sessionFactory.openSession();
		List<ThirdPartyTokenStoreEntity> tpTokeList = session.createQuery("from ThirdPartyTokenStoreEntity tse where tse.customerId = ? and tse.tpName=?")
		.setParameter(0, customerId)
		.setParameter(1, tpName).list();
		session.close();
		return tpTokeList;
	}

}
