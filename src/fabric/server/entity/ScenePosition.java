package fabric.server.entity;

import fabric.common.db.StyleEntityImpl;

/**
 * 场景应用位置
 * @author likaihua
 *
 */
public class ScenePosition extends StyleEntityImpl {
    
    public ScenePosition() {
    }
    
    /**
     * @param name
     */
    public ScenePosition(String name) {
        this.name = name;
    }
    
    /**
     * @param id
     */
    public ScenePosition(Long id) {
        this.id = id;
    }
	
}
