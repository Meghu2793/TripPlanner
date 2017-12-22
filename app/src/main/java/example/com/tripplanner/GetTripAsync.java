package example.com.tripplanner;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetTripAsync extends AsyncTask<StringBuilder,Void,ArrayList<Place>> {

    IData context;

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<Place> s) {
        if(s!=null){
            context.returnValues(s);
        } else{
            Log.d("demo", "No Results Found");
            //Toast.makeText(AddTripActivity.this,"No Results Found",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected ArrayList<Place> doInBackground(StringBuilder... stringBuilders) {
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(stringBuilders[0].toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder s = new StringBuilder();
            String line = "";
            while((line = bufferedReader.readLine())!=null){
                s.append(line);
            }
            return TripSearchUtil.TripSearchJSONParser.parseTripSearch(s.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    interface  IData {
        void returnValues(ArrayList<Place> places);
    }
}
