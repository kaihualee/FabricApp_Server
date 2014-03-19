/**
 * @(#)SessionPool.java, 2013-7-15. 
 * 
 */
package fabric.server.web.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import fabric.common.db.Status;
import fabric.server.entity.Account;
import fabric.server.entity.AccountSession;
import fabric.server.entity.ClientPower;
import fabric.server.manager.AccountSessionManager;

/**
 * @author likaihua
 */
@Controller
@Lazy(false)
@Scope("singleton")
public class SessionPool implements InitializingBean {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private final Long timeout = 60 * 60 * 1000L;

    private final Long interval = 30 * 60 * 1000L;

    private static Random rand = new Random();

    private ConcurrentMap<Integer, AccountSession> mapSessions = new ConcurrentHashMap<Integer, AccountSession>();

    private Timer timer;

    private boolean INIT_FLAG = false;

    @Autowired
    private AccountSessionManager accountSessionManager;

    /**
     * 插入会话池 如果已经登陆过则直接返回会话信息
     * 
     * @param account
     * @param ip
     * @return
     */
    public AccountSession insert(Account account, String ip, ClientPower power) {

        /**
         * 同账号同IP,同界面只允许一个Sid存在
         */
        AccountSession accountSessionTmp = accountSessionManager
            .getByAccountByIPAndPower(account, ip, power);
        if (accountSessionTmp != null) {
            logger.info("[account]: username= {}, ip= {} login again.",
                new Object[] { account.getName(), ip });
            return accountSessionTmp;
        }
        String info;
        int sid = randInt();
        AccountSession session = new AccountSession();
        session.setAccount(account);
        session.setIp(ip);
        session.setModifyTime(new Date());
        session.setSid(String.valueOf(sid));

        /**
         * ClientPower记录登录权限（需要修改）
         */
        session.setPower(power);

        /**
         * 需要整改，挪位置调整顺序
         */
        if (mapSessions.putIfAbsent(sid, session) == null) {
            info = String.format("insert session. sid=%d, UserName=%s", sid,
                account.getName());
            accountSessionManager.saveOrUpdate(session);
        } else {
            info = String.format(
                "insert session. sid=%d, UserName=%s duplicate", sid,
                account.getName());
            session = new AccountSession();
            session.setSid("-1");
        }

        logger.info(info);
        return session;
    }

    /**
     * 查找会话池中是否已经存在
     * 
     * @param sid
     * @param ip
     * @return
     */
    public AccountSession find(int sid, String ip) {
        String info;
        AccountSession session = mapSessions.get(sid);
        if (session == null) {
            logger.debug("invalid sid: {}. ClientIP: {}.", new Object[] { sid,
                ip });
        } else if (session.getIp().equals(ip)) {
            logger
                .debug("find sid: {}, ClienIP: {}.", new Object[] { sid, ip });
            //session.setModifyTime(new Date());
            //accountSessionManager.update(session);
        } else {
            session = null;
        }
        return session;
    }

    public void remove(AccountSession session) {
        if (session == null) {
            return;
        } else {
            logger.info("Remove session sid:{} user:{} Ip:{} power:{}.",
                new Object[] { session.getSid(),
                    session.getAccount().getName(), session.getIp(),
                    session.getAccount().getRule().getName() });
            Long id = session.getId();
            mapSessions.remove(Integer.valueOf(session.getSid()));
            accountSessionManager.deleteById(id);

        }
    }

    /**
     * 定时超时的会话
     */

    //@Scheduled(fixedDelay = 30 * 60 * 1000L)
    @Scheduled(cron = "59 59 23 * * ?")
    public void run() {
        Date sessionDate;
        Long currentTime = new Date().getTime();
        List<AccountSession> deletelist = new ArrayList();
        for (Map.Entry mapEntry: mapSessions.entrySet()) {
            Integer sid = (Integer) mapEntry.getKey();
            AccountSession item = (AccountSession) mapEntry.getValue();
            sessionDate = item.getModifyTime();
            Long time = sessionDate.getTime();
            if (currentTime - time > timeout) {
                deletelist.add(item);
            }
        }
        for (AccountSession item: deletelist) {
            remove(item);
        }
    }

    /**
     * 随机生成正整数
     * 
     * @return
     */
    public int randInt() {
        int id;
        while (true) {
            id = rand.nextInt();
            if (id < 0) {
                continue;
            }
            if (mapSessions.containsKey(id) == false) {
                break;
            }
        }
        return id;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /**
         * 第一次插入初始化
         */
        if (INIT_FLAG == false) {
            synchronized (getClass()) {
                if (INIT_FLAG == false) {
                    List<AccountSession> list = accountSessionManager
                        .getAllByStatus(Status.Normal);
                    if (list == null || list.isEmpty()) {} else {
                        for (AccountSession item: list) {
                            mapSessions.putIfAbsent(
                                Integer.valueOf(item.getSid()), item);
                            String info = String.format(
                                "load sessions sid : %s", item.getSid());
                            logger.info(info);
                        }
                    }
                    INIT_FLAG = true;
                }
            }
        }

    }
}
