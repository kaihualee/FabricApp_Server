package fabric.server.entity;

import fabric.common.db.StyleEntityImpl;


/**
 * 场景风格
 * @author likaihua
 *
 */
public class SceneStyle extends StyleEntityImpl {
    
    public SceneStyle() {
    }
    
    /**
     * @param name
     */
    public SceneStyle(String name) {
        this.name = name;
    }
    
    /**
     * @param id
     */
    public SceneStyle(Long id) {
        this.id = id;
    }
	
}
