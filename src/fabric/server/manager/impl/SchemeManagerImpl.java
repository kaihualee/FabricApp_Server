package fabric.server.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.BusinessManagerImpl;
import fabric.common.db.Status;
import fabric.server.dao.SchemeDao;
import fabric.server.entity.Account;
import fabric.server.entity.ClientPower;
import fabric.server.entity.DataFile;
import fabric.server.entity.FlowerType;
import fabric.server.entity.GrantTable;
import fabric.server.entity.ProductType;
import fabric.server.entity.Scheme;
import fabric.server.entity.UserRule;
import fabric.server.manager.DataFileManager;
import fabric.server.manager.FlowerTypeManager;
import fabric.server.manager.GrantTableManager;
import fabric.server.manager.SchemeManager;

@Service
public class SchemeManagerImpl extends BusinessManagerImpl<Scheme, SchemeDao>
    implements SchemeManager {

    @Autowired
    private SchemeDao schemeDao;

    @Autowired
    private GrantTableManager gtManager;

    @Autowired
    private DataFileManager dataFileManager;

    @Autowired
    private FlowerTypeManager flowerTypeManager;

    @Override
    public Scheme getById(Account account, Long id) {
        if (account.getRule() == UserRule.SUPER_ADMIN) {
            return schemeDao.getById(id);
        }
        Scheme scheme = super.getById(account, id);
        if (scheme != null) {
            return scheme;
        }

        if (gtManager.authorization(account, id, ProductType.Scheme)) {
            scheme = schemeDao.getNormalById(id);
        }
        return scheme;
    }

    @Override
    public List<Scheme> getAll(Account account, ClientPower power) {
        if (account.getRule() == UserRule.SUPER_ADMIN) {
            return schemeDao.getAll();
        }

        if (power == ClientPower.SHOW_SHOP) {
            return this.getAllByAccountAndStatus(account, Status.Normal);
        } else {
            return this.getAllByAccountAndStatus(account, Status.Normal,
                Status.Disabled);
        }

    }

    public List<Scheme> getAllByAccountAndStatus(Account account,
        Status... status) {
        List<Scheme> result = null;
        result = schemeDao.getAllByOwnerIdAndStatus(account.getId(), status);
        List<GrantTable> gtList = gtManager.getAllByTypeAndStatus(account,
            ProductType.Scheme, status);
        if (gtList == null) {
            return result;
        }
        if (result == null) {
            result = new ArrayList<Scheme>();
        }

        
        Map<Long, Status> map = new HashMap();
        List<Long> ids = new ArrayList();
        for (GrantTable item: gtList) {
            map.put(item.getProduct(), item.getStatus());
            ids.add(item.getProduct());
        }
        List<Scheme> schemes = schemeDao.getListByIdAndStatus(ids, status);
        for (Scheme item: schemes) {
            item.setStatus(map.get(item.getId()));
            result.add(item);
        }
    
       /*
        for (GrantTable item: gtList) {
            Scheme scheme = schemeDao.getByStatusAndId(item.getProduct(),
                status);
            // if (!result.contains(scheme)) {
            //   Scheme schemeTmp = new Scheme();
            //  schemeTmp.setEntity(scheme);
            scheme.setStatus(item.getStatus());
            result.add(scheme);
            //}
        }   */
        return result;
    }

    @Override
    public boolean setStatusByAccount(Account partB, Long id, Status status) {
        if (partB == null || id == null) {
            return false;
        }

        GrantTable grantTable = gtManager.getByProductIdAndStatus(partB,
            ProductType.Scheme, id, Status.Normal, Status.Disabled);
        if (grantTable == null) {
            return false;
        }
        grantTable.setStatus(status);
        gtManager.update(grantTable);
        return true;
    }

    @Override
    public void delete(Scheme scheme) {
        DataFile dataFile = scheme.getCoverImage();
        if (dataFile != null) {
            dataFileManager.delete(dataFile);
        }
        dataFile = scheme.getXmlFile();
        if (dataFile != null) {
            dataFileManager.delete(dataFile);
        }
        gtManager.deleteByPAAndTypeAndID(scheme.getOwner(), ProductType.Scheme,
            scheme.getId());
        Set<FlowerType> flowers = scheme.getFlowers();
        if (flowers != null && !flowers.isEmpty()) {
            for (FlowerType item: flowers) {
                Long refTimes = item.getReferences();
                refTimes--;
                item.setReferences(refTimes);
                flowerTypeManager.update(item);
            }
        }
        super.delete(scheme);
    }

    @Override
    public List<Scheme> getAllByFlowerTypeId(Long flowerTypeId) {
        return schemeDao.getAllByFlowerTypeIdAndStatus(flowerTypeId,
            Status.Normal, Status.Disabled);
    }

    @Override
    public void update(Scheme scheme, Set<FlowerType> flowers) {
        if (flowers != null && !flowers.isEmpty()) {
            for (FlowerType item: flowers) {
                Long refTimes = item.getReferences();
                refTimes--;
                item.setReferences(refTimes);
                flowerTypeManager.update(item);
            }
        }
        flowers = scheme.getFlowers();
        if (flowers != null && !flowers.isEmpty()) {
            for (FlowerType item: flowers) {
                Long refTimes = item.getReferences();
                refTimes++;
                item.setReferences(refTimes);
                flowerTypeManager.update(item);
            }
        }
        super.update(scheme);
    }

    public void updateWithoutFlower(Scheme scheme) {
        super.update(scheme);
    }
}
