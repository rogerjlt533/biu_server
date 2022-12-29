package com.zuosuo.treehole.service;

import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.treehole.factory.NoteCacheListCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component("HoleService")
public class HoleService {

    @Autowired
    private BiuDbFactory biuDbFactory;

    /**
     * 获取树洞评论用户列表与计数
     * @param noteId
     * @return
     */
    public NoteCacheListCount getNoteCommentListCount(long noteId) {
        NoteCacheListCount<Long> listCount = new NoteCacheListCount<>();
        String sql = "select count(0) as ts_count, GROUP_CONCAT(hnc.user_id) as users\n" +
                "from biu_hole_note_comments hnc\n" +
                "left join biu_users hnu on hnc.user_id=hnu.id\n" +
                "where hnc.note_id=" + noteId + " and hnc.comment_id=0 and hnu.use_status=1 and hnc.deleted_at IS NULL;";
        Map<String, Object> row = biuDbFactory.getUserDbFactory().getBiuUserImpl().executeRow(sql);
        if (row != null) {
            long count = Long.valueOf(String.valueOf(row.get("ts_count")));
            listCount.count += count;
            if (count > 0) {
                Arrays.asList(String.valueOf(row.get("users")).split(",")).stream().map(item -> Long.valueOf(item)).forEach(item -> {
                    if (!listCount.list.contains(item)) {
                        listCount.list.add(item);
                    }
                });
            }
        }
        sql = "select count(0) as ts_count, GROUP_CONCAT(hnc.user_id) as users\n" +
                "from biu_hole_note_comments hnc\n" +
                "left join biu_users hnu on hnc.user_id=hnu.id\n" +
                "left join biu_users hncu on hnc.comment_userid=hncu.id\n" +
                "where hnc.note_id=" + noteId + " and hnu.use_status=1 and hnc.comment_id>0 and hncu.use_status=1 and hnc.deleted_at IS NULL;";
        row = biuDbFactory.getUserDbFactory().getBiuUserImpl().executeRow(sql);
        if (row != null) {
            long count = Long.valueOf(String.valueOf(row.get("ts_count")));
            listCount.count += count;
            if (count > 0) {
                Arrays.asList(String.valueOf(row.get("users")).split(",")).stream().map(item -> Long.valueOf(item)).forEach(item -> {
                    if (!listCount.list.contains(item)) {
                        listCount.list.add(item);
                    }
                });
            }
        }
        return listCount;
    }
}
