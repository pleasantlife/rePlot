package com.android.fastcampus.kwave.plot;

import android.os.AsyncTask;
import android.util.Log;

import com.android.fastcampus.kwave.plot.DataSource.ServerData;
import com.google.gson.Gson;

import java.util.List;

import static com.android.fastcampus.kwave.plot.GetDataFromServer.getData;
import static com.android.fastcampus.kwave.plot.MainActivity.datas;

/**
 * Created by maxx on 2017. 8. 7..
 */

public class ThrowData2Activity {
    public static void task(final ILoadData iLoadData) {

        new AsyncTask<String, Void, List<ServerData>>() {
            String result = null;

            @Override
            protected List<ServerData> doInBackground(String... param) {
                String uri = param[0];
                try {
                    result = getData(uri);
                    Log.i("result", "==================================="+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                ServerData[] serverData = gson.fromJson(result, ServerData[].class);
                for(ServerData data : serverData){
                    datas.add(data);
                }
                return datas;
            }

            @Override
            protected void onPostExecute(List<ServerData> result) {
                iLoadData.setServerData(result);
            }
        }.execute(iLoadData.getUrl());
    }
}
