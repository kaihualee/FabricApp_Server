package fabric.server.manager;

import java.util.List;
import java.util.Set;

import fabric.common.db.BusinessManager;
import fabric.common.db.Status;
import fabric.server.entity.Account;
import fabric.server.entity.FlowerType;
import fabric.server.entity.FlowerTypeTag;

public interface FlowerTypeManager extends BusinessManager<FlowerType> {
    
    /**
     * 更新花型标签
     * @param account
     * @param tags
     * @param ft
     */
    public void update(Account account, Set<FlowerTypeTag> tags, FlowerType ft);
    
    /**
     * 
     * @param account
     * @param status
     * @return
     */
    public List<FlowerType> getAllByAccountAndStatus(Account account,Status... status);
    
    /**
     * @param tag
     * @return
     */
    public List<FlowerType> getAllByTag(FlowerTypeTag tag);
}
