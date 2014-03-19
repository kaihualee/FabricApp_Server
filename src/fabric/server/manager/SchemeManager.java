package fabric.server.manager;

import java.util.List;
import java.util.Set;

import fabric.common.db.BusinessManager;
import fabric.common.db.Status;
import fabric.server.entity.Account;
import fabric.server.entity.ClientPower;
import fabric.server.entity.FlowerType;
import fabric.server.entity.Scheme;

public interface SchemeManager extends BusinessManager<Scheme> {

    /**
     * 根据账户和登录界面获取方案列表
     * @param account
     * @param power
     * @return
     */
    public List<Scheme>  getAll(Account account, ClientPower power);
    
    /**
     * 
     * @param account
     * @param enable
     * @return
     */
    public boolean setStatusByAccount(Account partB,Long id, Status status);
    
    /**
     * @param flowerTypeId
     * @return
     */
    public List<Scheme> getAllByFlowerTypeId(Long flowerTypeId);
    
    /**
     * 更新方案以及花型的应用次数
     * @param scheme
     * @param flowers
     */
    public void update(Scheme scheme, Set<FlowerType> flowers);
    
    public void updateWithoutFlower(Scheme scheme);
}
