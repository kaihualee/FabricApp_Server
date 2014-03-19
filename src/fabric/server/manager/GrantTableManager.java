/**
 * @(#)GrantTableManager.java, 2013-7-7. 
 * 
 */
package fabric.server.manager;

import java.util.List;
import java.util.Set;

import fabric.common.db.BusinessEntityImpl;
import fabric.common.db.BusinessManager;
import fabric.common.db.Status;
import fabric.server.entity.Account;
import fabric.server.entity.GrantTable;
import fabric.server.entity.ProductType;
import fabric.server.entity.Scheme;
import fabric.server.entity.Shop;

/**
 * @author likaihua
 */
public interface GrantTableManager extends BusinessManager<GrantTable> {

    /**
     * 根据乙方,产品ID，产品类型 只有商家才可以查询权限表
     * 
     * @param account
     * @param id
     * @return
     */
    public boolean authorization(Account account, Long id, ProductType type);

    /**
     * 根据甲方,乙方来查询所有授权表
     * 
     * @param partA
     * @param partB
     * @return
     */
    //public List<GrantTable> getAllByPAPB(Account partA, Account partB);

    /**
     * 根据乙方,产品类型,状态查询所有授权表
     * 
     * @param account
     * @param type
     * @return
     */
    public List<GrantTable> getAllByTypeAndStatus(Account partB,
        ProductType type, Status... status);

    /**
     * 根据甲方,产品类型,状态查询所有授权表
     * 
     * @param partA
     * @param type
     * @param status
     * @return
     */
    public List<GrantTable> getAllByPAAndTypeAndStatus(Account partA,
        ProductType type, Status... status);

    /**
     * 批量授权产品
     * 
     * @param <T>
     * @param owner
     * @param partA
     * @param partB
     * @param list
     * @param type
     */
    public <T extends BusinessEntityImpl> void grantlist(Account owner,
        Account partA, Account partB, Set<T> list, ProductType type);

    /**
     * 批量删除授权产品
     * 
     * @param <T>
     * @param owner
     * @param partA
     * @param partB
     * @param list
     * @param type
     */
    public void deleteByList(Account partA, List<Long> list);

    /**
     * 乙方,产品类型，产品id，状态集合
     * @param partB
     * @param type
     * @param id
     * @param status
     * @return
     */
    public GrantTable getByProductIdAndStatus(Account partB, ProductType type,
        Long id, Status... status);


    /**
     * 根据甲方,授权类型，授权产品ID，
     * 逻辑删除
     * @param partA
     * @param type
     * @param productID
     */
    public void deleteByPAAndTypeAndID(Account partA, ProductType type,
        Long productID);
}
