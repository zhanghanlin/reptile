package com.demo.java.collector.util;

import com.demo.java.collector.model.CrawlDatum;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

public class CrawlDatumFormater {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String datumToString(CrawlDatum datum) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nKEY: ").append(datum.getKey())
                .append("\nURL: ").append(datum.getUrl())
                .append("\nSTATUS: ");
        switch (datum.getStatus()) {
            case CrawlDatum.STATUS_DB_SUCCESS:
                sb.append("success");
                break;
            case CrawlDatum.STATUS_DB_FAILED:
                sb.append("failed");
                break;
            case CrawlDatum.STATUS_DB_UNEXECUTED:
                sb.append("unexecuted");
                break;
        }
        sb.append("\nExecuteTime:").append(sdf.format(new Date(datum.getExecuteTime())))
                .append("\nExecuteCount:").append(datum.getExecuteCount());
        int metaIndex = 0;
        for (Entry<String, String> entry : datum.getMetaData().entrySet()) {
            sb.append("\nMETA").append("[").append(metaIndex++).append("]:(")
                    .append(entry.getKey()).append(",").append(entry.getValue()).append(")");
        }
        sb.append("\n");
        return sb.toString();
    }

    public static CrawlDatum jsonStrToDatum(String crawlDatumKey, String str) {
        JSONArray jsonArray = new JSONArray(str);
        CrawlDatum crawlDatum = new CrawlDatum();
        crawlDatum.setKey(crawlDatumKey);
        crawlDatum.setUrl(jsonArray.getString(0));
        crawlDatum.setStatus(jsonArray.getInt(1));
        crawlDatum.setExecuteTime(jsonArray.getLong(2));
        crawlDatum.setExecuteCount(jsonArray.getInt(3));
        if (jsonArray.length() == 5) {
            JSONObject metaJSONObject = jsonArray.getJSONObject(4);
            for (Object keyObject : metaJSONObject.keySet()) {
                String key = keyObject.toString();
                String value = metaJSONObject.getString(key);
                crawlDatum.meta(key, value);
            }
        }
        return crawlDatum;
    }

    public static String datumToJsonStr(CrawlDatum datum) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(datum.getUrl());
        jsonArray.put(datum.getStatus());
        jsonArray.put(datum.getExecuteTime());
        jsonArray.put(datum.getExecuteCount());
        if (!datum.getMetaData().isEmpty()) {
            jsonArray.put(new JSONObject(datum.getMetaData()));
        }
        return jsonArray.toString();
    }
}