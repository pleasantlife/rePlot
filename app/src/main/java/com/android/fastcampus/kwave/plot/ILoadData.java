package com.android.fastcampus.kwave.plot;

import com.android.fastcampus.kwave.plot.DataSource.ServerData;

import java.util.List;

/**
 * Created by maxx on 2017. 8. 7..
 */

public interface ILoadData {
    public void setServerData(List<ServerData> data);
    public String getUrl();
}
