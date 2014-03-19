/**
 * @(#)StyleEntityImpl.java, 2013-6-29. Copyright 2013 Netease, Inc. All rights
 *                           reserved. NETEASE PROPRIETARY/CONFIDENTIAL. Use is
 *                           subject to license terms.
 */
package fabric.common.db;

import java.util.List;

import fabric.common.utils.BeanUtils;

/**
 * 各类业务对象样式Style实体父类
 * 
 * @author nisonghai
 */
public class StyleEntityImpl extends LogicEntityImpl implements VersionEntity {

    /**
     * 版本
     */
    protected int version = 1;

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    @Override
    public void setEntity(BaseEntity entity) {
        List<String> fields = BeanUtils.getPropertyNames(this.getClass());
        for (String fieldName: fields) {
            if (fieldName.equals("version")) {
                continue;
            }
            try {
                Object newValue = BeanUtils.forceGetProperty(entity, fieldName);
                if (newValue != null) {
                    BeanUtils.forceSetProperty(this, fieldName, newValue);
                }
            } catch (NoSuchFieldException e) {
                // ignore
            }
        }
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + " " + id + " " + status + " "
            + version + "]" + name;
    }

}
