package fabric.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fabric.common.db.DaoException;
import fabric.common.db.LogicDaoImpl;
import fabric.common.db.Status;
import fabric.server.dao.AccountDao;
import fabric.server.entity.Account;
import fabric.server.entity.Shop;
import fabric.server.entity.UserRule;

@Repository
@SuppressWarnings("unchecked")
public class AccountDaoImpl extends LogicDaoImpl<Account> implements AccountDao {

    @Override
    public Account getByName(UserRule rule, String name, Shop shop) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        if (rule != null) {
            criteria.add(Restrictions.eq("rule", rule));
        }
        criteria.add(Restrictions.eq("name", name));
        if (shop != null) {
            criteria.add(Restrictions.eq("shop", shop));
        }
        criteria.add(Restrictions.ne("status", Status.Deleted));
        List<Account> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new DaoException("Select list not only one.");
        }
    }
    public Account getByName(UserRule rule, String name){
        Criteria criteria = super.getSession().createCriteria(entityClass);
        if (rule != null) {
            criteria.add(Restrictions.eq("rule", rule));
        }
        criteria.add(Restrictions.eq("name", name));
        criteria.add(Restrictions.ne("status", Status.Deleted));
        List<Account> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new DaoException("Select list not only one.");
        }
    }
    @Override
    public boolean checkPassword(Long accountId, String password) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.eq("id", accountId));
        criteria.add(Restrictions.eq("password", password));
        criteria.add(Restrictions.ne("status", Status.Deleted));
        List<Account> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return false;
        } else if (list.size() == 1) {
            return true;
        } else {
            throw new DaoException("Select list not only one.");
        }
    }
    
    @Override
    public List<Account> getAll(){
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.ne("rule", UserRule.SUPER_ADMIN));
        criteria.add(Restrictions.ne("status", Status.Deleted));
        return criteria.list();
    }

    
    @Override
    public boolean checkNickname(UserRule rule,  Long userId, String nickname) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.eq("nickname", nickname));
        criteria.add(Restrictions.ne("id", userId));
        criteria.add(Restrictions.ne("status", Status.Deleted));
        List<Account> list = criteria.list();
        if(list == null || list.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    
    @Override
    public List<Account> getAllByRule(Long id, UserRule rule){
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.ne("id", id));
        criteria.add(Restrictions.eq("rule", rule));
        criteria.add(Restrictions.ne("status", Status.Deleted));
        return criteria.list();
    }

}
