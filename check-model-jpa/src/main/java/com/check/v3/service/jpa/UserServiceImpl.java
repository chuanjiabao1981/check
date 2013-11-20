package com.check.v3.service.jpa;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Department;
import com.check.v3.domain.Organization;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.repository.DepartmentRepository;
import com.check.v3.repository.UserRepository;
import com.check.v3.service.UserService;
import com.check.v3.service.exception.UserAccountDuplicateException;

@Service("userService")
@Repository
@Transactional
public class UserServiceImpl implements UserService {

	@Resource
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@PersistenceContext 
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly=true)
	public User findByAccount(String account) {
		return userRepository.findByAccount(account);
	}

	@Override
	public User save(User user)  throws UserAccountDuplicateException{
		try {
				return userRepository.save(user);
		}catch(DataIntegrityViolationException ex){
			if (ex.getMessage().contains("users_unique_account")){
				throw new UserAccountDuplicateException("account");
			}else{
				throw ex;
			}
		}catch (Exception ex){
			throw ex;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public Object load(Long id) {
		return findById(id);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Override
	@Transactional
	public User createDepartmentAdmin(User user, Long department_id) {
		Department d = departmentRepository.findOne(department_id);
		user.setDepartment(d);
		user.setRole(Role.DEPARTMENT_ADMIN);
		return userRepository.save(user);
	}

	@Override
	public List<User> findAllByDepartmentId(Long departmentId) {
		CriteriaBuilder cb 					= em.getCriteriaBuilder();
		CriteriaQuery<User> c 				= cb.createQuery(User.class);
		Root<User> emp 						= c.from(User.class);
		c.select(emp).where(cb.equal(emp.get("department").get("id"), departmentId));
		return em.createQuery(c).getResultList();
	}

}
