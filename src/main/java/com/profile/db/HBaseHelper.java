package com.profile.db;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.profile.util.TextUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HBaseHelper {
    private static HBaseHelper sInstance;

    private Admin mAdmin;
    private HTable mTable;
    private String mColumnFamily = "tag";

    public static HBaseHelper getInstance(String[] args) {
        if (sInstance == null) {
            synchronized (HBaseHelper.class) {
                if (sInstance == null) {
                    try {
                        sInstance = new HBaseHelper(args);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }

        return sInstance;
    }

    public HBaseHelper(String[] args) throws IOException {
        String zookeeperIp = "192.168.57.141";
        String zookeeperPort = "2181";

        TableName tableName = TableName.valueOf("profile");
        if (args != null) {
            zookeeperIp = args.length > 0 ? args[0] : zookeeperIp;
            zookeeperPort = args.length > 1 ? args[1] : zookeeperPort;
            tableName = args.length > 2 ? TableName.valueOf(args[2]) : tableName;
            mColumnFamily = args.length > 3 ? args[3] : mColumnFamily;
        }

        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", zookeeperIp);
        config.set("hbase.zookeeper.property.clientPort", zookeeperPort);

        mAdmin = ConnectionFactory.createConnection(config).getAdmin();

        if (!mAdmin.tableExists(tableName)) {
            HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
            HColumnDescriptor columnDescriptor = new HColumnDescriptor(mColumnFamily);

            tableDescriptor.addFamily(columnDescriptor);
            mAdmin.createTable(tableDescriptor);

        }

        mTable = new HTable(config, tableName);
    }

    public void insertOneMedia(JSONObject jsonObject) throws IOException {
        String id = jsonObject.getString("id");

        if (!TextUtils.IDLengthSet() && !TextUtils.isNullValue(id)) {
            TextUtils.setIDLength(id.length());
        }

        id = !TextUtils.isNullValue(id) ? id : TextUtils.generateRandomID();

        byte[] row = Bytes.toBytes(id);
        byte[] columnFamily = Bytes.toBytes(mColumnFamily);

        Put put = new Put(row);

        for (String s : jsonObject.keySet()) {
            byte[] identifier = Bytes.toBytes(s);

            String value = jsonObject.get(s).toString();
            if (s.equals("id") && TextUtils.isNullValue(value)) {
                value = id;
            }

            put.add(columnFamily, identifier, Bytes.toBytes(value));
            mTable.put(put);
        }
    }

    public void insertMedias(JSONArray jsonArray) throws IOException {
        for (Object o : jsonArray) {
            insertOneMedia((JSONObject) o);
        }
    }

    public void scanMedias() throws IOException {
        Scan scan = new Scan();
        ResultScanner scanner = mTable.getScanner(scan);
        for (Result item : scanner) {
            for (Cell cell : item.listCells()) {
                String row = new String(cell.getRow(), "utf-8");
                String columnFamily = new String(cell.getFamily(), "utf-8");
                String identifier = new String(cell.getQualifier(), "utf-8");
                String value = new String(cell.getValue(), "utf-8");

                System.out.println("row:" + row + ", (columnFamily, identifier): (" + columnFamily +
                        "," + identifier + "), value: " + value);
            }
        }
    }

    public void close() throws IOException {
        mTable.close();
        mAdmin.close();
    }
}
