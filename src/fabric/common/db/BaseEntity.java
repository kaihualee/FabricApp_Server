/**
 * @(#)BaseEntity.java, 2012-3-13. Copyright 2012 Netease, Inc. All rights reserved.
 *                NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license
 *                terms.
 */
package fabric.common.db;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nisonghai
 */
/* 记录对象id和创建时间和序列化*/
public interface BaseEntity extends Serializable, Cloneable {

    public Date getCreateDate();

    public Long getId();

    public Date getModifyTime();

    public void setCreateDate(Date createDate);
    
    public void setId(Long id);
    
    public void setModifyTime(Date modifyTime);

}
