/**
 * @(#)GrantTableDao.java, 2013-7-5. 
 * 
 */
package fabric.server.dao;

import java.util.List;

import fabric.common.db.BusinessDao;
import fabric.common.db.Status;
import fabric.server.entity.Account;
import fabric.server.entity.GrantTable;
import fabric.server.entity.ProductType;
import fabric.server.entity.Shop;

/**
 * @author likaihua
 */
public interface GrantTableDao extends BusinessDao<GrantTable> {
    /**
     * 根据甲方 查询所有授权给别人的授权表 默认Normal,Disable
     * 
     * @param account
     * @return
     */
    public List<GrantTable> getAllByPA(Long partAId);

    /**
     * 根据甲方,产品类型,状态查询所有授权列表
     * @param partAId
     * @param type
     * @param status
     * @return
     */
    public List<GrantTable> getAllByPAAndTypeAndStatus(Long partAId,
        ProductType type, Status... status);

    /**
     * 根据甲方,乙方查询所有授权表 默认Normal,Disable
     * 
     * @param partA
     * @param partB
     * @return
     */
    public List<GrantTable> getAllByPAPB(Long partAId, Long partBId);

    /**
     * 根据乙方,产品类型,状态查询所有授权列表
     * 
     * @param account
     * @param type
     * @return
     */
    public List<GrantTable> getAllByTypeAndStatus(Long partB, ProductType type,
        Status... status);

    /**
     * 根据产品ID，产品类型,乙方ID 默认Normal
     * 
     * @param id
     * @param type
     * @return
     */
    public GrantTable getByID(Long partBId, Long id, ProductType type);
    
    /**
     * 根据产品ID，产品类型,甲方(可以为null),乙方ID,状态集合
     * @param partAId
     * @param partBId
     * @param id
     * @param type
     * @return
     */
    public GrantTable getByIDAndStatus(Long partAId, Long partBId,
        ProductType type, Long productID, Status... status) ;

    
    /**
     * 根据甲方,授权类型，授权产品ID，删除
     * 
     * @param partA
     * @param type
     * @param productID
     */
    public void deleteByPAAndTypeAndID(Long partA, ProductType type,
        Long productID);
}
