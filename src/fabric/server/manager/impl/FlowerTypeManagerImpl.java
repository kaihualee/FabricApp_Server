package fabric.server.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.BusinessManagerImpl;
import fabric.common.db.Status;
import fabric.server.dao.FlowerTypeDao;
import fabric.server.dao.GrantTableDao;
import fabric.server.entity.Account;
import fabric.server.entity.DataFile;
import fabric.server.entity.FlowerType;
import fabric.server.entity.FlowerTypeTag;
import fabric.server.entity.GrantTable;
import fabric.server.entity.ProductType;
import fabric.server.entity.Scheme;
import fabric.server.entity.UserRule;
import fabric.server.manager.DataFileManager;
import fabric.server.manager.FlowerTypeManager;
import fabric.server.manager.GrantTableManager;

@Service
public class FlowerTypeManagerImpl extends
    BusinessManagerImpl<FlowerType, FlowerTypeDao> implements FlowerTypeManager {

    @Autowired
    private FlowerTypeDao flowerTypeDao;

    @Autowired
    private DataFileManager dataFileManager;

    @Autowired
    private GrantTableManager gtManager;

    @Autowired
    private GrantTableDao grantTableDao;

    @Override
    public void save(FlowerType flowerType) {
        saveDataFile(flowerType);

        flowerTypeDao.save(flowerType);
    }

    private void saveDataFile(FlowerType flowerType) {
        if (flowerType.getCoverImage() != null) {
            dataFileManager.saveOrUpdate(flowerType.getCoverImage());
        }
        if (flowerType.getPrintImage() != null) {
            dataFileManager.saveOrUpdate(flowerType.getPrintImage());
        }
        if (flowerType.getXmlFile() != null) {
            dataFileManager.saveOrUpdate(flowerType.getXmlFile());
        }
    }

    @Override
    public void update(FlowerType flowerType) {
        FlowerType dbFlowerType = flowerTypeDao.get(flowerType.getId());
        if (dbFlowerType.getCoverImage() != null
            && flowerType.getCoverImage() != null
            && !dbFlowerType.getCoverImage().getId()
                .equals(flowerType.getCoverImage().getId())) {
            dataFileManager.remove(dbFlowerType.getCoverImage());
        }
        if (dbFlowerType.getXmlFile() != null
            && flowerType.getXmlFile() != null
            && !dbFlowerType.getXmlFile().getId()
                .equals(flowerType.getXmlFile().getId())) {
            dataFileManager.remove(dbFlowerType.getXmlFile());
        }
        if (dbFlowerType.getPrintImage() != null
            && flowerType.getPrintImage() != null
            && !dbFlowerType.getPrintImage().getId()
                .equals(flowerType.getPrintImage().getId())) {
            dataFileManager.remove(dbFlowerType.getPrintImage());
        }

        dbFlowerType.setEntity(flowerType);
        flowerTypeDao.update(dbFlowerType);
        flowerTypeDao.merge(flowerType);
    }

    @Override
    public void update(Account account, Set<FlowerTypeTag> tags, FlowerType ft) {
        ft.setTags(tags);
        super.update(account, ft);
    }

    @Override
    public void saveOrUpdate(FlowerType flowerType) {
        if (flowerType.getId() == null) {
            save(flowerType);
        } else {
            update(flowerType);
        }
    }

    /**
     * Normal, Disable状态的花型
     */
    @Override
    public FlowerType getById(Account account, Long id) {
        if (account.getRule() == UserRule.SUPER_ADMIN) {
            return flowerTypeDao.getById(id);
        }
        FlowerType ft;
        ft = super.getById(account, id);
        if (ft != null) {
            return ft;
        }
        if (gtManager.authorization(account, id, ProductType.FlowerType)) {
            ft = flowerTypeDao.getByStatusAndId(id, Status.Normal,
                Status.Disabled);
        }
        return ft;

    }

    @Override
    public List<FlowerType> getAll(Account account) {
        if (account.getRule() == UserRule.SUPER_ADMIN) {
            return flowerTypeDao.getAll();
        }
        return getAllByAccountAndStatus(account, Status.Normal, Status.Disabled);
    }

    @Override
    public List<FlowerType> getAllByAccountAndStatus(Account account,
        Status... status) {
        List<FlowerType> result = null;
        result = flowerTypeDao
            .getAllByOwnerIdAndStatus(account.getId(), status);
        List<GrantTable> gtList = gtManager.getAllByTypeAndStatus(
            account, ProductType.FlowerType, status);
        if (gtList == null) {
            return result;
        }
        if (result == null) {
            result = new ArrayList<FlowerType>();
        }
        List<Long> ids = new ArrayList();
        for (GrantTable item: gtList) {
            ids.add(item.getProduct());
        }
        //批量获取授权的花型
        List<FlowerType> flowers = flowerTypeDao.getListByIdAndStatus(ids,
            Status.Normal);
        result.addAll(flowers);
        return result;
    }

    @Override
    public void delete(FlowerType flower) {
        DataFile dataFile = flower.getCoverImage();
        if (dataFile != null) {
            dataFileManager.delete(dataFile);
        }
        dataFile = flower.getXmlFile();
        if (dataFile != null) {
            dataFileManager.delete(dataFile);
        }
        dataFile = flower.getPrintImage();
        if (dataFile != null) {
            dataFileManager.delete(dataFile);
        }
        gtManager.deleteByPAAndTypeAndID(flower.getOwner(),
            ProductType.FlowerType, flower.getId());
        super.delete(flower);
    }

    public List<FlowerType> getAllByTag(FlowerTypeTag tag) {
        if (tag == null) {
            return null;
        }
        return flowerTypeDao.getAllByTagId(tag.getId());
    }
}
