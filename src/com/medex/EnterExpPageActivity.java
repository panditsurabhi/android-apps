package com.medex;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class EnterExpPageActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("EnterExpPageActivity", "Creating Enter Page, finally...");
        setContentView(R.layout.enter_exp);

        EditText t = (EditText) findViewById(R.id.doctor);
        AsyncTask<String, Void, String> weirdResponseFromWebService = new DataAccessor().execute("");
        try {
        	String fromWebService = weirdResponseFromWebService.get();
        	t.setText(fromWebService);
        	Log.e("EnterExpPageActivity", "Text set to: " + fromWebService);
        } catch (Exception e) {
        	Log.e("EnterExpPageActivity", e.getMessage(), e);
        }
        
        Spinner spinner = (Spinner) findViewById(R.id.ailment);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.ailments_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        	  public void onItemSelected(AdapterView<?> parent,
        		        View view, int pos, long id) {
        		     Toast.makeText(parent.getContext(), "The ailment is " +
        		          parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
        		    }

        		    public void onNothingSelected(AdapterView parent) {
        		      // Do nothing.
        		    }

        });
        Log.e("EnterExpPageActivity", "Created Enter Page.");
    }
    
    private String callWebService() {
    	String response = "Initial Response.";
    	String url = "http://localhost/index.php";
    	HttpClient client = new DefaultHttpClient();
    	HttpGet getRequest = new HttpGet(url);
    	ResponseHandler<String> handler = new BasicResponseHandler();
    	try {
    		System.out.println("Calling WS...");
    		response = client.execute(getRequest, handler);
    		System.out.println("Response: "  + response);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return response;
    }
}