package com.example.strayanimals.ui.encyclopedia

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder

//https://www.abaozhan.com/news_txtlist_i3593v.html
class GetEncycItem(context: Context) {
    val mContext: Context
    lateinit var encycBeanList:ArrayList<EncycBean>
    init {
        this.mContext = context

    }

    //读取Json
    public fun getJson(): String {
        var builder = StringBuilder()
        try {
            var assetManager: AssetManager = mContext.resources.assets
            var inputStreamReader: InputStreamReader =
                InputStreamReader(assetManager.open("encyclopedia.json"), "UTF-8")
            var buffReader: BufferedReader = BufferedReader(inputStreamReader)
            var line: String? = null
            while ((buffReader.readLine().also { line = it }) != null) {
                builder.append(line)
            }
            buffReader.close()
            inputStreamReader.close()
        } catch (e: IOException) {
            e.printStackTrace();
        } catch (e: JSONException) {
            e.printStackTrace();
        }
        return builder.toString()
    }

    public fun getItem(): ArrayList<EncycBean> {
        var encycJson = JSONObject(getJson())
        encycBeanList = ArrayList<EncycBean>()
        try {
            var json = encycJson.getJSONArray("StrayAnimal")
            for (i in 0..json.length()) {
                var jsonObject = json.getJSONObject(i)
                var bean = EncycBean()
                bean.setTitle(jsonObject.getString("title"))
                bean.setUrl(jsonObject.getString("url"))
                bean.setImg(jsonObject.getString("img"))
                bean.setContent(jsonObject.getString("content"))

                encycBeanList.add(bean)
            }
        } catch (e: IOException) {
            e.printStackTrace();
        } catch (e: JSONException) {
            e.printStackTrace();
        }
        return encycBeanList
    }
}