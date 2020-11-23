package com.example.toqsoft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button acending,descending;
    List<EmployeeModel> employeeModels=new ArrayList<>();
    private EmployeeAdapter employeeAdapter;
    private RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView)findViewById(R.id.employ_recycler);
        acending=(Button)findViewById(R.id.ascending);
        descending=(Button)findViewById(R.id.descending);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getCarsResponse();

        acending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AScSorting();
            }
        });

        descending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DESCSorting();
            }
        });

    }

    private void getCarsResponse() {


        RequestInterface api = RetrofitClient.getInstance().getApi();
        Call<ResponModel> call = api.getEmpoyJson();
        call.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response)
            {

                if (response.isSuccessful()) {
                    employeeModels=response.body().getData();

                    employeeAdapter=new EmployeeAdapter(MainActivity.this,employeeModels);
                    recyclerView.setAdapter(employeeAdapter);
                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponModel> call, Throwable t) {

                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                employeeAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                employeeAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void DESCSorting()
    {
        Collections.sort(employeeModels, new Comparator<EmployeeModel>() {
            @Override
            public int compare(EmployeeModel lhs, EmployeeModel rhs) {
                if(Integer.parseInt(lhs.getEmployee_salary()) > Integer.parseInt(rhs.getEmployee_salary())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        employeeAdapter=new EmployeeAdapter(MainActivity.this,employeeModels);
        recyclerView.setAdapter(employeeAdapter);
    }
    public void AScSorting()
    {
        Collections.sort(employeeModels, new Comparator<EmployeeModel>() {
            @Override
            public int compare(EmployeeModel lhs, EmployeeModel rhs) {
                if(Integer.parseInt(lhs.getEmployee_salary()) < Integer.parseInt(rhs.getEmployee_salary())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        employeeAdapter=new EmployeeAdapter(MainActivity.this,employeeModels);
        recyclerView.setAdapter(employeeAdapter);
    }

}