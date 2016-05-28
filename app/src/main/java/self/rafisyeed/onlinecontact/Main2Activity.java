package self.rafisyeed.onlinecontact;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Main2Activity extends ListActivity {

    ArrayList<HashMap<String,String >> contactlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        contactlist = new ArrayList<HashMap<String, String>>();
        String url = "http://api.androidhive.info/contacts/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonResponseObject) {
                Log.d("response>>>>",jsonResponseObject.toString());
                try{
                    JSONArray jsonArray = jsonResponseObject.getJSONArray("contacts");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String id = obj.getString("id");
                        String name = obj.getString("name");
                        String email = obj.getString("email");
                        String address = obj.getString("address");
                        String gender = obj.getString("gender");
                        JSONObject phone = obj.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        HashMap<String,String> map = new HashMap<String,String>();
                        map.put("id",id);
                        map.put("name",name);
                        map.put("email",email);
                        map.put("address",address);
                        map.put("gender",gender);
                        map.put("mobile",mobile);
                        contactlist.add(map);
                    }
                    ListAdapter adapter = new SimpleAdapter(Main2Activity.this,contactlist,R.layout.list_item,new String[]{"id",
                    "name","email","address","gender","mobile"},new int[]{R.id.textView,R.id.textView2,R.id.textView3,R.id.textView4,
                    R.id.textView5,R.id.textView6});
                    setListAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
