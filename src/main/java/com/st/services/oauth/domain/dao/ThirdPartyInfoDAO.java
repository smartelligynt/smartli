package com.st.services.oauth.domain.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.st.services.oauth.domain.entity.ThirdPartyInfoEntity;

@Component("thirdPartyInfoDAO")
public class ThirdPartyInfoDAO {

	@Autowired
	@Qualifier("hibernate4AnnotatedSessionFactory")
	private SessionFactory sessionFactory;

	public void save(ThirdPartyInfoEntity thirdPartyInfoEntity) {
		Session session = null;
		try {

			session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			thirdPartyInfoEntity.setId(UUID.randomUUID().toString());
			session.persist(thirdPartyInfoEntity);
			tx.commit();
		} finally {
			session.close();
		}

	}

	public List<ThirdPartyInfoEntity> getAll() {
		Session session = null;
		try {
			session = this.sessionFactory.openSession();

			return (List<ThirdPartyInfoEntity>) session.createQuery(
					"from ThirdPartyInfoEntity where 1=1").list();

		} finally {
			session.close();
		}
	}

}
