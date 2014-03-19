/**
 * @(#)GrantTableManagerImpl.java, 2013-7-7. 
 * 
 */
package fabric.server.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.BusinessEntityImpl;
import fabric.common.db.BusinessManagerImpl;
import fabric.common.db.Status;
import fabric.common.web.ErrorCode;
import fabric.common.web.ErrorVO;
import fabric.server.dao.GrantTableDao;
import fabric.server.entity.Account;
import fabric.server.entity.GrantTable;
import fabric.server.entity.ProductType;
import fabric.server.entity.Scheme;
import fabric.server.entity.Shop;
import fabric.server.entity.UserRule;
import fabric.server.manager.GrantTableManager;

/**
 * @author likaihua
 */
@Service
public class GrantTableManagerImpl extends
    BusinessManagerImpl<GrantTable, GrantTableDao> implements GrantTableManager {

    @Autowired
    private GrantTableDao grantTableDao;

    @Override
    public boolean authorization(Account account, Long id, ProductType type) {
        UserRule rule = account.getRule();
        GrantTable gt = grantTableDao.getByID(account.getId(), id, type);
        if (gt == null) {
            return false;
        } else {
            return true;
        }

    }

    protected boolean checkAuth(Account partA, Account partB) {
        UserRule ruleA = partA.getRule();
        UserRule ruleB = partB.getRule();
        if ((ruleA != UserRule.SUPER_ADMIN && ruleA != UserRule.DESIGNER)
            || ruleB != UserRule.BUSINESS) {
            return false;
        }
        return true;
    }

    public <T extends BusinessEntityImpl> void grantlist(Account owner,
        Account partA, Account partB, Set<T> list, ProductType type) {
        if (list == null || list.isEmpty()) {
            return;
        }
        GrantTable grantTable, grantTableTmp;
        for (T item: list) {
            /**
             * 避免重复授权
             */
            grantTableTmp = grantTableDao.getByIDAndStatus(partA.getId(),
                partB.getId(), type, item.getId(), Status.Normal,
                Status.Disabled);
            if (grantTableTmp != null) {
                continue;
            }
            grantTable = new GrantTable();
            grantTable.setOwner(owner);
            grantTable.setPartA(partA);
            grantTable.setPartB(partB);
            grantTable.setProduct(item.getId());
            grantTable.setType(type);

            grantTableDao.save(grantTable);
        }
    }

    public void deleteByList(Account partA, List<Long> list) {
        for (Long id: list) {
            GrantTable grantTable = grantTableDao.getByStatusAndId(id,
                Status.Normal, Status.Disabled);
            //权限
            if (grantTable.getPartA().getId() == partA.getId()) {
                grantTable.setStatus(Status.Deleted);
                grantTableDao.update(grantTable);
            }
        }

    }

    /*
     * public <T extends BusinessEntityImpl> void removelist(Account owner,
     * Account partA, Shop partB, Set<T> list, ProductType type) { if (list ==
     * null || list.isEmpty()) { return; } GrantTable grantTable; for (T item:
     * list) { grantTable = grantTableDao .getByID(partA, partB, type,
     * item.getId()); if (grantTable == null) { continue; }
     * grantTable.setStatus(Status.Deleted); grantTableDao.update(grantTable); }
     * }
     */
    @Override
    public void save(GrantTable grantTable) {
        grantTableDao.save(grantTable);
    }

    @Override
    public List<GrantTable> getAllByTypeAndStatus(Account partB,
        ProductType type, Status... status) {
        if (partB == null) {
            return null;
        }
        return grantTableDao.getAllByTypeAndStatus(partB.getId(), type, status);
    }

    @Override
    public List<GrantTable> getAllByPAAndTypeAndStatus(Account partA,
        ProductType type, Status... status) {
        if (partA == null) {
            return null;
        }
        return grantTableDao.getAllByPAAndTypeAndStatus(partA.getId(), type,
            status);
    }

    @Override
    public GrantTable getByProductIdAndStatus(Account partB, ProductType type,
        Long id, Status... status) {
        if (partB == null) {
            return null;
        }
        return grantTableDao.getByIDAndStatus(null, partB.getId(), type, id,
            status);
    }

    @Override
    public void deleteByPAAndTypeAndID(Account partA, ProductType type,
        Long productID) {
        if (partA == null || productID == null) {
            return;
        }
        grantTableDao.deleteByPAAndTypeAndID(partA.getId(), type, productID);
    }
}
