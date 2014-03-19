package fabric.server.dao;

import java.util.List;

import fabric.common.db.BusinessDao;
import fabric.common.db.Status;
import fabric.common.utils.PageBean;
import fabric.server.entity.Scheme;

/**
 * @author likaihua
 */
public interface SchemeDao extends BusinessDao<Scheme> {

    /**
     * 根据花型ID,Page,状态集来查询方案
     * 
     * @param flowerId
     * @param pageBean
     * @param status
     * @return
     */
    public List<Scheme> getAllByFlowerTypeIdAndStatus(Long flowerId,
        PageBean pageBean, Status... status);

    public List<Scheme> getAllByFlowerTypeIdAndStatus(Long flowerId,
        Status... status);
}
