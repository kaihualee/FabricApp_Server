package fabric.server.dao;

import java.util.List;


import fabric.common.db.BusinessDao;
import fabric.common.db.Status;
import fabric.server.entity.FlowerType;

/**
 * @author likaihua
 */
public interface FlowerTypeDao extends BusinessDao<FlowerType> {
    /**
     * 根据标签id查询所有使用该标签的花型 Deleted状态花型不算
     * 
     * @param tagId
     * @return
     */
    public List<FlowerType> getAllByTagId(Long tagId);

    /**
     * 根据花型ID，所有者ID，状态集查询
     * @param ids
     * @param ownerID
     * @param status
     * @return
     */
    public List<FlowerType> getAllByIdsAndOwnerIdAndStatus(List<Long> ids,
        Long ownerID, Status... status);
}
