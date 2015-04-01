/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimerTaskGame;

import GameInfo.Player;
import GameInfo.Room;
import JudgeStatus.JudgeStatus;
import SendingData.SSLClient;
import static TimerTaskGame.Ask4RoomList.STATUS;
import UI.Registration;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Wei
 */
public class Ask4Roominfo extends TimerTask {

    private JSONObject response;
    private int userid;
    private int roomid;
    private int status;

    public static String KEY_RES = "result";
    public static String KEY_MEMBERS = "members";
    public static String KEY_TITLE = "title";
    public static String KEY_NUMBER = "mnumber";
    public static String KEY_TYPE = "type";
    public static String KEY_WAGER = "wager";
    public static String KEY_ROOMSTATUS = "roomstatus";
    public static String KEY_NICKNAME = "nickname";

    private String title;
    private int number;
    private int type;
    private int wager;
    private int roomstatus;
    private JSONArray members;
    private String[] Members = new String[10];
    private int members_num;

    public static String KEY_ROOMINFO = "roominfo";

    public interface OnRefreshListener {

        public void onRefresh(String test);
    }

    private OnRefreshListener onRefreshListener = null;

    public void setOnRefreshListner(OnRefreshListener listener) {
        onRefreshListener = listener;
    }

    @Override
    public void run() {
        try {
            response = SSLClient.postMessage(getMessge());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(response);
        try {
            status = response.getInt(STATUS);
        } catch (JSONException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
        //static method
        if (JudgeStatus.OutputStatus(status) == false) {
            return;
        }

        JSONObject result = new JSONObject();
        try {
            result = response.getJSONObject(KEY_RES);
        } catch (JSONException ex) {
            Logger.getLogger(Ask4Roominfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            members = result.getJSONArray(KEY_MEMBERS);
            title = result.getString(KEY_TITLE);
            number = result.getInt(KEY_NUMBER);
            type = result.getInt(KEY_TYPE);
            wager = result.getInt(KEY_WAGER);
            roomstatus = result.getInt(KEY_ROOMSTATUS);
            Members[0] = result.getString(KEY_NICKNAME);
        } catch (JSONException ex) {
            Logger.getLogger(Ask4Roominfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (members != null) {
            members_num = members.length() + 1;
            for(int i = 1; i < members_num - 1; i++){
                try {
                    Members[i] = members.getJSONObject(i).getString(KEY_NICKNAME);
                } catch (JSONException ex) {
                    Logger.getLogger(Ask4Roominfo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            members_num = 1;
        }
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh("test");
        }

    }

    public String Get_title() {
        return this.title;
    }

    public int Get_number() {
        return this.number;
    }

    public int Get_type() {
        return this.type;
    }

    public int Get_wager() {
        return this.wager;
    }

    public int Get_members_num() {
        return this.members_num;
    }

    public int Get_roomstatus() {
        return this.roomstatus;
    }

    public JSONObject getMessge() {
        JSONObject test = new JSONObject();
        userid = Player.GetPlayer().GetUserId();
        try {
            test.put("opt", KEY_ROOMINFO);
            test.put("userid", userid);
            JSONObject info = new JSONObject();
            roomid = Room.Getroom().GetRoomID();
            info.put("roomid", roomid);
            userid = Player.GetPlayer().GetUserId();

            test.put("info", info);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(test);
        return test;
    }
}
