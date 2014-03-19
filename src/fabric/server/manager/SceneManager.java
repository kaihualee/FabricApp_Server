package fabric.server.manager;

import java.util.List;

import fabric.common.db.BusinessManager;
import fabric.server.entity.Account;
import fabric.server.entity.Scene;

public interface SceneManager extends BusinessManager<Scene> {
    /**
     * 根据账户取得所有的场景列表
     * 当前无权限判断
     */
    public List<Scene> getAll(Account account);
}
